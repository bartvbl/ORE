package orre.animation.actions;

import orre.animation.Animatable;
import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.animation.Ease;
import orre.geom.Axis;
import orre.sceneGraph.CoordinateNode;

public class TranslateAction extends AnimationAction {

	private final String partName;
	private final Axis axis;
	private final double units;
	private Ease ease;
	private double totalPercentElapsed;

	public TranslateAction(String partName, Axis axis, double units) {
		super(AnimationActionType.TRANSLATE);
		this.partName = partName;
		this.axis = axis;
		this.units = units;
		this.ease = Ease.NO_EASE;
	}

	public TranslateAction(String partName, Axis axis, double units, Ease ease) {
		this(partName, axis, units);
		this.ease = ease;
	}

	@Override
	public void update(Animatable target, double percentElapsed, double timeSinceLastUpdate) {
		CoordinateNode part = target.getModelPartByName(partName);
		
		double previousPercentage = ease.calculateAnimationPercentage(totalPercentElapsed);
		double currentPercentage = ease.calculateAnimationPercentage(totalPercentElapsed + percentElapsed);
		double percentageDelta = currentPercentage - previousPercentage;
		
		double distanceTranslated = percentageDelta * units;
		if(axis == Axis.x) {
			part.translate(distanceTranslated, 0, 0);
		} else if(axis == Axis.y) {
			part.translate(0, distanceTranslated, 0);
		} else if(axis == Axis.z) {
			part.translate(0, 0, distanceTranslated);
		}
		totalPercentElapsed += percentElapsed;
	}
	
	public String toString() {
		return "Translate Action along the " + axis + "-axis by " + units + " units";
	}

}
