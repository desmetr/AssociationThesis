package test.music;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.Test;

import music.model.data.MusicDataManager;
import music.model.data.MusicalData;
import music.model.data.Piece;
import music.model.data.MusicalData.Dynamics;
import music.model.data.MusicalData.NoteInterval;
import music.model.data.MusicalData.NoteLength;
import music.model.data.MusicalData.NoteName;
import music.model.data.MusicalData.ScaleDegree;
import music.model.parser.MusicXMLParser;
import utilities.GeneralData;
import utilities.PropertyManager;

class MusicDataManagerTestCase 
{	
	private String testXMLDestinationPath;
	
	public MusicDataManagerTestCase() 
	{
		new PropertyManager().getPropertyValues();
		testXMLDestinationPath = PropertyManager.getDestinationFolderMusic();
	}

	@Test
	public void dynamicsCounts()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Hey Jude.xml");
			Piece piece = parser.parse(file);
			
			MusicDataManager musicDataManager = new MusicDataManager(piece);
			piece.calculateStatistics(musicDataManager);
		
			assertEquals(1, (int) musicDataManager.getDynamicsCounts().get(Dynamics.FFF));
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void noteIntervalsCounts()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 19.xml");
			Piece piece = parser.parse(file);
			
			MusicDataManager musicDataManager = new MusicDataManager(piece);
			piece.calculateStatistics(musicDataManager);
		
			assertEquals(1, (int) musicDataManager.getIntervalCounts().get(NoteInterval.MINOR_THIRD));
			assertEquals(2, (int) musicDataManager.getIntervalCounts().get(NoteInterval.MAJOR_SIXTH));
			assertEquals(2, (int) musicDataManager.getIntervalCounts().get(NoteInterval.MINOR_SEVENTH));
			assertEquals(1, (int) musicDataManager.getIntervalCounts().get(NoteInterval.MINOR_SIXTH));
			assertEquals(1, (int) musicDataManager.getIntervalCounts().get(NoteInterval.PERFECT_FIFTH));
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
		
	@Test
	public void noteNameCounts()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 19.xml");
			Piece piece = parser.parse(file);
			
			MusicDataManager musicDataManager = new MusicDataManager(piece);
			piece.calculateStatistics(musicDataManager);
		
			assertEquals(1, (int) musicDataManager.getNoteNameCounts().get(NoteName.C));
			assertEquals(2, (int) musicDataManager.getNoteNameCounts().get(NoteName.D));
			assertEquals(2, (int) musicDataManager.getNoteNameCounts().get(NoteName.E));
			assertEquals(2, (int) musicDataManager.getNoteNameCounts().get(NoteName.G));
			assertEquals(1, (int) musicDataManager.getNoteNameCounts().get(NoteName.B));
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
		
	@Test
	public void scaleDegreeCounts()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 19.xml");
			Piece piece = parser.parse(file);
			
			MusicDataManager musicDataManager = new MusicDataManager(piece);
			piece.calculateStatistics(musicDataManager);
		
			assertEquals(1, (int) musicDataManager.getScaleDegreeCounts().get(ScaleDegree.TONIC));
			assertEquals(2, (int) musicDataManager.getScaleDegreeCounts().get(ScaleDegree.SUPERTONIC));
			assertEquals(2, (int) musicDataManager.getScaleDegreeCounts().get(ScaleDegree.MEDIANT));
			assertEquals(2, (int) musicDataManager.getScaleDegreeCounts().get(ScaleDegree.DOMINANT));
			assertEquals(1, (int) musicDataManager.getScaleDegreeCounts().get(ScaleDegree.LEADING_TONE));
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void noteLengthCounts()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 19.xml");
			Piece piece = parser.parse(file);
			
			MusicDataManager musicDataManager = new MusicDataManager(piece);
			piece.calculateStatistics(musicDataManager);
		
			assertEquals(7, (int) musicDataManager.getNoteLengthCounts().get(NoteLength.QUARTER));
			assertEquals(1, (int) musicDataManager.getNoteLengthCounts().get(NoteLength.WHOLE));
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void incrementNoteNameMap()
	{
		MusicDataManager musicDataManager = new MusicDataManager();
		assertEquals(0, (int) musicDataManager.getNoteNameCounts().get(NoteName.C));
		assertEquals(0, (int) musicDataManager.getNoteNameCounts().get(NoteName.D));
		
		musicDataManager.incrementNoteNameMap(NoteName.C);
		assertEquals(1, (int) musicDataManager.getNoteNameCounts().get(NoteName.C));
		assertEquals(0, (int) musicDataManager.getNoteNameCounts().get(NoteName.D));
	}

