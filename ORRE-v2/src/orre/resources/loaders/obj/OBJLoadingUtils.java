package orre.resources.loaders.obj;

public class OBJLoadingUtils {
	public static float[] parseFloatLine(String line) {
		String[] parts = line.split(" ");
		float[] floatLine = new float[parts.length-1];//the parts array includes the line identifier (f.ex. v, vt, vn, etc) 
		for(int i = 1; i < parts.length; i++)
		{
			floatLine[i-1] = Float.parseFloat(parts[i]);
		}
		return floatLine;
	}
	
	
}
