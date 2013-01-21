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
import orre.gl.texture.Texture;
import orre.resources.FileToLoad;
import orre.resources.ResourceFile;
import orre.sceneGraph.SceneNode;

import static org.lwjgl.opengl.GL11.*;

public class MainMenu extends GameState {

	private SceneNode testNode;
	private float rotationX = 0, rotationY, zoomLevel = -100, xCoord, yCoord;
	
	private FloatBuffer buffer;
	private int displayListID = -1;
	private LightTestClass lightTest;
	private int time = 0;
	private Texture skybox;
	private Mesh3D node;
	private Mesh3D node2;

	public MainMenu(GameMain main, EventDispatcher eventDispatcher, GameState.State stateName) {
		super(main, eventDispatcher, stateName);
		this.buffer = BufferUtils.createFloatBuffer(4);
		FileToLoad mainCache = new FileToLoad(ResourceFile.RESOURCE_LIST_FILE, this.resourceCache, "res/reslist.xml", "mainCacheList");
		eventDispatcher.dispatchEvent(new Event<FileToLoad>(GlobalEventType.ENQUEUE_STARTUP_LOADING_ITEM, mainCache));
		FileToLoad mapFile = new FileToLoad(ResourceFile.MAP_FILE, this.resourceCache, "res/maps/sampleMap.rrm", "map");
		eventDispatcher.dispatchEvent(new Event<FileToLoad>(GlobalEventType.ENQUEUE_STARTUP_LOADING_ITEM, mapFile));
	}
	public void executeFrame(long frameNumber) {
		RenderUtils.set3DMode();
		glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer)buffer.put(new float[]{0, 0, 0, 1}).rewind());
		//glScalef(0.1f, 0.1f, 0.1f);
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {this.rotationY -= 2;}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {this.rotationY += 2;}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {this.rotationX += 2;}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {this.rotationX -= 2;}
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {this.zoomLevel += 0.3;}
		if(Keyboard.isKeyDown(Keyboard.KEY_X)) {this.zoomLevel -= 0.3;}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {this.yCoord += 0.3;}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {this.yCoord -= 0.3;}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {this.xCoord += 0.3;}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {this.xCoord -= 0.3;}
		glEnable(GL_LIGHT0);
		this.time ++;
		//glTranslated(0, -2, (-10 * Math.sin((double)time/200)) - 20);
		glTranslated(0, 0, zoomLevel);
		glRotatef(rotationX, 1, 0, 0);
		glRotatef(rotationY, 0, 0, 1);
		glTranslated(xCoord, yCoord, 0);
		glLight(GL_LIGHT0, GL_AMBIENT, (FloatBuffer)buffer.put(new float[]{0.2f, 0.2f, 0.2f, 1}).rewind());
		glLight(GL_LIGHT0, GL_SPECULAR, (FloatBuffer)buffer.put(new float[]{0.8f, 0.8f, 0.8f, 1}).rewind());
//		this.lightTest.draw();
		glEnable(GL_LIGHTING);
		glEnable(GL_NORMALIZE);
		glColor4f(1, 1, 1, 1);
		glCallList(this.displayListID);
		glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );

		glPolygonMode( GL_FRONT_AND_BACK, GL_FILL );
		//		glTranslatef(20, 0, 0);
//		glCallList(this.displayListID);
//		glTranslatef(-40, 0, 0);
//		glCallList(this.displayListID);
	}
	@Override
	public void set() {
		this.testNode = this.resourceCache.getMap().createSceneNode();
		this.node = this.resourceCache.createModelInstace("toolStore");
		this.node2 = this.resourceCache.createModelInstace("rockRaider");
		this.displayListID = glGenLists(1);
		this.skybox = this.resourceCache.getTexture("mainMenu_skybox");
		buildScene();
		//this.lightTest = new LightTestClass(displayListID);
	}
	private void buildScene() {
		glColor4f(1, 1, 1, 1);
		glDeleteLists(this.displayListID, 1);
		glNewList(this.displayListID, GL_COMPILE);
		RenderPass.render(testNode);
		glScaled(0.07, 0.07, 0.07);
		glTranslated(79, 107, 376.3);
		glRotated(90, 1, 0, 0);
		RenderPass.render(node);
		glTranslated(-0.5, 1.2, 3);
		RenderPass.render(node2);
		glEndList();
	}
	@Override
	public void unset() {
		
	}



}
