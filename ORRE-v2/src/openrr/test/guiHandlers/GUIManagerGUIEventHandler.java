package openrr.test.guiHandlers;

import openrr.test.GUIElement;
import openrr.test.EventHandler;
import openrr.test.Event;
import openrr.test.EventType;
import openrr.test.MenuManager;

public class GUIManagerGUIEventHandler implements EventHandler {
	
	MenuManager guiManager;
	
	public GUIManagerGUIEventHandler(MenuManager inGUIManager) {
		guiManager = inGUIManager;
		guiManager.guiEventManager.addEventListener(guiManager, EventType.GUIELEMENT_MOVING, this);
		guiManager.guiEventManager.addEventListener(guiManager, EventType.GUIELEMENT_MOVED, this);
	}
	
	public void handleEvent(Event<?> event) {
		if (event.eventType == EventType.GUIELEMENT_MOVING) {
			if (!guiManager.movingChildren.contains((GUIElement)event.getEventParameterObject())) {
				guiManager.movingChildren.add((GUIElement)event.getEventParameterObject());
			}
		}
		else if (event.eventType == EventType.GUIELEMENT_MOVED) {
			guiManager.movingChildren.remove(event.getEventParameterObject());
			}
	}

}
