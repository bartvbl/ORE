package orre.gameWorld.properties;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.PropertyType;

public class OreAppearance extends Appearance {
	public OreAppearance(GameObject gameObject) {
		super(PropertyType.ORE_APPEARANCE, "ore", gameObject);
		this.appearance.root.setLocation(48.5, 50.5, 0);
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
