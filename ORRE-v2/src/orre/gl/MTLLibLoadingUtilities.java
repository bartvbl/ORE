package orre.gl;

import java.util.ArrayList;

public class MTLLibLoadingUtilities {
	public static void readNewMtlLine(String line, Material material, ArrayList<Material> materialList)
	{
		String[] split = line.split(" ");
		material = new Material(split[1]);
		materialList.add(material);
	}

	public static void readAmbientColourLine(String line, Material material) {
		// TODO Auto-generated method stub
		
	}

	public static void readDiffuseColourLine(String line, Material material) {
		// TODO Auto-generated method stub
		
	}

	public static void readSpecularColourLine(String line, Material material) {
		// TODO Auto-generated method stub
		
	}
}
