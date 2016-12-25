package orre.resources.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

import orre.animation.Animation;
import orre.animation.AnimationAction;
import orre.animation.Ease;
import orre.animation.KeyFrame;
import orre.animation.TransitionType;
import orre.animation.actions.RotationAction;
import orre.animation.actions.ScaleAction;
import orre.animation.actions.SetAction;
import orre.animation.actions.TranslateAction;
import orre.geom.Axis;
import orre.resources.Finalizable;
import orre.resources.ResourceQueue;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.UnloadedResource;
import orre.resources.data.LWSAnimation;
import orre.resources.data.LWSKeyFrame;

public class LWSLoader implements ResourceTypeLoader {
	private static final double SCALE_CONVERSION_FACTOR = 0.001;
	
	private static final Axis lwsXAxis = Axis.y;
	private static final Axis lwsYAxis = Axis.x;
	private static final Axis lwsZAxis = Axis.z;
	
	@Override
	public Finalizable loadResource(UnloadedResource source, ResourceQueue queue) throws Exception {
		BufferedReader fileReader = new BufferedReader(new FileReader(source.location));
		
		readHeader(fileReader);
		Animation animation = readBody(fileReader, source.name);
		
		fileReader.close();
		
//		System.out.println("--- Animation ---");
//		for(KeyFrame frame : animation.keyFrames) {
//			if(frame == null) {
//				System.out.println("Frame is null!");
//				continue;
//			}
//			System.out.println("KeyFrame: " + frame.name + " of duration " + frame.duration);
//			for(AnimationAction action : frame.actions) {
//				System.out.println("\t" + action);
//			}
//		}
		
		return animation;
	}

	private Animation readBody(BufferedReader fileReader, String name) throws IOException {
		int firstFrame, lastFrame, frameStep;
		double framesPerSecond = 1;
		
		ArrayList<LWSAnimation> parsedAnimations = new ArrayList<LWSAnimation>();
		
		while(fileReader.ready()) {
			String line = seekNextLine(fileReader);
			line = line.trim(); // remove indentation, whitespace
			String[] parts = line.split(" ");
			
			if(parts[0].equals("FirstFrame")) {
				firstFrame = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("LastFrame")) {
				lastFrame = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("FrameStep")) {
				frameStep = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("FramesPerSecond")) {
				framesPerSecond = Double.parseDouble(parts[1]);
			} else if(parts[0].equals("LoadObject") || parts[0].equals("AddNullObject")) {
				LWSAnimation animation = parseAnimation(fileReader);
				if(animation != null) {
					parsedAnimations.add(animation);
				}
			}
		}
		
		Animation animation = convertAnimations(parsedAnimations, framesPerSecond, name);
		
		return animation;
	}

