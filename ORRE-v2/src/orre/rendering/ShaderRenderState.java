package orre.rendering;

import java.nio.FloatBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
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
		
		// arcane loop/switch method used so that new unsupported properties can be reported through a RuntimeException
		for(ShaderProperty property : ShaderProperty.values()) {
			switch(property) {
			case LIGHT_AMBIENT:			vec4Properties.put(ShaderProperty.LIGHT_AMBIENT, new float[]{1.0f, 1.0f, 1.0f, 1.0f}); break;
			case LIGHT_DIFFUSE:			vec4Properties.put(ShaderProperty.LIGHT_DIFFUSE, new float[]{1.0f, 1.0f, 1.0f, 1.0f}); break;
			case LIGHT_POSITION:		vec4Properties.put(ShaderProperty.LIGHT_POSITION, new float[]{0.0f, 0.0f, 0.0f, 1.0f}); break;
			case LIGHT_SPECULAR:		vec4Properties.put(ShaderProperty.LIGHT_SPECULAR, new float[]{1.0f, 1.0f, 1.0f, 1.0f}); break;
			
			case MATERIAL_AMBIENT:		vec4Properties.put(ShaderProperty.MATERIAL_AMBIENT, new float[]{1.0f, 1.0f, 1.0f, 1.0f}); break;
			case MATERIAL_DIFFUSE:		vec4Properties.put(ShaderProperty.MATERIAL_DIFFUSE, new float[]{1.0f, 1.0f, 1.0f, 1.0f}); break;
			case MATERIAL_EMISSION:		vec4Properties.put(ShaderProperty.MATERIAL_EMISSION, new float[]{1.0f, 1.0f, 1.0f, 1.0f}); break;
			case MATERIAL_SPECULAR:		vec4Properties.put(ShaderProperty.MATERIAL_SPECULAR, new float[]{1.0f, 1.0f, 1.0f, 1.0f}); break;
			case MATERIAL_SHININESS:	floatProperties.put(ShaderProperty.MATERIAL_SHININESS, 1.0f); break;
			
			case TEXTURE:				integerProperties.put(ShaderProperty.TEXTURE, 0); break;
			
			case TEXTURES_ENABLED:		booleanProperties.put(ShaderProperty.TEXTURES_ENABLED, false); break;
			
			case MVP_MATRIX: 			/* Do nothing -> Transformations are stored elsewhere */ break;
			case MVP_NORMAL_MATRIX: 	/* Do nothing -> Transformations are stored elsewhere */ break;
			
			default: 
				throw new RuntimeException("A ShaderProperty could not be initialised!");	
			}
		}
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
		Matrix4f model = renderState.transformations.peekMatrix();
		Matrix4f view = renderState.transformations.getViewMatrix();
		Matrix4f projection = renderState.transformations.getProjectionMatrix();
		
		Matrix4f MVP = new Matrix4f();
		Matrix4f.mul(projection, view, MVP);
		Matrix4f.mul(MVP, model, MVP);

		MVP.store(matrixBuffer);
		matrixBuffer.rewind();
		GL20.glUniformMatrix4(ShaderProperty.MVP_MATRIX.uniformID, false, matrixBuffer);

		matrixBuffer.rewind();
		Matrix4f normalTransform = new Matrix4f();
		Matrix4f.invert(MVP, normalTransform);
		normalTransform.storeTranspose(matrixBuffer);
		matrixBuffer.rewind();
		GL20.glUniformMatrix4(ShaderProperty.MVP_NORMAL_MATRIX.uniformID, false, matrixBuffer);
		

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, integerProperties.get(ShaderProperty.TEXTURE));
		//GL20.glUniform1i(ShaderProperty.TEXTURE.uniformID, integerProperties.get(ShaderProperty.TEXTURE));
		
		float[] lightPosition = vec4Properties.get(ShaderProperty.LIGHT_POSITION);
		GL20.glUniform4f(ShaderProperty.LIGHT_POSITION.uniformID, lightPosition[0], lightPosition[1], lightPosition[2], lightPosition[3]);
		float[] lightAmbient = vec4Properties.get(ShaderProperty.LIGHT_AMBIENT);
		GL20.glUniform4f(ShaderProperty.LIGHT_AMBIENT.uniformID, lightAmbient[0], lightAmbient[1], lightAmbient[2], lightAmbient[3]);
		float[] lightDiffuse = vec4Properties.get(ShaderProperty.LIGHT_DIFFUSE);
		GL20.glUniform4f(ShaderProperty.LIGHT_DIFFUSE.uniformID, lightDiffuse[0], lightDiffuse[1], lightDiffuse[2], lightDiffuse[3]);
		float[] lightSpecular = vec4Properties.get(ShaderProperty.LIGHT_SPECULAR);
		GL20.glUniform4f(ShaderProperty.LIGHT_SPECULAR.uniformID, lightSpecular[0], lightSpecular[1], lightSpecular[2], lightSpecular[3]);
		
		float[] materialAmbient = vec4Properties.get(ShaderProperty.MATERIAL_AMBIENT);
		GL20.glUniform4f(ShaderProperty.MATERIAL_AMBIENT.uniformID, materialAmbient[0], materialAmbient[1], materialAmbient[2], materialAmbient[3]);
		float[] materialDiffuse = vec4Properties.get(ShaderProperty.MATERIAL_DIFFUSE);
		GL20.glUniform4f(ShaderProperty.MATERIAL_DIFFUSE.uniformID, materialDiffuse[0], materialDiffuse[1], materialDiffuse[2], materialDiffuse[3]);
		float[] materialSpecular = vec4Properties.get(ShaderProperty.MATERIAL_SPECULAR);
		GL20.glUniform4f(ShaderProperty.MATERIAL_SPECULAR.uniformID, materialSpecular[0], materialSpecular[1], materialSpecular[2], materialSpecular[3]);
		float[] materialEmission = vec4Properties.get(ShaderProperty.MATERIAL_EMISSION);
		GL20.glUniform4f(ShaderProperty.MATERIAL_EMISSION.uniformID, materialEmission[0], materialEmission[1], materialEmission[2], materialEmission[3]);
		float materialShininess = floatProperties.get(ShaderProperty.MATERIAL_SHININESS);
		GL20.glUniform1f(ShaderProperty.MATERIAL_SHININESS.uniformID, materialShininess);
	}

}
