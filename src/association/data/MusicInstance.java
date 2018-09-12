package association.data;

import java.util.Arrays;

import utilities.Utilities;

public class MusicInstance extends DataInstance 
{
	public MusicInstance() 
	{
		super();
	}

	public MusicInstance(String[] data, int index) 
	{
		super();
		this.data = Arrays.copyOf(data, data.length - 1);
		this.index = index;
		
		calculateEntropy();
		this.name = data[data.length - 1];
	}
	
	private void calculateEntropy() 
	{		
		int[] inputDataIntArray = Arrays.asList(this.data).stream().mapToInt(Integer::parseInt).toArray();
    
		int[] inputNoteNames = Arrays.copyOfRange(inputDataIntArray, 0, 12); // note names
		int[] inputNoteRoles = Arrays.copyOfRange(inputDataIntArray, 12, 22); // note roles
		int[] inputNoteIntervals = Arrays.copyOfRange(inputDataIntArray, 200, 213); // note intervals
		
		int[] entropyArray = new int[inputNoteNames.length + inputNoteRoles.length + inputNoteIntervals.length];
		System.arraycopy(inputNoteNames, 0, entropyArray, 0, inputNoteNames.length);
		System.arraycopy(inputNoteRoles, 0, entropyArray, inputNoteNames.length, inputNoteRoles.length);
		System.arraycopy(inputNoteIntervals, 0, entropyArray, inputNoteNames.length + inputNoteRoles.length, inputNoteIntervals.length);
		
		this.entropy = Utilities.calculateEntropy(entropyArray);
	}
	
	@Override
	public String toString() 
	{
		return "--- Entropy of " + this.name + ": " + this.entropy + "\n";
	}
}
