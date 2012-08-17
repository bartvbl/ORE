package orre.gameStates;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
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
	private float rotationX, rotationY;
	
	private FloatBuffer buffer;
	
	public MainMenu(GameMain main, EventDispatcher eventDispatcher, GameState.State stateName) {
		super(main, eventDispatcher, stateName);
		this.buffer = BufferUtils.createFloatBuffer(4);
		FileToLoad mainCache = new FileToLoad(ResourceFile.RESOURCE_LIST_FILE, this.resourceCache, "res/reslist.xml");
		eventDispatcher.dispatchEvent(new Event<FileToLoad>(GlobalEventType.ENQUEUE_STARTUP_LOADING_ITEM, mainCache));
	}
	public void executeFrame(long frameNumber) {
		RenderUtils.set3DMode();
		//glScalef(0.1f, 0.1f, 0.1f);
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {this.rotationY += 2;}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {this.rotationY -= 2;}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {this.rotationX += 1.9;}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {this.rotationX -= 1.9;}
		glEnable(GL_LIGHT0);
		//glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer)buffer.put(new float[]{100, 100, 100, 1}).rewind());
		glTranslatef(20, -2f, -50);
		glRotatef(rotationX, 1, 0, 0);
		glRotatef(rotationY, 0, 1, 0);
		
		glColor4f(1, 1, 1, 1);
		glEnable(GL_LIGHTING);
		this.testNode.render();
	}
	@Override
	public void set() {
		
		this.testNode = this.resourceCache.createModelInstace("lmsExplorer");
	}
	@Override
	public void unset() {
		
		
	}



}
