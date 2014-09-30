package gui.HUD;

import gui.BufferedScreen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import utilities.Stats;
import characters.Player;

public class HInfoText extends HElement {
	Player p;
	Stats ps;
	List<String> stats = new ArrayList<String>();

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
		stats.add("Attack: " + ps.attack);
		stats.add("Defence: " + ps.def);
		stats.add("Health: " + ps.health);
		stats.add("MoveSpeed: " + ps.moveSpeed);
		stats.add("AttackSpeed: " + ps.speed);
	}

}
