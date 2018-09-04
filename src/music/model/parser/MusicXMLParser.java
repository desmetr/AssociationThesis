package music.model.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import music.model.data.Key;
import music.model.data.Measure;
import music.model.data.MusicalData;
import music.model.data.Note;
import music.model.data.Piece;
import music.model.data.TimeSignature;
import music.model.data.MusicalData.Clef;
import music.model.data.MusicalData.Dynamics;
import music.model.data.MusicalData.NoteLength;
import music.model.data.MusicalData.NoteName;
import music.model.data.MusicalData.Scale;

public class MusicXMLParser 
{
	private static Element root;
	private ArrayList<Measure> measures = new ArrayList<Measure>();
	private Piece piece; 
	private Double tieDuration;
	private Note previousNote;
	private Note currentNote;
	private int currentNoteIndex = 0;
	private int previousMeasure = 0;
	private boolean tieStop = false;
	private ArrayList<Integer> startTieNoteIndexes = new ArrayList<Integer>();
	private ArrayList<Double> tieDurations = new ArrayList<Double>();
	
	public MusicXMLParser()
	{
	}
	
	public Piece parse(File selectedFile) throws Exception
	{
		System.out.println(selectedFile.getName());
		piece = new Piece(selectedFile.getName().substring(0, selectedFile.getName().length() - 4));
		
		// Load file.
		File xmlFile = new File(selectedFile.getPath());
		SAXBuilder builder = new SAXBuilder();
		Document doc = (Document) builder.build(xmlFile);
	
		// Get root element.
		root = doc.getRootElement();
		if (!root.getName().equals("score-partwise"))
			throw new Exception("Root tag is not score-partwise.");
	
		Element partElement = root.getChild("part", root.getNamespace());
		
		Piece piece = parseMeasures(partElement);
		return piece;
	}	

