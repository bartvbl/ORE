package openrr.map;

import orre.resources.loaders.map.MapTexturePack;
import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;

public class MapBuilder {

	public static SceneNode buildMapGeometry(MapTile[][] tileMap, MapTexturePack texturePack) {
		SceneNode mapRootNode = new EmptySceneNode();
		int mapWidth = tileMap.length;
		int mapHeight = tileMap[0].length;
		boolean[][] wallMap = MapWallBuilder.createWallHeightMap(tileMap);
		WallType[][] wallTypeMap = WallTypeMapBuilder.buildWallTypeMap(wallMap);
		Orientation[][] orientationMap = MapWallOrientationBuilder.buildOrientationMap();
		return mapRootNode;
	}
	
}