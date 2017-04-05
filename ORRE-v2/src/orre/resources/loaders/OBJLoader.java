package orre.resources.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import orre.resources.incompleteResources.IncompleteModelPart;
import orre.resources.loaders.obj.OBJFileLineReader;
import orre.resources.loaders.obj.OBJLoadingContext;
import orre.resources.loaders.obj.OBJStatsContext;
import orre.resources.loaders.obj.OBJStatsLineReader;
import orre.util.FileUtil;
import orre.util.StringUtils;

public class OBJLoader {
	public static List<IncompleteModelPart> load(String src) throws Exception
	{
		File objFile = new File(src);
		OBJStatsContext statsContext = OBJStatsLineReader.analyseOBJFile(objFile);
		
		OBJLoadingContext context = new OBJLoadingContext(new File(src).getParentFile(), statsContext);
		List<IncompleteModelPart> parts = parseOBJFile(objFile, context);
		
		context.destroy();
		return parts;
	}


	
	private static List<IncompleteModelPart> parseOBJFile(String[] objFile, OBJLoadingContext context) throws Exception {
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
