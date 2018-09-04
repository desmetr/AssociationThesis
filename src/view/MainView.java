package view;

import application.Main;
import javafx.fxml.FXML;
import utilities.GeneralData.ScreenType;

public class MainView
{
	@FXML 
	public void initialize()
	{
	}
	
	@FXML 
	public void onShowParserGraphics()
	{
		Main.showScreen(ScreenType.GRAPHICS);
	}

	@FXML 
	public void onShowParserMusic()
	{
		Main.showScreen(ScreenType.MUSIC);
	}

	@FXML
	public void onShowActionsAssociation()
	{
		Main.showScreen(ScreenType.ASSOCIATION);
	}
}
