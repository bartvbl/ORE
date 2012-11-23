package openrr.map.soil;

import static org.lwjgl.opengl.GL11.*;
import openrr.map.MapTile;
import orre.entity.Entity;
import orre.geom.vbo.GeometryBuffer;
import orre.resources.ResourceCache;

public abstract class Soil {

	public final SoilType soilType;
	public final int[] rgb;
	private final SoilTextureSet textureSet;

	public Soil(SoilType soilType, SoilTextureSet textureSet, int[] rgb) {
		this.soilType = soilType;
		this.textureSet = textureSet;
		this.rgb = rgb;
	}

	public abstract void handleEntityTouch(Entity entity);
	public void generateGeometry(int[][] tileHeight, MapTile[][] tiles, int x, int y) {
		textureSet.finalizeTextures();
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		textureSet.bindTexture(SoilTextureType.ground);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex3f(0, 0, tileHeight[0][0]);
		glTexCoord2f(1, 0);
		glVertex3f(MapTile.TILE_WIDTH, 0, tileHeight[1][0]);
		glTexCoord2f(1, 1);
		glVertex3f(MapTile.TILE_WIDTH, MapTile.TILE_HEIGHT, tileHeight[1][1]);
		glTexCoord2f(0, 1);
		glVertex3f(0, MapTile.TILE_HEIGHT, tileHeight[0][1]);
		glEnd();
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
