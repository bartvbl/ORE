package orre.gameWorld.services;

public class WorldServices {
	public final AnimationService animationService;
	public final AIService aiService;
	public final SoundService soundService;

	public WorldServices() {
		this.animationService = new AnimationService();
		this.aiService = new AIService();
		this.soundService = new SoundService();
	}
	
	public void tickServices() {
		this.animationService.tick();
		this.aiService.tick();
		this.soundService.tick();
	}
}
