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
import orre.rendering.ShaderProperty;
import orre.resources.loaders.TextureLoader;

public class DefaultLoadingScreen implements LoadingScreenDrawer {
	
	private Texture loadingScreen;
	private Texture loadingBar;
	
	private GeometryNode texturedQuadVAO = Shapes.generateTexturedSquare();
	
	private int rotation = 0;
	private Texture loadingIcon;
	
	public DefaultLoadingScreen()
	{
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
		
		
		glColor4f(1, 1, 1, 1);
		
		state.shaders.setPropertyi(ShaderProperty.TEXTURE, loadingScreen.id);
		state.transformations.pushMatrix();
		Matrix4f current = state.transformations.peekMatrix();
		current = Matrix4f.scale(new Vector3f(Display.getWidth(), Display.getHeight(), 0), current, current);
		RenderPass.renderSingleNode(texturedQuadVAO, state);
//		
//		this.loadingScreen.blit(0, 0, Display.getWidth(), Display.getHeight());
//		int x = (int)(0.223f*Display.getWidth());
//		int y = (int)(0.046f*Display.getHeight());
//		int width = (int)((Display.getWidth() - 2.02f*x) * progress);
//		int height = (int)(Display.getHeight() / 60f);
//		
//		this.loadingBar.bind();
//		glBegin(GL_QUADS);
//		glTexCoord2f(0,0);
//		glVertex2f(x,y);
//		glTexCoord2f((float)progress,0);
//		glVertex2f(x+width,y);
//		glTexCoord2f((float)progress,1);
//		glVertex2f(x+width,y+height);
//		glTexCoord2f(0,1);
//		glVertex2f(x,y+height);
//		glEnd();
//		
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
