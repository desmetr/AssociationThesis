package view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import utilities.GeneralData;

public class UnsupervisedAssociationTrainView 
{
	@FXML private TextField clusterCountTextField;
	@FXML private CheckBox clusteringVerboseCheckBox;
		
	@FXML 
	public void initialize()
	{
	}
	
	@FXML
	public void onKMeansButtonClicked()
	{		
		GeneralData.associationModelManager.clusterKMeans(clusterCountTextField.getText());
	}
	
	@FXML
	public void onAgglomerativeButtonClicked()
	{
		GeneralData.associationModelManager.clusterAgglomerative(clusterCountTextField.getText());
	}
	
	@FXML
	public void onSomClusterButtonClicked()
	{
		GeneralData.associationModelManager.clusterSOM();
	}
	
	@FXML
	public void onClusteringVerboseCheckBoxClicked()
	{
		GeneralData.clusteringVerbose = clusteringVerboseCheckBox.isSelected();
	}
}
