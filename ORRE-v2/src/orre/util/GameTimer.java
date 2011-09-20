package orre.util;

import org.lwjgl.util.Timer;

public class GameTimer {
	private Timer timer;
	private float previousDeltaTime;
	
	public GameTimer()
	{
		this.timer = new Timer();
	}
	
	public float getDelta()
	{
		Timer.tick();
		float currentTime = this.timer.getTime();
		float deltaTime = currentTime - this.previousDeltaTime;
		this.previousDeltaTime = currentTime;
		return deltaTime;
	}
}
