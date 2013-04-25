package orre.gameWorld.services;

import orre.sceneGraph.Camera;

public class CameraService {

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
