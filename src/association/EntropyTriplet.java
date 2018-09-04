package association;

public class EntropyTriplet implements Comparable<Object>
{
	private String name;
	private Double entropy;
	private Integer index;
	
	public EntropyTriplet(String name, Double entropy, Integer index)
	{
		this.name = name;
		this.entropy = entropy;
		this.index = index;
	}
	
	public String getName() 	{	return name;	}
	public Double getEntropy() 	{	return entropy;	}
	public Integer getIndex() 	{	return index;	}
	
	public void setName(String name) 		{	this.name = name;		}
	public void setEntropy(Double entropy) 	{	this.entropy = entropy;	}
	public void setIndex(Integer index) 	{	this.index = index;		}

	@Override
	public int compareTo(Object other) 
	{
		return -Double.compare(this.entropy, ((EntropyTriplet) other).getEntropy());
	}
	
	@Override
	public String toString()
	{
		return "[name = " + name + ", entropy = " + entropy + ", index = " + index + "]";
	}
}
