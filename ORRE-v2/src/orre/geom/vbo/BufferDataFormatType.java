package orre.geom.vbo;

public enum BufferDataFormatType {
	VERTICES (3),
	VERTICES_AND_TEXTURES (5),
	VERTICES_AND_NORMALS (6),
	VERTICES_TEXTURES_NORMALS (8);
	
	public final int elementsPerVertex;

	private BufferDataFormatType(int elementSize)
	{
		this.elementsPerVertex = elementSize;
	}
}
