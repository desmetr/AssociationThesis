package association.data;

import java.util.Arrays;

public class GraphicsInstance extends DataInstance 
{
	public GraphicsInstance() 
	{
		super();
	}
	
	public GraphicsInstance(String[] data, int index) 
	{
		super();
		this.data = Arrays.copyOf(data, data.length - 1);
		this.index = index;
		
		this.entropy = Double.valueOf(this.data[19]);
		this.name = data[data.length - 1];
	}
	
	@Override
	public String toString() 
	{
		return "--- Entropy of " + this.name + ": " + this.entropy + "\n";
	}
}
