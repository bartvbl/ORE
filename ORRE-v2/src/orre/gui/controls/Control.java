package orre.gui.controls;

import java.util.HashMap;

import orre.gameWorld.core.GameWorld;
import orre.gui.Bounds;
import static org.lwjgl.opengl.GL11.*;
import orre.gui.baseNodes.GUIBaseNode;
import orre.gui.elements.GUIElement;

public abstract class Control extends GUIElement {
	private double x;
	private double y;
	private double width;
	private double height;
	
	private boolean wasMouseOver;
	private boolean wasMousePressed;
	
	private final String onClickAction;
	private GameWorld world;
	private final HashMap<String, String> eventParams = new HashMap<String, String>();

	public Control(GUIBaseNode node, Bounds bounds, String name, String onClickAction) {
		super(bounds, node, name);
		this.onClickAction = onClickAction;
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
		if((x < mouseX) && (y < mouseY) && (x + width > mouseX) && (y + height > mouseY)) {
			this.onMouseOver();
			if(mouseState) {
				this.onMouseDown();
			}
			if(!wasMousePressed && mouseState && wasMouseOver) {
				this.onClick();
				if(onClickAction != null) {
					eventParams.put("action", onClickAction);
					this.world.services.scriptingService.dispatchScriptEvent("GUI_Click", eventParams);
				}
			}
			wasMouseOver = true;
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

	public void setCurrentWorld(GameWorld world) {
		this.world = world;
	}
}
