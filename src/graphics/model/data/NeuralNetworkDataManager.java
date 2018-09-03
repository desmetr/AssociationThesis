package graphics.model.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import utilities.GeneralData;;

public class NeuralNetworkDataManager 
{
	private int numberOfFullLines = 0;
	private int numberOfVerticalLines = 0;
	private int numberOfHorizontalLines = 0;
	private int numberOfDiagonalUpLines = 0;
	private int numberOfDiagonalDownLines = 0;
	
	private int numberOfLineSegments = 0;
	private int numberOfVerticalLineSegments = 0;
	private int numberOfHorizontalLineSegments = 0;
	private int numberOfDiagonalUpLineSegments = 0;
	private int numberOfDiagonalDownLineSegments = 0;
	
	private Double entropy = 0.0;
	
	public LinkedHashMap<Integer, Integer> polygonCounts = new LinkedHashMap<Integer, Integer>();
	
	private int numberOfCircles = 0;
	private int numberOfEllipses = 0;
	
	public void increaseNumberOfVerticalLines(int toAdd)            						{ this.numberOfVerticalLines += toAdd;                  						}
	public void increaseNumberOfHorizontalLines(int toAdd)          						{ this.numberOfHorizontalLines += toAdd;               							}
	public void increaseNumberOfDiagonalUpLines(int toAdd)          						{ this.numberOfDiagonalUpLines += toAdd;                						}
	public void increaseNumberOfDiagonalDownLines(int toAdd)        						{ this.numberOfDiagonalDownLines += toAdd;              						}
	public void setNumberOfLineSegments(int numberOfLineSegments)   						{ this.numberOfLineSegments = numberOfLineSegments;								}
	public void setNumberOfVerticalLines(int numberOfVerticalLines) 						{ this.numberOfVerticalLines = numberOfVerticalLines;							}
	public void setNumberOfHorizontalLines(int numberOfHorizontalLines) 					{ this.numberOfHorizontalLines = numberOfHorizontalLines;						}
	public void setNumberOfDiagonalUpLines(int numberOfDiagonalUpLines) 					{ this.numberOfDiagonalUpLines = numberOfDiagonalUpLines;						}
	public void setNumberOfDiagonalDownLines(int numberOfDiagonalDownLines) 				{ this.numberOfDiagonalDownLines = numberOfDiagonalDownLines;					}
	public void setNumberOfVerticalLineSegments(int numberOfVerticalLineSegments) 			{ this.numberOfVerticalLineSegments = numberOfVerticalLineSegments;				}
	public void setNumberOfHorizontalLineSegments(int numberOfHorizontalLineSegments) 		{ this.numberOfHorizontalLineSegments = numberOfHorizontalLineSegments;			}
	public void setNumberOfDiagonalUpLineSegments(int numberOfDiagonalUpLineSegments) 		{ this.numberOfDiagonalUpLineSegments = numberOfDiagonalUpLineSegments;			}
	public void setNumberOfDiagonalDownLineSegments(int numberOfDiagonalDownLineSegments) 	{ this.numberOfDiagonalDownLineSegments = numberOfDiagonalDownLineSegments;		}
	public void increaseNumberOfVerticalLineSegments(int toAdd)     						{ this.numberOfVerticalLineSegments += toAdd;     								}
	public void increaseNumberOfHorizontalLineSegments(int toAdd) 							{ this.numberOfHorizontalLineSegments += toAdd; 								}
	public void increaseNumberOfDiagonalUpLineSegments(int toAdd)							{ this.numberOfDiagonalUpLineSegments += toAdd;     							}
	public void increaseNumberOfDiagonalDownLineSegments(int toAdd)							{ this.numberOfDiagonalDownLineSegments += toAdd;     							}
	public void increaseNumberOfCircles(int toAdd) 											{ this.numberOfCircles += toAdd;												}
	public void setNumberOfEllipses(int numberOfEllipses) 									{ this.numberOfEllipses = numberOfEllipses;										}
	public void setEntropy(Double entropy) 													{ this.entropy = entropy;														}
	
