package view;

import java.io.File;

//import association.AssociationManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
//import model.DataManager;
import utilities.PropertyManager;
//import utilities.Utilities;

public class AssociationView 
{
	@FXML private TextArea textAreaResult;
	@FXML private CheckBox verboseCheckBox;
	
//	private DataManager dataManager;
//	private AssociationManager associationManager;
	
	@FXML 
	public void initialize()
	{
//    	dataManager = new DataManager();
	}

	@FXML
	public void onProcessImagesButtonClicked()
	{
//		String resultText = dataManager.processImagesData();
//		textAreaResult.appendText(resultText);
//		textAreaResult.appendText("Process Images Done\n");
	}
	
	@FXML 
	public void onProcessMusicButtonClicked()
	{
//		String resultText = dataManager.processMusicData();
//		textAreaResult.appendText(resultText);
//		textAreaResult.appendText("Process Music Done\n");
	}
	
	@FXML
	public void onRankButtonClicked()
	{
//		String resultText = dataManager.rank();
//		textAreaResult.appendText(resultText);
//		textAreaResult.appendText("Ranking Done\n");
	}	
	
	@FXML
	public void onMakeCSVButtonClicked()
	{
//		String resultText = dataManager.makeCSV();
//		textAreaResult.appendText(resultText);
	}
	
	@FXML
	public void onAssociationButtonClicked()
	{
//		String resultText = "";
//		
//		if (! new File(PropertyManager.getAllDataPath()).exists())
//		{
//			Alert alert = new Alert(AlertType.WARNING);
//			alert.setTitle("No CSV file found!");
//			alert.setHeaderText("No CSV file found!");
//			alert.setContentText("Please make sure there is a CSV file!");
//			alert.show();
//		}
//		else
//		{
//			associationManager = new AssociationManager();
//			resultText = associationManager.trainAndTestModel();
//			textAreaResult.appendText(resultText);
//			dataManager.deleteCSV();
//		}
	}
	
	@FXML
	public void onVerboseCheckBoxClicked()
	{
//		Utilities.verbose = verboseCheckBox.isSelected();
//		System.out.println(Utilities.verbose);
	}
}
