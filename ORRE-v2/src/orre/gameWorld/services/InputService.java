package orre.gameWorld.services;

import java.util.HashMap;
import java.util.Set;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import orre.input.KeyListener;
import orre.sceneGraph.SceneNode;

public class InputService implements Service {
	
	private final HashMap<Integer, KeyListener> keyDownListeners = new HashMap<Integer, KeyListener>();

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
	
	public void setKeyDownListener(int keyID, KeyListener handler) {
		keyDownListeners.put(keyID, handler);
	}

	public void tick() {
		Set<Integer> registeredKeys = keyDownListeners.keySet();
		for(int registeredKey : registeredKeys) {
			if(Keyboard.isKeyDown(registeredKey)) {
				keyDownListeners.get(registeredKey).handleKey(registeredKey);
			}
		}
	}

}
