package orre.resources.incompleteResources;

import orre.gl.shaders.ShaderNode;
import orre.resources.IncompleteResourceObject;

public class IncompleteShader implements IncompleteResourceObject<IncompleteShader> {

	public final String name;
	public final String vertSource;
	public final String fragSource;

	public IncompleteShader(String name, String vertSource, String fragSource) {
		this.name = name;
		this.vertSource = vertSource;
		this.fragSource = fragSource;
	}
}
