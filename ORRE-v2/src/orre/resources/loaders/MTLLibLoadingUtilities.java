package orre.resources.loaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import orre.gl.Material;
import orre.util.FeedbackProvider;
import orre.util.StringUtils;

public class MTLLibLoadingUtilities {
	public static void parseMaterialLibFile(String src, HashMap<String, Material> materialMap)
	{
		Material currentMaterial = new Material("<invalid material file>");
		try {
			FileReader fileReader = new FileReader(src);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while(bufferedReader.ready())
			{
				String line = bufferedReader.readLine();
				line = StringUtils.stripString(line);
				readMaterialLibLine(line, currentMaterial, materialMap);
			}
		} catch (FileNotFoundException e) {
			FeedbackProvider.showLoadOBJFileNotFoundMessage(src);
			e.printStackTrace();
		} catch (IOException e) {
			FeedbackProvider.showLoadOBJFileFailedMessage(src);
			e.printStackTrace();
		}
	}
	
	public static void readNewMtlLine(String line, Material material, HashMap<String, Material> materialMap)
	{
		String[] split = line.split(" ");
		material = new Material(split[1]);
		materialMap.put(material.name, material);
	}
	
	private static void readMaterialLibLine(String line, Material material, HashMap<String, Material> materialMap)
	{
		if(line.charAt(0) == '#')
		{
			return;
		} else if(line.startsWith("newmtl"))
		{
			MTLLibLoadingUtilities.readNewMtlLine(line, material, materialMap);
		} else if(line.startsWith("Ka"))
		{
			MTLLibLoadingUtilities.readAmbientColourLine(line, material);
		} else if(line.startsWith("Kd"))
		{
			MTLLibLoadingUtilities.readDiffuseColourLine(line, material);
		} else if(line.startsWith("Ks"))
		{
			MTLLibLoadingUtilities.readSpecularColourLine(line, material);
		} else if(line.startsWith("Ts") || line.startsWith("d"))
		{
			MTLLibLoadingUtilities.readTransparencyLine(line, material);
		} 
	}

	private static void readAmbientColourLine(String line, Material material) {
		// TODO Auto-generated method stub
		
	}

	private static void readDiffuseColourLine(String line, Material material) {
		// TODO Auto-generated method stub
		
	}

	private static void readSpecularColourLine(String line, Material material) {
		// TODO Auto-generated method stub
		
	}

	private static void readTransparencyLine(String line, Material material) {
		// TODO Auto-generated method stub
		
	}
}
