package orre.resources.loaders.obj;

public class OBJStatsLineReader {
	public static void readOBJLine(OBJStatsContext context, String line) {
		if(line.length() == 0){return;}
		if(line.startsWith("vt")){
			parseTextureCoordinateLine(context);
		} else if(line.startsWith("vn")){
			parseNormalLine(context);
		} else if(line.charAt(0) == 'v'){
			parseVertexLine(context);
		} else if(line.startsWith("mtllib")){
			parseMtlLibLine(context);
		} else if(line.startsWith("usemtl")){
			parseUseMtlLine(context);
		} else if(line.charAt(0) == 'g'){
			parseGroupLine(context, line);
		} else if(line.charAt(0) == 'f'){
			parseFaceLine(context, line);
		}
	}
	
	private static void parseVertexLine(OBJStatsContext context) {
		context.registerVertex();
	}
	private static void parseTextureCoordinateLine(OBJStatsContext context) {
		context.registerTexCoord();
	}
	private static void parseNormalLine(OBJStatsContext context) {
		context.registerNormal();
	}
	private static void parseMtlLibLine(OBJStatsContext context) {}
	private static void parseUseMtlLine(OBJStatsContext context) {}
	private static void parseGroupLine(OBJStatsContext context, String line) {
		String[] parts = line.split(" ");
		context.setModelPart(parts[1]);
	}
	private static void parseFaceLine(OBJStatsContext context, String line) {
		if(!context.bufferDataTypeHasBeenSet()) {
			OBJLoadingUtils.parseFaceFormat(context, line);
		}
		context.registerFace();
	}
}
