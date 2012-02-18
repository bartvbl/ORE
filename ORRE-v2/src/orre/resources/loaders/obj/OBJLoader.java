package orre.resources.loaders.obj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import orre.geom.vbo.BufferDataFormatType;
import orre.gl.Material;
import orre.resources.ResourceCache;
import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;
import orre.sceneGraph.Mesh3D;
import orre.util.FeedbackProvider;
import orre.util.StringUtils;

public class OBJLoader {
	public static List<PartiallyLoadableModelPart> load(String src)
	{
		try {
			FileReader fileReader = new FileReader(src);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			return parseOBJFile(bufferedReader);
		} catch (FileNotFoundException e) {
			FeedbackProvider.showLoadOBJFileNotFoundMessage(src);
			e.printStackTrace();
		} catch (IOException e) {
			FeedbackProvider.showLoadOBJFileFailedMessage(src);
			e.printStackTrace();
		}
		return null;
	}

	private static List<PartiallyLoadableModelPart> parseOBJFile(BufferedReader bufferedReader) throws IOException {
		HashMap<String, Material> materials = new HashMap<String, Material>(5);
		ArrayList<PartiallyLoadableModelPart> modelParts = new ArrayList<PartiallyLoadableModelPart>();
		while(bufferedReader.ready())
		{
			String line = bufferedReader.readLine();
			line = StringUtils.stripString(line);
			readOBJLine(line, materials, modelParts);
		}
		return modelParts;
	}

	private static void readOBJLine(String line, HashMap<String, Material> materials, ArrayList<PartiallyLoadableModelPart> modelParts) {
		if((line.length() == 0) || (line.charAt(0) == '#'))
		{
			return;
		}
		if(line.charAt(0) == 'v')
		{
			if(modelParts.isEmpty())
			{
				BufferDataFormatType vertexFormat = OBJFileLineReader.readVertexFormat();
			//	OBJLoadingUtils.createNewModelPart
			}
			OBJFileLineReader.readVertexLine(line);
		}
	}
}
