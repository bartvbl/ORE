package orre.gl.renderer;

import orre.gl.shaders.ActiveShader;

public class RenderContext {
	public static void setTexturesEnabled(boolean enabled) {
		if(enabled) {
			ActiveShader.setUniformValue1f("texturesEnabled", 1.0f);
		} else {
			ActiveShader.setUniformValue1f("texturesEnabled", 0.0f);
		}
	}
}
