package dev.Rishi.tilegame.Entity.Creature;

import java.awt.Graphics;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.Entity.Entity;
import dev.Rishi.tilegame.Entity.Projectiles;
import dev.Rishi.tilegame.gfx.Assets;

public class RangedEnemy extends Enemy {

	protected Entity recogniseEntity = null;

	public RangedEnemy(Handler handler, float x, float y) {
		super(handler, x, y);
		bounds.x = 17;
		bounds.y = 20;
		bounds.width = 95;
		bounds.height = 100;
		if (speedModifier) {
			this.speed = Creature.DEFAULT_SPEED - 1.5f;
		} else {
			this.speed = Creature.DEFAULT_SPEED - 2.5f;
		}
		ID = 4;
	}

	//does not need to track to the player as this enemy shoots projectiles
	//instead it just attacks if aggression is true
	@Override
	public void tick() {
		flickerImage();
		checkStunned();
		if (!stunned) {
			checkAggression();

			//Movement
			if (!aggression) {
				getInput();
				move();
			}

			//Attack
			checkAttacks();
		}
	}

	@Override
	protected void checkAttacks() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCooldown) {
			return;
		}

		//when player is within aggression range it'll stop moving and start attack in direction
		//that the player is furtherest away from the enemy
		if (aggression) {
			yMove = 0;
			xMove = 0;
			if ((yDelta < 0) && (Math.abs(yDelta) > Math.abs(xDelta))) {
				lastMoved = Direction.UP;
				move();
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x + Creature.DEFAULT_CREATURE_WIDTH/2 - 10, this.y - 20, 20, 20, Direction.UP));
			} else if ((yDelta > 0) && (Math.abs(yDelta) > Math.abs(xDelta))) {
				lastMoved = Direction.DOWN;
				move();
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x + Creature.DEFAULT_CREATURE_WIDTH/2 - 10, this.y + Creature.DEFAULT_CREATURE_HEIGHT + 20, 20, 20, Direction.DOWN));
			} else if ((xDelta < 0) && (Math.abs(yDelta) < Math.abs(xDelta))) {
				lastMoved = Direction.LEFT;
				move();
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x - 20, this.y + Creature.DEFAULT_CREATURE_HEIGHT/2, 20, 20, Direction.LEFT));
			} else if ((xDelta > 0) && (Math.abs(yDelta) < Math.abs(xDelta))) {
				lastMoved = Direction.RIGHT;
				move();				
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x + Creature.DEFAULT_CREATURE_WIDTH + 20, this.y + Creature.DEFAULT_CREATURE_HEIGHT/2, 20, 20, Direction.RIGHT));
			}
		}

		attackTimer = 0;
	}

	@Override
	public void render(Graphics g) {
		if ((flickerTimer % 15 < 5) && (flickerTimer != 0)) {

		} else {
			g.drawImage(Assets.brown_closet, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		}
	}
}