	private Animation convertAnimations(ArrayList<LWSAnimation> parsedAnimations, double framesPerSecond, String name) {
		
		// Figure out which frame numbers have been defined
		int parsedAnimationCount = parsedAnimations.size();
		
		HashSet<Integer> frameNumbersSet = new HashSet<Integer>();
		for(LWSAnimation anim : parsedAnimations) {
			for(LWSKeyFrame frame : anim.frames) {
				frameNumbersSet.add(frame.frameNumber);
			}
		}
		
//		for(LWSAnimation animation : parsedAnimations) {
//			System.out.println("Animation " + animation.partName);
//			for(LWSKeyFrame frame : animation.frames) {
//				System.out.println("\t" + frame);
//			}
//		}
		
		// Next, sort them in ascending order

		int frameCount = frameNumbersSet.size();
		
		Integer[] frameNumbers = new Integer[frameCount];
		frameNumbersSet.toArray(frameNumbers);
		Arrays.sort(frameNumbers);
		
		// Convert frame numbers to frame times
		
		double[] frameTimes = new double[frameCount];
		for(int i = 0; i < frameTimes.length; i++) {
			frameTimes[i] = (double) frameNumbers[i] / framesPerSecond;
		}
//		System.out.println("FRAME TIMES: " + Arrays.toString(frameTimes));
		
		// One less keyframe because we're working with deltas, one more because frame 0 needs to set up the initial state.
		KeyFrame[] keyFrames = new KeyFrame[frameCount - 1 + 1];
		
		ArrayList<AnimationAction> setActions = new ArrayList<AnimationAction>();
		for(LWSAnimation animation : parsedAnimations) {
			SetAction translateXAction = new SetAction(animation.partName, Axis.x, TransitionType.position, animation.frames[0].translationX);
			setActions.add(translateXAction);
			SetAction translateYAction = new SetAction(animation.partName, Axis.y, TransitionType.position, animation.frames[0].translationY);
			setActions.add(translateYAction);
			SetAction translateZAction = new SetAction(animation.partName, Axis.z, TransitionType.position, animation.frames[0].translationZ);
			setActions.add(translateZAction);
			
			SetAction rotateXAction = new SetAction(animation.partName, lwsXAxis, TransitionType.rotation, animation.frames[0].rotationX);
			setActions.add(rotateXAction);
			SetAction rotateYAction = new SetAction(animation.partName, lwsYAxis, TransitionType.rotation, animation.frames[0].rotationY);
			setActions.add(rotateYAction);
			SetAction rotateZAction = new SetAction(animation.partName, lwsZAxis, TransitionType.rotation, animation.frames[0].rotationZ);
			setActions.add(rotateZAction);
			
			SetAction scaleXAction = new SetAction(animation.partName, lwsXAxis, TransitionType.scale, animation.frames[0].scaleX);
			setActions.add(scaleXAction);
			SetAction scaleYAction = new SetAction(animation.partName, lwsYAxis, TransitionType.scale, animation.frames[0].scaleY);
			setActions.add(scaleYAction);
			SetAction scaleZAction = new SetAction(animation.partName, lwsZAxis, TransitionType.scale, animation.frames[0].scaleZ);
			setActions.add(scaleZAction);
		}
		
		// create the setup frame (overwrites current part positions!)
		keyFrames[0] = new KeyFrame("Frame 0 (setup)", 0, false, true, setActions.toArray(new SetAction[setActions.size()]));
		
		// Note that since ORE's animation format uses deltas, we only convert up to the final frame.
		for(int i = 0; i < frameCount - 1; i++) {
			int frameNumber = frameNumbers[i];
			int nextFrameNumber = frameNumbers[i + 1];
			
			ArrayList<AnimationAction> actions = new ArrayList<AnimationAction>();
			for(int j = 0; j < parsedAnimationCount; j++) {
				sample(frameNumber, nextFrameNumber, parsedAnimations.get(j), actions);				
			}
			
			AnimationAction[] frameActions = new AnimationAction[actions.size()];
			actions.toArray(frameActions);
			double frameDuration = (frameTimes[i + 1] - frameTimes[i]);
			keyFrames[i + 1] = new KeyFrame("Frame " + i + " (LWS frame " + frameNumber + ")", frameDuration, false, false, frameActions);
		}
		
		return new Animation(name, keyFrames);
	}

