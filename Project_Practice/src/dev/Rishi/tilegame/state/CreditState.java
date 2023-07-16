package dev.Rishi.tilegame.state;

import java.awt.Color;
import java.awt.Graphics;

import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.UI.ClickListener;
import dev.Rishi.tilegame.UI.UIImageButton;
import dev.Rishi.tilegame.UI.UIManager;
import dev.Rishi.tilegame.gfx.Assets;
import dev.Rishi.tilegame.gfx.Text;

//basic credits screen
public class CreditState extends State{
	
	private UIManager uiManager;

	public CreditState(final Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);
		
		uiManager.addObject(new UIImageButton(412, 600, 200, 75, Assets.button_back, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUiManager(null);
				MenuState menu = new MenuState(handler);
				State.setState(menu);
						
			}}));
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.background, 0, 0, null);
		uiManager.render(g);
		Text.drawString(g, "Made by Rishab Goswami and Grun Wua Wong", 512, 384, true, Color.BLACK, 28);
	}

	@Override
	public void tick() {
		uiManager.tick();
		
	}

}
