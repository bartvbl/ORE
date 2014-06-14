package orre.gameWorld.services;

import java.util.HashMap;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import orre.devTools.GameInfoWindow;
import orre.gameWorld.core.GameWorld;
import orre.gl.util.CoordConverter;
import orre.input.CommandDispatcher;
import orre.resources.ResourceCache;

public class InputService implements Service {

	private double mapRotationX = 0;
	private double mapRotationZ = 0;
	private double mapX = 0;
	private double mapY = 0;
	private double mapZ = 30;
	private double mouseX = 0;
	private double mouseY = 0;
	private float[] mouseLocation = new float[]{0, 0, 0};

	private final HashMap<Integer, Boolean> keyStateMap;
	private final GameWorld world;
	private final ResourceCache cache;
	private final CommandDispatcher dispatcher;

	private static final double mapMoveSpeed = 0.3d;

	public InputService(final GameWorld world, final ResourceCache cache) {
		this.world = world;
		this.cache = cache;
		this.dispatcher = new CommandDispatcher(world);
		try {
			if(!Mouse.isCreated()) {
				Mouse.create();
			}
			if(!Keyboard.isCreated()) {
				Keyboard.create();
			}
		} catch (final LWJGLException e) {
			e.printStackTrace();
		}
		this.keyStateMap = new HashMap<Integer, Boolean>();
	}

	public void addCommandListener(final int gameObjectID, final String command) {
		dispatcher.addInputEventListener(command, gameObjectID);
	}

	public void removeCommandListener(final int gameObjectID, final String command) {

	}

	@Override
	public void tick() {
		final double mouseDX = Mouse.getDX();
		final double mouseDY = Mouse.getDY();
		final double mouseX = Mouse.getX();
		final double mouseY = Mouse.getY();
		final double mouseDWheel = Mouse.getDWheel();

		if(Mouse.isButtonDown(1)) {
			mapRotationZ += mouseDX / 4d;
			mapRotationX -= mouseDY / 4d;
		}

		double mapDeltaX = 0;
		double mapDeltaY = 0;

		while(Keyboard.next()) {
			final int pressedKey = Keyboard.getEventKey();
			final boolean isKeyDown = Keyboard.getEventKeyState();
			keyStateMap.put(pressedKey, isKeyDown);
			if(isKeyDown) {
				onKeyDown(pressedKey);
			}
		}

		if(keyStateMap.containsKey(Keyboard.KEY_A) && keyStateMap.get(Keyboard.KEY_A)) {
			mapDeltaX--;
		}
		if(keyStateMap.containsKey(Keyboard.KEY_D) && keyStateMap.get(Keyboard.KEY_D)) {
			mapDeltaX++;
		}
		if(keyStateMap.containsKey(Keyboard.KEY_S) && keyStateMap.get(Keyboard.KEY_S)) {
			mapDeltaY--;
		}
		if(keyStateMap.containsKey(Keyboard.KEY_W) && keyStateMap.get(Keyboard.KEY_W)) {
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

	private void onKeyDown(final int pressedKey) {
		switch(pressedKey) {
		case Keyboard.KEY_F3:
			GameInfoWindow.showDebugInfo(world, cache);
		}
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
