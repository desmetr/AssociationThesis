package view;

import association.AssociationModelManager;
import javafx.fxml.FXML;

public class UnsupervisedAssociationTrainView 
{
	@FXML
	public void onClusterDatasetButtonClicked()
	{
		AssociationModelManager associationModelManager = new AssociationModelManager();
		associationModelManager.trainAndTestModelUnsupervised();
	}
}
