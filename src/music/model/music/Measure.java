package music.model.music;

import java.util.ArrayList;

import music.model.music.MusicalData.Dynamics;

/*
 * Class that holds the information of a measure.
 */

public class Measure 
{
	public ArrayList<Note> notes = new ArrayList<Note>();
	public ArrayList<Dynamics> dynamics = new ArrayList<Dynamics>();
	public TimeSignature timeSignature;
	public boolean anacrusis = false;
	public int index = 0;
	public int numberOfStaffs = 0;
	
	public Measure(int index)
	{
		this.index = index;
	}

	public int getNumberOfNotes()				{	return notes.size();				}
	public Note getLastNote()					{	return notes.get(notes.size() - 1);	}
	
	public String toString()
	{
		String returnString = "";
		
		returnString += "Measure " + String.valueOf(index) + ":\n";
		returnString += "\tAnacrusis: " + String.valueOf(anacrusis) + "\n";
		returnString += "\t" + timeSignature.toString();
		returnString += "\tNotes:\n";
		
		for (int i = 1; i <= numberOfStaffs; i++)
		{
			returnString += "\t\tStaff " + String.valueOf(i) + "\n";
			for (Note note : notes) 
			{
				if (note.staff == i)
					returnString += "\t\t\t" + note.toString();
			}
			
			returnString += "\t\tDynamics: " + dynamics + "\n";
		}
		
		return returnString;
	}
	
	public void reset()
	{
		for (Note note : notes)
		{
			note.reset();
		}
		notes.clear();
		
		timeSignature.reset();
	}
}
