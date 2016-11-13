package orre.geom;

import org.lwjgl.util.vector.Vector3f;

public enum Axis {
	x(new Vector3f(1, 0, 0)),
	y(new Vector3f(0, 1, 0)),
	z(new Vector3f(0, 0, 1))
	
	;
	
	public final Vector3f vector;

	private Axis(Vector3f vector) {
		this.vector = vector;
	}
}
