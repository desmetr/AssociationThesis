package view;

import application.Main;
import association.AssociationModelManager;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import utilities.GeneralData;
import utilities.GeneralData.ScreenType;

public class MainView
{
	@FXML private MenuItem testSupervisedMenuItem;
	@FXML private MenuItem testUnsupervisedMenuItem;
	
	private boolean isManagerInitialized = false;
	
	@FXML 
	public void initialize()
	{
		testSupervisedMenuItem.setDisable(true);
		testUnsupervisedMenuItem.setDisable(true);
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
	public void onTrainSupervisedAssociationClicked()
	{
		GeneralData.associationModelManager = new AssociationModelManager();
		isManagerInitialized = true;
		testSupervisedMenuItem.setDisable(false);
		Main.showScreen(ScreenType.ASSOCIATION_TRAIN_SUPERVISED);
	}
	
	@FXML
	public void onTestSupervisedAssociationClicked()
	{
		try 
		{
			if (isManagerInitialized == true)
				Main.showScreen(ScreenType.ASSOCIATION_TEST_SUPERVISED);
			else
				throw new Exception("No AssociationModelManager found!");
		}
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@FXML
	public void onTrainUnsupervisedAssociationClicked()
	{
		GeneralData.associationModelManager = new AssociationModelManager();
		isManagerInitialized = true;
		testUnsupervisedMenuItem.setDisable(false);
		Main.showScreen(ScreenType.ASSOCIATION_TRAIN_UNSUPERVISED);
	}
	
	@FXML
	public void onTestUnsupervisedAssociationClicked()
	{
		try 
		{
			if (isManagerInitialized == true)
				Main.showScreen(ScreenType.ASSOCIATION_TEST_UNSUPERVISED);
			else
				throw new Exception("No AssociationModelManager found!");
		}
		catch (Exception e) {	e.printStackTrace();	}
	}
}
