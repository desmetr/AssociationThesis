package association;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.Layer;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration.ListBuilder;
import org.deeplearning4j.nn.conf.distribution.UniformDistribution;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer.Builder;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import utilities.PropertyManager;

public class AssociationModelManager
{	
	public AssociationModelManager()
	{
	}

	public String trainAndTestModel()
	{		
		String resultText = "";
		
        int labelIndex = 224;
        int numClasses = 4;
        int batchSize = 2000;
        int numLinesToSkip = 0;
        char delimiter = ',';
        
        // Model
        RecordReader recordReader = new CSVRecordReader(numLinesToSkip, delimiter);
        try 
        {
        	recordReader.initialize(new FileSplit(new File(PropertyManager.getAllDataPath())));
		} 
        catch (IOException | InterruptedException e) {	e.printStackTrace();	}	
        
		DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize, labelIndex, numClasses);
        DataSet allData = iterator.next();
        
        ArrayList<String> labelNames = new ArrayList<String>();
        labelNames.addAll(Arrays.asList("Bruegel", "Mondriaan", "Picasso", "Rubens"));
        allData.setLabelNames(labelNames);
      
        allData.shuffle();
        SplitTestAndTrain testAndTrainData = allData.splitTestAndTrain(0.8);

        DataSet trainingData = testAndTrainData.getTrain();
        DataSet testData = testAndTrainData.getTest();
        
        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(trainingData);
        normalizer.transform(trainingData);
        normalizer.transform(testData);        		
        
    	int numInputs = 224;
        int outputNum = 4;
    	long seed = 6;
    	
		MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
	            .seed(seed)
	            .activation(Activation.SELU)
	            .weightInit(WeightInit.RELU)
	            .updater(new Sgd(0.1))
	            .l2(1e-4)
	            .list()
	            .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(200).build())
	            .layer(1, new DenseLayer.Builder().nIn(200).nOut(150).build())
	            .layer(2, new DenseLayer.Builder().nIn(150).nOut(85).build())
	            .layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
	                    .activation(Activation.SIGMOID).nIn(85).nOut(outputNum).build())
	            .backprop(true).pretrain(true)
	            .build();
	
	    //run the model
	    MultiLayerNetwork model = new MultiLayerNetwork(conf);
	    model.init();
	    
//        // Initialize the user interface backend
//        UIServer uiServer = UIServer.getInstance();
//
//        // Configure where the network information (gradients, score vs. time etc) is to be stored. Here: store in memory.
//        StatsStorage statsStorage = new InMemoryStatsStorage();         //Alternative: new FileStatsStorage(File), for saving and loading later
//
//        // Attach the StatsStorage instance to the UI: this allows the contents of the StatsStorage to be visualized
//        uiServer.attach(statsStorage);
//
//        // Then add the StatsListener to collect this information from the network, as it trains
//        model.setListeners(new StatsListener(statsStorage));
		
	    model.setListeners(new ScoreIterationListener(100));
		model.fit(trainingData);
		
		Evaluation eval = new Evaluation(numClasses);
		INDArray output = model.output(testData.getFeatureMatrix());
		eval.eval(testData.getLabels(), output);
		
//		System.out.println(testData.getLabelName(0));
//		resultText += "\n" + model.output(testData.get(0).getFeatures(), false) + "\n";
		
//		if (GeneralData.associationVerbose)
//		{
			for (int i = 0; i < testData.numExamples(); i++)
			{
//				String expectedResult = Utilities.oneHotToLabel(testData.getLabels().getRow(i));
				INDArray expectedResult = testData.getLabels().getRow(i);
	//			String modelPrediction = Utilities.oneHotToLabel(output.getRow(i));
				INDArray modelPrediction = output.getRow(i);
				resultText += "\nFor a single example that is labeled " + expectedResult + " the model predicted " + modelPrediction;
			}
