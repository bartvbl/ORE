package orre.input;

import java.util.HashMap;

import orre.gameWorld.core.GameWorld;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;
import orre.util.ArrayUtils;

public class CommandDispatcher {

	private final GameWorld world;
	private final HashMap<String, int[]> commandMap;

	public CommandDispatcher(final GameWorld world) {
		this.world = world;
		this.commandMap = new HashMap<String, int[]>();
	}

	public void dispatchCommand(final InputEvent event) {
		final Message<InputEvent> message = new Message<InputEvent>(MessageType.INPUT_EVENT, event);
		for(final String command : KeyBindings.getBindings(event.type)) {
			for(final int gameObjectID : commandMap.get(command)) {
				world.dispatchMessage(message, gameObjectID);
				if(event.isConsumed()) {
					return;
				}
			}
		}
	}

	public void addInputEventListener(final String command, final int gameObjectID) {
		if(!commandMap.containsKey(command)) {
			commandMap.put(command, new int[]{gameObjectID});
		} else {
			this.commandMap.put(command, ArrayUtils.append(this.commandMap.get(command), gameObjectID));
		}
	}
}
