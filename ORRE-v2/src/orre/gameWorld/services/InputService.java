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
	
	private static final double mapMoveSpeed = 0.3d;
	
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
			mapRotationZ += mouseDX / 4d;
			mapRotationX -= mouseDY / 4d;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			mapX -= mapMoveSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			mapX += mapMoveSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			mapY -= mapMoveSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			mapY += mapMoveSpeed;
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
