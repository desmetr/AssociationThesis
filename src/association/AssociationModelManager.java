package association;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.time.StopWatch;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.clustering.cluster.Cluster;
import org.deeplearning4j.clustering.cluster.ClusterSet;
import org.deeplearning4j.clustering.cluster.Point;
import org.deeplearning4j.clustering.cluster.PointClassification;
import org.deeplearning4j.clustering.kmeans.KMeansClustering;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.InMemoryLookupCache;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.text.documentiterator.LabelsSource;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.core.io.ClassPathResource;

import javafx.util.Pair;
import utilities.PropertyManager;

public class AssociationModelManager
{	
	private DataSet trainingData;
	private DataSet testData;
	private MultiLayerNetwork model;
	
	public AssociationModelManager()
	{
	}

	public String trainAndTestModelSupervised()
	{		
		String resultText = "";
		
        int labelIndex = 224;
        int numClasses = 2;
        int batchSize = 1000;
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
        labelNames.addAll(Arrays.asList("Bruegel", "Picasso"));
//        labelNames.addAll(Arrays.asList("Bruegel", "Mondriaan", "Picasso", "Rubens"));
        allData.setLabelNames(labelNames);
      
        allData.shuffle();
        SplitTestAndTrain testAndTrainData = allData.splitTestAndTrain(0.8);

        trainingData = testAndTrainData.getTrain();
        testData = testAndTrainData.getTest();
        
        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(trainingData);
        normalizer.transform(trainingData);
        normalizer.transform(testData);        		
        
    	int numInputs = 224;
        int outputNum = numClasses;
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
	                    .activation(Activation.SOFTMAX).nIn(85).nOut(outputNum).build())
	            .backprop(true).pretrain(true)
	            .build();
	
	    //run the model
	    model = new MultiLayerNetwork(conf);
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
	
	public Pair<INDArray, INDArray> testRandomInstance()
	{
		int upperLimit = testData.numExamples();
		int index = new Random().nextInt(upperLimit);
		INDArray output = model.output(testData.getFeatureMatrix());
		INDArray expectedResult = testData.getLabels().getRow(index);
		INDArray modelPrediction = output.getRow(index);
		
		return new Pair<INDArray, INDArray>(expectedResult, modelPrediction);
	}
	
	public void trainAndTestModelUnsupervised()
	{
		try 
		{
			String datafilepath = "bbchealth.txt";
	        ClassPathResource resource = new ClassPathResource(datafilepath);
	        File file = resource.getFile();
	        SentenceIterator iter = new BasicLineIterator(file);
	        InMemoryLookupCache cache = new InMemoryLookupCache();
	        TokenizerFactory t = new DefaultTokenizerFactory();
	        t.setTokenPreProcessor(new CommonPreprocessor());
	
	        StopWatch sw = new StopWatch();
	        /*
	             if you don't have LabelAwareIterator handy, you can use synchronized labels generator
	              it will be used to label each document/sequence/line with it's own label.
	              But if you have LabelAwareIterator ready, you can can provide it, for your in-house labels
	        */
	        LabelsSource source = new LabelsSource("DOC_");
	
	        ParagraphVectors vec = new ParagraphVectors.Builder()
	                .minWordFrequency(1)
	                .iterations(3)
	                .epochs(1)
	                .layerSize(100)  /*length of a paragraph vector*/
	                .learningRate(0.025)
	                .labelsSource(source)
	                .windowSize(5)
	                .iterate(iter)
	                .trainWordVectors(false)
	                .vocabCache(cache)
	                .tokenizerFactory(t)
	                .sampling(0)
	                .build();
	
	        vec.fit();
	
	        
	        //1. create a kmeanscluster instance
	        int maxIterationCount = 5;
	        int clusterCount = 10;
	        String distanceFunction = "cosinesimilarity";
	        KMeansClustering kmc = KMeansClustering.setup(clusterCount, maxIterationCount, distanceFunction);
	
	        //2. iterate over rows in the paragraphvector and create a List of paragraph vectors
	        List<INDArray> vectors = new ArrayList<INDArray>();
	        for (String word : vec.vocab().words()) {
	        	vectors.add(vec.getWordVectorMatrix(word));
	        }
	        System.out.println(vectors.size() + " vectors extracted to create Point list");
	        List<Point> pointsLst = Point.toPoints(vectors);
	        System.out.println(pointsLst.size() + " Points created out of " + vectors.size() + " vectors");
	        
	        System.out.println("Start Clustering " + pointsLst.size() + " points/docs");
	        sw.reset();
	        sw.start();
	        ClusterSet cs = kmc.applyTo(pointsLst);
	        sw.stop();
	        System.out.println("Time taken to run clustering on " + vectors.size() + " paragraphVectors: " + sw.getTime());
	        vectors = null;  
	        pointsLst = null;
	
	        System.out.println("Finish  Clustering");
	
	        List<Cluster> clsterLst = cs.getClusters();
	
			System.out.println("\nCluster Centers:");
	        for(Cluster c: clsterLst) {
	        	Point center = c.getCenter();
	        	System.out.println(center.getId());
	        }
	
	        System.out.println("Trying to classify a point that was used for generating the Clusters");
	        double[] nesVec = vec.getWordVector("DOC_400");
	        Point newpoint = new Point("myid", "mylabel", nesVec);
	        PointClassification pc = cs.classifyPoint(newpoint);
	        System.out.println(pc.getCluster().getCenter().getId());
	       
	        System.out.println("\nEnd Test");
		} 
		catch (IOException e) {	e.printStackTrace();	}
	}
}
