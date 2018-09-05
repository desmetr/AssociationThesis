package test.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import utilities.PropertyManager;
import utilities.Utilities;

class UtilitiesTestCase 
{
	NumberFormat formatter = new DecimalFormat("#0.000000");
	
	public UtilitiesTestCase() 
	{
		new PropertyManager().getAllValues();
	}
	
	@Test
	public void convertHexToHSV_FF0000() 
	{
		int hex = Integer.parseInt("FF0000", 16);
		float[] result = Utilities.convertHexToHSV(hex);
		assertEquals(0, result[0]);
		assertEquals(1, result[1]);
		assertEquals(1, result[2]);
	}
	
	@Test
	public void convertHexToHSV_24786F() 
	{
		int hex = Integer.parseInt("24786F", 16);
		float[] result = Utilities.convertHexToHSV(hex);
		assertEquals(3.029393, Double.parseDouble(formatter.format(result[0])));
		assertEquals(0.7, Double.parseDouble(formatter.format(result[1])));
		assertEquals(0.470588, Double.parseDouble(formatter.format(result[2])));
	}
	
	@Test
	public void convertHexToRGB_FF0000()
	{
		int hex = Integer.parseInt("FF0000", 16);
		int[] result = Utilities.convertHexToRGB(hex);
		assertEquals(255, result[0]);
		assertEquals(0, result[1]);
		assertEquals(0, result[2]);
	}
	
	@Test
	public void convertHexToRGB_24786F()
	{
		int hex = Integer.parseInt("24786F", 16);
		int[] result = Utilities.convertHexToRGB(hex);
		assertEquals(36, result[0]);
		assertEquals(120, result[1]);
		assertEquals(111, result[2]);
	}
	
	@Test
	public void decimalFormat()
	{
		double value = 2.3568752542;
		assertEquals(2.35688, Double.parseDouble(Utilities.decimalFormat(value)));
		
		value = 2.00005;
		assertEquals(2.00005, Double.parseDouble(Utilities.decimalFormat(value)));
	}
	
	@Test
	public void mod()
	{
		int a = 5;
		int n = 3;
		assertEquals(2, Utilities.mod(a, n));
		
		a = 5;
		n = 9;
		assertEquals(5, Utilities.mod(a, n));
	
		a = -5;
		n = 3;
		assertEquals(1, Utilities.mod(a, n));
	}
	
	@Test
	public void convertStringArrayToString()
	{
		String[] test = {"a", "b", "c"};
		assertEquals("a,b,c", Utilities.convertStringArrayToString(test, ","));
	}
}
