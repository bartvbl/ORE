package orre.gameWorld.properties;

import java.util.ArrayList;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;
import orre.gameWorld.services.InputService;
import orre.gl.lighting.Light;
import orre.gl.util.CoordConverter;
import orre.sceneGraph.SceneNode;

public class Flashlight extends Property {

	private final InputService service;
	private final Light light;

	public Flashlight(GameObject gameObject) {
		super(PropertyType.LIGHT, gameObject);
		this.light = new Light();
		this.service = this.gameObject.world.services.inputService;
		gameObject.world.mapContentsNode.addChild(light);
	}

	@Override
	public void handleMessage(Message<?> message) {
		
	}

	@Override
	public void tick() {
		float[] mapCoordinates = CoordConverter.getMapCoords((int)this.service.getMouseX(), (int)this.service.getMouseY());
		this.light.setPosition(mapCoordinates[0], mapCoordinates[1], mapCoordinates[2]);
	}

	@Override
	public void destroy() {
		
	}
}
