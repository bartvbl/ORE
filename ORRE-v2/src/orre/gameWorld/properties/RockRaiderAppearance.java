package orre.gameWorld.properties;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.GraphicsObject;
import orre.gameWorld.core.GraphicsObjectType;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;
import orre.geom.mesh.Mesh3D;

public class RockRaiderAppearance extends Property {

	private final Mesh3D raiderObject;

	public RockRaiderAppearance(GameObject gameObject) {
		super(PropertyType.ROCK_RAIDER_APPEARANCE, gameObject);
		Mesh3D rockRaiderObject = gameObject.world.resourceCache.createModelInstace("rockRaider");
		rockRaiderObject.setLocation(3, 3, 0);
		gameObject.takeControl(new GraphicsObject(GraphicsObjectType.BODY, rockRaiderObject));
		gameObject.world.mapContentsNode.addChild(rockRaiderObject);
		this.raiderObject = rockRaiderObject;
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
