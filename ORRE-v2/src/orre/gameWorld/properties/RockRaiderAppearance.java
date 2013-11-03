package orre.gameWorld.properties;

import orre.animation.AnimationType;
import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.GraphicsObject;
import orre.gameWorld.core.GraphicsObjectType;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyDataType;
import orre.gameWorld.core.PropertyType;
import orre.geom.mesh.Mesh3D;

public class RockRaiderAppearance extends Appearance {

	public RockRaiderAppearance(GameObject gameObject) {
		super(PropertyType.ROCK_RAIDER_APPEARANCE, "rockRaider", gameObject);
		appearance.root.setLocation(3, 3, 0);
		gameObject.world.services.animationService.applyAnimation(AnimationType.raiderWalking, appearance);
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
