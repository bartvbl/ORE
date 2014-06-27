package orre.gameWorld.core;

import orre.geom.Point3D;
import orre.geom.mesh.Model;

public enum PropertyDataType {
PHYSICAL_OBJECT_COORDINATES(Point3D.class), 
APPEARANCE(Model.class);

public final Class<?> expectedReturnDataType;

private PropertyDataType(Class<?> dataType) {
	this.expectedReturnDataType = dataType;
}
}
