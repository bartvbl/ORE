package orre.gameWorld.properties;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.PropertyType;

public class ChrystalAppearance extends Appearance {

	public ChrystalAppearance(GameObject gameObject) {
		super(PropertyType.CHRYSTAL_APPEARANCE, "chrystal", gameObject);
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

	@Override
	protected void initAppearance() {
		
	}

}
