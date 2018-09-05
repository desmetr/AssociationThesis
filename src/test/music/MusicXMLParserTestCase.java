package test.music;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

import music.model.data.Key;
import music.model.data.Measure;
import music.model.data.MusicalData;
import music.model.data.Note;
import music.model.data.Piece;
import music.model.data.MusicalData.Clef;
import music.model.data.MusicalData.NoteLength;
import music.model.data.MusicalData.NoteName;
import music.model.data.MusicalData.Scale;
import music.model.data.MusicalData.TimeSignatureEnum;
import music.model.parser.MusicXMLParser;
import utilities.GeneralData;
import utilities.PropertyManager;

/*
 * Test case to test the MusicXML parser. We test if the files are read correctly and the according objects are made correctly.
 */

class MusicXMLParserTestCase 
{
	private String testXMLDestinationPath;
	private String testFailXMLDestinationPath;
	
	public MusicXMLParserTestCase() 
	{
		new PropertyManager().getAllValues();
		testXMLDestinationPath = PropertyManager.getDestinationFolderMusic();
		testFailXMLDestinationPath = PropertyManager.getFailDestinationFolderMusic();
	}
	
	// Score 2.xml //
	
	@Test
	public void score2_Piece()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			assertEquals(2.0, piece.divisions);
			assertEquals(2, piece.getNumberOfMeasures());
			assertEquals(150, piece.tempo);
			assertFalse(piece.anacrusis);
			assertEquals(NoteLength.QUARTER, piece.beatUnit);
			assertEquals("QUARTER", piece.getBeatUnitAsString());
			assertEquals(150, piece.beatsPerMinute);
			assertEquals(Clef.TREBLE, piece.clef);
			assertEquals("TREBLE", piece.getClefAsString());
			assertEquals(TimeSignatureEnum.FOUR_FOUR, piece.getTimeSignature());
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Key()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			Key key = piece.key;
			
