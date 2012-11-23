package openrr.map.soil.wallDrawing;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.Arrays;

import openrr.map.MapTile;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilTextureType;

public class WallDrawer {
	private static final int wallHeight = 10;
	
	public static void drawWall(SoilTextureSet textureSet, MapTile[][] tiles, int x, int y) {
		int[][] wallHeights = tiles[x][y].getTileHeights();
		boolean[][] neighbourhood = analyzeNeighbourhood(tiles, x, y);
		if(neighbourhood[0][1] && neighbourhood[1][0] && neighbourhood[2][1] && neighbourhood[1][2]) {
			drawRoof(wallHeights, textureSet);
		}
//		textureSet.bindTexture(SoilTextureType.wall);
//		glBegin(GL_QUADS);
//		glTexCoord2f(0, 0);
//		glVertex3f(0, 0, wallHeights[0][0]);
//		glTexCoord2f(1, 0);
//		glVertex3f(MapTile.TILE_WIDTH, 0, wallHeights[1][0] + wallHeight);
//		glTexCoord2f(1, 1);
//		glVertex3f(MapTile.TILE_WIDTH, MapTile.TILE_HEIGHT, wallHeights[1][1] + wallHeight);
//		glTexCoord2f(0, 1);
//		glVertex3f(0, MapTile.TILE_HEIGHT, wallHeights[0][1]);
//		glEnd();
	}

	private static void drawRoof(int[][] wallHeights, SoilTextureSet textureSet) {
		textureSet.bindTexture(SoilTextureType.roof);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex3f(0, 0, wallHeights[0][0] + wallHeight);
		glTexCoord2f(1, 0);
		glVertex3f(MapTile.TILE_WIDTH, 0, wallHeights[1][0] + wallHeight);
		glTexCoord2f(1, 1);
		glVertex3f(MapTile.TILE_WIDTH, MapTile.TILE_HEIGHT, wallHeights[1][1] + wallHeight);
		glTexCoord2f(0, 1);
		glVertex3f(0, MapTile.TILE_HEIGHT, wallHeights[0][1] + wallHeight);
		glEnd();
	}

	private static boolean[][] analyzeNeighbourhood(MapTile[][] tiles, int x, int y) {
		boolean[][] area = new boolean[3][3];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				try {
					area[i][j] = tiles[x + i - 1][y + j - 1].isWall();
				} catch(Exception e) {
					area[i][j] = true;
				}
			}
		}
		return area;
	}

}