	private void sample(int globalStartFrame, int globalNextFrame, LWSAnimation lwsAnimation, ArrayList<AnimationAction> actions) {
		// This function samples the input LWS animation between two specific frame numbers, 
		// and determines any actions which describe animation changes, which are added to a list.
		// Assumes that the start and end global frame parameters are placed at places where any frame starts or ends.
		// This means only a single linear action needs to be calculated within a single frame within the LWS animation.
		
		int lwsFrameNumber = findLWSFrame(globalStartFrame, lwsAnimation);
		
		LWSKeyFrame currentAnimationFrame = lwsAnimation.frames[lwsFrameNumber];
		LWSKeyFrame nextAnimationFrame = lwsAnimation.frames[Math.min(lwsFrameNumber + 1, lwsAnimation.frames.length - 1)];
		
		int currentFrameStart = currentAnimationFrame.frameNumber;
		int nextFrameStart = nextAnimationFrame.frameNumber;
		
		int currentFrameDuration = nextFrameStart - currentFrameStart;
		
		int startProgressFrames = globalStartFrame - currentFrameStart;
		double startProgressPercent = (double) startProgressFrames / (double) currentFrameDuration;
		
		int endProgressFrames = globalNextFrame - currentFrameStart;
		double endProgressPercent = (double) endProgressFrames / (double) currentFrameDuration;
		
		double percentDelta = endProgressPercent - startProgressPercent;
		
		// Now we look which values changed, and add actions for each of them.
		// ORE's coordinate system: z is up
		// So x, y, z becomes z, x, y
		
		if(currentAnimationFrame.translationX != nextAnimationFrame.translationX) {
			double units = (nextAnimationFrame.translationX - currentAnimationFrame.translationX) * percentDelta;
			TranslateAction translation = new TranslateAction(lwsAnimation.partName, lwsXAxis, units, Ease.NO_EASE);
			actions.add(translation);
		}
		if(currentAnimationFrame.translationY != nextAnimationFrame.translationY) {
			double units = (nextAnimationFrame.translationY - currentAnimationFrame.translationY) * percentDelta;
			TranslateAction translation = new TranslateAction(lwsAnimation.partName, lwsYAxis, units, Ease.NO_EASE);
			actions.add(translation);
		}
		if(currentAnimationFrame.translationZ != nextAnimationFrame.translationZ) {
			double units = (nextAnimationFrame.translationZ - currentAnimationFrame.translationZ) * percentDelta;
			TranslateAction translation = new TranslateAction(lwsAnimation.partName, lwsZAxis, units, Ease.NO_EASE);
			actions.add(translation);
		}
		
		if(currentAnimationFrame.rotationX != nextAnimationFrame.rotationX) {
			double units = (nextAnimationFrame.rotationX - currentAnimationFrame.rotationX) * percentDelta;
			RotationAction translation = new RotationAction(lwsAnimation.partName, lwsXAxis, units, Ease.NO_EASE);
			actions.add(translation); // z
		}
		if(currentAnimationFrame.rotationY != nextAnimationFrame.rotationY) {
			double units = (nextAnimationFrame.rotationY - currentAnimationFrame.rotationY) * percentDelta;
			RotationAction translation = new RotationAction(lwsAnimation.partName, lwsYAxis, units, Ease.NO_EASE);
			actions.add(translation); // x
		}
		if(currentAnimationFrame.rotationZ != nextAnimationFrame.rotationZ) {
			double units = (nextAnimationFrame.rotationZ - currentAnimationFrame.rotationZ) * percentDelta;
			RotationAction translation = new RotationAction(lwsAnimation.partName, lwsZAxis, units, Ease.NO_EASE);
			actions.add(translation); // y
		}
		
		if(currentAnimationFrame.scaleX != nextAnimationFrame.scaleX) {
			double units = (nextAnimationFrame.scaleX - currentAnimationFrame.scaleX) * percentDelta;
			ScaleAction translation = new ScaleAction(lwsAnimation.partName, lwsXAxis, units);
			actions.add(translation);
		}
		if(currentAnimationFrame.scaleY != nextAnimationFrame.scaleY) {
			double units = (nextAnimationFrame.scaleY - currentAnimationFrame.scaleY) * percentDelta;
			ScaleAction translation = new ScaleAction(lwsAnimation.partName, lwsYAxis, units);
			actions.add(translation);
		}
		if(currentAnimationFrame.scaleZ != nextAnimationFrame.scaleZ) {
			double units = (nextAnimationFrame.scaleZ - currentAnimationFrame.scaleZ) * percentDelta;
			ScaleAction translation = new ScaleAction(lwsAnimation.partName, lwsZAxis, units);
			actions.add(translation);
		}
	}
	

	private int findLWSFrame(int frameNumber, LWSAnimation lwsAnimation) {
		int currentFrameNumber = 0;
		
//		System.out.println("Finding frame " + frameNumber + " in: ");
//		for(LWSKeyFrame frame : lwsAnimation.frames) {
//			System.out.println("\t" + frame.frameNumber);
//		}
		
		// Note that the final frame is again excluded
		while(currentFrameNumber < lwsAnimation.frames.length - 1 && frameNumber >= lwsAnimation.frames[currentFrameNumber + 1].frameNumber) {
			currentFrameNumber++;
		}
		
		System.out.println("Chosen: " + currentFrameNumber);
		return currentFrameNumber;
	}

