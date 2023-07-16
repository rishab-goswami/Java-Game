package dev.Rishi.tilegame.Tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.Rishi.tilegame.gfx.Assets;

public class Tile {

	//Makes a tiles array of different types of tiles to be used when building worlds
	public static Tile[] tiles = new Tile[256];

	public static Tile darkTile = new SolidTile(Assets.dark_tile, 1);
	public static Tile woodTile = new NotSolidTile(Assets.wood, 2);
	public static Tile crackedWoodTile = new NoisyTile(Assets.cracked_wood, 3);

	public static Tile leftWall = new SolidTile(Assets.left_wall, 4);
	public static Tile topWall = new SolidTile(Assets.top_wall, 5);
	public static Tile rightWall = new SolidTile(Assets.right_wall, 6);
	public static Tile bottomWall = new SolidTile(Assets.bottom_wall, 7);

	public static Tile topLeftEdge = new SolidTile(Assets.top_left_edge, 8);
	public static Tile topRightEdge = new SolidTile(Assets.top_right_edge, 9);
	public static Tile bottomRigthEdge = new SolidTile(Assets.bottom_right_edge, 10);
	public static Tile bottomLeftEdge = new SolidTile(Assets.bottom_left_edge, 11);

	public static Tile topLeftEdge2 = new SolidTile(Assets.top_left_edge_2, 12);
	public static Tile topRightEdge2 = new SolidTile(Assets.top_right_edge_2, 13);
	public static Tile bottomRigthEdge2 = new SolidTile(Assets.bottom_right_edge_2, 14);
	public static Tile bottomLeftEdge2 = new SolidTile(Assets.bottom_left_edge_2, 15);

	public static Tile pinkTile = new NotSolidTile(Assets.pink_mat, 16);
	public static Tile blueTile = new NotSolidTile(Assets.blue_mat, 17);
	public static Tile greenTile = new NotSolidTile(Assets.green_mat, 18);

	public static Tile checkedTile = new NotSolidTile(Assets.checker_tile, 19);
	public static Tile crackedCheckerTile = new NoisyTile(Assets.cracked_checker_tile, 20);
	//Class
	public static final int TILEWIDTH = 100, TILEHEIGHT = 100;

	protected BufferedImage texture;
	protected final int id;

	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;

		tiles[id] = this;
	}

	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);

	}

	//checks if tile is solid or now
	public boolean isSolid() {
		return false;
	}

	//checks if tile makes noise
	public boolean isNoisy() {
		return false;
	}

	public int getId() {
		return id;
	}

}
