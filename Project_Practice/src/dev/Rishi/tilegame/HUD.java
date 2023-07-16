package dev.Rishi.tilegame;

import java.awt.Color;
import java.awt.Graphics;

import dev.Rishi.tilegame.gfx.Assets;
import dev.Rishi.tilegame.gfx.Text;
import dev.Rishi.tilegame.state.GameOverState;
import dev.Rishi.tilegame.state.State;

public class HUD {
	
	private int[] time = new int[3];

	private Handler handler;
	public HUD(Handler handler) {
		this.handler = handler;
	}
	
	
	public void tick() {
		
	}
	
	
	//Implementation of our timer system inside the game, it is created here since the
	//HUD stops ticking during the pause states. When a game state is created they are
	//given 5 minutes and that is inputed as 3 elements into an array, if all 3 elements
	//hit 0 it calls the game over state
	public void timer (int minutes, int tensSeconds, int onesSeconds) {
		time[0] = minutes;
		time[1] = tensSeconds;
		time[2] = onesSeconds;
	}
	
	public void timerTick() {
		countDown();
	}

	public void countDown() {
		if ((time[2] == 0) && (time[1] != 0)) {
			time[2] = 9;
			time[1]--;
		}else if ((time[2] == 0) && (time[1] == 0) && (time[0] != 0)) {
			time[2] = 9;
			time[1] = 5;
			time[0]--;
		} else if ((time[2] == 0) && (time[1] == 0) && (time[0] == 0)) {
			GameOverState gameOverState = new GameOverState(handler);
			State.setState(gameOverState);
		} else {
			time[2]--;
		}
	}
	
	public int getMinutes() {
		return time[0];
	}
	
	public int getTensSeconds() {
		return time[1];
	}
	
	public int getOnesSeconds() {
		return time[2];
	}
	
	public void render(Graphics g) {
		
		//rendering our HUD
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Launcher.getLauncherWidth(), 150);
		
		//drawing our timer
		Text.drawString(g, "Time:", 440, 50, true, Color.white, 20);
		Text.drawString(g, Integer.toString(getMinutes())+":"+Integer.toString(getTensSeconds())+Integer.toString(getOnesSeconds()),
				512, 50, true, Color.WHITE, 20);
		
		//logic for rendering health
		Text.drawString(g, "Hearts", 90, 50, true, Color.white, 20);
		if(handler.getWorld().getEntityManager().getPlayer().getHealth() == 10) {
			g.drawImage(Assets.hearts[0], 10, 45, null);
		} else if (handler.getWorld().getEntityManager().getPlayer().getHealth() == 9) {
			g.drawImage(Assets.hearts[1], 10, 45, null);
		} else if (handler.getWorld().getEntityManager().getPlayer().getHealth() == 8) {
			g.drawImage(Assets.hearts[2], 10, 45, null);
		} else if (handler.getWorld().getEntityManager().getPlayer().getHealth() == 7) {
			g.drawImage(Assets.hearts[3], 10, 45, null);
		} else if (handler.getWorld().getEntityManager().getPlayer().getHealth() == 6) {
			g.drawImage(Assets.hearts[4], 10, 45, null);
		} else if (handler.getWorld().getEntityManager().getPlayer().getHealth() == 5) {
			g.drawImage(Assets.hearts[5], 10, 45, null);
		} else if (handler.getWorld().getEntityManager().getPlayer().getHealth() == 4) {
			g.drawImage(Assets.hearts[6], 10, 45, null);
		} else if (handler.getWorld().getEntityManager().getPlayer().getHealth() == 3) {
			g.drawImage(Assets.hearts[7], 10, 45, null);
		} else if (handler.getWorld().getEntityManager().getPlayer().getHealth() == 2) {
			g.drawImage(Assets.hearts[8], 10, 45, null);
		} else if (handler.getWorld().getEntityManager().getPlayer().getHealth() == 1) {
			g.drawImage(Assets.hearts[9], 10, 45, null);
		} else if (handler.getWorld().getEntityManager().getPlayer().getHealth() == 0) {
			g.drawImage(Assets.hearts[10], 10, 45, null);
		}
		
		
		//logic for rendering noise
		Text.drawString(g, "Noise Meter", 90, 115, true, Color.white, 20);
		if(handler.getWorld().getEntityManager().getPlayer().getNoise() == 0) {
			g.drawImage(Assets.noisebar[0], 10, 110, null);
		} else if (handler.getWorld().getEntityManager().getPlayer().getNoise() == 1) {
			g.drawImage(Assets.noisebar[1], 10, 110, null);
		} else if (handler.getWorld().getEntityManager().getPlayer().getNoise() == 2) {
			g.drawImage(Assets.noisebar[2], 10, 110, null);
		} else if (handler.getWorld().getEntityManager().getPlayer().getNoise() == 3) {
			g.drawImage(Assets.noisebar[3], 10, 110, null);
		} else if (handler.getWorld().getEntityManager().getPlayer().getNoise() == 4) {
			g.drawImage(Assets.noisebar[4], 10, 110, null);
		} else if (handler.getWorld().getEntityManager().getPlayer().getNoise() == 5) {
			g.drawImage(Assets.noisebar[5], 10, 110, null);
		}
		
		
		//drawing our score
		Text.drawString(g, "Score:", 460, 90, true, Color.white, 20);
		Text.drawString(g, Integer.toString(handler.getWorld().getScore().getScore()), 540, 90, true, Color.white, 20);
		
		//logic for our map and drawing which room our player is in
		Text.drawString(g, "Map", 730, 50, true, Color.white, 20);
		g.setColor(Color.WHITE);
		g.drawRect(700, 45, 300, 100);
		
		if(handler.getWorld().isWorld1Loaded()) {
			g.drawRect(730, 80, 40, 25);
		}
		if(handler.getWorld().isWorld2Loaded()) {
			g.drawRect(825, 50, 40, 25);
		}
		if(handler.getWorld().isWorld3Loaded()) {
			g.drawRect(920, 80, 40, 25);
		}
		if(handler.getWorld().isWorld4Loaded()) {
			g.drawRect(825, 115, 40, 25);
		}
		if(handler.getWorld().isBossWorldLoaded()) {
			g.drawRect(825, 80, 40, 25);
		}
		
		if(handler.getWorld().isInWorld1()) {
			g.drawImage(Assets.player_down[0], 740, 85, 20, 15, null);
		}
		if(handler.getWorld().isInWorld2()) {
			g.drawImage(Assets.player_down[0], 835, 55, 20, 15, null);
		}
		if(handler.getWorld().isInWorld3()) {
			g.drawImage(Assets.player_down[0], 930, 85, 20, 15, null);
		}
		if(handler.getWorld().isInWorld4()) {
			g.drawImage(Assets.player_down[0], 835, 120, 20, 15, null);
		}
		if(handler.getWorld().isInBossWorld()) {
			g.drawImage(Assets.player_down[0], 835, 85, 20, 15, null);
		}
	}
}
