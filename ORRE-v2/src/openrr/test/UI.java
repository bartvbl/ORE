package openrr.test;

import org.lwjgl.input.Mouse;

import openrr.test.Button;

public class UI {
	
	private boolean mouseClickHandled = true;
	
	private Button buttonInteractingWith = null;

	private int x;
	private int y;
	
	MenuManager menuManager;
	
	public UI(MenuManager m) { 
		menuManager = m;
	}
	
	public void handleUI(MouseEvent event) {
		 if (menuManager.mouseIsInMenuBounds(event.x, event.y)) {
			 handle2D(event);
		 }
		 else {
			 if (event.type==MouseEvent.RELEASE) {
				 handle2D(event);
				 buttonInteractingWith = null;
			 }
			 else {
				 if (buttonInteractingWith!=null && buttonInteractingWith.getState()!=Button.PRESSED) {
					 buttonInteractingWith.setState(Button.NORMAL);
					 buttonInteractingWith = null;
				 }
				 handle3D(event);
			 }
		 }
	}
	
	public void handle2D(MouseEvent event) {
		if (event.type==MouseEvent.MOVE) {
			if (buttonInteractingWith==null) {
				set2DMouseData(event.x, event.y);
				if (buttonInteractingWith!=null) {
					buttonInteractingWith.hoveredOver();
				}
			}
			else {
				if (!(buttonInteractingWith.getState()==Button.PRESSED)) {
					if (!buttonInteractingWith.inBounds(event.x, event.y)) {
						buttonInteractingWith.setState(Button.NORMAL);
						set2DMouseData(event.x, event.y);
						if (buttonInteractingWith!=null) {
							buttonInteractingWith.hoveredOver();
						}
					}
				}
			}
		}
		else{
			if (event.type==MouseEvent.PRESS) {
				set2DMouseData(event.x, event.y);
				if (buttonInteractingWith!=null) {
					buttonInteractingWith.pressed();
				}
			}
			else {
				if (event.type==MouseEvent.RELEASE && buttonInteractingWith!=null) {
					if (buttonInteractingWith.inBounds(event.x, event.y)) {
						buttonInteractingWith.clicked();
						buttonInteractingWith = null;
					}
					else {
						buttonInteractingWith.setState(Button.NORMAL);
						buttonInteractingWith = null;
					}
				}
			}
		}
	}
	
	public void handle3D(MouseEvent event) {
	
	}
	
	public void set2DMouseData(int eX, int eY) {
		x = eX;
		y = eY;
		buttonInteractingWith = menuManager.getButtonInBounds(x, y);
	}
	
	

}
