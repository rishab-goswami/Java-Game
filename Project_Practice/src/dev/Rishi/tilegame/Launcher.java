package dev.Rishi.tilegame;


import dev.Rishi.tilegame.sfx.SoundEffect;

public class Launcher {
	//temporarily increasing size of window for surface
	private static int launcherWidth = 1024;
	private static int launcherHeight = 768;

	public static void main(String[] args) {
		Game game = new Game("The Legend of Jun", launcherWidth, launcherHeight);
		game.start();
		SoundEffect.playSound("owzers chamber (online-audio-converter.com).wav");
	}



	public static int getLauncherWidth() {
		return launcherWidth;
	}

	public static int getLauncherHeight() {
		return launcherHeight;
	}


}
