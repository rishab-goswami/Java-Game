package dev.Rishi.tilegame.worlds;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.Score;
import dev.Rishi.tilegame.Entity.Entity;
import dev.Rishi.tilegame.Entity.EntityManager;
import dev.Rishi.tilegame.Entity.Projectiles;
import dev.Rishi.tilegame.Entity.Creature.BossEnemy;
import dev.Rishi.tilegame.Entity.Creature.BossUnlocker;
import dev.Rishi.tilegame.Entity.Creature.Enemy;
import dev.Rishi.tilegame.Entity.Creature.Player;
import dev.Rishi.tilegame.Entity.Creature.Player2;
import dev.Rishi.tilegame.Entity.Creature.RangedEnemy;
import dev.Rishi.tilegame.Entity.Creature.StunEnemy;
import dev.Rishi.tilegame.Entity.StaticEntity.DoorToWorld1;
import dev.Rishi.tilegame.Entity.StaticEntity.DoorToWorld2;
import dev.Rishi.tilegame.Entity.StaticEntity.DoorToWorld3;
import dev.Rishi.tilegame.Entity.StaticEntity.DoorToWorld4;
import dev.Rishi.tilegame.Entity.StaticEntity.Furniture;
import dev.Rishi.tilegame.Items.Item;
import dev.Rishi.tilegame.Items.ItemManager;
import dev.Rishi.tilegame.Tiles.Tile;
import dev.Rishi.tilegame.gfx.Assets;
import dev.Rishi.tilegame.utilities.Utilities;

public class World {

	private Handler handler;
	//width and height describes number of tiles 
	private int width, height;
	//spawn X,Y spawn position of player, X2,Y2 position of player 2
	private int spawnX, spawnY, spawnX2, spawnY2;
	private int[][] tiles;
	//Entities
	private EntityManager entityManager;
	//Items
	private ItemManager itemManager;

	private int enemyId, furnitureId, doorId; 

	private boolean bossDefeated = false;

	private Score score;
	private int loadedWorld;

	public boolean co_Op = false;
	private boolean world1Loaded = true, world2Loaded = false, world3Loaded = false,
			world4Loaded = false, bossWorldLoaded = false, inWorld1 = true, inWorld2 = false,
			inWorld3 = false, inWorld4 = false, inBossWorld = false;

	private boolean hardMode;

	public World(Handler handler) {
		this.handler = handler;
		entityManager = new EntityManager(handler, new Player(handler, 100, 100));
		itemManager = new ItemManager(handler);

		score = new Score(handler);
		itemManager.addItem(Item.sword.createNew(900, 1300));

	}

	public void tick() {
		itemManager.tick();
		entityManager.tick();
		score.IncrementScore();
	}
	//destroys all of the projectiles so they don't transition into next room with player
	public void destroyProjectiles() {
		Iterator<Projectiles> itP = entityManager.getProjectiles().iterator();
		while(itP.hasNext()) {
			Entity p = itP.next();
			if(p != entityManager.getPlayer())
				itP.remove();
		}

	}

