package orre.resources.loaders.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;
import orre.util.FeedbackProvider;
import orre.util.StringUtils;

public class OBJLoader {
	public static List<PartiallyLoadableModelPart> load(String src)
	{
		try {
			loadObjFile(src);
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
		
		
		BufferedReader objFileAnalysisReader = openObjFile(src);
		OBJStatsContext statsContext = new OBJStatsContext();
		analyseObjFile(objFileAnalysisReader);
		objFileAnalysisReader.close();
		
		BufferedReader objFileParsingReader = openObjFile(src);
		OBJLoadingContext context = new OBJLoadingContext(new File(src).getParentFile());
		List<PartiallyLoadableModelPart> parts = parseOBJFile(objFileParsingReader, context);
		objFileParsingReader.close();
		
		context.destroy();
		return parts;
	}

	private static BufferedReader openObjFile(String src) throws FileNotFoundException {
		FileReader fileReader = new FileReader(src);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		return bufferedReader;
	}
	
	private static void analyseObjFile(BufferedReader bufferedReader) throws IOException {
		while(bufferedReader.ready())
		{
			String line = bufferedReader.readLine();
			line = StringUtils.stripString(line);
			
		}
	}
	
	private static List<PartiallyLoadableModelPart> parseOBJFile(BufferedReader bufferedReader, OBJLoadingContext context) throws IOException {
		while(bufferedReader.ready())
		{
			String line = bufferedReader.readLine();
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
