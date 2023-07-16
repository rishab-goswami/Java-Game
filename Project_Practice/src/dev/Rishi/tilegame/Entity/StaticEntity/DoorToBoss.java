package dev.Rishi.tilegame.Entity.StaticEntity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.sfx.SoundAssets;

public class DoorToBoss extends StaticEntity{

	private BufferedImage texture;

	public DoorToBoss(Handler handler, float x, float y, int width, int height, BufferedImage texture) {
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

	//checks if player steps on top of teleportation tile
	//same logic is applied to all room changes with other 
	//respective tiles
	private void nextStage() {
		if(checkPlayerCollision(0f, 0f)) {
			SoundAssets.teleport.play();
			handler.getWorld().setBossWorldLoaded();
			handler.getWorld().inWorld(5);
			if(handler.getWorld().isCo_Op()) {
				handler.getWorld().loadBossWorld("Res/worldCoOp/bossWorld.txt",
						"Res/entities/bossEnemy.txt");
			} else {
				if(handler.getWorld().isHardMode()) {
					handler.getWorld().loadBossWorld("Res/worlds/bossWorld.txt",
							"Res/entities/bossEnemyHard.txt");
				} else {
					handler.getWorld().loadBossWorld("Res/worlds/bossWorld.txt",
							"Res/entities/bossEnemy.txt");
				}
			}
		}
	}

	@Override
	public void knockback(Direction direction) {
	}

}
