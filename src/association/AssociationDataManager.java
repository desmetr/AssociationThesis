package association;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import utilities.GeneralData;
import utilities.PropertyManager;
import utilities.Utilities;

public class AssociationDataManager 
{	
	private List<EntropyTriplet> graphicsEntropy = new ArrayList<EntropyTriplet>();
	private List<EntropyTriplet> musicEntropy = new ArrayList<EntropyTriplet>();
	
	private List<String> graphicsData = new LinkedList<String>();
	private List<String> musicData = new LinkedList<String>();
	
	public List<String> getGraphicsData() 	{	return graphicsData;	}
	public List<String> getMusicData() 		{	return musicData;		}

	public AssociationDataManager()
	{
	}
	
	public String processImagesData() 
	{		
		// Specify the root directory.
		String baseDir = PropertyManager.getMainFolderGraphics();
		String inputPath = baseDir + "vectorsGraphics.csv";
		
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
		
	    String currentImageName;
	    Double currentImageEntropy;
	    String resultText = "";
	    for (int i = 1; i < graphicsInputData.size(); i++)
	    {
	    	String[] inputDataStringArray = graphicsInputData.get(i);
	    	currentImageName = inputDataStringArray[168];
	    	String[] inputDataStringArrayNoName = Arrays.copyOf(inputDataStringArray, inputDataStringArray.length - 1);
	    	currentImageEntropy = Double.valueOf(inputDataStringArray[19]);
	    	
	    	if (GeneralData.verbose)
	    		resultText += "--- Entropy of " + currentImageName + ": " + currentImageEntropy + "\n";
	    
	    	graphicsEntropy.add(new EntropyTriplet(currentImageName, currentImageEntropy, i - 1));	// Index - 1 because we ignore the first line with labels.
	    	graphicsData.add(Utilities.convertStringArrayToString(inputDataStringArrayNoName, ","));
	    }
	    
	    resultText = "Number of images: " + String.valueOf(graphicsInputData.size());
	    resultText += "\n";
	    return resultText;
	}
	
	public String processMusicData() 
	{		
		// Specify the root directory.
		String baseDir = PropertyManager.getResultFolderMusic();
		String inputPath = baseDir + "vectorsMusic.csv";
		
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
		
        String currentSongName;
        Double currentSongEntropy;
        String resultText = "";
        for (int i = 1; i < musicInputData.size(); i++)
        {
	        String[] inputDataStringArray = musicInputData.get(i);
	        currentSongName = inputDataStringArray[224];
	     
	        String[] inputDataStringArrayNoName = Arrays.copyOf(inputDataStringArray, inputDataStringArray.length - 1);
	        int[] inputDataIntArray = Arrays.asList(inputDataStringArrayNoName).stream().mapToInt(Integer::parseInt).toArray();
	        
	        int[] inputNoteNames = Arrays.copyOfRange(inputDataIntArray, 0, 12); // note names
	        int[] inputNoteRoles = Arrays.copyOfRange(inputDataIntArray, 12, 22); // note roles
	        int[] inputNoteIntervals = Arrays.copyOfRange(inputDataIntArray, 200, 213); // note intervals
	        
	        int[] entropyArray = new int[inputNoteNames.length + inputNoteRoles.length + inputNoteIntervals.length];
	        System.arraycopy(inputNoteNames, 0, entropyArray, 0, inputNoteNames.length);
	        System.arraycopy(inputNoteRoles, 0, entropyArray, inputNoteNames.length, inputNoteRoles.length);
	        System.arraycopy(inputNoteIntervals, 0, entropyArray, inputNoteNames.length + inputNoteRoles.length, inputNoteIntervals.length);
	        
	        currentSongEntropy = calculateEntropy(entropyArray);
	        if (GeneralData.verbose)
	        	resultText += "--- Entropy of " + currentSongName + ": " + currentSongEntropy + "\n";
	        
	        musicEntropy.add(new EntropyTriplet(currentSongName, currentSongEntropy, i - 1));	// Index - 1 because we ignore the first line with labels.
	        musicData.add(Utilities.convertStringArrayToString(inputDataStringArrayNoName, ","));
        }
        
        resultText = "Number of music pieces: " + String.valueOf(musicInputData.size());
        resultText += "\n";
        return resultText;
	}
	
	@SuppressWarnings("unchecked")
	public String rank()
	{
		String resultText = "";
		
		Collections.sort(musicEntropy);
		
		if (GeneralData.verbose)
		{
			resultText += "\n--- Sorted Music:\n";
			for (EntropyTriplet entry : musicEntropy) 
				resultText += entry.toString() + "\n";
		}
		
		Collections.sort(graphicsEntropy);
		
		if (GeneralData.verbose)
		{
			resultText += "\n--- Sorted Graphics:\n";
			for (EntropyTriplet entry : graphicsEntropy) 
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
	        
			HashMap<String, Integer> graphicsClasses = new HashMap<>();
			graphicsClasses.put("bruegel", 0);
			graphicsClasses.put("mondriaan", 1);
			graphicsClasses.put("picasso", 2);
			graphicsClasses.put("rubens", 3);
			
			// Run through graphicsEntropy and append current entry from graphicsData.
			for (int i = 0; i < graphicsEntropy.size(); i++)
			{
				String currentName = graphicsEntropy.get(i).getName();
				int currentIndexGraphics = graphicsEntropy.get(i).getIndex();
				
				for (Entry<String, Integer> entry : graphicsClasses.entrySet())
				{
					if (currentName.contains(entry.getKey()))	
						stringBuilder.append(graphicsData.get(currentIndexGraphics) + padding() + "," + String.valueOf(entry.getValue()) + "\n");
				}
			}
			
			HashMap<String, Integer> musicClasses = new HashMap<>();
			int currentClass = 0;
			// Run through musicEntropy and append current entry from musicData.
			for (int i = 0; i < musicEntropy.size(); i++)
			{
				String currentName = musicEntropy.get(i).getName();
				if (! musicClasses.containsKey(currentName))
					musicClasses.put(currentName, currentClass++);
				
				int currentIndexMusic = musicEntropy.get(i).getIndex();
				stringBuilder.append(musicData.get(currentIndexMusic) + ",");
				
				if (musicClasses.containsKey(currentName))
					stringBuilder.append(musicClasses.get(currentName) + "\n");
			}
			
			printWriter.write(stringBuilder.toString());
			printWriter.close();
	        return "All data writen to file " + PropertyManager.getAllDataPath() + "\n";
		}
		catch (FileNotFoundException e) {	e.printStackTrace();	}
		return null;
	}
	
	public void deleteCSV()
	{
		File file = new File(PropertyManager.getAllDataPath());
		file.delete();
	}
	
	private String padding()
	{
		String padding = "";
		for (int i = 0; i < GeneralData.numberOfMusicFeatures - GeneralData.numberOfGraphicFeatures; i++)
			padding += ",0";
		return padding;
	}
	
	private double calculateEntropy(int[] inputData)
	{
		HashMap<Integer, Integer> counts = new HashMap<Integer, Integer>();
		for (int x : inputData)
		{
			if (counts.get(x) != null)
				counts.put(x, counts.get(x) + 1);
			else
				counts.put(x, 1);
		}
		
		double entropy = 0.0;
		double i = 0.0;
		Integer n = inputData.length;
		for (int x : inputData)
		{
			i = (double) counts.get(x) / n;
			entropy += (double) i * Math.log(i);
		}
		
		return -entropy;
	}
}
