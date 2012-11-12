package orre.resources.loaders.map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

public class ZipImageLoader {
	public static BufferedImage readImageFromZipFile(ZipFile mapFile, String src) {
		try {
			InputStream inStream = mapFile.getInputStream(mapFile.getEntry(src));
			return ImageIO.read(inStream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
