package orre.input.devices;

import java.util.Arrays;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

import orre.input.CommandDispatcher;
import orre.input.InputEvent;
import orre.input.KeyType;

public class ControllerDevice {
	//Note that this class assumes a 360 pad.

	private final boolean isEnabled;
	
	private final Controller controller;
	private final CommandDispatcher dispatcher;
	
	private final double[] directionStates;
	private final boolean[] buttonStates;
	private final double[] axisStates;
	
	private double leftStickValue = 0;
	private double rightStickValue = 0;

	public ControllerDevice(CommandDispatcher dispatcher) {
		this.dispatcher = dispatcher;
		
		if(Controllers.getControllerCount() > 0) {
			this.isEnabled = true;
			this.controller = Controllers.getController(0);
			buttonStates = new boolean[controller.getButtonCount()];
			this.axisStates = new double[controller.getAxisCount()];
			for(int i = 0; i < controller.getAxisCount(); i++) {
				controller.setDeadZone(i, 0.15f);
			}
			this.directionStates = new double[2];
		} else {
			this.isEnabled = false;
			this.controller = null;
			this.axisStates = null;
			this.buttonStates = null;
			this.directionStates = null;
		}
	}

	public void update() {
		if(!isEnabled) {
			return;
		}
		upateButtons();
		updateAxis();
		
		final double directionKeysX = controller.getPovX();
		final double directionKeysY = -1.0 * controller.getPovY();
		
		if(directionKeysX != 0) {
			dispatcher.dispatchCommand(KeyType.JOY_DIRECTIONAL_RIGHT, directionKeysX, directionKeysX - directionStates[0]);
		}
		if(directionKeysY != 0) {
			dispatcher.dispatchCommand(KeyType.JOY_DIRECTIONAL_UP, directionKeysY, directionKeysY - directionStates[1]);
		}
		
		directionStates[0] = directionKeysX;
		directionStates[1] = directionKeysY;
		
	}

	private void updateAxis() {
		for(int i = 0; i < controller.getAxisCount(); i++) {
			double axisValue = controller.getAxisValue(i);
			if(axisValue != 0) {
				KeyType axisType = axisTypeByIndex(i);
				if(axisType != null) {
					dispatcher.dispatchCommand(axisType, axisValue, axisValue - axisStates[i]);
				}
			}
			axisStates[i] = axisValue;
		}
	}

	private KeyType axisTypeByIndex(int axisID) {
		switch(axisID) {
		case 0:
			return KeyType.JOY_LEFT_STICK_Y_AXIS;
		case 1:
			return KeyType.JOY_LEFT_STICK_X_AXIS;
		case 2:
			return KeyType.JOY_RIGHT_STICK_X_AXIS;
		case 3:
			return KeyType.JOY_RIGHT_STICK_Y_AXIS;
		case 4:
			return KeyType.JOY_LTRIGGER;
		case 5:
			return KeyType.JOY_RTRIGGER;
		}
		return null;
	}

	private void upateButtons() {
		for(int i = 0; i < buttonStates.length; i++) {
			boolean newButtonState = controller.isButtonPressed(i);
			if(buttonStates[i] != newButtonState) {
				KeyType type = getKeyType(i);
				if(type != null) {
					double buttonDelta = newButtonState ? 1.0 : -1.0;
					double buttonState = newButtonState ? 1.0 : 0.0;
					dispatcher.dispatchCommand(type, buttonState, buttonDelta);
				}
				buttonStates[i] = newButtonState;
			}
		}
	}

	private KeyType getKeyType(int buttonID) {
		switch(buttonID) {
		case 0:
			return KeyType.JOY_A;
		case 1:
			return KeyType.JOY_B;
		case 2:
			return KeyType.JOY_X;
		case 3:
			return KeyType.JOY_Y;
		case 4:
			return KeyType.JOY_LSHOULDER;
		case 5:
			return KeyType.JOY_RSHOULDER;
		case 6:
			return KeyType.JOY_BACK;
		case 7:
			return KeyType.JOY_START;
		case 8:
			return KeyType.JOY_LEFT_STICK_BUTTON;
		case 9:
			return KeyType.JOY_RIGHT_STICK_BUTTON;
		}
		return null;
	}

}
