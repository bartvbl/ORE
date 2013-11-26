package orre.gameWorld.services;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import orre.gl.util.CoordConverter;

public class InputService implements Service {
	
	private double mapRotationX = 0;
	private double mapRotationZ = 0;
	private double mapX = 0;
	private double mapY = 0;
	private double mapZ = 30;
	private double mouseX = 0;
	private double mouseY = 0;
	private float[] mouseLocation = new float[]{0, 0, 0};
	
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
		
		double mapDeltaX = 0;
		double mapDeltaY = 0;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			mapDeltaX--;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			mapDeltaX++;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			mapDeltaY--;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			mapDeltaY++;
		}
		
		if((mapDeltaX != 0) || (mapDeltaY != 0)) {
			double moveDirection = Math.atan2(mapDeltaX, mapDeltaY);
			moveDirection -= Math.toRadians(mapRotationZ);
			mapX += Math.sin(moveDirection) * mapMoveSpeed;
			mapY += Math.cos(moveDirection) * mapMoveSpeed;
		}
		
		mapZ += mouseDWheel / 35d;
		
		this.mouseX = mouseX;
		this.mouseY = mouseY;
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

	public double getMouseX() {
		return mouseX;
	}
	
	public double getMouseY() {
		return mouseY;
	}

	public void updateMouseTargetLocation() {
		this.mouseLocation  = CoordConverter.getMapCoords(Mouse.getX(), Mouse.getY());
	}

	public float[] getMouseTargetLocation() {
		return this.mouseLocation;
	}

}
