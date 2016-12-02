package orre.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import orre.gameWorld.core.GameWorld;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;

public class CommandDispatcher {

	private final GameWorld world;
	private final HashMap<String, ArrayList<CommandHandler>> commandMap;
	private final ArrayList<Runnable> postponedTaskList = new ArrayList<Runnable>();
	
	private class CommandHandler {
		public final int priority;
		public final int gameObjectID;
		
		public CommandHandler(int priority, int gameObjectID) {
			this.priority = priority;
			this.gameObjectID = gameObjectID;
		}
	}

	public CommandDispatcher(final GameWorld world) {
		this.world = world;
		this.commandMap = new HashMap<String, ArrayList<CommandHandler>>();
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
			ArrayList<CommandHandler> handlers = commandMap.get(command);
			Collections.sort(handlers, new Comparator<CommandHandler>() {
				@Override
				public int compare(CommandHandler left, CommandHandler right) {
					return Integer.compare(left.priority, right.priority);
				}
			});
			for(int i = 0; i < handlers.size(); i++) {
				int gameObjectID = handlers.get(i).gameObjectID;
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

	public void addInputEventListener(final String command, final int gameObjectID, final int priority) {
		postponedTaskList.add(new Runnable() {
			public void run() {
				if(!commandMap.containsKey(command)) {
					commandMap.put(command, new ArrayList<CommandHandler>());
				}
				commandMap.get(command).add(new CommandHandler(priority, gameObjectID));
			}
		});
	}

	public void removeInputEventListener(final String command, final int gameObjectID) {
		postponedTaskList.add(new Runnable() {
			public void run() {
				if(commandMap.containsKey(command)) {
					ArrayList<CommandHandler> objects = commandMap.get(command);
					int index = -1;
					for(int i = 0; i < objects.size(); i++) {
						if(objects.get(i).gameObjectID == gameObjectID) {
							index = i;
							break;
						}
					}
					if(index != -1) {
						commandMap.get(command).remove(index);
					}
				}
			}
		});
	}
}
