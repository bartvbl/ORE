package orre.resources.loaders;

import java.io.File;
import java.io.IOException;

import lib.ldd.lif.AssetsFilePaths;
import lib.ldd.lif.LIFFile;
import lib.ldd.lif.LIFReader;
import orre.resources.FileToLoad;
import orre.resources.Finalizable;

public class LXFMLLoader {
	private static final File assetsFile = new File("res/Assets.lif");
	private static final LIFReader dbReader = openDBReader();
	private static IOException assetsFileError = null;

	public static Finalizable load(FileToLoad file) throws IOException {
		checkDBFileAvailability();
		
		File fileLocation = new File(file.getPath());
		
		return null;
	}

	private static void checkDBFileAvailability() throws IOException {
		if(dbReader == null) {
			throw new IOException("Failed to load Assets.lif file", assetsFileError);
		}
	}

	private static LIFReader openDBReader() {
		try {
			LIFReader assetsReader = LIFReader.openLIFFile(assetsFile);
			LIFFile dbFile = assetsReader.getFileAt(AssetsFilePaths.dbFile);
			return assetsReader.readInternalLIFFile(dbFile);
		} catch (IOException e) {
			assetsFileError  = e;
			return null;
		}
	}

}
