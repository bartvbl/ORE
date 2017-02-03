package orre.resources.loaders;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import lib.ldd.data.VBOContents;
import orre.resources.IncompleteResourceObject;
import orre.resources.Resource;
import orre.resources.ResourceObject;
import orre.resources.ResourceQueue;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;

public class STLLoader implements ResourceTypeLoader {
	private final byte[] header = new byte[80];
	private static final int bytesPerTriangle = 12 + 12 + 12 + 12 + 2;
	

	@Override
	public IncompleteResourceObject<VBOContents> readResource(Resource source) {
		try {
			FileInputStream inputStream = new FileInputStream(source.fileLocation);
			byte[] singleTriangle = new byte[bytesPerTriangle];
			ByteBuffer triangleBuffer = ByteBuffer.allocate(bytesPerTriangle);
			triangleBuffer.order(ByteOrder.LITTLE_ENDIAN);
			
			inputStream.read(header);
			int triangleCount = inputStream.read();
			
			float[] normal = new float[3];
			
			FloatBuffer normalBuffer = FloatBuffer.allocate(triangleCount * 3);
			FloatBuffer vertexBuffer = FloatBuffer.allocate(triangleCount * 12);
			IntBuffer indexBuffer = IntBuffer.allocate(triangleCount * 3);
			
			for(int i = 0; i < triangleCount; i++) {
				inputStream.read(singleTriangle);
				triangleBuffer.rewind();
				triangleBuffer.put(singleTriangle);
				triangleBuffer.rewind();
				
				normalBuffer.rewind();
				vertexBuffer.rewind();
				
				normal[0] = triangleBuffer.getFloat();
				normal[1] = triangleBuffer.getFloat();
				normal[2] = triangleBuffer.getFloat();
				
				for(int j = 0; j < 3; j++) {
					normalBuffer.put(normal);
				}
				
				for(int j = 0; j < 12; j++) {
					vertexBuffer.put(triangleBuffer.getFloat());
				}
				
				for(int j = 0; j < 3; j++) {
					indexBuffer.put(3 * i + j);
				}
				
			}
			
			inputStream.close();
			
			float[] normals = normalBuffer.array();
			float[] vertices = vertexBuffer.array();
			int[] indices = indexBuffer.array();
			
			VBOContents geometry = new VBOContents(vertices, normals, indices); 
			
			return null;//geometry
		
		} catch (IOException e) {
			throw new RuntimeException("Failed to load STL file", e);
		}
		
	}

	@Override
	public Enum<?> getResourceType() {
		return ResourceType.stlFile;
	}

	@Override
	public ResourceObject<?> completeResource(IncompleteResourceObject<?> object) {
		return null;
	}

}
