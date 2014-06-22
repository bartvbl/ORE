package orre.gui.controls;

import orre.gui.Bounds;
import orre.gui.baseNodes.GUIBaseNode;
import orre.gui.elements.GUIElement;

public abstract class Control extends GUIElement {
	private double x;
	private double y;
	private double width;
	private double height;
	
	private boolean wasMouseOver;
	private boolean wasMousePressed;

	public Control(GUIBaseNode node, Bounds bounds, String name) {
		super(bounds, node, name);
	}
	
	@Override
	public void update(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.update();
	}

	public void updateMouse(double mouseX, double mouseY, boolean mouseState) {
		System.out.println(x + " vs " + mouseX);
		if((x < mouseX) && (y < mouseY) && (x + width > mouseX) && (y + height > mouseY)) {
			this.onMouseOver();
			wasMouseOver = true;
			if(mouseState) {
				this.onMouseDown();
			}
			if(!wasMousePressed && mouseState) {
				this.onClick();
			}
		} else if(wasMouseOver) {
			wasMouseOver = false;
			this.onMouseOut();
		}
		wasMousePressed = mouseState;
	}


	protected abstract void update();

	protected abstract void onClick();
	protected abstract void onMouseDown();
	protected abstract void onMouseOut();
	protected abstract void onMouseOver();
}
