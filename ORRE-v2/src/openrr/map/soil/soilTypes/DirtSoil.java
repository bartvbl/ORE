package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.geom.mesh.Mesh3D;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;
import static org.lwjgl.opengl.GL11.*;

public class DirtSoil extends Soil{

	public DirtSoil(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.DIRT, textureSet, rgb);
	}
	
	public void handleEntityTouch(Entity entity) {}

	public void generateGeometry(int[][] tileHeight, MapTile[][] tiles, ResourceCache cache) {
		//if(this.stud == null) this.stud = cache.createModelInstace("map_stud");
		glPushMatrix();
		//glTranslatef(i, j, tileHeight[0][0]);
		//this.stud.render();
		glBegin(GL_LINES);
		glVertex3f(0, 0, tileHeight[0][0]);
		glVertex3f(MapTile.TILE_WIDTH, 0, tileHeight[1][0]);
		glVertex3f(MapTile.TILE_WIDTH, 0, tileHeight[1][0]);
		glVertex3f(MapTile.TILE_WIDTH, MapTile.TILE_HEIGHT, tileHeight[1][1]);
		glVertex3f(MapTile.TILE_WIDTH, MapTile.TILE_HEIGHT, tileHeight[1][1]);
		glVertex3f(0, MapTile.TILE_HEIGHT, tileHeight[0][1]);
		glVertex3f(0, MapTile.TILE_HEIGHT, tileHeight[0][1]);
		glVertex3f(0, 0, tileHeight[0][0]);
		glEnd();
		glPopMatrix();
	}
}
