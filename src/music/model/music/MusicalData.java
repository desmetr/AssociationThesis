package music.model.music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * Utilities class that contains all the fixed data used in the application.
 */

public class MusicalData 
{
	// Lists that define different scales.
	public static ArrayList<ScaleDegree> majorScale = new ArrayList<ScaleDegree>();
	public static ArrayList<ScaleDegree> minorScale = new ArrayList<ScaleDegree>();
	public static ArrayList<ScaleDegree> harmonicMinorScale = new ArrayList<ScaleDegree>();
	public static ArrayList<ScaleDegree> naturalMinorScale = new ArrayList<ScaleDegree>();
	public static ArrayList<ScaleDegree> bluesScale = new ArrayList<ScaleDegree>();
	public static ArrayList<ScaleDegree> pentatonicMajorScale = new ArrayList<ScaleDegree>();
	public static ArrayList<ScaleDegree> pentatonicMinorScale = new ArrayList<ScaleDegree>();
	// ionian = major
	public static ArrayList<ScaleDegree> dorianScale = new ArrayList<ScaleDegree>();
	public static ArrayList<ScaleDegree> phrygianScale = new ArrayList<ScaleDegree>();
	public static ArrayList<ScaleDegree> lydianScale = new ArrayList<ScaleDegree>();
	public static ArrayList<ScaleDegree> mixolydianScale = new ArrayList<ScaleDegree>();
	// aeolian = natural minor
	public static ArrayList<ScaleDegree> locrianScale = new ArrayList<ScaleDegree>();
	
	public static Map<Integer, NoteName> circleOfFifths = new HashMap<Integer, NoteName>();
	
	public static Map<Double, NoteLength> durationToLengthSixteenth = new HashMap<Double, MusicalData.NoteLength>();
	public static Map<Double, NoteLength> durationToLengthEighth = new HashMap<Double, MusicalData.NoteLength>();
	public static Map<Double, NoteLength> durationToLengthQuarter= new HashMap<Double, MusicalData.NoteLength>();
	public static Map<Double, NoteLength> durationToLengthHalf = new HashMap<Double, MusicalData.NoteLength>();
	public static Map<Double, NoteLength> durationToLengthWhole= new HashMap<Double, MusicalData.NoteLength>();
	
	public static Map<Double, NoteLength> durationToLengthTiedSixteenth = new HashMap<Double, MusicalData.NoteLength>();
	public static Map<Double, NoteLength> durationToLengthTiedEighth = new HashMap<Double, MusicalData.NoteLength>();
	public static Map<Double, NoteLength> durationToLengthTiedQuarter = new HashMap<Double, MusicalData.NoteLength>();
	public static Map<Double, NoteLength> durationToLengthTiedHalf = new HashMap<Double, MusicalData.NoteLength>();
	public static Map<Double, NoteLength> durationToLengthTiedWhole = new HashMap<Double, MusicalData.NoteLength>();
	
	public static Map<Double, NoteLength> durationOfRests = new HashMap<Double, NoteLength>();
	
	public enum Scale 				{MAJOR, MINOR, HARMONIC_MINOR, NATURAL_MINOR, BLUES, PENTATONIC_MAJOR, PENTATONIC_MINOR, IONIAN, DORIAN, PHRYGIAN, LYDIAN, MIXOLYDIAN, AEOLIAN, LOCRIAN};
	public enum NoteName 			{C, D_FLAT, D, E_FLAT, E, F, G_FLAT, G, A_FLAT, A, B_FLAT, B}	
	public enum NoteLength			{WHOLE, WHOLE_TIED_TO_SIXTEENTH, WHOLE_TIED_TO_EIGHTH, WHOLE_TIED_TO_QUARTER, WHOLE_TIED_TO_HALF, WHOLE_TIED_TO_WHOLE,
									 WHOLE_TIED_TO_WHOLE_TIED_TO_WHOLE, WHOLE_TIED_TO_WHOLE_TIED_TO_HALF, WHOLE_TIED_TO_WHOLE_TIED_TO_QUARTER, WHOLE_TIED_TO_WHOLE_TIED_TO_EIGHTH, WHOLE_TIED_TO_WHOLE_TIED_TO_SIXTEENTH,
									 WHOLE_TIED_TO_HALF_TIED_TO_WHOLE, WHOLE_TIED_TO_HALF_TIED_TO_HALF, WHOLE_TIED_TO_HALF_TIED_TO_QUARTER, WHOLE_TIED_TO_HALF_TIED_TO_EIGHTH, WHOLE_TIED_TO_HALF_TIED_TO_SIXTEENTH,
									 WHOLE_TIED_TO_QUARTER_TIED_TO_WHOLE, WHOLE_TIED_TO_QUARTER_TIED_TO_HALF, WHOLE_TIED_TO_QUARTER_TIED_TO_QUARTER, WHOLE_TIED_TO_QUARTER_TIED_TO_EIGHTH, WHOLE_TIED_TO_QUARTER_TIED_TO_SIXTEENTH,
									 WHOLE_TIED_TO_EIGHTH_TIED_TO_WHOLE, WHOLE_TIED_TO_EIGHTH_TIED_TO_HALF, WHOLE_TIED_TO_EIGHTH_TIED_TO_QUARTER, WHOLE_TIED_TO_EIGHTH_TIED_TO_EIGHTH, WHOLE_TIED_TO_EIGHTH_TIED_TO_SIXTEENTH,
									 WHOLE_TIED_TO_SIXTEENTH_TIED_TO_WHOLE, WHOLE_TIED_TO_SIXTEENTH_TIED_TO_HALF, WHOLE_TIED_TO_SIXTEENTH_TIED_TO_QUARTER, WHOLE_TIED_TO_SIXTEENTH_TIED_TO_EIGHTH, WHOLE_TIED_TO_SIXTEENTH_TIED_TO_SIXTEENTH,
									 
