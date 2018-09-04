package music.model.data;

import java.util.ArrayList;

import com.google.common.annotations.VisibleForTesting;

import music.model.data.MusicalData.Clef;
import music.model.data.MusicalData.Dynamics;
import music.model.data.MusicalData.NoteInterval;
import music.model.data.MusicalData.NoteLength;
import music.model.data.MusicalData.NoteName;
import music.model.data.MusicalData.ScaleDegree;
import music.model.data.MusicalData.TimeSignatureEnum;
import utilities.Utilities;

public class Piece 
{
	public Key key;
	public ArrayList<Measure> measures = new ArrayList<Measure>();
	public ArrayList<Note> allNotes = new ArrayList<Note>();
	public int tempo;
	public Clef clef;
	public NoteLength beatUnit;
	public int beatsPerMinute;
	public boolean anacrusis = false;
	public double divisions = 0.0;
	public int totalNumberOfNotes = 0;
	
	public String name = "";
		
	public int numberOfTonics = 0;
	public int numberOfSuperTonics = 0;
	public int numberOfMediants = 0;
	public int numberOfSubdominants = 0;
	public int numberOfBlueNotes = 0;
	public int numberOfDominants = 0;
	public int numberOfSubmediants = 0;
	public int numberOfSubtonics = 0;
	public int numberOfLeadingTones = 0;
	public int numberOfNonScaleNotes = 0;
	
	public int numberOfWholeNotes = 0;
	public int numberOfWholeTiedToSixteenthNotes = 0;
	public int numberOfWholeTiedToEighthNotes = 0;
	public int numberOfWholeTiedToQuarterNotes = 0;
	public int numberOfWholeTiedToHalfNotes = 0;
	public int numberOfWholeTiedToWholeNotes = 0;
	
	public int numberOfHalfNotes = 0;
	public int numberOfHalfDottedNotes = 0;
	public int numberOfHalfDoubleDottedNotes = 0;
	public int numberOfHalfTripletNotes = 0;
	public int numberOfHalfTiedToSixteenthNotes = 0;
	public int numberOfHalfTiedToEighthNotes = 0;
	public int numberOfHalfTiedToQuarterNotes = 0;
	public int numberOfHalfTiedToHalfNotes = 0;
	public int numberOfHalfTiedToWholeNotes = 0;
	
	public int numberOfQuarterDottedNotes = 0;
	public int numberOfQuarterDoubleDottedNotes = 0;
	public int numberOfQuarterNotes = 0;
	public int numberOfQuarterTripletNotes = 0;
	public int numberOfQuarterTiedToSixteenthNotes = 0;
	public int numberOfQuarterTiedToEighthNotes = 0;
	public int numberOfQuarterTiedToQuarterNotes = 0;
	public int numberOfQuarterTiedToHalfNotes = 0;
	public int numberOfQuarterTiedToWholeNotes = 0;
	
	public int numberOfEighthDottedNotes = 0;
	public int numberOfEighthDoubleDottedNotes = 0;
	public int numberOfEighthNotes = 0;
	public int numberOfEighthTripletNotes = 0;
	public int numberOfEighthTiedToSixteenthNotes = 0;
	public int numberOfEighthTiedToEighthNotes = 0;
	public int numberOfEighthTiedToQuarterNotes = 0;
	public int numberOfEighthTiedToHalfNotes = 0;
	public int numberOfEighthTiedToWholeNotes = 0;
	
	public int numberOfSixteenthNotes = 0;
	public int numberOfSixteenthDottedNotes = 0;
	public int numberOfSixteenthDoubleDottedNotes = 0;
	public int numberOfSixteenthTripletNotes = 0;
	public int numberOfSixteenthTiedToSixteenthNotes = 0;
	public int numberOfSixteenthTiedToEighthNotes = 0;
	public int numberOfSixteenthTiedToQuarterNotes = 0;
	public int numberOfSixteenthTiedToHalfNotes = 0;
	public int numberOfSixteenthTiedToWholeNotes = 0;
	
