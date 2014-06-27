package orre.gl.shaders;

import static org.lwjgl.opengl.GL20.*;
import orre.sceneGraph.ContainerNode;

public class Uniform {
	public final String variableName;
	private final int programID;

	private enum DataType {INT, FLOAT};
	private DataType currentDataType;
	
	private int intValue;
	private float floatValue;
	private int uniformLocation = -1;
	
	Uniform(String variableName, int programID) {
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
	
	public void set() {
		if(uniformLocation == -1) {
			uniformLocation = glGetUniformLocation(programID, variableName);
		}
		switch(this.currentDataType) {
		case INT:
			glUniform1i(uniformLocation, this.intValue);
			break;
		case FLOAT:
			glUniform1f(uniformLocation, this.floatValue);
			break;
		}
	}
}
