package dev.Rishi.tilegame.state;

import java.awt.Graphics;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.UI.ClickListener;
import dev.Rishi.tilegame.UI.UIImageButton;
import dev.Rishi.tilegame.UI.UIManager;
import dev.Rishi.tilegame.gfx.Assets;

//Main menu state that allows user to the game, high score, credits and allows the user to exit the game via
//button created below
public class MenuState extends State { 
	
	private UIManager uiManager;
	
	public MenuState(final Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);
		
		uiManager.addObject(new UIImageButton(150, 200, 200, 75, Assets.button_start, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUiManager(null);
				DifficultySelect game = new DifficultySelect(handler);
				State.setState(game);
				
			}}));
		
		uiManager.addObject(new UIImageButton(150, 290, 200, 75, Assets.button_coop, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUiManager(null);
				GameState game = new GameState(handler, true);
				handler.getWorld().setCo_Op(true);
				State.setState(game);
			}}));
		
		uiManager.addObject(new UIImageButton(150, 380, 200, 75, Assets.button_instructions, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUiManager(null);
				TutorialState tutorial = new TutorialState(handler);
				State.setState(tutorial);
			}}));
		
		uiManager.addObject(new UIImageButton(150, 470, 200, 75, Assets.button_high_score, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUiManager(null);
				HighScoresState highScores = new HighScoresState(handler);
				State.setState(highScores);
			}}));
		
		uiManager.addObject(new UIImageButton(150, 560, 200, 75, Assets.button_credits, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUiManager(null);
				CreditState credits = new CreditState(handler);
				State.setState(credits);
			}}));
		
		uiManager.addObject(new UIImageButton(150, 650, 200, 75, Assets.button_exit, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUiManager(null);
				System.exit(0);
				
			}}));
	}

	@Override
	public void tick() {
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.start_menu, 0, 0, null);
		uiManager.render(g);
		g.drawImage(Assets.player_down[0], 462, 200, 512, 512, null);
	}
}
