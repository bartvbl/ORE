package openrr.map.soil.wallDrawing;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import openrr.map.MapTile;

public class GroundDrawer {

	public static void drawGroundTile(int[][] tileHeight) {
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
	}

}