								 	 HALF, HALF_DOTTED, HALF_DOUBLE_DOTTED, HALF_TRIPLET, HALF_TIED_TO_SIXTEENTH, HALF_TIED_TO_EIGHTH, HALF_TIED_TO_QUARTER, HALF_TIED_TO_HALF, HALF_TIED_TO_WHOLE,
								 	 HALF_TIED_TO_WHOLE_TIED_TO_WHOLE, HALF_TIED_TO_WHOLE_TIED_TO_HALF, HALF_TIED_TO_WHOLE_TIED_TO_QUARTER, HALF_TIED_TO_WHOLE_TIED_TO_EIGHTH, HALF_TIED_TO_WHOLE_TIED_TO_SIXTEENTH,
									 HALF_TIED_TO_HALF_TIED_TO_WHOLE, HALF_TIED_TO_HALF_TIED_TO_HALF, HALF_TIED_TO_HALF_TIED_TO_QUARTER, HALF_TIED_TO_HALF_TIED_TO_EIGHTH, HALF_TIED_TO_HALF_TIED_TO_SIXTEENTH,
									 HALF_TIED_TO_QUARTER_TIED_TO_WHOLE, HALF_TIED_TO_QUARTER_TIED_TO_HALF, HALF_TIED_TO_QUARTER_TIED_TO_QUARTER, HALF_TIED_TO_QUARTER_TIED_TO_EIGHTH, HALF_TIED_TO_QUARTER_TIED_TO_SIXTEENTH,
									 HALF_TIED_TO_EIGHTH_TIED_TO_WHOLE, HALF_TIED_TO_EIGHTH_TIED_TO_HALF, HALF_TIED_TO_EIGHTH_TIED_TO_QUARTER, HALF_TIED_TO_EIGHTH_TIED_TO_EIGHTH, HALF_TIED_TO_EIGHTH_TIED_TO_SIXTEENTH,
									 HALF_TIED_TO_SIXTEENTH_TIED_TO_WHOLE, HALF_TIED_TO_SIXTEENTH_TIED_TO_HALF, HALF_TIED_TO_SIXTEENTH_TIED_TO_QUARTER, HALF_TIED_TO_SIXTEENTH_TIED_TO_EIGHTH, HALF_TIED_TO_SIXTEENTH_TIED_TO_SIXTEENTH,

								 	 QUARTER, QUARTER_DOTTED, QUARTER_DOUBLE_DOTTED, QUARTER_TRIPLET, QUARTER_TIED_TO_SIXTEENTH, QUARTER_TIED_TO_QUARTER, QUARTER_TIED_TO_EIGHTH, QUARTER_TIED_TO_HALF, QUARTER_TIED_TO_WHOLE,
								 	 QUARTER_TIED_TO_WHOLE_TIED_TO_WHOLE, QUARTER_TIED_TO_WHOLE_TIED_TO_HALF, QUARTER_TIED_TO_WHOLE_TIED_TO_QUARTER, QUARTER_TIED_TO_WHOLE_TIED_TO_EIGHTH, QUARTER_TIED_TO_WHOLE_TIED_TO_SIXTEENTH,
								 	 QUARTER_TIED_TO_HALF_TIED_TO_WHOLE, QUARTER_TIED_TO_HALF_TIED_TO_HALF, QUARTER_TIED_TO_HALF_TIED_TO_QUARTER, QUARTER_TIED_TO_HALF_TIED_TO_EIGHTH, QUARTER_TIED_TO_HALF_TIED_TO_SIXTEENTH,
								 	 QUARTER_TIED_TO_QUARTER_TIED_TO_WHOLE, QUARTER_TIED_TO_QUARTER_TIED_TO_HALF, QUARTER_TIED_TO_QUARTER_TIED_TO_QUARTER, QUARTER_TIED_TO_QUARTER_TIED_TO_EIGHTH, QUARTER_TIED_TO_QUARTER_TIED_TO_SIXTEENTH,
								 	 QUARTER_TIED_TO_EIGHTH_TIED_TO_WHOLE, QUARTER_TIED_TO_EIGHTH_TIED_TO_HALF, QUARTER_TIED_TO_EIGHTH_TIED_TO_QUARTER, QUARTER_TIED_TO_EIGHTH_TIED_TO_EIGHTH, QUARTER_TIED_TO_EIGHTH_TIED_TO_SIXTEENTH,
								 	 QUARTER_TIED_TO_SIXTEENTH_TIED_TO_WHOLE, QUARTER_TIED_TO_SIXTEENTH_TIED_TO_HALF, QUARTER_TIED_TO_SIXTEENTH_TIED_TO_QUARTER, QUARTER_TIED_TO_SIXTEENTH_TIED_TO_EIGHTH, QUARTER_TIED_TO_SIXTEENTH_TIED_TO_SIXTEENTH,

								 	 EIGHTH, EIGHTH_DOTTED, EIGHTH_DOUBLE_DOTTED, EIGHTH_TRIPLET, EIGHTH_TIED_TO_SIXTEENTH, EIGHTH_TIED_TO_EIGHTH, EIGHTH_TIED_TO_QUARTER, EIGHTH_TIED_TO_HALF, EIGHTH_TIED_TO_WHOLE,
								 	 EIGHTH_TIED_TO_WHOLE_TIED_TO_WHOLE, EIGHTH_TIED_TO_WHOLE_TIED_TO_HALF, EIGHTH_TIED_TO_WHOLE_TIED_TO_QUARTER, EIGHTH_TIED_TO_WHOLE_TIED_TO_EIGHTH, EIGHTH_TIED_TO_WHOLE_TIED_TO_SIXTEENTH,
								 	 EIGHTH_TIED_TO_HALF_TIED_TO_WHOLE, EIGHTH_TIED_TO_HALF_TIED_TO_HALF, EIGHTH_TIED_TO_HALF_TIED_TO_QUARTER, EIGHTH_TIED_TO_HALF_TIED_TO_EIGHTH, EIGHTH_TIED_TO_HALF_TIED_TO_SIXTEENTH,
								 	 EIGHTH_TIED_TO_QUARTER_TIED_TO_WHOLE, EIGHTH_TIED_TO_QUARTER_TIED_TO_HALF, EIGHTH_TIED_TO_QUARTER_TIED_TO_QUARTER, EIGHTH_TIED_TO_QUARTER_TIED_TO_EIGHTH, EIGHTH_TIED_TO_QUARTER_TIED_TO_SIXTEENTH,
								 	 EIGHTH_TIED_TO_EIGHTH_TIED_TO_WHOLE, EIGHTH_TIED_TO_EIGHTH_TIED_TO_HALF, EIGHTH_TIED_TO_EIGHTH_TIED_TO_QUARTER, EIGHTH_TIED_TO_EIGHTH_TIED_TO_EIGHTH, EIGHTH_TIED_TO_EIGHTH_TIED_TO_SIXTEENTH,
								 	 EIGHTH_TIED_TO_SIXTEENTH_TIED_TO_WHOLE, EIGHTH_TIED_TO_SIXTEENTH_TIED_TO_HALF, EIGHTH_TIED_TO_SIXTEENTH_TIED_TO_QUARTER, EIGHTH_TIED_TO_SIXTEENTH_TIED_TO_EIGHTH, EIGHTH_TIED_TO_SIXTEENTH_TIED_TO_SIXTEENTH,

