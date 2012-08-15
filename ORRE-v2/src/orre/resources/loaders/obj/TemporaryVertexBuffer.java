package orre.resources.loaders.obj;

import orre.geom.vbo.BufferDataFormatType;

public class TemporaryVertexBuffer {
	private float[] vertices;
	private float[] textureCoordinates;
	private float[] normals;
	
	private int vertexBufferPosition = 0;
	private int textureCoordBufferPosition = 0;
	private int normalBufferPosition = 0;
	
	private float[] vertex; //array used for putting together vertices from the different data buffers. It is reused to avoid a massive spam of temporary objects.
	
	private int ELEMENTS_PER_VERTEX = 3;
	private int ELEMENTS_PER_TEXCOORD = 2;
	private int ELEMENTS_PER_NORMAL = 3;

	private BufferDataFormatType dataFormat;
	
	public TemporaryVertexBuffer(int numVertices, int numTexCoords, int numNormals)
	{
		this.setBufferDataFormat(BufferDataFormatType.VERTICES_TEXTURES_NORMALS);
	}
	
	public void addVertex(float x, float y, float z)
	{
		this.vertices[vertexBufferPosition] = x;
		this.vertices[vertexBufferPosition + 1] = y;
		this.vertices[vertexBufferPosition + 2] = z;
		this.vertexBufferPosition += ELEMENTS_PER_VERTEX;
	}
	public void addTextureCoordinate(float x, float y) 
	{
		this.textureCoordinates[textureCoordBufferPosition] = x;
		this.textureCoordinates[textureCoordBufferPosition + 1] = y;
		this.textureCoordBufferPosition += ELEMENTS_PER_TEXCOORD;
	}
	public void addNormal(float x, float y, float z) 
	{
		this.normals[normalBufferPosition] = x;
		this.normals[normalBufferPosition + 1] = y;
		this.normals[normalBufferPosition + 2] = z;
		this.normalBufferPosition += ELEMENTS_PER_NORMAL;
	}
	
	public float[] getVertex(int vertexIndex, int textureIndex, int normalIndex)
	{
		copyVertexIntoVertexArray(0, vertexIndex);//the temporary texture array is formatted such that the vertex always comes first no matter the texture/normal format of the OBJ file

		switch(this.dataFormat) {
		case VERTICES_AND_NORMALS:
			copyNormalIntoVertexArray(ELEMENTS_PER_VERTEX, normalIndex);
			break;
		case VERTICES_AND_TEXTURES:
			copyTextureCoordsIntoVertexArray(ELEMENTS_PER_VERTEX, textureIndex);
			break;
		case VERTICES_TEXTURES_NORMALS:
			copyTextureCoordsIntoVertexArray(ELEMENTS_PER_VERTEX, textureIndex);
			copyNormalIntoVertexArray(ELEMENTS_PER_VERTEX + ELEMENTS_PER_TEXCOORD, normalIndex);
			break;
		}
		return vertex;
	}
	
	private void copyVertexIntoVertexArray(int tmpVertexStartIndex, int vertexStartIndex) {
		for(int i = 0; i < ELEMENTS_PER_VERTEX; i++) {
			vertex[tmpVertexStartIndex + i] = this.normals[vertexStartIndex + i];
		}
	}
	
	private void copyNormalIntoVertexArray(int vertexStartIndex, int normalStartIndex) {
		for(int i = 0; i < ELEMENTS_PER_NORMAL; i++) {
			vertex[vertexStartIndex + i] = this.normals[normalStartIndex + i];
		}
	}
	
	private void copyTextureCoordsIntoVertexArray(int vertexStartIndex, int textureCoordStartIndex) {
		for(int i = 0; i < ELEMENTS_PER_TEXCOORD; i++) {
			vertex[vertexStartIndex + i] = this.normals[textureCoordStartIndex + i];
		}
	}
	
	public void setBufferDataFormat(BufferDataFormatType dataFormat)
	{
		this.dataFormat = dataFormat;
		this.vertex = new float[dataFormat.elementsPerVertex];
	}
}
