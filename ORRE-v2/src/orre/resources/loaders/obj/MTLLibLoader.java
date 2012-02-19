package orre.resources.loaders.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import orre.gl.Material;
import orre.util.FeedbackProvider;
import orre.util.StringUtils;

public class MTLLibLoader {
	public static void parseMaterialLibFile(String src, OBJLoadingContext context)
	{
		try {
			String source = context.getContainingDirectory().getPath() + File.separator + src;
			FileReader fileReader = new FileReader(source);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while(bufferedReader.ready())
			{
				String line = bufferedReader.readLine();
				line = StringUtils.stripString(line);
				readMaterialLibLine(line, context);
			}
		} catch (FileNotFoundException e) {
			FeedbackProvider.showLoadOBJFileNotFoundMessage(src);
			e.printStackTrace();
		} catch (IOException e) {
			FeedbackProvider.showLoadOBJFileFailedMessage(src);
			e.printStackTrace();
		}
	}
	
	private static void readMaterialLibLine(String line, OBJLoadingContext context)
	{
		if((line.length() == 0) || (line.charAt(0) == '#'))
		{
			return;
		} else if(line.startsWith("newmtl"))
		{
			MTLLibLoader.readNewMtlLine(line, context);
		} else if(line.startsWith("Ka"))
		{
			MTLLibLoader.readAmbientColourLine(line, context);
		} else if(line.startsWith("Kd"))
		{
			MTLLibLoader.readDiffuseColourLine(line, context);
		} else if(line.startsWith("Ks"))
		{
			MTLLibLoader.readSpecularColourLine(line, context);
		} else if(line.startsWith("Tr") || line.startsWith("d"))
		{
			MTLLibLoader.readTransparencyLine(line, context);
		}
	}
	private static void readNewMtlLine(String line, OBJLoadingContext context) {
		String materialName = line.split(" ")[1];
		Material material = new Material(materialName);
		context.addMaterial(material);
		context.setMaterial(materialName);
	}

	private static void readAmbientColourLine(String line, OBJLoadingContext context) {
		Material currentMaterial = context.getCurrentMaterial();
		currentMaterial.setAmbientColour(OBJLoadingUtils.parseFloatLine(line));
	}

	private static void readDiffuseColourLine(String line, OBJLoadingContext context) {
		Material currentMaterial = context.getCurrentMaterial();
		currentMaterial.setDiffuseColour(OBJLoadingUtils.parseFloatLine(line));
	}

	private static void readSpecularColourLine(String line, OBJLoadingContext context) {
		Material currentMaterial = context.getCurrentMaterial();
		currentMaterial.setSpecularColour(OBJLoadingUtils.parseFloatLine(line));
	}

	private static void readTransparencyLine(String line, OBJLoadingContext context) {
		String alphaValue = line.split(" ")[1];
		Material currentMaterial = context.getCurrentMaterial();
		currentMaterial.setAlpha(Float.parseFloat(alphaValue));
	}
}
