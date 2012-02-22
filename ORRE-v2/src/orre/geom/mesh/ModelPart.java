package orre.geom.mesh;

import orre.geom.vbo.GeometryBuffer;
import orre.gl.materials.Material;
import orre.sceneGraph.SceneNode;
import orre.sceneGraph.SimpleSceneNode;

public class ModelPart extends SimpleSceneNode implements SceneNode {
	
	public void addMaterialAndGeometryBufferCombo(Material material, GeometryBuffer buffer) {
		this.addChild(material);
		material.addChild(buffer);
	}
	
	public void render() {
		this.renderChildren();
	}

	public void destroy() {}
	
}
