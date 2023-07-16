package dev.Rishi.tilegame.Entity.StaticEntity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.sfx.SoundAssets;

public class DoorToWorld1 extends StaticEntity{

	private BufferedImage texture;

	public DoorToWorld1(Handler handler, float x, float y, int width, int height, BufferedImage texture) {
		super(handler, x, y, width, height);
		this.texture = texture;
		setSolid(false);
	}

	@Override
	public void tick() {
		nextStage();

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(texture, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

	@Override
	public void die() {
		health = 10;
		active = true;
	}

	@Override
	public void stun() {
	}

	private void nextStage() {
		if(checkPlayerCollision(0f, 0f)) {
			SoundAssets.teleport.play();
			handler.getWorld().setWorld1Loaded();
			handler.getWorld().inWorld(1);
			if(handler.getWorld().isCo_Op()) {
				handler.getWorld().loadWorld("Res/worldCoOp/world1.txt",
						"Res/entities/world1Enemies.txt",
						"Res/entities/world1Furniture.txt", "Res/entities/world1Doors.txt");
			} else {
				handler.getWorld().loadWorld("Res/worlds/world1.txt",
						"Res/entities/world1Enemies.txt",
						"Res/entities/world1Furniture.txt", "Res/entities/world1Doors.txt");
			}
			
		}
	}

	@Override
	public void knockback(Direction direction) {
	}

}
