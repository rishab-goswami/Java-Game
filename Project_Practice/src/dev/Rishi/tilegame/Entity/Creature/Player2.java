package dev.Rishi.tilegame.Entity.Creature;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.Entity.Entity;
import dev.Rishi.tilegame.Inventory.Inventory;
import dev.Rishi.tilegame.Tiles.Tile;
import dev.Rishi.tilegame.gfx.Animation;
import dev.Rishi.tilegame.gfx.Assets;
import dev.Rishi.tilegame.sfx.SoundAssets;

//class is only called in co-op to create a helper character for player 1
//all logic is exactly the same as player 1 however player 2 dying won't
//end the game
public class Player2 extends Player {

	//Animations
	private Animation playerDown, playerUp, playerLeft, playerRight;

	Direction lastMoved = Direction.DOWN;

	//Attack timer 
	private long lastAttackTimer, attackCooldown = 200, attackTimer = attackCooldown;
	private int attackFrame;

	//Inventory
	private Inventory inventory;

	private int noise = 0;
	private boolean stepped = false;
	int sameTileRight, sameTileLeft, sameTileTop, sameTileBottom;


	public Player2(Handler handler, float x, float y) {
		super(handler, x, y);

		//x and y tell us the starting position of our collision detection rectangle
		//width and height tell us the size of our collision detection rectangle
		bounds.x = 44;
		bounds.y = 50;
		bounds.width = 40;
		bounds.height = 64;

		//Animations
		//every 300 ms animation changes
		playerDown = new Animation(300, Assets.player_down);
		playerUp = new Animation(300, Assets.player_up);
		playerLeft = new Animation(300, Assets.player_left);
		playerRight = new Animation(300, Assets.player_right);

		speed = 5.0f;
		this.health = 2;
	}

	//updates any variables for any objects
	@Override
	public void tick() {
		flickerImage();
		checkStunned();
		if(!stunned) {
			//Animations
			playerDown.tick();
			playerUp.tick();
			playerLeft.tick();
			playerRight.tick();

			//Movement
			getInput();
			move();
			noiseX();
			noiseY();

			//Attack
			checkAttacks();
		}
	}

	public void stun() {
		stunned = true;
	}

	private void checkAttacks() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();

		if(attackTimer < attackCooldown) {	
			return;
		}

		Rectangle cb = getCollisionBounds(0,0);
		Rectangle ar = new Rectangle();
		int arSize = 20;
		ar.width = arSize;
		ar.height = arSize;

		if((handler.getKeyManager().m) && (lastMoved == Direction.UP)) {
			attackFrame = 10;
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize;
		} else if((handler.getKeyManager().m) && (lastMoved == Direction.DOWN)){
			attackFrame = 10;
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
		} else if((handler.getKeyManager().m) && (lastMoved == Direction.LEFT)) {
			attackFrame = 10;
			ar.x = cb.x -arSize;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		}else  if((handler.getKeyManager().m) && (lastMoved == Direction.RIGHT)) {
			attackFrame = 10;
			ar.x = cb.x + cb.width;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		} else {
			return;
		}

