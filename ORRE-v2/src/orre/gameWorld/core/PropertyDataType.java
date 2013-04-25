package orre.gameWorld.core;

import orre.geom.Point3D;

public enum PropertyDataType {
PHYSICAL_OBJECT_COORDINATES(Point3D.class);

public final Class<?> expectedReturnDataType;

private PropertyDataType(Class<?> dataType) {
	this.expectedReturnDataType = dataType;
}
}
