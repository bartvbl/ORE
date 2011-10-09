package orre.gl;

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

import static org.lwjgl.opengl.GL11.*;

public class Texture {
	public int texRef = -1;
	public BufferedImage buffer;
	public int width, height;
	public byte[] bytes;
	public int[][] RGBAPixels;
	
	public Texture(String src)
	{
		buffer = this.loadImage(src);
		this.width = buffer.getWidth();
		this.height = buffer.getHeight();
		this.bytes = this.getBytesFromBuffer(buffer);
		texRef = this.createImage(buffer);
	}
	public void blit(int x, int y, int width, int height)
	{
		glBindTexture(GL_TEXTURE_2D, texRef);
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2f(x,y+height);
		glTexCoord2f(0,1);
		glVertex2f(x,y);
		glTexCoord2f(1,1);
		glVertex2f(x+width,y);
		glTexCoord2f(1,0);
		glVertex2f(x+width,y+height);
		glEnd();
	}
	public byte[] getRGBABytes()
	{
		if(this.bytes != null)
		{
			return this.bytes;
		} else {
			return null;
		}
	}
	public int[] getPixelAt(int x, int y)
	{
		return this.RGBAPixels[width*x + y];
	}
	public void setImage(BufferedImage image)
	{
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.bytes = this.getBytesFromBuffer(image);
		this.texRef = this.createImage(image);
	}
	@SuppressWarnings("unused")
	private BufferedImage loadImage(String src)
	{
		BufferedImage img = null;
		InputStream in = null;
    	try {
    		in = new FileInputStream(src);
    	}
    	catch (IOException ioe) {
    		ioe.printStackTrace();
    		if (in != null) {
    			try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    	catch (Exception e) {System.out.println("oops. " + e.getMessage());}
		
		try {
			
			img = ImageIO.read(in);
		}
		catch (Exception e) {System.out.println("oops. " + e.getMessage());}
		AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -1*img.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage image = op.filter(img, null);
		return image;
	}
	private int createImage(BufferedImage image)
	{
		IntBuffer textureHandle = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
		ByteBuffer bb = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder());
		bb.put(bytes).flip();
		glGenTextures(textureHandle);
		int texRef = textureHandle.get(0);
		glPushAttrib(GL_TEXTURE_BIT);
		glBindTexture(GL_TEXTURE_2D,texRef);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, bb);
		glPopAttrib();
		return texRef;
	}
	private byte[] getBytesFromBuffer(BufferedImage image)
	{
		int[] pixelsARGB = null;
		if (image != null) {
			int imgw = image.getWidth(null);
			int imgh = image.getHeight(null);
			pixelsARGB = new int[ imgw * imgh];
			PixelGrabber pg = new PixelGrabber(image, 0, 0, imgw, imgh, pixelsARGB, 0, imgw);
			try {
				pg.grabPixels();
			}
			catch (Exception e) {System.out.println("oops. " + e.getMessage());}
		}
		byte[] bytes = new byte[pixelsARGB.length*4];
        int p, r, g, b, a;
        int j=0;
        this.RGBAPixels = new int[pixelsARGB.length][4];
        int[] pixels;
        for (int i = 0; i < pixelsARGB.length; i++) {
            p = pixelsARGB[i];
            a = (p >> 24) & 0xFF;
            r = (p >> 16) & 0xFF;
            g = (p >> 8) & 0xFF;
            b = (p >> 0) & 0xFF;
            bytes[j+0] = (byte)r;
            bytes[j+1] = (byte)g;
            bytes[j+2] = (byte)b;
            bytes[j+3] = (byte)a;
            j += 4;
            pixels = new int[4];
            pixels[0] = r;
            pixels[1] = g;
            pixels[2] = b;
            pixels[3] = a;
            this.RGBAPixels[i] = pixels;
        }
        return bytes;
	}
}
