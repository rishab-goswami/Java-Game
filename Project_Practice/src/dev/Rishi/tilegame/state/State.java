package dev.Rishi.tilegame.state;

import java.awt.Graphics;

import dev.Rishi.tilegame.Handler;

//Base state class the main function of which is to get and set the required state 
//in the code
public abstract class State {
	private static State currentState = null;
	
	//CLASS (abstract)
	protected Handler handler;
	
	public State(Handler handler) {
		this.handler = handler; 
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	
	//sets state
	public static void setState(State state) {
		currentState = state;
	}
	
	//gets state
	public static State getState() {
		return currentState;
	}

}
