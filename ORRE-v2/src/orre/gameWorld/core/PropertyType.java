package orre.gameWorld.core;

import orre.gameWorld.properties.ChrystalAppearance;
import orre.gameWorld.properties.Flashlight;
import orre.gameWorld.properties.GravityProperty;
import orre.gameWorld.properties.HealthProperty;
import orre.gameWorld.properties.OreAppearance;
import orre.gameWorld.properties.RockRaiderAppearance;
import orre.gameWorld.properties.TaskExecutor;
import orre.gameWorld.properties.Transportable;
import orre.gui.GUI;
import orre.input.KeyboardCameraController;

public enum PropertyType {
	HEALTH(HealthProperty.class), 
	KEYBOARD_CAMERA_CONTROLLER(KeyboardCameraController.class), 
	LIGHT(Flashlight.class), 
	GRAVITY(GravityProperty.class), 
	ROCK_RAIDER_APPEARANCE(RockRaiderAppearance.class), 
	TRANSPORTABLE(Transportable.class), 
	ORE_APPEARANCE(OreAppearance.class), 
	CHRYSTAL_APPEARANCE(ChrystalAppearance.class),
	TASK_EXECUTOR(TaskExecutor.class), 
	IS_GUI(GUI.class);

	public final Class<?> propertyClass;

	private PropertyType(Class<? extends Property> propertyClass) {
		this.propertyClass = propertyClass;
	}
}
