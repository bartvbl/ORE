package orre.ai.pathFinding.map;

import java.util.ArrayList;

import openrr.map.Map;
import openrr.map.MapTile;
import openrr.map.soil.SoilType;
import orre.ai.pathFinding.State;

public class MapTileNode implements State<MapTileNode> {
	public final int x;
	public final int y;
	public final Map map;
	
	public MapTileNode(Map map, int x, int y) {
		this.x = x;
		this.y = y;
		this.map = map;
	}

	@Override
	public ArrayList<State<MapTileNode>> getSuccessors() {
		ArrayList<State<MapTileNode>> successors = new ArrayList<State<MapTileNode>>();
		MapTile nodeTile = map.getTileAt(x, y);
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if(i == 0 && j == 0) {
					continue;
				}
				MapTile neighbour = map.getTileAt(x + i, y + j);
				if(neighbour == nodeTile) {
					continue;
				}
				if(neighbour.isWall()) {
					continue;
				}
				SoilType soil = neighbour.getSoilType();
				if(soil == SoilType.LAVA) {
					continue;
				}
				if(soil == SoilType.WATER) {
					continue;
				}
				successors.add(new MapTileNode(map, x + i, y + j));
			}
		}
		return successors;
	}

	@Override
	public double estimateHeuristicTo(MapTileNode other) {
		return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
	}

	@Override
	public double getDistanceToSuccessor(MapTileNode other) {
		double dx = this.x - other.x;
		double dy = this.y - other.y;
		return Math.sqrt(dx*dx + dy*dy);
	}

	@Override
	public boolean isEqualTo(MapTileNode otherState) {
		return (otherState.x == this.x) && (otherState.y == this.y);
	}
	
	public String toString() {
		return "Node at " + x + ", " + y;
	}

}
