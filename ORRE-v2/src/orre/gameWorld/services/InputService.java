package orre.gameWorld.services;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InputService implements Service {
	
	private double mapRotationX = 0;
	private double mapRotationZ = 0;
	private double mapX = 0;
	private double mapZ = 0;
	
	public InputService() {
		try {
			if(!Mouse.isCreated()) {
				Mouse.create();
			}
			if(!Keyboard.isCreated()) {
				Keyboard.create();
			}
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		double mouseDX = Mouse.getDX();
		double mouseDY = Mouse.getDY();
		double mouseX = Mouse.getX();
		double mouseY = Mouse.getY();
		
		if(Mouse.isButtonDown(1)) {
			mapRotationZ += (double) (mouseDX) / 3d;
			mapRotationX -= (double) (mouseDY) / 3d;
		}
	}

	public double getMapRotationX() {
		return mapRotationX;
	}

	public double getMapRotationZ() {
		return mapRotationZ;
	}

}
