package dev.Rishi.tilegame.Items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.Entity.StaticEntity.DoorToBoss;
import dev.Rishi.tilegame.Tiles.Tile;
import dev.Rishi.tilegame.gfx.Assets;
import dev.Rishi.tilegame.sfx.SoundAssets;

public class Item {
	
	//Handler
	
	public static Item[] items = new Item[256];
	public static Item key = new Item(Assets.key, "Key", 0);
	public static Item heart = new Item(Assets.heart, "Heart", 1);
	public static Item noise = new Item(Assets.noise, "Key", 2);
	public static Item sword = new Item(Assets.sword, "Sword", 3);
	// Class
	
	public static final int ITEMWIDTH = 64, ITEMHEIGHT = 64;
	
	protected Handler handler;
	protected BufferedImage texture;
	protected String name;
	protected final int id;
	
	protected Rectangle bounds;
	
	protected int x, y, count;
	protected boolean pickedUp = false;
	
	public Item(BufferedImage texture, String name, int id){
		this.texture = texture;
		this.name = name;
		this.id = id;
		count = 1;
		
		bounds = new Rectangle(x, y, ITEMWIDTH, ITEMHEIGHT);
		
		items[id] = this;
	}
	
	//checking if a player is colliding with an item and picking it up if they are
	//also keeps track of what item was picked up and calls their effects
	public void tick(){
		if((id == 0) && 
				handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
			pickedUp = true;
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
			handler.getWorld().getEntityManager().addEntity(new DoorToBoss(handler, 1000,
						1000, Tile.TILEWIDTH, Tile.TILEHEIGHT, Assets.blue_mat));
			SoundAssets.pickUp.play();
		} else if((id == 1) && 
				handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
			pickedUp = true;
			handler.getWorld().getEntityManager().getPlayer().increaseHealth(1);
			SoundAssets.pickUp.play();
		} else if((id == 2) &&
				handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
			pickedUp = true;
			handler.getWorld().getEntityManager().getPlayer().decreaseNoise();
			SoundAssets.pickUp.play();
		} else if((id == 3) &&
				handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
			pickedUp = true;
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
		}
	}
	
	public void render(Graphics g){
		if(handler == null)
			return;
		render(g, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
	}
	
	//for testing purposes
	public Item createNew(int count){
		Item i = new Item(texture, name, id);
		i.setPickedUp(true);
		i.setCount(count);
		return i;
	}
	
	public Item createNew(int x, int y){
		Item i = new Item(texture, name, id);
		i.setPosition(x, y);
		return i;
	}

	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
		bounds.x = x;
		bounds.y = y;
	}
	
	// Getters and Setters
	
	public Handler getHandler() {
		return handler;
	}	
	
	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

}
