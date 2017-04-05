package orre.resources.loaders.obj;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import orre.gl.vao.VBOFormat;
import orre.util.FileUtil;
import orre.util.StringUtils;

public class OBJStatsLineReader {
	public static OBJStatistics analyseOBJFile(File objFile) throws IOException {
		String[] fileContents = FileUtil.readFileContents(objFile);
		
		int texCoordCount = 0;
		int normalCount = 0;
		int vertexCount = 0;
		int faceCount = 0;
		
		ArrayList<String> materialNames = new ArrayList<String>();
		ArrayList<String> modelPartNames = new ArrayList<String>();
		
		HashMap<String, String> partMaterialMap = new HashMap<String, String>();
		
		String currentMaterialName = null;
		String currentPartName = null;
		
		boolean hasBufferFormatBeenSet = false;
		VBOFormat bufferFormat = null;
		
		for(String line : fileContents)
		{
			line = StringUtils.stripString(line);
			if(line.length() == 0){
				continue;
			}
			
			if(line.startsWith("mtllib")) {
				String fileName = line.split(" ")[1];
				findMaterials(fileName, objFile, materialNames);
			} else if(line.startsWith("usemtl")) {
				currentMaterialName = line.split(" ")[1];
			} else if(line.startsWith("vt")){
				texCoordCount++;
			} else if(line.startsWith("vn")){
				normalCount++;
			} else if(line.charAt(0) == 'v'){
				vertexCount++;
			} else if(line.charAt(0) == 'g'){
				currentPartName = line.split(" ")[1];
			} else if(line.charAt(0) == 'f'){
				if(!hasBufferFormatBeenSet) {
					bufferFormat = OBJLoadingUtils.parseFaceFormat(line);
					hasBufferFormatBeenSet = true;
				}
			}
		}
		
		OBJStatistics stats = new OBJStatistics(
				vertexCount,
				normalCount,
				texCoordCount, 
				faceCount
			);
		return stats;
	}

	private static void findMaterials(String fileName, File objFile, ArrayList<String> materialNames) throws IOException {
		File mtlFile = new File(objFile.getParentFile().getAbsolutePath() + File.separator + fileName);
		String[] fileContents = FileUtil.readFileContents(mtlFile);
		for(String line : fileContents) {
			if(line.startsWith("newmtl")){
				materialNames.add(line.split(" ")[1]);
			}
		}
	}
}
