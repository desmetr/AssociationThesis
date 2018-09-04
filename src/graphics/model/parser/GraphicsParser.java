package graphics.model.parser;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

//import boofcv.Core;
import boofcv.abst.denoise.FactoryImageDenoise;
import boofcv.abst.denoise.WaveletDenoiseFilter;
import boofcv.abst.feature.detect.line.DetectLineHoughFoot;
import boofcv.abst.feature.detect.line.DetectLineHoughFootSubimage;
import boofcv.abst.feature.detect.line.DetectLineHoughPolar;
import boofcv.abst.feature.detect.line.DetectLineSegmentsGridRansac;
import boofcv.alg.color.ColorHsv;
import boofcv.alg.feature.detect.edge.CannyEdge;
import boofcv.alg.feature.detect.edge.EdgeContour;
import boofcv.alg.feature.detect.edge.EdgeSegment;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.Contour;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.shapes.FitData;
import boofcv.alg.shapes.ShapeFittingOps;
import boofcv.alg.shapes.polygon.DetectPolygonBinaryGrayRefine;
import boofcv.factory.feature.detect.edge.FactoryEdgeDetectors;
import boofcv.factory.feature.detect.line.ConfigHoughFoot;
import boofcv.factory.feature.detect.line.ConfigHoughFootSubimage;
import boofcv.factory.feature.detect.line.ConfigHoughPolar;
import boofcv.factory.feature.detect.line.FactoryDetectLineAlgs;
import boofcv.factory.shape.ConfigPolygonDetector;
import boofcv.factory.shape.FactoryShapeDetector;
import boofcv.gui.binary.VisualizeBinaryData;
import boofcv.gui.feature.VisualizeShapes;
import boofcv.io.UtilIO;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.ConnectRule;
import boofcv.struct.PointIndex_I32;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayS16;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.Planar;
import georegression.metric.UtilAngle;
import georegression.struct.line.LineParametric2D_F32;
import georegression.struct.line.LineSegment2D_F32;
import georegression.struct.shapes.EllipseRotated_F64;
import georegression.struct.shapes.Polygon2D_F64;
import graphics.model.data.ColorData;
import graphics.model.data.GraphicsDataManager;
import utilities.GeneralData;
import utilities.GeneralData.Hough;
import utilities.Utilities;

/*
 * list of algorithms to parse images
 * 	- preprocessing
 * 		* noise removal
 * 		* entropy calculation
 *  - processing
 *  	* edge detection
 *  	* shape recognition
 *  	* color analysis (= image segmentation)
 */

public class GraphicsParser
{
	private GraphicsDataManager dataManager = new GraphicsDataManager();

	private int totalNumberOfPixels;
	
	private BufferedImage imageNoiseRemoved;
	private BufferedImage imageLinesDetected;
	private BufferedImage imageLineSegmentsDetected;
	private BufferedImage imageVerticalLines;
	private BufferedImage imageHorizontalLines;
	private BufferedImage imageColorSegmentationBlack;
	private BufferedImage imageColorSegmentationWhite;
	private BufferedImage imageColorSegmentationRed;
	private BufferedImage imageColorSegmentationLime;
	private BufferedImage imageColorSegmentationBlue;
	private BufferedImage imageColorSegmentationYellow;
	private BufferedImage imageColorSegmentationCyan;
	private BufferedImage imageColorSegmentationMagenta;
	private BufferedImage imageColorSegmentationGreen;
	private BufferedImage imageColorSegmentationPurple;
	private BufferedImage imageEllipseDetection;
	private BufferedImage imageCannyContour;
	private BufferedImage imageBinaryEdges;
	private BufferedImage imageCannyTrace;
	private BufferedImage imagePositivePolygonDetection;
	private BufferedImage imageNegativePolygonDetection;
	
	private BufferedImage image;
	
	private final float Q1Begin = 0.0f;
	private final float Q1End = 1.5707963f;
	private final float Q2End = round(Math.PI);
	private final float Q3End = 4.7123890f;
	private final float Q4End = 6.2831853f;
	private final float minusQ4Begin = 0.0f;
	private final float minusQ3Begin = -1.5707963f;
	private final float minusQ2Begin = -3.1415927f;
	private final float minusQ1Begin = -4.7123890f;
	
