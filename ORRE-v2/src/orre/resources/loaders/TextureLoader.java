package orre.resources.loaders;

import static org.lwjgl.opengl.GL11.*;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import orre.gl.texture.Texture;
import orre.resources.UnloadedResource;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;
import orre.util.FeedbackProvider;

public class TextureLoader {
	public static PartiallyLoadableTexture partiallyLoadTextureFromFile(UnloadedResource file) throws FileNotFoundException, IOException, Exception {
		BufferedImage image = loadImageFromFile(file.getPath());
		byte[] imageData = TexturePixelConverter.getImageDataBytes(image);
		return new PartiallyLoadableTexture(file.name, imageData, image.getWidth(), image.getHeight());
	}
	
	public static Texture createTextureFromImage(BufferedImage image) throws Exception
	{
		byte[] imageData = TexturePixelConverter.getImageDataBytes(image);
		if((image != null) && (imageData != null))
		{
			return createTexture(imageData, image.getWidth(), image.getHeight());
		} else {
			return null;
		}
	}
	
	public static ByteBuffer getImageData(String src) throws FileNotFoundException, IOException, Exception
	{
		BufferedImage image = loadImageFromFile(src);
		byte[] imageData = TexturePixelConverter.getImageDataBytes(image);
		ByteBuffer bb = ByteBuffer.allocateDirect(imageData.length).order(ByteOrder.nativeOrder());
		bb.put(imageData).flip();
		return bb;
	}
	
	public static Texture loadTextureFromFile(String src) throws FileNotFoundException, IOException, Exception
	{
		return createTextureFromImage(loadImageFromFile(src));
	}
	
	public static BufferedImage loadImageFromFile(String src) throws FileNotFoundException, IOException
	{
		BufferedImage img = null;
		InputStream in = null;
		in = new FileInputStream(src);
		img = ImageIO.read(in);
		AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -1*img.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage image = op.filter(img, null);
		return image;
	}
	
	public static Texture createTexture(byte[] imageData, int width, int height)
	{
		IntBuffer textureHandle = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
		ByteBuffer bb = ByteBuffer.allocateDirect(imageData.length).order(ByteOrder.nativeOrder());
		bb.put(imageData).flip();
		glGenTextures(textureHandle);
		int texRef = textureHandle.get(0);
		glPushAttrib(GL_TEXTURE_BIT);
		glBindTexture(GL_TEXTURE_2D,texRef);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, bb);
		glPopAttrib();
		return new Texture(texRef, width, height);
	}

	
}
