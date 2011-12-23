package orre.geom.loaders;

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

public class OBJLoader {
	public Mesh3D load(String src, ResourceCache cache)
	{
		Mesh3D mesh = new Mesh3D();
		HashMap<String, Material> materials = new HashMap<String, Material>();
		
		return mesh;
	}
}