	public void setNumberOfFullLines()         												
	{
		this.numberOfFullLines = numberOfVerticalLines + numberOfHorizontalLines + numberOfDiagonalUpLines + numberOfDiagonalDownLines;
	}
	
	public NeuralNetworkDataManager()
	{
		for (int i = 3; i <= GeneralData.circleThreshold; i++)
			polygonCounts.put(i, 0);
	}
	
	public void reset()
	{
		GeneralData.resetColors();
		polygonCounts.clear();
		
		for (int i = 3; i <= GeneralData.circleThreshold; i++)
			polygonCounts.put(i, 0);
		
		numberOfFullLines = 0;
		numberOfVerticalLines = 0;
		numberOfHorizontalLines = 0;
		numberOfDiagonalUpLines = 0;
		numberOfDiagonalDownLines = 0;
		
		numberOfLineSegments = 0;
		numberOfVerticalLineSegments = 0;
		numberOfHorizontalLineSegments = 0;
		numberOfDiagonalUpLineSegments = 0;
		numberOfDiagonalDownLineSegments = 0;
		
		entropy = 0.0;
		numberOfCircles = 0;
		numberOfEllipses = 0;
	}
	
	public void writeLabelsToFile()
	{	
		StringBuilder vectorStringBuilder = new StringBuilder();
		
		vectorStringBuilder.append("ALL_LINES");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("VERTICAL_LINES");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("HORIZONTAL_LINES");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("DIAGONAL_UP_LINES");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("DIAGONAL_DOWN_LINES");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("LINE_SEGMENTS");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("VERTICAL_LINE_SEGMENTS");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("HORIZONTAL_LINE_SEGMENTS");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("DIAGONAL_UP_LINE_SEGMENTS");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("DIAGONAL_DOWN_LINE_SEGMENTS");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("CIRCLES");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("ELLIPSES");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("TRIANGLES");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("SQUARES");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("PENTAGONS");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("HEXAGONS");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("HEPTAGONS");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("OCTAGONS");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("NONAGONS");
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append("ENTROPY");
		vectorStringBuilder.append(GeneralData.separator);
		
		for (ColorData color : GeneralData.listOfColors)
		{
			vectorStringBuilder.append(color.getName());
			vectorStringBuilder.append(GeneralData.separator);
		}
		
		vectorStringBuilder.append("NAME");
		vectorStringBuilder.append("\n");
		
		try 
		{
			Files.write(Paths.get("results" + File.separator + "vectorsGraphics.csv"), vectorStringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
		} 
		catch (IOException e) {	e.printStackTrace();	}
	}
	
	public void writeValuesToFile()
	{
		StringBuilder vectorStringBuilder = new StringBuilder();
		
		vectorStringBuilder.append(numberOfFullLines);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(numberOfVerticalLines);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(numberOfHorizontalLines);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(numberOfDiagonalUpLines);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(numberOfDiagonalDownLines);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(numberOfLineSegments);	
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(numberOfVerticalLineSegments);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(numberOfHorizontalLineSegments);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(numberOfDiagonalUpLineSegments);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(numberOfDiagonalDownLineSegments);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(numberOfCircles);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(numberOfEllipses);
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(polygonCounts.get(3));
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(polygonCounts.get(4));
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(polygonCounts.get(5));
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(polygonCounts.get(6));
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(polygonCounts.get(7));
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(polygonCounts.get(8));
		vectorStringBuilder.append(GeneralData.separator);
		vectorStringBuilder.append(polygonCounts.get(9));
		vectorStringBuilder.append(GeneralData.separator);		
		vectorStringBuilder.append(GeneralData.decimalFormat(entropy));
		vectorStringBuilder.append(GeneralData.separator);
		
		for (ColorData color : GeneralData.listOfColors)
		{
	        vectorStringBuilder.append(GeneralData.decimalFormat(color.getPercentage()));
	        vectorStringBuilder.append(GeneralData.separator);
		}
		
		vectorStringBuilder.append(GeneralData.currentFileName);
		vectorStringBuilder.append("\n");

		try 
		{
			Files.write(Paths.get("results" + File.separator + "vectorsGraphics.csv"), vectorStringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
		} 
		catch (IOException e) {	e.printStackTrace();	}
	}	
}