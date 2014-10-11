package gui.HUD;

import gui.Screen;

import java.awt.Color;
import java.awt.Graphics;

import characters.Player;

import components.AttackComponent;

public class HBars extends HElement {

	Player p;
	Color hp1 = Color.red, hp2 = Color.green, mana1 = Color.black,
			mana2 = Color.blue, attk1 = Color.black, attk2 = Color.cyan;

	public HBars(int x, int y, int width, int height, Player p) {
		super(x, y, width, height);
		this.p = p;
	}

	@Override
	public void draw(Screen s) {
		Graphics gMain = s.getGraphics();
		if (p != null) {
			int barW = width, barH = height / 3;

			gMain.setColor(hp1);
			gMain.fillRect(x, y, barW, barH);

			gMain.setColor(hp2);
			double temp = ((p.get(AttackComponent.class)).getMaxHealth() / (p
					.get(AttackComponent.class)).getMaxHealth()) * barW;
			gMain.fillRect(x, y, (int) temp, barH);

			gMain.setColor(Color.black);
			gMain.drawRect(x, y, barW, barH);

			gMain.drawString(
					"hp: " + (p.get(AttackComponent.class)).getMaxHealth(),
					x + 5, y + 18);

			int y1 = y + barH;
			// mana
			gMain.setColor(mana1);
			gMain.fillRect(x, y1, barW, barH);

			gMain.setColor(mana2);
			temp = (p.getStats().mana / (p.get(AttackComponent.class))
					.getMaxMana()) * barW;
			gMain.fillRect(x, y1, (int) temp, barH);

			gMain.setColor(Color.black);
			gMain.drawRect(x, y1, barW, barH);

			gMain.drawString("mana: " + p.getStats().mana, x + 5, y1 + 18);

			y1 = y1 + barH;
			barH = 10;

			gMain.setColor(attk1);
			gMain.fillRect(x, y1, barW, barH);

			gMain.setColor(attk2);
			temp = (p.get(AttackComponent.class)).getWaitRatio() * barW;
			gMain.fillRect(x, y1, (int) temp, barH);
		}
	}

	public void setColorsAttk(Color bg2, Color fg2) {
		this.attk1 = bg2;
		this.attk2 = fg2;
	}

	public void setColorsHp(Color bg1, Color fg1) {
		this.hp1 = bg1;
		this.hp2 = fg1;
	}

	public void setColorsMana(Color bg2, Color fg2) {
		this.mana1 = bg2;
		this.mana2 = fg2;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
