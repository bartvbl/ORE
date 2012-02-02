package openrr.test;

import java.util.ArrayList;

public class ButtonEvent {
	
	private String type;
	private String target;
	private String targetType;
	
	private ArrayList<ButtonEventCondition> conditions = new ArrayList<ButtonEventCondition>();
	
	public ButtonEvent(String inType, String inTarget) {
		type = inType;
		target = inTarget;
	}
	
	public String getType() {
		return type;
	}
	
	public String getTarget() {
		return target;
	}

}
