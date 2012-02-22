package orre.gameStates;

import org.lwjgl.input.Mouse;

import orre.core.GameMain;
import orre.events.Event;
import orre.events.EventDispatcher;
import orre.events.GlobalEventType;
import orre.geom.mesh.Mesh3D;
import orre.gl.RenderUtils;
import orre.resources.FileToLoad;
import orre.resources.ResourceFile;

import static org.lwjgl.opengl.GL11.*;

public class MainMenu extends GameState {

	private Mesh3D testNode;
	private int rotation;
	public MainMenu(GameMain main, EventDispatcher eventDispatcher, GameState.State stateName) {
		super(main, eventDispatcher, stateName);
		FileToLoad mainCache = new FileToLoad(ResourceFile.RESOURCE_LIST_FILE, this.resourceCache, "res/reslist.xml");
		eventDispatcher.dispatchEvent(new Event<FileToLoad>(GlobalEventType.ENQUEUE_STARTUP_LOADING_ITEM, mainCache));
	}
	public void executeFrame(long frameNumber) {
		RenderUtils.set3DMode();
		this.rotation += 3;
		glRotatef(rotation, 1, 1, 0);
		glTranslatef(0, 0, 10);
		glColor4f(1, 1, 1, 1);
		this.testNode.render();
	}
	@Override
	public void set() {
		
		this.testNode = this.resourceCache.createModelInstace("rockRaider");
	}
	@Override
	public void unset() {
		// TODO Auto-generated method stub
		
	}



}