								 	 SIXTEENTH, SIXTEENTH_DOTTED, SIXTEENTH_DOUBLE_DOTTED, SIXTEENTH_TRIPLET, SIXTEENTH_TIED_TO_SIXTEENTH, SIXTEENTH_TIED_TO_EIGHTH, SIXTEENTH_TIED_TO_QUARTER, SIXTEENTH_TIED_TO_HALF, SIXTEENTH_TIED_TO_WHOLE,
								 	 SIXTEENTH_TIED_TO_WHOLE_TIED_TO_WHOLE, SIXTEENTH_TIED_TO_WHOLE_TIED_TO_HALF, SIXTEENTH_TIED_TO_WHOLE_TIED_TO_QUARTER, SIXTEENTH_TIED_TO_WHOLE_TIED_TO_EIGHTH, SIXTEENTH_TIED_TO_WHOLE_TIED_TO_SIXTEENTH,
								 	 SIXTEENTH_TIED_TO_HALF_TIED_TO_WHOLE, SIXTEENTH_TIED_TO_HALF_TIED_TO_HALF, SIXTEENTH_TIED_TO_HALF_TIED_TO_QUARTER, SIXTEENTH_TIED_TO_HALF_TIED_TO_EIGHTH, SIXTEENTH_TIED_TO_HALF_TIED_TO_SIXTEENTH,
								 	 SIXTEENTH_TIED_TO_QUARTER_TIED_TO_WHOLE, SIXTEENTH_TIED_TO_QUARTER_TIED_TO_HALF, SIXTEENTH_TIED_TO_QUARTER_TIED_TO_QUARTER, SIXTEENTH_TIED_TO_QUARTER_TIED_TO_EIGHTH, SIXTEENTH_TIED_TO_QUARTER_TIED_TO_SIXTEENTH,
								 	 SIXTEENTH_TIED_TO_EIGHTH_TIED_TO_WHOLE, SIXTEENTH_TIED_TO_EIGHTH_TIED_TO_HALF, SIXTEENTH_TIED_TO_EIGHTH_TIED_TO_QUARTER, SIXTEENTH_TIED_TO_EIGHTH_TIED_TO_EIGHTH, SIXTEENTH_TIED_TO_EIGHTH_TIED_TO_SIXTEENTH,
								 	 SIXTEENTH_TIED_TO_SIXTEENTH_TIED_TO_WHOLE, SIXTEENTH_TIED_TO_SIXTEENTH_TIED_TO_HALF, SIXTEENTH_TIED_TO_SIXTEENTH_TIED_TO_QUARTER, SIXTEENTH_TIED_TO_SIXTEENTH_TIED_TO_EIGHTH, SIXTEENTH_TIED_TO_SIXTEENTH_TIED_TO_SIXTEENTH,

								 	 MIDDLE_OF_TIE, END_OF_TIE, LENGTH_OUT_OF_SCOPE}
	public enum ScaleDegree			{TONIC, SUPERTONIC, MEDIANT, SUBDOMINANT, DOMINANT, SUBMEDIANT, SUBTONIC, LEADING_TONE, BLUE_NOTE, NON_SCALE_NOTE}
	public enum Clef				{TREBLE, BASS}
	public enum TimeSignatureEnum	{FOUR_FOUR, THREE_FOUR, SIX_EIGHT, TWO_FOUR, TWELVE_EIGHT, SEVEN_EIGHT, SEVEN_FOUR, NINE_EIGHT, FIVE_FOUR, SIX_FOUR, CUT}
	public enum Dynamics			{PPP, PP, P, MP, MF, F, FF, FFF};
	public enum NoteInterval		{PERFECT_UNISON, MINOR_SECOND, MAJOR_SECOND, MINOR_THIRD, MAJOR_THIRD, PERFECT_FOURTH, DIMINISHED_FIFTH, PERFECT_FIFTH, MINOR_SIXTH, MAJOR_SIXTH, MINOR_SEVENTH, MAJOR_SEVENTH, PERFECT_OCTAVE}
	
	public static ArrayList<NoteName> notes = new ArrayList<NoteName>();
	public static ArrayList<TimeSignatureEnum> timeSignatures = new ArrayList<TimeSignatureEnum>();
	
