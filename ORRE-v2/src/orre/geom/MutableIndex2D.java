package orre.geom;

public class MutableIndex2D {
	public int x;
	public int y;
	
	public Index2D asImmutable() {
		return new Index2D(x, y);
	}
}
