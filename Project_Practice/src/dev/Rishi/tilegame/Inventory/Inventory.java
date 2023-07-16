package dev.Rishi.tilegame.Inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.Items.Item;
import dev.Rishi.tilegame.gfx.Assets;
import dev.Rishi.tilegame.gfx.Text;

public class Inventory {

	private Handler handler;
	private ArrayList<Item> inventoryItems;

	private int invX = 250, invY = 50,
			invWidth = 130, invHeight = 60;

	public Inventory(Handler handler) {
		this.handler = handler;
		inventoryItems = new ArrayList<Item>();			
	}

	public void tick() {
	}

	//draws the inventory and items if they're picked up into the inventory
	public void render(Graphics g) {
		g.drawImage(Assets.inventory_screen, invX, invY, invWidth, invHeight, null);
		Text.drawString(g, "Sword", invX+25, invY, true, Color.white, 20);
		Text.drawString(g, "Key", invX+105, invY, true, Color.white, 20);

		int length = inventoryItems.size();
		for(int i =0; i < length; i++) {
			if(inventoryItems.get(i).getId() == 0) {
				g.drawImage(Assets.key, invX+85, invY+10, 40, 40, null);
			}
			if(inventoryItems.get(i).getId() == 3) {
				g.drawImage(Assets.sword, invX+5, invY+10, 40, 40, null);
			}
		}
	}


	//Inventory methods
	public void addItem(Item item) {
		for(Item i : inventoryItems) {
			if(i.getId() == item.getId()) {
				i.setCount(i.getCount() + 1);
				return;
			}
		}
		inventoryItems.add(item);
	}
	
	public int getItem(int id) {
		for(Item i : inventoryItems) {
			if(i.getId() == id) {
				return i.getCount();
				
			}
		}
		return 0;
	}

	//GETTERS AND SETTERS
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
}
