package music.model.music;

import java.util.Objects;

import music.model.music.MusicalData.TimeSignatureEnum;

public class TimeSignature 
{
	public int beatUnit; 
	public int numberOfBeats;
	public TimeSignatureEnum timeSignatureAsEnum;
	
	public TimeSignature()
	{
		
	}
	
	public TimeSignature(int beatUnit, int numberOfBeats, TimeSignatureEnum timeSignatureAsEnum)
	{
		this.beatUnit = beatUnit;
		this.numberOfBeats = numberOfBeats;
		this.timeSignatureAsEnum = timeSignatureAsEnum;
	}
	
	public String toString()
	{		
		return "Time signature: " + beatUnit + "/" +  numberOfBeats + "\n";
	}
	
	public void reset()
	{
		beatUnit = 0;
		numberOfBeats = 0;
	}
	
	public boolean equals(Object other) 
	{
	    if (!(other instanceof TimeSignature))
	        return false;

	    TimeSignature that = (TimeSignature) other;

	    return (this.beatUnit == that.beatUnit) && (this.numberOfBeats == that.numberOfBeats);
	}
	
	public int hashCode()
	{
		return Objects.hash(beatUnit, numberOfBeats);
	}
	
	public void calculateFullTimeSignature()
	{
		if (beatUnit == 2 & numberOfBeats == 2)
			timeSignatureAsEnum = TimeSignatureEnum.CUT;
		if (beatUnit == 4 & numberOfBeats == 4)
			timeSignatureAsEnum = TimeSignatureEnum.FOUR_FOUR;
		if (beatUnit == 3 & numberOfBeats == 4)
			timeSignatureAsEnum = TimeSignatureEnum.THREE_FOUR;
		if (beatUnit == 6 & numberOfBeats == 8)
			timeSignatureAsEnum = TimeSignatureEnum.SIX_EIGHT;
		if (beatUnit == 2 & numberOfBeats == 4)
			timeSignatureAsEnum = TimeSignatureEnum.TWO_FOUR;
		if (beatUnit == 12 & numberOfBeats == 8)
			timeSignatureAsEnum = TimeSignatureEnum.TWELVE_EIGHT;
		if (beatUnit == 7 & numberOfBeats == 8)
			timeSignatureAsEnum = TimeSignatureEnum.SEVEN_EIGHT;
		if (beatUnit == 7 & numberOfBeats == 4)
			timeSignatureAsEnum = TimeSignatureEnum.SEVEN_FOUR;
		if (beatUnit == 9 & numberOfBeats == 8)
			timeSignatureAsEnum = TimeSignatureEnum.NINE_EIGHT;
		if (beatUnit == 5 & numberOfBeats == 4)
			timeSignatureAsEnum = TimeSignatureEnum.FIVE_FOUR;
		if (beatUnit == 6 & numberOfBeats == 4)
			timeSignatureAsEnum = TimeSignatureEnum.SIX_FOUR;
	}
}


