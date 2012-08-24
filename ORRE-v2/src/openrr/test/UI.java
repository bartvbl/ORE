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
		 handle2D(event);
		/* if (menuManager.mouseIsInMenuBounds(event.x, event.y)) {
			 handle2D(event);
		 }
		 else {
			 if (event.type==MouseEvent.RELEASE) {
				 handle2D(event);
				 buttonInteractingWith = null;
			 }
			 else {
				 if (buttonInteractingWith!=null && buttonInteractingWith.getState()!="p") {
					 buttonInteractingWith.setState("n");
					 buttonInteractingWith = null;
				 }
				 handle3D(event);
			 }
		 }*/
	}
	
	public void handle2D(MouseEvent event) {
		if (event.getType()==MouseEvent.MOVE) {
			if (buttonInteractingWith==null) {
				buttonInteractingWith = menuManager.getButton(event.getPosData());
				menuManager.getDispatcher().dispatchEvent(new Event(EventType.MOUSE_MOVE, buttonInteractingWith));
			} else {
				if (!(buttonInteractingWith.getState()=="p") && !(buttonInteractingWith.inCoords(event.getPosData()))) {
					buttonInteractingWith.setState("n");
					buttonInteractingWith = menuManager.getButton(event.getPosData());
					if (buttonInteractingWith!=null) {
						menuManager.getDispatcher().dispatchEvent(new Event(EventType.MOUSE_MOVE, buttonInteractingWith));
						//buttonInteractingWith.hover();
					}
				}
			}
					
		}
		else {
			if (event.getType()==MouseEvent.PRESS) {
				buttonInteractingWith = menuManager.getButton(event.getPosData());
				if (buttonInteractingWith!=null) {
					menuManager.getDispatcher().dispatchEvent(new Event(EventType.MOUSE_PRESS, buttonInteractingWith));
					//buttonInteractingWith.press();
				}
			}
			else {
				if (event.getType()==MouseEvent.RELEASE) {
					if (buttonInteractingWith!=null) {
						if (buttonInteractingWith.inCoords(event.getPosData())) {
							menuManager.getDispatcher().dispatchEvent(new Event(EventType.MOUSE_RELEASE, buttonInteractingWith));
							//buttonInteractingWith.release()
							buttonInteractingWith = null;
						}
						else {
							buttonInteractingWith.setState("n");
							buttonInteractingWith = null;
						}
					}
				}
			}
		}	
	}
	
	public void handle3D(MouseEvent event) {
	
	}
	
	

}
