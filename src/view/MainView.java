package view;

import application.Main;
import association.AssociationModelManager;
import javafx.fxml.FXML;
import utilities.GeneralData;
import utilities.GeneralData.ScreenType;

public class MainView
{
	@FXML 
	public void initialize()
	{
	}
	
	@FXML 
	public void onShowParserGraphicsClicked()
	{
		Main.showScreen(ScreenType.GRAPHICS);
	}

	@FXML 
	public void onShowParserMusicClicked()
	{
		Main.showScreen(ScreenType.MUSIC);
	}

	@FXML
	public void onTrainAssociationClicked()
	{
		GeneralData.associationModelManager = new AssociationModelManager();
		Main.showScreen(ScreenType.ASSOCIATION_TRAIN);
	}
	
	@FXML
	public void onTestAssociationClicked()
	{
//		try 
//		{
//			if (GeneralData.associationModelManager != null)
//				Main.showScreen(ScreenType.ASSOCIATION_TEST);
//			else
//				throw new Exception("No AssociationModelManager found!");
//		}
//		catch (Exception e) {	e.printStackTrace();	}
		
		Main.showScreen(ScreenType.ASSOCIATION_TEST);
	}
}
