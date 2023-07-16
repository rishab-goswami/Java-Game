package dev.Rishi.tilegame.sfx;

//making the sound effect for the required interactions e.g. atttacking, or stepping on noise tile
public class SoundAssets {
	
	public static SoundEffect collide, creak, hit, teleport, backgroundMusic, pickUp; 
	
	public static void init() {
		collide = new SoundEffect();
		collide.setFile("Res/soundEffects/jute-dh-rpgsounds/click_2.wav");

		creak = new SoundEffect();
		creak.setFile("Res/soundEffects/jute-dh-rpgsounds/doorwood_close.wav");
		
		hit = new SoundEffect();
		hit.setFile("Res/soundEffects/jute-dh-rpgsounds/hit_3.wav");
		
		teleport = new SoundEffect();
		teleport.setFile("Res/soundEffects/172206__fins__teleport.wav");
		
		pickUp = new SoundEffect();
		pickUp.setFile("Res/soundEffects/ring_inventory.wav");
	}
	

}
