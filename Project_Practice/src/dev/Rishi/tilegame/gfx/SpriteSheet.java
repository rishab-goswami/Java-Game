package dev.Rishi.tilegame.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	private BufferedImage sheet;

	public SpriteSheet(BufferedImage sheet) {
		this.sheet = sheet; 
	}
	
	//crops certain parts of the buffered imaged 
	public BufferedImage Crop(int x, int y, int width, int height) {
		return sheet.getSubimage(x, y, width, height);
	}
}
