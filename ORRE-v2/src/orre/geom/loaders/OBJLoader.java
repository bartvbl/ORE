package orre.geom.loaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import orre.gl.Material;
import orre.resources.ResourceCache;
import orre.sceneGraph.Mesh3D;
import orre.util.FeedbackProvider;

public class OBJLoader {
	public Mesh3D load(String src, ResourceCache cache)
	{
		Mesh3D mesh = new Mesh3D();
		ArrayList<Material> materials = new ArrayList<Material>();
		
		return mesh;
	}
}
