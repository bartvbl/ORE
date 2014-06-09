package orre.gameWorld.properties;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.GraphicsObject;
import orre.gameWorld.core.GraphicsObjectType;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyDataType;
import orre.gameWorld.core.PropertyType;
import orre.geom.mesh.Mesh3D;
import orre.resources.ResourceType;
import orre.resources.data.BlueprintModel;

public abstract class Appearance extends Property {
	protected Mesh3D appearance;
	private final String meshName;

	public Appearance(Enum<?> type, String meshName, GameObject gameObject) {
		super(type, gameObject);
		this.meshName = meshName;
	}
	
	public void init() {
		this.appearance = ((BlueprintModel) gameObject.world.resourceCache.getResource(ResourceType.model, meshName).content).createMesh();
		gameObject.takeControl(new GraphicsObject(GraphicsObjectType.BODY, appearance.root));
		//gameObject.world.mapContentsNode.addChild(appearance.root);
		gameObject.setPropertyData(PropertyDataType.APPEARANCE, appearance);
		this.initAppearance();
	}

	protected abstract void initAppearance();

}
