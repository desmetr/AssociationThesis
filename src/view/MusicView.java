package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import music.model.music.MusicalData;
import music.model.music.MusicDataManager;
import music.model.music.Piece;
import music.model.parser.MusicXMLParser;
import utilities.GeneralData;
import utilities.PropertyManager;

public class MusicView 
{
	@FXML private Label chosenFileLabel;
	@FXML private Label nameOfChosenFile;
	@FXML private Button newFileButton;
	@FXML private Button augmentationButton;
	
	@FXML private BarChart<String, Number> harmonyBarChart;
	@FXML private BarChart<String, Number> rhythmBarChart;
	
	@FXML private TextArea generalTextArea;
	@FXML private ListView<String> generalListView;
	@FXML private ListView<String> networkListView;

	@FXML private TextField augmentationTextField;
	
	private Piece currentPiece;
	private String currentFile;
	private MusicDataManager dataManager;
	MusicXMLParser parser;
	
	private Random rand;
	
	private static final Logger logger = LoggerFactory.getLogger("Main");
	
	@FXML 
	public void initialize()
	{
		parser = new MusicXMLParser();
		dataManager = new MusicDataManager();
		dataManager.writeLabelsToFile();
	}
	
	@FXML
	public void onParseAllTestScoresClicked()
	{
		parseAllScoresInFolder(PropertyManager.getTestFolderMusic());
		System.out.println("Test scores parsed.");
	}
	
