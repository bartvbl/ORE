package openrr.map.loader;

import java.util.HashMap;
import java.util.Set;

import openrr.map.soil.MapTexture;

import orre.gl.texture.Texture;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;

public class MapTextureSet {
	private final HashMap<String, MapTexture> uncompiledTextureSet;
	private final HashMap<String, MapTexture> compiledTextureSet;

	public MapTextureSet() {
		this.uncompiledTextureSet = new HashMap<String, MapTexture>();
		this.compiledTextureSet = new HashMap<String, MapTexture>();
	}
	
	public void addTexture(MapTexture texture) {
		this.uncompiledTextureSet.put(texture.name, texture);
	}

	public MapTexture getTextureByName(String name) {
		return compiledTextureSet.get(name);
	}
	
	public void finalizeTextures() {
		Set<String> keySet = uncompiledTextureSet.keySet();
		for(String key : keySet) {
			MapTexture uncompiledTexture = uncompiledTextureSet.get(key);
			uncompiledTextureSet.remove(key);
			uncompiledTexture.finalizeTexture();
			compiledTextureSet.put(key, uncompiledTexture);
		}
	}
}
