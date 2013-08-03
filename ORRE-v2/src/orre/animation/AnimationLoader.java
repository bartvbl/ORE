package orre.animation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class AnimationLoader {
	public static Animation load(String src) {
		try {			
			KeyFrame[] keyFrames = loadKeyframesFromFile(src);
			return new Animation(keyFrames);
		}
		catch(Exception e) {
			System.out.println("Failed to load animation at \"" + src + "\".");
			e.printStackTrace();
		}
		return new Animation(new KeyFrame[0]);
	}

	private static KeyFrame[] loadKeyframesFromFile(String src) throws Exception {
		FileReader fileReader = new FileReader(src);
		BufferedReader reader = new BufferedReader(fileReader);
		
		ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>();
		
		while(reader.ready()) {
			String line = reader.readLine();
			String[] tokens = line.split(" ");
			
		}
		
		reader.close();
		fileReader.close();
		return keyFrames.toArray(new KeyFrame[keyFrames.size()]);
	}
}
