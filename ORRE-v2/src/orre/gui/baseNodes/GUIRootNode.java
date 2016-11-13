package orre.gui.baseNodes;

import orre.gl.RenderUtils;
import orre.gl.renderer.RenderContext;
import orre.rendering.RenderState;

import static org.lwjgl.opengl.GL11.*;

import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneNode;

public class GUIRootNode extends ContainerNode implements SceneNode {
	public GUIRootNode() {
		super("GUI root");
	}
	
	@Override
	public void preRender(RenderState state) {
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		RenderUtils.set2DMode();
	}
	
	@Override
	public void postRender(RenderState state) {
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
		glPopMatrix();
		RenderUtils.set3DMode();
	}
}
