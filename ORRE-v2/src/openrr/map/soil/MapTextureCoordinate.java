package openrr.map.soil;

public class MapTextureCoordinate {
	public final MapTexture mapTexture;
	public final int x;
	public final int y;

	public MapTextureCoordinate(MapTexture mapTexture, int x, int y) {
		this.mapTexture = mapTexture;
		this.x = x;
		this.y = y;
	}
}