	// Initialize all lists, maps, etc.
	public static void initialize()
	{
		majorScale.addAll(Arrays.asList(ScaleDegree.TONIC,
										ScaleDegree.NON_SCALE_NOTE,
										ScaleDegree.SUPERTONIC,
										ScaleDegree.NON_SCALE_NOTE,
										ScaleDegree.MEDIANT, 
										ScaleDegree.SUBDOMINANT, 
										ScaleDegree.NON_SCALE_NOTE,
										ScaleDegree.DOMINANT, 
										ScaleDegree.NON_SCALE_NOTE,
										ScaleDegree.SUBMEDIANT, 
										ScaleDegree.NON_SCALE_NOTE,
										ScaleDegree.LEADING_TONE));
		
		minorScale.addAll(Arrays.asList(ScaleDegree.TONIC,
											   ScaleDegree.NON_SCALE_NOTE,
											   ScaleDegree.SUPERTONIC, 
											   ScaleDegree.MEDIANT, 
											   ScaleDegree.NON_SCALE_NOTE,
											   ScaleDegree.SUBDOMINANT, 
											   ScaleDegree.NON_SCALE_NOTE,
											   ScaleDegree.DOMINANT, 
											   ScaleDegree.NON_SCALE_NOTE,
											   ScaleDegree.SUBMEDIANT, 
											   ScaleDegree.NON_SCALE_NOTE,
											   ScaleDegree.LEADING_TONE));
		
		harmonicMinorScale.addAll(Arrays.asList(ScaleDegree.TONIC,
												ScaleDegree.NON_SCALE_NOTE,
												ScaleDegree.SUPERTONIC, 
												ScaleDegree.MEDIANT, 
												ScaleDegree.NON_SCALE_NOTE,
												ScaleDegree.SUBDOMINANT, 
												ScaleDegree.NON_SCALE_NOTE,
												ScaleDegree.DOMINANT, 
												ScaleDegree.SUBMEDIANT, 
												ScaleDegree.NON_SCALE_NOTE,
												ScaleDegree.NON_SCALE_NOTE,
												ScaleDegree.LEADING_TONE));
		
		naturalMinorScale.addAll(Arrays.asList(ScaleDegree.TONIC, 
											   ScaleDegree.NON_SCALE_NOTE,
											   ScaleDegree.SUPERTONIC,
											   ScaleDegree.MEDIANT, 
											   ScaleDegree.NON_SCALE_NOTE,
											   ScaleDegree.SUBDOMINANT, 
											   ScaleDegree.NON_SCALE_NOTE,
											   ScaleDegree.DOMINANT, 
											   ScaleDegree.SUBMEDIANT, 
											   ScaleDegree.NON_SCALE_NOTE,
											   ScaleDegree.SUBTONIC,
											   ScaleDegree.NON_SCALE_NOTE));
		
		bluesScale.addAll(Arrays.asList(ScaleDegree.TONIC,
										ScaleDegree.NON_SCALE_NOTE,
										ScaleDegree.NON_SCALE_NOTE,
										ScaleDegree.MEDIANT, 
										ScaleDegree.NON_SCALE_NOTE,
										ScaleDegree.SUBDOMINANT, 
										ScaleDegree.BLUE_NOTE,
										ScaleDegree.DOMINANT, 
										ScaleDegree.NON_SCALE_NOTE,
										ScaleDegree.NON_SCALE_NOTE,
										ScaleDegree.SUBTONIC,
										ScaleDegree.NON_SCALE_NOTE));
		
		pentatonicMajorScale.addAll(Arrays.asList(ScaleDegree.TONIC, 
												  ScaleDegree.NON_SCALE_NOTE,
												  ScaleDegree.SUPERTONIC, 
												  ScaleDegree.NON_SCALE_NOTE,
												  ScaleDegree.MEDIANT, 
												  ScaleDegree.NON_SCALE_NOTE,
												  ScaleDegree.NON_SCALE_NOTE,
												  ScaleDegree.DOMINANT, 
												  ScaleDegree.NON_SCALE_NOTE,
												  ScaleDegree.SUBMEDIANT,
												  ScaleDegree.NON_SCALE_NOTE,
												  ScaleDegree.NON_SCALE_NOTE));
		
		pentatonicMinorScale.addAll(Arrays.asList(ScaleDegree.TONIC, 
												  ScaleDegree.NON_SCALE_NOTE,
												  ScaleDegree.NON_SCALE_NOTE,
												  ScaleDegree.MEDIANT, 
												  ScaleDegree.NON_SCALE_NOTE,
												  ScaleDegree.SUBDOMINANT, 
												  ScaleDegree.NON_SCALE_NOTE,
												  ScaleDegree.DOMINANT, 
												  ScaleDegree.NON_SCALE_NOTE,
												  ScaleDegree.NON_SCALE_NOTE,
												  ScaleDegree.SUBTONIC,
												  ScaleDegree.NON_SCALE_NOTE));
		
		dorianScale.addAll(Arrays.asList(ScaleDegree.TONIC,
										 ScaleDegree.NON_SCALE_NOTE,
										 ScaleDegree.SUPERTONIC,
										 ScaleDegree.MEDIANT,
										 ScaleDegree.NON_SCALE_NOTE, 
										 ScaleDegree.SUBDOMINANT, 
										 ScaleDegree.NON_SCALE_NOTE,
										 ScaleDegree.DOMINANT, 
										 ScaleDegree.NON_SCALE_NOTE,
										 ScaleDegree.SUBMEDIANT, 
										 ScaleDegree.SUBTONIC,
										 ScaleDegree.NON_SCALE_NOTE));
		
		phrygianScale.addAll(Arrays.asList(ScaleDegree.TONIC,
										   ScaleDegree.SUPERTONIC,
										   ScaleDegree.NON_SCALE_NOTE,
										   ScaleDegree.MEDIANT,
										   ScaleDegree.NON_SCALE_NOTE, 
										   ScaleDegree.SUBDOMINANT, 
										   ScaleDegree.NON_SCALE_NOTE,
										   ScaleDegree.DOMINANT, 
										   ScaleDegree.SUBMEDIANT,
										   ScaleDegree.NON_SCALE_NOTE, 
										   ScaleDegree.SUBTONIC,
										   ScaleDegree.NON_SCALE_NOTE));
		
		lydianScale.addAll(Arrays.asList(ScaleDegree.TONIC,
										 ScaleDegree.NON_SCALE_NOTE,
										 ScaleDegree.SUPERTONIC,
										 ScaleDegree.NON_SCALE_NOTE,
										 ScaleDegree.MEDIANT, 
										 ScaleDegree.NON_SCALE_NOTE, 
										 ScaleDegree.SUBDOMINANT,
										 ScaleDegree.DOMINANT, 
										 ScaleDegree.NON_SCALE_NOTE,
										 ScaleDegree.SUBMEDIANT, 
										 ScaleDegree.NON_SCALE_NOTE,
										 ScaleDegree.LEADING_TONE));
		
		mixolydianScale.addAll(Arrays.asList(ScaleDegree.TONIC,
											 ScaleDegree.NON_SCALE_NOTE,
											 ScaleDegree.SUPERTONIC,
											 ScaleDegree.NON_SCALE_NOTE,
											 ScaleDegree.MEDIANT, 
											 ScaleDegree.SUBDOMINANT, 
											 ScaleDegree.NON_SCALE_NOTE,
											 ScaleDegree.DOMINANT, 
											 ScaleDegree.NON_SCALE_NOTE,
											 ScaleDegree.SUBMEDIANT, 
											 ScaleDegree.SUBTONIC,
											 ScaleDegree.NON_SCALE_NOTE));
		
		locrianScale.addAll(Arrays.asList(ScaleDegree.TONIC,
										  ScaleDegree.SUPERTONIC,
										  ScaleDegree.NON_SCALE_NOTE,
										  ScaleDegree.MEDIANT,
										  ScaleDegree.NON_SCALE_NOTE, 
										  ScaleDegree.SUBDOMINANT, 
										  ScaleDegree.DOMINANT,
										  ScaleDegree.NON_SCALE_NOTE, 
										  ScaleDegree.SUBMEDIANT,
										  ScaleDegree.NON_SCALE_NOTE, 
										  ScaleDegree.SUBTONIC,
										  ScaleDegree.NON_SCALE_NOTE));
									
		notes.addAll(Arrays.asList(NoteName.C, NoteName.D_FLAT, NoteName.D, NoteName.E_FLAT, NoteName.E, NoteName.F, NoteName.G_FLAT, NoteName.G, NoteName.A_FLAT, NoteName.A, NoteName.B_FLAT, NoteName.B));
		timeSignatures.addAll(Arrays.asList(TimeSignatureEnum.FOUR_FOUR, TimeSignatureEnum.THREE_FOUR, TimeSignatureEnum.SIX_EIGHT, TimeSignatureEnum.TWO_FOUR, TimeSignatureEnum.TWELVE_EIGHT, 
												TimeSignatureEnum.SEVEN_EIGHT, TimeSignatureEnum.SEVEN_FOUR, TimeSignatureEnum.NINE_EIGHT, TimeSignatureEnum.FIVE_FOUR, TimeSignatureEnum.SIX_FOUR, TimeSignatureEnum.CUT));

		// MusicXML represents sharps with positive numbers and flats with negative numbers. 
		// This is based on the circle of fifths, where the number of sharps increase clock-wise (positive) and flats counter-clock-wise (negative)
		circleOfFifths.put(-7, NoteName.B);
		circleOfFifths.put(-6, NoteName.G_FLAT);
		circleOfFifths.put(-5, NoteName.D_FLAT);
		circleOfFifths.put(-4, NoteName.A_FLAT);
		circleOfFifths.put(-3, NoteName.E_FLAT);
		circleOfFifths.put(-2, NoteName.B_FLAT);
		circleOfFifths.put(-1, NoteName.F);
		circleOfFifths.put(0, NoteName.C);
		circleOfFifths.put(1, NoteName.G);
		circleOfFifths.put(2, NoteName.D);
		circleOfFifths.put(3, NoteName.A);
		circleOfFifths.put(4, NoteName.E);
		circleOfFifths.put(5, NoteName.B);
		circleOfFifths.put(6, NoteName.G_FLAT);
		circleOfFifths.put(7, NoteName.D_FLAT);
	}
	
