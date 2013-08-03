package orre.animation;

import orre.animation.actions.MoveAction;
import orre.animation.actions.RepeatAction;
import orre.animation.actions.RotationAction;
import orre.animation.actions.ScaleAction;
import orre.animation.actions.SetAction;
import orre.animation.actions.SpinAction;
import orre.animation.actions.TranslateAction;
import orre.geom.Axis;

public class AnimationLineParser {

	public static AnimationAction parseLine(String line, KeyFrame currentFrame) throws Exception {
		String[] tokens = line.split(" ");
		if(tokens[0].equals("rotate")) {
			assureKeyframeNotInfinite(currentFrame, line);
			return parseRotateLine(tokens);
		} else if(tokens[0].equals("spin")) {
			return parseSpinLine(tokens);
		} else if(tokens[0].equals("translate")) {
			assureKeyframeNotInfinite(currentFrame, line);
			return parseTranslateLine(tokens);
		} else if(tokens[0].equals("move")) {
			return parseMoveLine(tokens);
		} else if(tokens[0].equals("scale")) {
			assureKeyframeNotInfinite(currentFrame, line);
			return parseScaleLine(tokens);
		} else if(tokens[0].equals("repeat")) {
			assureKeyframeNotInfinite(currentFrame, line);
			return parseRepeatLine(tokens);
		} else if(tokens[0].equals("set")) {
			return parseSetLine(tokens);
		} else {
			throw new Exception("The line '"+line+"' does not contain any valid commands.\nValid commands are (#, rotate, spin, translate, move, scale, repeat, end).");
		}
	}

	private static AnimationAction parseRotateLine(String[] tokens) {
		String partName = tokens[1];
		double rotation = Double.parseDouble(tokens[2]);
		Axis axis = parseAxis(tokens[5]);
		return new RotationAction(partName, rotation, axis);
	}

	private static AnimationAction parseSpinLine(String[] tokens) {
		String partName = tokens[1];
		Axis axis = parseAxis(tokens[3]);
		double speedDegrees = Double.parseDouble(tokens[5]);
		return new SpinAction(partName, axis, speedDegrees);
	}

	private static AnimationAction parseTranslateLine(String[] tokens) {
		String partName = tokens[1];
		Axis axis = parseAxis(tokens[3]);
		double units = Double.parseDouble(tokens[5]);
		return new TranslateAction(partName, axis, units);
	}

	private static AnimationAction parseMoveLine(String[] tokens) {
		String partName = tokens[1];
		Axis axis = parseAxis(tokens[3]);
		double speedUnits = Double.parseDouble(tokens[5]);
		return new MoveAction(partName, axis, speedUnits);
	}

	private static AnimationAction parseScaleLine(String[] tokens) {
		String partName = tokens[1];
		Axis axis = parseAxis(tokens[3]);
		double percentage = Double.parseDouble(tokens[5]);
		return new ScaleAction(partName, axis, percentage);
	}

	private static AnimationAction parseRepeatLine(String[] tokens) {
		String targetFrame = tokens[2];
		return new RepeatAction(targetFrame);
	}
	
	private static AnimationAction parseSetLine(String[] tokens) {
		String partName = tokens[1];
		Axis axis = parseAxis(tokens[2]);
		TransitionType type = Enum.valueOf(TransitionType.class, tokens[3]);
		double value = Double.parseDouble(tokens[5]);
		return new SetAction(partName, axis, type, value);
	}
	
	private static void assureKeyframeNotInfinite(KeyFrame currentFrame, String line) throws Exception {
		if(currentFrame.isInfinite) {
			throw new Exception("The operations rotate, move, scale and repeat can not appear in a keyframe of infinite duration.\nAt line: '"+line+"'.");
		}
	}
	
	private static Axis parseAxis(String token) {
		char axisCharacter = token.charAt(0);
		String axisString = new String(new char[]{axisCharacter});
		Axis axis = Enum.valueOf(Axis.class, axisString);
		return axis;
	}
}