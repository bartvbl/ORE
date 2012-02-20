package orre.resources.loaders.obj;

import java.util.ArrayList;
import java.util.Arrays;

import orre.geom.vbo.BufferDataFormatType;

public class GeometryBufferGenerator {
	private ArrayList<float[]> vertices = new ArrayList<float[]>();
	private ArrayList<float[]> textureCoordinates = new ArrayList<float[]>();
	private ArrayList<float[]> normals = new ArrayList<float[]>();

	private BufferDataFormatType dataFormat;
	
	public GeometryBufferGenerator()
	{
		this.dataFormat = BufferDataFormatType.VERTICES_TEXTURES_NORMALS;
	}
	
	public void setBufferDataFormat(BufferDataFormatType dataFormat)
	{
		this.dataFormat = dataFormat;
	}
	public void addVertex(float x, float y, float z)
	{
		this.vertices.add(new float[] {x, y, z});
	}
	public void addTextureCoordinate(float x, float y) 
	{
		this.textureCoordinates.add(new float[]{x, y});
	}
	public void addNormal(float x, float y, float z) 
	{
		this.normals.add(new float[] {x, y, z});
	}
	
	public float[] getVertex(int vertexIndex, int textureIndex, int normalIndex)
	{
		float[] vertex = new float[this.dataFormat.elementSize]; //allocate a vertex that will be able to hold all data
		this.insertVertex(vertex, vertexIndex);

		switch(this.dataFormat) {
		case VERTICES_AND_NORMALS:
			this.insertNormalAfterVertexCoord(vertex, normalIndex);
		case VERTICES_AND_TEXTURES:
			this.insertTextureCoordinate(vertex, textureIndex);
		case VERTICES_TEXTURES_NORMALS:
			this.insertTextureCoordinate(vertex, textureIndex);
			this.insertNormalAfterTextureCoord(vertex, normalIndex);
		}
		return vertex;
	}
	private void insertVertex(float[] vertex, int vertexIndex) {
		float[] vert = this.vertices.get(vertexIndex-1); //-1 as OBJ files are 1-indexed
		vertex[0] = vert[0];
		vertex[1] = vert[1];
		vertex[2] = vert[2];
	}
	private void insertTextureCoordinate(float[] vertex, int textureIndex) {
		float[] texCoord = this.textureCoordinates.get(textureIndex-1);
		vertex[3] = texCoord[0];
		vertex[4] = texCoord[1];
	}
	private void insertNormalAfterVertexCoord(float[] vertex, int normalIndex) {
		float[] vert = this.normals.get(normalIndex-1);
		vertex[3] = vert[0];
		vertex[4] = vert[1];
		vertex[5] = vert[2];
	}
	private void insertNormalAfterTextureCoord(float[] vertex, int normalIndex) {
		float[] vert = this.normals.get(normalIndex-1);
		vertex[5] = vert[0];
		vertex[6] = vert[1];
		vertex[7] = vert[2];
	}
}
