package orre.gameWorld.core;

import openrr.world.properties.ChrystalAppearance;
import openrr.world.properties.Flashlight;
import openrr.world.properties.GravityProperty;
import openrr.world.properties.HealthProperty;
import openrr.world.properties.OreAppearance;
import openrr.world.properties.RockRaiderAppearance;
import openrr.world.properties.Transportable;
import orre.gameWorld.properties.TaskExecutor;
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
