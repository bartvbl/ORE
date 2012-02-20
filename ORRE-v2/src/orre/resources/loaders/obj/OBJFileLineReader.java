package orre.resources.loaders.obj;

import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;

public class OBJFileLineReader {
	public static void readOBJLine(OBJLoadingContext context) {
		if(context.getLine().startsWith("vt"))
		{
			parseTextureCoordinateLine(context);
		} else if(context.getLine().startsWith("vn"))
		{
			parseNormalLine(context);
		} else if(context.getLine().charAt(0) == 'v')
		{
			parseVertexLine(context);
		} else if(context.getLine().startsWith("mtllib"))
		{
			parseMtlLibLine(context);
		} else if(context.getLine().startsWith("usemtl"))
		{
			parseUseMtlLine(context);
		} else if(context.getLine().charAt(0) == 'g')
		{
			parseGroupLine(context);
		} else if(context.getLine().charAt(0) == 'f')
		{
			parseFaceLine(context);
		}
	}
	
	private static void parseVertexLine(OBJLoadingContext context) {
		float[] values = OBJLoadingUtils.parseFloatLine(context.getLine());
		context.getBuffergenerator().addVertex(values[0], values[1], values[2]);
	}
	private static void parseTextureCoordinateLine(OBJLoadingContext context) {
		float[] values = OBJLoadingUtils.parseFloatLine(context.getLine());
		context.getBuffergenerator().addTextureCoordinate(values[0], values[1]);
	}
	private static void parseNormalLine(OBJLoadingContext context) {
		float[] values = OBJLoadingUtils.parseFloatLine(context.getLine());
		context.getBuffergenerator().addNormal(values[0], values[1], values[2]);
	}
	private static void parseMtlLibLine(OBJLoadingContext context) {
		String mtlLibSrc = context.getLine().split(" ")[1];
		MTLLibLoader.parseMaterialLibFile(mtlLibSrc, context);
	}
	private static void parseUseMtlLine(OBJLoadingContext context) {
		String materialName = context.getLine().split(" ")[1];
		context.setMaterial(materialName);
	}
	private static void parseGroupLine(OBJLoadingContext context) {
		String partName = context.getLine().split(" ")[1];
		context.addAndUseModelPart(new PartiallyLoadableModelPart(partName));
	}
	private static void parseFaceLine(OBJLoadingContext context) {
		OBJLoadingUtils.parseFaceFormat(context);
		String[] parts = context.getLine().split(" ");
		for(int i = 1; i < parts.length; i++) {
			int[] face = OBJLoadingUtils.parseIntString(parts[i], '/');
			float[] vertex = context.getBuffergenerator().getVertex(face[0], face[1], face[2]);
			context.addVertexToCurrentModelPart(vertex);
		}
	}
}
