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
	public void onGraphicsMenuClicked()
	{
		System.out.println("A");
		Main.showScreen(ScreenType.GRAPHICS);
	}

	@FXML 
	public void onMusicMenuClicked()
	{
		System.out.println("B");
		Main.showScreen(ScreenType.MUSIC);
	}

	@FXML
	public void onAssociationMenuClicked()
	{
		System.out.println("C");
		Main.showScreen(ScreenType.ASSOCIATION);
	}
}
