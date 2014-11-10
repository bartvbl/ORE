package orre.gameWorld.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import orre.api.PropertyTypeProvider;
import orre.gameWorld.services.WorldServices;
import orre.gl.shaders.ShaderNode;
import orre.resources.ResourceCache;
import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneNode;
import orre.scripting.ScriptInterpreter;
import orre.util.ArrayUtils;
import orre.util.Queue;

public class GameWorld {
	public final SceneNode scene3DRoot;
	public final WorldServices services;
	public final ResourceCache resourceCache;
	public final ContainerNode sceneRoot;

	private final HashMap<Integer, GameObject> gameObjectSet;
	private final HashMap<Enum<?>, int[]> propertyMap;
	private final HashMap<Enum<?>, int[]> objectTypeMap;
	private final HashMap<MessageType, ArrayList<MessageHandler>> messageListeners;
	private final Queue<Integer> despawnQueue;
	
	public GameWorld(ResourceCache cache, ScriptInterpreter interpreter, ContainerNode shader, ContainerNode rootNode, ContainerNode cameraContainer) {
		this.scene3DRoot = shader;//new ContainerNode("Scene root");
		this.sceneRoot = rootNode;
		this.services = new WorldServices(this, cache, interpreter, cameraContainer);
		this.gameObjectSet = new HashMap<Integer, GameObject>();
		this.messageListeners = new HashMap<MessageType, ArrayList<MessageHandler>>();
		this.propertyMap = new HashMap<Enum<?>, int[]>();
		this.objectTypeMap = new HashMap<Enum<?>, int[]>();
		this.despawnQueue = new Queue<Integer>();
		this.resourceCache = cache;
	}
	
	public int spawnGameObject(Enum<?> gameObjectType) {
		GameObject object = GameObjectBuilder.buildGameObjectByType(gameObjectType, this);
		registerGameObject(object);
		return object.id;
	}

	private void registerGameObject(GameObject object) {
		gameObjectSet.put(object.id, object);
		Enum<?>[] properties = object.getAttachedPropertyTypes();
		for(Enum<?> propertyType : properties) {
			if(!propertyMap.containsKey(propertyType)) {
				propertyMap.put(propertyType, new int[]{object.id});
			} else {
				int[] idArray = propertyMap.get(propertyType);
				int[] appendedArray = ArrayUtils.append(idArray, object.id);
				propertyMap.put(propertyType, appendedArray);
			}
		}
		if(!objectTypeMap.containsKey(object.type)) {
			objectTypeMap.put(object.type, new int[]{object.id});
		} else {
			int[] idArray = objectTypeMap.get(object.type);
			int[] appendedArray = ArrayUtils.append(idArray, object.id);
			objectTypeMap.put(object.type, appendedArray);
		}
	}
	
	public void despawnObject(int objectID) {
		despawnQueue.enqueue(objectID);
	}
	
	public int[] getAllGameObjectsByType(Enum<?> type) {
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
		ArrayList<MessageHandler> listenerList = messageListeners.get(message.type);
		for(MessageHandler listener : listenerList) {
			listener.handleMessage(message);
		}
	}
	
	public void dispatchMessage(Message<?> message, int destinationObject) {
		GameObject object = gameObjectSet.get(destinationObject);
		object.handleMessage(message);
	}
	
	public void addMessageListener(MessageType type, MessageHandler listener) {
		if(!messageListeners.containsKey(type)) {
			ArrayList<MessageHandler> objectList = new ArrayList<MessageHandler>();
			messageListeners.put(type, objectList);
		}
		ArrayList<MessageHandler> listenerList = messageListeners.get(type);
		listenerList.add(listener);
	}

	public void tick() {
		this.services.tickServices();
		Collection<GameObject> gameObjects = gameObjectSet.values();
		for(GameObject gameObject : gameObjects) {
			gameObject.tick();
		}
		while(!despawnQueue.isEmpty()) {
			int objectID = despawnQueue.dequeue();
			GameObject object = gameObjectSet.remove(objectID);
			if(object != null) {
				object.destroy();
			}
		}
	}
	
	public Object requestPropertyData(int target, Enum<?> propertyDataType, Object defaultValue, Class<?> expectedDataType) {
		try {
			GameObject targetObject = this.gameObjectSet.get(target);
			Object returnedData = targetObject.requestPropertyData(propertyDataType, expectedDataType);
			if(returnedData == null) {
				return defaultValue;
			}
			return returnedData;
		}
		catch(Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public Collection<GameObject> debugonly_getAllGameOjects() {
		return gameObjectSet.values();
	}

	public static void setPropertyTypeProvider(PropertyTypeProvider provider) {
		GameObjectBuilder.setPropertyTypeProvider(provider);
	}

	public void api_spawnGameObjectFromString(String gameObjectType) {
		Enum<?> type = GameObjectBuilder.getGameObjectTypeFromString(gameObjectType);
		spawnGameObject(type);
	}

	public int getOnlyGameObject(Enum<?> gameObjectType) {
		return objectTypeMap.get(gameObjectType)[0];
	}
}
