package orre.gameWorld.properties;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyDataType;
import orre.gameWorld.core.PropertyType;
import orre.geom.Point3D;
import orre.geom.mesh.Mesh3D;

public class GravityProperty extends Property {

	public GravityProperty(GameObject gameObject) {
		super(PropertyType.GRAVITY, gameObject);
	}

	@Override
	public void handleMessage(Message<?> message) {
		
	}

	@Override
	public void tick() {
		Mesh3D appearance = (Mesh3D) gameObject.requestPropertyData(PropertyDataType.APPEARANCE, Mesh3D.class);
		Point3D location = appearance.root.getLocation();
		double tileHeight = this.gameObject.world.map.getTileHeightAt(location.x, location.y);
		appearance.root.setZ(tileHeight);
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init() {
		
	}

}
