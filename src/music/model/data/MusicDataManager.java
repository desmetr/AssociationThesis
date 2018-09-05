package music.model.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import music.model.data.MusicalData.Dynamics;
import music.model.data.MusicalData.NoteInterval;
import music.model.data.MusicalData.NoteLength;
import music.model.data.MusicalData.NoteName;
import music.model.data.MusicalData.ScaleDegree;

import java.util.Set;

import utilities.GeneralData;
import utilities.PropertyManager;

public class MusicDataManager 
{
	private LinkedHashMap<MusicalData.NoteName, Integer> noteNameCounts = new LinkedHashMap<MusicalData.NoteName, Integer>();
	private LinkedHashMap<MusicalData.ScaleDegree, Integer> scaleDegreeCounts = new LinkedHashMap<MusicalData.ScaleDegree, Integer>();
	private LinkedHashMap<MusicalData.NoteLength, Integer> noteLengthCounts = new LinkedHashMap<MusicalData.NoteLength, Integer>();
	private LinkedHashMap<MusicalData.Dynamics, Integer> dynamicsCounts = new LinkedHashMap<MusicalData.Dynamics, Integer>();
	private LinkedHashMap<MusicalData.NoteInterval, Integer> intervalCounts = new LinkedHashMap<MusicalData.NoteInterval, Integer>();
	
	private Piece piece;
	
	public LinkedHashMap<MusicalData.NoteName, Integer> getNoteNameCounts() 		{	return noteNameCounts;		}
	public LinkedHashMap<MusicalData.ScaleDegree, Integer> getScaleDegreeCounts() 	{	return scaleDegreeCounts;	}
	public LinkedHashMap<MusicalData.NoteLength, Integer> getNoteLengthCounts()	 	{	return noteLengthCounts;	}
	public LinkedHashMap<MusicalData.Dynamics, Integer> getDynamicsCounts() 		{	return dynamicsCounts;		}
	public LinkedHashMap<MusicalData.NoteInterval, Integer> getIntervalCounts() 	{	return intervalCounts;		}

	public Piece getPiece() {	return piece;	}

	public MusicDataManager(Piece piece)
	{
		this.piece = piece;
	}
	
	public MusicDataManager()
	{
		for (NoteName noteName: MusicalData.NoteName.values())
			noteNameCounts.put(noteName, 0);
		
		for (ScaleDegree scaleDegree : MusicalData.ScaleDegree.values())
			scaleDegreeCounts.put(scaleDegree, 0);
		
		for (NoteLength noteLength : MusicalData.NoteLength.values())
			noteLengthCounts.put(noteLength, 0);
		
		for (Dynamics dynamic : MusicalData.Dynamics.values())
			dynamicsCounts.put(dynamic, 0);
		
		for (NoteInterval interval : MusicalData.NoteInterval.values())
			intervalCounts.put(interval, 0);
	}
	
	public void reset()
	{
		noteNameCounts.clear();
		for (NoteName noteName: MusicalData.NoteName.values())
			noteNameCounts.put(noteName, 0);
		
		scaleDegreeCounts.clear();
		for (ScaleDegree scaleDegree : MusicalData.ScaleDegree.values())
			scaleDegreeCounts.put(scaleDegree, 0);
		
		noteLengthCounts.clear();
		for (NoteLength noteLength : MusicalData.NoteLength.values())
			noteLengthCounts.put(noteLength, 0);
		
		dynamicsCounts.clear();
		for (Dynamics dynamic : MusicalData.Dynamics.values())
			dynamicsCounts.put(dynamic, 0);
		
		intervalCounts.clear();
		for (NoteInterval interval : MusicalData.NoteInterval.values())
			intervalCounts.put(interval, 0);
	}
	
	public void setPiece(Piece piece)	{	this.piece = piece;	}
	
	public void incrementNoteNameMap(MusicalData.NoteName key)
	{
		if (!noteNameCounts.containsKey(key))
			noteNameCounts.put(key, 1);
		else
		{
			int currentCount = noteNameCounts.get(key); 
			noteNameCounts.put(key, currentCount + 1);
		}
	}
	
	public void incrementScaleDegreeMap(MusicalData.ScaleDegree key)
	{
		if (!scaleDegreeCounts.containsKey(key))
			scaleDegreeCounts.put(key, 1);
		else
		{
			int currentCount = scaleDegreeCounts.get(key); 
			scaleDegreeCounts.put(key, currentCount + 1);
		}
	}
	
