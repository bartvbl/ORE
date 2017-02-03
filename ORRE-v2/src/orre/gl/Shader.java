package orre.gl;

import orre.gl.shaders.ShaderNode;
import orre.resources.ResourceObject;

import static org.lwjgl.opengl.GL20.*;

public class Shader implements ResourceObject<ShaderNode> {

	private final String name;
	private final int programID;
	private final int vertexShaderID;
	private final int fragmentShaderID;

	public Shader(String name, int programID, int vertexShaderID, int fragmentShaderID) {
		this.name = name;
		this.programID = programID;
		this.vertexShaderID = vertexShaderID;
		this.fragmentShaderID = fragmentShaderID;
	}

	@Override
	public ShaderNode createInstance() {
		return new ShaderNode(name, programID);
	}

	public ShaderNode createSceneNode() {
		return createInstance();
	}

	public void destroy() {
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		glDeleteProgram(programID);
	}

}
