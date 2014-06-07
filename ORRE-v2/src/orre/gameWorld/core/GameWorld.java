package orre.gameWorld.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import openrr.map.Map;
import orre.api.PropertyTypeProvider;
import orre.gameWorld.services.WorldServices;
import orre.resources.ResourceCache;
import orre.sceneGraph.SceneNode;
import orre.scripting.ScriptInterpreter;

public class GameWorld {
	public final SceneNode rootNode;
	public final SceneNode mapContentsNode;
	public final Map map;
	public final WorldServices services;
	public final ResourceCache resourceCache;

	private final HashMap<Integer, GameObject> gameObjectSet;
	private final HashMap<MessageType, ArrayList<GameObject>> messageListeners;
	
	public GameWorld(SceneNode rootNode, SceneNode mapContentsNode, Map map, ResourceCache cache, ScriptInterpreter interpreter) {
		this.rootNode = rootNode;
		this.map = map;
		this.mapContentsNode = mapContentsNode;
		this.services = new WorldServices(this, cache, interpreter);
		this.gameObjectSet = new HashMap<Integer, GameObject>();
		this.messageListeners = new HashMap<MessageType, ArrayList<GameObject>>();
		this.resourceCache = cache;
	}
	
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
	
	public void dispatchMessage(Message<?> message) {
		ArrayList<GameObject> listenerList = messageListeners.get(message.type);
		for(GameObject listener : listenerList) {
			listener.handleMessage(message);
		}
	}
	
	public void dispatchMessage(Message<?> message, int destinationObject) {
		GameObject object = gameObjectSet.get(destinationObject);
		object.handleMessage(message);
	}
	
	public void addMessageListener(MessageType type, GameObject listener) {
		if(!messageListeners.containsKey(type)) {
			ArrayList<GameObject> objectList = new ArrayList<GameObject>();
			messageListeners.put(type, objectList);
		}
		ArrayList<GameObject> listenerList = messageListeners.get(type);
		listenerList.add(listener);
	}

	public void tick() {
		this.services.tickServices();
		this.map.tick();
		Collection<GameObject> gameObjects = gameObjectSet.values();
		for(GameObject gameObject : gameObjects) {
			gameObject.tick();
		}
	}
	
	public Object requestPropertyData(int target, PropertyDataType type, Object defaultValue, Class<?> expectedDataType) {
		try {
			GameObject targetObject = this.gameObjectSet.get(target);
			Object returnedData = targetObject.requestPropertyData(type, expectedDataType);
			if(returnedData == null) {
				return defaultValue;
			}
			return returnedData;
		}
		catch(Exception e) {
			return defaultValue;
		}
	}

	public Collection<GameObject> debugonly_getAllGameOjects() {
		return gameObjectSet.values();
	}

	public static void setPropertyTypeProvider(PropertyTypeProvider provider) {
		
	}
}
