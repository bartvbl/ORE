package orre.gui;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import orre.geom.Shapes;
import orre.gl.RenderUtils;
import orre.gl.renderer.RenderPass;
import orre.gl.texture.Texture;
import orre.gl.vao.GeometryNode;
import orre.rendering.RenderState;
import orre.resources.ResourceLoader;
import orre.resources.loaders.TextureLoader;

public class DefaultLoadingScreen extends LoadingScreenDrawer {
	
	private Texture loadingScreen;
	private Texture loadingBar;
	
	private GeometryNode texturedQuadVAO = Shapes.generateTexturedSquare();
	
	private int rotation = 0;
	private Texture loadingIcon;
	
	public DefaultLoadingScreen(ResourceLoader loader)
	{
		super(loader);
		try {
			this.loadingScreen = TextureLoader.loadTextureFromFile(new File("res/images/loadingScreen/loadingScreen.png"));
			this.loadingBar = TextureLoader.loadTextureFromFile(new File("res/images/loadingScreen/loadingBar.png"));
			this.loadingIcon = TextureLoader.loadTextureFromFile(new File("res/images/loadingScreen/busy.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(double progress, RenderState state) {
		state.transformations.pushMatrix();
		
		RenderUtils.loadIdentity(state);
		RenderUtils.set2DMode(state);
		
		state.transformations.pushMatrix();
		
			loadingScreen.bind(state);
			state.transformations.scale(new Vector3f(Display.getWidth(), Display.getHeight(), 1.0f));
			RenderPass.renderSingleNode(texturedQuadVAO, state);
		
		state.transformations.popMatrix();
		state.transformations.pushMatrix();
		
		
			int x = (int)(0.223f*Display.getWidth());
			int y = (int)(0.046f*Display.getHeight());
			int width = (int)((Display.getWidth() - 2.02f*x) * progress);
			int height = (int)(Display.getHeight() / 60f);
			
			this.loadingBar.bind(state);
			state.transformations.translate(new Vector3f(x, y, 0));
			state.transformations.scale(new Vector3f(width, height, 1.0f));
			
			RenderPass.renderSingleNode(texturedQuadVAO, state);
		state.transformations.popMatrix();
		
//		this.loadingIcon.bind();
//		rotation += 3;
//		int size = 50;
//		glTranslatef(80, 80, 0);
//		glRotatef(rotation, 0, 0, 1);
//		glBegin(GL_QUADS);
//		glTexCoord2f(0, 0);
//		glVertex2f(-size, -size);
//		glTexCoord2f(1, 0);
//		glVertex2f(size, -size);
//		glTexCoord2f(1, 1);
//		glVertex2f(size, size);
//		glTexCoord2f(0, 1);
//		glVertex2f(-size, size);
//		glEnd();
//		glShadeModel(GL_SMOOTH);
		
		state.transformations.popMatrix();
	}
	
}