	private void parseAllScoresInFolder(String folder)
	{
		try 
		{
			File dir = new File(folder);
			File[] directoryListing = dir.listFiles();
			if (directoryListing != null) 
			{
				for (File selectedFile : directoryListing) 
				{
					GeneralData.currentFileName = selectedFile.getName();
					currentPiece = parser.parse(selectedFile);
					dataManager.setPiece(currentPiece);
					dataManager.writeValuesToFile();	
					dataManager.reset();
				}
			}
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@FXML
	public void onNewFileButtonClicked()
	{	
		try 
		{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File("xml_dest"));
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML Files", "*.xml"));
			File selectedFile = fileChooser.showOpenDialog(GeneralData.primaryStage);
			currentFile = selectedFile.getName();
			
		    currentPiece = parser.parse(selectedFile);
		    
		    dataManager.setPiece(currentPiece);
		    dataManager.writeValuesToFile();
			
			generalTextArea.setText(currentPiece.toString());
			
			logger.info(currentFile);
			logger.info("-----------------");
			logger.info(currentPiece.toString());
			
			if (! currentFile.equals(""))
				nameOfChosenFile.setText(currentFile);
			
			setBarChartHarmony();
			setBarChartRhythm();
			
			setGeneralListView();
			setNetworkListView();
			
			dataManager.reset();
			parser.reset();	
			currentPiece.reset();
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@FXML
	public void onAugmentationClicked()
	{
		File file = new File(PropertyManager.getResultFolderMusic() + "vectorsMusic.csv");
		
		BufferedReader br = null;
		String line = "";
		
		try 
		{
			System.out.println("Doing " + augmentationTextField.getText() + " augmentations.");
			
			br = new BufferedReader(new FileReader(file));
			StringBuilder augmentationStringBuilder;
			
			int numberOfLines = -1;
		    while((line = br.readLine()) != null)
		    	numberOfLines++;
		    
		    br.close();
		    br = new BufferedReader(new FileReader(file));
			
		    int iter = 0;
	        while ((line = br.readLine()) != null) 
	        {	        
	        	// Skip first line.
	        	if (iter == 0)
	        	{
	        		iter++;
	        		continue;
	        	}
	        	
	        	// Per original line do augmentation.
	        	for (int i = 0; i < Integer.valueOf(augmentationTextField.getText()); i++)
	        	{
	        		rand = new Random(System.currentTimeMillis());
	        		augmentationStringBuilder = new StringBuilder();
	        		
	        		for (int j = 0; j < GeneralData.numberOfFeatures; j++)
		        	{
	        			String currentValue = line.split(GeneralData.separator)[j];
	        			
		        		// Notes
			        	if (GeneralData.startIndexNotes <= j && j <= GeneralData.endIndexNotes)
							augmentationStringBuilder.append(String.valueOf(doVariation(Integer.valueOf(currentValue), 2)));			        		
			        	// Dynamics
			        	else if (GeneralData.startIndexDynamics <= j && j <= GeneralData.endIndexDynamics)
			        		augmentationStringBuilder.append(String.valueOf(doVariation(Integer.valueOf(currentValue), 3)));
			        	// Tempo
			        	else if (j == GeneralData.indexTempo)
			        		augmentationStringBuilder.append(String.valueOf(doVariation(Integer.valueOf(currentValue), 30)));
			        	// Clef
			        	else if (j == GeneralData.indexClef)
			        	{
			        		if (rand.nextInt(2) == 0)
			        			augmentationStringBuilder.append(currentValue); // Do nothing.
			        		else if (currentValue.equals("0"))			// "0" is TREBLE
			        			augmentationStringBuilder.append("1");	// "1" is BASS
			        		else
			        			augmentationStringBuilder.append("0");
			        	}
			        	// Time signature
			        	else if (j == GeneralData.indexTimeSignature)
			        	{
			        		int index = rand.nextInt(MusicalData.timeSignatures.size());
			        		augmentationStringBuilder.append(MusicalData.timeSignatures.get(index).ordinal());
			        	}
			        	
			        	// Anacrusis
			        	else if (j == GeneralData.indexAnacrusis)
			        		augmentationStringBuilder.append(String.valueOf(rand.nextInt(2)));
			        	
			        	// Append all the other values.
			        	else
			        		augmentationStringBuilder.append(currentValue);
			        	
			        	augmentationStringBuilder.append(GeneralData.separator);
		        	}
	        	
	        		augmentationStringBuilder.append("\n");
	        		Files.write(Paths.get("results" + File.separator + "vectorsMusic.csv"), augmentationStringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
	        	}
	        	
	        	if (iter == numberOfLines)
	        	{
	        		System.out.println("Augmentation done.");
	        		br.close();
	        		return;
	        	}
	        		
//	        	if (iter == 1)
//	        		return;
	        	iter++;
	        }
	        
	        br.close();
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	private int doVariation(int originalValue, int range)
	{
		int n = rand.nextInt(range + range + 1) - range;	// Formule: (max - min + 1) + min
		if ((n + originalValue) < 0)
			return doVariation(originalValue, range);
		return n + originalValue;
	}
	
	private void setBarChartHarmony()
	{
	    XYChart.Series<String, Number> series = new Series<String, Number>();
	    series.setName(currentFile);
	   
	    series.getData().add(new XYChart.Data<String, Number>("Tonic", currentPiece.numberOfTonics));
	    series.getData().add(new XYChart.Data<String, Number>("Supertonic", currentPiece.numberOfSuperTonics));
	    series.getData().add(new XYChart.Data<String, Number>("Mediant", currentPiece.numberOfMediants));
	    series.getData().add(new XYChart.Data<String, Number>("Subdominant", currentPiece.numberOfSubdominants));
	    series.getData().add(new XYChart.Data<String, Number>("Blue Note", currentPiece.numberOfBlueNotes));
	    series.getData().add(new XYChart.Data<String, Number>("Dominant", currentPiece.numberOfDominants));
	    series.getData().add(new XYChart.Data<String, Number>("Submediant", currentPiece.numberOfSubmediants));
	    series.getData().add(new XYChart.Data<String, Number>("Subtonic", currentPiece.numberOfSubtonics));
	    series.getData().add(new XYChart.Data<String, Number>("Leading Tone", currentPiece.numberOfLeadingTones));
	    series.getData().add(new XYChart.Data<String, Number>("Non Scale Note", currentPiece.numberOfNonScaleNotes));
	    
	    harmonyBarChart.getData().add(series);
	}
	
	private void setBarChartRhythm()
	{
		XYChart.Series<String, Number> series = new Series<String, Number>();
		series.setName(currentFile);
		
		series.getData().add(new XYChart.Data<String, Number>("Whole", currentPiece.numberOfWholeNotes));
		series.getData().add(new XYChart.Data<String, Number>("Half", currentPiece.numberOfHalfNotes));
		series.getData().add(new XYChart.Data<String, Number>("Quarter", currentPiece.numberOfQuarterNotes));
		series.getData().add(new XYChart.Data<String, Number>("Eighth", currentPiece.numberOfEighthNotes));
		series.getData().add(new XYChart.Data<String, Number>("Sixteenth", currentPiece.numberOfSixteenthNotes));
		
		series.getData().add(new XYChart.Data<String, Number>("Whole to Sixteenth", currentPiece.numberOfWholeTiedToSixteenthNotes));
		series.getData().add(new XYChart.Data<String, Number>("Half to Sixteenth", currentPiece.numberOfHalfTiedToSixteenthNotes));
		series.getData().add(new XYChart.Data<String, Number>("Quarter to Sixteenth", currentPiece.numberOfQuarterTiedToSixteenthNotes));
		series.getData().add(new XYChart.Data<String, Number>("Eighth to Sixteenth", currentPiece.numberOfEighthTiedToSixteenthNotes));
		series.getData().add(new XYChart.Data<String, Number>("Sixteenth to Sixteenth", currentPiece.numberOfSixteenthTiedToSixteenthNotes));
		
		series.getData().add(new XYChart.Data<String, Number>("Whole To Eighth", currentPiece.numberOfWholeTiedToEighthNotes));
		series.getData().add(new XYChart.Data<String, Number>("Half To Eighth", currentPiece.numberOfHalfTiedToEighthNotes));
		series.getData().add(new XYChart.Data<String, Number>("Quarter To Eighth", currentPiece.numberOfQuarterTiedToEighthNotes));
		series.getData().add(new XYChart.Data<String, Number>("Eighth To Eighth", currentPiece.numberOfEighthTiedToEighthNotes));
		series.getData().add(new XYChart.Data<String, Number>("Sixteenth To Eighth", currentPiece.numberOfSixteenthTiedToEighthNotes));
		
		series.getData().add(new XYChart.Data<String, Number>("Whole To Quarter", currentPiece.numberOfWholeTiedToQuarterNotes));
		series.getData().add(new XYChart.Data<String, Number>("Half To Quarter", currentPiece.numberOfHalfTiedToQuarterNotes));
		series.getData().add(new XYChart.Data<String, Number>("Quarter To Quarter", currentPiece.numberOfQuarterTiedToQuarterNotes));
		series.getData().add(new XYChart.Data<String, Number>("Eighth To Quarter", currentPiece.numberOfEighthTiedToQuarterNotes));
		series.getData().add(new XYChart.Data<String, Number>("Sixteenth To Quarter", currentPiece.numberOfSixteenthTiedToQuarterNotes));
		
		series.getData().add(new XYChart.Data<String, Number>("Whole to Half", currentPiece.numberOfWholeTiedToHalfNotes));
		series.getData().add(new XYChart.Data<String, Number>("Half to Half", currentPiece.numberOfHalfTiedToHalfNotes));
		series.getData().add(new XYChart.Data<String, Number>("Quarter to Half", currentPiece.numberOfQuarterTiedToHalfNotes));
		series.getData().add(new XYChart.Data<String, Number>("Eighth to Half", currentPiece.numberOfEighthTiedToHalfNotes));
		series.getData().add(new XYChart.Data<String, Number>("Sixteenth to Half", currentPiece.numberOfSixteenthTiedToHalfNotes));
		
		series.getData().add(new XYChart.Data<String, Number>("Whole to Whole", currentPiece.numberOfWholeTiedToWholeNotes));
		series.getData().add(new XYChart.Data<String, Number>("Half to Whole", currentPiece.numberOfHalfTiedToWholeNotes));
		series.getData().add(new XYChart.Data<String, Number>("Quarter to Whole", currentPiece.numberOfQuarterTiedToWholeNotes));
		series.getData().add(new XYChart.Data<String, Number>("Eighth to Whole", currentPiece.numberOfEighthTiedToWholeNotes));
		series.getData().add(new XYChart.Data<String, Number>("Sixteenth to Whole", currentPiece.numberOfSixteenthTiedToWholeNotes));
		
		series.getData().add(new XYChart.Data<String, Number>("Half dotted", currentPiece.numberOfHalfDottedNotes));
		series.getData().add(new XYChart.Data<String, Number>("Quarter dotted", currentPiece.numberOfQuarterDottedNotes));
		series.getData().add(new XYChart.Data<String, Number>("Eighth dotted", currentPiece.numberOfEighthDottedNotes));
		series.getData().add(new XYChart.Data<String, Number>("Sixteenth dotted", currentPiece.numberOfSixteenthDottedNotes));
		
		series.getData().add(new XYChart.Data<String, Number>("Half triplet", currentPiece.numberOfHalfTripletNotes));
		series.getData().add(new XYChart.Data<String, Number>("Quarter triplet", currentPiece.numberOfQuarterTripletNotes));
		series.getData().add(new XYChart.Data<String, Number>("Eighth triplet", currentPiece.numberOfEighthTripletNotes));
		series.getData().add(new XYChart.Data<String, Number>("Sixteenth triplet", currentPiece.numberOfSixteenthTripletNotes));
		
		series.getData().add(new XYChart.Data<String, Number>("Rest", currentPiece.numberOfRests));
		
		rhythmBarChart.getData().add(series);
	}
	
	private void setGeneralListView()
	{
		generalListView.getItems().clear();
		generalListView.getItems().add("Key: " + " " + currentPiece.key.noteName + "" + currentPiece.key.scale);
		generalListView.getItems().add("Tempo: " + " " + String.valueOf(currentPiece.tempo));
		generalListView.getItems().add("Clef: " + " " + currentPiece.getClefAsString());
	}
	
	private void setNetworkListView()
	{
		networkListView.getItems().clear();
		networkListView.getItems().add("Tonic: " + " " + currentPiece.numberOfTonics);
		networkListView.getItems().add("Supertonic: " + " " + currentPiece.numberOfSuperTonics);
		networkListView.getItems().add("Mediant: " + " " + currentPiece.numberOfMediants);
		networkListView.getItems().add("Subdominant: " + " " + currentPiece.numberOfSubdominants);
		networkListView.getItems().add("Blue note: " + " " + currentPiece.numberOfBlueNotes);
		networkListView.getItems().add("Dominant: " + " " + currentPiece.numberOfDominants);
		networkListView.getItems().add("Submediant: " + " " + currentPiece.numberOfSubmediants);
		networkListView.getItems().add("Subtonic: " + " " + currentPiece.numberOfSubtonics);
		networkListView.getItems().add("Leading tone: " + " " + currentPiece.numberOfLeadingTones);
		networkListView.getItems().add("Non scale tone: " + " " + currentPiece.numberOfNonScaleNotes);
		networkListView.getItems().add("Whole notes: " + " " + currentPiece.numberOfWholeNotes);
		networkListView.getItems().add("Whole tied to sixteen notes: " + " " + currentPiece.numberOfWholeTiedToSixteenthNotes);
		networkListView.getItems().add("Whole tied to eighth notes: " + " " + currentPiece.numberOfWholeTiedToEighthNotes);
		networkListView.getItems().add("Whole tied to quarter notes: " + " " + currentPiece.numberOfWholeTiedToQuarterNotes);
		networkListView.getItems().add("Whole tied to half notes: " + " " + currentPiece.numberOfWholeTiedToHalfNotes);
		networkListView.getItems().add("Whole tied to whole notes: " + " " + currentPiece.numberOfWholeTiedToWholeNotes);
		networkListView.getItems().add("Half notes: " + " " + currentPiece.numberOfHalfNotes);
		networkListView.getItems().add("Half dotted notes: " + " " + currentPiece.numberOfHalfDottedNotes);
		networkListView.getItems().add("Half triplet notes: " + " " + currentPiece.numberOfHalfTripletNotes);
		networkListView.getItems().add("Half tied to sixteenth notes: " + " " + currentPiece.numberOfHalfTiedToSixteenthNotes);
		networkListView.getItems().add("Half tied to eighth notes: " + " " + currentPiece.numberOfHalfTiedToEighthNotes);
		networkListView.getItems().add("Half tied to quarter notes: " + " " + currentPiece.numberOfHalfTiedToQuarterNotes);
		networkListView.getItems().add("Half tied to half notes: " + " " + currentPiece.numberOfHalfTiedToHalfNotes);
		networkListView.getItems().add("Half tied to whole notes: " + " " + currentPiece.numberOfHalfTiedToWholeNotes);
		networkListView.getItems().add("Quarter notes: " + " " + currentPiece.numberOfQuarterNotes);
		networkListView.getItems().add("Quarter dotted notes: " + " " + currentPiece.numberOfQuarterDottedNotes);
		networkListView.getItems().add("Quarter triplet notes: " + " " + currentPiece.numberOfQuarterTripletNotes);
		networkListView.getItems().add("Quarter tied to sixteenth notes: " + " " + currentPiece.numberOfQuarterTiedToSixteenthNotes);
		networkListView.getItems().add("Quarter tied to eighth notes: " + " " + currentPiece.numberOfQuarterTiedToEighthNotes);
		networkListView.getItems().add("Quarter tied to quarter notes: " + " " + currentPiece.numberOfQuarterTiedToQuarterNotes);
		networkListView.getItems().add("Quarter tied to half notes: " + " " + currentPiece.numberOfQuarterTiedToHalfNotes);
		networkListView.getItems().add("Quarter tied to whole notes: " + " " + currentPiece.numberOfQuarterTiedToWholeNotes);
		networkListView.getItems().add("Eighth notes: " + " " + currentPiece.numberOfEighthNotes);
		networkListView.getItems().add("Eighth dotted notes: " + " " + currentPiece.numberOfEighthDottedNotes);
		networkListView.getItems().add("Eighth triplet notes: " + " " + currentPiece.numberOfEighthTripletNotes);
		networkListView.getItems().add("Eighth tied to sixteenth notes: " + " " + currentPiece.numberOfEighthTiedToSixteenthNotes);
		networkListView.getItems().add("Eighth tied to eighth notes: " + " " + currentPiece.numberOfEighthTiedToEighthNotes);
		networkListView.getItems().add("Eighth tied to quarter notes: " + " " + currentPiece.numberOfEighthTiedToQuarterNotes);
		networkListView.getItems().add("Eighth tied to half notes: " + " " + currentPiece.numberOfEighthTiedToHalfNotes);
		networkListView.getItems().add("Eighth tied to whole notes: " + " " + currentPiece.numberOfEighthTiedToWholeNotes);
		networkListView.getItems().add("Sixteenth notes: " + " " + currentPiece.numberOfSixteenthNotes);
		networkListView.getItems().add("Sixteenth dotted notes: " + " " + currentPiece.numberOfSixteenthDottedNotes);
		networkListView.getItems().add("Sixteenth triplet notes: " + " " + currentPiece.numberOfSixteenthTripletNotes);
		networkListView.getItems().add("Sixteenth tied to sixteenth notes: " + " " + currentPiece.numberOfSixteenthTiedToSixteenthNotes);
		networkListView.getItems().add("Sixteenth tied to eighth notes: " + " " + currentPiece.numberOfSixteenthTiedToEighthNotes);
		networkListView.getItems().add("Sixteenth tied to quarter notes: " + " " + currentPiece.numberOfSixteenthTiedToQuarterNotes);
		networkListView.getItems().add("Sixteenth tied to half notes: " + " " + currentPiece.numberOfSixteenthTiedToHalfNotes);
		networkListView.getItems().add("Sixteenth tied to whole notes: " + " " + currentPiece.numberOfSixteenthTiedToWholeNotes);
		networkListView.getItems().add("Length out of scope notes: " + " " + currentPiece.numberOfLengthOutOfScopes);
		networkListView.getItems().add("Middleties: " + " " + currentPiece.numberOfMiddleOfTies);
		networkListView.getItems().add("Endties: " + " " + currentPiece.numberOfEndOfTies);
		networkListView.getItems().add("Rests: " + " " + currentPiece.numberOfRests);
		networkListView.getItems().add("Key: " + " " + currentPiece.key.noteName.ordinal());
		networkListView.getItems().add("Tempo: " + " " + currentPiece.tempo);
		networkListView.getItems().add("Clef: " + " " + currentPiece.clef.ordinal());
		networkListView.getItems().add("Time signature: " + " " + currentPiece.getTimeSignature().ordinal());
		networkListView.getItems().add("Anacrusis: " + " " + currentPiece.anacrusis);
		networkListView.getItems().add("Total number of notes: " + " " + currentPiece.totalNumberOfNotes);
		networkListView.getItems().add("Total number of measures: " + " " + currentPiece.measures.size());
		networkListView.getItems().add("Number of codas: " + " " + currentPiece.numberOfCodas);
		networkListView.getItems().add("Number of to_codas: " + " " + currentPiece.numberOfToCodas);
		networkListView.getItems().add("Number of segnos: " + " " + currentPiece.numberOfSegnos);
		networkListView.getItems().add("Number of dal_segnos: " + " " + currentPiece.numberOfDalSegnos);
	}
}
