package orre.resources.loaders.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import orre.resources.incompleteResources.BlueprintMaterial;
import orre.util.StringUtils;

public class MTLLibLoader {
	public static void parseMaterialLibFile(String src, OBJLoadingContext context) throws Exception
	{
		String source = context.getContainingDirectory().getPath() + File.separator + src;
		FileReader fileReader = new FileReader(source);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		while(bufferedReader.ready())
		{
			String line = bufferedReader.readLine();
			line = StringUtils.stripString(line);
			readMaterialLibLine(line, context);
		}
	}
	
	private static void readMaterialLibLine(String line, OBJLoadingContext context) throws Exception
	{
		if((line.length() == 0) || (line.charAt(0) == '#')) {
			return;
		} else if(line.startsWith("newmtl")) {
			MTLLibLoader.readNewMtlLine(line, context);
		} else if(line.startsWith("Ka")) {
			MTLLibLoader.readAmbientColourLine(line, context);
		} else if(line.startsWith("Kd")) {
			MTLLibLoader.readDiffuseColourLine(line, context);
		} else if(line.startsWith("Ks")) {
			MTLLibLoader.readSpecularColourLine(line, context);
		} else if(line.startsWith("Ke")) {
			MTLLibLoader.readEmissionColourLine(line, context);
		} else if(line.startsWith("Tr") || line.startsWith("d")) {
			MTLLibLoader.readTransparencyLine(line, context);
		} else if(line.startsWith("map_Ka")) {
			MTLLibLoader.readAmbientTextureLine(line, context);
		} else if(line.startsWith("map_Kd")) {
			MTLLibLoader.readDiffuseTextureLine(line, context);
		} else if(line.startsWith("map_Ks")) {
			MTLLibLoader.readSpecularTextureLine(line, context);
		} else if(line.startsWith("illum")) {
			MTLLibLoader.readIlluminationLine(line, context);
		} else if(line.startsWith("Ns")) {
			MTLLibLoader.readShininessLine(line, context);
		}
	}

	private static void readNewMtlLine(String line, OBJLoadingContext context) {
		String materialName = line.split(" ")[1];
		BlueprintMaterial material = new BlueprintMaterial(materialName);
		context.addMaterial(material);
		context.setMaterial(materialName);
	}

	private static void readAmbientColourLine(String line, OBJLoadingContext context) {
		BlueprintMaterial currentMaterial = context.getCurrentMaterial();
		currentMaterial.setAmbientColour(OBJLoadingUtils.parseFloatLine(line));
	}
	private static void readDiffuseColourLine(String line, OBJLoadingContext context) {
		BlueprintMaterial currentMaterial = context.getCurrentMaterial();
		currentMaterial.setDiffuseColour(OBJLoadingUtils.parseFloatLine(line));
	}
	private static void readSpecularColourLine(String line, OBJLoadingContext context) {
		BlueprintMaterial currentMaterial = context.getCurrentMaterial();
		currentMaterial.setSpecularColour(OBJLoadingUtils.parseFloatLine(line));
	}
	private static void readEmissionColourLine(String line, OBJLoadingContext context) {
		BlueprintMaterial currentMaterial = context.getCurrentMaterial();
		currentMaterial.setEmissionColour(OBJLoadingUtils.parseFloatLine(line));
	}
	private static void readTransparencyLine(String line, OBJLoadingContext context) {
		String alphaValue = line.split(" ")[1];
		BlueprintMaterial currentMaterial = context.getCurrentMaterial();
		currentMaterial.setAlpha(Float.parseFloat(alphaValue));
	}
	
	private static void readAmbientTextureLine(String line, OBJLoadingContext context) throws Exception {
		String textureSrc = line.split(" ")[1];
		BlueprintMaterial currentMaterial = context.getCurrentMaterial();
		currentMaterial.setAmbientTexture(textureSrc, context);
	}
	private static void readDiffuseTextureLine(String line, OBJLoadingContext context) throws Exception {
		String textureSrc = line.split(" ")[1];
		BlueprintMaterial currentMaterial = context.getCurrentMaterial();
		currentMaterial.setDiffuseTexture(textureSrc, context);
		
	}
	private static void readSpecularTextureLine(String line, OBJLoadingContext context) throws Exception {
		String textureSrc = line.split(" ")[1];
		BlueprintMaterial currentMaterial = context.getCurrentMaterial();
		currentMaterial.setSpecularTexture(textureSrc, context);
	}
	
	private static void readIlluminationLine(String line, OBJLoadingContext context) {
		
	}
	
	private static void readShininessLine(String line, OBJLoadingContext context) {
		String shininessValue = line.split(" ")[1];
		BlueprintMaterial currentMaterial = context.getCurrentMaterial();
		currentMaterial.setShininess(Float.parseFloat(shininessValue));
	}
}
