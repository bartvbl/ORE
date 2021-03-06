package orre.gl.renderer;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL13.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

public class ShaderRenderState {

	private final RenderState renderState;
	private final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	private HashMap<ShaderProperty, Integer> integerProperties = new HashMap<ShaderProperty, Integer>();
	private HashMap<ShaderProperty, Float> floatProperties = new HashMap<ShaderProperty, Float>();
	private HashMap<ShaderProperty, float[]> vec4Properties = new HashMap<ShaderProperty, float[]>();
	private HashMap<ShaderProperty, Boolean> booleanProperties = new HashMap<ShaderProperty, Boolean>();
	private int activeProgramID = 0;
	private Matrix4f lightMatrix = new Matrix4f();

	public ShaderRenderState(RenderState renderState) {
		this.renderState = renderState;
		
		// arcane loop/switch method used so that new unsupported properties can be reported through a RuntimeException
		for(ShaderProperty property : ShaderProperty.values()) {
			switch(property) {
			case LIGHT_AMBIENT:			vec4Properties.put(ShaderProperty.LIGHT_AMBIENT, new float[]{1.0f, 1.0f, 1.0f, 1.0f}); break;
			case LIGHT_DIFFUSE:			vec4Properties.put(ShaderProperty.LIGHT_DIFFUSE, new float[]{1.0f, 1.0f, 1.0f, 1.0f}); break;
			case LIGHT_SPECULAR:		vec4Properties.put(ShaderProperty.LIGHT_SPECULAR, new float[]{1.0f, 1.0f, 1.0f, 1.0f}); break;
			case LIGHT_POSITION:		vec4Properties.put(ShaderProperty.LIGHT_POSITION, new float[]{0.0f, 0.0f, -1.0f, 1.0f}); break;
			case LIGHT_SPECULAR_STRENGTH: floatProperties.put(ShaderProperty.LIGHT_SPECULAR_STRENGTH, 8f); break;
			
			case ATTENUATION_CONSTANT: 	floatProperties.put(ShaderProperty.ATTENUATION_CONSTANT, 1f); break;
			case ATTENUATION_LINEAR:	floatProperties.put(ShaderProperty.ATTENUATION_LINEAR, 0.09f); break;
			case ATTENUATION_QUADRATIC: floatProperties.put(ShaderProperty.ATTENUATION_QUADRATIC, 0.032f); break;
			
			case MATERIAL_AMBIENT:		vec4Properties.put(ShaderProperty.MATERIAL_AMBIENT, new float[]{1.0f, 1.0f, 1.0f, 1.0f}); break;
			case MATERIAL_DIFFUSE:		vec4Properties.put(ShaderProperty.MATERIAL_DIFFUSE, new float[]{1.0f, 1.0f, 1.0f, 1.0f}); break;
			case MATERIAL_EMISSION:		vec4Properties.put(ShaderProperty.MATERIAL_EMISSION, new float[]{1.0f, 1.0f, 1.0f, 1.0f}); break;
			case MATERIAL_SPECULAR:		vec4Properties.put(ShaderProperty.MATERIAL_SPECULAR, new float[]{1.0f, 1.0f, 1.0f, 1.0f}); break;
			case MATERIAL_SHININESS:	floatProperties.put(ShaderProperty.MATERIAL_SHININESS, 32.0f); break;
			
			case TEXTURE0:				integerProperties.put(ShaderProperty.TEXTURE0, 0); break;
			case TEXTURE1:				integerProperties.put(ShaderProperty.TEXTURE1, 0); break;
			
			case CAMERA_POSITION:		/* Do nothing -> Calculated elsewhere */ break;
			
			case TEXTURES_ENABLED:		booleanProperties.put(ShaderProperty.TEXTURES_ENABLED, false); break;
			
			case MV_MATRIX: break;
			case LIGHT_MVP: break;
			case MVP_MATRIX: 			/* Do nothing -> Transformations are stored elsewhere */ break;
			case MV_NORMAL_MATRIX: 		/* Do nothing -> Transformations are stored elsewhere */ break;
			
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
		glUniformMatrix4(ShaderProperty.MVP_MATRIX.uniformID, false, matrixBuffer);
		
		model.store(matrixBuffer);
		matrixBuffer.rewind();
		glUniformMatrix4(ShaderProperty.MV_MATRIX.uniformID, false, matrixBuffer);
		
		Matrix4f lightMVP = new Matrix4f();
		Matrix4f.mul(lightMatrix, model, lightMVP);
		lightMVP.store(matrixBuffer);
		matrixBuffer.rewind();
		glUniformMatrix4(ShaderProperty.LIGHT_MVP.uniformID, false, matrixBuffer);

		matrixBuffer.rewind();
		Matrix4f normalTransform = new Matrix4f();
		Matrix4f.invert(model, normalTransform);
		Matrix4f.transpose(normalTransform, normalTransform);
		normalTransform.store(matrixBuffer);
		matrixBuffer.rewind();
		glUniformMatrix4(ShaderProperty.MV_NORMAL_MATRIX.uniformID, false, matrixBuffer);
		
		glUniform1f(ShaderProperty.TEXTURES_ENABLED.uniformID, booleanProperties.get(ShaderProperty.TEXTURES_ENABLED) ? 1.0f : 0.0f);
		
		
		
		Vector4f cameraPosition = new Vector4f(0, 0, 0, 1);
		Matrix4f transformedView = new Matrix4f();
		Matrix4f.invert(view, transformedView);
		Matrix4f.transform(transformedView, cameraPosition, cameraPosition);
		glUniform4f(ShaderProperty.CAMERA_POSITION.uniformID, cameraPosition.x, cameraPosition.y, cameraPosition.z, cameraPosition.w);
		
		
		float[] lightPosition = vec4Properties.get(ShaderProperty.LIGHT_POSITION);
		glUniform4f(ShaderProperty.LIGHT_POSITION.uniformID, lightPosition[0], lightPosition[1], lightPosition[2], lightPosition[3]);
		float[] lightAmbient = vec4Properties.get(ShaderProperty.LIGHT_AMBIENT);
		glUniform4f(ShaderProperty.LIGHT_AMBIENT.uniformID, lightAmbient[0], lightAmbient[1], lightAmbient[2], lightAmbient[3]);
		float[] lightDiffuse = vec4Properties.get(ShaderProperty.LIGHT_DIFFUSE);
		glUniform4f(ShaderProperty.LIGHT_DIFFUSE.uniformID, lightDiffuse[0], lightDiffuse[1], lightDiffuse[2], lightDiffuse[3]);
		float[] lightSpecular = vec4Properties.get(ShaderProperty.LIGHT_SPECULAR);
		glUniform4f(ShaderProperty.LIGHT_SPECULAR.uniformID, lightSpecular[0], lightSpecular[1], lightSpecular[2], lightSpecular[3]);
		float specularStrength = floatProperties.get(ShaderProperty.LIGHT_SPECULAR_STRENGTH);
		glUniform1f(ShaderProperty.LIGHT_SPECULAR_STRENGTH.uniformID, specularStrength);
		
		float attenuationConstant = floatProperties.get(ShaderProperty.ATTENUATION_CONSTANT);
		glUniform1f(ShaderProperty.ATTENUATION_CONSTANT.uniformID, attenuationConstant);
		float attenuationLinear = floatProperties.get(ShaderProperty.ATTENUATION_LINEAR);
		glUniform1f(ShaderProperty.ATTENUATION_LINEAR.uniformID, attenuationLinear);
		float attenuationQuadratic = floatProperties.get(ShaderProperty.ATTENUATION_QUADRATIC);
		glUniform1f(ShaderProperty.ATTENUATION_QUADRATIC.uniformID, attenuationQuadratic);
		
		float[] materialAmbient = vec4Properties.get(ShaderProperty.MATERIAL_AMBIENT);
		glUniform4f(ShaderProperty.MATERIAL_AMBIENT.uniformID, materialAmbient[0], materialAmbient[1], materialAmbient[2], materialAmbient[3]);
		float[] materialDiffuse = vec4Properties.get(ShaderProperty.MATERIAL_DIFFUSE);
		glUniform4f(ShaderProperty.MATERIAL_DIFFUSE.uniformID, materialDiffuse[0], materialDiffuse[1], materialDiffuse[2], materialDiffuse[3]);
		float[] materialSpecular = vec4Properties.get(ShaderProperty.MATERIAL_SPECULAR);
		glUniform4f(ShaderProperty.MATERIAL_SPECULAR.uniformID, materialSpecular[0], materialSpecular[1], materialSpecular[2], materialSpecular[3]);
		float[] materialEmission = vec4Properties.get(ShaderProperty.MATERIAL_EMISSION);
		glUniform4f(ShaderProperty.MATERIAL_EMISSION.uniformID, materialEmission[0], materialEmission[1], materialEmission[2], materialEmission[3]);
		float materialShininess = floatProperties.get(ShaderProperty.MATERIAL_SHININESS);
		glUniform1f(ShaderProperty.MATERIAL_SHININESS.uniformID, materialShininess);
		
		glActiveTexture(GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, integerProperties.get(ShaderProperty.TEXTURE0));
		glUniform1i(ShaderProperty.TEXTURE0.uniformID, 0);
		glActiveTexture(GL_TEXTURE1);
		int texture1ID = integerProperties.get(ShaderProperty.TEXTURE1);
		if(texture1ID != 0) { // only activate if a texture is set
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture1ID);
			glUniform1i(ShaderProperty.TEXTURE1.uniformID, 1);			
		}
		glActiveTexture(GL_TEXTURE0);
		
	}

	public float[] getProperty4f(ShaderProperty property) {
		return vec4Properties.get(property);
	}

	public void setActiveProgram(int programID) {
		this.activeProgramID  = programID;
	}

	public void setLightMatrix(Matrix4f shadowVP) {
		this.lightMatrix  = shadowVP;
	}
	

}
