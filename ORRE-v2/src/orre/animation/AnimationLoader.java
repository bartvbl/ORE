package orre.animation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

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
		
		ArrayList<AnimationAction> actions = new ArrayList<AnimationAction>();
		String keyframeLine = null;
		//dummy keyframe to use to ensure sanity of the animation script
		KeyFrame currentKeyFrame = null;
		
		String animationName = parseFirstLine(reader.readLine());
		
		while(reader.ready()) {
			String line = reader.readLine();
			if(line.startsWith("keyframe")) {
				if(keyframeLine != null) {
					KeyFrame newFrame = parseKeyFrameLine(keyframeLine, actions);
					keyFrames.add(newFrame);
					actions.clear();
				}
				currentKeyFrame = parseKeyFrameLine(line, actions);
				keyframeLine = line;
			} else if(line.equals("end animation")) {
				break;
			} else if(line.startsWith("#") || line.equals("")) {
				continue;
			} else {
				AnimationAction action = AnimationLineParser.parseLine(line, currentKeyFrame);
				actions.add(action);
			}
		}
		
		keyFrames.add(parseKeyFrameLine(keyframeLine, actions));
		
		reader.close();
		fileReader.close();
		KeyFrame[] framesArray = keyFrames.toArray(new KeyFrame[keyFrames.size()]);
		AnimationType type = AnimationType.valueOf(animationName);
		return new Animation(type, framesArray); 
	}

	private static String parseFirstLine(String line) throws Exception {
		if(!line.startsWith("animation")) {
			throw new Exception("The first line of an animation file must contain an animation definition ('animation <name>').");
		}
		String[] parts = line.split(" ");
		return parts[1];
	}

	private static KeyFrame parseKeyFrameLine(String line, ArrayList<AnimationAction> actions) throws Exception {
		String[] tokens = line.split(" ");
		double duration = 0;
		boolean isInfinite = false;
		if(tokens[4].equals("infinity")) {
			isInfinite = true;
		} else {
			duration = Double.parseDouble(tokens[4]);
			if(duration == 0) {
				throw new Exception("The duration of a keyframe can not be 0. If you want an instantanious change, use a tiny duration instead.");
			}
		}
		AnimationAction[] actionList = actions.toArray(new AnimationAction[actions.size()]);
		KeyFrame newFrame = new KeyFrame(tokens[1], duration, isInfinite, actionList);
		return newFrame;
	}
}