	public BufferedImage getImageNoiseRemoved() 			{	return imageNoiseRemoved;				}
	public BufferedImage getImageLinesDetected() 			{	return imageLinesDetected;				}
	public BufferedImage getImageVerticalLines() 			{	return imageVerticalLines;				}
	public BufferedImage getImageHorizontalLines() 			{	return imageHorizontalLines;			}
	public BufferedImage getImageLineSegmentsDetected() 	{	return imageLineSegmentsDetected;		}
	public BufferedImage getImageColorSegmentationBlack() 	{	return imageColorSegmentationBlack;		}
	public BufferedImage getImageColorSegmentationWhite() 	{	return imageColorSegmentationWhite;		}
	public BufferedImage getImageColorSegmentationRed() 	{	return imageColorSegmentationRed;		}
	public BufferedImage getImageColorSegmentationLime() 	{	return imageColorSegmentationLime;		}
	public BufferedImage getImageColorSegmentationBlue() 	{	return imageColorSegmentationBlue;		}
	public BufferedImage getImageColorSegmentationYellow() 	{	return imageColorSegmentationYellow;	}
	public BufferedImage getImageColorSegmentationCyan() 	{	return imageColorSegmentationCyan;		}
	public BufferedImage getImageColorSegmentationMagenta() {	return imageColorSegmentationMagenta;	}
	public BufferedImage getImageColorSegmentationGreen() 	{	return imageColorSegmentationGreen;		}
	public BufferedImage getImageColorSegmentationPurple() 	{	return imageColorSegmentationPurple;	}
	public int getTotalNumberOfPixels()		 				{	return totalNumberOfPixels;				}
	public BufferedImage getImageEllipseDetetcion() 		{	return imageEllipseDetection;			}
	public BufferedImage getImageCannyContour() 			{	return imageCannyContour;				}
	public BufferedImage getImageBinaryEdges() 				{	return imageBinaryEdges;				}
	public BufferedImage getImageCannyTrace() 				{	return imageCannyTrace;					}
	public BufferedImage getImagePositivePolygonDetection() {	return imagePositivePolygonDetection;	}
	public BufferedImage getImageNegativePolygonDetection() {	return imageNegativePolygonDetection;	}
	public GraphicsDataManager getNetworkDataManager() {	return dataManager;						}
	
	public GraphicsParser(GraphicsDataManager dataManager)
	{
		this.dataManager = dataManager;
	}
	
	public void parseImage(BufferedImage inputImage)
	{
		image = inputImage;
		removeNoise();
		
		// Calculate mean of three line detection algorithms for vertical, horizontal, diagonal up and diagonal down lines + graphical feedback.
		ArrayList<Integer> footSubImageResults = detectLines(GrayF32.class, GrayF32.class, Hough.FOOT_SUBIMAGE);
		ArrayList<Integer> footResults = detectLines(GrayF32.class, GrayF32.class, Hough.FOOT);
		ArrayList<Integer> polarResults = detectLines(GrayF32.class, GrayF32.class, Hough.POLAR);
					
		double meanVerticalLines = (footSubImageResults.get(0) + footResults.get(0) + polarResults.get(0)) / 3;
		dataManager.setNumberOfVerticalLines((int) Math.floor(meanVerticalLines));
		
		double meanHorizontalLines = (footSubImageResults.get(1) + footResults.get(1) + polarResults.get(1)) / 3;
		dataManager.setNumberOfHorizontalLines((int) Math.floor(meanHorizontalLines));
		
		double meanDiagonalUpLines = (footSubImageResults.get(2) + footResults.get(2) + polarResults.get(2)) / 3;
		dataManager.setNumberOfDiagonalUpLines((int) Math.floor(meanDiagonalUpLines));
		
		double meanDiagonalDownLines = (footSubImageResults.get(3) + footResults.get(3) + polarResults.get(3)) / 3;
		dataManager.setNumberOfDiagonalDownLines((int) Math.floor(meanDiagonalDownLines));
		
		dataManager.setNumberOfFullLines();
		
		// Compute number of vertical, horizontal, diagonal up and diagonal down line segments + graphical feedback.
		dataManager.setNumberOfLineSegments(detectLineSegments(GrayF32.class, GrayF32.class));
		
		// Compute percentage of all 148 colors + graphical feedback.
		for (ColorData currentColor : GeneralData.listOfColors)
		{
			currentColor.setPercentage(calculatePercentageSelectedColor(currentColor.getHex()));
			
			if (currentColor.getName().equals("black"))
				imageColorSegmentationBlack = showSelectedColor(currentColor.getHex());
			if (currentColor.getName().equals("white")) 
				imageColorSegmentationWhite = showSelectedColor(currentColor.getHex());
			if (currentColor.getName().equals("red")) 
				imageColorSegmentationRed = showSelectedColor(currentColor.getHex());
			if (currentColor.getName().equals("lime"))
				imageColorSegmentationLime = showSelectedColor(currentColor.getHex());
            if (currentColor.getName().equals("blue")) 
            	imageColorSegmentationBlue = showSelectedColor(currentColor.getHex());
            if (currentColor.getName().equals("yellow")) 
            	imageColorSegmentationYellow = showSelectedColor(currentColor.getHex());
            if (currentColor.getName().equals("cyan")) 
            	imageColorSegmentationCyan = showSelectedColor(currentColor.getHex());
            if (currentColor.getName().equals("magenta"))
            	imageColorSegmentationMagenta = showSelectedColor(currentColor.getHex());
            if (currentColor.getName().equals("green"))
            	imageColorSegmentationGreen = showSelectedColor(currentColor.getHex());
            if (currentColor.getName().equals("purple"))
            	imageColorSegmentationPurple = showSelectedColor(currentColor.getHex());
		}
		
		// Only for graphical feedback.
		fitCannyEdges();
		edgeDetection();

		// Compute number of ellipses + graphical feedback.
		dataManager.setNumberOfEllipses(fitEllipses());

		// Compute number of polygons + graphical feedback.
		imagePositivePolygonDetection = Utilities.deepCopy(image);
		detectPolygons(imagePositivePolygonDetection, true);
		
		imageNegativePolygonDetection = convertImageToNegative(Utilities.deepCopy(image));
		detectPolygons(imageNegativePolygonDetection, false);
		
		getShannonEntropy(image);
	}
	
