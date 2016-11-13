package orre.rendering;

import java.nio.FloatBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

public class ShaderRenderState {

	private final RenderState renderState;
	private final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	private HashMap<ShaderProperty, Integer> integerProperties = new HashMap<ShaderProperty, Integer>();
	private HashMap<ShaderProperty, Float> floatProperties = new HashMap<ShaderProperty, Float>();
	private HashMap<ShaderProperty, float[]> vec4Properties = new HashMap<ShaderProperty, float[]>();
	private HashMap<ShaderProperty, Boolean> booleanProperties = new HashMap<ShaderProperty, Boolean>();

	public ShaderRenderState(RenderState renderState) {
		this.renderState = renderState;
		
		integerProperties.put(ShaderProperty.TEXTURE, 0);
	}

	public void setPropertyi(ShaderProperty property, int value) {
		integerProperties.put(property, value);
	}

	public void setPropertyf(ShaderProperty property, float value) {
		floatProperties.put(property, value);
	}

	public void setProperty4f(ShaderProperty property, float[] value) {
		vec4Properties.put(property, value);
	}

	public void setPropertyb(ShaderProperty property, boolean value) {
		booleanProperties.put(property, value);
	}

	public void commitUniformState() {
		Matrix4f transform = renderState.transformations.peekMatrix();
		
		Matrix4f projection = renderState.transformations.getProjectionMatrix();
		
		Matrix4f MVP = new Matrix4f();
		Matrix4f.mul(projection, transform, MVP);
		
		MVP.store(matrixBuffer);
		matrixBuffer.rewind();
		GL20.glUniformMatrix4(ShaderProperty.MVP_MATRIX.uniformID, false, matrixBuffer);

		Matrix4f normalTransform = new Matrix4f();
		Matrix4f.invert(transform, normalTransform);
		normalTransform.storeTranspose(matrixBuffer);
		matrixBuffer.rewind();
		GL20.glUniformMatrix4(ShaderProperty.MVP_NORMAL_MATRIX.uniformID, false, matrixBuffer);
		
		GL20.glUniform1i(ShaderProperty.TEXTURE.uniformID, integerProperties.get(ShaderProperty.TEXTURE));
	}

}
