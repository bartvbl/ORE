package orre.gameWorld.properties;

import org.lwjgl.input.Keyboard;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;
import orre.gameWorld.core.RequestedDataType;
import orre.gameWorld.services.InputService;
import orre.input.KeyListener;
import orre.sceneGraph.Camera;

public class CameraController extends Property implements KeyListener {

	private Camera controlledCamera;

	public CameraController(GameObject gameObject) {
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
			
			service.setKeyDownListener(Keyboard.KEY_W, this);
			service.setKeyDownListener(Keyboard.KEY_A, this);
			service.setKeyDownListener(Keyboard.KEY_S, this);
			service.setKeyDownListener(Keyboard.KEY_D, this);
		}
	}

	public void tick() {}
	public void destroy() {}

	public Object handlePropertyDataRequest(RequestedDataType type) {
		return null;
	}

	public void handleKey(int keyID) {
		switch(keyID) {
			case Keyboard.KEY_A:
				this.controlledCamera.translate(-1, 0, 0);
				return;
			case Keyboard.KEY_D:
				this.controlledCamera.translate(1, 0, 0);
				return;
			case Keyboard.KEY_W:
				this.controlledCamera.translate(0, 0, 1);
				return;
			case Keyboard.KEY_S:
				this.controlledCamera.translate(0, 0, -1);
				return;
			
			case Keyboard.KEY_LEFT:
				this.controlledCamera.rotate(0, 0.01f, 0);
				return;
			case Keyboard.KEY_RIGHT:
				this.controlledCamera.rotate(0, -0.01f, 0);
				return;
			case Keyboard.KEY_UP:
				this.controlledCamera.rotate(-0.01f, 0, 0);
				return;
			case Keyboard.KEY_DOWN:
				this.controlledCamera.rotate(0.01f, 0, 0);
				return;
		}
	}

}
