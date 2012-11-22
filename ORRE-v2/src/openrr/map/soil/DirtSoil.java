package openrr.map.soil;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.Soil;
import static org.lwjgl.opengl.GL11.*;

public class DirtSoil extends Soil{
	public DirtSoil() {
		super(SoilType.DIRT);
	}
	
	public void handleEntityTouch(Entity entity) {}

	public void generateGeometry(int[][] tileHeight, ResourceCache cache) {
		System.out.println("drawing tile");
		glBegin(GL_LINES);
		glVertex3f(0, 0, tileHeight[0][0]);
		glVertex3f(MapTile.TILE_WIDTH, 0, tileHeight[1][0]);
		glVertex3f(MapTile.TILE_WIDTH, MapTile.TILE_HEIGHT, tileHeight[1][1]);
		glVertex3f(0, MapTile.TILE_HEIGHT, tileHeight[0][1]);
		glEnd();
	}
}
