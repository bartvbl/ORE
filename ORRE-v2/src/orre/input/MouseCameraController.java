package orre.input;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;

public class MouseCameraController extends Property {

	public MouseCameraController(GameObject gameObject) {
		super(PropertyType.MOUSE_CAMERA_CONTROLLER, gameObject);
	}

	@Override
	public void handleMessage(Message<?> message) {

	}

	@Override
	public void tick() {
	}

	@Override
	public void destroy() {

	}

}
