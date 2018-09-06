package association;

import java.io.File;
import java.io.IOException;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.api.storage.StatsStorage;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.ui.api.UIServer;
import org.deeplearning4j.ui.stats.StatsListener;
import org.deeplearning4j.ui.storage.InMemoryStatsStorage;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
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
	    
//	    // UI
//        //Initialize the user interface backend
//        UIServer uiServer = UIServer.getInstance();
//
//        //Configure where the network information (gradients, score vs. time etc) is to be stored. Here: store in memory.
//        StatsStorage statsStorage = new InMemoryStatsStorage();         //Alternative: new FileStatsStorage(File), for saving and loading later
//
//        //Attach the StatsStorage instance to the UI: this allows the contents of the StatsStorage to be visualized
//        uiServer.attach(statsStorage);
//
//        //Then add the StatsListener to collect this information from the network, as it trains
//        model.setListeners(new StatsListener(statsStorage));
		model.setListeners(new ScoreIterationListener(100));
		
		model.fit(trainingData);
		
		Evaluation eval = new Evaluation(numClasses);
		INDArray output = model.output(testData.getFeatureMatrix());
		eval.eval(testData.getLabels(), output);
		
//		resultText += model.toString() + "\n";
		resultText += eval.stats() + "\n";
		resultText += eval.confusionToString() + "\n";
		
		return resultText;
	}
}