	// Noise removal.
	// https://github.com/lessthanoptimal/BoofCV/blob/v0.23/examples/src/boofcv/examples/enhance/ExampleWaveletDenoise.java
	private void removeNoise()
	{
		GrayF32 inputGrayF32Image = UtilImageIO.loadImage(UtilIO.pathExample(GeneralData.currentFilePath),GrayF32.class);
		imageNoiseRemoved = Utilities.deepCopy(image);

		GrayF32 outputGrayF32Image = inputGrayF32Image.createSameShape();

		// How many levels in wavelet transform?
		int numLevels = 4;
		
		// Create the noise removal algorithm.
		WaveletDenoiseFilter<GrayF32> denoiser = FactoryImageDenoise.waveletBayes(GrayF32.class, numLevels, 0, 255);

		// Remove noise from the image.
		denoiser.process(inputGrayF32Image, outputGrayF32Image);
		
		ConvertBufferedImage.convertTo(outputGrayF32Image, imageNoiseRemoved);
		
		Graphics g = imageNoiseRemoved.createGraphics();
		g.drawImage(imageNoiseRemoved, 0, 0, null);
		g.dispose();
	}
	
	// Line detection.
	// https://github.com/lessthanoptimal/BoofCV/blob/v0.27/examples/src/boofcv/examples/features/ExampleLineDetection.java
	public <T extends ImageGray<T>, D extends ImageGray<D>> 
	ArrayList<Integer> detectLines(Class<T> imageType, Class<D> derivType, Hough detectType)
	{
		// Convert the line into a single band image.
		T input = ConvertBufferedImage.convertFromSingle(image, null, imageType);

		List<LineParametric2D_F32> found = null;
		
		// Comment/uncomment to try a different type of line detector
		switch (detectType)
		{
			case FOOT:
				DetectLineHoughFoot<T,D> detectorFoot = FactoryDetectLineAlgs.houghFoot(new ConfigHoughFoot(3, 8, 5, GeneralData.edgeThreshold, GeneralData.maxLines), imageType, derivType);
				found = detectorFoot.detect(input);
				break;
			case FOOT_SUBIMAGE:
				DetectLineHoughFootSubimage<T,D> detectorFootSubImage = FactoryDetectLineAlgs.houghFootSub(new ConfigHoughFootSubimage(3, 8, 5, GeneralData.edgeThreshold, GeneralData.maxLines, 2, 2), imageType, derivType);
				found = detectorFootSubImage.detect(input);
				break;
			case POLAR:
				DetectLineHoughPolar<T,D> detectorPolar = FactoryDetectLineAlgs.houghPolar(new ConfigHoughPolar(3, 30, 2, Math.PI / 180, GeneralData.edgeThreshold, GeneralData.maxLines), imageType, derivType);
				found = detectorPolar.detect(input);
				break;
			default:
				break;
		}
		
		imageLinesDetected = Utilities.deepCopy(image);

		Graphics2D g2 = imageLinesDetected.createGraphics();
		
		float error = 0.0000005f;
		
		Integer numberOfVerticalLines = 0;
		Integer numberOfHorizontalLines = 0;
		Integer numberOfDiagonalUpLines = 0;
		Integer numberOfDiagonalDownLines = 0;
		
		for (LineParametric2D_F32 line : found)
		{	
			float angle = line.getAngle();

			// Vertical lines
			if (inRangeWithError(angle, Q1End, error) || inRangeWithError(angle, Q3End, error) || inRangeWithError(angle, minusQ3Begin, error) || inRangeWithError(angle, minusQ1Begin, error))
			{
				drawLine(line, g2, Color.RED);
				dataManager.increaseNumberOfVerticalLines(1);
				continue;
			}
			// Horizontal lines
			if (inRangeWithError(angle, Q1Begin, error) || inRangeWithError(angle, Q2End, error) || inRangeWithError(angle, Q4End, error) || inRangeWithError(angle, minusQ2Begin, error) || inRangeWithError(angle, minusQ4Begin, error))
			{
				drawLine(line, g2, Color.BLUE);
				dataManager.increaseNumberOfHorizontalLines(1);
				continue;
			}
			// Diagonal up lines
			if (inRange(angle, Q1Begin, Q1End) || inRange(angle, Q2End, Q3End))
			{
				drawLine(line, g2, Color.GREEN);
				dataManager.increaseNumberOfDiagonalUpLines(1);
				continue;
			}
			// Diagonal down lines
			if (inRange(angle, Q1End, Q2End) || inRange(angle, Q3End, Q4End))
			{
				drawLine(line, g2, Color.MAGENTA);
				dataManager.increaseNumberOfDiagonalDownLines(1);
				continue;
			}
		}
		
		ArrayList<Integer> results = new ArrayList<Integer>();
		results.add(numberOfVerticalLines);
		results.add(numberOfHorizontalLines);
		results.add(numberOfDiagonalUpLines);
		results.add(numberOfDiagonalDownLines);
		return results;
	}
	
