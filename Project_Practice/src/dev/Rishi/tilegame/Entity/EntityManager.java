package dev.Rishi.tilegame.Entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.Entity.Creature.Player;

public class EntityManager {

	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	private ArrayList<Projectiles> projectiles;
	private Comparator<Entity> renderSorter = new Comparator<Entity>(){
		@Override
		public int compare(Entity a, Entity b) {
			//-1 if a rendered before b, 1 if a rendered after b
			if(a.getY() + a.getHeight() < b.getY() + b.getHeight()) {
				return -1;
			}
			return 1;
		}
	};
	private int deathCount =0;

	public EntityManager(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		projectiles = new ArrayList<Projectiles>();
		addEntity(player);

	}

	//uses the enemy ID to identify which enemy has died at each tick
	//and increments death count accordingly
	//this method is also used to tick entities and projectiles
	public void tick(){
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()) {
			Entity e = it.next();
			e.tick();
			if(!e.isActive()) {
				it.remove();
				if(e != player) {
					if(e.getID() == 1) {
						deathCount++;
					} else if(e.getID() == 2) {
						deathCount = deathCount + 2;
					} else if(e.getID() == 3) {
						deathCount = deathCount + 3;
					} else if(e.getID() == 4) {
						deathCount = deathCount + 4;
					} else if(e.getID() == 5) {
						deathCount = deathCount + 10;
					} else if(e.getID() == 6) {
						deathCount = deathCount + 30;
					}
				}
			}
		}
		entities.sort(renderSorter);
		Iterator<Projectiles> it2 = projectiles.iterator();
		while(it2.hasNext()) {
			Projectiles p = it2.next();
			p.tick();
			if(!p.isActive()) {
				it2.remove();
			}
		}
		projectiles.sort(renderSorter);
	}

	public void render(Graphics g) {
		//creates an entity called e and loop through all of them
		for(Entity e : entities) {
			e.render(g);
		}
		player.postRender(g);
		for(Projectiles p : projectiles) {
			p.render(g);
		}
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	public void addProjectile(Projectiles p) {
		projectiles.add(p);
	}


	//GETTERS AND SETTERS
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

	public ArrayList<Projectiles> getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(ArrayList<Projectiles> projectiles) {
		this.projectiles = projectiles;
	}

	//used to prevent player from leaving room once mini boss is spawned
	public void setSolid() {
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()) {
			Entity e = it.next();
			e.setSolid(true);;
		}
	}

	public int getDeathCount() {
		return deathCount;
	}
	
	//sets parameters of enemy entities for different difficulty modes
	public void setSpeed(boolean speedModifier) {
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()) {
			Entity e = it.next();
			if(!e.equals(player)) {
				e.setSpeed(speedModifier);
			}
		}
	}
	
	public void setAttackCooldown(long cooldown) {
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()) {
			Entity e = it.next();
			if(!e.equals(player)) {
				e.setAttackCooldown(cooldown);
			}
		}
	}
	
	public void setAggressionRange(int range) {
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()) {
			Entity e = it.next();
			if(!e.equals(player)) {
				e.setAggressionRange(range);
			}
		}
	}
	
}