//		}

		resultText += "\n\n";
		
		resultText += model.summary() + "\n";
		resultText += eval.stats() + "\n";
		resultText += eval.confusionToString() + "\n";
		
		return resultText;
	}
	
	public void test()
	{
		// list off input values, 4 training samples with data for 2 input-neurons each
        INDArray input = Nd4j.zeros(4, 2);

        // correspondending list with expected output values, 4 training samples with data for 2 output-neurons each
        INDArray labels = Nd4j.zeros(4, 2);

        // create first dataset
        // when first input=0 and second input=0
        input.putScalar(new int[]{0, 0}, 0);
        input.putScalar(new int[]{0, 1}, 0);
        // then the first output fires for false, and the second is 0
        labels.putScalar(new int[]{0, 0}, 1);
        labels.putScalar(new int[]{0, 1}, 0);

        // when first input=1 and second input=0
        input.putScalar(new int[]{1, 0}, 1);
        input.putScalar(new int[]{1, 1}, 0);
        // then xor is true, therefore the second output neuron fires
        labels.putScalar(new int[]{1, 0}, 0);
        labels.putScalar(new int[]{1, 1}, 1);

        // same as above
        input.putScalar(new int[]{2, 0}, 0);
        input.putScalar(new int[]{2, 1}, 1);
        labels.putScalar(new int[]{2, 0}, 0);
        labels.putScalar(new int[]{2, 1}, 1);

        // when both inputs fire, xor is false again - the first output should fire
        input.putScalar(new int[]{3, 0}, 1);
        input.putScalar(new int[]{3, 1}, 1);
        labels.putScalar(new int[]{3, 0}, 1);
        labels.putScalar(new int[]{3, 1}, 0);

        // create dataset object
        DataSet ds = new DataSet(input, labels);

        // Set up network configuration
        NeuralNetConfiguration.Builder builder = new NeuralNetConfiguration.Builder();
        // Updater and learning rate
        builder.updater(new Sgd(0.1));
        // fixed seed for the random generator, so any run of this program brings the same results
        // may not work if you do something like ds.shuffle()
        builder.seed(123);
        // init the bias with 0 - empirical value, too
        builder.biasInit(0);
        // from "http://deeplearning4j.org/architecture": The networks can process the input more quickly and more accurately by ingesting
        // minibatches 5-10 elements at a time in parallel. this example runs better without, because the dataset is smaller than the mini batch size
        builder.miniBatch(false);

        // create a multilayer network with 2 layers (including the output layer, excluding the input payer)
        ListBuilder listBuilder = builder.list();

        DenseLayer.Builder hiddenLayerBuilder = new DenseLayer.Builder();
        // two input connections - simultaneously defines the number of input neurons, because it's the first non-input-layer
        hiddenLayerBuilder.nIn(2);
        // number of outgooing connections, nOut simultaneously defines the number of neurons in this layer
        hiddenLayerBuilder.nOut(4);
        // put the output through the sigmoid function, to cap the output value between 0 and 1
        hiddenLayerBuilder.activation(Activation.SIGMOID);
        // random initialize weights with values between 0 and 1
        hiddenLayerBuilder.weightInit(WeightInit.DISTRIBUTION);
        hiddenLayerBuilder.dist(new UniformDistribution(0, 1));

        // build and set as layer 0
        listBuilder.layer(0, hiddenLayerBuilder.build());

        // MCXENT or NEGATIVELOGLIKELIHOOD (both are mathematically equivalent) work ok for this example - this
        // function calculates the error-value (aka 'cost' or 'loss function value'), and quantifies the goodness
        // or badness of a prediction, in a differentiable way
        // For classification (with mutually exclusive classes, like here), use multiclass cross entropy, in conjunction
        // with softmax activation function
        Builder outputLayerBuilder = new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD);
        // must be the same amout as neurons in the layer before
        outputLayerBuilder.nIn(4);
        // two neurons in this layer
        outputLayerBuilder.nOut(2);
        outputLayerBuilder.activation(Activation.SOFTMAX);
        outputLayerBuilder.weightInit(WeightInit.DISTRIBUTION);
        outputLayerBuilder.dist(new UniformDistribution(0, 1));
        listBuilder.layer(1, outputLayerBuilder.build());

        // no pretrain phase for this network
        listBuilder.pretrain(false);

        // seems to be mandatory
        // according to agibsonccc: You typically only use that with
        // pretrain(true) when you want to do pretrain/finetune without changing
        // the previous layers finetuned weights that's for autoencoders and
        // rbms
        listBuilder.backprop(true);

        // build and init the network, will check if everything is configured
        // correct
        MultiLayerConfiguration conf = listBuilder.build();
        MultiLayerNetwork net = new MultiLayerNetwork(conf);
        net.init();

        // add an listener which outputs the error every 100 parameter updates
        net.setListeners(new ScoreIterationListener(100));

        // C&P from LSTMCharModellingExample
        // Print the number of parameters in the network (and for each layer)
        Layer[] layers = net.getLayers();
        int totalNumParams = 0;
        for (int i = 0; i < layers.length; i++) {
            int nParams = layers[i].numParams();
            System.out.println("Number of parameters in layer " + i + ": " + nParams);
            totalNumParams += nParams;
        }
        System.out.println("Total number of network parameters: " + totalNumParams);

        // here the actual learning takes place
        for( int i=0; i<10000; i++ ) {
            net.fit(ds);
        }

        // create output for every training sample
        INDArray output = net.output(ds.getFeatures());
        System.out.println(output);

        // let Evaluation prints stats how often the right output had the
        // highest value
        Evaluation eval = new Evaluation(2);
        eval.eval(ds.getLabels(), output);
        System.out.println(eval.stats());

	}
}
