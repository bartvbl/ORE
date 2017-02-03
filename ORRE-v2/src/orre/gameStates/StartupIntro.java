package orre.gameStates;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Color;

import static org.lwjgl.opengl.GL11.*;
import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gl.Shader;
import orre.gl.lighting.Light;
import orre.gl.materials.Material;
import orre.gl.renderer.RenderPass;
import orre.gl.renderer.RenderState;
import orre.gl.shaders.ShaderNode;
import orre.gl.shaders.Uniform;
import orre.resources.ResourceCache;
import orre.resources.ResourceService;
import orre.resources.ResourceType;
import orre.resources.incompleteResources.BlueprintMaterial;
import orre.resources.incompleteResources.IncompleteModelPart;
import orre.resources.loaders.OBJLoader;
import orre.resources.loaders.obj.ModelPartType;
import orre.resources.loaders.obj.StoredModelPart;
import orre.sceneGraph.CoordinateNode;
import orre.sceneGraph.SceneNode;

/* Too much hardcoding in here. Should really not exist in the first place.. */

public class StartupIntro extends SequencableGameState implements AbstractGameState {

	private SceneNode node;
	private CoordinateNode brickPosition;
	private int imageWidth;
	private int imageHeight;
	private double brickSize = 0.8;
	private double[][][] rotationSpeeds;
	private CoordinateNode[][] brickNodes;
	private static final double animSpeed = 0.5;
	private static final double animHeight = 150;
	private static final double rotationSpeed = 10;
	private int animationFinishedCounter = 0;

	public StartupIntro(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceService resourceService) {
		super(main, eventDispatcher, GameStateName.STARTUP_INTRO, resourceService);
	}

	@Override
	public void set() {
		try {
			glClearColor(0, 0, 0, 1);
			BufferedImage logoImage = ImageIO.read(new File("res/images/intro/logo.png"));
			this.imageWidth = logoImage.getWidth();
			this.imageHeight = logoImage.getHeight();
			
			List<IncompleteModelPart> partList = OBJLoader.load("res/mesh/objects3D/singleBrick.obj");
			IncompleteModelPart brickPart = partList.get(0);
			StoredModelPart brickBlueprint = new StoredModelPart(ModelPartType.PHYSICAL, "single brick", "none");
			brickPart.setDestinationPart(brickBlueprint);
			BlueprintMaterial material = new BlueprintMaterial("untitled-1");
			brickPart.setMaterial(material);
			brickPart.finalizeResource();
			Light light = new Light();
			ShaderNode shader = ((Shader) resourceService.getResource(ResourceType.shader, "phong")).createSceneNode();
			Uniform uniform = shader.createUniform("texturesEnabled");
			SceneNode brickNode = brickBlueprint.createSceneNode().getChildren().get(0).getChildren().get(0);
			this.brickPosition = new CoordinateNode();
			uniform.setValue(0f);
			this.node = light;
			light.setPosition(0, 0, 10);
			light.setAmbientLight(new float[]{0, 0, 0, 1});
			light.setDiffuseLight(new float[]{1f, 1f, 1f, 1f});
			light.setSpecularLight(new float[]{1f, 1f, 1f, 1f});
			light.addChild(shader);
			shader.addChild(brickPosition);
			buildImage(logoImage, brickNode, brickPosition);
			animationFinishedCounter = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void buildImage(BufferedImage logoImage, SceneNode brickNode, CoordinateNode modelRoot) {
		brickNodes = new CoordinateNode[imageWidth][imageHeight];
		rotationSpeeds = new double[imageWidth][imageHeight][3];
		HashMap<Color, ArrayList<SceneNode>> materials = new HashMap<Color, ArrayList<SceneNode>>();
		int[] colourAt = new int[4];
		for(int i = 0; i < logoImage.getWidth(); i++) {
			for(int j = 0; j < logoImage.getHeight(); j++) {
				logoImage.getData().getPixel(i, j, colourAt);
				Color colour = new Color(colourAt[0], colourAt[1], colourAt[2], colourAt[3]);
				if(!materials.containsKey(colour)) {
					materials.put(colour, new ArrayList<SceneNode>());
				}
				CoordinateNode brickLocation = new CoordinateNode();
				
				double randomFactor = Math.random();
				double height = ((1d - Math.sin(randomFactor * (Math.PI / 2d))) * animHeight) + 100;
				brickLocation.setLocation((double)i * brickSize, height, (double)j * brickSize);
				double fallDuration = height / animSpeed;
				
				double xRotation = Math.random() * height * rotationSpeed;
				rotationSpeeds[i][j][0] = -xRotation / (fallDuration);
				
				double yRotation = Math.random() * height * rotationSpeed;
				rotationSpeeds[i][j][1] = -yRotation / (fallDuration);
				
				double zRotation = Math.random() * height * rotationSpeed;
				rotationSpeeds[i][j][2] = -zRotation / (fallDuration);
				
				brickLocation.setRotation(xRotation, yRotation, zRotation);
				brickLocation.addChild(brickNode);
				materials.get(colour).add(brickLocation);
				brickNodes[i][j] = brickLocation;
			}
		}
		double[] glColour = new double[4];
		for(Color colour : materials.keySet()) {
			Material material = new Material("brick material");
			material.setShininess(40);
			glColour[0] = ((double)colour.getRed()) / 255.0;
			glColour[1] = ((double)colour.getGreen()) / 255.0;
			glColour[2] = ((double)colour.getBlue()) / 255.0;
			glColour[3] = ((double)colour.getAlpha()) / 255.0;
			material.setDiffuseColour(glColour);
			material.setSpecularColour(glColour);
			material.setShininess(90);
			for(SceneNode child : materials.get(colour)) {
				material.addChild(child);
			}
			modelRoot.addChild(material);
		}
	}

	@Override
	public void unset() {

	}

	@Override
	public void executeFrame(long frameNumber, RenderState state) {
		boolean allFinished = true;
		for(int x = 0; x < brickNodes.length; x++) {
			for(int y = 0; y < brickNodes.length; y++) {
				CoordinateNode column = brickNodes[x][y];
				if(column.getY() > 0) {
					column.translate(0, -animSpeed, 0);
					allFinished = false;
				} else {
					column.setY(0);
				}
				if(column.getRotationX() > 0) {
					column.rotate(rotationSpeeds[x][y][0], 0, 0);
				} else {
					column.setRotationX(0);
				}
				if(column.getRotationY() > 0) {
					column.rotate(0, rotationSpeeds[x][y][1], 0);
				} else {
					column.setRotationY(0);
				}
				if(column.getRotationZ() > 0) {
					column.rotate(0, 0, rotationSpeeds[x][y][2]);
				} else {
					column.setRotationZ(0);
				}
			}
		}
		if(allFinished) {
			if(animationFinishedCounter > -100) {
				animationFinishedCounter--;
			} else {
				this.finish();
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			this.finish();
		}
		brickPosition.setLocation(-((double)imageWidth * brickSize)/2, ((double)imageHeight * brickSize)/2, -100);
		brickPosition.setRotationX(90);
		RenderPass.render(node, new RenderState());
		
	}

}
