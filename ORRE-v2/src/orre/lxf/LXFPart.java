package orre.lxf;

import orre.gl.vao.GeometryNode;
import orre.sceneGraph.CoordinateNode;
import orre.sceneGraph.SceneNode;

public class LXFPart extends CoordinateNode implements SceneNode {

	public final String partName;

	public LXFPart(String name, GeometryNode geometry) {
		super(name);
		this.partName = name;
		this.addChild(geometry);
	}

}
