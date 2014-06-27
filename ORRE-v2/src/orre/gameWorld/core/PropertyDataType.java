package orre.gameWorld.core;

import orre.animation.Animatable;
import orre.geom.Point3D;
import orre.geom.mesh.Mesh3D;
import orre.sceneGraph.SceneNode;

public enum PropertyDataType {
PHYSICAL_OBJECT_COORDINATES(Point3D.class), 
APPEARANCE(Animatable.class);

public final Class<?> expectedReturnDataType;

private PropertyDataType(Class<?> dataType) {
	this.expectedReturnDataType = dataType;
}
}
