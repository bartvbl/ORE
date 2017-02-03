package orre.resources.incompleteResources;

import orre.resources.IncompleteResourceObject;

public class IncompleteTexture implements IncompleteResourceObject<IncompleteTexture> {
	public final String name;
	public final byte[] imageData;
	public final int width;
	public final int height;

	public IncompleteTexture(String name, byte[] imageData, int width, int height) {
		this.imageData = imageData;
		this.width = width;
		this.height = height;
		this.name = name;
	}
}