	public int numberOfLengthOutOfScopes = 0;
	public int numberOfStartOfTies = 0;
	public int numberOfMiddleOfTies = 0;
	public int numberOfEndOfTies = 0;
	public int totalNumberOfTies = 0;
	public int numberOfRests = 0;
	
	public int numberOfCodas = 0;
	public int numberOfToCodas = 0;
	public int numberOfSegnos = 0;
	public int numberOfDalSegnos = 0;
	
	public int getNumberOfMeasures()				{	return measures.size();									  }
	public String getBeatUnitAsString()				{	return beatUnit.toString();								  }
	public String getClefAsString()					{	return clef.toString(); 								  }
	public TimeSignatureEnum getTimeSignature()		{   return measures.get(0).timeSignature.timeSignatureAsEnum; }
	
	public Piece()
	{
		
	}
	
	public Piece(String fileName)
	{
		name = fileName;
	}
	
	public String toString()
	{
		String returnString = "";
		
		for (Measure measure : measures) 
		{
			returnString += measure.toString();
			returnString += "\n";
		}
		
		return returnString;
	}
	
	public void reset()
	{
		for (Measure measure : measures)
		{
			measure.reset();
		}
		measures.clear();
		
		key.reset();
		tempo = 0;
		clef = null;
		beatUnit = null;
		beatsPerMinute = 0;
	}
	
	public void calculateStatistics(MusicDataManager vectorManager)
	{		
		for (Measure measure : this.measures) 
		{
			ArrayList<Note> currentNotes = measure.notes;
			allNotes.addAll(currentNotes);
			totalNumberOfNotes += measure.getNumberOfNotes();
			
			for (Note note : currentNotes)
			{
				if (note.startOfTie)
					numberOfStartOfTies++;
				
				if (! note.isRest)
				{
					ScaleDegree scaleDegreeNote = calculateScaleDegree(note.name);
					incrementCounterForBarChart(scaleDegreeNote);
					vectorManager.incrementScaleDegreeMap(scaleDegreeNote);
					
					NoteLength noteLength = note.length;
					incrementNoteLengthForBarChart(noteLength);
					vectorManager.incrementNoteLengthMap(noteLength);
					
					NoteName noteName = note.name;
					vectorManager.incrementNoteNameMap(noteName);
				}
				else
					numberOfRests++;
			}
			
			for (Dynamics dynamic : measure.dynamics)
				vectorManager.incrementDynamicsMap(dynamic);
		}
		
		// Calculate intervals, based on all notes in order, not on measures.
		Note previousNote = this.allNotes.get(0);
		NoteInterval interval;
		for (Note currentNote : this.allNotes.subList(1, this.allNotes.size()))
		{
			if (previousNote.isRest || currentNote.isRest)
			{
				previousNote = currentNote;
				continue;
			}
			int indexPreviousNote = MusicalData.notes.indexOf(previousNote.name); 
			int indexCurrentNote = MusicalData.notes.indexOf(currentNote.name);
			
			int intervalLength = Utilities.mod(indexCurrentNote - indexPreviousNote, 12);
			if (intervalLength == 0 && previousNote.pitch == currentNote.pitch)
				interval = MusicalData.NoteInterval.PERFECT_UNISON;
			else if (intervalLength == 0 && (Math.abs(previousNote.pitch - currentNote.pitch) == 1))
				interval = MusicalData.NoteInterval.PERFECT_OCTAVE;
			else
				interval = getNoteInterval(intervalLength);
			
			vectorManager.incrementIntervalMap(interval);
			
			previousNote = currentNote;
		}
		
		totalNumberOfTies = numberOfMiddleOfTies + numberOfEndOfTies;
	}
	
