package graphics.model.data;

public class ColorData 
{
	private int hex = 0;
	private String name = "";
	private double percentage = 0.0;
	
	public ColorData(String name, int hex)
	{
		this.name = name;
		this.hex = hex;
	}
	
	public int getHex() 			{	return hex;			}
	public String getName()			{	return name;		}
	public double getPercentage() 	{	return percentage;	}

	public void setHex(int hex) 					{	this.hex = hex;					}
	public void setName(String name) 				{	this.name = name;				}
	public void setPercentage(double percentage) 	{	this.percentage = percentage;	}
}
