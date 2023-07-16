package dev.Rishi.tilegame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import dev.Rishi.tilegame.Input.KeyManager;
import dev.Rishi.tilegame.Input.MouseManager;
import dev.Rishi.tilegame.display.Display;
import dev.Rishi.tilegame.gfx.Assets;
import dev.Rishi.tilegame.gfx.GameCamera;
import dev.Rishi.tilegame.sfx.SoundAssets;
import dev.Rishi.tilegame.state.MenuState;
import dev.Rishi.tilegame.state.State;
import dev.Rishi.tilegame.utilities.Utilities;

public class Game implements Runnable {

	private Display display;
	private int width, height;
	public String title;	
	private String highScoreName1, highScoreName2, highScoreName3;
	private int highScore1, highScore2, highScore3;
	private String file, tempToken;
	private String[] tokens;
	
	private boolean running = false;
	//Need to learn what this does and if we are allowed to use it.
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	//States
	private State menuState;
	
	//Inputs
	private KeyManager keyManager;
	int ticks = 0;
	private MouseManager mouseManager;
	
	//Camera
	private GameCamera gameCamera;
	
	//Handler
	private Handler handler;
	
	//creating the game method
	public Game(String title, int width, int height){
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}
	
	
	private void init(){
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();
		SoundAssets.init();
		
		handler = new Handler(this);
		gameCamera = new GameCamera(handler,0,0);
		
		menuState = new MenuState(handler);
		State.setState(menuState);
		
	}
	
	private void tick(){
		keyManager.tick();
		
		if(State.getState() != null)
			State.getState().tick();
	}
	
	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		//Draw Here!
		
		if(State.getState() != null)
			State.getState().render(g);
		
		bs.show();
		g.dispose();
	}
	
	public void run(){
		
		init();
		
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
					tick();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000){
				ticks = 0;
				timer = 0;
			}
		}
		
		end();
		
	}
	
	public KeyManager getKeyManager(){
		return keyManager;
	}
	
	public MouseManager getMouesManager() {
		return mouseManager;
	}
	
	public GameCamera getGameCamera() {
		return gameCamera;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void end() {
		if(!running){
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//readFromFile(), readHighScores(), sortHighScores, writeToFile() and checkNewHighScore
	//are used together to read a high scores text file and implement bubble sort on the
	//scores and overwrite the file with the sorted scores. It also adds in a new high score
	//if it beats third place, resorts the scores and rewrite
	public void readFromFile() {
		file = Utilities.loadFileAsString("Res/scores/highscoresList.txt");
		tokens = file.split("\\s+");
	}
	
	public void readHighScores() {
		sortHighScores();
		highScoreName1 = tokens[0];
		highScore1 = Utilities.parseInt(tokens[1]);
		highScoreName2 = tokens[2];
		highScore2 = Utilities.parseInt(tokens[3]);
		highScoreName3 = tokens[4];
		highScore3 = Utilities.parseInt(tokens[5]);
		writeToFile();
	}

	public void sortHighScores() {
		for(int i = 0; i < 2; i++) {
			for(int j = 1; j < 5; j += 2) {
				if (Utilities.parseInt(tokens[j]) < Utilities.parseInt(tokens[j+2])) {
					tempToken = tokens[j+2];
					tokens[j+2] = tokens[j];
					tokens[j] = tempToken;
				} 
			}
		}
	}

	private void writeToFile() {
		try (FileWriter f = new FileWriter("Res/scores/highscoresList.txt", false);
				BufferedWriter b = new BufferedWriter(f);
				PrintWriter writer = new PrintWriter(b);) {			

			writer.println(highScoreName1+" "+highScore1);
			writer.println(highScoreName2+" "+highScore2);
			writer.println(highScoreName3+" "+highScore3);
			writer.close();

		} catch (IOException i) {

			i.printStackTrace();
		}
	}

	public void checkNewHighScore(int score) {
		if (Utilities.parseInt(tokens[5]) < score) {
			System.out.print("new score");
			tokens[5] = Integer.toString(score);
		}
		readHighScores();
	}

	public String getHighScoreName1() {
		return highScoreName1;
	}

	public String getHighScoreName2() {
		return highScoreName2;
	}

	public String getHighScoreName3() {
		return highScoreName3;
	}

	public int getHighScore1() {
		return highScore1;
	}

	public int getHighScore2() {
		return highScore2;
	}

	public int getHighScore3() {
		return highScore3;
	}


}
