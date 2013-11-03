package orre.ai.pathFinding.map;

import java.util.ArrayList;

import orre.ai.pathFinding.Path;
import orre.animation.Animation;
import orre.animation.AnimationAction;
import orre.animation.KeyFrame;
import orre.animation.TransitionType;
import orre.animation.actions.SetAction;
import orre.animation.actions.TranslateAction;
import orre.geom.Axis;

public class AnimationGenerator {
	private static final double moveSpeed = 0.3;

	public static Animation generateAnimation(Path path) {
		MapTileNode currentNode = (MapTileNode) path.getStartingState();
		MapTileNode nextNode = (MapTileNode) path.getNextState();
		ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>();
		
		int frameNumber = 0;
		
		while(!path.hasFinished()) {
			double dx = nextNode.x - currentNode.x;
			double dy = nextNode.y - currentNode.y;
			double distance = Math.sqrt(dx*dx + dy*dy);
			AnimationAction[] actions = new AnimationAction[3];
			
			actions[0] = new TranslateAction("root", Axis.x, dx);
			actions[1] = new TranslateAction("root", Axis.y, dy);
			actions[2] = new SetAction("root", Axis.z, TransitionType.rotation, Math.atan2(dy, dx));
			
			KeyFrame frame = new KeyFrame("Frame " + frameNumber, distance * moveSpeed, false, actions);
			keyFrames.add(frame);
			currentNode = nextNode;
			nextNode = (MapTileNode) path.getNextState();
		}
		
		return new Animation(null, keyFrames.toArray(new KeyFrame[keyFrames.size()]));
	}

}
