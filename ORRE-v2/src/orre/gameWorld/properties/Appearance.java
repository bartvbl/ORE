package orre.gameWorld.properties;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.GraphicsObject;
import orre.gameWorld.core.GraphicsObjectType;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyDataType;
import orre.gameWorld.core.PropertyType;
import orre.geom.mesh.Mesh3D;

public abstract class Appearance extends Property {
	protected final Mesh3D appearance;

	public Appearance(PropertyType type, String meshName, GameObject gameObject) {
		super(type, gameObject);
		this.appearance = gameObject.world.resourceCache.createModelInstace(meshName);
		gameObject.takeControl(new GraphicsObject(GraphicsObjectType.BODY, appearance.root));
		gameObject.world.mapContentsNode.addChild(appearance.root);
		gameObject.setPropertyData(PropertyDataType.APPEARANCE, appearance);
	}

}
