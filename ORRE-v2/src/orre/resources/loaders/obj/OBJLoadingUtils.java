package orre.resources.loaders.obj;

import orre.gl.vao.VBOFormat;

public class OBJLoadingUtils {
	public static float[] parseFloatLine(String line) {
		String[] parts = line.split(" ");
		float[] floatLine = new float[parts.length-1];//the array from the split string includes the line identifier (f.ex. v, vt, vn, etc) 
		for(int i = 1; i < parts.length; i++)
		{
			floatLine[i-1] = Float.parseFloat(parts[i]);
		}
		return floatLine;
	}
	
	public static double[] parseDoubleLine(String line) {
		String[] parts = line.split(" ");
		double[] doubleLine = new double[parts.length-1];//the array from the split string includes the line identifier (f.ex. v, vt, vn, etc) 
		for(int i = 1; i < parts.length; i++)
		{
			doubleLine[i-1] = Double.parseDouble(parts[i]);
		}
		return doubleLine;
	}
	
	public static int[] parseIntString(String line, char separator)
	{
		String[] parts = line.split(Character.toString(separator));
		int[] intArray = new int[parts.length];
		for(int i = 0; i < parts.length; i++) {
			if(parts[i].length() == 0)
			{
				intArray[i] = 0;
			} else {				
				intArray[i] = Integer.parseInt(parts[i]);
			}
		}
		return intArray;
	}

	public static VBOFormat parseFaceFormat(String line) {
		String face = line.split(" ")[1];
		String[] parts = face.split("/");
		if(parts[1].length() == 0) {
			if(parts[2].length() == 0) {
				return VBOFormat.VERTICES;				
			} else {
				return VBOFormat.VERTICES_AND_NORMALS;
			}
		} else if(parts[2].length() == 0)
		{
			return VBOFormat.VERTICES_AND_TEXTURES;
		} else {
			return VBOFormat.VERTICES_TEXTURES_NORMALS;
		}
	}

	
}
