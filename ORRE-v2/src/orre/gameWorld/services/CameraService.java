package orre.gameWorld.services;

import openrr.map.Map;

import org.lwjgl.opengl.Display;

import orre.gameWorld.core.GameWorld;
import orre.gl.util.CoordConverter;
import orre.sceneGraph.Camera;

public class CameraService implements Service {

	private Camera currentCamera = null;
	private GameWorld world;

	public void setCurrentCamera(Camera camera, GameWorld world) {
		this.currentCamera = camera;
		this.world = world;
	}
	
	public void tick() {
		if(currentCamera != null) {
			
			currentCamera.transform(world.map.getTileHeightAt(world.services.inputService.getMapX(), world.services.inputService.getMapY()));
		}
	}

}
