package utilities;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nd4j.linalg.api.ndarray.INDArray;

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
	
//	public static String getGraphicsPath(INDArray label)
//	{
//		int index = 0;
//		
//		double max = -1.0;
//		for (int i = 0; i < label.length(); i++)
//		{
//			if (label.getDouble(i) >= max)
//			{
//				max = label.getDouble(i);
//				index = i;
//			}
//		}
//		
//		switch (index)
//		{
//			case 0:
//				return PropertyManager.getBruegelPath();
//			case 1:
//				return PropertyManager.getMondriaanPath();
//			case 2:
//				return PropertyManager.getBruegelPath();
//			case 3:
//				return PropertyManager.getBruegelPath();
//			default:
//				return null;
//		}
//	}
//	
//	public static String getMusicPath(INDArray label)
//	{
//		
//	}
}