	@VisibleForTesting
	private NoteInterval getNoteInterval(int intervalLength)
	{
		switch (intervalLength)
		{
		case 1:
			return MusicalData.NoteInterval.MINOR_SECOND;
		case 2:
			return MusicalData.NoteInterval.MAJOR_SECOND;
		case 3:
			return MusicalData.NoteInterval.MINOR_THIRD;
		case 4:
			return MusicalData.NoteInterval.MAJOR_THIRD;
		case 5:
			return MusicalData.NoteInterval.PERFECT_FOURTH;
		case 6:
			return MusicalData.NoteInterval.DIMINISHED_FIFTH;
		case 7:
			return MusicalData.NoteInterval.PERFECT_FIFTH;
		case 8:
			return MusicalData.NoteInterval.MINOR_SIXTH;
		case 9:
			return MusicalData.NoteInterval.MAJOR_SIXTH;
		case 10:
			return MusicalData.NoteInterval.MINOR_SEVENTH;
		case 11:
			return MusicalData.NoteInterval.MAJOR_SEVENTH;
		}
		return null;
	}
	
	private ScaleDegree calculateScaleDegree(NoteName note)
	{
		// Calculates the distance between the current note and the key of the piece in the array Utilities.notes. 
		// This distance is used to get the correct scale degree.
		int indexNote = MusicalData.notes.indexOf(note);
		int indexKey = MusicalData.notes.indexOf(this.key.noteName);
		int difference = 0;
		
		if (indexKey < indexNote)
			difference = indexNote - indexKey;
		else
			difference = (12 - (Math.abs(indexNote - indexKey))) % 12;
		
		switch (this.key.scale)
		{
			case MAJOR:
				return MusicalData.majorScale.get(difference);
			case MINOR:
				return MusicalData.minorScale.get(difference);
			case HARMONIC_MINOR:
				return MusicalData.harmonicMinorScale.get(difference);
			case NATURAL_MINOR:
				return MusicalData.naturalMinorScale.get(difference);
			case BLUES:
				return MusicalData.bluesScale.get(difference);
			case PENTATONIC_MAJOR:
				return MusicalData.pentatonicMajorScale.get(difference);
			case PENTATONIC_MINOR:
				return MusicalData.pentatonicMinorScale.get(difference);
			default:
				break;
		}
		
		return null;	
	}
	
	private void incrementCounterForBarChart(ScaleDegree scaleDegree)
	{
		switch (scaleDegree)
		{
			case TONIC:
				numberOfTonics++;
				break;
			case SUPERTONIC:
				numberOfSuperTonics++;
				break;
			case MEDIANT:
				numberOfMediants++;
				break;
			case SUBDOMINANT:
				numberOfSubdominants++;
				break;
			case BLUE_NOTE:
				numberOfBlueNotes++;
				break;
			case DOMINANT:
				numberOfDominants++;
				break;
			case SUBMEDIANT:
				numberOfSubtonics++;
				break;
			case SUBTONIC:
				numberOfSubtonics++;
				break;
			case LEADING_TONE:
				numberOfLeadingTones++;
				break;
			case NON_SCALE_NOTE:
				numberOfNonScaleNotes++;
				break;
			default:
				break;
		}
	}
	
