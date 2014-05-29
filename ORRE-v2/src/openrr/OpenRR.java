package openrr;

import openrr.map.loader.MapLoader;
import orre.api.ORRE;

public class OpenRR {
	public static void main(String[] args) {
		ORRE engine = ORRE.init(args, "Terrain");
		engine.registerResourceLoader(new MapLoader(), new String[]{".rrm"});
		engine.run();
	}
}