	// Line detection
	// https://github.com/lessthanoptimal/BoofCV/blob/v0.27/examples/src/boofcv/examples/features/ExampleLineDetection.java
	public <T extends ImageGray<T>, D extends ImageGray<D>>
	int detectLineSegments(Class<T> imageType, Class<D> derivType)
	{
		// convert the line into a single band image
		T input = ConvertBufferedImage.convertFromSingle(image, null, imageType);

		DetectLineSegmentsGridRansac<T,D> detector = FactoryDetectLineAlgs.lineRansac(40, 30, 2.36, true, imageType, derivType);

		List<LineSegment2D_F32> found = detector.detect(input);

		imageLineSegmentsDetected = Utilities.deepCopy(image);
		Graphics2D g2 = imageLineSegmentsDetected.createGraphics();
		
		float error = 0.05f;
		for (LineSegment2D_F32 line : found)
		{
			float slope = line.slopeY() / line.slopeX();
		
			// Vertical line
			if (slope == Double.POSITIVE_INFINITY || slope == Double.NEGATIVE_INFINITY)
			{
				drawLineSegment(line, g2, Color.RED);
				dataManager.increaseNumberOfVerticalLineSegments(1);
				continue;
			}
			// Horizontal line
			if (inRangeWithError(slope, 0.0f, error))
			{
				drawLineSegment(line, g2, Color.BLUE);
				dataManager.increaseNumberOfHorizontalLineSegments(1);
				continue;
			}
			// Diagonal up line
			if (slope > error && slope != Double.POSITIVE_INFINITY && slope != Double.NEGATIVE_INFINITY)
			{
				drawLineSegment(line, g2, Color.GREEN);
				dataManager.increaseNumberOfDiagonalUpLineSegments(1);
				continue;
			}
			// Diagonal down line
			if (slope < -error && slope != Double.POSITIVE_INFINITY && slope != Double.NEGATIVE_INFINITY)
			{
				drawLineSegment(line, g2, Color.MAGENTA);
				dataManager.increaseNumberOfDiagonalDownLineSegments(1);
				continue;
			}
		}
		
		return found.size();
	}
	
//	// Line detection.
//	// http://cartucho.github.io/tutorial_morph_lines_detection.html
//	// Uses OpenCV library.
//	private void detectVerticalAndHorizontalLines()
//	{
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//
//		Mat src = Imgcodecs.imread(GeneralData.currentFilePath);
//		Mat gray = new Mat();
//		
//		// Transform source image to gray if it is not
//		if (src.channels() == 3)
//            Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
//        else
//            gray = src;
//		
//		// Apply adaptiveThreshold at the bitwise_not of gray
//		Mat bw = new Mat();
//        Core.bitwise_not(gray, gray);
//        Imgproc.adaptiveThreshold(gray, bw, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, -2);
//
//        // Create the images that will use to extract the horizontal and vertical lines
//        Mat horizontal = bw.clone();
//        Mat vertical = bw.clone();
//        
//        // Specify size on horizontal axis
//        int horizontalsize = horizontal.cols() / 30;
//        // Create structure element for extracting horizontal lines through morphology operations
//        Mat horizontalStructure = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(horizontalsize, 1));
//        // Apply morphology operations
//        Imgproc.erode(horizontal, horizontal, horizontalStructure);
//        Imgproc.dilate(horizontal, horizontal, horizontalStructure);
//        
//        // Specify size on vertical axis
//        int verticalsize = vertical.rows() / 30;
//        // Create structure element for extracting vertical lines through morphology operations
//        Mat verticalStructure = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size( 1,verticalsize));
//        // Apply morphology operations
//        Imgproc.erode(vertical, vertical, verticalStructure);
//        Imgproc.dilate(vertical, vertical, verticalStructure);
//        Core.bitwise_not(vertical, vertical);
//
//        
//        // Extract vertical edges and smooth image according to the logic
//        // 1. extract edges
//        // 2. dilate(edges)
//        // 3. src.copyTo(smooth)
//        // 4. blur smooth img
//        // 5. smooth.copyTo(src, edges)
//        // Step 1
//        Mat edgesVertical = new Mat();
//        Imgproc.adaptiveThreshold(vertical, edgesVertical, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 3, -2);
//        // Step 2	
//        Mat kernelVertical = Mat.ones(2, 2, CvType.CV_8UC1);
//        Imgproc.dilate(edgesVertical, edgesVertical, kernelVertical);
//        // Step 3
//        Mat smoothVertical = new Mat();
//        vertical.copyTo(smoothVertical);
//        // Step 4
//        Imgproc.blur(smoothVertical, smoothVertical, new Size(2, 2));
//        // Step 5
//        smoothVertical.copyTo(vertical, edgesVertical);
//        
//        imageVerticalLines = matToBufferedImage(vertical);
//        
//        // Extract horizontal edges and smooth image according to the logic
//        // Step 1
//        Mat edgesHorizontal = new Mat();
//        Imgproc.adaptiveThreshold(horizontal, edgesHorizontal, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 3, -2);
//        // Step 2	
//        Mat kernel = Mat.ones(2, 2, CvType.CV_8UC1);
//        Imgproc.dilate(edgesHorizontal, edgesHorizontal, kernel);
//        // Step 3
//        Mat smoothHorizontal = new Mat();
//        vertical.copyTo(smoothHorizontal);
//        // Step 4
//        Imgproc.blur(smoothHorizontal, smoothHorizontal, new Size(2, 2));
//        // Step 5
//        smoothHorizontal.copyTo(horizontal, edgesHorizontal);
//        
//        imageHorizontalLines = matToBufferedImage(horizontal);
//        
//        ////////////
//        Mat dst = new Mat();
//        
//        // Edge detection
//        Imgproc.Canny(src, dst, 50, 200, 3, false);
//        
//        Mat lines = new Mat(); // will hold the results of the detection
//        Imgproc.HoughLines(dst, lines, 1, Math.PI/180, 150); // runs the actual detection
//	}
//	
//	private BufferedImage matToBufferedImage(Mat matrix)
//	{   
//		try 
//		{
//		    MatOfByte mob = new MatOfByte();
//		    Imgcodecs.imencode(".jpg", matrix, mob);
//		    byte ba[] = mob.toArray();
//	
//		    BufferedImage bi = ImageIO.read(new ByteArrayInputStream(ba));
//		    
//		    return bi;
//		} 
//		catch (IOException e) {	e.printStackTrace();}
//		return null;	    
//	}

