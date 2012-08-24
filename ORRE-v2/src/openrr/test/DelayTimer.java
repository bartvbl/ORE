package openrr.test;

import org.lwjgl.util.Timer;
import openrr.test.EventDispatcher;
import openrr.test.guiHandlers.TimerTickHandler;

public class DelayTimer {
	
	EventDispatcher eventDispatcher;
	Timer timer;
	InteractiveElement element;
	int runTime;
	Boolean running;
	TimerTickHandler tickHandler;
	
	public DelayTimer(InteractiveElement inElement, EventDispatcher inEventDispatcher, int inRunTime) {
		element = inElement;
		timer = new Timer();
		runTime = inRunTime;
		eventDispatcher = inEventDispatcher;
		tickHandler = new TimerTickHandler(this);
		eventDispatcher.addEventListener(this, EventType.TIMER_TICK, tickHandler);
		running = true;
	}
	
	public void check() {
		if (running && timer.getTime()>=runTime) {
			eventDispatcher.dispatchEvent(new Event(EventType.TIMER_EXPIRED, element));
			running = false;
		}
	}
	
	public void destroyTimer() {
		eventDispatcher.removeEventListener(this, EventType.TIMER_TICK);
		tickHandler = null;
	}
}
