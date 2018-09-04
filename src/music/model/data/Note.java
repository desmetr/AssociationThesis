package music.model.data;

import music.model.data.MusicalData.NoteLength;
import music.model.data.MusicalData.NoteName;

/*
 * Class that holds all the information of a single note.
 */

public class Note 
{
	public NoteName name = null;
	public int pitch;
	public NoteLength length = null;
	public boolean isRest = false;
	public double duration;
	public boolean startOfTie = false;
	public boolean endOfTie = false;
	public boolean isChord = false;
	public boolean hasChord = false;
	public int chordNumber = 0;
	public int measureNumber;
	public int noteIndex;	// Represents the index of the note in its measure.
	public int staff = 0;
	
	public String defaultX = "";	// Used to determine if note is part of chord.

	public String getNameAsString()			
	{	
		if (name != null)
			return name.name();
		else
			return "";
	}

	public String toString()
	{
		String returnString = "Part of Chord: " + String.valueOf(isChord);
			
		if (isChord)
			returnString += "\tChord #" + String.valueOf(chordNumber) + "\n";
		else
			returnString += "\n";
		
		if (isRest)
			returnString = "Rest, length: " + String.valueOf(length) + "\n";
		else if (endOfTie)
			returnString += "\t\t\tNote " + name.name() + String.valueOf(pitch) + ", end note of tie." +  String.valueOf(duration) +"\n";
		else
			returnString += "\t\t\tNote " + name.name() + String.valueOf(pitch) + ", length: " + String.valueOf(length) + ", duration: " + String.valueOf(duration) + "\n";
		
		return returnString + "\n";
	}
	
	public void reset()
	{
		name = null;
		pitch = 0;
		length = null;
		isRest = false;
		endOfTie = false;
	}
}
