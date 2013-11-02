package orre.resources.loaders.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;
import orre.util.FeedbackProvider;
import orre.util.StringUtils;

public class OBJLoader {
	public static List<PartiallyLoadableModelPart> load(String src)
	{
		try {
			return loadObjFile(src);
		} catch (FileNotFoundException e) {
			FeedbackProvider.showLoadOBJFileNotFoundMessage(src);
			e.printStackTrace();
		} catch (IOException e) {
			FeedbackProvider.showLoadOBJFileFailedMessage(src);
			e.printStackTrace();
		}
		return null;
	}
	
	private static List<PartiallyLoadableModelPart> loadObjFile(String src) throws FileNotFoundException, IOException {
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
	
	private static List<PartiallyLoadableModelPart> parseOBJFile(String[] objFile, OBJLoadingContext context) throws IOException {
		for(String line : objFile)
		{
			line = StringUtils.stripString(line);
			context.setCurrentLine(line);
			parseOBJLine(context);
		}
		return context.getModelParts();
	}

	private static void parseOBJLine(OBJLoadingContext context) {
		if((context.getCurrentLine().length() == 0) || (context.getCurrentLine().charAt(0) == '#'))
		{
			return;
		}
		OBJFileLineReader.readOBJLine(context);
	}
}
