package dev.Rishi.tilegame.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.Rishi.tilegame.HUD;
import dev.Rishi.tilegame.Handler;
import dev.Rishi.tilegame.Entity.Creature.Player;
import dev.Rishi.tilegame.Items.Item;
import dev.Rishi.tilegame.UI.ClickListener;
import dev.Rishi.tilegame.UI.UIImageButton;
import dev.Rishi.tilegame.UI.UIManager;
import dev.Rishi.tilegame.gfx.Assets;
import dev.Rishi.tilegame.worlds.World;

//Main state where the game gets played
public class GameState extends State {

	private World world;
	private HUD hud;
	private boolean pressed = false, quit = false;
	protected Player player;
	private UIManager uiManager;
	private long timeNow = System.currentTimeMillis(), gameTimer = 0, lastGameTimer = 0;
	private boolean cheat = false;

	public GameState(Handler handler, boolean CoOp){
		super(handler);
		//creating a new hud and world to be displayed on to the window
		hud = new HUD(handler);
		world = new World(handler);
		world.setCo_Op(CoOp);
		//checks whether or not to input the spawn location of player2
		if(CoOp) {
			world.loadWorld("Res/worldCoOp/world1.txt", "Res/entities/world1Enemies.txt",
					"Res/entities/world1Furniture.txt","Res/entities/world1Doors.txt");
		} else {
			world.loadWorld("Res/worlds/world1.txt", "Res/entities/world1Enemies.txt",
					"Res/entities/world1Furniture.txt","Res/entities/world1Doors.txt");
		}

		handler.setWorld(world);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);
		//setting the time limit of the game
		hud.timer(5, 0, 1);

	}

	@Override
	public void tick() {
		//
		timeNow = System.currentTimeMillis();
		gameTimer += timeNow - lastGameTimer;
		lastGameTimer = timeNow;

		//pressing p on the keyboard causes the game to be paused, UI objects added for resuming and leaving the game
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_P)) {
			pressed = !pressed;
			quit = false;
			uiManager = new UIManager(handler);
			handler.getMouseManager().setUiManager(uiManager);
			uiManager.addObject(new UIImageButton(412, 300, 200, 75, Assets.button_resume, new ClickListener() {
				@Override
				public void onClick() {
					handler.getMouseManager().setUiManager(null);
					pressed = false;
				}}));
			uiManager.addObject(new UIImageButton(412, 400, 200, 75, Assets.button_pauseMainMenu, new ClickListener() {
				@Override
				public void onClick() {
					handler.getMouseManager().setUiManager(null);
					MenuState menuState = new MenuState(handler);
					State.setState(menuState);

				}}));
		}
		//pressing escape shows a prompt to exit the game
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
			quit = !quit;
			pressed = false;
			uiManager = new UIManager(handler);
			handler.getMouseManager().setUiManager(uiManager);
			uiManager.addObject(new UIImageButton(287, 400, 200, 75, Assets.button_yes, new ClickListener() {
				@Override
				public void onClick() {
					handler.getMouseManager().setUiManager(null);
					System.exit(0);

				}}));

			uiManager.addObject(new UIImageButton(537, 400, 200, 75, Assets.button_no, new ClickListener() {
				@Override
				public void onClick() {
					handler.getMouseManager().setUiManager(null);
					quit = false;

				}}));

		}
		//making sure the game isn't paused before ticking 
		if(!pressed && !quit) {
			world.tick();
			hud.tick();
			finalScore();
			if (gameTimer >= 1000) {
				gameTimer = 0;
				hud.timerTick();
			}
		}else{
			uiManager.tick();
		}
		//cheat button that transport player to the boss stage with full health and all the items needed
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_PAGE_DOWN)) {
			if(!cheat) {
				world.getItemManager().setPickedUp();
				world.getEntityManager().getPlayer().getInventory().addItem(Item.sword.createNew(0, 0));
				world.getEntityManager().getPlayer().getInventory().addItem(Item.key.createNew(0, 0));
			}
			if(handler.getWorld().isCo_Op()) {
				world.setBossWorldLoaded();
				world.inWorld(5);
				world.getEntityManager().getPlayer().increaseHealth(10);
				world.loadBossWorld("Res/worldCoOp/bossWorld.txt", "Res/entities/bossEnemy.txt");
			} else {
				world.setBossWorldLoaded();
				world.inWorld(5);
				world.getEntityManager().getPlayer().increaseHealth(10);
				if(world.isHardMode()) {
					world.loadBossWorld("Res/worlds/bossWorld.txt",
							"Res/entities/bossEnemyHard.txt");
				} else {
					world.loadBossWorld("Res/worlds/bossWorld.txt",
							"Res/entities/bossEnemy.txt");
				}
			}
			cheat = true;
		}
	}

	//if game has been finish, adds the remaining time onto the score 
	public void finalScore() {
		if(world.isBossDefeated()) {
			handler.getWorld().getScore().FinalScore(hud.getMinutes(), hud.getTensSeconds(),
					hud.getOnesSeconds());
			WinState winState = new WinState(handler);
			State.setState(winState);
		}
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		hud.render(g);
		world.getEntityManager().getPlayer().getInventory().render(g);
		//rendering the UI objects if correct keys are being pressed
		if (pressed) {
			g.drawImage(Assets.pause_menu, 262, 134, null);
			uiManager.render(g);
		}
		if (quit) {
			g.drawImage(Assets.quit_screen, 262, 284, null);
			uiManager.render(g);
		}
	}
}