	// Parse the measures in a piece.
	private Piece parseMeasures(Element partElement)
	{
		int tempo = 120;	// Default tempo if no tempo is provided in MusicXML.
		NoteLength beatUnit = NoteLength.QUARTER;	// Default beatUnit if no beatUnit is provided in MusicXML.
		int beatsPerMinute = 120; // Default beatsPerMinute if no beatsPerMinute is provided in MusicXML.
		Clef clef = Clef.TREBLE;
		Key key = new Key();
		TimeSignature timeSignature = new TimeSignature();

		double divisions = 0.0;
		boolean anacrusis = false;
		int numberOfStaffs = 1;
		
		Element staffLayoutElement = partElement.getChild("measure", root.getNamespace()).getChild("print", root.getNamespace()).getChild("staff-layout", root.getNamespace());
		if (staffLayoutElement != null)
			numberOfStaffs = Integer.valueOf(staffLayoutElement.getAttributeValue("number"));
		
		List<Element> measureElements = partElement.getChildren("measure", root.getNamespace()); 
		Element firstMeasureElement = measureElements.get(0);
		if (firstMeasureElement.getAttributeValue("number").equals("0") && firstMeasureElement.getAttributeValue("implicit").equals("yes"))
		{
			anacrusis = true;
			piece.anacrusis = anacrusis;
		}
		
		int index = 1;
		for (Element measureElement : measureElements)
		{
			Measure measure = new Measure(index++);
			
			// Parse the direction element.
			Element directionElement = measureElement.getChild("direction", root.getNamespace());
			if (directionElement != null)
			{
				// Directions above the bar are for tempo.
				if (directionElement.getAttributeValue("placement").equals("above"))
				{
					Element metronomeElement = directionElement.getChild("direction-type", root.getNamespace()).getChild("metronome", root.getNamespace());
					if (metronomeElement != null)
					{
						beatUnit = NoteLength.valueOf(metronomeElement.getChildText("beat-unit", root.getNamespace()).toUpperCase());
						beatsPerMinute = Integer.valueOf(metronomeElement.getChildText("per-minute", root.getNamespace()));				
					}
					
					Element soundElement = directionElement.getChild("sound", root.getNamespace());
					if (soundElement != null)
					{
						if (soundElement.getAttribute("tempo") != null)
							if (soundElement.getAttributeValue("tempo").contains("."))
								tempo = (int) Math.round(Double.valueOf(soundElement.getAttributeValue("tempo")));
							else
								tempo = Integer.valueOf(soundElement.getAttributeValue("tempo"));
						if (soundElement.getAttribute("coda") != null)
							piece.numberOfCodas++;
						if (soundElement.getAttribute("tocoda")!= null)
							piece.numberOfToCodas++;
						if (soundElement.getAttribute("segno") != null)
							piece.numberOfSegnos++;
						if (soundElement.getAttribute("dalsegno") != null)
							piece.numberOfDalSegnos++;
					}
				}
			}
			
			// Parse the attributes element.
			Element attributesElement = measureElement.getChild("attributes", root.getNamespace());
			if (attributesElement != null)
			{
				if (attributesElement.getChild("divisions") != null)
				{
					divisions = Double.parseDouble(attributesElement.getChildText("divisions", root.getNamespace()));
					MusicalData.initializeMaps(divisions);
				}
				
				Element keyElement = attributesElement.getChild("key", root.getNamespace());
				if (keyElement != null)
					key = parseKey(keyElement);
				
				Element timeElement = attributesElement.getChild("time", root.getNamespace());
				if (timeElement != null)
					timeSignature = parseTimeSignature(timeElement);
				
				Element clefElement = attributesElement.getChild("clef", root.getNamespace());
				if (clefElement != null)
					clef = parseClef(clefElement);
			}
			
			measure.timeSignature = timeSignature;
			measure.numberOfStaffs = numberOfStaffs;
			measure.dynamics = parseDynamics(measureElement);
			
			ArrayList<Note> notesTemp = parseNotes(measureElement);
			notesTemp = groupChords(notesTemp);
			measure.notes = notesTemp;
			
			// Resolves the issue of a tie between two measures.
            if (tieStop)
            {
                if (previousNote.measureNumber == currentNote.measureNumber)
                {
                    for (int i = 0; i < startTieNoteIndexes.size(); i++)
                    {
                        Integer currentNoteIndex = startTieNoteIndexes.get(i);
                        Double currentTieDuration = tieDurations.get(i);
                         
                        measure.notes.get(currentNoteIndex).duration = currentTieDuration;
                        NoteLength newNoteLength = calculateNoteLengthFirstNoteTie(currentTieDuration, measure.notes.get(currentNoteIndex).length);
                        measure.notes.get(currentNoteIndex).length = newNoteLength;
                    }
 
                    startTieNoteIndexes.clear();
                    tieDurations.clear();
                }
                else
                {
                    measures.get(previousMeasure).getLastNote().duration = tieDuration;
                    NoteLength newNoteLength = calculateNoteLengthFirstNoteTie(tieDuration, measures.get(previousMeasure).getLastNote().length);
                    
                    if (newNoteLength == null)
                    	newNoteLength = NoteLength.LENGTH_OUT_OF_SCOPE;
                    measures.get(previousMeasure).getLastNote().length = newNoteLength;   
                }
                tieStop = false;
            }
			
			measures.add(measure);
			previousMeasure = measures.indexOf(measure);
			currentNoteIndex = 0;
			anacrusis = false;
		}
		
		measures.get(0).anacrusis = piece.anacrusis;
		
		piece.key = key;
		piece.clef = clef;
		piece.beatUnit = beatUnit;
		piece.beatsPerMinute = beatsPerMinute;
		piece.tempo = tempo;
		piece.measures = measures;
		piece.divisions = divisions;
		
		startTieNoteIndexes.clear();
		tieDurations.clear();
		
		return piece;
	}
	
	// Parse all the dynamics in a measure.
	private ArrayList<Dynamics> parseDynamics(Element measureElement)
	{
		ArrayList<Dynamics> dynamics = new ArrayList<Dynamics>();
		for (Element directionElement : measureElement.getChildren("direction", root.getNamespace()))
		{
			if (directionElement != null)
			{
				if (directionElement.getAttributeValue("placement").equals("below"))
				{
					Element dynamicsElement = directionElement.getChild("direction-type", root.getNamespace()).getChild("dynamics", root.getNamespace());
					if (dynamicsElement != null)
					{
						if (!dynamicsElement.getChildren().get(0).getName().equalsIgnoreCase("other-dynamics"))
							dynamics.add(MusicalData.Dynamics.valueOf(dynamicsElement.getChildren().get(0).getName().toUpperCase()));
					}
				}
			}	
		}
		
		return dynamics;
	}
	
