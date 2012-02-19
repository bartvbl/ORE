package orre.resources.loaders.obj;

import java.util.ArrayList;

import orre.geom.vbo.BufferDataFormatType;

public class GeometryBufferGenerator {
	private ArrayList<float[]> vertices = new ArrayList<float[]>();
	private ArrayList<float[]> textureCoordinates = new ArrayList<float[]>();
	private ArrayList<float[]> normals = new ArrayList<float[]>();
	private ArrayList<int[]> indices = new ArrayList<int[]>();

	private ArrayList<Integer> indexBuffers;
	private ArrayList<Integer> vertexBuffers;
	private BufferDataFormatType dataFormat;
	
	public GeometryBufferGenerator(BufferDataFormatType dataFormat)
	{
		this.vertexBuffers.add(VBOUtils.createBuffer());
		this.indexBuffers.add(VBOUtils.createBuffer());
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
	public void addFace(int vertexIndex, int textureIndex, int normalIndex)
	{
		this.indices.add(new int[]{vertexIndex, textureIndex, normalIndex});
	}
	public void pack()
	{	
		DataBufferGenerator generator = new DataBufferGenerator();
		generator.storeDataInVBOs(vertices, indices, this.dataFormat);
		generator.getGeometryBuffer();
	}
}
