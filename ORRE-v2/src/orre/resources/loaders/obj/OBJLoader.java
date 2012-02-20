package orre.resources.loaders.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import orre.geom.vbo.BufferDataFormatType;
import orre.gl.materials.Material;
import orre.resources.ResourceCache;
import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;
import orre.sceneGraph.Mesh3D;
import orre.util.FeedbackProvider;
import orre.util.StringUtils;

public class OBJLoader {
	public static List<PartiallyLoadableModelPart> load(String src)
	{
		OBJLoadingContext context = new OBJLoadingContext(new File(src).getParentFile());
		try {
			FileReader fileReader = new FileReader(src);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			return parseOBJFile(bufferedReader, context);
		} catch (FileNotFoundException e) {
			FeedbackProvider.showLoadOBJFileNotFoundMessage(src);
			e.printStackTrace();
		} catch (IOException e) {
			FeedbackProvider.showLoadOBJFileFailedMessage(src);
			e.printStackTrace();
		}
		return null;
	}

	private static List<PartiallyLoadableModelPart> parseOBJFile(BufferedReader bufferedReader, OBJLoadingContext context) throws IOException {
		while(bufferedReader.ready())
		{
			String line = bufferedReader.readLine();
			line = StringUtils.stripString(line);
			context.setLine(line);
			readOBJLine(context);
		}
		return context.getModelParts();
	}

	private static void readOBJLine(OBJLoadingContext context) {
		if((context.getLine().length() == 0) || (context.getLine().charAt(0) == '#'))
		{
			return;
		}
		OBJFileLineReader.readOBJLine(context);
	}
}
