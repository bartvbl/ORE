package orre.devTools;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;
import orre.input.InputEvent;

public class DevTools extends Property {

	public DevTools(GameObject gameObject) {
		super(PropertyType.DEV_TOOLS, gameObject);
	}

	@Override
	public void handleMessage(Message<?> message) {
		InputEvent event = (InputEvent) message.getPayload();
		if(event.value == 0) {
			GameInfoWindow.showDebugInfo(this.gameObject.world);
		}
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init() {
		// Priority has an arbitrary low number in order to be first in line.
		this.gameObject.world.services.inputService.addCommandListener(this.gameObject.id, "showDebugTools", -150);
	}

}
