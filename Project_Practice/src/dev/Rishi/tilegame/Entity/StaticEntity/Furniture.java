package dev.Rishi.tilegame.Entity.StaticEntity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.Rishi.tilegame.Handler;

public class Furniture extends StaticEntity {
	
	private BufferedImage texture;

	public Furniture(Handler handler, float x, float y, int width, int height, BufferedImage texture) {
		super(handler, x, y, width, height);
		this.texture = texture;
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(texture, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

	@Override
	public void die() {
		active = false;
	}

	@Override
	public void stun() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void knockback(Direction direction) {
		// TODO Auto-generated method stub
		
	}

}