			assertEquals(NoteName.B_FLAT, key.noteName);
			assertEquals(Scale.MAJOR, key.scale);		
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}

	@Test
	public void score2_Measure1()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Measure measure = piece.measures.get(0);
			assertEquals(4, measure.timeSignature.beatUnit);
			assertEquals(4, measure.timeSignature.numberOfBeats);
			assertEquals(6, measure.getNumberOfNotes());
			assertFalse(measure.anacrusis);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Measure2()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Measure measure = piece.measures.get(1);
			assertEquals(4, measure.timeSignature.beatUnit);
			assertEquals(4, measure.timeSignature.numberOfBeats);
			assertEquals(8, measure.getNumberOfNotes());
			assertFalse(measure.anacrusis);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Note1_Measure1()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(0).notes.get(0);
			assertEquals(NoteName.B_FLAT, note.name);
			assertEquals("B_FLAT", note.getNameAsString());
			assertEquals(0.5 * piece.divisions, note.duration);
			assertEquals(NoteLength.EIGHTH, note.length);
			assertEquals(1, note.measureNumber);
			assertEquals(1, note.noteIndex);
			assertEquals(4, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Note2_Measure1()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(0).notes.get(1);
			assertEquals(NoteName.F, note.name);
			assertEquals("F", note.getNameAsString());
			assertEquals(0.5 * piece.divisions, note.duration);
			assertEquals(NoteLength.EIGHTH, note.length);
			assertEquals(1, note.measureNumber);
			assertEquals(2, note.noteIndex);
			assertEquals(4, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Note3_Measure1()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(0).notes.get(2);
			assertEquals(NoteName.B_FLAT, note.name);
			assertEquals("B_FLAT", note.getNameAsString());
			assertEquals(0.5 * piece.divisions, note.duration);
			assertEquals(NoteLength.EIGHTH, note.length);
			assertEquals(1, note.measureNumber);
			assertEquals(3, note.noteIndex);
			assertEquals(4, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Note4_Measure1()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(0).notes.get(3);
			assertEquals(NoteName.C, note.name);
			assertEquals("C", note.getNameAsString());
			assertEquals(0.5 * piece.divisions, note.duration);
			assertEquals(NoteLength.EIGHTH, note.length);
			assertEquals(1, note.measureNumber);
			assertEquals(4, note.noteIndex);
			assertEquals(5, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Note5_Measure1()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(0).notes.get(4);
			assertEquals(NoteName.D_FLAT, note.name);
			assertEquals("D_FLAT", note.getNameAsString());
			assertEquals(1.5 * piece.divisions, note.duration);
			assertEquals(NoteLength.QUARTER_DOTTED, note.length);
			assertEquals(1, note.measureNumber);
			assertEquals(5, note.noteIndex);
			assertEquals(5, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Note6_Measure1()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(0).notes.get(5);
			assertEquals(NoteName.G, note.name);
			assertEquals("G", note.getNameAsString());
			assertEquals(1.0 * piece.divisions, note.duration);
			assertEquals(1, note.measureNumber);
			assertEquals(6, note.noteIndex);
			assertEquals(4, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Note1_Measure2()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(1).notes.get(0);
			assertEquals(NoteName.G, note.name);
			assertEquals("G", note.getNameAsString());
			assertEquals(1.0 * piece.divisions, note.duration);
			assertEquals(NoteLength.END_OF_TIE, note.length);
			assertEquals(2, note.measureNumber);
			assertEquals(1, note.noteIndex);
			assertEquals(4, note.pitch);
			assertEquals(true, note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Note2_Measure2()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(1).notes.get(1);
			assertEquals(NoteName.E_FLAT, note.name);
			assertEquals("E_FLAT", note.getNameAsString());
			assertEquals(0.5 * piece.divisions, note.duration);
			assertEquals(NoteLength.EIGHTH, note.length);
			assertEquals(2, note.measureNumber);
			assertEquals(2, note.noteIndex);
			assertEquals(4, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Note3_Measure2()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(1).notes.get(2);
			assertEquals(NoteName.B_FLAT, note.name);
			assertEquals("B_FLAT", note.getNameAsString());
			assertEquals(0.5 * piece.divisions, note.duration);
			assertEquals(NoteLength.EIGHTH, note.length);
			assertEquals(2, note.measureNumber);
			assertEquals(3, note.noteIndex);
			assertEquals(4, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Note4_Measure2()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(1).notes.get(3);
			assertEquals(NoteName.C, note.name);
			assertEquals("C", note.getNameAsString());
			assertEquals(0.5 * piece.divisions, note.duration);
			assertEquals(NoteLength.EIGHTH, note.length);
			assertEquals(2, note.measureNumber);
			assertEquals(4, note.noteIndex);
			assertEquals(5, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Note5_Measure2()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(1).notes.get(4);
			assertEquals(NoteName.D_FLAT, note.name);
			assertEquals("D_FLAT", note.getNameAsString());
			assertEquals(0.5 * piece.divisions, note.duration);
			assertEquals(NoteLength.EIGHTH, note.length);
			assertEquals(2, note.measureNumber);
			assertEquals(5, note.noteIndex);
			assertEquals(5, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Note6_Measure2()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(1).notes.get(5);
			assertEquals(NoteName.B_FLAT, note.name);
			assertEquals("B_FLAT", note.getNameAsString());
			assertEquals(0.5 * piece.divisions, note.duration);
			assertEquals(NoteLength.EIGHTH, note.length);
			assertEquals(2, note.measureNumber);
			assertEquals(6, note.noteIndex);
			assertEquals(4, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Note7_Measure2()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(1).notes.get(6);
			assertEquals(NoteName.C, note.name);
			assertEquals("C", note.getNameAsString());
			assertEquals(0.5 * piece.divisions, note.duration);
			assertEquals(NoteLength.EIGHTH, note.length);
			assertEquals(2, note.measureNumber);
			assertEquals(7, note.noteIndex);
			assertEquals(5, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score2_Note8_Measure2()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(1).notes.get(7);
			assertEquals(NoteName.D_FLAT, note.name);
			assertEquals("D_FLAT", note.getNameAsString());
			assertEquals(0.5 * piece.divisions, note.duration);
			assertEquals(NoteLength.EIGHTH, note.length);
			assertEquals(2, note.measureNumber);
			assertEquals(8, note.noteIndex);
			assertEquals(5, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	// Score 3.xml and derivatives //
	
	@Test
	public void score3_Note1_Measure_4()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 3.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(3);
			Note note1 = measure.notes.get(0);
			
			assertEquals(NoteName.D, note1.name);
			assertEquals("D", note1.getNameAsString());
			assertEquals(2.0 * piece.divisions, note1.duration);
			assertEquals(NoteLength.HALF, note1.length);
			assertEquals(3, note1.measureNumber);
			assertEquals(1, note1.noteIndex);
			assertEquals(5, note1.pitch);
			assertFalse(note1.endOfTie);
			assertFalse(note1.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score3_WholeNotesTiedToQuarterNotes_Begin()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 3.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(1);
			Note note1 = measure.notes.get(0);
			
			assertEquals(NoteName.E_FLAT, note1.name);
			assertEquals("E_FLAT", note1.getNameAsString());
			assertEquals(5.0 * piece.divisions, note1.duration);
			assertEquals(1, note1.measureNumber);
			assertEquals(1, note1.noteIndex);
			assertEquals(5, note1.pitch);
			assertFalse(note1.endOfTie);
			assertFalse(note1.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score3_WholeNotesTiedToQuarterNotes_End()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 3.xml");
			Piece piece = parser.parse(file);
			
			Measure measure = piece.measures.get(2);
			Note note1 = measure.notes.get(0);
		
			assertEquals(NoteName.E_FLAT, note1.name);
			assertEquals("E_FLAT", note1.getNameAsString());
			assertEquals(5.0 * piece.divisions, note1.duration);
			assertEquals(NoteLength.END_OF_TIE, note1.length);
			assertEquals(2, note1.measureNumber);
			assertEquals(1, note1.noteIndex);
			assertEquals(5, note1.pitch);
			assertEquals(true, note1.endOfTie);
			assertFalse(note1.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score3_Piece()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 3.xml");
			Piece piece = parser.parse(file);
			
			assertEquals(1.0, piece.divisions);
			assertEquals(33, piece.getNumberOfMeasures());
			assertEquals(120, piece.tempo);
			assertEquals(true, piece.anacrusis);
			assertEquals(NoteLength.QUARTER, piece.beatUnit);
			assertEquals("QUARTER", piece.getBeatUnitAsString());
			assertEquals(120, piece.beatsPerMinute);
			assertEquals(Clef.TREBLE, piece.clef);
			assertEquals("TREBLE", piece.getClefAsString());
			assertEquals(TimeSignatureEnum.FOUR_FOUR, piece.getTimeSignature());
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score3_Anacrusis_2()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testFailXMLDestinationPath + "Score 3_Anacrusis_2.xml");
			Piece piece = parser.parse(file);
			
			assertFalse(piece.anacrusis);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score3_Anacrusis_3()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testFailXMLDestinationPath + "Score 3_Anacrusis_3.xml");
			Piece piece = parser.parse(file);
			
			assertFalse(piece.anacrusis);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score3_Anacrusis_4()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testFailXMLDestinationPath + "Score 3_Anacrusis_4.xml");
			Piece piece = parser.parse(file);
			
			assertFalse(piece.anacrusis);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	// Score 4.xml //
	
	@Test
	public void score4_Piece()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 4.xml");
			Piece piece = parser.parse(file);
			
			assertEquals(6.0, piece.divisions);
			assertEquals(4, piece.getNumberOfMeasures());
			assertEquals(120, piece.tempo);
			assertFalse(piece.anacrusis);
			assertEquals(NoteLength.QUARTER, piece.beatUnit);
			assertEquals("QUARTER", piece.getBeatUnitAsString());
			assertEquals(120, piece.beatsPerMinute);
			assertEquals(Clef.TREBLE, piece.clef);
			assertEquals("TREBLE", piece.getClefAsString());
			assertEquals(TimeSignatureEnum.FOUR_FOUR, piece.getTimeSignature());
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score4_Key()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 4.xml");
			Piece piece = parser.parse(file);
			Key key = piece.key;
			
			assertEquals(NoteName.A, key.noteName);
			assertEquals(Scale.MAJOR, key.scale);			
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score4_TripletEighth()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 4.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(0);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			Note note3 = measure.notes.get(2);
			
			assertEquals(NoteName.B, note1.name);
			assertEquals(NoteLength.EIGHTH_TRIPLET, note1.length);
			
			assertEquals(NoteName.D_FLAT, note2.name);
			assertEquals(NoteLength.EIGHTH_TRIPLET, note2.length);
			
			assertEquals(NoteName.B, note3.name);
			assertEquals(NoteLength.EIGHTH_TRIPLET, note3.length);
		}
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score4_TripletQuarter()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 4.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(1);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			Note note3 = measure.notes.get(2);
			
			assertEquals(NoteName.A_FLAT, note1.name);
			assertEquals(NoteLength.QUARTER_TRIPLET, note1.length);
			
			assertEquals(NoteName.B, note2.name);
			assertEquals(NoteLength.QUARTER_TRIPLET, note2.length);
			
			assertEquals(NoteName.G_FLAT, note3.name);
			assertEquals(NoteLength.QUARTER_TRIPLET, note3.length);
		}
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score4_TripletSixteenth()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 4.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(2);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			Note note3 = measure.notes.get(2);
			
			assertEquals(NoteName.E, note1.name);
			assertEquals(NoteLength.SIXTEENTH_TRIPLET, note1.length);
			
			assertEquals(NoteName.G_FLAT, note2.name);
			assertEquals(NoteLength.SIXTEENTH_TRIPLET, note2.length);
			
			assertEquals(NoteName.A_FLAT, note3.name);
			assertEquals(NoteLength.SIXTEENTH_TRIPLET, note3.length);
		}
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score4_TripletHalf()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 4.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(3);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			Note note3 = measure.notes.get(2);
			
			assertEquals(NoteName.B, note1.name);
			assertEquals(NoteLength.HALF_TRIPLET, note1.length);
			
			assertEquals(NoteName.E, note2.name);
			assertEquals(NoteLength.HALF_TRIPLET, note2.length);
			
			assertEquals(NoteName.A, note3.name);
			assertEquals(NoteLength.HALF_TRIPLET, note3.length);
		}
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	// Score 5.xml // 
	
	@Test
	public void score5_Piece()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 5.xml");
			Piece piece = parser.parse(file);
			
			assertEquals(2.0, piece.divisions);
			assertEquals(2, piece.getNumberOfMeasures());
			assertEquals(120, piece.tempo);
			assertFalse(piece.anacrusis);
			assertEquals(NoteLength.QUARTER, piece.beatUnit);
			assertEquals("QUARTER", piece.getBeatUnitAsString());
			assertEquals(120, piece.beatsPerMinute);
			assertEquals(Clef.BASS, piece.clef);
			assertEquals("BASS", piece.getClefAsString());
			assertEquals(TimeSignatureEnum.FOUR_FOUR, piece.getTimeSignature());
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score5_Key()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 5.xml");
			Piece piece = parser.parse(file);
			Key key = piece.key;
			
			assertEquals(NoteName.G, key.noteName);
			assertEquals(Scale.MAJOR, key.scale);			
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score5_TieInMeasure1()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 5.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(0);
			Note note1 = measure.notes.get(1);
			Note note2 = measure.notes.get(2);
			
			assertEquals(NoteName.D, note1.name);
			assertFalse(note1.endOfTie);
			
			assertEquals(NoteName.D, note2.name);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
			
		}
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score5_TieInMeasure2()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 5.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(1);
			Note note1 = measure.notes.get(3);
			Note note2 = measure.notes.get(4);
			
			assertEquals(NoteName.D, note1.name);
			assertFalse(note1.endOfTie);
			
			assertEquals(NoteName.D, note2.name);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
			
		}
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	// Score 6.xml //
	
	@Test
	public void score6_Note1_Measure1()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 6.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(0);
			Note note = measure.notes.get(0);
			
			assertEquals(NoteName.D_FLAT, note.name);
			assertEquals("D_FLAT", note.getNameAsString());
			assertEquals(2.0, note.duration);
			assertEquals(NoteLength.HALF, note.length);
			assertEquals(1, note.measureNumber);
			assertEquals(1, note.noteIndex);
			assertEquals(5, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score6_Note2_Measure1()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 6.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(0);
			Note note = measure.notes.get(1);
			
			assertEquals(NoteName.D_FLAT, note.name);
			assertEquals("D_FLAT", note.getNameAsString());
			assertEquals(4.0, note.duration);
			assertEquals(1, note.measureNumber);
			assertEquals(2, note.noteIndex);
			assertEquals(5, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score6_Note1_Measure2()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 6.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(1);
			Note note = measure.notes.get(0);
			
			assertEquals(NoteName.D_FLAT, note.name);
			assertEquals("D_FLAT", note.getNameAsString());
			assertEquals(4.0, note.duration);
			assertEquals(NoteLength.END_OF_TIE, note.length);
			assertEquals(2, note.measureNumber);
			assertEquals(1, note.noteIndex);
			assertEquals(5, note.pitch);
			assertEquals(true, note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score6_Note2_Measure2()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 6.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(1);
			Note note = measure.notes.get(1);
			
			assertEquals(NoteName.G, note.name);
			assertEquals("G", note.getNameAsString());
			assertEquals(2.0, note.duration);
			assertEquals(NoteLength.HALF, note.length);
			assertEquals(2, note.measureNumber);
			assertEquals(2, note.noteIndex);
			assertEquals(4, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	// Score 7.xml //
	
	@Test
	public void score7_Piece()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 7.xml");
			Piece piece = parser.parse(file);
		
			assertEquals(4.0, piece.divisions);
			assertEquals(1, piece.getNumberOfMeasures());
			assertEquals(120, piece.tempo);
			assertFalse(piece.anacrusis);
			assertEquals(NoteLength.QUARTER, piece.beatUnit);
			assertEquals("QUARTER", piece.getBeatUnitAsString());
			assertEquals(120, piece.beatsPerMinute);
			assertEquals(Clef.TREBLE, piece.clef);
			assertEquals("TREBLE", piece.getClefAsString());
			assertEquals(TimeSignatureEnum.FOUR_FOUR, piece.getTimeSignature());
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score7_Key()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 7.xml");
			Piece piece = parser.parse(file);
			Key key = piece.key;
			
			assertEquals(NoteName.E_FLAT, key.noteName);
			assertEquals(Scale.MAJOR, key.scale);			
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score7_Measure1()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 7.xml");
			Piece piece = parser.parse(file);
			
			Measure measure = piece.measures.get(0);
			assertEquals(4, measure.timeSignature.beatUnit);
			assertEquals(4, measure.timeSignature.numberOfBeats);
			assertEquals(13, measure.getNumberOfNotes());
			assertFalse(measure.anacrusis);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score7_Note1_Measure1()
	{
		try
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 7.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(0).notes.get(0);
			assertEquals(NoteName.C, note.name);
			assertEquals("C", note.getNameAsString());
			assertEquals(1.0, note.duration);
			assertEquals(NoteLength.SIXTEENTH, note.length);
			assertEquals(1, note.measureNumber);
			assertEquals(1, note.noteIndex);
			assertEquals(4, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	// Score 8.xml //
	
	@Test
	public void score8_EightTiedToHalf()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 8.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(0);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			
			assertEquals(NoteName.C, note1.name);
			assertEquals(2.5 * piece.divisions, note1.duration);
			
			assertEquals(NoteName.C, note2.name);
			assertEquals(2.5 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
		
	@Test
	public void score8_SixteenthTiedToSixteenth()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 8.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(1);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			
			assertEquals(NoteName.D_FLAT, note1.name);
			assertEquals(0.5 * piece.divisions, note1.duration);
			assertEquals(NoteLength.SIXTEENTH_TIED_TO_SIXTEENTH, note1.length);
			
			assertEquals(NoteName.D_FLAT, note2.name);
			assertEquals(0.5 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
		
	@Test
	public void score8_SixteenthTiedToEighth()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 8.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(2);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			
			assertEquals(NoteName.G_FLAT, note1.name);
			assertEquals(0.75 * piece.divisions, note1.duration);
			
			assertEquals(NoteName.G_FLAT, note2.name);
			assertEquals(0.75 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
		
	@Test
	public void score8_SixteenthTiedToQuarter()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 8.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(3);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			
			assertEquals(NoteName.B_FLAT, note1.name);
			assertEquals(1.25 * piece.divisions, note1.duration);
			
			assertEquals(NoteName.B_FLAT, note2.name);
			assertEquals(1.25 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
		
	@Test
	public void score8_SixteenthTiedToHalf()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 8.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(4);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			
			assertEquals(NoteName.E_FLAT, note1.name);
			assertEquals(2.25 * piece.divisions, note1.duration);
			
			assertEquals(NoteName.E_FLAT, note2.name);
			assertEquals(2.25 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	// Score 9.xml //
	
	@Test
	public void score9_QuarterTiedToSixteenth()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 9.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(0);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			
			assertEquals(NoteName.B, note1.name);
			assertEquals(1.25 * piece.divisions, note1.duration);
			assertEquals(NoteLength.QUARTER_TIED_TO_SIXTEENTH, note1.length);
			
			assertEquals(NoteName.B, note2.name);
			assertEquals(1.25 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score9_QuarterTiedToEighth()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 9.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(1);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			
			assertEquals(NoteName.F, note1.name);
			assertEquals(1.5 * piece.divisions, note1.duration);
			
			assertEquals(NoteName.F, note2.name);
			assertEquals(1.5 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score9_QuarterTiedToQuarter()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 9.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(2);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			
			assertEquals(NoteName.D, note1.name);
			assertEquals(2.0 * piece.divisions, note1.duration);
			
			assertEquals(NoteName.D, note2.name);
			assertEquals(2.0 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score9_QuarterTiedToHalf()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 9.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(3);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			
			assertEquals(NoteName.A, note1.name);
			assertEquals(3.0 * piece.divisions, note1.duration);
			
			assertEquals(NoteName.A, note2.name);
			assertEquals(3.0 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	// Score 10.xml -> nothing useful to test //
	
	// Score 11.xml -> nothing useful to test //
	
	// Score 12.xml //
	
	@Test
	public void score12_HalfTiedToSixteenth()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 12.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(0);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
						
			assertEquals(NoteName.C, note1.name);
			assertEquals(2.25 * piece.divisions, note1.duration);
			assertEquals(NoteLength.HALF_TIED_TO_SIXTEENTH, note1.length);
			
			assertEquals(NoteName.C, note2.name);
			assertEquals(2.25 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score12_HalfTiedToEighth()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 12.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(1);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			
			assertEquals(NoteName.B, note1.name);
			assertEquals(2.5 * piece.divisions, note1.duration);
			
			assertEquals(NoteName.B, note2.name);
			assertEquals(2.5 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score12_HalfTiedToQuarter()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 12.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(2);
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			
			assertEquals(NoteName.D, note1.name);
			assertEquals(3.0 * piece.divisions, note1.duration);
			
			assertEquals(NoteName.D, note2.name);
			assertEquals(3.0 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	// Score 13.xml //
	
	@Test
	public void score13_WholeTiedToSixteenth()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 13.xml");
			Piece piece = parser.parse(file);
			Measure measure1 = piece.measures.get(0);
			Note note1 = measure1.notes.get(0);
			
			Measure measure2 = piece.measures.get(1);
			Note note2 = measure2.notes.get(0);
			
			assertEquals(NoteName.B_FLAT, note1.name);
			assertEquals(4.25 * piece.divisions, note1.duration);
			
			assertEquals(NoteName.B_FLAT, note2.name);
			assertEquals(4.25 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score13_WholeTiedToEighth()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 13.xml");
			Piece piece = parser.parse(file);
			Measure measure1 = piece.measures.get(2);
			Note note1 = measure1.notes.get(0);
			
			Measure measure2 = piece.measures.get(3);
			Note note2 = measure2.notes.get(0);
			
			assertEquals(NoteName.G, note1.name);
			assertEquals(4.5 * piece.divisions, note1.duration);
			
			assertEquals(NoteName.G, note2.name);
			assertEquals(4.5 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score13_WholeTiedToHalf()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 13.xml");
			Piece piece = parser.parse(file);
			Measure measure1 = piece.measures.get(4);
			Note note1 = measure1.notes.get(0);
			
			Measure measure2 = piece.measures.get(5);
			Note note2 = measure2.notes.get(0);
			
			assertEquals(NoteName.D, note1.name);
			assertEquals(6.0 * piece.divisions, note1.duration);
			
			assertEquals(NoteName.D, note2.name);
			assertEquals(6.0 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score13_WholeTiedToWhole()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 13.xml");
			Piece piece = parser.parse(file);
			Measure measure1 = piece.measures.get(6);
			Note note1 = measure1.notes.get(0);
			
			Measure measure2 = piece.measures.get(7);
			Note note2 = measure2.notes.get(0);
			
			assertEquals(NoteName.G, note1.name);
			assertEquals(8.0 * piece.divisions, note1.duration);
			
			assertEquals(NoteName.G, note2.name);
			assertEquals(8.0 * piece.divisions, note2.duration);
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	// Score 14.xml //
	
	@Test
	public void score14_SixteenthRest()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 14.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(0);
			Note note = measure.notes.get(0);
						
			assertEquals(true, note.isRest);
			assertEquals(0.25 * piece.divisions, note.duration);
			assertEquals(NoteLength.SIXTEENTH, note.length);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score14_EighthRest()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 14.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(0);
			Note note = measure.notes.get(2);
						
			assertEquals(true, note.isRest);
			assertEquals(0.5 * piece.divisions, note.duration);
			assertEquals(NoteLength.EIGHTH, note.length);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score14_QuarterRest()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 14.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(0);
			Note note = measure.notes.get(3);
						
			assertEquals(true, note.isRest);
			assertEquals(1.0 * piece.divisions, note.duration);
			assertEquals(NoteLength.QUARTER, note.length);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score14_HalfRest()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 14.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(0);
			Note note = measure.notes.get(4);
						
			assertEquals(true, note.isRest);
			assertEquals(2.0 * piece.divisions, note.duration);
			assertEquals(NoteLength.HALF, note.length);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score14_WholeRest()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 14.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(1);
			Note note = measure.notes.get(0);
						
			assertEquals(true, note.isRest);
			assertEquals(4.0 * piece.divisions, note.duration);
			assertEquals(NoteLength.WHOLE, note.length);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	// Score 15.xml //
	
	@Test
	public void score15_TwoTiesOneMeasure()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 15.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(0);
			
			Note note1 = measure.notes.get(0);
			Note note2 = measure.notes.get(1);
			Note note5 = measure.notes.get(4);
			Note note6 = measure.notes.get(5);
			
			assertEquals(2.0 * piece.divisions, note1.duration);
			
			assertEquals(NoteLength.END_OF_TIE, note2.length);
			assertEquals(true, note2.endOfTie);
			
			assertEquals(1.0 * piece.divisions, note5.duration);
			
			assertEquals(NoteLength.END_OF_TIE, note6.length);
			assertEquals(true, note6.endOfTie);
			
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	// Score 16.xml //
	
	@Test
	public void score16_Piece()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 16.xml");
			Piece piece = parser.parse(file);
		
			assertEquals(4.0, piece.divisions);
			assertEquals(9, piece.getNumberOfMeasures());
			assertEquals(120, piece.tempo);
			assertEquals(true, piece.anacrusis);
			assertEquals(NoteLength.QUARTER, piece.beatUnit);
			assertEquals("QUARTER", piece.getBeatUnitAsString());
			assertEquals(120, piece.beatsPerMinute);
			assertEquals(Clef.TREBLE, piece.clef);
			assertEquals("TREBLE", piece.getClefAsString());
			assertEquals(TimeSignatureEnum.THREE_FOUR, piece.getTimeSignature());
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score16_Chord_Two_Notes_1()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 16.xml");

			Piece piece = parser.parse(file);
			
			Note note1 = piece.measures.get(1).notes.get(0);
			assertEquals(NoteName.B, note1.name);
			assertEquals(1.0 * piece.divisions, note1.duration);
			assertEquals(NoteLength.QUARTER, note1.length);
			assertEquals(true, note1.isChord);
			assertEquals(0, note1.chordNumber);
			
			Note note2 = piece.measures.get(1).notes.get(1);
			assertEquals(NoteName.G_FLAT, note2.name);
			assertEquals(1.0 * piece.divisions, note2.duration);
			assertEquals(NoteLength.QUARTER, note2.length);
			assertEquals(true, note2.isChord);
			assertEquals(0, note2.chordNumber);
			
			assertEquals(note1.chordNumber, note2.chordNumber);
			assertEquals(note1.defaultX, note2.defaultX);
		} 
		catch (Exception e)	{	e.printStackTrace();	}
	}
	
	@Test
	public void score16_Chord_Two_Notes_2()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 16.xml");

			Piece piece = parser.parse(file);
			
			Note note1 = piece.measures.get(6).notes.get(4);
			assertEquals(NoteName.D_FLAT, note1.name);
			assertEquals(0.75 * piece.divisions, note1.duration);
			assertEquals(NoteLength.EIGHTH_DOTTED, note1.length);
			assertEquals(true, note1.isChord);
			assertEquals(2, note1.chordNumber);
			
			Note note2 = piece.measures.get(6).notes.get(5);
			assertEquals(NoteName.A_FLAT, note2.name);
			assertEquals(0.75 * piece.divisions, note2.duration);
			assertEquals(NoteLength.EIGHTH_DOTTED, note2.length);
			assertEquals(true, note2.isChord);
			assertEquals(2, note2.chordNumber);
			
			assertEquals(note1.chordNumber, note2.chordNumber);
			assertEquals(note1.defaultX, note2.defaultX);
		} 
		catch (Exception e)	{	e.printStackTrace();	}
	}
	
	// Score 17.xml //
	
	@Test
	public void score17_Chord_Three_Notes()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 17.xml");

			Piece piece = parser.parse(file);
			
			Note note1 = piece.measures.get(0).notes.get(6);
			assertEquals(NoteName.D_FLAT, note1.name);
			assertEquals(0.5 * piece.divisions, note1.duration);
			assertEquals(NoteLength.EIGHTH, note1.length);
			assertEquals(true, note1.isChord);
			assertEquals(1, note1.chordNumber);
			
			Note note2 = piece.measures.get(0).notes.get(7);
			assertEquals(NoteName.F, note2.name);
			assertEquals(0.5 * piece.divisions, note2.duration);
			assertEquals(NoteLength.EIGHTH, note2.length);
			assertEquals(true, note2.isChord);
			assertEquals(1, note2.chordNumber);
			
			Note note3 = piece.measures.get(0).notes.get(8);
			assertEquals(NoteName.A_FLAT, note3.name);
			assertEquals(0.5 * piece.divisions, note3.duration);
			assertEquals(NoteLength.EIGHTH, note3.length);
			assertEquals(true, note3.isChord);
			assertEquals(1, note3.chordNumber);
			
			assertEquals(note1.chordNumber, note2.chordNumber);
			assertEquals(note1.chordNumber, note3.chordNumber);
			assertEquals(note2.chordNumber, note3.chordNumber);
			assertEquals(note1.defaultX, note2.defaultX);
			assertEquals(note1.defaultX, note3.defaultX);
			assertEquals(note2.defaultX, note3.defaultX);
		} 
		catch (Exception e)	{	e.printStackTrace();	}
	}
	
	
	// Score 18.xml //
	
	@Test
	public void score18_Piece()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 18.xml");
			Piece piece = parser.parse(file);
		
			assertEquals(8.0, piece.divisions);
			assertEquals(3, piece.getNumberOfMeasures());
			assertEquals(120, piece.tempo);
			assertFalse(piece.anacrusis);
			assertEquals(NoteLength.QUARTER, piece.beatUnit);
			assertEquals("QUARTER", piece.getBeatUnitAsString());
			assertEquals(120, piece.beatsPerMinute);
			assertEquals(Clef.BASS, piece.clef);
			assertEquals("BASS", piece.getClefAsString());
			assertEquals(TimeSignatureEnum.FOUR_FOUR, piece.getTimeSignature());
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score18_Key()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 18.xml");
			Piece piece = parser.parse(file);
			Key key = piece.key;
			
			assertEquals(NoteName.D_FLAT, key.noteName);
			assertEquals(Scale.MAJOR, key.scale);			
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score18_Sixteenth_Dotted()
	{
		try
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 18.xml");
			Piece piece = parser.parse(file);
			
			Note note1 = piece.measures.get(0).notes.get(0);
			assertEquals(NoteLength.SIXTEENTH_DOTTED, note1.length);
			
			Note note2 = piece.measures.get(0).notes.get(2);
			assertEquals(NoteLength.SIXTEENTH_DOTTED, note2.length);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score18_Eighth_Dotted()
	{
		try
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 18.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(1).notes.get(0);
			assertEquals(NoteLength.EIGHTH_DOTTED, note.length);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void score18_Half_Dotted()
	{
		try
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 18.xml");
			Piece piece = parser.parse(file);
			
			Note note = piece.measures.get(1).notes.get(2);
			assertEquals(NoteLength.HALF_DOTTED, note.length);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	// Score 19.xml //
	
	@Test
	public void score19_Piece()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 19.xml");
			Piece piece = parser.parse(file);
		
			assertEquals(1.0, piece.divisions);
			assertEquals(3, piece.getNumberOfMeasures());
			assertEquals(120, piece.tempo);
			assertFalse(piece.anacrusis);
			assertEquals(NoteLength.QUARTER, piece.beatUnit);
			assertEquals("QUARTER", piece.getBeatUnitAsString());
			assertEquals(120, piece.beatsPerMinute);
			assertEquals(Clef.TREBLE, piece.clef);
			assertEquals("TREBLE", piece.getClefAsString());
			assertEquals(TimeSignatureEnum.FOUR_FOUR, piece.getTimeSignature());
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
		
	@Test
	public void score19_Measure1()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 19.xml");
			Piece piece = parser.parse(file);
			
			Measure measure = piece.measures.get(0);
			assertEquals(4, measure.timeSignature.beatUnit);
			assertEquals(4, measure.timeSignature.numberOfBeats);
			assertEquals(4, measure.getNumberOfNotes());
			assertFalse(measure.anacrusis);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
		
	@Test
	public void score19_Measure2()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 19.xml");
			Piece piece = parser.parse(file);
			
			Measure measure = piece.measures.get(1);
			assertEquals(3, measure.timeSignature.beatUnit);
			assertEquals(4, measure.timeSignature.numberOfBeats);
			assertEquals(3, measure.getNumberOfNotes());
			assertFalse(measure.anacrusis);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
		
	@Test
	public void score19_WholeNote()
	{
		try 
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 19.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(2);
			Note note = measure.notes.get(0);
			
			assertEquals(NoteName.C, note.name);
			assertEquals("C", note.getNameAsString());
			assertEquals(4.0 * piece.divisions, note.duration);
			assertEquals(NoteLength.WHOLE, note.length);
			assertEquals(3, note.measureNumber);
			assertEquals(1, note.noteIndex);
			assertEquals(5, note.pitch);
			assertFalse(note.endOfTie);
			assertFalse(note.isRest);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	// Reset tests //
	
	@Test
	public void testResetPiece()
	{
		try
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			
			assertEquals(2, piece.getNumberOfMeasures());
			
			piece.reset();
			
			assertEquals(0, piece.tempo);
			assertEquals(null, piece.clef);
			assertEquals(null, piece.beatUnit);
			assertEquals(0, piece.beatsPerMinute);
			assertEquals(0, piece.getNumberOfMeasures());
			
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void testResetKey()
	{
		try
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			Key key = piece.key;
			
			assertEquals(NoteName.B_FLAT, key.noteName);
			
			key.reset();
			
			assertEquals(null, key.noteName);
			assertEquals(null, key.scale);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void testResetMeasure()
	{
		try
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(0);
			
			assertEquals(6, measure.getNumberOfNotes());
			
			measure.reset();
			
			assertEquals(0, measure.timeSignature.beatUnit);
			assertEquals(0, measure.timeSignature.numberOfBeats);
			assertEquals(0, measure.getNumberOfNotes());
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	@Test
	public void testResetNote()
	{
		try
		{
			MusicalData.initialize();
			GeneralData.preprocessMusicFiles();
			
			MusicXMLParser parser = new MusicXMLParser();
			File file = new File(testXMLDestinationPath + "Score 2.xml");
			Piece piece = parser.parse(file);
			Measure measure = piece.measures.get(0);
			Note note = measure.notes.get(0);
			
			assertEquals(NoteName.B_FLAT, note.name);
			
			note.reset();
			
			assertEquals(null, note.name);
			assertEquals(0, note.pitch);
			assertEquals(null, note.length);
			assertFalse(note.isRest);
			assertFalse(note.endOfTie);
		} 
		catch (Exception e) {	e.printStackTrace();	}
	}
}