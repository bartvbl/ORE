package orre.resources.loaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import orre.resources.Finalizable;
import orre.resources.ResourceQueue;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.UnloadedResource;
import orre.resources.partiallyLoadables.Shader;

public class ShaderLoader implements ResourceTypeLoader {

	@Override
	public Finalizable loadResource(UnloadedResource source, ResourceQueue queue) throws Exception {
		return loadShader(source);
	}

	public static Shader loadShader(UnloadedResource source) {
		String path = source.location.getAbsolutePath();
		File vertexShaderSource = new File(path + ".vert");
		File fragmentShaderSource = new File(path + ".frag");
		String vertexSource = readShaderFile(vertexShaderSource);
		String fragSource = readShaderFile(fragmentShaderSource);
		return new Shader(source.name, vertexSource, fragSource);
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

}
