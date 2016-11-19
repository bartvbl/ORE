package orre.gameWorld.services;

import orre.gameWorld.core.GameWorld;
import orre.resources.ResourceCache;
import orre.sceneGraph.ContainerNode;
import orre.scripting.ScriptInterpreter;

public class WorldServices {
	public final AnimationService animationService;
	public final AIService aiService;
	public final SoundService soundService;
	public final InputService inputService;
	public final ScriptingService scriptingService;

	public WorldServices(GameWorld world, ResourceCache cache, ScriptInterpreter interpreter) {
		this.animationService = new AnimationService(world);
		this.aiService = new AIService(world);
		this.soundService = new SoundService();
		this.inputService = new InputService(world, cache);
		this.scriptingService = new ScriptingService(world, interpreter);
	}
	
	public void tickServices() {
		this.aiService.tick();
		this.soundService.tick();
		this.inputService.tick();
		this.scriptingService.tick();
		this.animationService.tick();
	}
	
	public void shutdown() {
		aiService.stop();
	}
}
