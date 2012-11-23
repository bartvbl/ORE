package openrr.map.soil;

import orre.entity.Entity;
import orre.geom.mesh.Mesh3D;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.Soil;
import static org.lwjgl.opengl.GL11.*;

public class DirtSoil extends Soil{
	private Mesh3D stud = null;

	public DirtSoil() {
		super(SoilType.DIRT);
	}
	
	public void handleEntityTouch(Entity entity) {}

	public void generateGeometry(int[][] tileHeight, ResourceCache cache) {
	if(this.stud == null) this.stud = cache.createModelInstace("map_stud");
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				glPushMatrix();
				glTranslatef(i, j, tileHeight[0][0]);
				this.stud.render();
				glPopMatrix();
			}
		}
	}
}
