package dev.Rishi.tilegame.Entity;

import java.awt.Graphics;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.Tiles.Tile;
import dev.Rishi.tilegame.gfx.Assets;

public class Projectiles extends Entity{

	public static final float DEFAULT_SPEED = 4.0f;
	private float speed;
	private float xMove, yMove;
	private Direction direction;
	private int width, height;
	private float x, y;


	//implements concepts from other classes but uses it's own class as we don't want entities inside the array
	//list to create other entities in the same list
	public Projectiles(Handler handler, float x, float y, int width, int height, Direction direction) {
		super(handler, x, y, width, height);
		speed = DEFAULT_SPEED;
		this.direction = direction;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.solid = false;
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = width;
		bounds.height = height;
	}

	//the enemies direction has been added into the constructor, all you need to do is make it move in the direction that 
	//the enemy is facing
	public void move() {
		if(direction == Direction.LEFT) {
			xMove = -speed;
		} else if(direction == Direction.RIGHT) {
			xMove = speed;
		} else if(direction == Direction.UP) {
			yMove = -speed;
		} else {
			yMove = speed;
		}
		checkX();
		checkY();
	}

	public void checkY() {
		if(yMove < 0) { //Moving up
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;

			if(!collisionWithTile((int) (x + bounds.x)/ Tile.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width)/ Tile.TILEWIDTH, ty)) {
				y += yMove;
				bounds.y += yMove;
			} else {
				die();
			}
		} else if(yMove > 0) { //Moving down
			int ty = (int) (y + yMove + bounds.y + bounds.height ) / Tile.TILEHEIGHT;

			if(!collisionWithTile((int) (x + bounds.x)/ Tile.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width)/ Tile.TILEWIDTH, ty)) {
				y += yMove;
				bounds.y += yMove;
			} else {
				die();
			}
		}
	}

	public void checkX() {
		if(xMove > 0) { //Moving right
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y)/ Tile.TILEHEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
				bounds.x += xMove;
			} else {
				die();
			}
		}else if(xMove < 0) { //Moving left
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;

			if(!collisionWithTile(tx, (int) (y + bounds.y)/ Tile.TILEHEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			bounds.x += xMove;
			} else {
				die(); 				
			}
		}
		
	}

	//applies our entity collision logic to deal damage
	private void attackEntity(int amount) {
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)){
				continue;
			}
			if((e.getCollisionBounds(0, 0).intersects(this.getCollisionBounds(0, 0))) && (e.isSolid())) {
				e.hurt(amount, direction);
				die();
			}
		}
	}


	protected boolean collisionWithTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isSolid();
	}

	@Override
	public void tick() {
		move();
		attackEntity(1);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.projectile, (int)(x - handler.getGameCamera().getxOffset()),
				(int)(y - handler.getGameCamera().getyOffset()), width, height, null);
	}

	@Override
	public void die() {
		active=false;
	}

	@Override
	public void stun() {
	}

	@Override
	public void knockback(Direction direction) {
	}

}
