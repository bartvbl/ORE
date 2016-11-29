package orre.resources.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import orre.animation.Animation;
import orre.animation.AnimationAction;
import orre.animation.AnimationLineParser;
import orre.animation.KeyFrame;
import orre.resources.Finalizable;
import orre.resources.ResourceQueue;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.UnloadedResource;

public class AnimationLoader implements ResourceTypeLoader {
	@Override
	public Finalizable loadResource(UnloadedResource source, ResourceQueue queue) {
		return load(source);
	}

	@Override
	public ResourceType getResourceType() {
		return ResourceType.animation;
	}
	
	public static Animation load(UnloadedResource animationFile) {
		try {			
			return parseAnimationFile(animationFile);
		}
		catch(Exception e) {
			System.out.println("Failed to load animation at \"" + animationFile.location + "\".");
			System.out.println("Reason: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private static Animation parseAnimationFile(UnloadedResource animationFile) throws Exception {
		FileReader fileReader = new FileReader(animationFile.location);
		BufferedReader reader = new BufferedReader(fileReader);

		ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>();
		
		ArrayList<AnimationAction> actions = new ArrayList<AnimationAction>();
		String keyframeLine = null;
		//dummy keyframe to use to ensure sanity of the animation script
		KeyFrame currentKeyFrame = null;
		
		String animationName = parseFirstLine(reader.readLine());
		
		while(reader.ready()) {
			String line = reader.readLine().trim();
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
		return new Animation(animationName, framesArray); 
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
		boolean isInstant = false;
		if(tokens[4].equals("infinity")) {
			isInfinite = true;
		} else if(tokens[4].equals("instant")) {
			isInstant = true;
		} else {
			duration = Double.parseDouble(tokens[4]);
			if(duration == 0) {
				throw new Exception("The duration of a keyframe can not be 0. If you want an instantanious change, use \"of duration instant\".");
			}
		}
		AnimationAction[] actionList = actions.toArray(new AnimationAction[actions.size()]);
		KeyFrame newFrame = new KeyFrame(tokens[1], duration, isInfinite, isInstant, actionList);
		return newFrame;
	}
}
