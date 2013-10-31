package orre.geom.mesh;

import orre.geom.vbo.GeometryBuffer;
import orre.gl.materials.Material;
import orre.sceneGraph.SceneNode;
import orre.sceneGraph.CoordinateNode;

public class ModelPart extends CoordinateNode implements SceneNode {
	
	private String name;

	public void addMaterialAndGeometryBufferCombo(Material material, GeometryBuffer buffer, String name) {
		this.addChild(material);
		material.addChild(buffer);
		this.name = name;
	}
	
	public void render() {
		this.renderChildren();
	}

	public void destroy() {}
	
	public String toString() {
		return "ModelPart " + this.name;
	}



}
