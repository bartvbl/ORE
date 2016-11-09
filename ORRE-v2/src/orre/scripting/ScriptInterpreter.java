package orre.scripting;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.python.core.PyDictionary;
import org.python.core.PyString;

import orre.events.EventHandler;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gameWorld.core.GameWorld;
import orre.threads.ScriptExecutionThread;

public class ScriptInterpreter implements EventHandler {
	private static ScriptInterpreter instance;
	private final ScriptExecutionThread scriptThread;
	private final ArrayList<String> pythonPathList = new ArrayList<String>();

	private ScriptInterpreter(GlobalEventDispatcher globalEventDispatcher) {
		this.scriptThread = new ScriptExecutionThread();
		new ScriptAPI(globalEventDispatcher);
		globalEventDispatcher.addEventListener(this, GlobalEventType.CHANGE_GAME_STATE);
	}
	
	public static ScriptInterpreter create(GlobalEventDispatcher eventDispatcher) {
		instance = new ScriptInterpreter(eventDispatcher);
		return instance;
	}
	
	public static ScriptInterpreter get() {
		return instance;
	}

	public void init() {
		scriptThread.start();
	}
	
	public void update() {
		ScriptAPI.runMainThreadCommand();
	}

	public void setCurrentWorld(GameWorld world) {
		ScriptAPI.setCurrentWorld(world);
	}
	
	public void execute(String pythonCode) {
		this.scriptThread.execute(pythonCode);
	}
	
	public void dispatchScriptEvent(String type, HashMap<String, String> parameters) {
		
		PyDictionary dict = new PyDictionary();
		for(String key : parameters.keySet()) {
			dict.put(key, parameters.get(key));
		}
		this.scriptThread.execute("orre_handleEvent('" + type + "', "+ dict.toString() +")");
	}

	@Override
	public void handleEvent(GlobalEvent<?> event) {
		if(event.eventType == GlobalEventType.CHANGE_GAME_STATE) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("newState", event.getEventParameterObject().toString());
			this.dispatchScriptEvent("gameStateChanged", params);
		}
	}

	public void addToPythonPath(File directory) {
		String directoryPath = directory.getAbsolutePath();
		// Fix for recognising windows paths
		directoryPath = directoryPath.replace('\\', '/');
		if(!pythonPathList.contains(directoryPath)) {
			this.scriptThread.execute("sys.path.append('" + directoryPath + "')");
			pythonPathList.add(directoryPath);
		}
	}
	
}
