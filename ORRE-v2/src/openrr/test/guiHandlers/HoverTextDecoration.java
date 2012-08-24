package openrr.test.guiHandlers;

import java.lang.annotation.Target;

import org.newdawn.slick.Color;

import openrr.test.Button;
import openrr.test.Event;
import openrr.test.EventHandler;
import openrr.test.EventType;
import openrr.test.Frame;
import openrr.test.GUIElement;
import openrr.test.InteractiveElement;
import openrr.test.HoverText;
import openrr.test.guiHandlers.TimerTickHandler;
import openrr.test.DelayTimer;
import openrr.test.DrawableElement;

public class HoverTextDecoration implements EventHandler {
	
	InteractiveElement element;
	HoverText hoverText;
	DelayTimer timer;
	Frame parent;
	Boolean displayingText;
	
	public HoverTextDecoration(InteractiveElement target, String text, Frame parentFrame) {
		target.addEventListener(EventType.MOUSE_MOVE, this);
		target.addEventListener(EventType.TIMER_EXPIRED, this);
		target.addEventListener(EventType.NEW_DELAY_HOVER_TIMER, this);
		element = target;
		parent = parentFrame;
		hoverText = new HoverText(text, "Arial", new Color(255, 255, 255), 12, null);
		displayingText = false;
	}
	
	public void handleEvent(Event<?> event) {
		if (event.getEventParameterObject()==element) {
			if (event.eventType == EventType.MOUSE_MOVE && !displayingText && timer==null) {
				timer = new DelayTimer(element, element.getEventDispatcher(), 2);
				element.getEventDispatcher().dispatchEvent(new Event(EventType.NEW_DELAY_HOVER_TIMER, element));
			}
			else if (event.eventType == EventType.TIMER_EXPIRED) {
				parent.addChild(hoverText);
				displayingText = true;
				timer.destroyTimer();
				timer = null;
			}
		}
		else {
			if (event.eventType == EventType.MOUSE_MOVE && displayingText) {
				parent.removeChild(hoverText);
				displayingText = false;
			}
			else if (event.eventType == EventType.MOUSE_MOVE && !displayingText && timer!=null) {
				timer.destroyTimer();
				timer = null;
			}
			else if (timer!=null && event.eventType == EventType.NEW_DELAY_HOVER_TIMER) {
				timer.destroyTimer();
				timer = null;
			}
		}
		
	}

}
