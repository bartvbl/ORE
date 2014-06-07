package openrr;

import openrr.world.core.ORRGameObjectType;
import openrr.world.core.ORRPropertyDataType;
import openrr.world.core.ORRPropertyType;
import orre.api.PropertyTypeProvider;


public class OpenRRPropertyProvider implements PropertyTypeProvider<ORRGameObjectType, ORRPropertyType, ORRPropertyDataType> {

	@Override
	public ORRGameObjectType[] getGameObjectTypes() {
		return ORRGameObjectType.values();
	}

	@Override
	public ORRPropertyType[] getProperties(ORRGameObjectType gameObjectType) {
		return null;
	}

	@Override
	public Class<?> getPropertyClass(ORRPropertyType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getRequiredDataType(ORRPropertyDataType dataType) {
		// TODO Auto-generated method stub
		return null;
	}


}
