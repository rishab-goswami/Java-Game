package dev.Rishi.tilegame.gfx;

import java.awt.image.BufferedImage;

//animations class used for when any specific entity moves, attacks, interacts in anyway.
public class Animation {
	
	private int speed, index;
	private long lastTime, timer;
	private BufferedImage[] frames;
	
	
	public Animation(int speed, BufferedImage[] frames) {
		this.speed = speed;
		this.frames = frames;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}
	//ticks between the animation frames the animation has, which can then be rendered onto the screen
	public void tick() {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
	
		if(timer > speed) {
			index++;
			timer = 0;
			if(index >= frames.length) {
				index = 0;
			}
		}

	}
	
	public BufferedImage getCurrentFrame() {
		return frames[index];
	}
	

}
