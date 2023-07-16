package dev.Rishi.tilegame.Entity.Creature;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.Entity.Entity;
import dev.Rishi.tilegame.gfx.Assets;

public class StunEnemy extends Enemy{

	public StunEnemy(Handler handler, float x, float y) {
		super(handler, x, y);
		bounds.x = 30;
		bounds.y = 50;
		bounds.width = 60;
		bounds.height = 64;
		this.speed = 3.0f;
		ID = 3;
	}
	
	@Override
	protected void checkAttacks() {
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
		
		if (aggression) {
			if ((lastMoved == Direction.UP) && (yDelta < 0)) {
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y - arSize;
			} else if ((lastMoved == Direction.DOWN) && (yDelta > 0)) {
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y + cb.height;
			} else if ((lastMoved == Direction.LEFT) && (xDelta < 0)) {
				ar.x = cb.x -arSize;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			} else if ((lastMoved == Direction.RIGHT) && (xDelta > 0)) {
				ar.x = cb.x + cb.width;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			}
		}

		attackTimer = 0;

		//attacks are the same as the basic melee enemy however this enemy type also invokes
		//a stun lock on the entity that it hits (other enemies included)
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0, 0).intersects(ar)) {
				e.stun();
				e.hurt(1, lastMoved);
				return;
			}
		}
	}
	
	@Override
	public void stun() {
		stunned = true;
	}
	
	@Override
	public void render(Graphics g) {
		if ((flickerTimer % 15 < 5) && (flickerTimer != 0)) {
			
		} else {
		g.drawImage(Assets.pot, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		}
	}

}
