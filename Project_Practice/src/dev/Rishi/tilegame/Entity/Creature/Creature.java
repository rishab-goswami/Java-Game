package dev.Rishi.tilegame.Entity.Creature;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.Entity.Entity;
import dev.Rishi.tilegame.Tiles.Tile;
import dev.Rishi.tilegame.sfx.SoundAssets;

public abstract class Creature extends Entity {
	
	//Default stats of a creature
	public static final float DEFAULT_SPEED = 4.0f;
	public static final int DEFAULT_CREATURE_WIDTH = 128,
							DEFAULT_CREATURE_HEIGHT = 128;
	
	protected float speed;
	protected float xMove, yMove;
	private long lastStunnedTimer, StunnedCooldown = 2000, StunnedTimer = StunnedCooldown;
	protected boolean stunned = false;
	protected int flickerTimer = 1;
	
	public Creature(Handler handler, float x, float y, int width, int height) {
		//Passes x and y variable to the Entity class constructor
		super(handler, x, y, width, height);
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
		ID = 0;
	}
	
	public void move() {
		if(!checkEntityCollision(xMove, 0f))
			moveX();
		if(!checkEntityCollision(0f, yMove))
			moveY();
	}
	
	public void moveX() {
		if(xMove > 0) { //Moving right
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y)/ Tile.TILEHEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} else {
				x = tx * Tile.TILEWIDTH - bounds.x - bounds.width -2;
				SoundAssets.collide.play();
			}
		}else if(xMove < 0) { //Moving left
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y)/ Tile.TILEHEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} else {
				x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x ;
				SoundAssets.collide.play();
			}
		}
	}
	
	public void moveY() {
		if(yMove < 0) { //Moving up
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;
			
			if(!collisionWithTile((int) (x + bounds.x)/ Tile.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width)/ Tile.TILEWIDTH, ty)) {
				y += yMove;
			} else {
				y = ty* Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
				SoundAssets.collide.play();
			}
		} else if(yMove > 0) { //Moving down
			int ty = (int) (y + yMove + bounds.y + bounds.height ) / Tile.TILEHEIGHT;
			
			if(!collisionWithTile((int) (x + bounds.x)/ Tile.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width)/ Tile.TILEWIDTH, ty)) {
				y += yMove;
			} else {
				y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height -1;
				SoundAssets.collide.play();
			}
		}
	}
	
	//stunning method for entities
	protected void checkStunned() {
		StunnedTimer += System.currentTimeMillis() - lastStunnedTimer;
		lastStunnedTimer = System.currentTimeMillis();
		if(StunnedTimer > StunnedCooldown) {	
			stunned = false;
			StunnedTimer = 0;
		}
	}
		
	protected boolean collisionWithTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	//knock back method for entities after they get attacked
	//this gets called in the hurt method so it knows which 
	//entity gets knocked back
	@Override
	public void knockback(Direction direction) {
		if (direction == Direction.DOWN) {
			this.yMove = 100;
			move();
		} else if (direction == Direction.UP) {
			this.yMove = -100;
			move();
		} else if (direction == Direction.LEFT) {
			this.xMove = -100;
			move();
		} else if (direction == Direction.RIGHT) {
			this.xMove = 100;
			move();
		}
	}
	

	//method for timing how long to flicker image render for
	//each entity when hurt to indicate that the entity has
	//taken damage
	@Override
	public void hurt(int amount, Direction direction) {
		health -= amount;
		knockback(direction);
		flickerTimer = 61;
		if(health <= 0) {
			active = false;
			die();
		}
		SoundAssets.hit.play();
	}
	
	protected void flickerImage() {
		if(flickerTimer > 0) {
			flickerTimer--;
		}
	}
	
	public void Die() {
		active = false;
	}
}