	@Test
	public void incrementScaleDegreeMap()
	{
		MusicDataManager musicDataManager = new MusicDataManager();
		assertEquals(0, (int) musicDataManager.getScaleDegreeCounts().get(ScaleDegree.TONIC));
		assertEquals(0, (int) musicDataManager.getScaleDegreeCounts().get(ScaleDegree.DOMINANT));
		
		musicDataManager.incrementScaleDegreeMap(ScaleDegree.TONIC);
		assertEquals(1, (int) musicDataManager.getScaleDegreeCounts().get(ScaleDegree.TONIC));
		assertEquals(0, (int) musicDataManager.getScaleDegreeCounts().get(ScaleDegree.DOMINANT));
	}

	@Test
	public void incrementNoteLengthMap()
	{
		MusicDataManager musicDataManager = new MusicDataManager();
		assertEquals(0, (int) musicDataManager.getNoteLengthCounts().get(NoteLength.EIGHTH));
		assertEquals(0, (int) musicDataManager.getNoteLengthCounts().get(NoteLength.QUARTER));
		
		musicDataManager.incrementNoteLengthMap(NoteLength.EIGHTH);
		assertEquals(1, (int) musicDataManager.getNoteLengthCounts().get(NoteLength.EIGHTH));
		assertEquals(0, (int) musicDataManager.getNoteLengthCounts().get(NoteLength.QUARTER));
	}

	@Test
	public void incrementDynamicsMap()
	{
		MusicDataManager musicDataManager = new MusicDataManager();
		assertEquals(0, (int) musicDataManager.getDynamicsCounts().get(Dynamics.MF));
		assertEquals(0, (int) musicDataManager.getDynamicsCounts().get(Dynamics.MP));
		
		musicDataManager.incrementDynamicsMap(Dynamics.MF);
		assertEquals(1, (int) musicDataManager.getDynamicsCounts().get(Dynamics.MF));
		assertEquals(0, (int) musicDataManager.getDynamicsCounts().get(Dynamics.MP));
	}

	@Test
	public void incrementIntervalMap()
	{
		MusicDataManager musicDataManager = new MusicDataManager();
		assertEquals(0, (int) musicDataManager.getIntervalCounts().get(NoteInterval.MINOR_SECOND));
		assertEquals(0, (int) musicDataManager.getIntervalCounts().get(NoteInterval.DIMINISHED_FIFTH));
		
		musicDataManager.incrementIntervalMap(NoteInterval.MINOR_SECOND);
		assertEquals(1, (int) musicDataManager.getIntervalCounts().get(NoteInterval.MINOR_SECOND));
		assertEquals(0, (int) musicDataManager.getIntervalCounts().get(NoteInterval.DIMINISHED_FIFTH));
	}
	
	@Test
	public void reset()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 19.xml");
			Piece piece = parser.parse(file);
			
			MusicDataManager musicDataManager = new MusicDataManager(piece);
			piece.calculateStatistics(musicDataManager);
			musicDataManager.reset();
			
			assertEquals(0, (int) musicDataManager.getIntervalCounts().get(NoteInterval.MINOR_THIRD));
			assertEquals(0, (int) musicDataManager.getIntervalCounts().get(NoteInterval.MAJOR_SIXTH));
			assertEquals(0, (int) musicDataManager.getIntervalCounts().get(NoteInterval.MINOR_SEVENTH));
			assertEquals(0, (int) musicDataManager.getIntervalCounts().get(NoteInterval.MINOR_SIXTH));
			assertEquals(0, (int) musicDataManager.getIntervalCounts().get(NoteInterval.PERFECT_FIFTH));
			
			assertEquals(0, (int) musicDataManager.getNoteNameCounts().get(NoteName.C));
			assertEquals(0, (int) musicDataManager.getNoteNameCounts().get(NoteName.D));
			assertEquals(0, (int) musicDataManager.getNoteNameCounts().get(NoteName.E));
			assertEquals(0, (int) musicDataManager.getNoteNameCounts().get(NoteName.G));
			assertEquals(0, (int) musicDataManager.getNoteNameCounts().get(NoteName.B));
			
			assertEquals(0, (int) musicDataManager.getScaleDegreeCounts().get(ScaleDegree.TONIC));
			assertEquals(0, (int) musicDataManager.getScaleDegreeCounts().get(ScaleDegree.SUBMEDIANT));
			assertEquals(0, (int) musicDataManager.getScaleDegreeCounts().get(ScaleDegree.MEDIANT));
			assertEquals(0, (int) musicDataManager.getScaleDegreeCounts().get(ScaleDegree.DOMINANT));
			assertEquals(0, (int) musicDataManager.getScaleDegreeCounts().get(ScaleDegree.LEADING_TONE));
			
			assertEquals(0, (int) musicDataManager.getNoteLengthCounts().get(NoteLength.QUARTER));
			assertEquals(0, (int) musicDataManager.getNoteLengthCounts().get(NoteLength.WHOLE));
			
			assertEquals(0, (int) musicDataManager.getDynamicsCounts().get(Dynamics.FFF));
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
}
