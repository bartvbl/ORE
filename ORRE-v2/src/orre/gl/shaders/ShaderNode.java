package orre.gl.shaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import orre.sceneGraph.ContainerNode;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderNode extends ContainerNode {

	private int vertexShaderID;
	private int fragmentShaderID;
	private int programID;

	public ShaderNode(File vertexShaderSource, File fragmentShaderSource) {
			String vertexSource = readShaderFile(vertexShaderSource);
			String fragSource = readShaderFile(fragmentShaderSource);
			this.vertexShaderID = createShader(vertexSource, GL_VERTEX_SHADER);
			this.fragmentShaderID = createShader(fragSource, GL_FRAGMENT_SHADER);
			this.programID = glCreateProgram();
			glAttachShader(programID, vertexShaderID);
			glAttachShader(programID, fragmentShaderID);
			glLinkProgram(programID);
			String infoLog = glGetProgramInfoLog(programID, glGetProgrami(programID, GL_INFO_LOG_LENGTH));
			if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
				throw new RuntimeException("Failed to link shader: " + infoLog);
			}
	}

	private int createShader(String vertexSource, int type) {
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, vertexSource);
		glCompileShader(shaderID);
		String infoLog = glGetShaderInfoLog(shaderID, glGetShaderi(shaderID, GL_INFO_LOG_LENGTH));
		if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
			throw new RuntimeException("Shader compilation failed: " + infoLog);
		}
		return shaderID;
	}

	private String readShaderFile(File sourceFile) {
		FileInputStream stream;
		try {
			stream = new FileInputStream(sourceFile);
			byte[] fileContents = new byte[(int) sourceFile.length()];
			stream.read(fileContents);
			stream.close();
			return new String(fileContents);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@Override
	public void preRender() {
		
	}
	
	@Override
	public void render() {
		glUseProgram(programID);
		int location = glGetUniformLocation(programID, "diffuseTexture");
		glUniform1i(location, 0);
	}
	
	@Override
	public void postRender() {
		glUseProgram(0);
	}
	
	public String toString() {
		return "Shader node";
	}
}
