package dev.Rishi.tilegame.Entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.sfx.SoundAssets;

public abstract class Entity {

	public static final int DEFAULT_HEALTH = 10;
	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected int health;
	protected boolean active = true;
	protected boolean solid = true;
	protected Rectangle bounds;
	protected long attackCooldown;
	protected boolean speedModifier;
	protected int aggressionRange;
	protected int ID;
	
	//enums set for easy direction detection
	public enum Direction{
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
	
	public Entity(Handler handler, float x, float y, int width, int height){
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.solid = true;
		health = DEFAULT_HEALTH;
		
		bounds = new Rectangle(0, 0, width, height);
	}
	
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void die();
	
	public abstract void stun();
	
	public abstract void knockback(Direction direction);
	
	public void hurt(int amount, Direction direction) {
		health -= amount;
		knockback(direction);
		if(health <= 0) {
			die();
		}
		SoundAssets.hit.play();
	}
	
	//entity collision is done by calling all other entities and checking if their bounds intersect with the entity that
	//calls this method and also stops it checking with itself
	public Rectangle getCollisionBounds(float xOffset, float yOffset) {
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	public boolean checkEntityCollision(float xOffset, float yOffset) {
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)){
				continue;
			}
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset,yOffset)) && e.isSolid()) {
				return true;
			}
		}
		return false;
	}
	
	//an altered version of our entity collision, this time it's used by our room transition
	//method and checks if the entity colliding with it is also a player, this stops any other
	//entities from teleporting into other rooms
	public boolean checkPlayerCollision(float xOffset, float yOffset) {
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)){
				continue;
			}
			if((e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset,yOffset)) && e.isSolid() &&
					(e == handler.getWorld().getEntityManager().getPlayer()))) {
				return true;
			}
		}
		return false;
	}
	
	//auto generated getters and setters for x, y, width and height
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public void setSpeed(boolean speedModifier) {
		this.speedModifier = speedModifier;
	}
	
	public void setAttackCooldown(long cooldown) {
		this.attackCooldown = cooldown;
	}
	
	public void setAggressionRange(int range) {
		this.aggressionRange = range;
	}
	
	public int getID() {
		return ID;
	}
}