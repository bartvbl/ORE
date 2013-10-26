package orre.gameWorld.properties;

import java.util.ArrayList;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;
import orre.gameWorld.services.InputService;
import orre.gl.lighting.Light;
import orre.sceneGraph.SceneNode;

public class Flashlight extends Property {

	private final InputService service;
	private final Light light;

	public Flashlight(GameObject gameObject) {
		super(PropertyType.LIGHT, gameObject);
		this.light = new Light();
		this.service = this.gameObject.world.services.inputService;
		gameObject.world.rootNode.addChild(light);
		light.setPosition(0, 0, 20);
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
