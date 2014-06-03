package orre.gameWorld.properties;

import orre.ai.tasks.CollectOreTask;
import orre.ai.tasks.Task;
import orre.ai.tasks.TaskType;
import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyDataType;
import orre.gameWorld.core.PropertyType;
import orre.geom.Point3D;
import orre.geom.mesh.Mesh3D;
import orre.gl.texture.Texture;
import orre.sceneGraph.SceneNode;

public class Transportable extends Property {
	private boolean isRegisteredForPickup = false;

	public Transportable(GameObject gameObject) {
		super(PropertyType.TRANSPORTABLE, gameObject);
	}

	@Override
	public void handleMessage(Message<?> message) {
		
	}

	@Override
	public void tick() {
		if(!isRegisteredForPickup) {
			Mesh3D appearance = (Mesh3D) gameObject.requestPropertyData(PropertyDataType.APPEARANCE, Mesh3D.class);
			Point3D location = appearance.root.getLocation();
			gameObject.world.services.aiService.registerTask(new CollectOreTask(gameObject.id, location.in2D()));
			isRegisteredForPickup = true;
		}
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init() {
		
	}
}
