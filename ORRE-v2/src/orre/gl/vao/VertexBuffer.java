package orre.gl.vao;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

public class VertexBuffer {
	private final FloatBuffer vertices;
	private final FloatBuffer textureCoordinates;
	private final FloatBuffer normals;
	
	private static final int ELEMENTS_PER_VERTEX = 3;
	private static final int ELEMENTS_PER_TEXCOORD = 2;
	private static final int ELEMENTS_PER_NORMAL = 3;

	private final VBOFormat dataFormat;
	
	private final FloatBuffer vertex;
	
	private final float[] vertexCoordinate = new float[ELEMENTS_PER_VERTEX];
	private final float[] normalCoordinate = new float[ELEMENTS_PER_NORMAL];
	private final float[] texCoordinate = new float[ELEMENTS_PER_TEXCOORD];
	
	public VertexBuffer(int numVertices, int numTexCoords, int numNormals, VBOFormat dataFormat)
	{
		this.dataFormat = dataFormat;
		this.vertices = FloatBuffer.allocate(ELEMENTS_PER_VERTEX * numVertices);
		this.textureCoordinates = FloatBuffer.allocate(ELEMENTS_PER_TEXCOORD * numTexCoords);
		this.normals = FloatBuffer.allocate(ELEMENTS_PER_NORMAL * numNormals);
		this.vertex = FloatBuffer.allocate(dataFormat.elementsPerVertex);
	}
	
	public void addVertex(float x, float y, float z)
	{
		this.vertices.put(x).put(y).put(z);
	}
	public void addTextureCoordinate(float u, float v) 
	{
		this.textureCoordinates.put(u).put(v);
	}
	public void addNormal(float x, float y, float z) 
	{
		this.normals.put(x).put(y).put(z);
	}
	
	public FloatBuffer getVertex(int vertexIndex, int textureIndex, int normalIndex)
	{
		vertex.rewind();
		copyVertexIntoVertexArray(ELEMENTS_PER_VERTEX*vertexIndex);//the temporary texture array is formatted such that the vertex always comes first no matter the texture/normal format of the OBJ file
		switch(this.dataFormat) {
		case VERTICES_AND_NORMALS:
			copyNormalIntoVertexArray(ELEMENTS_PER_NORMAL*normalIndex);
			break;
		case VERTICES_AND_TEXTURES:
			copyTextureCoordsIntoVertexArray(ELEMENTS_PER_TEXCOORD*textureIndex);
			break;
		case VERTICES_TEXTURES_NORMALS:
			copyTextureCoordsIntoVertexArray(ELEMENTS_PER_TEXCOORD*textureIndex);
			copyNormalIntoVertexArray(ELEMENTS_PER_NORMAL*normalIndex);
			break;
		case VERTICES:
			break;
		}
		vertex.rewind();
		return vertex;
	}
	
	private void copyVertexIntoVertexArray(int vertexStartIndex) {
		int position = vertices.position();
		vertices.position(vertexStartIndex);
		vertices.get(vertexCoordinate, 0, ELEMENTS_PER_VERTEX);
		vertices.position(position);
		vertex.put(vertexCoordinate);
	}
	
	private void copyNormalIntoVertexArray(int normalStartIndex) {
		int position = normals.position();
		normals.position(normalStartIndex);
		normals.get(normalCoordinate, 0, ELEMENTS_PER_NORMAL);
		normals.position(position);
		vertex.put(normalCoordinate);
	}
	
	private void copyTextureCoordsIntoVertexArray(int textureCoordStartIndex) {
		int position = textureCoordinates.position();
		textureCoordinates.position(textureCoordStartIndex);
		textureCoordinates.get(texCoordinate, 0, ELEMENTS_PER_TEXCOORD);
		textureCoordinates.position(position);
		vertex.put(texCoordinate);
	}
}
