package orre.resources.partiallyLoadables;

import orre.geom.vbo.GeometryBuffer;
import orre.resources.Finalizable;
import orre.resources.loaders.obj.BlueprintMaterial;
import orre.sceneGraph.SceneNode;
import static org.lwjgl.opengl.GL11.*;

public class PartiallyLoadableModelPart extends Finalizable {
	private final GeometryBuffer geometryBuffer;
	private final BlueprintMaterial blueprintMaterial;
	private int displayListID = 0;

	public PartiallyLoadableModelPart(GeometryBuffer geometryBuffer, BlueprintMaterial material) {
		this.geometryBuffer = geometryBuffer;
		this.blueprintMaterial = material;
	}
		
	public void finalizeResource() {
		
	}

	public SceneNode createSceneNode() {return null;}
	public void addToCache() {}
}
