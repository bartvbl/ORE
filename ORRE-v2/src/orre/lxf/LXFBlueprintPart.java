package orre.lxf;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import orre.geom.vbo.VBOFormat;
import orre.geom.vbo.GeometryBufferGenerator;
import orre.geom.vbo.GeometryNode;
import orre.resources.Finalizable;
import orre.resources.Resource;
import orre.sceneGraph.SceneNode;
import lib.ldd.data.VBOContents;

public class LXFBlueprintPart implements Finalizable {

	private final VBOContents vboContents;
	public final String name;
	private GeometryNode compiledVBOContents;

	public LXFBlueprintPart(VBOContents vboContents, String name) {
		this.vboContents = vboContents;
		this.name = name;
	}
	
	public SceneNode getSceneNode() {
		return compiledVBOContents;
	}

	@Override
	public Resource finalizeResource() {
		int vertexCount = vboContents.vertexCount;
		int indexCount = vboContents.indices.length;
		int bufferSize = (vboContents.texturesEnabled ? 8 : 6) * vboContents.vertexCount;
		
		VBOFormat dataFormat = vboContents.texturesEnabled ? VBOFormat.VERTICES_TEXTURES_NORMALS : VBOFormat.VERTICES_AND_NORMALS;
		DoubleBuffer geometryBuffer = BufferUtils.createDoubleBuffer(bufferSize);
		for(int i = 0; i < vertexCount; i++) {
			geometryBuffer.put(vboContents.vertices[3*i]).put(vboContents.vertices[3*i+1]).put(vboContents.vertices[3*i+2]);
			if(vboContents.texturesEnabled) {
				geometryBuffer.put(vboContents.textures[2*i]).put(vboContents.textures[2*i + 1]);
			}
			geometryBuffer.put(vboContents.normals[3*i]).put(vboContents.normals[3*i+1]).put(vboContents.normals[3*i+2]);
		}
		
		IntBuffer indices = BufferUtils.createIntBuffer(indexCount);
		indices.put(vboContents.indices);
		
		geometryBuffer.flip();
		indices.flip();
		
		compiledVBOContents = GeometryBufferGenerator.generateGeometryBuffer(dataFormat, geometryBuffer, indices, vertexCount, indexCount);
		return null;
	}

}
