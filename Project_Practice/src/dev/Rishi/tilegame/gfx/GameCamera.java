package dev.Rishi.tilegame.gfx;

import dev.Rishi.tilegame.Entity.Entity;
import dev.Rishi.tilegame.Tiles.Tile;
import dev.Rishi.tilegame.Handler;

public class GameCamera {

	private float xOffset, yOffset;
	private Handler handler;
	//game camera class used to parts of the map that aren't showned on the window if the map 
	//size is larger compared to the window size
	public GameCamera(Handler handler, float xOffset, float yOffset) {
		this.handler = handler;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	//tells the game camera to stop moving once the end of the map has been reached
	public void checkVoidSpace() {
		if(xOffset < 0) {
			xOffset = 0;
		} else if( xOffset > handler.getWorld().getWidth() * Tile.TILEWIDTH - handler.getWidth()) {
			xOffset = handler.getWorld().getWidth() * Tile.TILEWIDTH - handler.getWidth();
		}
		if(yOffset < -150) {
			yOffset = -150;
		} else if( yOffset > handler.getWorld().getHeight() * Tile.TILEHEIGHT - handler.getHeight()) {
			yOffset = handler.getWorld().getHeight() * Tile.TILEHEIGHT - handler.getHeight();
		}
		
	}
	//centers the game camera on a specified entity (mainly the player)
	public void centreOnEntity(Entity e) {
		xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
		yOffset = e.getY() - handler.getHeight() /2 + e.getHeight() / 2;
		checkVoidSpace();
	}
	//moves the game camera at the same rate the entity is moving
	public void move(float xAmount, float yAmount) {
		xOffset += xAmount;
		yOffset += yAmount;
		checkVoidSpace();
	}
	
	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}
}
