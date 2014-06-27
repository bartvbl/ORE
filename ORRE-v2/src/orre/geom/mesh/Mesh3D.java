package orre.geom.mesh;

import java.util.HashMap;

import orre.animation.Animatable;
import orre.sceneGraph.CoordinateNode;
import orre.sceneGraph.SceneNode;

public class Mesh3D implements Animatable, Model {
	private HashMap<String, ModelPart> parts = new HashMap<String, ModelPart>();
	public final CoordinateNode root;
	
	public Mesh3D(String name) {
		ModelPart root = new ModelPart(name);
		this.root = root;
		this.parts.put("root", root);
	}
	
	public Mesh3D(String string, CoordinateNode rootNode) {
		this.root = rootNode;
	}

	public CoordinateNode getModelPartByName(String name) {
		return this.parts.get(name);
	}

	public void destroy() {}

	public void addPart(String name, ModelPart part) {
		this.parts.put(name, part);
	}

	@Override
	public void notifyAnimationStart() {
		
	}

	@Override
	public void notifyAnimationEnd() {
		
	}

	@Override
	public CoordinateNode getRootNode() {
		return root;
	}
}
