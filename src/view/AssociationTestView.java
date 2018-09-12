package view;

import org.nd4j.linalg.api.ndarray.INDArray;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import utilities.GeneralData;
import utilities.Utilities;

public class AssociationTestView
{
	@FXML private Label resultLabel;
	@FXML private Label graphicsNameLabel;
	@FXML private Label musicNameLabel;
	@FXML private ImageView graphicsImageView;
	
	@FXML private GridPane grid;
	
	private String musicPath;	
	private boolean play = false;
	
	@FXML
	public void initialize()
	{
		grid.getColumnConstraints().add(Utilities.getColumnConstraints(HPos.CENTER, 50));
		grid.getColumnConstraints().add(Utilities.getColumnConstraints(HPos.CENTER, 50));
		grid.getRowConstraints().add(Utilities.getRowConstraints(VPos.CENTER, 10));
		grid.getRowConstraints().add(Utilities.getRowConstraints(VPos.CENTER, 80));
		grid.getRowConstraints().add(Utilities.getRowConstraints(VPos.CENTER, 10));
	}
	
	@FXML
	public void onPlayMusicButtonClicked()
	{
		Utilities.musicPath = musicPath;
		if (! play)
		{
			Utilities.playAudio();
			play = true;
		}
		else
		{
			Utilities.pauseAudio();
			play = false;
		}
	}
	
	@FXML
	public void onRandomTestInstanceButtonClicked()
	{
		Pair<INDArray, INDArray> resultPair = GeneralData.associationModelManager.runRandomTestInstance();
		INDArray expectedResult = resultPair.getKey();
		INDArray modelPrediction = resultPair.getValue();
		
		Pair<String, String> graphicsPair = Utilities.getGraphicsPath(expectedResult);
		Pair<String, String> musicPair = Utilities.getMusicPath(modelPrediction); 
		musicPath = musicPair.getKey();
		
		resultLabel.setText("For a single example that is labeled " + expectedResult + " the model predicted " + modelPrediction);
		graphicsNameLabel.setText(graphicsPair.getValue());
		musicNameLabel.setText(musicPair.getValue());
		
		Image resultImage = new Image("file:" + graphicsPair.getKey());
		graphicsImageView.setImage(resultImage);
	}
}
