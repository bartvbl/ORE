package orre.gameWorld.properties;

import org.lwjgl.input.Keyboard;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;
import orre.gameWorld.core.PropertyDataType;
import orre.gameWorld.services.InputService;
import orre.input.KeyListener;
import orre.sceneGraph.Camera;

public class KeyboardCameraController extends Property implements KeyListener {

	private Camera controlledCamera;

	public KeyboardCameraController(GameObject gameObject) {
		super(PropertyType.CAMERA_CONTROLLER, gameObject);
		gameObject.world.addMessageListener(MessageType.ASSUME_CAMERA_CONTROL, gameObject);
	}

	public void handleMessage(Message<?> message) {
		if(message.type == MessageType.ASSUME_CAMERA_CONTROL) {
			this.controlledCamera = (Camera) message.getPayload();
			
			InputService service = this.gameObject.world.services.inputService;
			service.setKeyDownListener(Keyboard.KEY_LEFT, this);
			service.setKeyDownListener(Keyboard.KEY_RIGHT, this);
			service.setKeyDownListener(Keyboard.KEY_UP, this);
			service.setKeyDownListener(Keyboard.KEY_DOWN, this);
		}
	}

	public void tick() {}
	public void destroy() {}

	public Object handlePropertyDataRequest(PropertyDataType type) {
		return null;
	}

	public void handleKey(int keyID) {
		switch(keyID) {
			case Keyboard.KEY_LEFT:
				this.controlledCamera.translate(-1, 0, 0);
				return;
			case Keyboard.KEY_RIGHT:
				this.controlledCamera.translate(1, 0, 0);
				return;
			case Keyboard.KEY_UP:
				this.controlledCamera.translate(0, 1, 0);
				return;
			case Keyboard.KEY_DOWN:
				this.controlledCamera.translate(0, -1, 0);
				return;
		}
	}

}
