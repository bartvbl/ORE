package orre.resources.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import orre.resources.Finalizable;
import orre.resources.ResourceQueue;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.UnloadedResource;
import orre.resources.data.LWSAnimation;
import orre.resources.data.LWSKeyFrame;

public class LWSLoader implements ResourceTypeLoader {
	
	@Override
	public Finalizable loadResource(UnloadedResource source, ResourceQueue queue) throws Exception {
		BufferedReader fileReader = new BufferedReader(new FileReader(source.location));
		
		readHeader(fileReader);
		readBody(fileReader);
		
		fileReader.close();
		return null;
	}

	private void readBody(BufferedReader fileReader) throws IOException {
		int firstFrame, lastFrame, frameStep;
		double framesPerSecond;
		
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
			
			double translationX = Double.parseDouble(lineParts[0]);
			double translationY = Double.parseDouble(lineParts[0]);
			double translationZ = Double.parseDouble(lineParts[0]);
			
			double rotationX = Double.parseDouble(lineParts[0]);
			double rotationY = Double.parseDouble(lineParts[0]);
			double rotationZ = Double.parseDouble(lineParts[0]);
			
			double scaleX = Double.parseDouble(lineParts[0]);
			double scaleY = Double.parseDouble(lineParts[0]);
			double scaleZ = Double.parseDouble(lineParts[0]);
			
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
		String firstLine = seekNextLine(fileReader);
		if(!firstLine.equals("LWSC")) {
			throw new RuntimeException("Unexpected LWS header. Is this really an LWO file?");
		}
		String versionLine = seekNextLine(fileReader);
		if(!versionLine.equals("1")) {
			throw new RuntimeException("Only version 1 of LWO files is supported.");
		}
	}
	
	private String seekNextLine(BufferedReader fileReader) throws IOException {
		String line = fileReader.readLine();
		while(!line.equals("") && fileReader.ready()) {
			line = fileReader.readLine();
		}
		return line;
	}

	@Override
	public Enum<?> getResourceType() {
		return ResourceType.lwsAnimation;
	}

}
