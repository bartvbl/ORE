package orre.input;

import java.util.ArrayList;
import java.util.HashMap;

import orre.gameWorld.core.GameWorld;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;

public class CommandDispatcher {

	private final GameWorld world;
	private final HashMap<String, ArrayList<Integer>> commandMap;
	private final ArrayList<Runnable> postponedTaskList = new ArrayList<Runnable>();

	public CommandDispatcher(final GameWorld world) {
		this.world = world;
		this.commandMap = new HashMap<String, ArrayList<Integer>>();
	}

	public void dispatchCommand(KeyType type, double value, double delta) {
		if(!KeyBindings.hasBindingFor(type)) {
			return;
		}
		for(String command : KeyBindings.getBindings(type)) {
			final InputEvent event = new InputEvent(command, value, delta);
			Message<InputEvent> message = new Message<InputEvent>(MessageType.INPUT_EVENT, event);
			if(!commandMap.containsKey(command)) {
				continue;
			}
			//commandMap can be modified while handling an event.
			//handlers are intended to only concern themselves; not registering handlers on other objects.
			//This is assumed to be the case here.
			ArrayList<Integer> handlers = commandMap.get(command);
			for(int i = 0; i < handlers.size(); i++) {
				int gameObjectID = handlers.get(i);
				world.dispatchMessage(message, gameObjectID);
				if(event.isConsumed()) {
					break;
				}
			}
		}
		for(Runnable postponedTask : postponedTaskList) {
			postponedTask.run();
		}
		postponedTaskList.clear();
	}

	public void addInputEventListener(final String command, final int gameObjectID) {
		postponedTaskList.add(new Runnable() {
			public void run() {
				if(!commandMap.containsKey(command)) {
					commandMap.put(command, new ArrayList<Integer>());
				}
				commandMap.get(command).add(gameObjectID);
			}
		});
	}

	public void removeInputEventListener(final String command, final int gameObjectID) {
		postponedTaskList.add(new Runnable() {
			public void run() {
				if(commandMap.containsKey(command)) {
					int objectIndex = commandMap.get(command).indexOf(gameObjectID);
					commandMap.get(command).remove(objectIndex);
				}
			}
		});
	}
}
