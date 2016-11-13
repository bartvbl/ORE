package orre.gl.vao;
import static org.lwjgl.opengl.GL11.*;

public enum DrawingMode {
	QUADS(GL_QUADS),
	TRIANGLES(GL_TRIANGLES), 
	LINES(GL_LINES);
	
	public final int glEnum;

	private DrawingMode(int glEnum) {
		this.glEnum = glEnum;
	}
}
