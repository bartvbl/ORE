package orre.geom.mesh;

import orre.geom.vbo.GeometryNode;
import orre.gl.materials.Material;
import orre.sceneGraph.SceneNode;
import orre.sceneGraph.CoordinateNode;

public class ModelPart extends CoordinateNode implements SceneNode {
	
	private String name;
	
	public ModelPart() {
	}
	
	public ModelPart(String name) {
		this.name = name;
	}

	public void addMaterialAndGeometryBufferCombo(Material material, GeometryNode buffer, String name) {
		this.addChild(material);
		material.addChild(buffer);
		this.name = name;
	}
	
	@Override
	public void render() {
	}

	@Override
	public void destroy() {}
	
	@Override
	public String toString() {
		return "ModelPart " + this.name;
	}



}
