package orre.animation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class AnimationLoader {
	public static Animation load(String src) {
		try {			
			return parseAnimationFile(src);
		}
		catch(Exception e) {
			System.out.println("Failed to load animation at \"" + src + "\".");
			System.out.println("Reason: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private static Animation parseAnimationFile(String src) throws Exception {
		FileReader fileReader = new FileReader(src);
		BufferedReader reader = new BufferedReader(fileReader);

		ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>();
		
		String animationName = parseFirstLine(reader.readLine());
		
		while(reader.ready()) {
			String line = reader.readLine();
			if(line.startsWith("keyframe")) {
				parseKeyFrameLine(line, keyFrames);
			} else if(line.equals("end animation")) {
				break;
			} else if(line.startsWith("#")) {
				continue;
			} else {
				KeyFrame currentFrame = getCurrentFrame(keyFrames);
				AnimationAction action = AnimationLineParser.parseLine(line, currentFrame);
				currentFrame.addAction(action);					
			}
		}
		
		reader.close();
		fileReader.close();
		KeyFrame[] framesArray = keyFrames.toArray(new KeyFrame[keyFrames.size()]);
		return new Animation(animationName, framesArray); 
	}

	private static String parseFirstLine(String line) throws Exception {
		if(!line.startsWith("animation")) {
			throw new Exception("The first line of an animation file must contain an animation definition ('animation <name>').");
		}
		String[] parts = line.split(" ");
		return parts[1];
	}

	private static KeyFrame getCurrentFrame(ArrayList<KeyFrame> keyFrames) throws Exception {
		if(keyFrames.size() == 0) {
			throw new Exception("A keyframe must be defined before commands can be supplied");
		}
		return keyFrames.get(keyFrames.size() - 1);
	}

	private static void parseKeyFrameLine(String line, ArrayList<KeyFrame> keyFrames) {
		String[] tokens = line.split(" ");
		double duration = 0;
		boolean isInfinite = false;
		if(tokens[4].equals("infinity")) {
			isInfinite = true;
		} else {
			duration = Double.parseDouble(tokens[4]);
		}
		KeyFrame newFrame = new KeyFrame(tokens[1], duration, isInfinite);
		keyFrames.add(newFrame);
	}
}
