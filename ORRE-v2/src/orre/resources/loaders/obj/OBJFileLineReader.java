package orre.resources.loaders.obj;

import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;

public class OBJFileLineReader {
	public static void readOBJLine(OBJLoadingContext context) {
		if(context.getCurrentLine().startsWith("vt")){
			parseTextureCoordinateLine(context);
		} else if(context.getCurrentLine().startsWith("vn")){
			parseNormalLine(context);
		} else if(context.getCurrentLine().charAt(0) == 'v'){
			parseVertexLine(context);
		} else if(context.getCurrentLine().startsWith("mtllib")){
			parseMtlLibLine(context);
		} else if(context.getCurrentLine().startsWith("usemtl")){
			parseUseMtlLine(context);
		} else if(context.getCurrentLine().charAt(0) == 'g'){
			parseGroupLine(context);
		} else if(context.getCurrentLine().charAt(0) == 'f'){
			parseFaceLine(context);
		}
	}
	
	private static void parseVertexLine(OBJLoadingContext context) {
		float[] values = OBJLoadingUtils.parseFloatLine(context.getCurrentLine());
		context.getBuffergenerator().addVertex(values[0], values[1], values[2]);
	}
	private static void parseTextureCoordinateLine(OBJLoadingContext context) {
		float[] values = OBJLoadingUtils.parseFloatLine(context.getCurrentLine());
		context.getBuffergenerator().addTextureCoordinate(values[0], values[1]);
	}
	private static void parseNormalLine(OBJLoadingContext context) {
		float[] values = OBJLoadingUtils.parseFloatLine(context.getCurrentLine());
		context.getBuffergenerator().addNormal(values[0], values[1], values[2]);
	}
	private static void parseMtlLibLine(OBJLoadingContext context) {
		String mtlLibSrc = context.getCurrentLine().split(" ")[1];
		MTLLibLoader.parseMaterialLibFile(mtlLibSrc, context);
	}
	private static void parseUseMtlLine(OBJLoadingContext context) {
		String materialName = context.getCurrentLine().split(" ")[1];
		context.setMaterial(materialName);
	}
	private static void parseGroupLine(OBJLoadingContext context) {
		String partName = context.getCurrentLine().split(" ")[1];
		context.setCurrentModelPart(partName);
	}
	private static void parseFaceLine(OBJLoadingContext context) {
		String[] parts = context.getCurrentLine().split(" ");
		for(int i = 1; i < parts.length; i++) {
			int[] face = OBJLoadingUtils.parseIntString(parts[i], '/');
			float[] vertex = context.getBuffergenerator().getVertex(face[0]-1, face[1]-1, face[2]-1);//OBJ files are 1-indexed. values are stored in the buffer 0-indexed.
			context.addVertexToCurrentModelPart(vertex);
		}
	}
}
