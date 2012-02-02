package orre.resources.loaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import orre.gl.Material;
import orre.resources.ResourceCache;
import orre.sceneGraph.Mesh3D;
import orre.util.FeedbackProvider;
import orre.util.StringUtils;

public class OBJLoader {
	public static Mesh3D load(String src, ResourceCache cache)
	{
		Mesh3D mesh = new Mesh3D();
		HashMap<String, Material> materials = new HashMap<String, Material>(30);
		
		try {
			FileReader fileReader = new FileReader(src);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while(bufferedReader.ready())
			{
				String line = bufferedReader.readLine();
				line = StringUtils.stripString(line);
				readOBJLine(line, materials);
			}
		} catch (FileNotFoundException e) {
			FeedbackProvider.showLoadOBJFileNotFoundMessage(src);
			e.printStackTrace();
		} catch (IOException e) {
			FeedbackProvider.showLoadOBJFileFailedMessage(src);
			e.printStackTrace();
		}
		return mesh;
	}

	private static void readOBJLine(String line, HashMap<String, Material> materials) {
		if(line.charAt(0) == 'v')
		{
			readVertexLine();
		}
	}

	private static void readVertexLine() {
		// TODO Auto-generated method stub
		
	}
}
