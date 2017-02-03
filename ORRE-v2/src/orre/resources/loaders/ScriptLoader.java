package orre.resources.loaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import orre.resources.IncompleteResourceObject;
import orre.resources.Resource;
import orre.resources.ResourceObject;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.scripting.ScriptInterpreter;

public class ScriptLoader implements ResourceTypeLoader {

	@Override
	public IncompleteResourceObject<?> readResource(Resource source) throws Exception {
		String pythonSource = loadFileContents(source.fileLocation);
		ScriptInterpreter.get().addToPythonPath(source.fileLocation.getParentFile());
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

	@Override
	public ResourceObject<?> completeResource(IncompleteResourceObject<?> object) {
		return null;
	}

}