	public void render(Graphics g) {
		int xStart = (int) Math.max(0, (handler.getGameCamera().getxOffset()/Tile.TILEWIDTH));
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth())/ Tile.TILEWIDTH + 1);
		int yStart = (int) Math.max(0, (handler.getGameCamera().getyOffset()/Tile.TILEHEIGHT));
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight())/ Tile.TILEHEIGHT + 1);

		//having the y for loop is important
		//yStart is the tile the user can see on the top, yEnd is the tile the use can see at the bottom
		for(int y = yStart; y < yEnd; y++) { 
			//xStart is the tile the user can currently see at left, xEnd is the tile the user can see on the right
			for(int x = xStart; x < xEnd; x++) {
				//getTile uses a tile coordinate where as render uses pixel
				//coordinates, therefore we need to multiple by our tile width and height
				getTile(x,y).render(g, (int) (x*Tile.TILEWIDTH - handler.getGameCamera().getxOffset()),
						(int) (y*Tile.TILEWIDTH - handler.getGameCamera().getyOffset()));
			}
		}

		//Items
		itemManager.render(g);

		//entities
		entityManager.render(g);


	}

	public Tile getTile(int x, int y) {
		//if parameters outside the map, tile is a wood tile. 
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return Tile.woodTile;
		}

		Tile t = Tile.tiles[tiles[x][y]];
		//if tile id doesn't exist, return a wood tile
		if(t == null) {
			return Tile.woodTile;
		}
		return t;
	}

	//Loads world by loading 4 different text files from resources. Method firstly loads file as string
	// for first text file, the first 2 numbers describe the tile width and height of world in tile
	// then each number represents the type of tile, for the next three file, the text files describe the position of
	//the specific entity, but the type of entity is randomized.
	//at the end, there is a possibility that the boss unlocker enemy is spawned (the chance decreases the more you explore)
	//if playing CoOp, loads in the data for player2 spawn as well.
	public void loadWorld(String path, String pathEnemy, String pathFurniture, String pathDoor) {
		loadedWorld++;
		destroyProjectiles();
		String file = Utilities.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utilities.parseInt(tokens[0]);
		height = Utilities.parseInt(tokens[1]);
		spawnX = Utilities.parseInt(tokens[2]);
		spawnY = Utilities.parseInt(tokens[3]);
		if(co_Op) {
			spawnX2 = Utilities.parseInt(tokens[4]);
			spawnY2 = Utilities.parseInt(tokens[5]);
		}

		tiles = new int[width][height];

		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(co_Op) {
					tiles[x][y] = Utilities.parseInt(tokens[(x + y * width)+6]);
				} else {
					tiles[x][y] = Utilities.parseInt(tokens[(x + y * width)+4]);
				}
			}
		}

		ArrayList<Entity> tempEntities = new ArrayList<Entity>();
		tempEntities.add(entityManager.getPlayer());
		String enemies = Utilities.loadFileAsString(pathEnemy);
		String[] enemyTokens = enemies.split("\\s+");

		for(int i = 0; i < enemyTokens.length - 1; i = i + 2) {
			enemyId = (int)(Math.random() * 4 + 1);
			if(enemyId == 1) { 
				tempEntities.add(new Enemy(handler, Utilities.parseInt(enemyTokens[i]),
						Utilities.parseInt(enemyTokens[i+1])));
			} else if(enemyId == 2) {
				tempEntities.add(new RangedEnemy(handler, Utilities.parseInt(enemyTokens[i]),
						Utilities.parseInt(enemyTokens[i+1])));
			} else if(enemyId == 3) {
				tempEntities.add(new StunEnemy(handler, Utilities.parseInt(enemyTokens[i]),
						Utilities.parseInt(enemyTokens[i+1])));
			} 
		}

		String Furniture = Utilities.loadFileAsString(pathFurniture);
		String[] furnitureTokens = Furniture.split("\\s+");
		for(int i = 0; i < furnitureTokens.length - 1; i = i + 2) {
			furnitureId = (int)(Math.random() * 15 + 1);
			if(furnitureId == 1) { 
				tempEntities.add(new Furniture(handler, Utilities.parseInt(furnitureTokens[i]),
						Utilities.parseInt(furnitureTokens[i+1]), Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.blue_locker));
			} else if(furnitureId == 2) {
				tempEntities.add(new Furniture(handler, Utilities.parseInt(furnitureTokens[i]),
						Utilities.parseInt(furnitureTokens[i+1]), Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.computer_desk));
			} else if(furnitureId == 3) {
				tempEntities.add(new Furniture(handler, Utilities.parseInt(furnitureTokens[i]),
						Utilities.parseInt(furnitureTokens[i+1]), Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.bed));
			}  else if(furnitureId == 4) {
				tempEntities.add(new Furniture(handler, Utilities.parseInt(furnitureTokens[i]), Utilities.parseInt(furnitureTokens[i+1]),
						Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.gray_closet));
			}  else if(furnitureId == 5) {
				tempEntities.add(new Furniture(handler, Utilities.parseInt(furnitureTokens[i]),
						Utilities.parseInt(furnitureTokens[i+1]), Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.brown_closet));
			}  else if(furnitureId == 6) {
				tempEntities.add(new Furniture(handler, Utilities.parseInt(furnitureTokens[i]),
						Utilities.parseInt(furnitureTokens[i+1]), Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.lamp_stool));
			}  else if(furnitureId == 7) {
				tempEntities.add(new Furniture(handler, Utilities.parseInt(furnitureTokens[i]),
						Utilities.parseInt(furnitureTokens[i+1]), Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.stool));
			}  else if(furnitureId == 8) {
				tempEntities.add(new Furniture(handler, Utilities.parseInt(furnitureTokens[i]),
						Utilities.parseInt(furnitureTokens[i+1]), Tile.TILEWIDTH/2, Tile.TILEHEIGHT/2, Assets.seat));
			}  else if(furnitureId == 9) {
				tempEntities.add(new Furniture(handler, Utilities.parseInt(furnitureTokens[i]),
						Utilities.parseInt(furnitureTokens[i+1]), Tile.TILEWIDTH/2, Tile.TILEHEIGHT/2, Assets.seat_2));
			}  else if(furnitureId == 10) {
				tempEntities.add(new Furniture(handler, Utilities.parseInt(furnitureTokens[i]),
						Utilities.parseInt(furnitureTokens[i+1]), Tile.TILEWIDTH/2, Tile.TILEHEIGHT/2, Assets.radio));
			}  else if(furnitureId == 11) {
				tempEntities.add(new Furniture(handler, Utilities.parseInt(furnitureTokens[i]),
						Utilities.parseInt(furnitureTokens[i+1]), Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.bench));
			}  else if(furnitureId == 12) {
				tempEntities.add(new Furniture(handler, Utilities.parseInt(furnitureTokens[i]),
						Utilities.parseInt(furnitureTokens[i+1]), Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.pot));
			}  else if(furnitureId == 13) {
				tempEntities.add(new Furniture(handler, Utilities.parseInt(furnitureTokens[i]),
						Utilities.parseInt(furnitureTokens[i+1]), Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.pot_plant));
			}  else if(furnitureId == 14) {
				tempEntities.add(new Furniture(handler, Utilities.parseInt(furnitureTokens[i]),
						Utilities.parseInt(furnitureTokens[i+1]), Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.drawer));
			}  else if(furnitureId == 15) {
				tempEntities.add(new Furniture(handler, Utilities.parseInt(furnitureTokens[i]),
						Utilities.parseInt(furnitureTokens[i+1]), Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.drawer_2));
			}
		}

		String doors = Utilities.loadFileAsString(pathDoor);
		String[] doorTokens = doors.split("\\s+");

		for(int i = 0; i < doorTokens.length - 1; i = i + 2) {
			doorId = (int)(Math.random() * 4 + 1);
			if(doorId == 1) { 
				tempEntities.add(new DoorToWorld1(handler, Utilities.parseInt(doorTokens[i]),
						Utilities.parseInt(doorTokens[i+1]), Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.pink_mat));
			} else if(doorId == 2) {
				tempEntities.add(new DoorToWorld2(handler, Utilities.parseInt(doorTokens[i]),
						Utilities.parseInt(doorTokens[i+1]), Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.pink_mat));
			} else if(doorId == 3) {
				tempEntities.add(new DoorToWorld3(handler, Utilities.parseInt(doorTokens[i]),
						Utilities.parseInt(doorTokens[i+1]), Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.pink_mat));
			} else if(doorId == 4) {
				tempEntities.add(new DoorToWorld4(handler, Utilities.parseInt(doorTokens[i]),
						Utilities.parseInt(doorTokens[i+1]), Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.pink_mat));
			}
		}

		int BossUnlockId = (int)(Math.random() * (50/loadedWorld) + 1);	

		if(BossUnlockId == 1) {
			tempEntities.add(new BossUnlocker(handler, 900, 900));
		}
		if(co_Op)
			tempEntities.add(new Player2(handler, spawnX2, spawnY2));

		entityManager.setEntities(tempEntities);
		if(BossUnlockId ==1) {
			entityManager.setSolid();
		}
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);

		if(hardMode) {
			entityManager.setAttackCooldown(1500);
			entityManager.setSpeed(true);
			entityManager.setAggressionRange(300);
		} else {
			entityManager.setAttackCooldown(3000);
			entityManager.setSpeed(false);
			entityManager.setAggressionRange(200);
		}
	}
	//works the same as the loadworld except it only load the world and boss, no other entities.
	public void loadBossWorld(String path, String pathEnemy) {
		destroyProjectiles();
		String file = Utilities.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utilities.parseInt(tokens[0]);
		height = Utilities.parseInt(tokens[1]);
		spawnX = Utilities.parseInt(tokens[2]);
		spawnY = Utilities.parseInt(tokens[3]);
		if(co_Op) {
			spawnX2 = Utilities.parseInt(tokens[4]);
			spawnY2 = Utilities.parseInt(tokens[5]);
		}

		tiles = new int[width][height];

		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(co_Op) {
					tiles[x][y] = Utilities.parseInt(tokens[(x + y * width)+6]);
				} else {
					tiles[x][y] = Utilities.parseInt(tokens[(x + y * width)+4]);
				}
			}
		}

		ArrayList<Entity> tempEntities = new ArrayList<Entity>();
		tempEntities.add(entityManager.getPlayer());
		String enemies = Utilities.loadFileAsString(pathEnemy);
		String[] enemyTokens = enemies.split("\\s+");

		for(int i = 0; i < enemyTokens.length - 1; i = i + 2) {
			tempEntities.add(new BossEnemy(handler, Utilities.parseInt(enemyTokens[i]),
					Utilities.parseInt(enemyTokens[i+1])));
		}

		if(co_Op)
			tempEntities.add(new Player2(handler, spawnX2, spawnY2));

		entityManager.setEntities(tempEntities);
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);

		if(hardMode) {
			entityManager.setAttackCooldown(1500);
			entityManager.setSpeed(true);
			entityManager.setAggressionRange(300);
		} else {
			entityManager.setAttackCooldown(3000);
			entityManager.setSpeed(false);
			entityManager.setAggressionRange(200);
		}
	}

	//sets whether respective world has been explored yet, used for displaying map in hud
	public void setWorld1Loaded() {
		world1Loaded = true;
	}

	public void setWorld2Loaded() {
		world2Loaded = true;
	}

	public void setWorld3Loaded() {
		world3Loaded = true;
	}

	public void setWorld4Loaded() {
		world4Loaded = true;
	}

	public void setBossWorldLoaded() {
		bossWorldLoaded = true;
	}

	public boolean isWorld1Loaded() {
		return world1Loaded;
	}

	public boolean isWorld2Loaded() {
		return world2Loaded;
	}

	public boolean isWorld3Loaded() {
		return world3Loaded;
	}

	public boolean isWorld4Loaded() {
		return world4Loaded;
	}

	public boolean isBossWorldLoaded() {
		return bossWorldLoaded;
	}

	//tells hud which world the player is currently in
	public void inWorld(int Id) {
		if(Id == 1) {
			inWorld1 = true;
			inWorld2 = false;
			inWorld3 = false;
			inWorld4 = false;
			inBossWorld = false;
		} else if(Id == 2) {
			inWorld1 = false;
			inWorld2 = true;
			inWorld3 = false;
			inWorld4 = false;
			inBossWorld = false;
		} else if(Id == 3) {
			inWorld1 = false;
			inWorld2 = false;
			inWorld3 = true;
			inWorld4 = false;
			inBossWorld = false;
		} else if(Id == 4) {
			inWorld1 = false;
			inWorld2 = false;
			inWorld3 = false;
			inWorld4 = true;
			inBossWorld = false;
		} else if(Id == 5) {
			inWorld1 = false;
			inWorld2 = false;
			inWorld3 = false;
			inWorld4 = false;
			inBossWorld = true;
		}
	}
	//sets which world the player is currently in
	public boolean isInWorld1() {
		return inWorld1;
	}

	public boolean isInWorld2() {
		return inWorld2;
	}

	public boolean isInWorld3() {
		return inWorld3;
	}

	public boolean isInWorld4() {
		return inWorld4;
	}

	public boolean isInBossWorld() {
		return inBossWorld;		
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Score getScore() {
		return score;
	}


	public void setDifficulty(boolean hard) {
		this.hardMode = hard;
	}

	public void setBossDefeated(boolean defeated) {
		this.bossDefeated = defeated;
	}

	public boolean isBossDefeated() {
		return bossDefeated;
	}

	public boolean isCo_Op() {
		return co_Op;
	}

	public void setCo_Op(boolean co_Op) {
		this.co_Op = co_Op;
	}

	public boolean isHardMode() {
		return hardMode;
	}


}
