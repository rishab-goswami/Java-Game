package dev.Rishi.tilegame.state;

import java.awt.Color;
import java.awt.Graphics;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.UI.ClickListener;
import dev.Rishi.tilegame.UI.UIImageButton;
import dev.Rishi.tilegame.UI.UIManager;
import dev.Rishi.tilegame.gfx.Assets;
import dev.Rishi.tilegame.gfx.Text;


//Game over state for when the player has been defeated, gives the player the choice to try again, quit or return to main menu
public class GameOverState extends State {

	private UIManager uiManager;

	public GameOverState(final Handler handler) {
		super(handler);
		handler.getGame().readFromFile();
		handler.getGame().sortHighScores();
		handler.getGame().checkNewHighScore(handler.getWorld().getScore().getScore());
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);


		uiManager.addObject(new UIImageButton(412, 400, 200, 75, Assets.button_tryAgain, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUiManager(null);
				if(handler.getWorld().isCo_Op()) {
					GameState gameState = new GameState(handler, true);
					State.setState(gameState);
				} else {
					GameState gameState = new GameState(handler, false);
					State.setState(gameState);
				}
			}}));

		uiManager.addObject(new UIImageButton(412, 490, 200, 75, Assets.button_main_menu, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUiManager(null);
				MenuState menuState = new MenuState(handler);
				State.setState(menuState);	
			}}));

		uiManager.addObject(new UIImageButton(412, 580, 200, 75, Assets.button_gameover_exit, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUiManager(null);
				System.exit(0);
			}}));
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.game_over, 0, 0, null);
		uiManager.render(g);
		//showing the score the player received before dying
		Text.drawString(g, "Your Score: "+Integer.toString(handler.getWorld().getScore().getScore()), 512, 350, true, Color.WHITE, 50);
	}

}
