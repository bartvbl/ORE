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
}
