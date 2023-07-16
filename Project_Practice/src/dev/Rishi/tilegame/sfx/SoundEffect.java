package dev.Rishi.tilegame.sfx;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import dev.Rishi.tilegame.Launcher;


public class SoundEffect {
	Clip clip;
	//setting file to required sound
	public void setFile(String soundFileName) {
		
		try{			
			File file = new File(soundFileName);
			AudioInputStream sound = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(sound);
		} catch(Exception e) {
			
		}
	}
	//playing the file
	public void play() {
		for(int i = 0; i < 500; i++) {
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	//making a new thread for running the background music on a loop 
	public static synchronized void playSound(final String url) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(
							Launcher.class.getResourceAsStream(url));
					clip.open(inputStream);
					clip.start(); 
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
	
}