	private void incrementNoteLengthForBarChart(NoteLength noteLength)
	{
		switch (noteLength)
		{
			case WHOLE:
				numberOfWholeNotes++;
				break;
			case WHOLE_TIED_TO_SIXTEENTH:
				numberOfWholeTiedToSixteenthNotes++;
				break;
			case WHOLE_TIED_TO_EIGHTH:
				numberOfWholeTiedToEighthNotes++;
				break;
			case WHOLE_TIED_TO_QUARTER:
				numberOfWholeTiedToQuarterNotes++;
				break;
			case WHOLE_TIED_TO_HALF:
				numberOfWholeTiedToHalfNotes++;
				break;
			case WHOLE_TIED_TO_WHOLE:
				numberOfWholeTiedToWholeNotes++;
				break;
				
			case HALF:
				numberOfHalfNotes++;
				break;
			case HALF_DOTTED:
				numberOfHalfDottedNotes++;
				break;
			case HALF_DOUBLE_DOTTED:
				numberOfHalfDoubleDottedNotes++;
				break;
			case HALF_TRIPLET:
				numberOfHalfTripletNotes++;
				break;
			case HALF_TIED_TO_SIXTEENTH:
				numberOfHalfTiedToSixteenthNotes++;
				break;
			case HALF_TIED_TO_EIGHTH:
				numberOfHalfTiedToEighthNotes++;
				break;
			case HALF_TIED_TO_QUARTER:
				numberOfHalfTiedToQuarterNotes++;
				break;
			case HALF_TIED_TO_HALF:
				numberOfHalfTiedToHalfNotes++;
				break;
			case HALF_TIED_TO_WHOLE:
				numberOfHalfTiedToWholeNotes++;
				break;
				
			case QUARTER:
				numberOfQuarterNotes++;
				break;
			case QUARTER_DOTTED:
				numberOfQuarterDottedNotes++;
				break;
			case QUARTER_DOUBLE_DOTTED:
				numberOfQuarterDoubleDottedNotes++;
				break;
			case QUARTER_TRIPLET:
				numberOfQuarterTripletNotes++;
				break;
			case QUARTER_TIED_TO_SIXTEENTH:
				numberOfQuarterTiedToSixteenthNotes++;
				break;
			case QUARTER_TIED_TO_EIGHTH:
				numberOfQuarterTiedToEighthNotes++;
				break;
			case QUARTER_TIED_TO_QUARTER:
				numberOfQuarterTiedToQuarterNotes++;
				break;
			case QUARTER_TIED_TO_HALF:
				numberOfQuarterTiedToHalfNotes++;
				break;
			case QUARTER_TIED_TO_WHOLE:
				numberOfQuarterTiedToWholeNotes++;
				break;
				
			case EIGHTH:
				numberOfEighthNotes++;
				break;
			case EIGHTH_DOTTED:
				numberOfEighthDottedNotes++;
				break;
			case EIGHTH_DOUBLE_DOTTED:
				numberOfEighthDoubleDottedNotes++;
				break;
			case EIGHTH_TRIPLET:
				numberOfEighthTripletNotes++;
				break;
			case EIGHTH_TIED_TO_SIXTEENTH:
				numberOfEighthTiedToSixteenthNotes++;
				break;
			case EIGHTH_TIED_TO_EIGHTH:
				numberOfEighthTiedToEighthNotes++;
				break;
			case EIGHTH_TIED_TO_QUARTER:
				numberOfEighthTiedToQuarterNotes++;
				break;
			case EIGHTH_TIED_TO_HALF:
				numberOfEighthTiedToHalfNotes++;
				break;
			case EIGHTH_TIED_TO_WHOLE:
				numberOfEighthTiedToWholeNotes++;
				break;
				
			case SIXTEENTH:
				numberOfSixteenthNotes++;
				break;
			case SIXTEENTH_DOTTED:
				numberOfSixteenthDottedNotes++;
				break;
			case SIXTEENTH_DOUBLE_DOTTED:
				numberOfSixteenthDoubleDottedNotes++;
				break;
			case SIXTEENTH_TRIPLET:
				numberOfSixteenthTripletNotes++;
				break;
			case SIXTEENTH_TIED_TO_SIXTEENTH:
				numberOfSixteenthTiedToSixteenthNotes++;
				break;
			case SIXTEENTH_TIED_TO_EIGHTH:
				numberOfSixteenthTiedToEighthNotes++;
				break;
			case SIXTEENTH_TIED_TO_QUARTER:
				numberOfSixteenthTiedToQuarterNotes++;
				break;
			case SIXTEENTH_TIED_TO_HALF:
				numberOfSixteenthTiedToHalfNotes++;
				break;
			case SIXTEENTH_TIED_TO_WHOLE:
				numberOfSixteenthTiedToWholeNotes++;
				break;
				
			case MIDDLE_OF_TIE:
				numberOfMiddleOfTies++;
				break;
			case END_OF_TIE:
				numberOfEndOfTies++;
				break;
			case LENGTH_OUT_OF_SCOPE:
				numberOfLengthOutOfScopes++;
				break;
				
			default:
				break;
		}
	}
}
