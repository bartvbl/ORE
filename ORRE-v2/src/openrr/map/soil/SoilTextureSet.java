package openrr.map.soil;

import openrr.map.WallType;
import orre.gl.texture.Texture;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;

public class SoilTextureSet {
	private WallType[] soilTextureTypes;
	private MapTexture[] mapTextures;
	private boolean compiled = false;

	public SoilTextureSet() {
		this.soilTextureTypes = WallType.values();
		this.mapTextures = new MapTexture[soilTextureTypes.length];
	}
	
	public void bindTexture(WallType type) {
		mapTextures[indexOf(type)].bind();
	}
	
	public void setTexture(WallType type, MapTexture mapTexture) {
		mapTextures[indexOf(type)] = mapTexture;
	}
	
	public boolean textureEquals(WallType type, MapTexture texture) {
		return mapTextures[indexOf(type)] == texture;
	}
	
	public void finalizeTextures() {
		if(compiled) return;
		for(int i = 0; i < mapTextures.length; i++) {
			mapTextures[i].finalizeTexture();
		}
		compiled = true;
	}
	
	public SoilTextureSet cloneTextureSet() {
		SoilTextureSet newSet = new SoilTextureSet();
		for(int i = 0; i < soilTextureTypes.length; i++) {
			newSet.setTexture(soilTextureTypes[i], mapTextures[i]);
		}
		return newSet;
	}
	
	private int indexOf(WallType type) {
		for(int i = 0; i < soilTextureTypes.length; i++) {
			if(type == soilTextureTypes[i]) return i;
		}
		return -1;
	}
}
