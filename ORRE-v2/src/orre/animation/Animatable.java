package orre.animation;

import orre.sceneGraph.CoordinateNode;

public interface Animatable {
	public CoordinateNode getModelPartByName(String name);
	public CoordinateNode getRootNode();
	public void notifyAnimationStart();
	public void notifyAnimationEnd();
}
