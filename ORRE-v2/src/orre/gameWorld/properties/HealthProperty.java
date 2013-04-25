package orre.gameWorld.properties;

import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;
import orre.gameWorld.core.RequestedDataType;

public class HealthProperty extends Property {

	public HealthProperty() {
		super(PropertyType.HEALTH);
	}

	public void handleMessage(Message<?> message) {
		
	}

	public void tick() {
		
	}

	public void destroy() {
		
	}

	public Object handlePropertyDataRequest(RequestedDataType type) {
		return null;
	}

}