	// Color segmentation
	// https://github.com/lessthanoptimal/BoofCV/blob/v0.23/examples/src/boofcv/examples/segmentation/ExampleSegmentColor.java
	public double calculatePercentageSelectedColor(int targetColorHex) 
	{
		Planar<GrayF32> input = ConvertBufferedImage.convertFromPlanar(image, null, true, GrayF32.class);
		Planar<GrayF32> hsv = input.createSameShape();
		BufferedImage tempResult = new BufferedImage(input.width, input.height, BufferedImage.TYPE_INT_ARGB);

		// Convert into HSV
		ColorHsv.rgbToHsv_F32(input, hsv);
		
		// Convert hex into HSV
		float[] targetHSV = Utilities.convertHexToHSV(targetColorHex);
		float targetHue = targetHSV[0];
		float targetSaturation = targetHSV[1];

		// Convert hex into RGB
		int[] targetRGB = Utilities.convertHexToRGB(targetColorHex);

		// Euclidean distance squared threshold for deciding which pixels are members of the selected set
		float maxDist2 = 0.4f * 0.4f;

		// Extract hue and saturation bands which are independent of intensity
		GrayF32 H = hsv.getBand(0);
		GrayF32 S = hsv.getBand(1);

		// Adjust the relative importance of Hue and Saturation.
		// Hue has a range of 0 to 2*PI and Saturation from 0 to 1.
		float adjustUnits = (float)(Math.PI / 2.0);

		int numberOfColoredPixels = 0;
		
		// step through each pixel and mark how close it is to the selected color		
		for (int y = 0; y < hsv.height; y++) 
		{
			for (int x = 0; x < hsv.width; x++)
			{
				float distance = 0.0f;
				// Distinguish between black/white and all the other colors.
				if (targetColorHex == 0x000000 | targetColorHex == 0xFFFFFF)
				{
					int[] currentRGB = Utilities.convertHexToRGB(image.getRGB(x, y));
					distance = (float) Math.sqrt(Math.pow(currentRGB[0] - targetRGB[0], 2) + Math.pow(currentRGB[1] - targetRGB[1], 2) + Math.pow(currentRGB[2] - targetRGB[2], 2));
				}
				else
				{
					// Hue is an angle in radians, so simple subtraction doesn't work
					float dH = UtilAngle.dist(H.unsafe_get(x, y), targetHue);
					float dS = ((S.unsafe_get(x, y) - targetSaturation) * adjustUnits);
					
					// this distance measure is a bit naive, but good enough for to demonstrate the concept
					distance = (float) Math.pow(dH * dH + dS * dS, 2);
				}
				
				if (distance <= maxDist2) 
				{
					numberOfColoredPixels++;
					tempResult.setRGB(x, y, image.getRGB(x, y));
				}
			}
		}
		
		totalNumberOfPixels = hsv.height * hsv.width;
		return (double) numberOfColoredPixels / totalNumberOfPixels;
	}

