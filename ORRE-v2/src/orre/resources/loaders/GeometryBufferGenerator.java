package orre.resources.loaders;

import java.util.ArrayList;

import orre.geom.vbo.BufferDataFormatType;
import orre.resources.loaders.obj.VBOUtils;

public class GeometryBufferGenerator {
	private ArrayList<float[]> vertices = new ArrayList<float[]>();
	private ArrayList<Integer> indices = new ArrayList<Integer>();

	private boolean isPacked = false;
	
	private ArrayList<Integer> indexBuffers;
	private ArrayList<Integer> vertexBuffers;
	private BufferDataFormatType dataFormat;
	
	public GeometryBufferGenerator(BufferDataFormatType dataFormat)
	{
		this.vertexBuffers.add(VBOUtils.createBuffer());
		this.indexBuffers.add(VBOUtils.createBuffer());
		this.dataFormat = dataFormat;
	}
	public void addVertex(float x, float y, float z, float texX, float texY, float normalX, float normalY, float normalZ)
	{
		this.vertices.add(new float[] {x, y, z, normalX, normalY, normalZ, texX, texY});
	}
	public void addIndex(int index)
	{
		this.indices.add(index);
	}
	public void pack()
	{	
		if(this.isPacked)
		{
			System.out.println("ERROR: tried to repack a BufferedVertexData instance");
			return;
		}
		this.isPacked = true;
		DataBufferGenerator generator = new DataBufferGenerator();
		generator.storeDataInVBOs(vertices, indices, this.dataFormat);
		generator.getGeometryBuffer();
	}
}
