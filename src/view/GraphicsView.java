package view;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import graphics.model.data.GraphicsDataManager;
import graphics.model.parser.GraphicsParser;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import utilities.GeneralData;
import utilities.PropertyManager;
import utilities.Utilities;

public class GraphicsView
{
	@FXML private Label chosenFileLabel;
	@FXML private Label nameOfChosenFile;
	@FXML private Button newFileButton;
	@FXML private Button augmentationButton;
	@FXML private TabPane mainTabPane;
	@FXML private Tab originalImageTab;
	@FXML private TextField augmentationTextField;
	@FXML private CheckBox graphicsVerboseCheckBox;
	@FXML private ImageView originalImageView;
	@FXML private ImageView noiseRemovedImageView;
	@FXML private ImageView linesImageView;
	@FXML private ImageView linesVerticalImageView;
	@FXML private ImageView linesHorizontalImageView;
	@FXML private ImageView lineSegmentsImageView;
	@FXML private ImageView blackImageView;
	@FXML private ImageView whiteImageView;
	@FXML private ImageView redImageView;
	@FXML private ImageView limeImageView;
	@FXML private ImageView blueImageView;
	@FXML private ImageView yellowImageView;
	@FXML private ImageView cyanImageView;
	@FXML private ImageView magentaImageView;
	@FXML private ImageView greenImageView;
	@FXML private ImageView purpleImageView;
	@FXML private ImageView ellipsesImageView;
	@FXML private ImageView cannyContourImageView;
	@FXML private ImageView binaryEdgesImageView;
	@FXML private ImageView cannyTraceImageView;
	@FXML private ImageView positivePolygonsImageView;
	@FXML private ImageView negativePolygonsImageView;
	
	private BufferedImage originalImage;
	private GraphicsParser graphicsParser;
	
	private GraphicsDataManager dataManager;
	
	@FXML 
	public void initialize()
	{
		dataManager = new GraphicsDataManager();
		graphicsParser = new GraphicsParser(dataManager);
	}
		
	@FXML
	public void onParseAllPaintingsClicked()
	{
		parseAllPaintingsInFolder(PropertyManager.getTestFolderGraphics());
		System.out.println("Parsed all test paintings.");
	}
	
	private void parseAllPaintingsInFolder(String folder)
	{
		try 
		{
			File dir = new File(folder);
			File[] directoryListing = dir.listFiles();
			
			dataManager.writeLabelsToFile();
			System.out.println(folder);
			if (directoryListing != null) 
			{
				for (File selectedFile : directoryListing) 
				{
					GeneralData.currentFilePath = selectedFile.getAbsolutePath();
					GeneralData.currentFileName = selectedFile.getName();
					graphicsParser.parseImage(ImageIO.read(selectedFile));
					dataManager.writeValuesToFile();
					dataManager.reset();
				}
			}
		} 
		catch (IOException e) {	e.printStackTrace();	}
	}
	