	// Color segmentation.
	// https://github.com/lessthanoptimal/BoofCV/blob/v0.23/examples/src/boofcv/examples/segmentation/ExampleSegmentColor.java
	public BufferedImage showSelectedColor(int targetColorHex) 
	{
		Planar<GrayF32> input = ConvertBufferedImage.convertFromPlanar(image, null, true, GrayF32.class);
		Planar<GrayF32> hsv = input.createSameShape();
		BufferedImage tempResult = new BufferedImage(input.width, input.height, BufferedImage.TYPE_INT_ARGB);

		// Convert inputimage into HSV
		ColorHsv.rgbToHsv_F32(input, hsv);
		
		// Convert hex into HSV
		float[] targetHSV = Utilities.convertHexToHSV(targetColorHex);
		float targetHue = targetHSV[0];
		float targetSaturation = targetHSV[1];

		// Convert hex into RGB
		int[] targetRGB = Utilities.convertHexToRGB(targetColorHex);
		
		// Euclidean distance squared threshold for deciding which pixels are members of the selected set
		float maxDist2 = 0.4f * 0.4f; 

		// Extract hue and saturation bands which are independent of intensity
		GrayF32 H = hsv.getBand(0);
		GrayF32 S = hsv.getBand(1);

		// Adjust the relative importance of Hue and Saturation.
		// Hue has a range of 0 to 2*PI and Saturation from 0 to 1.
		float adjustUnits = (float)(Math.PI / 2.0);
		
		// step through each pixel and mark how close it is to the selected color		
		for (int y = 0; y < hsv.height; y++) 
		{
			for (int x = 0; x < hsv.width; x++)
			{				
				float distance = 0.0f;
				
				// Distinguish between black/white and all the other colors.
				if (targetColorHex == 0x000000 | targetColorHex == 0xFFFFFF)
				{
					int[] currentRGB = Utilities.convertHexToRGB(image.getRGB(x, y));
					distance = (float) Math.sqrt(Math.pow(currentRGB[0] - targetRGB[0], 2) + Math.pow(currentRGB[1] - targetRGB[1], 2) + Math.pow(currentRGB[2] - targetRGB[2], 2));
				}
				else
				{
					// Hue is an angle in radians, so simple subtraction doesn't work
					float dH = UtilAngle.dist(H.unsafe_get(x, y), targetHue);
					float dS = ((S.unsafe_get(x, y) - targetSaturation) * adjustUnits);
					
					// this distance measure is a bit naive, but good enough for to demonstrate the concept
					distance = (float) Math.pow(dH * dH + dS * dS, 2);
				}
				
				if (distance <= maxDist2) 
					tempResult.setRGB(x, y, image.getRGB(x, y));
			}
		}
	
		// Now we have the results converted to a BufferedImage.
		BufferedImage output = new BufferedImage(GeneralData.screenWidth, GeneralData.innerTabHeight, BufferedImage.TYPE_INT_ARGB);

		Graphics g = output.createGraphics();
		g.drawImage(tempResult, 0, 0, GeneralData.screenWidth, GeneralData.innerTabHeight, null);
		g.dispose();
		
		return output;
	}

	// Polygon detection.
	// https://github.com/lessthanoptimal/BoofCV/blob/v0.28/examples/src/main/java/boofcv/examples/features/ExampleFitPolygon.java
	public void fitCannyEdges() 
	{
		GrayF32 input = ConvertBufferedImage.convertFromSingle(image, null, GrayF32.class);
		imageCannyTrace = new BufferedImage(input.width, input.height, BufferedImage.TYPE_INT_RGB);

		// Finds edges inside the image
		CannyEdge<GrayF32,GrayF32> canny = FactoryEdgeDetectors.canny(2, true, true, GrayF32.class, GrayF32.class);

		canny.process(input, 0.1f, 0.3f, null);
		List<EdgeContour> contours = canny.getContours();

		Graphics2D g2 = imageCannyTrace.createGraphics();
		g2.setStroke(new BasicStroke(2));

		// used to select colors for each line
		Random rand = new Random(234);

		for (EdgeContour e : contours) 
		{
			g2.setColor(new Color(rand.nextInt()));

			for (EdgeSegment s : e.segments) 
			{
				// fit line segments to the point sequence.  Note that loop is false
				List<PointIndex_I32> vertexes = ShapeFittingOps.fitPolygon(s.points, false, GeneralData.splitFraction, GeneralData.minimumSide, 100);
				
				VisualizeShapes.drawPolygon(vertexes, false, g2);
			}
		}
	}

