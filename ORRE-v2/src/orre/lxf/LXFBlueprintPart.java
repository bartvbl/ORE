package orre.lxf;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector4f;

import orre.gl.vao.GeometryBufferGenerator;
import orre.gl.vao.GeometryNode;
import orre.gl.vao.VBOFormat;
import orre.resources.Finalizable;
import orre.resources.Resource;
import lib.ldd.data.VBOContents;

public class LXFBlueprintPart implements Finalizable {

	private final VBOContents vboContents;
	public final String name;
	private GeometryNode compiledVBOContents;

	public LXFBlueprintPart(VBOContents vboContents, String name) {
		this.vboContents = vboContents;
		this.name = name;
	}

	public LXFPart getPartInstance() {
		LXFPart part = new LXFPart(name, compiledVBOContents);
		Vector4f origin = vboContents.getOrigin();
		part.setPivotLocation(-origin.x, -origin.y, -origin.z);
		return part;
	}

	@Override
	public Resource finalizeResource() {
		int vertexCount = vboContents.vertexCount;
		int indexCount = vboContents.indices.length;
		int bufferSize = (vboContents.texturesEnabled ? 8 : 6) * vboContents.vertexCount;
		
		VBOFormat dataFormat = vboContents.texturesEnabled ? VBOFormat.VERTICES_TEXTURES_NORMALS : VBOFormat.VERTICES_AND_NORMALS;
		FloatBuffer geometryBuffer = BufferUtils.createFloatBuffer(bufferSize);
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
