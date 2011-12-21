package orre.gl.texture;

import static org.lwjgl.opengl.GL11.*;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import orre.util.FeedbackProvider;

public class TextureLoader {
	public static Texture loadTextureFromFile(String src)
	{
		BufferedImage image = loadImageFromFile(src);
		return createTextureFromImage(image);
	}
	
	public static Texture createTextureFromImage(BufferedImage image)
	{
		byte[] imageData = getImageDataBytes(image);
		if((image != null) && (imageData != null))
		{
			int textureID = createTexture(imageData, image.getWidth(), image.getHeight());
			return new Texture(textureID, image.getWidth(), image.getHeight());
		} else {
			return null;
		}
	}
	
	public static BufferedImage loadImageFromFile(String src)
	{
		BufferedImage img = null;
		InputStream in = null;
    	try {
    		in = new FileInputStream(src);
    		img = ImageIO.read(in);
    	}
    	catch (IOException ioe) {
    		FeedbackProvider.showLoadTextureFailedMessage(src, ioe.getMessage()); ioe.printStackTrace();
    		ioe.printStackTrace();
    		if (in != null) {
    			try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		return null;
    	}
		catch (Exception e) {
			FeedbackProvider.showLoadTextureFailedMessage(src, e.getMessage()); e.printStackTrace();
			return null;
		}
		AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -1*img.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage image = op.filter(img, null);
		return image;
	}
	
	private static int[] getPixels(BufferedImage image)
	{
		if (image == null) {
			return null;
		}
		int imgw = image.getWidth(null);
		int imgh = image.getHeight(null);
		int[] pixelsARGB = new int[imgw * imgh];
		PixelGrabber pg = new PixelGrabber(image, 0, 0, imgw, imgh, pixelsARGB, 0, imgw);
		try {
			pg.grabPixels();
		}
		catch (Exception e) {System.out.println("oops. " + e.getMessage());}
		return pixelsARGB;
	}
	
	private static byte[] getImageDataBytes(BufferedImage image)
	{
		int[] pixelsARGB = getPixels(image);
		if(pixelsARGB == null)
		{
			return null;
		}
		int[] pixel;
		byte[] bytes = new byte[pixelsARGB.length * 4];
        for (int i = 0; i < 4 * pixelsARGB.length; i += 4) {
            pixel = convertPixelToRGBAArray(pixelsARGB[i/4]);
            bytes[i+0] = (byte)pixel[0];
            bytes[i+1] = (byte)pixel[1];
            bytes[i+2] = (byte)pixel[2];
            bytes[i+3] = (byte)pixel[3];
        }
        return bytes;
	}
	
	private static int[][] getRGBAPixels(BufferedImage image)
	{
		int[] pixelsARGB = getPixels(image);
		if(pixelsARGB == null)
		{
			return null;
		}
        int[][] RGBAPixels = new int[pixelsARGB.length][4];
        for (int i = 0; i < 4 * pixelsARGB.length; i += 4) {
            RGBAPixels[i] = convertPixelToRGBAArray(pixelsARGB[i/4]);
        }
        return RGBAPixels;
	}
	
	private static int[] convertPixelToRGBAArray(int pixel)
	{
		int[] pixelArr = new int[4];
		pixelArr[0] = (pixel >> 24) & 0xFF; //red
		pixelArr[1] = (pixel >> 16) & 0xFF;	//green
		pixelArr[2] = (pixel >> 8) & 0xFF;	//blue
		pixelArr[3] = (pixel >> 0) & 0xFF;	//alpha
		return pixelArr;
	}
	
//	public int[] getPixelAt(int x, int y)
//	{
//		return this.RGBAPixels[width*x + y];
//	}
	
	private static int createTexture(byte[] imageData, int width, int height)
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
		return texRef;
	}
}
