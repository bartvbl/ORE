package openrr.test;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.EXTFramebufferObject.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

/**
 * The vertex and fragment shaders are setup when the box object is
 * constructed. They are applied to the GL state prior to the box
 * being drawn, and released from that state after drawing.
 * @author Stephen Jones
 */
public class LightTestClass {

	/*
	 * if the shaders are setup ok we can use shaders, otherwise we just
	 * use default settings
	 */
	private boolean useShader;

	/*
	 * program shader, to which is attached a vertex and fragment shaders.
	 * They are set to 0 as a check because GL will assign unique int
	 * values to each
	 */
	private int program=0;

	private int draw;

	private int texture;

	private int framebufferID;

	private int colorTextureID;

	private int depthRenderBufferID;

	public LightTestClass(int displayListID){
		this.draw = displayListID;
		int vertShader = 0, fragShader = 0;
		createTexture(new byte[1024*1024], 1024, 1024);

		try {
			vertShader = createShader("res/shaders/ShadowMap.vert",ARBVertexShader.GL_VERTEX_SHADER_ARB);
			fragShader = createShader("res/shaders/ShadowMap.frag",ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
		}
		catch(Exception exc) {
			exc.printStackTrace();
			return;
		}
		finally {
			if(vertShader == 0 || fragShader == 0)
				return;
		}

		program = ARBShaderObjects.glCreateProgramObjectARB();

		if(program == 0)
			return;

		/*
		 * if the vertex and fragment shaders setup sucessfully,
		 * attach them to the shader program, link the sahder program
		 * (into the GL context I suppose), and validate
		 */
		ARBShaderObjects.glAttachObjectARB(program, vertShader);
		ARBShaderObjects.glAttachObjectARB(program, fragShader);

		ARBShaderObjects.glLinkProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
			System.err.println(getLogInfo(program));
			return;
		}

		ARBShaderObjects.glValidateProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
			System.err.println(getLogInfo(program));
			return;
		}

		useShader = true;
	}

	/*
	 * If the shader was setup succesfully, we use the shader. Otherwise
	 * we run normal drawing code.
	 */
	public void draw(){
		glViewport (0, 0, 1024, 1024);                                  

		glBindTexture(GL_TEXTURE_2D, 0);                                
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID);       

		glClearColor (1.0f, 0.0f, 0.0f, 0.5f);
		glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);            
		glLoadIdentity ();                   
		glCallList(this.draw);

		glEnable(GL_TEXTURE_2D);                                     
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);

		if(useShader){
			int textureLocation = ARBShaderObjects.glGetUniformLocationARB(program, "DepthMap");
			ARBShaderObjects.glUniform1iARB(textureLocation, depthRenderBufferID);
			ARBShaderObjects.glUseProgramObjectARB(program);
		}

		glCallList(this.draw);
		//release the shader
		if(useShader)
			ARBShaderObjects.glUseProgramObjectARB(0);

	}

	/*
	 * With the exception of syntax, setting up vertex and fragment shaders
	 * is the same.
	 * @param the name and path to the vertex shader
	 */
	private int createShader(String filename, int shaderType) throws Exception {
		int shader = 0;
		try {
			shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

			if(shader == 0)
				return 0;

			ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
			ARBShaderObjects.glCompileShaderARB(shader);

			if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
				throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
			System.out.println("created shader with ID: " + shader);
			return shader;
		}
		catch(Exception exc) {
			ARBShaderObjects.glDeleteObjectARB(shader);
			throw exc;
		}
	}

	private static String getLogInfo(int obj) {
		return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
	}

	private String readFileAsString(String filename) throws Exception {
		StringBuilder source = new StringBuilder();

		FileInputStream in = new FileInputStream(filename);

		Exception exception = null;

		BufferedReader reader;
		try{
			reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));

			Exception innerExc= null;
			try {
				String line;
				while((line = reader.readLine()) != null)
					source.append(line).append('\n');
			}
			catch(Exception exc) {
				exception = exc;
			}
			finally {
				try {
					reader.close();
				}
				catch(Exception exc) {
					if(innerExc == null)
						innerExc = exc;
					else
						exc.printStackTrace();
				}
			}

			if(innerExc != null)
				throw innerExc;
		}
		catch(Exception exc) {
			exception = exc;
		}
		finally {
			try {
				in.close();
			}
			catch(Exception exc) {
				if(exception == null)
					exception = exc;
				else
					exc.printStackTrace();
			}

			if(exception != null)
				throw exception;
		}

		return source.toString();
	}

	private void createTexture(byte[] imageData, int width, int height)
	{
		this.framebufferID = glGenFramebuffersEXT();                                         // create a new framebuffer
		this.colorTextureID = glGenTextures();                                               // and a new texture used as a color buffer
		this.depthRenderBufferID = glGenRenderbuffersEXT();                                  // And finally a new depthbuffer

		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID);                        // switch to the new framebuffer

		// initialize color texture
		glBindTexture(GL_TEXTURE_2D, colorTextureID);                                   // Bind the colorbuffer texture
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);               // make it linear filterd
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0,GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);  // Create the texture data
		glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT,GL_COLOR_ATTACHMENT0_EXT,GL_TEXTURE_2D, colorTextureID, 0); // attach it to the framebuffer


		// initialize depth renderbuffer
		glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthRenderBufferID);                // bind the depth renderbuffer
		glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH_COMPONENT24, width, height); // get the data space for it
		glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT,GL_DEPTH_ATTACHMENT_EXT,GL_RENDERBUFFER_EXT, depthRenderBufferID); // bind it to the renderbuffer

		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);    
	}
}