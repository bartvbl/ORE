package orre.resources.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import orre.resources.loaders.obj.OBJFileLineReader;
import orre.resources.loaders.obj.OBJLoadingContext;
import orre.resources.loaders.obj.OBJStatsContext;
import orre.resources.loaders.obj.OBJStatsLineReader;
import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;
import orre.util.StringUtils;

public class OBJLoader {
	public static List<PartiallyLoadableModelPart> load(String src) throws Exception
	{
		return loadObjFile(src);
	}
	
	private static List<PartiallyLoadableModelPart> loadObjFile(String src) throws Exception {
		String[] objFile = readOBJFile(src);
		
		OBJStatsContext statsContext = new OBJStatsContext();
		analyseObjFile(objFile, statsContext);
		
		OBJLoadingContext context = new OBJLoadingContext(new File(src).getParentFile(), statsContext);
		List<PartiallyLoadableModelPart> parts = parseOBJFile(objFile, context);
		
		context.destroy();
		return parts;
	}

	private static String[] readOBJFile(String src) throws IOException {
		FileReader fileReader = new FileReader(src);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		ArrayList<String> fileContents = new ArrayList<String>();
		while(bufferedReader.ready()) {
			fileContents.add(bufferedReader.readLine());
		}
		bufferedReader.close();
		fileReader.close();
		return fileContents.toArray(new String[fileContents.size()]);
	}
	
	private static void analyseObjFile(String[] objFile, OBJStatsContext context) throws IOException {
		for(String line : objFile)
		{
			line = StringUtils.stripString(line);
			OBJStatsLineReader.readOBJLine(context, line);
		}
	}
	
	private static List<PartiallyLoadableModelPart> parseOBJFile(String[] objFile, OBJLoadingContext context) throws Exception {
		for(String line : objFile)
		{
			line = StringUtils.stripString(line);
			context.setCurrentLine(line);
			parseOBJLine(context);
		}
		return context.getModelParts();
	}

	private static void parseOBJLine(OBJLoadingContext context) throws Exception {
		if((context.getCurrentLine().length() == 0) || (context.getCurrentLine().charAt(0) == '#'))
		{
			return;
		}
		OBJFileLineReader.readOBJLine(context);
	}
}
