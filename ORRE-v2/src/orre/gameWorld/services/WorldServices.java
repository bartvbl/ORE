package orre.gameWorld.services;

import orre.gameWorld.core.GameWorld;
import orre.resources.ResourceCache;

public class WorldServices {
	public final AnimationService animationService;
	public final AIService aiService;
	public final SoundService soundService;
	public final InputService inputService;
	public final CameraService cameraService;

	public WorldServices(GameWorld world, ResourceCache cache) {
		this.animationService = new AnimationService(world);
		this.aiService = new AIService(world);
		this.soundService = new SoundService();
		this.inputService = new InputService(world, cache);
		this.cameraService = new CameraService();
	}
	
	public void tickServices() {
		this.cameraService.tick();
		this.animationService.tick();
		this.aiService.tick();
		this.soundService.tick();
		this.inputService.tick();
	}

	public void shutdown() {
		aiService.stop();
	}
}
