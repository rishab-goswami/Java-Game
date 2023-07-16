package dev.Rishi.tilegame.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {
	//loads in an imaged from a given file path
	public static BufferedImage loadImage(String path) {
	try {
		return ImageIO.read(ImageLoader.class.getResource(path));
	} catch (IOException e) {
		e.printStackTrace();
		System.exit(1);
	}
	return null;
	}
	
}

