package dev.Rishi.tilegame.Entity.Creature;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.Items.Item;
import dev.Rishi.tilegame.gfx.Animation;
import dev.Rishi.tilegame.gfx.Assets;

public class BossUnlocker extends Enemy  {

	private Animation enemyDown, enemyUp, enemyLeft, enemyRight;

	public BossUnlocker(Handler handler, float x, float y) {
		super(handler, x, y);
		
		bounds.x = 30;
		bounds.y = 10;
		bounds.width = 70;
		bounds.height = 110;
		
		//Animations
		//every 100 ms animation changes
		enemyDown = new Animation(100, Assets.enemy_down);
		enemyUp = new Animation(100, Assets.enemy_up);
		enemyLeft = new Animation(100, Assets.enemy_left);
		enemyRight = new Animation(100, Assets.enemy_right);
		ID = 5;
		this.health = 20;
	}
	
	@Override
	public void tick() {
		flickerImage();
		checkStunned();
		if (!stunned) {
			//Animations
			enemyDown.tick();
			enemyUp.tick();
			enemyLeft.tick();
			enemyRight.tick();

			checkAggression();

			//Movement
			if (aggression == false) {
				getInput();
				move();
			}

			//Attack
			trackPlayer();
			checkAttacks();
		}
	}
	
	protected void trackPlayer() {
		if (aggression) {
			yMove = 0;
			xMove = 0;
			if (xDelta < -5) {
				xMove = -speed;
			} else if (xDelta > 5) {
				xMove = speed;
			} else if (yDelta > 5) {
				yMove = speed;
			} else if (yDelta < -5) {
				yMove = -speed;
			} else {
				return;
			}
			move();
		}
	}

	private BufferedImage getCurrentAnimationFrame() {
		if(xMove < 0) {
			lastMoved = Direction.LEFT;
			return enemyLeft.getCurrentFrame();
		} else if(xMove > 0) {
			lastMoved = Direction.RIGHT;
			return enemyRight.getCurrentFrame();
		} else if(yMove < 0) {
			lastMoved = Direction.UP;
			return enemyUp.getCurrentFrame();
		} else if(yMove > 0) {
			lastMoved = Direction.DOWN;
			return enemyDown.getCurrentFrame();
		} else {
			if(lastMoved == Direction.LEFT)
				return Assets.enemy_left[0];
			else if(lastMoved == Direction.RIGHT)
				return Assets.enemy_right[0];
			else if(lastMoved == Direction.UP)
				return Assets.enemy_up[0];
			else
				return Assets.enemy_down[0];

		}
	}
	
	//as this enemy dies it'll spawn a key that unlocks the teleportation tile to the boss room
	@Override
	public void die() {
		this.active = false;
		handler.getWorld().getItemManager().addItem(Item.key.createNew((int) x+(Creature.DEFAULT_CREATURE_WIDTH/2),
				(int) y+(Creature.DEFAULT_CREATURE_HEIGHT/2)));
	}

	@Override
	public void render(Graphics g) {
		if ((flickerTimer % 15 < 5) && (flickerTimer != 0)) {

		} else {
			g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
					(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		}
	}
}
