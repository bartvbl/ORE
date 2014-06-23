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

	public static ImageButton create(Bounds bounds, String name, String upImageName, String overImageName, String downImageName, String disabledImageName, String onClickAction) {
		ButtonNode buttonNode = new ButtonNode(name);
		return new ImageButton(buttonNode, bounds, name, upImageName, overImageName, downImageName, disabledImageName, onClickAction);
	}

	protected ImageButton(ButtonNode node, Bounds bounds, String name, String upImageName, String overImageName, String downImageName, String disabledImageName, String onClickAction) {
		super(node, bounds, name, onClickAction);
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
		Texture upTexture = null;
		Texture overTexture = null;
		Texture downTexture = null; 
		Texture disabledTexture = null;
		if(upImageName != null) {
			upTexture = (Texture) resourceCache.getResource(ResourceType.texture, upImageName).content;
		}
		if(overImageName != null) {
			overTexture = (Texture) resourceCache.getResource(ResourceType.texture, overImageName).content;
		}
		if(downImageName != null) {
			downTexture = (Texture) resourceCache.getResource(ResourceType.texture, downImageName).content;
		}
		if(disabledImageName != null) {
			disabledTexture = (Texture) resourceCache.getResource(ResourceType.texture, disabledImageName).content;
		}
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
