package test.graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import graphics.model.data.GraphicsDataManager;
import utilities.PropertyManager;

class GraphicsDataManagerTestCase 
{
	public GraphicsDataManagerTestCase() 
	{
		new PropertyManager().getAllValues();
	}
	
	@Test
	public void polygonCounts() 
	{
		GraphicsDataManager graphicsDataManager = new GraphicsDataManager();
		assertEquals(0, (int) graphicsDataManager.getPolygonCounts().get(3));
		assertEquals(null, graphicsDataManager.getPolygonCounts().get(1));
		
		graphicsDataManager.incrementPolygonCountsMap(3);
		assertEquals(1, (int) graphicsDataManager.getPolygonCounts().get(3));
		assertEquals(0, (int) graphicsDataManager.getPolygonCounts().get(4));
	}
	
	@Test
	public void reset()
	{
		GraphicsDataManager graphicsDataManager = new GraphicsDataManager();
		graphicsDataManager.incrementPolygonCountsMap(3);
		
		graphicsDataManager.reset();
		assertEquals(0, (int) graphicsDataManager.getPolygonCounts().get(3));
	}
}
