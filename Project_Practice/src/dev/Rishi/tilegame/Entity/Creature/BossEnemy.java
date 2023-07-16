package dev.Rishi.tilegame.Entity.Creature;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.Entity.Entity;
import dev.Rishi.tilegame.Entity.Projectiles;
import dev.Rishi.tilegame.gfx.Animation;
import dev.Rishi.tilegame.gfx.Assets;

public class BossEnemy extends Enemy{

	private Animation bossDown, bossUp, bossLeft, bossRight,
	bossDownAttack, bossUpAttack, bossLeftAttack, bossRightAttack;
	private int setAttackFrame;

	public BossEnemy(Handler handler, float x, float y) {
		super(handler, x, y);
		this.width = 200;
		this.height = 200;
		bounds.x = 50;
		bounds.y = 50;
		bounds.width = 100;
		bounds.height = 140;
		if (speedModifier) {
			this.speed = Creature.DEFAULT_SPEED - 1;
		} else {
			this.speed = Creature.DEFAULT_SPEED - 2;
		}
		this.health = 50;

		//animations for boss attacks
		bossDown = new Animation(100, Assets.boss_down);
		bossUp = new Animation(100, Assets.boss_up);
		bossLeft = new Animation(100, Assets.boss_left);
		bossRight = new Animation(100, Assets.boss_right);
		bossDownAttack = new Animation(100, Assets.boss_down_attack);
		bossUpAttack = new Animation(100, Assets.boss_up_attack);
		bossLeftAttack = new Animation(100, Assets.boss_left_attack);
		bossRightAttack = new Animation(100, Assets.boss_right_attack);
		ID = 6;
	}

	@Override
	public void tick() {
		flickerImage();
		checkStunned();
		if (!stunned) {
			bossDown.tick();
			bossUp.tick();
			bossLeft.tick();
			bossRight.tick();
			bossDownAttack.tick();
			bossUpAttack.tick();
			bossLeftAttack.tick();
			bossRightAttack.tick();	

			checkAggression();

			if(aggression) {
				trackPlayer();
			} else {
				yMove = 0;
				xMove = 0;
				move();
			}

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

	protected void checkAggression() {
		xDelta = handler.getWorld().getEntityManager().getPlayer().getX() - this.getX();
		yDelta = handler.getWorld().getEntityManager().getPlayer().getY() - this.getY();

		if (((xDelta < 100) && (xDelta > -100)) || ((yDelta < 100) && (yDelta > -100))) {
			aggression = true;
		} else if (handler.getWorld().getEntityManager().getPlayer().getHealth() <= 0) {
			aggression = false;
		} else {
			aggression = false;
		}
	}


	protected void checkAttacks() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCooldown) {
			return;
		}

		Rectangle cb = getCollisionBounds(0,0);
		Rectangle ar = new Rectangle();
		int arSize = 50;
		ar.width = arSize;
		ar.height = arSize;

		//uses the aggression method to check if player is within melee range otherwise it will only use ranged attacks
		if (aggression) {
			setAttackFrame = 30;
			if ((lastMoved == Direction.UP) && (yDelta < 0)) {
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y - arSize;
			} else if ((lastMoved == Direction.DOWN) && (yDelta > 0)) {
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y + cb.height;
			} else if ((lastMoved == Direction.LEFT) && (xDelta < 0)) {
				ar.x = cb.x -arSize;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			} else if ((lastMoved == Direction.RIGHT) && (yDelta > 0)) {
				ar.x = cb.x + cb.width;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			}
		} else {
			yMove = 0;
			xMove = 0;
			if ((yDelta < 0) && (Math.abs(yDelta) > Math.abs(xDelta))) {
				lastMoved = Direction.UP;
				move();
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x + this.width/2 - 25,
						this.y - 50, 50, 50, Direction.UP));
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x + this.width/2 - 85,
						this.y - 50, 50, 50, Direction.UP));
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x + this.width/2 + 35,
						this.y - 50, 50, 50, Direction.UP));
			}   else if ((yDelta > 0) && (Math.abs(yDelta) > Math.abs(xDelta))) {
				lastMoved = Direction.DOWN;
				move();
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x + this.width/2 - 25,
						this.y + this.height + 50, 50, 50, Direction.DOWN));
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x + this.width/2 - 85,
						this.y + this.height + 50, 50, 50, Direction.DOWN));
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x + this.width/2 + 35,
						this.y + this.height + 50, 50, 50, Direction.DOWN));
			} else if ((xDelta < 0) && (Math.abs(yDelta) < Math.abs(xDelta))) {
				lastMoved = Direction.LEFT;
				move();
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x - 50,
						this.y + this.height/2, 50, 50, Direction.LEFT));
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x - 50,
						this.y + this.height/2 + 60, 50, 50, Direction.LEFT));
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x - 50,
						this.y + this.height/2 - 60, 50, 50, Direction.LEFT));
			} else if ((xDelta > 0) && (Math.abs(yDelta) < Math.abs(xDelta))) {
				lastMoved = Direction.RIGHT;
				move();				
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x + this.width + 50,
						this.y + this.height/2, 50, 50, Direction.RIGHT));
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x + this.width + 50,
						this.y + this.height/2 + 60, 50, 50, Direction.RIGHT));
				handler.getWorld().getEntityManager().addProjectile(new Projectiles(handler, this.x + this.width + 50,
						this.y + this.height/2 - 60, 50, 50, Direction.RIGHT));
			}
		}

		attackTimer = 0;

		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0, 0).intersects(ar)) {
				e.hurt(2, lastMoved);
				return;

			}
		}
	}

	private BufferedImage getCurrentAnimationFrame() {
		if(xMove < 0) {
			lastMoved = Direction.LEFT;
			return bossLeft.getCurrentFrame();
		} else if(xMove > 0) {
			lastMoved = Direction.RIGHT;
			return bossRight.getCurrentFrame();
		} else if(yMove < 0) {
			lastMoved = Direction.UP;
			return bossUp.getCurrentFrame();
		} else if(yMove > 0) {
			lastMoved = Direction.DOWN;
			return bossDown.getCurrentFrame();
		} else {
			if(lastMoved == Direction.LEFT)
				return Assets.boss_left[0];
			else if(lastMoved == Direction.RIGHT)
				return Assets.boss_right[0];
			else if(lastMoved == Direction.UP)
				return Assets.boss_up[0];
			else
				return Assets.boss_down[0];

		}
	}

	private BufferedImage getCurrentAttackAnimationFrame() {
		if(lastMoved == Direction.LEFT) {
			return bossLeftAttack.getCurrentFrame();
		}else if(lastMoved == Direction.RIGHT) {
			return bossRightAttack.getCurrentFrame();
		}else if(lastMoved == Direction.UP) {
			return bossUpAttack.getCurrentFrame();
		}else {
			return bossDownAttack.getCurrentFrame();
		}
	}

	@Override
	public void die() {
		active = false;
		handler.getWorld().setBossDefeated(true);
	}

	@Override
	public void render(Graphics g) {
		if ((flickerTimer % 15 < 5) && (flickerTimer != 0)) {

		} else {
			if(setAttackFrame > 0) {
				g.drawImage(getCurrentAttackAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
						(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
				setAttackFrame--;
			}else {
				g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
						(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			}
		}

	}
}