	// Ellipse detection.
	// https://github.com/lessthanoptimal/BoofCV/blob/v0.23/examples/src/boofcv/examples/features/ExampleFitEllipse.java
	private int fitEllipses()
	{
		imageEllipseDetection = Utilities.deepCopy(image);
		GrayF32 input = ConvertBufferedImage.convertFromSingle(imageEllipseDetection, null, GrayF32.class);
		GrayU8 binary = new GrayU8(input.width,input.height);

		// the mean pixel value is often a reasonable threshold when creating a binary image
		double mean = ImageStatistics.mean(input);

		// create a binary image by thresholding
		ThresholdImageOps.threshold(input, binary, (float) mean, true);

		// reduce noise with some filtering
		GrayU8 filtered = BinaryImageOps.erode8(binary, 1, null);
		filtered = BinaryImageOps.dilate8(filtered, 1, null);

		// Find the contour around the shapes
		List<Contour> contours = BinaryImageOps.contour(filtered, ConnectRule.EIGHT, null);

		// Fit an ellipse to each external contour and draw the results
		Graphics2D g2 = imageEllipseDetection.createGraphics();
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.RED);

		for (Contour c : contours) 
		{
			FitData<EllipseRotated_F64> ellipse = ShapeFittingOps.fitEllipse_I32(c.external, 0, false, null);
			VisualizeShapes.drawEllipse(ellipse.shape, g2);
		}
		
