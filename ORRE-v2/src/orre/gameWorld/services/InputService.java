package orre.gameWorld.services;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InputService implements Service {
	
	private double mapRotationX = 0;
	private double mapRotationZ = 0;
	private double mapX = 0;
	private double mapY = 0;
	private double mapZ = 30;
	
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
		double mouseDWheel = Mouse.getDWheel();
		
		if(Mouse.isButtonDown(1)) {
			mapRotationZ += mouseDX / 5d;
			mapRotationX -= mouseDY / 5d;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			mapX--;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			mapX++;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			mapY--;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			mapY++;
		}
		
		mapZ += mouseDWheel / 15d;
	}

	public double getMapRotationX() {
		return mapRotationX;
	}

	public double getMapRotationZ() {
		return mapRotationZ;
	}

	public double getMapX() {
		return mapX;
	}
	
	public double getMapY() {
		return mapY;
	}

	public double getMapZ() {
		return mapZ;
	}

}
