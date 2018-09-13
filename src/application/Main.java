package application;
	
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import music.model.data.MusicalData;
import utilities.GeneralData;
import utilities.GeneralData.ScreenType;
import utilities.PropertyManager;
import view.MainView;

public class Main extends Application
{
	public static BorderPane root;
	public static Map<ScreenType, BorderPane> screens;
	
	private Image icon;

	@Override
	public void start(Stage primaryStage) 
	{
		try
		{	
			icon = new Image("file:icon.png");
			new PropertyManager().getAllValues();
			MusicalData.initialize();
			GeneralData.primaryStage = primaryStage;
			GeneralData.preprocessMusicFiles();
			GeneralData.initializeClasses();
			
			FXMLLoader loaderMain = new FXMLLoader();
			loaderMain.setLocation(getClass().getResource("/view/MainScreen.fxml"));
			root = loaderMain.load(); 
									
			screens = new HashMap<ScreenType, BorderPane>();
			
			FXMLLoader loaderGraphics = new FXMLLoader();
			loaderGraphics.setLocation(getClass().getResource("/view/GraphicsScreen.fxml"));				
			screens.put(ScreenType.GRAPHICS, (BorderPane)loaderGraphics.load());
			
			FXMLLoader loaderMusic = new FXMLLoader();
			loaderMusic.setLocation(getClass().getResource("/view/MusicScreen.fxml"));	
			screens.put(ScreenType.MUSIC, (BorderPane)loaderMusic.load());
			
			FXMLLoader loaderSupervisedAssociationTrain = new FXMLLoader();
			loaderSupervisedAssociationTrain.setLocation(getClass().getResource("/view/SupervisedAssociationTrainScreen.fxml"));
			screens.put(ScreenType.ASSOCIATION_TRAIN_SUPERVISED, (BorderPane)loaderSupervisedAssociationTrain.load());
			
			FXMLLoader loaderSupervisedAssociationTest = new FXMLLoader();
			loaderSupervisedAssociationTest.setLocation(getClass().getResource("/view/SupervisedAssociationTestScreen.fxml"));
			screens.put(ScreenType.ASSOCIATION_TEST_SUPERVISED, (BorderPane)loaderSupervisedAssociationTest.load());
					
			FXMLLoader loaderUnsupervisedAssociationTrain = new FXMLLoader();
			loaderUnsupervisedAssociationTrain.setLocation(getClass().getResource("/view/UnsupervisedAssociationTrainScreen.fxml"));				
			screens.put(ScreenType.ASSOCIATION_TRAIN_UNSUPERVISED, (BorderPane)loaderUnsupervisedAssociationTrain.load());
			
			FXMLLoader loaderUnsupervisedAssociationTest = new FXMLLoader();
			loaderUnsupervisedAssociationTest.setLocation(getClass().getResource("/view/UnsupervisedAssociationTestScreen.fxml"));				
			screens.put(ScreenType.ASSOCIATION_TEST_UNSUPERVISED, (BorderPane)loaderUnsupervisedAssociationTest.load());
			
			new MainView();		
						
			Scene scene = new Scene(root, 1250, 750);	
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.getIcons().add(icon);
			primaryStage.setTitle("Association Thesis");
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
		
			primaryStage.show();
		} 
		catch(IOException e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}	
	
	public static void showScreen(ScreenType screenType)
	{
		root.setCenter(screens.get(screenType));			
	}	
}
