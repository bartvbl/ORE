package orre.geom.mesh;

import java.util.HashMap;

import orre.sceneGraph.SceneNode;
import orre.sceneGraph.CoordinateNode;

public class Mesh3D extends CoordinateNode implements SceneNode {
	private HashMap<String, ModelPart> parts = new HashMap<String, ModelPart>();
	
	public ModelPart getModelPartByName(String name) {
		return this.parts.get(name);
	}

	public void render() {
		this.renderChildren();
	}

	public void destroy() {}
}
