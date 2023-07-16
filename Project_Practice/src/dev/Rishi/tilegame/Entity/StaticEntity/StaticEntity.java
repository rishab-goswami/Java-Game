package dev.Rishi.tilegame.Entity.StaticEntity;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.Entity.Entity;

public abstract class StaticEntity extends Entity {

	public StaticEntity(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		ID = 1;
	}
	
}