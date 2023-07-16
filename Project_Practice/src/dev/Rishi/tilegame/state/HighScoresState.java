package dev.Rishi.tilegame.state;

import java.awt.Color;
import java.awt.Graphics;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.UI.ClickListener;
import dev.Rishi.tilegame.UI.UIImageButton;
import dev.Rishi.tilegame.UI.UIManager;
import dev.Rishi.tilegame.gfx.Assets;
import dev.Rishi.tilegame.gfx.Text;

public class HighScoresState extends State{

	private UIManager uiManager;

	//State that displays the highscore
	public HighScoresState(final Handler handler) {
		super(handler);
		//reading the highscore from a text file
		handler.getGame().readFromFile();
		handler.getGame().readHighScores();
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);

		//back button
		uiManager.addObject(new UIImageButton(412, 600, 200, 75, Assets.button_back, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUiManager(null);
				MenuState menu = new MenuState(handler);
				State.setState(menu);

			}}));
	}

	//rendering the three high scores onto the screen
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.background, 0, 0, null);
		uiManager.render(g);
		Text.drawString(g, "High Scores", 512, 100, true, Color.BLACK, 50);
		Text.drawString(g, handler.getGame().getHighScoreName1(), 400, 300, true, Color.BLACK, 30);
		Text.drawString(g, Integer.toString(handler.getGame().getHighScore1()), 600 , 300, true, Color.BLACK, 30);
		Text.drawString(g, handler.getGame().getHighScoreName2(), 400, 350, true, Color.BLACK, 30);
		Text.drawString(g, Integer.toString(handler.getGame().getHighScore2()), 600 , 350, true, Color.BLACK, 30);
		Text.drawString(g, handler.getGame().getHighScoreName3(), 400, 400, true, Color.BLACK, 30);
		Text.drawString(g, Integer.toString(handler.getGame().getHighScore3()), 600 , 400, true, Color.BLACK, 30);
	}

	@Override
	public void tick() {
		uiManager.tick();

	}

	
}
