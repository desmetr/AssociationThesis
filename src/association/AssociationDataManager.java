package association;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import association.data.GraphicsInstance;
import association.data.MusicInstance;
import utilities.GeneralData;
import utilities.PropertyManager;

public class AssociationDataManager 
{	
//	private List<EntropyTriplet> graphicsEntropy = new ArrayList<EntropyTriplet>();
//	private List<EntropyTriplet> musicEntropy = new ArrayList<EntropyTriplet>();
	
//	private List<String> graphicsData = new LinkedList<String>();
//	private List<String> musicData = new LinkedList<String>();
	
//	public List<String> getGraphicsData() 	{	return graphicsData;	}
//	public List<String> getMusicData() 		{	return musicData;		}

	public AssociationDataManager()
	{
	}
	
	public String readImagesData() 
	{		
		String inputPath = PropertyManager.getMainFolderGraphics() + PropertyManager.getVectorDataFileGraphics();
		
		// Read data file
	    List<String[]> graphicsInputData = new ArrayList<>();
	    
	    try (BufferedReader br = new BufferedReader(new FileReader(inputPath))) 
	    {
	        String line = "";
	        while ((line = br.readLine()) != null)
	        	graphicsInputData.add(line.split(","));
	        
	        br.close();
	    } 
	    catch (IOException e) { e.printStackTrace();	}
		
	    String resultText = "";
	    for (int i = 1; i < graphicsInputData.size(); i++)
	    {	    
	    	GraphicsInstance currentGraphicsInstance = new GraphicsInstance(graphicsInputData.get(i), i - 1);
	    	if (GeneralData.associationVerbose)
	    		resultText += currentGraphicsInstance.toString();
	    	
	    	GeneralData.graphicsInstances.add(currentGraphicsInstance);
	    }
	    
	    resultText += "Number of images: " + String.valueOf(graphicsInputData.size() - 1) + "\n";
	    return resultText;
	}
	
	public String readMusicData() 
	{		
		String inputPath = PropertyManager.getResultFolderMusic() + PropertyManager.getVectorDataFileMusic();
		
		// Read data file
		List<String[]> musicInputData = new ArrayList<>();
	    
	    try (BufferedReader br = new BufferedReader(new FileReader(inputPath))) 
	    {
	        String line = "";
	        while ((line = br.readLine()) != null)
	        	musicInputData.add(line.split(","));
	        
	        br.close();
	    } 
	    catch (IOException e) { e.printStackTrace();	}
		
        String resultText = "";
        for (int i = 1; i < musicInputData.size(); i++)
        {	     
	        MusicInstance currentMusicInstance = new MusicInstance(musicInputData.get(i), i - 1);
	    	if (GeneralData.associationVerbose)
	    		resultText += currentMusicInstance.toString();
	    	
	    	GeneralData.musicInstances.add(currentMusicInstance);
        }
        
        resultText += "Number of music pieces: " + String.valueOf(musicInputData.size() - 1) + "\n";
        return resultText;
	}
	
	public String rank()
	{
		String resultText = "";
				
		Collections.sort(GeneralData.graphicsInstances);
		
		if (GeneralData.associationVerbose)
		{
			resultText += "\n--- Sorted Graphics:\n";
			for (GraphicsInstance entry : GeneralData.graphicsInstances) 
				resultText += entry.toString() + "\n";
		}
		
		Collections.sort(GeneralData.musicInstances);
		
		if (GeneralData.associationVerbose)
		{
			resultText += "\n--- Sorted Music:\n";
			for (MusicInstance entry : GeneralData.musicInstances) 
				resultText += entry.toString() + "\n";
		}
		
		return resultText + "\n";
	}

	public String makeCSV()
	{
		try 
		{
			PrintWriter printWriter = new PrintWriter(new File(PropertyManager.getAllDataPath()));
			StringBuilder stringBuilder = new StringBuilder();
			
			// Run through graphicsEntropy and append current entry from graphicsData.
			for (int i = 0; i < GeneralData.graphicsInstances.size(); i++)
			{
				String currentName = GeneralData.graphicsInstances.get(i).getName();
				for (Entry<String, Integer> entry : GeneralData.graphicsClasses.entrySet())
				{
					// Get correct label from map
					if (currentName.contains(entry.getKey()))	
					{
						GeneralData.graphicsInstances.get(i).setLabel(entry.getValue());
						stringBuilder.append(GeneralData.graphicsInstances.get(i).getDataAsString() + padding() + "," + String.valueOf(entry.getValue()) + "\n");
					}
				}
			}
			
			int currentClass = 0;
			// Run through musicEntropy and append current entry from musicData.
			for (int i = 0; i < GeneralData.musicInstances.size(); i++)
			{
				String currentName = GeneralData.musicInstances.get(i).getName();
				if (! GeneralData.musicClasses.containsKey(currentName))
					GeneralData.musicClasses.put(currentName, currentClass++);
				
				stringBuilder.append(GeneralData.musicInstances.get(i).getDataAsString() + ",");
				
				if (GeneralData.musicClasses.containsKey(currentName))
				{
					GeneralData.musicInstances.get(i).setLabel(GeneralData.musicClasses.get(currentName));
					stringBuilder.append(GeneralData.musicClasses.get(currentName) + "\n");
				}
			}
			
			printWriter.write(stringBuilder.toString());
			printWriter.close();
	        return "All data writen to file " + PropertyManager.getAllDataPath() + "\n";
		}
		catch (FileNotFoundException e) {	e.printStackTrace();	}
		return null;
	}
	
	public String deleteCSV()
	{
		File file = new File(PropertyManager.getAllDataPath());
		file.delete();
		
		return "Deleted the CSV\n";
	}
	
	private String padding()
	{
		String padding = "";
		for (int i = 0; i < GeneralData.numberOfMusicFeatures - GeneralData.numberOfGraphicFeatures; i++)
			padding += ",0";
		return padding;
	}
}
