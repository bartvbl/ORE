package orre.input;

import java.util.ArrayList;
import java.util.HashMap;

import orre.gameWorld.core.GameWorld;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;

public class CommandDispatcher {

	private final GameWorld world;
	private final HashMap<String, ArrayList<Integer>> commandMap;

	public CommandDispatcher(final GameWorld world) {
		this.world = world;
		this.commandMap = new HashMap<String, ArrayList<Integer>>();
	}

	public void dispatchCommand(final InputEvent event) {
		for(String command : KeyBindings.getBindings(event.type)) {
			Message<InputEvent> message = new Message<InputEvent>(MessageType.INPUT_EVENT, event);
			for(int gameObjectID : commandMap.get(command)) {
				world.dispatchMessage(message, gameObjectID);
				if(event.isConsumed()) {
					break;
				}
			}
		}
	}

	public void addInputEventListener(final String command, final int gameObjectID) {
		if(!commandMap.containsKey(command)) {
			commandMap.put(command, new ArrayList<Integer>());
		}
		this.commandMap.get(command).add(gameObjectID);
	}
	
	public void removeInputEventListener(String command, int gameObjectID) {
		if(commandMap.containsKey(command)) {
			commandMap.get(command).remove(gameObjectID);
		}
	}
}
