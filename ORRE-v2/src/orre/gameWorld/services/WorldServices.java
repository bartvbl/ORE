package orre.gameWorld.services;

public class WorldServices {
	public final AnimationService animationService;
	public final AIService aiService;
	public final SoundService soundService;
	public final InputService inputService;
	public final CameraService cameraService;

	public WorldServices() {
		this.animationService = new AnimationService();
		this.aiService = new AIService();
		this.soundService = new SoundService();
		this.inputService = new InputService();
		this.cameraService = new CameraService();
	}
	
	public void tickServices() {
		this.cameraService.tick();
		this.animationService.tick();
		this.aiService.tick();
		this.soundService.tick();
		this.inputService.tick();
	}
}
