package association.data;

import utilities.Utilities;

public class DataInstance implements Comparable<Object>
{
	protected String[] data;
	protected int index;
	protected double entropy;
	protected String name;
	protected int label;
	
	public String[] getData() 	{	return data;	}
	public int getIndex() 		{	return index;	}
	public double getEntropy() 	{	return entropy;	}
	public String getName() 	{	return name;	}
	public int getLabel() 		{	return label;	}
	
	public void setData(String[] data) 		{	this.data = data;		}
	public void setIndex(int index) 		{	this.index = index;		}
	public void setEntropy(double entropy) 	{	this.entropy = entropy;	}
	public void setName(String name) 		{	this.name = name;		}
	public void setLabel(int label) 		{	this.label = label;		}
	
	public DataInstance() 
	{
	}
	
	public String getDataAsString()
	{
		return Utilities.convertStringArrayToString(data, ",");
	}
	
	@Override
	public int compareTo(Object other) 
	{
		return -Double.compare(this.entropy, ((DataInstance) other).getEntropy());
	}
}
