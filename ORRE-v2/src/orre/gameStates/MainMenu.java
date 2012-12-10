package orre.gameStates;

import java.nio.FloatBuffer;

import openrr.test.LightTestClass;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import orre.core.GameMain;
import orre.events.Event;
import orre.events.EventDispatcher;
import orre.events.GlobalEventType;
import orre.geom.mesh.Mesh3D;
import orre.gl.RenderUtils;
import orre.gl.renderer.RenderPass;
import orre.resources.FileToLoad;
import orre.resources.ResourceFile;
import orre.sceneGraph.SceneNode;

import static org.lwjgl.opengl.GL11.*;

public class MainMenu extends GameState {

	private SceneNode testNode;
	private float rotationX = 0, rotationY, zoomLevel = -80, xCoord, yCoord;
	
	private FloatBuffer buffer;
	private int displayListID;
	private LightTestClass lightTest;
	private int time = 0;

	public MainMenu(GameMain main, EventDispatcher eventDispatcher, GameState.State stateName) {
		super(main, eventDispatcher, stateName);
		this.buffer = BufferUtils.createFloatBuffer(4);
		FileToLoad mainCache = new FileToLoad(ResourceFile.RESOURCE_LIST_FILE, this.resourceCache, "res/reslist.xml", "mainCacheList");
		eventDispatcher.dispatchEvent(new Event<FileToLoad>(GlobalEventType.ENQUEUE_STARTUP_LOADING_ITEM, mainCache));
//		FileToLoad mapFile = new FileToLoad(ResourceFile.MAP_FILE, this.resourceCache, "res/maps/sampleMap.rrm", "map");
//		eventDispatcher.dispatchEvent(new Event<FileToLoad>(GlobalEventType.ENQUEUE_STARTUP_LOADING_ITEM, mapFile));
	}
	public void executeFrame(long frameNumber) {
		RenderUtils.set3DMode();
		//glScalef(0.1f, 0.1f, 0.1f);
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {this.rotationY += 2;}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {this.rotationY -= 2;}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {this.rotationX += 1.9;}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {this.rotationX -= 1.9;}
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {this.zoomLevel += 2;}
		if(Keyboard.isKeyDown(Keyboard.KEY_X)) {this.zoomLevel -= 2;}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {this.yCoord -= 4;}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {this.xCoord += 4;}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {this.yCoord += 4;}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {this.xCoord -= 4;}
		glEnable(GL_LIGHT0);
		//glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer)buffer.put(new float[]{0, 5, 0, 1}).rewind());
		this.time ++;
		//glTranslated(0, -2, (-10 * Math.sin((double)time/200)) - 20);
		glTranslated(xCoord, yCoord, zoomLevel);
		glRotatef(rotationX, 1, 0, 0);
		glRotatef(rotationY, 0, 1, 0);
//		this.lightTest.draw();
		glPushMatrix();
		for(int i = -10; i < 10; i++){
			for(int j = -10; j < 10; j++) {
				for(int k = -10; k < 10; k++) {
					glPushMatrix();
					glTranslatef(2*i, 2*j, 2*k);
					glCallList(this.displayListID);
					glPopMatrix();				
					
				}
			}
		}

		
//		glTranslatef(20, 0, 0);
//		glCallList(this.displayListID);
//		glTranslatef(-40, 0, 0);
//		glCallList(this.displayListID);
	}
	@Override
	public void set() {
		
		this.testNode = this.resourceCache.createModelInstace("chrystal");//this.resourceCache.getMap().createSceneNode();
		//this.resourceCache.getMap().tick();
		this.displayListID = glGenLists(1);
		glNewList(this.displayListID, GL_COMPILE);
		RenderPass.render(this.testNode);
		glEndList();
		this.lightTest = new LightTestClass(displayListID);
	}
	@Override
	public void unset() {
		
		
	}



}
