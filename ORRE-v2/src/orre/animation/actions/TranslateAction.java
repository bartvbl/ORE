package orre.animation.actions;

import orre.animation.Animatable;
import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.geom.Axis;
import orre.sceneGraph.CoordinateNode;

public class TranslateAction extends AnimationAction {

	private final String partName;
	private final Axis axis;
	private final double units;

	public TranslateAction(String partName, Axis axis, double units) {
		super(AnimationActionType.TRANSLATE);
		this.partName = partName;
		this.axis = axis;
		this.units = units;
	}

	@Override
	public void update(Animatable target, double percentElapsed, double timeSinceLastUpdate) {
		CoordinateNode part = target.getModelPartByName(partName);
		double distanceTranslated = percentElapsed * units;
		if(axis == Axis.x) {
			part.translate(distanceTranslated, 0, 0);
		} else if(axis == Axis.y) {
			part.translate(0, distanceTranslated, 0);
		} else if(axis == Axis.z) {
			part.translate(0, 0, distanceTranslated);
		}
	}

}
