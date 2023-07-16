package dev.Rishi.tilegame.Entity.Creature;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.Entity.Entity;
import dev.Rishi.tilegame.Items.Item;
import dev.Rishi.tilegame.gfx.Assets;

public class Enemy extends Creature{

	Direction lastMoved = Direction.DOWN;

	private int direction;

	protected float xDelta, yDelta;

	protected boolean aggression;

	//used to determine movement time for enemy AI
	private long lastMovementTimer, movementCooldown = 1000, movementTimer = movementCooldown;

	protected long lastAttackTimer = 0, attackTimer = attackCooldown;
	
	private int itemId;


	public Enemy(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		//x and y tell us the starting position of our collision detection rectangle
		//width and height tell us the size of our collision detection rectangle
		bounds.x = 20;
		bounds.y = 40;
		bounds.width = 90;
		bounds.height = 80;
		//this is used to determine movement speed depending on game difficulty
		//other enemy types also have the same behaviour
		if(speedModifier) {
			this.speed = Creature.DEFAULT_SPEED - 1;
		} else {
			this.speed = Creature.DEFAULT_SPEED - 2;
		}
		ID =2;

	}

	@Override
	public void tick() {
		flickerImage();
		checkStunned();
		if (!stunned) {
			checkAggression();

			//Movement
			if (!aggression) {
				getInput();
				changeDirection();
				move();
			}

			//Attack
			trackPlayer();
			checkAttacks();
		}
	}

	//this overrides the enemy movements if it finds that a player is within
	//it's aggression range which changes depending on difficulty
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
			changeDirection();
			move();
		}

	}

	//finds the difference in x and y co-ordinates between player and enemy with respect to enemy and
	//decides if it's within aggression range or not
	protected void checkAggression() {
		xDelta = handler.getWorld().getEntityManager().getPlayer().getX() - this.getX();
		yDelta = handler.getWorld().getEntityManager().getPlayer().getY() - this.getY();

		if (((xDelta < aggressionRange) && (xDelta > -aggressionRange)) || ((yDelta < aggressionRange) 
				&& (yDelta > -aggressionRange))) {
			aggression = true;
		} else if (handler.getWorld().getEntityManager().getPlayer().getHealth() <= 0) {
			aggression = false;
		} else {
			aggression = false;
		}
	}

	//implements attack cooldowns which change with difficulty and 
	//attacks by creating new collision boxes in the direction the 
	//enemy faces
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
			} else if ((lastMoved == Direction.RIGHT) && (yDelta > 0)) {
				ar.x = cb.x + cb.width;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			}
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

	@Override
	public void render(Graphics g) {

		if ((flickerTimer % 15 < 5) && (flickerTimer != 0)) {

		} else {
			g.drawImage(Assets.drawer, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			}
	}

	private void changeDirection() {
		if(xMove < 0) {
			lastMoved = Direction.LEFT;
		} else if(xMove > 0) {
			lastMoved = Direction.RIGHT;
		} else if(yMove < 0) {
			lastMoved = Direction.UP;
		} else if(yMove > 0) {
			lastMoved = Direction.DOWN;
		}		
	}


	//randomly assigns item drops when regular enemies die
	@Override
	public void die() {
		active = false;

		itemId = (int)(Math.random() * 5 + 1);
		if(itemId == 1) {
			handler.getWorld().getItemManager().addItem(Item.noise.createNew((int) x+(Creature.DEFAULT_CREATURE_WIDTH/2),
					(int) y+(Creature.DEFAULT_CREATURE_HEIGHT/2)));
		} else if(itemId == 2) {
			handler.getWorld().getItemManager().addItem(Item.heart.createNew((int) x+(Creature.DEFAULT_CREATURE_WIDTH/2),
					(int) y+(Creature.DEFAULT_CREATURE_HEIGHT/2)));
		}
	}

	//AI random movement by periodically calling a random number in
	//a switch case format to decide which direction to move in
	//the enemies can also not move
	public void getInput() {
		xMove = 0;
		yMove = 0;

		getDirection();
		switch(direction) {
		case 1:
			yMove = speed;
			break;
		case 2:
			yMove = -speed;
			break;
		case 3:
			xMove = -speed;
			break;
		case 4:
			xMove = speed;
			break;
		default:
			break;
		}
	}

	public void getDirection() {
		movementTimer += System.currentTimeMillis() - lastMovementTimer;
		lastMovementTimer = System.currentTimeMillis();
		if(movementTimer < movementCooldown) {
			return;
		}
		direction = (int)(Math.random() * 5 + 1);
		movementTimer = 0;
	}

	@Override
	public void stun() {
	}

}
