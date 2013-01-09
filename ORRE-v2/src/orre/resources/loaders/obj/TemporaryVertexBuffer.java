package orre.resources.loaders.obj;

import java.util.Arrays;

import orre.geom.vbo.BufferDataFormatType;

public class TemporaryVertexBuffer {
	private double[] vertices;
	private double[] textureCoordinates;
	private double[] normals;
	
	private int vertexBufferPosition = 0;
	private int textureCoordBufferPosition = 0;
	private int normalBufferPosition = 0;
	
	private double[] vertex; //array used for putting together vertices from the different data buffers. It is reused to avoid a massive spam of temporary objects.
	
	private int ELEMENTS_PER_VERTEX = 3;
	private int ELEMENTS_PER_TEXCOORD = 2;
	private int ELEMENTS_PER_NORMAL = 3;

	private BufferDataFormatType dataFormat;
	
	public TemporaryVertexBuffer(int numVertices, int numTexCoords, int numNormals, BufferDataFormatType dataFormat)
	{
		this.dataFormat = dataFormat;
		this.vertices = new double[ELEMENTS_PER_VERTEX * numVertices];
		this.textureCoordinates = new double[ELEMENTS_PER_TEXCOORD * numTexCoords];
		this.normals = new double[ELEMENTS_PER_NORMAL * numNormals];
		this.vertex = new double[dataFormat.elementsPerVertex];
	}
	
	public void addVertex(double vertex1, double vertex2, double vertex3)
	{
		this.vertices[vertexBufferPosition] = vertex1;
		this.vertices[vertexBufferPosition + 1] = vertex2;
		this.vertices[vertexBufferPosition + 2] = vertex3;
		this.vertexBufferPosition += ELEMENTS_PER_VERTEX;
	}
	public void addTextureCoordinate(double x, double y) 
	{
		this.textureCoordinates[textureCoordBufferPosition] = x;
		this.textureCoordinates[textureCoordBufferPosition + 1] = y;
		this.textureCoordBufferPosition += ELEMENTS_PER_TEXCOORD;
	}
	public void addNormal(double x, double y, double z) 
	{
		this.normals[normalBufferPosition] = x;
		this.normals[normalBufferPosition + 1] = y;
		this.normals[normalBufferPosition + 2] = z;
		this.normalBufferPosition += ELEMENTS_PER_NORMAL;
	}
	
	public double[] getVertex(int vertexIndex, int textureIndex, int normalIndex)
	{
		copyVertexIntoVertexArray(0, ELEMENTS_PER_VERTEX*vertexIndex);//the temporary texture array is formatted such that the vertex always comes first no matter the texture/normal format of the OBJ file
		switch(this.dataFormat) {
		case VERTICES_AND_NORMALS:
			copyNormalIntoVertexArray(ELEMENTS_PER_VERTEX, ELEMENTS_PER_NORMAL*normalIndex);
			break;
		case VERTICES_AND_TEXTURES:
			copyTextureCoordsIntoVertexArray(ELEMENTS_PER_VERTEX, ELEMENTS_PER_TEXCOORD*textureIndex);
			break;
		case VERTICES_TEXTURES_NORMALS:
			copyTextureCoordsIntoVertexArray(ELEMENTS_PER_VERTEX, ELEMENTS_PER_TEXCOORD*textureIndex);
			copyNormalIntoVertexArray(ELEMENTS_PER_VERTEX + ELEMENTS_PER_TEXCOORD, ELEMENTS_PER_NORMAL*normalIndex);
			break;
		}
		return vertex;
	}
	
	private void copyVertexIntoVertexArray(int tmpVertexStartIndex, int vertexStartIndex) {
		for(int i = 0; i < ELEMENTS_PER_VERTEX; i++) {
			vertex[tmpVertexStartIndex + i] = this.vertices[vertexStartIndex + i];
		}
	}
	
	private void copyNormalIntoVertexArray(int vertexStartIndex, int normalStartIndex) {
		for(int i = 0; i < ELEMENTS_PER_NORMAL; i++) {
			vertex[vertexStartIndex + i] = this.normals[normalStartIndex + i];
		}
	}
	
	private void copyTextureCoordsIntoVertexArray(int vertexStartIndex, int textureCoordStartIndex) {
		for(int i = 0; i < ELEMENTS_PER_TEXCOORD; i++) {
			vertex[vertexStartIndex + i] = this.textureCoordinates[textureCoordStartIndex + i];
		}
	}

	public void destroy() {
		this.vertices = this.normals = this.textureCoordinates = this.vertex = null;
	}
}
