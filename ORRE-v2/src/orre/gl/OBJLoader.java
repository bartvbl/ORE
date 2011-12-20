package orre.gl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
	private void parseMaterialLib(String src, ArrayList<Material> materialList)
	{
		Material currentMaterial = new Material("<material name missing>");
		try {
			FileReader fileReader = new FileReader(src);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while(bufferedReader.ready())
			{
				String line = bufferedReader.readLine();
				line = stripString(line);
				this.readMaterialLibLine(line, currentMaterial, materialList);
			}
		} catch (FileNotFoundException e) {
			FeedbackProvider.showLoadOBJFileNotFoundMessage(src);
			e.printStackTrace();
		} catch (IOException e) {
			FeedbackProvider.showLoadOBJFileFailedMessage(src);
			e.printStackTrace();
		}
	}
	
	private String stripString(String string)
	{
		return string.trim();
	}
	
	private void readMaterialLibLine(String line, Material material, ArrayList<Material> materialList)
	{
		if(line.charAt(0) == '#')
		{
			return;
		} else if(line.startsWith("newmtl"))
		{
			MTLLibLoadingUtilities.readNewMtlLine(line, material, materialList);
		} else if(line.startsWith("Ka"))
		{
			MTLLibLoadingUtilities.readAmbientColourLine(line, material);
		} else if(line.startsWith("Kd"))
		{
			MTLLibLoadingUtilities.readDiffuseColourLine(line, material);
		} else if(line.startsWith("Ks"))
		{
			MTLLibLoadingUtilities.readSpecularColourLine(line, material);
		}
	}
}
