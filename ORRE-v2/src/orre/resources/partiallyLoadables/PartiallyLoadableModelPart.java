package orre.resources.partiallyLoadables;

import java.util.ArrayList;

import orre.geom.vbo.GeometryBuffer;
import orre.gl.materials.Material;
import orre.resources.Finalizable;
import orre.sceneGraph.SceneNode;
import static org.lwjgl.opengl.GL11.*;

public class PartiallyLoadableModelPart extends Finalizable {
	private ArrayList<BlueprintMaterial> materials = new ArrayList<BlueprintMaterial>();
	private ArrayList<UnpackedGeometryBuffer> geometryBuffers = new ArrayList<UnpackedGeometryBuffer>();
	
	public final String name;

	public PartiallyLoadableModelPart(String name) {
		this.name = name;
	}
		
	public void addMaterial(BlueprintMaterial currentMaterial) {
		this.materials.add(currentMaterial);
		this.geometryBuffers.add(new UnpackedGeometryBuffer());
	}
	public void addVertex(float[] vertex) {
		UnpackedGeometryBuffer buffer = this.geometryBuffers.get(this.geometryBuffers.size()-1);
		buffer.addVertex(vertex); 
	}
	
	public void finalizeResource() {
		//remember to check if the buffer is empty before finalizing it. It may not have been filled with anything when parsing the OBJ file.
	}

	public SceneNode createSceneNode() {return null;}
	public void addToCache() {}

}
