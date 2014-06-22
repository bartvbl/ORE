package orre.gui.controls;

import orre.gl.texture.Texture;
import orre.gui.Bounds;
import orre.gui.elementNodes.ButtonNode;
import orre.resources.ResourceCache;
import orre.resources.ResourceType;

public class ImageButton extends Control {
	private final ButtonNode buttonNode;
	private final String upImageName;
	private final String overImageName;
	private final String downImageName;
	private final String disabledImageName;

	public static ImageButton create(Bounds bounds, String name, String upImageName, String overImageName, String downImageName, String disabledImageName) {
		ButtonNode buttonNode = new ButtonNode();
		return new ImageButton(buttonNode, bounds, name, upImageName, overImageName, downImageName, disabledImageName);
	}

	private ImageButton(ButtonNode node, Bounds bounds, String name, String upImageName, String overImageName, String downImageName, String disabledImageName) {
		super(node, bounds, name);
		this.buttonNode = node;
		this.upImageName = upImageName;
		this.overImageName = overImageName;
		this.downImageName = downImageName;
		this.disabledImageName = disabledImageName;
	}

	@Override
	protected void update() {
		
	}
	
	

	@Override
	public void initGraphics(ResourceCache resourceCache) {
		Texture upTexture = (Texture) resourceCache.getResource(ResourceType.texture, upImageName).content;
		Texture overTexture = (Texture) resourceCache.getResource(ResourceType.texture, overImageName).content;
		Texture downTexture = (Texture) resourceCache.getResource(ResourceType.texture, downImageName).content;
		Texture disabledTexture = (Texture) resourceCache.getResource(ResourceType.texture, disabledImageName).content;
		buttonNode.setTextures(upTexture, overTexture, downTexture, disabledTexture);
	}

	@Override
	protected void onClick() {
		
	}

	@Override
	protected void onMouseDown() {
		buttonNode.setDownState();
	}

	@Override
	protected void onMouseOut() {
		buttonNode.setUpState();
	}

	@Override
	protected void onMouseOver() {
		buttonNode.setOverState();
	}	
	
	public String toString() {
		return "ImageButton " + this.name;
	}
}