	private LWSAnimation parseAnimation(BufferedReader fileReader) throws IOException {
		// This line always initiates a block specifying an animation.
		// The LoadObject line has a long path to a model file. We're not interested in that, so we'll skip it.

		// The second line of the block is ShowObject. It can tell us whether the object should be visible -> whether we should care about its animation.
		// The command has two parameters, but only the first appears to be of interest.
		// If this parameter is 8, the object is visible.
		// If it is 10, the object is invisible.
		// Since some objects have invisible parts that are not in the models of the game files, the most conservative policy is to throw them out.
		// Parsing the animation thus stops here if the object is invisible.
		
		String line = seekNextLine(fileReader);
		String[] lineParts = line.trim().split(" ");
		int visibilityValue = Integer.parseInt(lineParts[1]);
		if(visibilityValue == 10) {
			return null;
		}
		if(visibilityValue != 8 || !lineParts[0].equals("ShowObject")) {
			throw new RuntimeException("Something about this animation file is different! (ShowObject)");
		}
		
		// The third line defines the animation sequence.
		// The command is ObjectMotion. This is usually the name of the animation, but we'll exploit use it here as a hack to define which
		// parts should be mapped to which animation. 
		
		line = seekNextLine(fileReader);
		lineParts = line.trim().split(" ");
		String partName = lineParts[1];
		if(!lineParts[0].equals("ObjectMotion")) {
			throw new RuntimeException("Something about this animation file is different! (ObjectMotion)");
		}
		
		// Now begins the definition of the animation itself. There are now two lines with 1 integer each. 
		// The first seems to always be 9.
		// The second indicates the number of keyframes.
		line = seekNextLine(fileReader);
		if(!line.trim().equals("9")) {
			throw new RuntimeException("Something about this animation file is different! (9 line)");
		}
		
		line = seekNextLine(fileReader);
		int keyframeCount = Integer.parseInt(line.trim());
		
		// Keyframes are defined in pairs of two lines. 
		// The first defines translation, rotation and scale (in that order)
		// The second has miscellaneous settings, of which only the first is of note; the frame number
		
		LWSKeyFrame[] frames = new LWSKeyFrame[keyframeCount];
		
		for(int frame = 0; frame < keyframeCount; frame++) {
			line = seekNextLine(fileReader);
			lineParts = line.trim().split(" ");
			
			double translationX = Double.parseDouble(lineParts[0]) * SCALE_CONVERSION_FACTOR;
			double translationY = Double.parseDouble(lineParts[1]) * SCALE_CONVERSION_FACTOR;
			double translationZ = Double.parseDouble(lineParts[2]) * SCALE_CONVERSION_FACTOR;
			
			double rotationX = Double.parseDouble(lineParts[3]);
			double rotationY = Double.parseDouble(lineParts[4]);
			double rotationZ = Double.parseDouble(lineParts[5]);
			
			double scaleX = Double.parseDouble(lineParts[6]);
			double scaleY = Double.parseDouble(lineParts[7]);
			double scaleZ = Double.parseDouble(lineParts[8]);
			
			line = seekNextLine(fileReader);
			lineParts = line.trim().split(" ");
			
			int keyFrameID = Integer.parseInt(lineParts[0]);
			
			frames[frame] = new LWSKeyFrame(
					keyFrameID,
					translationX, translationY, translationZ,
					rotationX, rotationY, rotationZ,
					scaleX, scaleY, scaleZ);
		}
		
		// The final line we're interested in is EndBehavior
		// It defines properties such as whether the object should disappear or whether the animation should loop.
		
		line = seekNextLine(fileReader);
		lineParts = line.trim().split(" ");
		int endBehaviourValue = Integer.parseInt(lineParts[1]);
		
		return new LWSAnimation(partName, frames, endBehaviourValue);
	}

	private void readHeader(BufferedReader fileReader) throws IOException {
		String firstLine = seekNextLine(fileReader).trim();
		if(!firstLine.equals("LWSC")) {
			throw new RuntimeException("Unexpected LWS header. Is this really an LWO file?");
		}
		String versionLine = seekNextLine(fileReader);
		if(!versionLine.equals("1")) {
			throw new RuntimeException("Only version 1 of LWS files is supported.");
		}
	}
	
	private String seekNextLine(BufferedReader fileReader) throws IOException {
		String line = fileReader.readLine();
		while(line.trim().equals("") && fileReader.ready()) {
			line = fileReader.readLine();
		}
		return line;
	}

	@Override
	public Enum<?> getResourceType() {
		return ResourceType.lwsAnimation;
	}

}
