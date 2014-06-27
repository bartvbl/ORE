package orre.geom.vbo;

public enum VBOFormat {
	VERTICES (3),
	VERTICES_AND_TEXTURES (5),
	VERTICES_AND_NORMALS (6),
	VERTICES_TEXTURES_NORMALS (8);
	
	public final int elementsPerVertex;

	private VBOFormat(int elementSize)
	{
		this.elementsPerVertex = elementSize;
	}
}
