package dev.Rishi.tilegame.state;

import java.awt.Color;
import java.awt.Graphics;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.UI.ClickListener;
import dev.Rishi.tilegame.UI.UIImageButton;
import dev.Rishi.tilegame.UI.UIManager;
import dev.Rishi.tilegame.gfx.Assets;
import dev.Rishi.tilegame.gfx.Text;

public class TutorialState extends State{
	
	private UIManager uiManager;
	
	//A state describing the game controls and objectives
	public TutorialState(final Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);
		
		//UI button taking user back to main menu
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
		Text.drawString(g, "How To Play", 512, 100, true, Color.BLACK, 50);
		Text.drawString(g, "Player 1:", 50, 150, false, Color.BLACK, 30);
		Text.drawString(g, "Use W,A,S,D to move and Spacebar to attack", 50, 180, false, Color.BLACK, 20);
		Text.drawString(g, "Player 2:", 50, 230, false, Color.BLACK, 30);
		Text.drawString(g, "Use Arrow keys to move and M to attack", 50, 260, false, Color.BLACK, 20);
		Text.drawString(g, "Objective:", 50, 310, false, Color.BLACK, 30);
		Text.drawString(g, "To protect Jun, you are tasked with finding and defeating the spirits haunting this ", 
				50, 340, false, Color.BLACK, 20);
		Text.drawString(g, "house. Explore the house by using the enchanted pink carpet and find the boss's ",
				50, 370, false, Color.BLACK, 20);
		Text.drawString(g, "right hand man. He might just drop a key to unlock the magical blue carpet!",
				50, 400, false, Color.BLACK, 20);
	}

}
