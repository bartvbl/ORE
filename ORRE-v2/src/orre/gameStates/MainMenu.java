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
	private double time = 0;
	private Texture skybox;
	//private Mesh3D node;
	//private Mesh3D node2;

	public MainMenu(GameMain main, GlobalEventDispatcher eventDispatcher, GameState.State stateName) {
		super(main, eventDispatcher, stateName);
		this.buffer = BufferUtils.createFloatBuffer(4);
		FileToLoad mainCache = new FileToLoad(ResourceFile.RESOURCE_LIST_FILE, this.resourceCache, "res/reslist.xml", "mainCacheList");
		eventDispatcher.dispatchEvent(new GlobalEvent<FileToLoad>(GlobalEventType.ENQUEUE_STARTUP_LOADING_ITEM, mainCache));
		FileToLoad mapFile = new FileToLoad(ResourceFile.MAP_FILE, this.resourceCache, "res/maps/sampleMap.rrm", "map");
		eventDispatcher.dispatchEvent(new GlobalEvent<FileToLoad>(GlobalEventType.ENQUEUE_GAME_LOADING_ITEM, mapFile));
	}
	public void executeFrame(long frameNumber) {
		RenderUtils.set3DMode();
		//glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer)buffer.put(new float[]{0, 0, 0, 1}).rewind());
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
		this.time += 0.003;
		glRotatef(rotationX, 1, 0, 0);
		glRotatef(rotationY, 0, 0, 1);
		glTranslated(xCoord, yCoord, 0);
		glTranslated(0, 0, zoomLevel);
		float x = 15;
		float y = 52;
		float z = 13;
		glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer)buffer.put(new float[]{x, y, z, 1}).rewind());
		glLight(GL_LIGHT0, GL_SPOT_CUTOFF, (FloatBuffer)buffer.put(new float[]{30}).rewind());
		glLight(GL_LIGHT0, GL_SPOT_DIRECTION, (FloatBuffer)buffer.put(new float[]{(float) (Math.cos(time)), (float) (Math.sin(time)), 0}).rewind());
		
		glBegin(GL_QUADS);
		glVertex3d(x-0.5, y, z-0.5);
		glVertex3d(x+0.5, y, z-0.5);
		glVertex3d(x+0.5, y, z+0.5);
		glVertex3d(x-0.5, y, z+0.5);
		glEnd();
		glLight(GL_LIGHT0, GL_AMBIENT, (FloatBuffer)buffer.put(new float[]{0.1f, 0.1f, 0.1f, 1}).rewind());
		glLight(GL_LIGHT0, GL_DIFFUSE, (FloatBuffer)buffer.put(new float[]{0.8f, 0.8f, 0.8f, 1}).rewind());
		glLight(GL_LIGHT0, GL_SPECULAR, (FloatBuffer)buffer.put(new float[]{0.5f, 0.5f, 0.5f, 1}).rewind());
		
		glEnable(GL_LIGHTING);
		glEnable(GL_NORMALIZE);
		glColor4f(1, 1, 1, 1);
		glCallList(this.displayListID);
		//this.lightTest.draw();
	}
	@Override
	public void set() {
		this.testNode = this.resourceCache.getMap().createSceneNode();
		//this.node = this.resourceCache.createModelInstace("toolStore");
		//this.node2 = this.resourceCache.createModelInstace("rockRaider");
		this.displayListID = glGenLists(1);
		this.skybox = this.resourceCache.getTexture("mainMenu_skybox");
		buildScene();
		this.lightTest = new LightTestClass(displayListID);
	}
	private void buildScene() {
		glColor4f(1, 1, 1, 1);
		glDeleteLists(this.displayListID, 1);
		glNewList(this.displayListID, GL_COMPILE);
		RenderPass.render(testNode);
		glScaled(0.07, 0.07, 0.07);
		glTranslated(81, 609, 216.3);
		glRotated(90, 1, 0, 0);
		//RenderPass.render(node);
		glTranslated(-0.5, 1.2, 3);
		//RenderPass.render(node2);
		glEndList();
	}
	@Override
	public void unset() {
		
	}



}
