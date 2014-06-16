package orre.gameWorld.services;

import orre.sceneGraph.Camera;
import orre.sceneGraph.ContainerNode;
import orre.util.Stack;

public class CameraService implements Service {

	private final Stack<Camera> cameraStack = new Stack<Camera>();
	private final ContainerNode cameraContainer;

	public CameraService(ContainerNode cameraContainer) {
		this.cameraContainer = cameraContainer;
		Camera defaultCamera = new Camera();
		activateCamera(defaultCamera);
	}
	
	public void activateCamera(Camera camera) {
		this.cameraContainer.removeChild(cameraStack.peek());
		this.cameraContainer.addChild(camera);
		this.cameraStack.push(camera);
	}
	
	public void deactivateCurrentCamera() {
		Camera currentCamera = this.cameraStack.pop();
		if(cameraStack.isEmpty()) {
			this.cameraStack.push(currentCamera);
			throw new RuntimeException("Cannot remove the default camera!");
		}
		this.cameraContainer.removeChild(currentCamera);
	}
	
	@Override
	public void tick() {
	}

}
