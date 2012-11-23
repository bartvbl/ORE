package openrr.map;

import orre.resources.ResourceCache;
import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;
import static org.lwjgl.opengl.GL11.*;

public class Chunk extends EmptySceneNode implements SceneNode{
	public static final int CHUNK_WIDTH_TILES = 5;
	public static final int CHUNK_HEIGHT_TILES = 5;
	
	private final MapTile[][] tiles;
	private boolean requiresRebuild = true;
	private final int x, y;
	private int displayListID = -1;
	private ResourceCache cache;

	public Chunk(MapTile[][] tiles, int x, int y) {
		this.tiles = tiles;
		this.x = x;
		this.y = y;
	}
	
	public void preRender() {
		if(requiresRebuild){
			rebuild();
		}
	}
	
	public void render() {
		glPushMatrix();
		glCallList(this.displayListID);
		
		glPushMatrix();
		glTranslatef((float)this.x * MapTile.TILE_WIDTH * Chunk.CHUNK_WIDTH_TILES, (float)this.y * MapTile.TILE_HEIGHT * Chunk.CHUNK_HEIGHT_TILES, 0);
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[0].length; j++) {
				glPushMatrix();
				glTranslatef(i*MapTile.TILE_WIDTH, j*MapTile.TILE_HEIGHT, 0);
				tiles[i][j].render(cache);
				glPopMatrix();
			}
		}
		glPopMatrix();
		glPopMatrix();
	}
	
	public boolean requiresRebuild() {
		return requiresRebuild;
	}
	
	//NOTE: rebuilding should only be performed by the main thread, since openGL is used to recreate the display list.
	public void rebuild() {
		if(this.displayListID != -1) glDeleteLists(this.displayListID, 1);
		this.displayListID = glGenLists(1);
		glNewList(this.displayListID, GL_COMPILE);
		
		glEndList();
		this.requiresRebuild = false;
	}

	//with a lack of a better way of delivering the resource cache needed for building tiles
	public void setResourceCache(ResourceCache cache) {
		this.cache = cache;
	}
}