	public static void initializeMaps(double divisions)
	{
		durationToLengthSixteenth.put(0.25 * divisions, NoteLength.SIXTEENTH);
		durationToLengthSixteenth.put(0.5 * divisions, NoteLength.SIXTEENTH_DOTTED);
		durationToLengthSixteenth.put(0.167 * divisions, NoteLength.SIXTEENTH_TRIPLET);		
		
		durationToLengthEighth.put(0.3 * divisions, NoteLength.EIGHTH_TRIPLET);
		durationToLengthEighth.put(0.5 * divisions, NoteLength.EIGHTH);
		durationToLengthEighth.put(0.75 * divisions, NoteLength.EIGHTH_DOTTED);
		
		durationToLengthQuarter.put(1.0 * divisions, NoteLength.QUARTER);
		durationToLengthQuarter.put(1.5 * divisions, NoteLength.QUARTER_DOTTED);
		durationToLengthQuarter.put(0.67 * divisions, NoteLength.QUARTER_TRIPLET);
		
		durationToLengthHalf.put(2.0 * divisions, NoteLength.HALF);
		durationToLengthHalf.put(3.0 * divisions, NoteLength.HALF_DOTTED);
		durationToLengthHalf.put(1.3 * divisions, NoteLength.HALF_TRIPLET);
		
		durationToLengthWhole.put(4.0 * divisions, NoteLength.WHOLE);
		
		///////
		
		durationOfRests.put(0.25 * divisions, NoteLength.SIXTEENTH);
		durationOfRests.put(0.5 * divisions, NoteLength.SIXTEENTH_DOTTED);
		durationOfRests.put(0.5 * divisions, NoteLength.EIGHTH);
		durationOfRests.put(0.75 * divisions, NoteLength.EIGHTH_DOTTED);
		durationOfRests.put(1.0 * divisions, NoteLength.QUARTER);
		durationOfRests.put(1.5 * divisions, NoteLength.QUARTER_DOTTED);
		durationOfRests.put(2.0 * divisions, NoteLength.HALF);
		durationOfRests.put(3.0 * divisions, NoteLength.HALF_DOTTED);
		durationOfRests.put(4.0 * divisions, NoteLength.WHOLE);
		
		///////

		durationToLengthTiedWhole.put(4.25 * divisions, NoteLength.WHOLE_TIED_TO_SIXTEENTH);
		durationToLengthTiedWhole.put(4.5 * divisions, NoteLength.WHOLE_TIED_TO_EIGHTH);
		durationToLengthTiedWhole.put(5.0 * divisions, NoteLength.WHOLE_TIED_TO_QUARTER);
		durationToLengthTiedWhole.put(6.0 * divisions, NoteLength.WHOLE_TIED_TO_HALF);
		durationToLengthTiedWhole.put(8.0 * divisions, NoteLength.WHOLE_TIED_TO_WHOLE);
		
		durationToLengthTiedWhole.put(12.0 * divisions, NoteLength.WHOLE_TIED_TO_WHOLE_TIED_TO_WHOLE);
		durationToLengthTiedWhole.put(10.0 * divisions, NoteLength.WHOLE_TIED_TO_WHOLE_TIED_TO_HALF);
		durationToLengthTiedWhole.put(9.0 * divisions, NoteLength.WHOLE_TIED_TO_WHOLE_TIED_TO_QUARTER);
		durationToLengthTiedWhole.put(8.5 * divisions, NoteLength.WHOLE_TIED_TO_WHOLE_TIED_TO_EIGHTH);
		durationToLengthTiedWhole.put(8.25 * divisions, NoteLength.WHOLE_TIED_TO_WHOLE_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedWhole.put(10.0 * divisions, NoteLength.WHOLE_TIED_TO_HALF_TIED_TO_WHOLE);
		durationToLengthTiedWhole.put(8.0 * divisions, NoteLength.WHOLE_TIED_TO_HALF_TIED_TO_HALF);
		durationToLengthTiedWhole.put(7.0 * divisions, NoteLength.WHOLE_TIED_TO_HALF_TIED_TO_QUARTER);
		durationToLengthTiedWhole.put(6.5 * divisions, NoteLength.WHOLE_TIED_TO_HALF_TIED_TO_EIGHTH);
		durationToLengthTiedWhole.put(6.25 * divisions, NoteLength.WHOLE_TIED_TO_HALF_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedWhole.put(9.0 * divisions, NoteLength.WHOLE_TIED_TO_QUARTER_TIED_TO_WHOLE);
		durationToLengthTiedWhole.put(7.0 * divisions, NoteLength.WHOLE_TIED_TO_QUARTER_TIED_TO_HALF);
		durationToLengthTiedWhole.put(6.0 * divisions, NoteLength.WHOLE_TIED_TO_QUARTER_TIED_TO_QUARTER);
		durationToLengthTiedWhole.put(5.5 * divisions, NoteLength.WHOLE_TIED_TO_QUARTER_TIED_TO_EIGHTH);
		durationToLengthTiedWhole.put(5.25 * divisions, NoteLength.WHOLE_TIED_TO_QUARTER_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedWhole.put(8.5 * divisions, NoteLength.WHOLE_TIED_TO_EIGHTH_TIED_TO_WHOLE);
		durationToLengthTiedWhole.put(6.5 * divisions, NoteLength.WHOLE_TIED_TO_EIGHTH_TIED_TO_HALF);
		durationToLengthTiedWhole.put(5.5 * divisions, NoteLength.WHOLE_TIED_TO_EIGHTH_TIED_TO_QUARTER);
		durationToLengthTiedWhole.put(5.0 * divisions, NoteLength.WHOLE_TIED_TO_EIGHTH_TIED_TO_EIGHTH);
		durationToLengthTiedWhole.put(4.75 * divisions, NoteLength.WHOLE_TIED_TO_EIGHTH_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedWhole.put(8.25 * divisions, NoteLength.WHOLE_TIED_TO_SIXTEENTH_TIED_TO_WHOLE);
		durationToLengthTiedWhole.put(6.25 * divisions, NoteLength.WHOLE_TIED_TO_SIXTEENTH_TIED_TO_HALF);
		durationToLengthTiedWhole.put(5.25 * divisions, NoteLength.WHOLE_TIED_TO_SIXTEENTH_TIED_TO_QUARTER);
		durationToLengthTiedWhole.put(4.75 * divisions, NoteLength.WHOLE_TIED_TO_SIXTEENTH_TIED_TO_EIGHTH);
		durationToLengthTiedWhole.put(4.5 * divisions, NoteLength.WHOLE_TIED_TO_SIXTEENTH_TIED_TO_SIXTEENTH);
		
		//
		
		durationToLengthTiedHalf.put(2.25 * divisions, NoteLength.HALF_TIED_TO_SIXTEENTH);
		durationToLengthTiedHalf.put(2.5 * divisions, NoteLength.HALF_TIED_TO_EIGHTH);
		durationToLengthTiedHalf.put(3.0 * divisions, NoteLength.HALF_TIED_TO_QUARTER);
		durationToLengthTiedHalf.put(4.0 * divisions, NoteLength.HALF_TIED_TO_HALF);
		durationToLengthTiedHalf.put(6.0 * divisions, NoteLength.HALF_TIED_TO_WHOLE);
		
		durationToLengthTiedHalf.put(10.0 * divisions, NoteLength.HALF_TIED_TO_WHOLE_TIED_TO_WHOLE);
		durationToLengthTiedHalf.put(8.0 * divisions, NoteLength.HALF_TIED_TO_WHOLE_TIED_TO_HALF);
		durationToLengthTiedHalf.put(7.0 * divisions, NoteLength.HALF_TIED_TO_WHOLE_TIED_TO_QUARTER);
		durationToLengthTiedHalf.put(6.5 * divisions, NoteLength.HALF_TIED_TO_WHOLE_TIED_TO_EIGHTH);
		durationToLengthTiedHalf.put(6.25 * divisions, NoteLength.HALF_TIED_TO_WHOLE_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedHalf.put(8.0 * divisions, NoteLength.HALF_TIED_TO_HALF_TIED_TO_WHOLE);
		durationToLengthTiedHalf.put(6.0 * divisions, NoteLength.HALF_TIED_TO_HALF_TIED_TO_HALF);
		durationToLengthTiedHalf.put(5.0 * divisions, NoteLength.HALF_TIED_TO_HALF_TIED_TO_QUARTER);
		durationToLengthTiedHalf.put(4.5 * divisions, NoteLength.HALF_TIED_TO_HALF_TIED_TO_EIGHTH);
		durationToLengthTiedHalf.put(4.25 * divisions, NoteLength.HALF_TIED_TO_HALF_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedHalf.put(7.0 * divisions, NoteLength.HALF_TIED_TO_QUARTER_TIED_TO_WHOLE);
		durationToLengthTiedHalf.put(5.0 * divisions, NoteLength.HALF_TIED_TO_QUARTER_TIED_TO_HALF);
		durationToLengthTiedHalf.put(4.0 * divisions, NoteLength.HALF_TIED_TO_QUARTER_TIED_TO_QUARTER);
		durationToLengthTiedHalf.put(3.5 * divisions, NoteLength.HALF_TIED_TO_QUARTER_TIED_TO_EIGHTH);
		durationToLengthTiedHalf.put(3.25 * divisions, NoteLength.HALF_TIED_TO_QUARTER_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedHalf.put(6.5 * divisions, NoteLength.HALF_TIED_TO_EIGHTH_TIED_TO_WHOLE);
		durationToLengthTiedHalf.put(4.5 * divisions, NoteLength.HALF_TIED_TO_EIGHTH_TIED_TO_HALF);
		durationToLengthTiedHalf.put(3.5 * divisions, NoteLength.HALF_TIED_TO_EIGHTH_TIED_TO_QUARTER);
		durationToLengthTiedHalf.put(3.0 * divisions, NoteLength.HALF_TIED_TO_EIGHTH_TIED_TO_EIGHTH);
		durationToLengthTiedHalf.put(2.75 * divisions, NoteLength.HALF_TIED_TO_EIGHTH_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedHalf.put(6.25 * divisions, NoteLength.HALF_TIED_TO_SIXTEENTH_TIED_TO_WHOLE);
		durationToLengthTiedHalf.put(4.25 * divisions, NoteLength.HALF_TIED_TO_SIXTEENTH_TIED_TO_HALF);
		durationToLengthTiedHalf.put(3.25 * divisions, NoteLength.HALF_TIED_TO_SIXTEENTH_TIED_TO_QUARTER);
		durationToLengthTiedHalf.put(2.75 * divisions, NoteLength.HALF_TIED_TO_SIXTEENTH_TIED_TO_EIGHTH);
		durationToLengthTiedHalf.put(2.5 * divisions, NoteLength.HALF_TIED_TO_SIXTEENTH_TIED_TO_SIXTEENTH);
		
		//
		
		durationToLengthTiedQuarter.put(1.25 * divisions, NoteLength.QUARTER_TIED_TO_SIXTEENTH);
		durationToLengthTiedQuarter.put(1.5 * divisions, NoteLength.QUARTER_TIED_TO_EIGHTH);
		durationToLengthTiedQuarter.put(2.0 * divisions, NoteLength.QUARTER_TIED_TO_QUARTER);
		durationToLengthTiedQuarter.put(3.0 * divisions, NoteLength.QUARTER_TIED_TO_HALF);
		durationToLengthTiedQuarter.put(5.0 * divisions, NoteLength.QUARTER_TIED_TO_WHOLE);
		
		durationToLengthTiedQuarter.put(9.0 * divisions, NoteLength.QUARTER_TIED_TO_WHOLE_TIED_TO_WHOLE);
		durationToLengthTiedQuarter.put(7.0 * divisions, NoteLength.QUARTER_TIED_TO_WHOLE_TIED_TO_HALF);
		durationToLengthTiedQuarter.put(6.0 * divisions, NoteLength.QUARTER_TIED_TO_WHOLE_TIED_TO_QUARTER);
		durationToLengthTiedQuarter.put(5.5 * divisions, NoteLength.QUARTER_TIED_TO_WHOLE_TIED_TO_EIGHTH);
		durationToLengthTiedQuarter.put(5.25 * divisions, NoteLength.QUARTER_TIED_TO_WHOLE_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedQuarter.put(7.0 * divisions, NoteLength.QUARTER_TIED_TO_HALF_TIED_TO_WHOLE);
		durationToLengthTiedQuarter.put(5.0 * divisions, NoteLength.QUARTER_TIED_TO_HALF_TIED_TO_HALF);
		durationToLengthTiedQuarter.put(4.0 * divisions, NoteLength.QUARTER_TIED_TO_HALF_TIED_TO_QUARTER);
		durationToLengthTiedQuarter.put(3.5 * divisions, NoteLength.QUARTER_TIED_TO_HALF_TIED_TO_EIGHTH);
		durationToLengthTiedQuarter.put(3.25 * divisions, NoteLength.QUARTER_TIED_TO_HALF_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedQuarter.put(6.0 * divisions, NoteLength.QUARTER_TIED_TO_QUARTER_TIED_TO_WHOLE);
		durationToLengthTiedQuarter.put(4.0 * divisions, NoteLength.QUARTER_TIED_TO_QUARTER_TIED_TO_HALF);
		durationToLengthTiedQuarter.put(3.0 * divisions, NoteLength.QUARTER_TIED_TO_QUARTER_TIED_TO_QUARTER);
		durationToLengthTiedQuarter.put(2.5 * divisions, NoteLength.QUARTER_TIED_TO_QUARTER_TIED_TO_EIGHTH);
		durationToLengthTiedQuarter.put(2.25 * divisions, NoteLength.QUARTER_TIED_TO_QUARTER_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedQuarter.put(5.5 * divisions, NoteLength.QUARTER_TIED_TO_EIGHTH_TIED_TO_WHOLE);
		durationToLengthTiedQuarter.put(3.5 * divisions, NoteLength.QUARTER_TIED_TO_EIGHTH_TIED_TO_HALF);
		durationToLengthTiedQuarter.put(2.5 * divisions, NoteLength.QUARTER_TIED_TO_EIGHTH_TIED_TO_QUARTER);
		durationToLengthTiedQuarter.put(2.0 * divisions, NoteLength.QUARTER_TIED_TO_EIGHTH_TIED_TO_EIGHTH);
		durationToLengthTiedQuarter.put(1.75* divisions, NoteLength.QUARTER_TIED_TO_EIGHTH_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedQuarter.put(5.25 * divisions, NoteLength.QUARTER_TIED_TO_SIXTEENTH_TIED_TO_WHOLE);
		durationToLengthTiedQuarter.put(3.25 * divisions, NoteLength.QUARTER_TIED_TO_SIXTEENTH_TIED_TO_HALF);
		durationToLengthTiedQuarter.put(2.25 * divisions, NoteLength.QUARTER_TIED_TO_SIXTEENTH_TIED_TO_QUARTER);
		durationToLengthTiedQuarter.put(1.75 * divisions, NoteLength.QUARTER_TIED_TO_SIXTEENTH_TIED_TO_EIGHTH);
		durationToLengthTiedQuarter.put(1.5 * divisions, NoteLength.QUARTER_TIED_TO_SIXTEENTH_TIED_TO_SIXTEENTH);
		
		//
		
		durationToLengthTiedEighth.put(0.75 * divisions, NoteLength.EIGHTH_TIED_TO_SIXTEENTH);
		durationToLengthTiedEighth.put(1.0 * divisions, NoteLength.EIGHTH_TIED_TO_EIGHTH);
		durationToLengthTiedEighth.put(1.5 * divisions, NoteLength.EIGHTH_TIED_TO_QUARTER);
		durationToLengthTiedEighth.put(2.5 * divisions, NoteLength.EIGHTH_TIED_TO_HALF);
		durationToLengthTiedEighth.put(4.5 * divisions, NoteLength.EIGHTH_TIED_TO_WHOLE);
		
		durationToLengthTiedEighth.put(8.5 * divisions, NoteLength.EIGHTH_TIED_TO_WHOLE_TIED_TO_WHOLE);
		durationToLengthTiedEighth.put(6.5 * divisions, NoteLength.EIGHTH_TIED_TO_WHOLE_TIED_TO_HALF);
		durationToLengthTiedEighth.put(5.5 * divisions, NoteLength.EIGHTH_TIED_TO_WHOLE_TIED_TO_QUARTER);
		durationToLengthTiedEighth.put(5.0 * divisions, NoteLength.EIGHTH_TIED_TO_WHOLE_TIED_TO_EIGHTH);
		durationToLengthTiedEighth.put(4.75 * divisions, NoteLength.EIGHTH_TIED_TO_WHOLE_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedEighth.put(6.5 * divisions, NoteLength.EIGHTH_TIED_TO_HALF_TIED_TO_WHOLE);
		durationToLengthTiedEighth.put(4.5 * divisions, NoteLength.EIGHTH_TIED_TO_HALF_TIED_TO_HALF);
		durationToLengthTiedEighth.put(3.5 * divisions, NoteLength.EIGHTH_TIED_TO_HALF_TIED_TO_QUARTER);
		durationToLengthTiedEighth.put(3.0 * divisions, NoteLength.EIGHTH_TIED_TO_HALF_TIED_TO_EIGHTH);
		durationToLengthTiedEighth.put(2.75 * divisions, NoteLength.EIGHTH_TIED_TO_HALF_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedEighth.put(5.5 * divisions, NoteLength.EIGHTH_TIED_TO_QUARTER_TIED_TO_WHOLE);
		durationToLengthTiedEighth.put(3.5 * divisions, NoteLength.EIGHTH_TIED_TO_QUARTER_TIED_TO_HALF);
		durationToLengthTiedEighth.put(2.5 * divisions, NoteLength.EIGHTH_TIED_TO_QUARTER_TIED_TO_QUARTER);
		durationToLengthTiedEighth.put(2.0 * divisions, NoteLength.EIGHTH_TIED_TO_QUARTER_TIED_TO_EIGHTH);
		durationToLengthTiedEighth.put(1.75 * divisions, NoteLength.EIGHTH_TIED_TO_QUARTER_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedEighth.put(5.0 * divisions, NoteLength.EIGHTH_TIED_TO_EIGHTH_TIED_TO_WHOLE);
		durationToLengthTiedEighth.put(3.0 * divisions, NoteLength.EIGHTH_TIED_TO_EIGHTH_TIED_TO_HALF);
		durationToLengthTiedEighth.put(2.0 * divisions, NoteLength.EIGHTH_TIED_TO_EIGHTH_TIED_TO_QUARTER);
		durationToLengthTiedEighth.put(1.5 * divisions, NoteLength.EIGHTH_TIED_TO_EIGHTH_TIED_TO_EIGHTH);
		durationToLengthTiedEighth.put(1.25 * divisions, NoteLength.EIGHTH_TIED_TO_EIGHTH_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedEighth.put(4.75 * divisions, NoteLength.EIGHTH_TIED_TO_SIXTEENTH_TIED_TO_WHOLE);
		durationToLengthTiedEighth.put(2.75 * divisions, NoteLength.EIGHTH_TIED_TO_SIXTEENTH_TIED_TO_HALF);
		durationToLengthTiedEighth.put(1.75 * divisions, NoteLength.EIGHTH_TIED_TO_SIXTEENTH_TIED_TO_QUARTER);
		durationToLengthTiedEighth.put(1.25 * divisions, NoteLength.EIGHTH_TIED_TO_SIXTEENTH_TIED_TO_EIGHTH);
		durationToLengthTiedEighth.put(1.0 * divisions, NoteLength.EIGHTH_TIED_TO_SIXTEENTH_TIED_TO_SIXTEENTH);
		
		//
		
		durationToLengthTiedSixteenth.put(0.5 * divisions, NoteLength.SIXTEENTH_TIED_TO_SIXTEENTH);
		durationToLengthTiedSixteenth.put(0.75 * divisions, NoteLength.SIXTEENTH_TIED_TO_EIGHTH);				
		durationToLengthTiedSixteenth.put(1.25 * divisions, NoteLength.SIXTEENTH_TIED_TO_QUARTER);
		durationToLengthTiedSixteenth.put(2.25 * divisions, NoteLength.SIXTEENTH_TIED_TO_HALF);
		durationToLengthTiedSixteenth.put(4.25 * divisions, NoteLength.SIXTEENTH_TIED_TO_WHOLE);
		
		durationToLengthTiedSixteenth.put(8.25 * divisions, NoteLength.SIXTEENTH_TIED_TO_WHOLE_TIED_TO_WHOLE);
		durationToLengthTiedSixteenth.put(6.25 * divisions, NoteLength.SIXTEENTH_TIED_TO_WHOLE_TIED_TO_HALF);
		durationToLengthTiedSixteenth.put(5.25 * divisions, NoteLength.SIXTEENTH_TIED_TO_WHOLE_TIED_TO_QUARTER);
		durationToLengthTiedSixteenth.put(4.75 * divisions, NoteLength.SIXTEENTH_TIED_TO_WHOLE_TIED_TO_EIGHTH);
		durationToLengthTiedSixteenth.put(4.5 * divisions, NoteLength.SIXTEENTH_TIED_TO_WHOLE_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedSixteenth.put(6.25 * divisions, NoteLength.SIXTEENTH_TIED_TO_HALF_TIED_TO_WHOLE);
		durationToLengthTiedSixteenth.put(4.25 * divisions, NoteLength.SIXTEENTH_TIED_TO_HALF_TIED_TO_HALF);
		durationToLengthTiedSixteenth.put(3.25 * divisions, NoteLength.SIXTEENTH_TIED_TO_HALF_TIED_TO_QUARTER);
		durationToLengthTiedSixteenth.put(2.75 * divisions, NoteLength.SIXTEENTH_TIED_TO_HALF_TIED_TO_EIGHTH);
		durationToLengthTiedSixteenth.put(2.5 * divisions, NoteLength.SIXTEENTH_TIED_TO_HALF_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedSixteenth.put(5.25 * divisions, NoteLength.SIXTEENTH_TIED_TO_QUARTER_TIED_TO_WHOLE);
		durationToLengthTiedSixteenth.put(3.25 * divisions, NoteLength.SIXTEENTH_TIED_TO_QUARTER_TIED_TO_HALF);
		durationToLengthTiedSixteenth.put(2.25 * divisions, NoteLength.SIXTEENTH_TIED_TO_QUARTER_TIED_TO_QUARTER);
		durationToLengthTiedSixteenth.put(1.75 * divisions, NoteLength.SIXTEENTH_TIED_TO_QUARTER_TIED_TO_EIGHTH);
		durationToLengthTiedSixteenth.put(1.5 * divisions, NoteLength.SIXTEENTH_TIED_TO_QUARTER_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedSixteenth.put(4.75 * divisions, NoteLength.SIXTEENTH_TIED_TO_EIGHTH_TIED_TO_WHOLE);
		durationToLengthTiedSixteenth.put(2.75 * divisions, NoteLength.SIXTEENTH_TIED_TO_EIGHTH_TIED_TO_HALF);
		durationToLengthTiedSixteenth.put(1.75 * divisions, NoteLength.SIXTEENTH_TIED_TO_EIGHTH_TIED_TO_QUARTER);
		durationToLengthTiedSixteenth.put(1.25 * divisions, NoteLength.SIXTEENTH_TIED_TO_EIGHTH_TIED_TO_EIGHTH);
		durationToLengthTiedSixteenth.put(1.0 * divisions, NoteLength.SIXTEENTH_TIED_TO_EIGHTH_TIED_TO_SIXTEENTH);
		
		durationToLengthTiedSixteenth.put(4.5 * divisions, NoteLength.SIXTEENTH_TIED_TO_SIXTEENTH_TIED_TO_WHOLE);
		durationToLengthTiedSixteenth.put(2.5 * divisions, NoteLength.SIXTEENTH_TIED_TO_SIXTEENTH_TIED_TO_HALF);
		durationToLengthTiedSixteenth.put(1.5 * divisions, NoteLength.SIXTEENTH_TIED_TO_SIXTEENTH_TIED_TO_QUARTER);
		durationToLengthTiedSixteenth.put(1.0 * divisions, NoteLength.SIXTEENTH_TIED_TO_SIXTEENTH_TIED_TO_EIGHTH);
		durationToLengthTiedSixteenth.put(0.75 * divisions, NoteLength.SIXTEENTH_TIED_TO_SIXTEENTH_TIED_TO_SIXTEENTH);
	}
}
