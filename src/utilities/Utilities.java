package utilities;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nd4j.linalg.api.ndarray.INDArray;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;

class MyComparator implements Comparator<Entry<String, Double>> 
{
	public int compare(Entry<String, Double> o1, Entry<String, Double> o2) 
	{
		return -o1.getValue().compareTo(o2.getValue());
	}
}

public class Utilities
{
	public static float[] convertHexToHSV(int hex)
	{
	    float r = (hex & 0xFF0000) >> 16;
	    float g = (hex & 0xFF00) >> 8;
	    float b = (hex & 0xFF);
	     
	    float rTemp = r / 255;
	    float gTemp = g / 255;
	    float bTemp = b / 255;
	    
	    float cMax = Math.max(rTemp, Math.max(gTemp, bTemp));
	    float cMin = Math.min(rTemp, Math.min(gTemp, bTemp));
	    float difference = cMax - cMin;
	    
	    float hue = 0.0f;
	    float saturation = 0.0f;
	    float value = 0.0f;
	    
	    if (difference == 0.0f)
	    	hue = 0.0f;
	    else if (cMax == rTemp)
	    	hue = (float) (1.0471975512f * (((gTemp - bTemp) / difference) % 6));
		else if (cMax == gTemp)
	    	hue = (float) (1.0471975512f * (((bTemp - rTemp) / difference) + 2.0));
		else if (cMax == bTemp)
	    	hue = (float) (1.0471975512f * (((rTemp - gTemp) / difference) + 4.0));
	    
	    if (cMax == 0.0f)
	    	saturation = 0.0f;
	    else
	    	saturation = difference / cMax;
	    
	    value = cMax;
	   
	    float[] tempColor = {hue, saturation, value};
	    return tempColor;
	}
	
	public static int[] convertHexToRGB(int hex)
	{
	    int r = (hex & 0xFF0000) >> 16;
	    int g = (hex & 0xFF00) >> 8;
	    int b = (hex & 0xFF);
	    
	    int[] tempColor = {r, g, b};
	    
	    return tempColor;
	}
	
