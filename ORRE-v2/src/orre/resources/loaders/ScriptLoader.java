package orre.resources.loaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import orre.resources.Finalizable;
import orre.resources.ResourceQueue;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.UnloadedResource;
import orre.scripting.ScriptInterpreter;

public class ScriptLoader implements ResourceTypeLoader {

	@Override
	public Finalizable loadResource(UnloadedResource source, ResourceQueue queue) throws Exception {
		String pythonSource = loadFileContents(source.location);
		ScriptInterpreter.get().execute(pythonSource);
		return null;
	}

	private String loadFileContents(File sourceFile) {
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
	public ResourceType getResourceType() {
		return ResourceType.script;
	}

}