	@FXML
	public void onNewFileButtonClicked()
	{	
		try 
		{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(PropertyManager.getMainFolderGraphics()));
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.bmp", "*.png"));
			File selectedFile = fileChooser.showOpenDialog(GeneralData.primaryStage);
			
			if (selectedFile == null)
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("No image chosen!");
				alert.setHeaderText("No image chosen!");
				alert.setContentText("Please choose an image to parse!");
				alert.show();
			}
			else
			{
				// Always show original image tab first.
				SingleSelectionModel<Tab> selectionModel = mainTabPane.getSelectionModel();
				selectionModel.select(originalImageTab); //select by object
				
				GeneralData.currentFileName = selectedFile.getName();
				GeneralData.currentFilePath = selectedFile.getAbsolutePath();	
							
				if (! GeneralData.currentFileName.equals(""))
					nameOfChosenFile.setText(GeneralData.currentFileName);
				
				graphicsParser.parseImage(ImageIO.read(selectedFile));
				dataManager.writeLabelsToFile();
				dataManager.writeValuesToFile();
				dataManager.reset();
				
				showOriginalImage();
				showNoiseRemovedImage();
				showLinesDetectionImages();
				showColorSegmentationImages();
				showEllipseDetectionImages();
				showEdgeDetectionImages();
				showPolygonDetectionImages();
			}			
		} 
		catch (IOException e) {	e.printStackTrace();	}
	}
	
	@FXML
	public void onAugmentationButtonClicked()
	{
		try 
		{
			String n = augmentationTextField.getText();
			Process pAugmentor = Runtime.getRuntime().exec("pip install Augmentor");
	        
			if (GeneralData.graphicsVerbose)
			{
				String line;
				BufferedReader is = new BufferedReader(new InputStreamReader(pAugmentor.getInputStream()));
		        while ((line = is.readLine()) != null)
		              System.out.println(line);
				
		        BufferedReader es = new BufferedReader(new InputStreamReader(pAugmentor.getErrorStream()));
		        while ((line = es.readLine()) != null)
		              System.out.println(line);
			}
			pAugmentor.destroy();
			
			Process p = Runtime.getRuntime().exec("python " + PropertyManager.getAugmentationScriptGraphics() + " " + n + " test");
			
			if (GeneralData.graphicsVerbose)
			{
				String line;
				BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
		        while ((line = is.readLine()) != null)
		              System.out.println(line);
	        
		        BufferedReader es = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		        while ((line = es.readLine()) != null)
		              System.out.println(line);
			}
			p.destroy();
			
			augmentationButton.setText("Done");
		} 
		catch (IOException e) {	e.printStackTrace();	}
	}
	
	@FXML
	public void onGraphicsVerboseCheckBoxClicked()
	{
		GeneralData.graphicsVerbose = graphicsVerboseCheckBox.isSelected();
		System.out.println(GeneralData.graphicsVerbose);
	}
	
	private void showOriginalImage()
	{
		Image originalImageToShow = SwingFXUtils.toFXImage(originalImage, null);
		originalImageView.setImage(originalImageToShow);
	}
	
	private void showNoiseRemovedImage()
	{
		Image noiseRemovedImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageNoiseRemoved(), null);
		noiseRemovedImageView.setImage(noiseRemovedImageToShow);
	}
	
	private void showLinesDetectionImages()
	{		
		Image linesDetectedImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageLinesDetected(), null);
		Image lineSegmentsDetectedImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageLineSegmentsDetected(), null);
//		Image linesVerticalImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageVerticalLines(), null);
//		Image linesHorizontalImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageHorizontalLines(), null);
		
		linesImageView.setImage(linesDetectedImageToShow);		
		lineSegmentsImageView.setImage(lineSegmentsDetectedImageToShow);
//		linesVerticalImageView.setImage(linesVerticalImageToShow);
//		linesHorizontalImageView.setImage(linesHorizontalImageToShow);
	}
	
	private void showEdgeDetectionImages()
	{
		Image cannyContourImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageCannyContour(), null);
		Image binaryEdgesImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageBinaryEdges(), null);
		Image cannyTraceImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageCannyTrace(), null);
		
		cannyContourImageView.setImage(cannyContourImageToShow);
		binaryEdgesImageView.setImage(binaryEdgesImageToShow);
		cannyTraceImageView.setImage(cannyTraceImageToShow);
	}	
	
	private void showEllipseDetectionImages()
	{
		Image ellipseDetectionImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageEllipseDetetcion(), null);
		ellipsesImageView.setImage(ellipseDetectionImageToShow);
	}
	
	private void showColorSegmentationImages()
	{
		Image blackImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageColorSegmentationBlack(), null);
		Image whiteImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageColorSegmentationWhite(), null);
		Image redImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageColorSegmentationRed(), null);
		Image limeImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageColorSegmentationLime(), null);
		Image blueImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageColorSegmentationBlue(), null);
		Image yellowImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageColorSegmentationYellow(), null);
		Image cyanImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageColorSegmentationCyan(), null);
		Image magentaImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageColorSegmentationMagenta(), null);
		Image greenImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageColorSegmentationGreen(), null);
		Image purpleImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageColorSegmentationPurple(), null);
		
		blackImageView.setImage(blackImageToShow);
		whiteImageView.setImage(whiteImageToShow);
		redImageView.setImage(redImageToShow);
		limeImageView.setImage(limeImageToShow);
		blueImageView.setImage(blueImageToShow);
		yellowImageView.setImage(yellowImageToShow);
		cyanImageView.setImage(cyanImageToShow);
		magentaImageView.setImage(magentaImageToShow);
		greenImageView.setImage(greenImageToShow);
		purpleImageView.setImage(purpleImageToShow);
	}
	
	private void showPolygonDetectionImages()
	{
		Image positivePolygonDetectionImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImagePositivePolygonDetection(), null);
		Image negativePolygonDetectionImageToShow = SwingFXUtils.toFXImage(graphicsParser.getImageNegativePolygonDetection(), null);
	
		positivePolygonsImageView.setImage(positivePolygonDetectionImageToShow);
		negativePolygonsImageView.setImage(negativePolygonDetectionImageToShow);
	}
}
