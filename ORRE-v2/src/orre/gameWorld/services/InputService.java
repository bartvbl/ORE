package orre.gameWorld.services;


import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import orre.gameWorld.core.GameWorld;
import orre.input.CommandDispatcher;
import orre.input.devices.ControllerDevice;
import orre.input.devices.KeyboardDevice;
import orre.input.devices.MouseDevice;
import orre.resources.ResourceCache;

public class InputService implements Service {
	private final GameWorld world;
	private final ResourceCache cache;
	private final CommandDispatcher dispatcher;
	
	private final KeyboardDevice keyboard;
	private final MouseDevice mouse;
	private final ControllerDevice controller;

	public InputService(final GameWorld world, final ResourceCache cache) {
		this.world = world;
		this.cache = cache;
		this.dispatcher = new CommandDispatcher(world);
		createDevices();
		
		keyboard = new KeyboardDevice(dispatcher);
		mouse = new MouseDevice(dispatcher);
		controller = new ControllerDevice(dispatcher);
	}

	@Override
	public void tick() {
		keyboard.update();
		mouse.update();
		controller.update();
	}

	public void addCommandListener(final int gameObjectID, final String command, final int priority) {
		dispatcher.addInputEventListener(command, gameObjectID, priority);
	}

	public void removeCommandListener(final int gameObjectID, final String command) {
		dispatcher.removeInputEventListener(command, gameObjectID);
	}

	private void createDevices() {
		try {
			if(!Mouse.isCreated()) {
				Mouse.create();
			}
			if(!Keyboard.isCreated()) {
				Keyboard.create();
			}
			if(!Controllers.isCreated()) {
				Controllers.create();
			}
		} catch (final LWJGLException e) {
			e.printStackTrace();
		}
	}
}
