package orre.resources.loaders;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

import orre.gl.texture.Texture;

public class TexturePixelConverter {
	
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
	
	public static byte[] getImageDataBytes(BufferedImage image)
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
	
	private static int[] convertPixelToRGBAArray(int pixel)
	{
		int[] pixelArr = new int[4];
		pixelArr[0] = (pixel >> 16) & 0xFF;	//red
		pixelArr[1] = (pixel >> 8) & 0xFF;	//green
		pixelArr[2] = (pixel >> 0) & 0xFF;	//blue
		pixelArr[3] = (pixel >> 24) & 0xFF; //alpha
		return pixelArr;
	}
}
