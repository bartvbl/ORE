package orre.util;

import org.lwjgl.util.Timer;

public class FlexibleFrameTimer extends GameTimer{
	private int maximumFrameSleepTime;
	private float minimumFrameSleepTime;

	
	public FlexibleFrameTimer(int framesPerSecond, float minimumFrameSleepTime)
	{
		this.setup(framesPerSecond, minimumFrameSleepTime);
	}
	public FlexibleFrameTimer(int framesPerSecond)
	{
		this.setup(framesPerSecond, 0);
	}
	
	private void setup(int framesPerSecond, float minimumFrameSleepTime)
	{
		this.minimumFrameSleepTime = minimumFrameSleepTime;
		this.maximumFrameSleepTime = 1000/framesPerSecond;
	}
	
	public void sleep()
	{
		Timer.tick();
		
		float deltaInMillis = 1000/this.getDelta();
		float timeToSleep;
		
		if(deltaInMillis < this.minimumFrameSleepTime)
		{
			timeToSleep = this.minimumFrameSleepTime;
		} else if(deltaInMillis > this.maximumFrameSleepTime)
		{
			timeToSleep = this.maximumFrameSleepTime;
		} else {
			timeToSleep = deltaInMillis;
		}
		try
		{
			Thread.sleep((int)timeToSleep);
		}
		catch(InterruptedException e)
		{
			System.out.println("Interrupted exception error when sleeping: ");
			e.printStackTrace();
		}
	}
}
