package orre.gameWorld.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class GameWorld {
	private final HashMap<Integer, GameObject> gameObjectSet = new HashMap<Integer, GameObject>();
	private final HashMap<MessageType, ArrayList<GameObject>> messageListeners = new HashMap<MessageType, ArrayList<GameObject>>();
	
	public int spawnGameObject(GameObjectType type) {
		GameObject object = GameObjectBuilder.buildGameObjectByType(type, this);
		gameObjectSet.put(object.id, object);
		return object.id;
	}
	
	public void despawnObject(int objectID) {
		GameObject object = gameObjectSet.remove(objectID);
		if(object != null) {
			object.destroy();
		}
	}
	
	public int[] getAllGameObjectsByType(GameObjectType type) {
		ArrayList<Integer> objectIDs = new ArrayList<Integer>();
		Collection<GameObject> gameObjects = gameObjectSet.values();
		for(GameObject object : gameObjects) {
			if(object.type == type) {
				objectIDs.add(object.id);
			}
		}
		Integer[] idList = objectIDs.toArray(new Integer[objectIDs.size()]);
		int[] outputList = new int[idList.length];
		for(int i = 0; i < idList.length; i++) {
			outputList[i] = idList[i].intValue();
		}
		return outputList;
	}
	
	public void dispatchMessage(Message message) {
		ArrayList<GameObject> listenerList = messageListeners.get(message.type);
		for(GameObject listener : listenerList) {
			listener.handleMessage(message);
		}
	}
	
	public void dispatchMessage(Message message, int destinationObject) {
		GameObject object = gameObjectSet.get(destinationObject);
		object.handleMessage(message);
	}
	
	public void addMessageListener(MessageType type, GameObject object) {
		if(!messageListeners.containsKey(type)) {
			ArrayList<GameObject> objectList = new ArrayList<GameObject>();
			messageListeners.put(type, objectList);
		}
		ArrayList<GameObject> listenerList = messageListeners.get(type);
		listenerList.add(object);
	}

	public void tick() {
		Collection<GameObject> gameObjects = gameObjectSet.values();
		for(GameObject gameObject : gameObjects) {
			gameObject.tick();
		}
	}
}