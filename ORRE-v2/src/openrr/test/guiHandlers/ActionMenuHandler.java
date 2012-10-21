package openrr.test.guiHandlers;

import openrr.test.Button;
import openrr.test.ButtonID;
import openrr.test.Menu;
import openrr.test.Event;
import openrr.test.EventType;
import openrr.test.EventHandler;

public class ActionMenuHandler implements EventHandler{
	
	Menu menu;
	
	public ActionMenuHandler(Menu target) {
		target.addEventListener(EventType.BUTTON_RELEASE, this);
		menu = target;
	}
	
	public void handleEvent(Event<?> event) {
		if (event.eventType==EventType.BUTTON_RELEASE) {
			if (event.getEventParameterObject() instanceof Button) {
				Button button = (Button)event.getEventParameterObject();
				if (menu.buttonWithinMenu((Button)event.getEventParameterObject())) {
					if (button.getButtonID()==ButtonID.BUILDINGS) {
						menu.getEventDispatcher().dispatchEvent(new Event(EventType.GUIELEMENT_MOVED, menu));
					}
				}
			}
		}
	}
	
}
