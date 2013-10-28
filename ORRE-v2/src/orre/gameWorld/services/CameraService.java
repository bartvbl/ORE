package orre.gameWorld.services;

import openrr.map.Map;

import org.lwjgl.opengl.Display;

import orre.gl.util.CoordConverter;
import orre.sceneGraph.Camera;

public class CameraService implements Service {

	private Camera currentCamera = null;

	public void setCurrentCamera(Camera camera) {
		this.currentCamera = camera;
	}
	
	public void tick() {
		if(currentCamera != null) {
			currentCamera.transform();
		}
	}

}
