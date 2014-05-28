package orre.gameStates;

import java.nio.FloatBuffer;

import openrr.test.LightTestClass;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import orre.core.GameMain;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.geom.mesh.Mesh3D;
import orre.gl.RenderUtils;
import orre.gl.renderer.RenderPass;
import orre.gl.texture.Texture;
import orre.resources.UnloadedResource;
import orre.resources.ResourceCache;
import orre.resources.ResourceFile;
import orre.sceneGraph.SceneNode;

import static org.lwjgl.opengl.GL11.*;

public class MainMenu extends GameState {

	public MainMenu(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceCache cache) {
		super(main, eventDispatcher, cache);
		UnloadedResource mainCache = new UnloadedResource(ResourceFile.RESOURCE_LIST_FILE, this.resourceCache, "res/reslist.xml", "mainCacheList");
		eventDispatcher.dispatchEvent(new GlobalEvent<UnloadedResource>(GlobalEventType.ENQUEUE_STARTUP_LOADING_ITEM, mainCache));
		
	}
	
	public void executeFrame(long frameNumber) {
		
	}

	public void set() {
		UnloadedResource mapFile = new UnloadedResource(ResourceFile.MAP_FILE, this.resourceCache, "res/maps/sampleMap.rrm", "map");
		this.globalEventDispatcher.dispatchEvent(new GlobalEvent<UnloadedResource>(GlobalEventType.ENQUEUE_GAME_LOADING_ITEM, mapFile));
		this.globalEventDispatcher.dispatchEvent(new GlobalEvent<GameStateName>(GlobalEventType.CHANGE_GAME_STATE, GameStateName.GAME_LOADING));
	}
	public void unset() {}
}
