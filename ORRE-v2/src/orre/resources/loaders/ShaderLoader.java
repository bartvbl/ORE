package orre.resources.loaders;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import orre.gl.Shader;
import orre.resources.IncompleteResourceObject;
import orre.resources.Resource;
import orre.resources.ResourceObject;
import orre.resources.ResourceQueue;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.incompleteResources.IncompleteShader;

public class ShaderLoader implements ResourceTypeLoader {

	@Override
	public IncompleteResourceObject<?> readResource(Resource source) throws Exception {
		return loadShader(source);
	}

	public static IncompleteShader loadShader(Resource source) {
		String path = source.fileLocation.getAbsolutePath();
		File vertexShaderSource = new File(path + ".vert");
		File fragmentShaderSource = new File(path + ".frag");
		String vertexSource = readShaderFile(vertexShaderSource);
		String fragSource = readShaderFile(fragmentShaderSource);
		return new IncompleteShader(source.name, vertexSource, fragSource);
	}

	@Override
	public ResourceType getResourceType() {
		return ResourceType.shader;
	}
	
	public static String readShaderFile(File sourceFile) {
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
	public ResourceObject<?> completeResource(IncompleteResourceObject<?> object) {
		IncompleteShader shader = (IncompleteShader) object;
		return compile(shader);
	}
	
	private int createShader(String name, String typeString, String vertexSource, int type) {
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, vertexSource);
		glCompileShader(shaderID);
		String infoLog = glGetShaderInfoLog(shaderID, glGetShaderi(shaderID, GL_INFO_LOG_LENGTH));
		if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
			throw new RuntimeException(typeString + " shader compilation of \"" + name + "\" failed: " + infoLog);
		}
		return shaderID;
	}

	private Shader compile(IncompleteShader shader) {
		int vertexShaderID = createShader(shader.name, "Vertex", shader.vertSource, GL_VERTEX_SHADER);
		int fragmentShaderID = createShader(shader.name, "Fragment", shader.fragSource, GL_FRAGMENT_SHADER);
		int programID = glCreateProgram();
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		glLinkProgram(programID);
		String infoLog = glGetProgramInfoLog(programID, glGetProgrami(programID, GL_INFO_LOG_LENGTH));
		if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
			throw new RuntimeException("Failed to link shader: " + infoLog + "\nIn file: " + shader.name + "\n\nVertex Shader source: " + shader.vertSource + "\n\nFragment Shader Source:" + shader.fragSource);
		}
		return new Shader(shader.name, programID, vertexShaderID, fragmentShaderID);
	}

}
