package dev.Rishi.tilegame.Tiles;

import java.awt.image.BufferedImage;

public class NoisyTile extends Tile {

	//a tile that makes noise when stepped on
	public NoisyTile(BufferedImage texture ,int id) {
		super(texture, id);
	}
	@Override
	public boolean isNoisy() {
		return true;
	}

}