	public static BufferedImage deepCopy(BufferedImage source)
	{
		ColorModel cm = source.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = source.copyData(null);
		
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	public static String decimalFormat(double value)
	{
		DecimalFormat doubleFormat = new DecimalFormat("#####.#####");
		doubleFormat.setRoundingMode(RoundingMode.HALF_UP);
		
		return String.format("%s", doubleFormat.format(value));
	}
	
    public static int mod(int a, int n) 
    {
    	int ret = a % n;
	    if (ret < 0)
	        ret += n;
	    return ret;
    }
    
    public static HashMap<String, Double> sortByComparator(HashMap<String, Double> unsortMap)
	{
		List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(unsortMap.entrySet());

		Collections.sort(list, new MyComparator());

		HashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		
		for (Entry<String, Double> entry : list) 
			sortedMap.put(entry.getKey(), entry.getValue());
		return sortedMap;
	}
	
	public static String convertStringArrayToString(String[] strArr, String delimiter)
	{
		StringBuilder sb = new StringBuilder();
		for (String str : strArr)
			sb.append(str).append(delimiter);
		return sb.substring(0, sb.length() - 1);
	}
	
	public static <K, V> void printMap(Map<K, V> map)
	{
		for (Entry<K, V> entry : map.entrySet())
			System.out.println(entry.getKey() + " " + entry.getValue());
	}
	
	public static String oneHotToLabel(INDArray oneHot)
	{		
		for (int i = 0; i < oneHot.length(); i++)
		{
			if (oneHot.getInt(i) == 1)
				return GeneralData.PainterLabels.values()[i].toString();
		}
				
		return "";
	}
	
	public static double calculateEntropy(int[] inputData)
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
	
	// https://stackoverflow.com/questions/22265872/calculation-of-entropy-of-an-image-using-java
	public static double calculateEntropy(BufferedImage actualImage)
	{
		List<String> values= new ArrayList<String>();
		int n = 0;
		Map<Integer, Integer> occ = new HashMap<Integer, Integer>();
    
	    for (int i = 0; i < actualImage.getHeight(); i++)
	    {
	    	for (int j = 0; j < actualImage.getWidth(); j++)
	    	{
	    		int pixel = actualImage.getRGB(j, i);
	            int red = (pixel >> 16) & 0xff;
	            int green = (pixel >> 8) & 0xff;
	            int blue = (pixel) & 0xff;
	
	            int d = (int) Math.round(0.2989 * red + 0.5870 * green + 0.1140 * blue);
	            if (!values.contains(String.valueOf(d)))
	            	values.add(String.valueOf(d));
	            if (occ.containsKey(d))
	            	occ.put(d, occ.get(d) + 1);
	            else 
	            	occ.put(d, 1);
	            
	            ++n;
	    	}
	    }
    
	    double e = 0.0;
	    for (Map.Entry<Integer, Integer> entry : occ.entrySet()) 
	    {
	        double p = (double) entry.getValue() / n;
	        e += p * (Math.log(p) / Math.log(2));
	    }
	    return -e;
	}
	
	public static ColumnConstraints getColumnConstraints(HPos hAlignment, int percentage)
	{
		ColumnConstraints cc = new ColumnConstraints();
		
		cc.setHalignment(hAlignment);
		cc.setPercentWidth(percentage);
		
		return cc;
	}
	
	public static RowConstraints getRowConstraints(VPos vAlignment, int percentage)
	{
		RowConstraints rc = new RowConstraints();
		
		rc.setValignment(vAlignment);
		rc.setPercentHeight(percentage);
		
		return rc;
	}

	public static Object getKeyFromValue(Map hm, Object value) 
	{
	    for (Object o : hm.keySet()) 
	    {
	    	if (hm.get(o).equals(value))
	    		return o;
	    }
	    return null;
	}
	
	public static Pair<String, String> getGraphicsPath(INDArray label)
	{
		int index = 0;
		
		double max = -1.0;
		for (int i = 0; i < label.length(); i++)
		{
			if (label.getDouble(i) >= max)
			{
				max = label.getDouble(i);
				index = i;
			}
		}
		
		switch (index)
		{
			case 0:
				return new Pair<String, String>(PropertyManager.getBruegelPath(), "Bruegel");
			case 1:
				return new Pair<String, String>(PropertyManager.getMondriaanPath(), "Mondriaan");
			case 2:
				return new Pair<String, String>(PropertyManager.getPicassoPath(), "Picasso");
			case 3:
				return new Pair<String, String>(PropertyManager.getRubensPath(), "Rubens");
			default:
				return null;
		}
	}
	
	public static Pair<String, String> getMusicPath(INDArray label)
	{
		int index = 0;
		
		double max = -1.0;
		for (int i = 0; i < label.length(); i++)
		{
			if (label.getDouble(i) >= max)
			{
				max = label.getDouble(i);
				index = i;
			}
		}
		
		String name = (String) getKeyFromValue(GeneralData.musicClasses, index);
		if (name.contains("Hey Jude"))
			return new Pair<String, String>(PropertyManager.getHeyJudePath(), name);
		if (name.contains("Autumn_Leaves"))
			return new Pair<String, String>(PropertyManager.getAutumnLeavesPath(), name);
		if (name.contains("Cello_Suite"))
			return new Pair<String, String>(PropertyManager.getCelloSuitePath(), name);
		if (name.contains("For_The_Longest_Time"))
			return new Pair<String, String>(PropertyManager.getForTheLongestTimePath(), name);
		
		return null;
	}
	
	public static String musicPath;
	private static Media media;
    private static MediaPlayer mediaPlayer;
	 
    public static void playAudio()
    {
    	media = new Media(new File("/home/rafael/DataOnderzoeksproject2/music/mp3/Cello_Suite.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    
    public static void pauseAudio()
    {
        mediaPlayer.pause();
    }
    
    public static void executeCommand(String command, boolean verbose)
    {
    	try 
		{
    		Process p = Runtime.getRuntime().exec(command);
			if (verbose)
			{
				String line;
				BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
		        while ((line = is.readLine()) != null)
		              System.out.println(line);
				
		        BufferedReader es = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		        while ((line = es.readLine()) != null)
		              System.out.println(line);
		        
		        System.out.println("-- Process Done --");
			}
			p.destroy();
		} 
    	catch (IOException e) {	e.printStackTrace();	}
    }
}
