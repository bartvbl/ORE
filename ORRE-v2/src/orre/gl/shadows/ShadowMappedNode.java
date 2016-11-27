package orre.gl.shadows;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.python.antlr.PythonParser.defparameter_return;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;


import orre.geom.Projections;
import orre.geom.Shapes;
import orre.gl.RenderUtils;
import orre.gl.renderer.RenderPass;
import orre.gl.renderer.RenderState;
import orre.gl.renderer.ShaderProperty;
import orre.gl.shaders.ShaderNode;
import orre.gl.vao.GeometryNode;
import orre.resources.ResourceCache;
import orre.resources.ResourceType;
import orre.resources.partiallyLoadables.Shader;
import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneNode;

public class ShadowMappedNode implements SceneNode {
	private final int frameBufferID;
	private final int frameTextureID;
	private static final int shadowMapWidth = 4096;
	private static final int shadowMapHeight = 4096;
	private final ContainerNode shadowElementsContainer = new ContainerNode("Shadow Mapped Node contents");
	
	private final ShaderNode shadowPhong;
	private final ShaderNode shadowPass;
	private final ArrayList<SceneNode> shadowPhongChildList = new ArrayList<SceneNode>();
	private final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	private final GeometryNode frameVAO;

	public ShadowMappedNode(ResourceCache resourceCache) {
		this.shadowPhong = ((Shader) resourceCache.getResource(ResourceType.shader, "shadow_phong").content).createSceneNode();
		shadowPhong.addChild(shadowElementsContainer);
		
		this.shadowPass = ((Shader) resourceCache.getResource(ResourceType.shader, "shadow_pass").content).createSceneNode();
		shadowPass.addChild(shadowElementsContainer);
		
		this.frameBufferID = glGenFramebuffers();
		
		shadowPhongChildList.add(shadowPhong);

		this.frameTextureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, frameTextureID);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, shadowMapWidth, shadowMapHeight, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer) null);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT); 
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	    
	    glBindFramebuffer(GL_FRAMEBUFFER, frameBufferID);
	    glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, frameTextureID, 0);
	    glDrawBuffer(GL_NONE);
	    glReadBuffer(GL_NONE);
	    glBindFramebuffer(GL_FRAMEBUFFER, 0);
		
		int status = glCheckFramebufferStatus(GL_FRAMEBUFFER);
		
		if(status != GL_FRAMEBUFFER_COMPLETE) {
			switch(status) {
			case GL_FRAMEBUFFER_UNDEFINED: throw new RuntimeException("Framebuffer error: GL_FRAMEBUFFER_UNDEFINED");
			case GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT: throw new RuntimeException("Framebuffer error: GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
			case GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT: throw new RuntimeException("Framebuffer error: GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
			case GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER: throw new RuntimeException("Framebuffer error: GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
			case GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER: throw new RuntimeException("Framebuffer error: GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
			case GL_FRAMEBUFFER_UNSUPPORTED: throw new RuntimeException("Framebuffer error: GL_FRAMEBUFFER_UNSUPPORTED");
			case GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE: throw new RuntimeException("Framebuffer error: GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE");
//			case GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS: throw new RuntimeException("Framebuffer error: GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS");
			}
		}
		this.frameVAO = Shapes.generateTexturedSquare();
		
		glBindFramebuffer(GL_FRAMEBUFFER, 0);  
	}

	@Override
	public void preRender(RenderState state) {
		glBindFramebuffer(GL_FRAMEBUFFER, frameBufferID);
		glViewport(0, 0, shadowMapWidth, shadowMapHeight);

		glClear(GL_DEPTH_BUFFER_BIT);
		state.transformations.pushMatrix();
		
		// Disable face culling, as it matters to 
		glDisable(GL_CULL_FACE);
		
		// Set up camera transform
		float[] cameraPosition = state.shaders.getProperty4f(ShaderProperty.LIGHT_POSITION);
		Matrix4f cameraView = new Matrix4f();
		cameraView.translate(new Vector3f(-cameraPosition[0], -cameraPosition[1], -cameraPosition[2]));
		state.transformations.setViewMatrix(cameraView);
		
		// And projection
		Matrix4f projection = Projections.createPerspectiveMatrix((float)shadowMapWidth/(float)shadowMapHeight, 160, RenderUtils.NEAR_POINT, RenderUtils.FAR_POINT);
		state.transformations.setProjectionMatrix(projection);
		
		// We need this for projecting coordinates into light space
		Matrix4f shadowVP = new Matrix4f();
		Matrix4f.mul(projection, cameraView, shadowVP);
		
		state.shaders.setPropertyi(ShaderProperty.TEXTURE1, 0);
		
		// Perform the depth buffer transform
		RenderPass.render(shadowPass, state);
		
		state.transformations.popMatrix();
		
		// Return to the default framebuffer
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		RenderUtils.resetViewport();
		
		// Binding the shader to set uniforms. It'll be bound right after anyway
		shadowPhong.preRender(state);

		// Set the depth texture so that the shadow pass can use it
		state.shaders.setPropertyi(ShaderProperty.TEXTURE1, frameTextureID);
		
		// The light MVP is needed
		state.shaders.setLightMatrix(shadowVP);
		
		// Turn face culling back on
		glEnable(GL_CULL_FACE);
	}

	@Override
	public void postRender(RenderState state) {
		
	}

	@Override
	public void render(RenderState state) {}

	@Override
	public void addChild(SceneNode node) {
		shadowElementsContainer.addChild(node);
	}

	@Override
	public void removeChild(SceneNode node) {
		shadowElementsContainer.removeChild(node);
	}

	@Override
	public ArrayList<SceneNode> getChildren() {
		return shadowPhongChildList;
	}

	@Override
	public void destroy() {
		glDeleteFramebuffers(frameBufferID);
		glDeleteTextures(frameTextureID);
	}

	@Override
	public void setVisibility(boolean isVisible) {
		shadowElementsContainer.setVisibility(isVisible);
	}

	@Override
	public boolean isVisible() {
		return shadowElementsContainer.isVisible();
	}

	@Override
	public float getRenderRadius() {
		return 0;
	}
}