	// Parse the notes in a measure.
	private ArrayList<Note> parseNotes(Element measureElement)
	{
		ArrayList<Note> notes = new ArrayList<Note>();
		int measureNumber = Integer.valueOf(measureElement.getAttributeValue("number", root.getNamespace()));
		
		for (Element noteElement : measureElement.getChildren("note", root.getNamespace()))
		{
			Note note = new Note();
			NoteName name = null;
			NoteLength length = null;
			int pitch = 0;
			double duration = 0.0;
			int staff = 1;
			boolean isRest = false;
			boolean hasChord = false;
			boolean startOfTie = false;
			boolean endOfTie = false;
			String defaultX = noteElement.getAttributeValue("default-x");
			
			Element chordElement = noteElement.getChild("chord", root.getNamespace());
			if (chordElement != null)
				hasChord = true;
			
			Element restElement = noteElement.getChild("rest", root.getNamespace());
			if (restElement != null)
				isRest = true;
			
			Element pitchElement = noteElement.getChild("pitch", root.getNamespace());
			if (pitchElement != null)
			{
				name = parseStep(pitchElement);
				pitch = Integer.parseInt(pitchElement.getChildText("octave", root.getNamespace()));			
			}
			
			Element staffElement = noteElement.getChild("staff", root.getNamespace());
			if (staffElement != null)
				staff = Integer.parseInt(staffElement.getValue());
			
			duration = Double.valueOf(noteElement.getChildText("duration", root.getNamespace()));   // If not tied, duration is just the value of the duration element in the xml file.
	        length = parseNoteLength(noteElement, duration);
			
			List<Element> tieElements = noteElement.getChildren("tie", root.getNamespace());
			if (tieElements.size() == 1)
			{
                String type = tieElements.get(0).getAttributeValue("type");
                if (type.equals("start"))
                {
                	startOfTie = true;
                    tieDuration = 0.0;
                    previousNote = note;
                    startTieNoteIndexes.add(currentNoteIndex);
                    length = parseNoteLength(noteElement, duration);    // Temporary noteLength, in this case the actual noteLength will be calculated in the previous method in the tieStop case.
                }
 
                tieDuration += Double.valueOf(noteElement.getChildText("duration", root.getNamespace()));
                 
                // If a tie ends, we can set the correct values in the start note of the tie (see parseMeasures)
                if (type.equals("stop"))
                {
                	currentNote = note;
                	duration = tieDuration;
                    tieDurations.add(tieDuration);
                    length = NoteLength.END_OF_TIE;
                    tieStop = endOfTie = true;
                }
			}
			if (tieElements.size() == 2)
			{
				// First tie is stop by definition, second tie is start by definition.
				tieDuration += Double.valueOf(noteElement.getChildText("duration", root.getNamespace()));
				length = NoteLength.MIDDLE_OF_TIE;
			}    
            
            currentNoteIndex++;
			
			note.name = name;
			note.pitch = pitch;
			note.length = length;
			note.duration = duration;
			note.isRest = isRest;
			note.startOfTie = startOfTie;
			note.endOfTie = endOfTie;
			note.hasChord = hasChord;
			note.defaultX = defaultX;
			note.measureNumber = measureNumber;
			note.noteIndex = currentNoteIndex;
			note.staff = staff;
			notes.add(note);	
		}
		
		return notes;
	}
	
	// Parse the step element.
	private NoteName parseStep(Element pitchElement)
	{
		String step = pitchElement.getChildText("step");
		if (pitchElement.getChild("alter") != null)
		{
			int alter = Integer.valueOf(pitchElement.getChildText("alter"));
			int index = MusicalData.notes.indexOf(NoteName.valueOf(step.toUpperCase()));
			return MusicalData.notes.get(Math.floorMod(index + alter, 12));
		}
		else
			return NoteName.valueOf(step.toUpperCase());
	}
	
