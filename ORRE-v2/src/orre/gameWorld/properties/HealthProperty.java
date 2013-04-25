package orre.gameWorld.properties;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;
import orre.gameWorld.core.PropertyDataType;

public class HealthProperty extends Property {

	public HealthProperty(GameObject object) {
		super(PropertyType.HEALTH, object);
	}

	public void handleMessage(Message<?> message) {
		
	}

	public void tick() {
		
	}

	public void destroy() {
		
	}

	public Object handlePropertyDataRequest(PropertyDataType type) {
		return null;
	}

}
