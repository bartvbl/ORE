package orre.gameWorld.core;

import orre.geom.Point3D;

public enum RequestedDataType {
PHYSICAL_OBJECT_COORDINATES(Point3D.class);

public final Class<?> expectedReturnDataType;

private RequestedDataType(Class<?> dataType) {
	this.expectedReturnDataType = dataType;
}
}
