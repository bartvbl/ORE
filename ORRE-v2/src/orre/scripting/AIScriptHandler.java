package orre.scripting;

import orre.ai.tasks.Task;
import orre.gameWorld.core.GameWorld;

public class AIScriptHandler {
	private static GameWorld gameWorld;
	
	public void setCurrentWorld(GameWorld world) {
		gameWorld = world;
	}
	
	public static void show(final Task task) {
		ScriptAPI.runOnMainThread(new Runnable() {
			@Override
			public void run() {
				gameWorld.services.aiService.registerTask(task);
			}
		});
	}
}