	public void incrementNoteLengthMap(MusicalData.NoteLength key)
	{
		if (!noteLengthCounts.containsKey(key))
			noteLengthCounts.put(key, 1);
		else
		{
			int currentCount = noteLengthCounts.get(key); 
			noteLengthCounts.put(key, currentCount + 1);
		}
	}
	
	public void incrementDynamicsMap(MusicalData.Dynamics key)
	{
		if (!dynamicsCounts.containsKey(key))
			dynamicsCounts.put(key, 1);
		else
		{
			int currentCount = dynamicsCounts.get(key); 
			dynamicsCounts.put(key, currentCount + 1);
		}
	}
	
	public void incrementIntervalMap(MusicalData.NoteInterval key)
	{
		if (!intervalCounts.containsKey(key))
			intervalCounts.put(key, 1);
		else
		{
			int currentCount = intervalCounts.get(key); 
			intervalCounts.put(key, currentCount + 1);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void writeLabelsToFile()
	{
		StringBuilder vectorStringBuilder = new StringBuilder();
		
		Set<?> noteNameSet = noteNameCounts.entrySet();
		Iterator<?> noteNameIterator = noteNameSet.iterator();
		
		Set<?> noteLengthSet = noteLengthCounts.entrySet();
		Iterator<?> noteLengthIterator = noteLengthSet.iterator();
		
		Set<?> scaleDegreeSet = scaleDegreeCounts.entrySet();
		Iterator<?> scaleDegreeIterator = scaleDegreeSet.iterator();
		
		Set<?> dynamicsSet = dynamicsCounts.entrySet();
		Iterator<?> dynamicsIterator = dynamicsSet .iterator();
		
		Set<?> intervalSet = intervalCounts.entrySet();
		Iterator<?> intervalIterator = intervalSet .iterator();
		
		// Write labels
		// NOTE NAMES
		while (noteNameIterator.hasNext()) 
		{
	         Map.Entry<MusicalData.NoteName, Integer> me = (Entry<NoteName, Integer>) noteNameIterator.next();
	         vectorStringBuilder.append(me.getKey().name().toUpperCase());
	         vectorStringBuilder.append(GeneralData.separator);
		}
		
		// SCALE DEGREES
		while (scaleDegreeIterator.hasNext()) 
		{
	         Map.Entry<MusicalData.ScaleDegree, Integer> me = (Map.Entry<MusicalData.ScaleDegree, Integer>) scaleDegreeIterator.next();
	         vectorStringBuilder.append(me.getKey().name().toUpperCase());
	         vectorStringBuilder.append(GeneralData.separator);
		}
		
		// NOTE LENGTHS
		while (noteLengthIterator.hasNext()) 
		{
	         Map.Entry<MusicalData.NoteLength, Integer> me = (Map.Entry<MusicalData.NoteLength, Integer>) noteLengthIterator.next();
	         vectorStringBuilder.append(me.getKey().name().toUpperCase());
	         vectorStringBuilder.append(GeneralData.separator);
		}
		
		// DYNAMICS
		while (dynamicsIterator.hasNext()) 
		{
	         Map.Entry<MusicalData.Dynamics, Integer> me = (Map.Entry<MusicalData.Dynamics, Integer>) dynamicsIterator.next();
	         vectorStringBuilder.append(me.getKey().name().toUpperCase());
	         vectorStringBuilder.append(GeneralData.separator);
		}
		
		// INTERVALS
		while (intervalIterator.hasNext()) 
		{
	         Map.Entry<MusicalData.NoteInterval, Integer> me = (Map.Entry<MusicalData.NoteInterval, Integer>) intervalIterator.next();
	         vectorStringBuilder.append(me.getKey().name().toUpperCase());
	         vectorStringBuilder.append(GeneralData.separator);
		}
		
		vectorStringBuilder.append("KEY");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("TEMPO");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("CLEF");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("TIME_SIGNATURE");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("ANACRUSIS");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("TOTAL_NUMBER_OF_NOTES");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("NUMBER_OF_MEASURES");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("NUMBER_OF_CODAS");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("NUMBER_OF_TO_CODAS");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("NUMBER_OF_SEGNOS");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("NUMBER_OF_DAL_SEGNOS");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("NAME");
		
		vectorStringBuilder.append("\n");
		
		try 
		{
			Files.write(Paths.get(PropertyManager.getResultFolderMusic() + "vectorsMusic.csv"), vectorStringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
		} 
		catch (IOException e) {	e.printStackTrace();	}
	}
	
	@SuppressWarnings("unchecked")
	public void writeValuesToFile()
	{
		this.piece.calculateStatistics(this);
		
		StringBuilder vectorStringBuilder = new StringBuilder();
		
		Set<?> noteNameSet = noteNameCounts.entrySet();
		Iterator<?> noteNameIterator = noteNameSet.iterator();
		
		Set<?> noteLengthSet = noteLengthCounts.entrySet();
		Iterator<?> noteLengthIterator = noteLengthSet.iterator();
		
		Set<?> scaleDegreeSet = scaleDegreeCounts.entrySet();
		Iterator<?> scaleDegreeIterator = scaleDegreeSet.iterator();
		
		Set<?> dynamicsSet = dynamicsCounts.entrySet();
		Iterator<?> dynamicsIterator = dynamicsSet .iterator();
		
		Set<?> intervalSet = intervalCounts.entrySet();
		Iterator<?> intervalIterator = intervalSet .iterator();
					
		// NOTE NAMES
		while (noteNameIterator.hasNext()) 
		{
	         Map.Entry<MusicalData.NoteName, Integer> me = (Map.Entry<MusicalData.NoteName, Integer>) noteNameIterator.next();
	         vectorStringBuilder.append(String.valueOf(me.getValue()));
	         vectorStringBuilder.append(GeneralData.separator);
		}
		
		// SCALE DEGREES
		while (scaleDegreeIterator.hasNext()) 
		{
	         Map.Entry<MusicalData.ScaleDegree, Integer> me = (Map.Entry<MusicalData.ScaleDegree, Integer>) scaleDegreeIterator.next();
	         vectorStringBuilder.append(String.valueOf(me.getValue()));
	         vectorStringBuilder.append(GeneralData.separator);
		}
		
		// NOTE LENGTHS
		while (noteLengthIterator.hasNext()) 
		{
	         Map.Entry<MusicalData.NoteLength, Integer> me = (Map.Entry<MusicalData.NoteLength, Integer>) noteLengthIterator.next();
	         vectorStringBuilder.append(String.valueOf(me.getValue()));
	         vectorStringBuilder.append(GeneralData.separator);
		}
		
		// DYNAMICS
		while (dynamicsIterator.hasNext()) 
		{
	         Map.Entry<MusicalData.Dynamics, Integer> me = (Map.Entry<MusicalData.Dynamics, Integer>) dynamicsIterator.next();
	         vectorStringBuilder.append(String.valueOf(me.getValue()));
	         vectorStringBuilder.append(GeneralData.separator);
		}
		
		// INTERVALS 
		for (Map.Entry<MusicalData.NoteInterval, Integer> entry : intervalCounts.entrySet()) 
		{
			// The intervals in a tie are counted as well because we need to know the interval between the last note of the tie and the next note.
			// But the interval between the notes of the tie are not interesting (always a perfect unison) so we ignore these.
			if (entry.getKey().equals(NoteInterval.PERFECT_UNISON))
				intervalCounts.put(NoteInterval.PERFECT_UNISON, intervalCounts.get(NoteInterval.PERFECT_UNISON) - piece.numberOfStartOfTies);
		}
		while (intervalIterator.hasNext()) 
		{
	         Map.Entry<MusicalData.NoteInterval, Integer> me = (Map.Entry<MusicalData.NoteInterval, Integer>) intervalIterator.next();	        	 
	         vectorStringBuilder.append(String.valueOf(me.getValue()));
	         vectorStringBuilder.append(GeneralData.separator);
		}
		
		// MISCELLANEOUS
		vectorStringBuilder.append(piece.key.noteName.ordinal());
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(piece.tempo);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(piece.clef.ordinal());
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(piece.getTimeSignature().ordinal());
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(piece.anacrusis ? 1 : 0);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(piece.totalNumberOfNotes);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(piece.measures.size());
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(piece.numberOfCodas);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(piece.numberOfToCodas);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(piece.numberOfSegnos);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(piece.numberOfDalSegnos);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(piece.name);
		
		vectorStringBuilder.append("\n");

		try 
		{
			Files.write(Paths.get(PropertyManager.getResultFolderMusic() + "vectorsMusic.csv"), vectorStringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
		} 
		catch (IOException e) {	e.printStackTrace();	}	
	}
}
