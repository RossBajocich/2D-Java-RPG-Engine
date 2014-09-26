package gui.HUD;

import gui.BufferedScreen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

import utilities.PlayerStats;
import characters.Player;

public class HInfoText extends HElement {
	Player p;
	PlayerStats ps;
	LinkedList<String> stats = new LinkedList<String>();

	public HInfoText(int x, int y, int width, int height, Player p) {
		super(x, y, width, height);
		this.p = p;
		ps = p.getStats();
	}

	@Override
	public void draw(BufferedScreen s) {
		int h = 18;
		
		Graphics g = s.getGraphics();
		
		g.drawImage(image, x, y, width, height, null);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.SERIF, Font.PLAIN, h));
		int ty = 1;
		for (String str : stats) {
			g.drawString(str, x, (h * ty) + y);
			ty++;
		}
	}

	@Override
	public void update() {
		ps = p.getStats();
		stats.clear();
		stats.add("Attack: " + ps.attk);
		stats.add("Defence: " + ps.def);
		stats.add("Health: " + ps.health);
		stats.add("MoveSpeed: " + ps.moveSpeed);
		stats.add("AttackSpeed: " + ps.speed);
	}

}