		return contours.size() - 1;
	}
	
	// Polygon detection.
	private void detectPolygons(BufferedImage polygonImage, boolean count)
	{
		// first configure the detector to only detect convex shapes with 3 to 7 sides
		ConfigPolygonDetector config = new ConfigPolygonDetector(true, 3, GeneralData.maxNumberOfSides);
		DetectPolygonBinaryGrayRefine<GrayU8> detector = FactoryShapeDetector.polygon(config, GrayU8.class);

		GrayU8 input = ConvertBufferedImage.convertFromSingle(polygonImage, null, GrayU8.class);
		GrayU8 binary = new GrayU8(input.width, input.height);

		// Binarization is done outside to allows creative tricks.  For example, when applied to a chessboard
		// pattern where square touch each other, the binary image is eroded first so that they don't touch.
		// The squares are expanded automatically during the subpixel optimization step.
		int threshold = GThresholdImageOps.computeOtsu(input, 0, 255);
		ThresholdImageOps.threshold(input, binary, threshold, true);

		// it takes in a grey scale image and binary image
		// the binary image is used to do a crude polygon fit, then the grey image is used to refine the lines using a sub-pixel algorithm
		detector.process(input, binary);
		
		java.util.List<Polygon2D_F64> polygons = detector.getPolygons(null,null);	
		
		for (int i = 0; i < polygons.size(); i++)
		{
			ArrayList<Double> sideLengths = new ArrayList<Double>();
			ArrayList<Double> normalizedSideLengths = new ArrayList<Double>();
			for (int j = 0; j < polygons.get(i).size(); j++)
				sideLengths.add(polygons.get(i).getSideLength(j));
			
			Double max = Collections.max(sideLengths);
			Double min = Collections.min(sideLengths);
			
			// Normalize.
			for (Double sideLength : sideLengths)
				normalizedSideLengths.add((sideLength - min) / (max - min));
			
			for (int j = 0; j < normalizedSideLengths.size(); j++)
			{
				if (normalizedSideLengths.get(j) <= 0.1 & normalizedSideLengths.get(j) != 0.0)
					polygons.get(i).vertexes.remove(j);
			}
		}
		
		// visualize results by drawing red polygons
		Graphics2D g2 = polygonImage.createGraphics();
		g2.setStroke(new BasicStroke(3));
	
		for (int i = 0; i < polygons.size(); i++) 
		{
			g2.setColor(Color.RED);
			VisualizeShapes.drawPolygon(polygons.get(i), true, g2, true);
			g2.setColor(Color.CYAN);
			VisualizeShapes.drawPolygonCorners(polygons.get(i), 2, g2, true);
		
			if (count)
			{
				int numberOfSides = polygons.get(i).vertexes.size;
				if (numberOfSides > GeneralData.circleThreshold)
				{
					dataManager.increaseNumberOfCircles(1);
				}
				else if (numberOfSides >= 3)	// Polygons of size 2 or 1 don't exist.
				{
					int currentCount = dataManager.polygonCounts.get(numberOfSides);
					dataManager.polygonCounts.put(numberOfSides, currentCount + 1);	
				}
			}
		}
	}
	
	private void edgeDetection()
	{
		BufferedImage displayImage = Utilities.deepCopy(image);
		GrayU8 gray = ConvertBufferedImage.convertFrom(displayImage,(GrayU8)null);
		GrayU8 edgeImage = gray.createSameShape();

		// Create a canny edge detector which will dynamically compute the threshold based on maximum edge intensity
		// It has also been configured to save the trace as a graph.  This is the graph created while performing
		// hysteresis thresholding.
		CannyEdge<GrayU8,GrayS16> canny = FactoryEdgeDetectors.canny(2, true, true, GrayU8.class, GrayS16.class);

		// The edge image is actually an optional parameter.  If you don't need it just pass in null
		canny.process(gray, 0.1f, 0.3f, edgeImage);

		// First get the contour created by canny
		List<EdgeContour> edgeContours = canny.getContours();

		// display the results
		imageBinaryEdges = VisualizeBinaryData.renderBinary(edgeImage, false, null);
		imageCannyContour = VisualizeBinaryData.renderContours(edgeContours, null, gray.width, gray.height, null);
	}
	
	// https://stackoverflow.com/questions/22265872/calculation-of-entropy-of-an-image-using-java
	private void getShannonEntropy(BufferedImage actualImage)
	{
		List<String> values= new ArrayList<String>();
	    int n = 0;
	    Map<Integer, Integer> occ = new HashMap<Integer, Integer>();
	    
	    for (int i = 0; i < actualImage.getHeight(); i++)
	    {
	    	for (int j = 0; j < actualImage.getWidth(); j++)
	    	{
	    		int pixel = actualImage.getRGB(j, i);
	            int red = (pixel >> 16) & 0xff;
	            int green = (pixel >> 8) & 0xff;
	            int blue = (pixel) & 0xff;

	            int d = (int) Math.round(0.2989 * red + 0.5870 * green + 0.1140 * blue);
	            if (!values.contains(String.valueOf(d)))
	            	values.add(String.valueOf(d));
	            if (occ.containsKey(d))
	            	occ.put(d, occ.get(d) + 1);
	            else 
	            	occ.put(d, 1);
	            
	            ++n;
	    	}
	    }
	    
	    double e = 0.0;
	    for (Map.Entry<Integer, Integer> entry : occ.entrySet()) 
	    {
	        double p = (double) entry.getValue() / n;
	        e += p * (Math.log(p) / Math.log(2));
	    }
	    dataManager.setEntropy(-e);
	 }

	private BufferedImage convertImageToNegative(BufferedImage positiveImage)
	{	    
	    int width = positiveImage.getWidth();
	    int height = positiveImage.getHeight();
	    
	    for(int y = 0; y < height; y++)
	    {
	    	for(int x = 0; x < width; x++)
	    	{
		        int p = positiveImage.getRGB(x, y);
		        int a = (p >> 24) & 0xff;
		        int r = (p >> 16) & 0xff;
		        int g = (p >> 8) & 0xff;
		        int b = p & 0xff;
		      
		        // subtract RGB from 255
		        r = 255 - r;
		        g = 255 - g;
		        b = 255 - b;
		        
		        // set new RGB value
		        p = (a << 24) | (r << 16) | (g << 8) | b;
		        positiveImage.setRGB(x, y, p);
	    	}
	    }
	    
	    return positiveImage;
	}
	
	private boolean inRangeWithError(float toCheck, float toCheckAgainst, float error)
	{
		return toCheckAgainst - error < toCheck && toCheck < toCheckAgainst + error;
	}
	
	private boolean inRange(float toCheck, float toCheckAgainstLower, float toCheckAgainstUpper)
	{
		return toCheckAgainstLower < toCheck && toCheck < toCheckAgainstUpper;
	}
	
	private void drawLine(LineParametric2D_F32 line, Graphics2D g2, Color color)
	{
		Line2D line2D = new Line2D.Double(line.getPointOnLine(-GeneralData.screenWidth).x, line.getPointOnLine(-GeneralData.innerTabHeight).y, line.getPointOnLine(GeneralData.screenWidth).x, line.getPointOnLine(GeneralData.innerTabHeight).y);
		g2.setColor(color);
		g2.setStroke(new BasicStroke(3, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
		g2.draw(line2D);
	}
	
	private void drawLineSegment(LineSegment2D_F32 line, Graphics2D g2, Color color)
	{
		Line2D line2D = new Line2D.Double(line.getA().x, line.getA().y, line.getB().x, line.getB().y);
		g2.setColor(color);
		g2.setStroke(new BasicStroke(3, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
		g2.draw(line2D);
	}
	
	private float round(double number)
	{
		DecimalFormat df = new DecimalFormat("#.#######");
		df.setRoundingMode(RoundingMode.HALF_UP);
		String numberString = df.format(number);
		numberString = numberString.replace(",", ".");
		return Float.valueOf(numberString);
	}
}
