package music.model.data;

import music.model.data.MusicalData.NoteName;
import music.model.data.MusicalData.Scale;

/*
 * Class that holds the information of the key of a piece.
 */

public class Key 
{
	public NoteName noteName;
	public Scale scale;
	
	public String toString()
	{
		return "The piece is in " + noteName.name() + " " + scale.name() + "\n"; 
	}
	
	public void reset()
	{
		noteName = null;
		scale = null;
	}
}
