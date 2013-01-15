package openrr.map;

import orre.geom.Dimension2D;
import orre.resources.loaders.map.MapTexturePack;
import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;

public class MapBuilder {

	public static SceneNode buildMapGeometry(MapTile[][] tileMap, MapTexturePack texturePack) {
		SceneNode mapRootNode = new EmptySceneNode();
		int mapWidth = tileMap.length;
		int mapHeight = tileMap[0].length;
		Dimension2D mapSize = new Dimension2D(mapWidth, mapHeight);
		//wallmap size: mapWidth + 1, mapHeight + 1
		boolean[][] wallMap = MapWallBuilder.createWallHeightMap(tileMap, mapSize);
		//wallTypeMap size: mapWidth, mapHeight
		WallType[][] wallTypeMap = WallTypeMapBuilder.buildWallTypeMap(wallMap, mapSize);
		//orientationMap size: mapWidth, mapHeight
		Orientation[][] orientationMap = MapWallOrientationBuilder.buildOrientationMap(wallMap, wallTypeMap, mapSize);
		return mapRootNode;
	}
	
}