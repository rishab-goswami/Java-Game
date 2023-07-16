package dev.Rishi.tilegame.state;

import java.awt.Color;
import java.awt.Graphics;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.UI.ClickListener;
import dev.Rishi.tilegame.UI.UIImageButton;
import dev.Rishi.tilegame.UI.UIManager;
import dev.Rishi.tilegame.gfx.Assets;
import dev.Rishi.tilegame.gfx.Text;

public class WinState extends State{
	
	private UIManager uiManager;

	//state that displays the score when game has been won. 
	public WinState(final Handler handler) {
		super(handler);
		handler.getGame().readFromFile();
		handler.getGame().sortHighScores();
		handler.getGame().checkNewHighScore(handler.getWorld().getScore().getScore());
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);
		
		//object that takes you back to main menu
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
		g.drawImage(Assets.background, 0, 0, null);
		uiManager.render(g);
		Text.drawString(g, "You Win!", 512, 100, true, Color.BLACK, 50);
		Text.drawString(g, "Your Score: "+Integer.toString(handler.getWorld().getScore().getScore()), 512, 384, true, Color.BLACK, 30);
	}

}
