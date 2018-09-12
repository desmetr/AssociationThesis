package view;

import java.io.File;

import association.AssociationDataManager;
import association.AssociationModelManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import utilities.GeneralData;
import utilities.PropertyManager;

public class AssociationTrainView 
{
	@FXML private TextArea textAreaResult;
	@FXML private CheckBox associationVerboseCheckBox;
	
	private AssociationDataManager dataManager;
	
	@FXML 
	public void initialize()
	{
    	dataManager = new AssociationDataManager();
	}

	@FXML
	public void onProcessImagesButtonClicked()
	{
		String resultText = dataManager.readImagesData();
		textAreaResult.appendText(resultText);
		textAreaResult.appendText("Process Images Done\n");
	}
	
	@FXML 
	public void onProcessMusicButtonClicked()
	{
		String resultText = dataManager.readMusicData();
		textAreaResult.appendText(resultText);
		textAreaResult.appendText("Process Music Done\n");
	}
	
	@FXML
	public void onRankButtonClicked()
	{
		String resultText = dataManager.rank();
		textAreaResult.appendText(resultText);
		textAreaResult.appendText("Ranking Done\n");
	}	
	
	@FXML
	public void onMakeCSVButtonClicked()
	{
		String resultText = dataManager.makeCSV();
		textAreaResult.appendText(resultText);
	}
	
	@FXML
	public void onAssociationButtonClicked()
	{
		String resultText = "";
		
		if (! new File(PropertyManager.getAllDataPath()).exists())
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No CSV file found!");
			alert.setHeaderText("No CSV file found!");
			alert.setContentText("Please make sure there is a CSV file!");
			alert.show();
		}
		else
		{
			resultText = GeneralData.associationModelManager.trainAndTestModel();
			textAreaResult.appendText(resultText);
//			dataManager.deleteCSV();
		}
	}
	
	@FXML
	public void onAssociationVerboseCheckBoxClicked()
	{
		GeneralData.associationVerbose = associationVerboseCheckBox.isSelected();
		System.out.println(GeneralData.associationVerbose);
	}
	
	@FXML
	public void onDeleteCSVButtonClicked()
	{
		String resultText = dataManager.deleteCSV();
		textAreaResult.appendText(resultText);	
	}
}
