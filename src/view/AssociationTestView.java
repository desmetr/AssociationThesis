package view;

import org.nd4j.linalg.api.ndarray.INDArray;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Pair;
import utilities.GeneralData;

public class AssociationTestView
{
	@FXML private Label resultLabel;
	
	@FXML
	public void onPlayMusicButtonClicked()
	{
		System.out.println("A");
	}
	
	@FXML
	public void onRandomTestInstanceButtonClicked()
	{
		Pair<INDArray, INDArray> resultPair = GeneralData.associationModelManager.runRandomTestInstance();
		INDArray expectedResult = resultPair.getKey();
		INDArray modelPrediction = resultPair.getValue();
		
		resultLabel.setText("For a single example that is labeled " + expectedResult + " the model predicted " + modelPrediction);
	}
}
