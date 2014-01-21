package orre.gameWorld.properties;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;

public class Location extends Property {

	public Location(GameObject gameObject) {
		super(PropertyType.LOCATION, gameObject);
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
