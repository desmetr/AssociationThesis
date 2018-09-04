package utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

import graphics.model.data.ColorData;

public class PropertyManager 
{
//	private static String resultsFolderGraphics = "";
//	private static String resultsFolderMusic = "";
	private static String allDataPath = "";
	
//	private static String sourceFolderGraphics = "";
	private static String mainFolderGraphics = "";
//	private static String bruegelFolderGraphics = "";
//	private static String mondriaanFolderGraphics = "";
//	private static String picassoFolderGraphics = "";
//	private static String rubensFolderGraphics = "";
	private static String augmentationScriptGraphics = "";
	private static String testFolderGraphics = "";
	
	private static String sourceFolderMusic = "";
	private static String destinationFolderMusic = "";
	private static String failDestinationFolderMusic = "";
	private static String resultFolderMusic = "";
	private static String testFolderMusic = "";
//	private static String classicalFolderMusic = "";
//	private static String jazzFolderMusic = "";
//	private static String popFolderMusic = "";
	
	private InputStream inputStream;
 
//	public static String getResultsFolderGraphics() 		{	return resultsFolderGraphics;		}
//	public static String getResultsFolderMusic()			{	return resultsFolderMusic;			}
	public static String getAllDataPath()					{	return allDataPath;					}
	
//	public static String getSourceFolderGraphics() 			{	return sourceFolderGraphics;		}
	public static String getMainFolderGraphics() 			{	return mainFolderGraphics;			}
//	public static String getBruegelFolderGraphics() 		{	return bruegelFolderGraphics;		}
//	public static String getMondriaanFolderGraphics() 		{	return mondriaanFolderGraphics;		}
//	public static String getPicassoFolderGraphics() 		{	return picassoFolderGraphics;		}
//	public static String getRubensFolderGraphics() 			{	return rubensFolderGraphics;		}
	public static String getAugmentationScriptGraphics()	{	return augmentationScriptGraphics;	}
	public static String getTestFolderGraphics()			{	return testFolderGraphics;			}
	
	public static String getSourceFolderMusic() 			{	return sourceFolderMusic;			}
	public static String getDestinationFolderMusic() 		{	return destinationFolderMusic;		}
	public static String getFailDestinationFolderMusic()	{	return failDestinationFolderMusic;	}
	public static String getResultFolderMusic() 			{	return resultFolderMusic;			}
	public static String getTestFolderMusic() 				{	return testFolderMusic;				}
//	public static String getClassicalFolderMusic() 			{	return classicalFolderMusic;		}
//	public static String getJazzFolderMusic() 				{	return jazzFolderMusic;				}
//	public static String getPopFolderMusic() 				{	return popFolderMusic;				}
	
	public void getAllValues()
	{
		getPropertyValues();
		getColors();
	}
	
	public void getPropertyValues()
	{
 		try 
 		{
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null)
				prop.load(inputStream);
			else
				throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath.");
 
			// Get association properties.
//			resultsFolderGraphics = prop.getProperty("resultsFolderGraphics");
//			resultsFolderMusic = prop.getProperty("resultsFolderMusic");
			allDataPath = prop.getProperty("allDataPath");
			
			// Get graphics properties.
//			sourceFolderGraphics = prop.getProperty("sourceFolderGraphics");
			mainFolderGraphics = prop.getProperty("mainFolderGraphics");
//			bruegelFolderGraphics = prop.getProperty("bruegelFolderGraphics");
//			mondriaanFolderGraphics = prop.getProperty("mondriaanFolderGraphics");
//			picassoFolderGraphics = prop.getProperty("picassoFolderGraphics");
//			rubensFolderGraphics = prop.getProperty("rubensFolderGraphics");
			augmentationScriptGraphics = prop.getProperty("augmentationScriptGraphics");
			testFolderGraphics = prop.getProperty("testFolderGraphics");
			
			// Get music properties.
			sourceFolderMusic = prop.getProperty("sourceFolderMusic");
			destinationFolderMusic = prop.getProperty("destinationFolderMusic");
			failDestinationFolderMusic = prop.getProperty("failDestinationFolderMusic");
			resultFolderMusic = prop.getProperty("resultFolderMusic");
			testFolderMusic = prop.getProperty("testFolderMusic");
//			classicalFolderMusic = prop.getProperty("classicalFolderMusic");
//			jazzFolderMusic = prop.getProperty("jazzFolderMusic");
//			popFolderMusic = prop.getProperty("popFolderMusic");
 		} 
 		catch (Exception e) {	e.printStackTrace();	} 
	}
	
	private void getColors()
	{
		String propFileName = "resources/colors.dat";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "=";
        
        try
        {
            br = new BufferedReader(new FileReader(propFileName));
            while ((line = br.readLine()) != null) 
            {
                String[] colorData = line.split(cvsSplitBy);
                ColorData newColorData = new ColorData(colorData[0], Integer.parseInt(colorData[1].substring(2), 16));
                GeneralData.listOfColors.add(newColorData);
            }
        } 
        catch (Exception e) {	e.printStackTrace();	} 
	}
}