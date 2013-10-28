package orre.gameWorld.properties;

import openrr.map.MapTile;
import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.GraphicsObject;
import orre.gameWorld.core.GraphicsObjectType;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;
import orre.geom.Point3D;
import orre.sceneGraph.CoordinateNode;

public class GravityProperty extends Property {

	public GravityProperty(GameObject gameObject) {
		super(PropertyType.GRAVITY, gameObject);
	}

	@Override
	public void handleMessage(Message<?> message) {
		
	}

	@Override
	public void tick() {
		for(int i = 0; i < this.gameObject.getGraphicsObjectCount(); i++) {
			GraphicsObject currentGraphicsObject = this.gameObject.getGraphicsObject(i);
			if(currentGraphicsObject.type == GraphicsObjectType.BODY) {
				CoordinateNode node = (CoordinateNode) currentGraphicsObject.rootNode;
				Point3D location = node.getLocation();
				double tileHeight = this.gameObject.world.map.getTileHeightAt(location.x, location.y);
				node.setZ(tileHeight);
			}
		}
	}

	@Override
	public void destroy() {
		
	}

}
