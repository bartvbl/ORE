package openrr.test.guiHandlers;


import openrr.test.Button;
import openrr.test.Menu;
import openrr.test.Event;
import openrr.test.EventType;
import openrr.test.EventHandler;

public class MenuActionHandler implements EventHandler{
	
	Menu menu;
	
	public MenuActionHandler(Menu target) {
		target.addEventListener(EventType.BUTTON_RELEASE, this);
		menu = target;
	}
	
	public void handleEvent(Event<?> event) {
		if (event.eventType==EventType.BUTTON_RELEASE) {
			if (event.getEventParameterObject() instanceof Button && menu.buttonWithinMenu((Button)event.getEventParameterObject())) {
				
			}
		}
	
	}
	
}
