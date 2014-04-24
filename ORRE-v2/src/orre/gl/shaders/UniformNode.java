package orre.gl.shaders;

import static org.lwjgl.opengl.GL20.*;
import orre.sceneGraph.ContainerNode;

public class UniformNode extends ContainerNode {
	private final String variableName;
	private final int programID;

	private enum DataType {INT, FLOAT};
	private DataType currentDataType;
	
	private int intValue;
	private float floatValue;

	
	UniformNode(String variableName, int programID) {
		this.variableName = variableName;
		this.programID = programID;
	}
	
	public void setValue(int value) {
		this.intValue = value;
		this.currentDataType = DataType.INT;
	}
	
	public void setValue(float value) {
		this.floatValue = value;
		this.currentDataType = DataType.FLOAT;
	}
	
	@Override
	public void preRender() {
		int location = glGetUniformLocation(programID, variableName);
		switch(this.currentDataType) {
		case INT:
			glUniform1i(location, this.intValue);
			break;
		case FLOAT:
			glUniform1f(location, this.floatValue);
			break;
		}
	}
	
	@Override
	public void postRender() {
		
	}
}
