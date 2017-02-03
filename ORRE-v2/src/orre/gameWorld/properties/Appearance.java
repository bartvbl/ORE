package orre.gameWorld.properties;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.GraphicsObject;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyDataType;
import orre.geom.mesh.BlueprintModel;
import orre.geom.mesh.Mesh3D;
import orre.geom.mesh.Model;
import orre.resources.ResourceType;
import orre.resources.data.OBJBlueprintModel;

public abstract class Appearance extends Property {
	protected Model appearance;
	private final String meshName;
	private final ResourceType resourceType;

	public Appearance(Enum<?> type, ResourceType resourceType, String meshName, GameObject gameObject) {
		super(type, gameObject);
		this.meshName = meshName;
		this.resourceType = resourceType;
	}
	
	@Override
	public final void init() {
		this.appearance = ((BlueprintModel) gameObject.world.resourceService.getResource(resourceType, meshName)).createInstance();
		gameObject.takeControl(new GraphicsObject(appearance.getRootNode()));
		gameObject.setPropertyData(PropertyDataType.APPEARANCE, appearance);
		this.placeAppearanceInScene();
		this.initAppearance();
	}
	
	@Override
	public final void destroy() {
		this.removeAppearenceFromScene();
	}


	protected abstract void initAppearance();
	protected abstract void placeAppearanceInScene();
	protected abstract void removeAppearenceFromScene();

}
