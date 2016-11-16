package orre.gl.shaders;

import orre.rendering.RenderState;
import orre.sceneGraph.ContainerNode;
import static org.lwjgl.opengl.GL20.*;

public class ShaderNode extends ContainerNode {

	private int programID;

	public ShaderNode(String name, int programID) {
		super(name);
		this.programID = programID;
	}
	
	public Uniform createUniform(String variableName) {
		return new Uniform(variableName, programID);
	}

	@Override
	public void preRender(RenderState state) {
		glUseProgram(programID);
	}
	
	@Override
	public void render(RenderState state) {
	}
	
	@Override
	public void postRender(RenderState state) {
		glUseProgram(0);
	}
	
	@Override
	public String toString() {
		return "Shader node \"" + name + "\"";
	}
}
