package orre.gameWorld.chaining;

import java.util.HashMap;

import orre.gameWorld.core.GameWorld;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageHandler;
import orre.gameWorld.core.MessageType;

public class ChainUtil implements MessageHandler {

	private static GameWorld world;
	private static ChainUtil instance;
	// Assumption: one chain function per animation
	private HashMap<Integer, Runnable> animationRunnables = new HashMap<Integer, Runnable>();

	public static void onAnimationComplete(int playheadID, Runnable runnable) {
		instance.animationRunnables.put(playheadID, runnable);
	}

	public static void init(GameWorld gameWorld) {
		instance = new ChainUtil();
		world = gameWorld;
		world.addMessageListener(MessageType.ANIMATION_ENDED, instance);
	}

	@Override
	public void handleMessage(Message<?> message) {
		if(message.type == MessageType.ANIMATION_ENDED) {
			int playheadID = (Integer) message.getPayload();
			if(animationRunnables.containsKey(playheadID)) {
				animationRunnables.get(playheadID).run();
				animationRunnables.remove(playheadID);
			}
		}
	}

}
