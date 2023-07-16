package dev.Rishi.tilegame;

public class Score {
	
	private Handler handler;
	private int score;
	private int deaths;
	

	public Score(Handler handler) {
		this.handler = handler;
	}
	
	//calculations for our scoring system, each enemy type increments death 
	//count by a different number and time remaining gets added on top
	public void IncrementScore() {
		deaths = handler.getWorld().getEntityManager().getDeathCount();
		score = deaths * 10;
	}
	
	public void FinalScore(int minutes, int tens, int ones) {
		score += minutes*100;
		score += tens*100;
		score += ones*100;
	}

	public int getScore() {
		return score;
	}	

}
