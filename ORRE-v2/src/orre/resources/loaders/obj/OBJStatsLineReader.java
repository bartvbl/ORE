package orre.resources.loaders.obj;

public class OBJStatsLineReader {
	public static void readOBJLine(OBJStatsContext context, String line) {
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
			parseGroupLine(context);
		} else if(line.charAt(0) == 'f'){
			parseFaceLine(context, line);
		}
	}
	
	private static void parseVertexLine(OBJStatsContext context) {
		
	}
	private static void parseTextureCoordinateLine(OBJStatsContext context) {
		
	}
	private static void parseNormalLine(OBJStatsContext context) {
		
	}
	private static void parseMtlLibLine(OBJStatsContext context) {
		
	}
	private static void parseUseMtlLine(OBJStatsContext context) {}
	private static void parseGroupLine(OBJStatsContext context) {
		
	}
	private static void parseFaceLine(OBJStatsContext context, String line) {
		OBJLoadingUtils.parseFaceFormat(context, line);
	}
}