		attackTimer = 0;

		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0, 0).intersects(ar)) {
				e.hurt(1, lastMoved);
				return;
			}
		}
	}

	public void noiseX() {
		if(xMove > 0) {
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;

			if(!isNoisyTile(tx, (int) (y + bounds.y)/ Tile.TILEHEIGHT) &&
					!isNoisyTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				//add somthing?
			} else if(!stepped) {
				increaseNoise(1);
				stepped = true;
				sameTileRight = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
				sameTileLeft = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
				sameTileTop = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;
				sameTileBottom = (int) (y + yMove + bounds.y + bounds.height ) / Tile.TILEHEIGHT;
				SoundAssets.creak.play();
			} 
			if((sameTileRight != (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH) && (sameTileLeft != (int) (x + xMove + bounds.x) / Tile.TILEWIDTH) &&
					(sameTileTop != (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT) && (sameTileBottom != (int) (y + yMove + bounds.y + bounds.height ) / Tile.TILEHEIGHT)) {
				stepped = false;
			}
		}else if(xMove < 0) {
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;

			if(!isNoisyTile(tx, (int) (y + bounds.y)/ Tile.TILEHEIGHT) &&
					!isNoisyTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
			} else if(!stepped){
				increaseNoise(1);
				stepped = true;
				sameTileRight = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
				sameTileLeft = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
				sameTileTop = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;
				sameTileBottom = (int) (y + yMove + bounds.y + bounds.height ) / Tile.TILEHEIGHT;
				SoundAssets.creak.play();
			}
			if((sameTileRight != (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH) && (sameTileLeft != (int) (x + xMove + bounds.x) / Tile.TILEWIDTH) &&
					(sameTileTop != (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT) && (sameTileBottom != (int) (y + yMove + bounds.y + bounds.height ) / Tile.TILEHEIGHT)) {
				stepped = false;
			}
		}
	}

	public void noiseY() {
		if(yMove < 0) {
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;

			if(!isNoisyTile((int) (x + bounds.x)/ Tile.TILEWIDTH, ty) &&
					!isNoisyTile((int) (x + bounds.x + bounds.width)/ Tile.TILEWIDTH, ty)) {
			} else if(!stepped){
				increaseNoise(1);
				stepped = true;
				sameTileRight = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
				sameTileLeft = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
				sameTileTop = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;
				sameTileBottom = (int) (y + yMove + bounds.y + bounds.height ) / Tile.TILEHEIGHT;
				SoundAssets.creak.play();
			}
			if((sameTileRight != (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH) && (sameTileLeft != (int) (x + xMove + bounds.x) / Tile.TILEWIDTH) &&
					(sameTileTop != (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT) && (sameTileBottom != (int) (y + yMove + bounds.y + bounds.height ) / Tile.TILEHEIGHT)) {
				stepped = false;
			}
		} else if(yMove > 0) {
			int ty = (int) (y + yMove + bounds.y + bounds.height ) / Tile.TILEHEIGHT;

			if(!isNoisyTile((int) (x + bounds.x)/ Tile.TILEWIDTH, ty) &&
					!isNoisyTile((int) (x + bounds.x + bounds.width)/ Tile.TILEWIDTH, ty)) {
			} else if(!stepped) {
				increaseNoise(1);
				stepped = true;
				sameTileRight = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
				sameTileLeft = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
				sameTileTop = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;
				sameTileBottom = (int) (y + yMove + bounds.y + bounds.height ) / Tile.TILEHEIGHT;
				SoundAssets.creak.play();
			}
			if((sameTileRight != (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH) && (sameTileLeft != (int) (x + xMove + bounds.x) / Tile.TILEWIDTH) &&
					(sameTileTop != (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT) && (sameTileBottom != (int) (y + yMove + bounds.y + bounds.height ) / Tile.TILEHEIGHT)) {
				stepped = false;
			}
		}
	}
	
	@Override
	public void die() {
		active = false;
	}

	public void getInput() {
		xMove = 0;
		yMove = 0;

		//moving is more object oriented. yMove and xMove in the creature method are being
		//changed by the speed we set (also in the creature method)
		//In the tick method, we get our input (from below) and then input those into the move() 
		//method in the creature class;
		if(handler.getKeyManager().p2_up) {
			yMove = -speed;
		}
		if(handler.getKeyManager().p2_down) {
			yMove = speed;
		}
		if(handler.getKeyManager().p2_left) {
			xMove = -speed;
		}
		if(handler.getKeyManager().p2_right) {
			xMove = speed;
		}
	}

	@Override
	public void render(Graphics g) {
		if ((flickerTimer % 15 < 5) && (flickerTimer != 0)) {
		} else {
			if (attackFrame > 0) {
				if (lastMoved == Direction.UP) {
					g.drawImage(Assets.playerAttackUp, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
				} else if (lastMoved == Direction.DOWN) {
					g.drawImage(Assets.playerAttackDown, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
				} else if (lastMoved == Direction.LEFT) {
					g.drawImage(Assets.playerAttackLeft, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
				} else if (lastMoved == Direction.RIGHT) {
					g.drawImage(Assets.playerAttackRight, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
				}
			} else {
				g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			}
			attackFrame--;
		}
	}

	public void postRender(Graphics g) {
		inventory.render(g);
	}

	private BufferedImage getCurrentAnimationFrame() {
		//going left and right takes priority over up and down
		if(xMove < 0) {
			lastMoved = Direction.LEFT;
			return playerLeft.getCurrentFrame();
		} else if(xMove > 0) {
			lastMoved = Direction.RIGHT;
			return playerRight.getCurrentFrame();
		} else if(yMove < 0) {
			lastMoved = Direction.UP;
			return playerUp.getCurrentFrame();
		} else if (yMove > 0){
			lastMoved = Direction.DOWN;
			return playerDown.getCurrentFrame();
		} else {
			if(lastMoved == Direction.LEFT)
				return Assets.player_left[0];
			else if(lastMoved == Direction.RIGHT)
				return Assets.player_right[0];
			else if(lastMoved == Direction.UP)
				return Assets.player_up[0];
			else
				return Assets.player_down[0];

		}
	}

	public Inventory getInventory() {
		return inventory;
	}	

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public int getNoise() {
		return noise;
	}

	public void increaseHealth() {
		health += 1;
		if(health >= 10) {
			health = 10;
		}
	}
	
	public void decreaseNoise() {
		noise -= 1;
		if(noise <= 0) {
			noise = 0;
		}
	}

}
