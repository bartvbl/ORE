package openrr.test;

import org.lwjgl.input.Mouse;

import openrr.test.Button;

public class UI {
	
	private boolean mouseClickHandled = true;
	
	private Button buttonInteractingWith = null;

	private int x;
	private int y;
	
	public UI() { }
	
	public void handleUI(MouseEvent event) {
		 
		
	}
	
	public void handle2D(MouseEvent event) {
		if (event.type==MouseEvent.PRESS) {
			x = event.x;
			y = event.y;
			//search within 2d to find what menu, and then what button.  button.click or w/e
		}
	}
	
	public void handle3D(MouseEvent event) {
	
	}
	
	

}
