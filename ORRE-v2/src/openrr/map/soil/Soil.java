package openrr.map.soil;

import static org.lwjgl.opengl.GL11.*;
import openrr.map.MapTile;
import openrr.map.soil.wallDrawing.GroundDrawer;
import openrr.map.soil.wallDrawing.WallDrawer;
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
		MapTile tileToRender = tiles[x][y];
		glEnable(GL_TEXTURE_2D);
		textureSet.bindTexture(SoilTextureType.ground);
		if(tileToRender.isWall()) {
			WallDrawer.drawWall(textureSet, tiles, x, y);
		} else {
			GroundDrawer.drawGroundTile(tileHeight);
		}
	}

}