	// Calculate the note length.
	private NoteLength parseNoteLength(Element noteElement, double duration)
	{
		String length = noteElement.getChildText("type", root.getNamespace());
		Element timeModElement = noteElement.getChild("time-modification", root.getNamespace());
		Element restElement = noteElement.getChild("rest", root.getNamespace());
		
		// In the case of dotted notes.
		List<Element> dotElements = noteElement.getChildren("dot", root.getNamespace());
		if (dotElements.size() == 1)
		{
			switch (length)
			{
				case "half":
					return NoteLength.HALF_DOTTED;
				case "quarter":
					return NoteLength.QUARTER_DOTTED;
				case "eighth":
					return NoteLength.EIGHTH_DOTTED;
				case "sixteenth":
					return NoteLength.SIXTEENTH_DOTTED;
				default:
					break;
			}
		}
		else if (dotElements.size() == 2)
		{
			switch (length)
			{
				case "half":
					return NoteLength.HALF_DOUBLE_DOTTED;
				case "quarter":
					return NoteLength.QUARTER_DOUBLE_DOTTED;
				case "eighth":
					return NoteLength.EIGHTH_DOUBLE_DOTTED;
				case "sixteenth":
					return NoteLength.SIXTEENTH_DOUBLE_DOTTED;
				default:
					break;
			}
		}
		// In the case of triplets.
		if (timeModElement != null)
		{
			int actualNotes = Integer.valueOf(timeModElement.getChildText("actual-notes", root.getNamespace()));
			int normalNotes = Integer.valueOf(timeModElement.getChildText("normal-notes", root.getNamespace()));
			
			if (actualNotes == 3 && normalNotes == 2)
			{
				switch (length)
				{
					case "quarter":
						return NoteLength.QUARTER_TRIPLET;
					case "eighth":
						return NoteLength.EIGHTH_TRIPLET;
					case "sixteenth":
						return NoteLength.SIXTEENTH_TRIPLET;
					case "half":
						return NoteLength.HALF_TRIPLET;
					default:
						break;
				}
			}
		}
		else if (restElement != null)
			return MusicalData.durationOfRests.get(duration); 
		else
			return NoteLength.valueOf(length.toUpperCase());	
	
		return null;
	}
	
	// Parse the key element.
	private Key parseKey(Element keyElement)
	{
		Key key = new Key();
		
		int fifths = Integer.parseInt(keyElement.getChildText("fifths", root.getNamespace()));
		key.noteName = MusicalData.circleOfFifths.get(fifths);

		String mode = keyElement.getChildText("mode", root.getNamespace());
		if (mode != null)
			key.scale = Scale.valueOf(mode.toUpperCase());
		else
			key.scale = Scale.MAJOR;
		
		return key;
	}
	
	// Parse the clef element.
	private Clef parseClef(Element clefElement)
	{
		String sign = clefElement.getChildText("sign", root.getNamespace());
		int line = Integer.parseInt(clefElement.getChildText("line", root.getNamespace()));
		
		// No tests for this if-statement because we assume Musescore generates the clef element correctly.
		if (sign.equals("G") && line == 2)
			return Clef.TREBLE;
		else if (sign.equals("F") && line == 4)
			return Clef.BASS;
		
		return null;
	}
	
	private TimeSignature parseTimeSignature(Element timeElement)
	{
		TimeSignature timeSignature = new TimeSignature();
		int beats = Integer.parseInt(timeElement.getChildText("beats", root.getNamespace()));
		int beatType = Integer.parseInt(timeElement.getChildText("beat-type", root.getNamespace()));
		
		timeSignature.beatUnit = beats;
		timeSignature.numberOfBeats = beatType;
		timeSignature.calculateFullTimeSignature();
		
		return timeSignature;
	}
	
	private NoteLength calculateNoteLengthFirstNoteTie(Double tieDuration, NoteLength previousNoteLength)
	{		
		switch (previousNoteLength)
		{
			case SIXTEENTH:
				return MusicalData.durationToLengthTiedSixteenth.get(tieDuration);
			case EIGHTH:
				return MusicalData.durationToLengthTiedEighth.get(tieDuration);
			case QUARTER:
				return MusicalData.durationToLengthTiedQuarter.get(tieDuration);
			case HALF:
				return MusicalData.durationToLengthTiedHalf.get(tieDuration);
			case WHOLE:
				return MusicalData.durationToLengthTiedWhole.get(tieDuration);
			default:
				break;
		}
		return previousNoteLength;		
	}
	
	private ArrayList<Note> groupChords(ArrayList<Note> notes)
	{
		int currentChord = 0;
		boolean increase = false;
		Note previousNote = notes.get(0);
		for (int i = 1; i < notes.size(); i++)
		{
			if (!notes.get(i).isRest & !previousNote.isRest)
			{
				if (previousNote.defaultX.equals(notes.get(i).defaultX))
				{
					previousNote.chordNumber = currentChord;
					previousNote.isChord = true;
					notes.get(i).chordNumber = currentChord;
					notes.get(i).isChord = true;
					
					increase = true;
				}
				if ((!notes.get(i).hasChord) & increase)
				{
					currentChord++;
					increase = false;
				}
			}
			
			previousNote = notes.get(i);
		}
		
		return notes;
	}
	
	public void reset()
	{
		this.piece.reset();
		this.piece = null;
	}
}
