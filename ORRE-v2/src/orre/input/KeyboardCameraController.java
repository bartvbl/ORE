package orre.input;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;
import orre.gameWorld.services.InputService;
import orre.gl.camera.Camera;

public class KeyboardCameraController extends Property {

	private Camera controlledCamera;
	private InputService service;

	public KeyboardCameraController(GameObject gameObject) {
		super(PropertyType.KEYBOARD_CAMERA_CONTROLLER, gameObject);
		gameObject.world.addMessageListener(MessageType.ASSUME_CAMERA_CONTROL, gameObject);
	}

	@Override
	public void handleMessage(Message<?> message) {
		if(message.type == MessageType.ASSUME_CAMERA_CONTROL) {
			this.controlledCamera = (Camera) message.getPayload();
			
			InputService service = this.gameObject.world.services.inputService;
			this.service = service;
		}
	}

	@Override
	public void tick() {
		//this.controlledCamera.setRotation(service.getMapRotationX(), 0, service.getMapRotationZ());
		//this.controlledCamera.setLocation(service.getMapX(), service.getMapY(), service.getMapZ());
	}
	
	@Override
	public void destroy() {}

	@Override
	public void init() {
		
	}

}
