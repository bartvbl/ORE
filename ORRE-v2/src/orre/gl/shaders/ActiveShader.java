package orre.gl.shaders;

import java.util.ArrayList;
import java.util.HashMap;

public class ActiveShader {
	private static final HashMap<ShaderNode, ArrayList<Uniform>> uniformMap = new HashMap<ShaderNode, ArrayList<Uniform>>();
	private static ShaderNode currentActiveShader;
	
	public static void setActiveShader(ShaderNode node) {
		if(!uniformMap.containsKey(node)) {
			uniformMap.put(node, new ArrayList<Uniform>());
		}
		currentActiveShader = node;
		for(Uniform uniform : uniformMap.get(node)) {
			uniform.set();
		}
	}
	
	public static void setUniformValue(String uniformName, int value) {
		Uniform uniform = getUniformByName(uniformName);
		uniform.setValue(value);
	}

	private static Uniform getUniformByName(String uniformName) {
		ArrayList<Uniform> uniforms = uniformMap.get(uniformName);
		if(uniforms == null) {
			uniforms = new ArrayList<Uniform>();
			uniformMap.put(currentActiveShader, uniforms);
		}
		Uniform uniform = getUniformByName(uniformName, uniforms);
		if(uniform == null) {
			uniform = currentActiveShader.createUniform(uniformName);
			uniforms.add(uniform);
		}
		return uniform;
	}
	
	private static Uniform getUniformByName(String uniformName, ArrayList<Uniform> uniforms) {
		for(Uniform uniform : uniforms) {
			if(uniform.variableName.equals(uniformName)) {
				return uniform;
			}
		}
		return null;
	}

	public static void setUniformValue(String uniformName, float value) {
		Uniform uniform = getUniformByName(uniformName);
		uniform.setValue(value);
	}
}
