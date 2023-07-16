package dev.Rishi.tilegame.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.FontMetrics;

public class Text {
	//helper function used to write text on the screen in serif font. The text can be centered between the x position and 
	//y position and change colour and size.
	public static void drawString(Graphics g, String text, int xPos, int yPos, boolean center, Color c, int size) {
		Font font = new Font("serif", Font.BOLD, size);
		g.setColor(c);
		g.setFont(font);
		int x = xPos;
		int y = yPos;
		if(center) {
			FontMetrics fm = g.getFontMetrics(font);
			x = xPos - fm.stringWidth(text) / 2;
			y = (yPos - fm.getHeight() / 2);
		}
		g.drawString(text, x, y);
	}
	
}
