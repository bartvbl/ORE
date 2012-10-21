package openrr.test.guiHandlers;

import openrr.test.Button;
import openrr.test.Event;
import openrr.test.EventType;
import openrr.test.EventHandler;
import openrr.test.DelayTimer;

public class TimerTickHandler implements EventHandler {
	
	DelayTimer timer;
		
	public TimerTickHandler(DelayTimer target) {
		timer = target;
	}
	
	public void handleEvent(Event<?> event) {
		if (event.eventType == EventType.TIMER_TICK) {
			timer.check();
		}
	}

}
