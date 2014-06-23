package orre.gui.controls;

import orre.gui.Bounds;
import orre.gui.elementNodes.ButtonNode;

public class ImageToggleButton extends ImageButton {
	private final ButtonNode buttonNode;
	private boolean isSelected = false;
	
	public static ImageToggleButton create(Bounds bounds, String name, String upImageName, String overImageName, String downImageName, String disabledImageName, String onClickAction) {
		ButtonNode buttonNode = new ButtonNode(name);
		return new ImageToggleButton(buttonNode, bounds, name, upImageName, overImageName, downImageName, disabledImageName, onClickAction);
	}

	private ImageToggleButton(ButtonNode node, Bounds bounds, String name, String upImageName, String overImageName, String downImageName, String disabledImageName, String onClickAction) {
		super(node, bounds, name, upImageName, overImageName, downImageName, disabledImageName, onClickAction);
		this.buttonNode = node;
	}

	@Override
	protected void update() {
		
	}

	@Override
	protected void onClick() {
		isSelected = !isSelected;
		if(isSelected) {
			buttonNode.setDownState();
		}
	}

	@Override
	protected void onMouseDown() {}

	@Override
	protected void onMouseOut() {
		if(!this.isSelected) {
			buttonNode.setUpState();
		}
	}

	@Override
	protected void onMouseOver() {
		if(!this.isSelected) {
			buttonNode.setOverState();
		}
	}	
	
	public String toString() {
		return "ImageToggleButton " + this.name;
	}
}
