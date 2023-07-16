package dev.Rishi.tilegame.state;

import java.awt.Color;
import java.awt.Graphics;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.UI.ClickListener;
import dev.Rishi.tilegame.UI.UIImageButton;
import dev.Rishi.tilegame.UI.UIManager;
import dev.Rishi.tilegame.gfx.Assets;
import dev.Rishi.tilegame.gfx.Text;


//state for letting player choose difficulty when playing the game, only for single player
public class DifficultySelect extends State{
	
	private UIManager uiManager;

	public DifficultySelect(final Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);
		
		uiManager.addObject(new UIImageButton(412, 309, 200, 75, Assets.button_hard, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUiManager(null);
				GameState game = new GameState(handler, false);
				handler.getWorld().setDifficulty(true);
				State.setState(game);
						
			}}));
		
		uiManager.addObject(new UIImageButton(412, 459, 200, 75, Assets.button_normal, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUiManager(null);
				GameState game = new GameState(handler, false);
				handler.getWorld().setDifficulty(false);
				State.setState(game);
						
			}}));

		uiManager.addObject(new UIImageButton(412, 600, 200, 75, Assets.button_back, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUiManager(null);
				MenuState menu = new MenuState(handler);
				State.setState(menu);
						
			}}));
	}

	@Override
	public void tick() {
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.background, 0, 0, 1024, 768, null);
		uiManager.render(g);
		Text.drawString(g, "Difficulty", 512, 100, true, Color.BLACK, 50);
	}

}
