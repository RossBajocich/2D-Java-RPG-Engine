package gui.HUD;

import game.View;
import game.World;
import gui.Screen;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import loaders.ResourceLoader;
import characters.Player;

public class HUD {
	World w;
	List<HElement> elements = new ArrayList<HElement>();
	View currentView;

	public HUD(World world, int width, int height) {
		w = world;
		initialize(width, height);
	}

	private void initialize(int width, int height) {
		Player p = w.getCurrentLevel().getMainPlayer();

		int mapWidth = 100, mapHeight = 100;

		int iWidth = 100, iHeight = 200;
		HBackPack backpack = new HBackPack(0, width - iHeight, iWidth,
				iHeight, p);
		backpack.setImage(ResourceLoader.getImage("white.png"));
		addHUDElement(backpack);

		int infoW = 150, infoH = 100;
		HInfoText info = new HInfoText(iWidth, height - infoH, infoW,
				infoH, p);
		info.setImage(ResourceLoader.getImage("white.png"));
		addHUDElement(info);

		int barW = width - (iWidth + infoW + mapWidth), barH = iWidth;
		HBars bars = new HBars(iWidth + infoW, height - barH, barW,
				barH, p);
		addHUDElement(bars);
	}

	public void setView(View v) {
		currentView = v;
	}

	public void render(Screen s) {
		for (HElement h : elements) {
			h.update();
			h.draw(s);
		}
	}

	public static boolean isWithin(Point p, Rectangle r) {
		return (p.x >= r.x && p.y >= r.y && p.x <= r.x + r.width && p.y <= r.y
				+ r.height);
	}

	public void onMousePressed(MouseEvent e) {
		for (HElement h : elements) {
			if (isWithin(e.getPoint(), h.getBounds())) {
				h.mousePressed(e);
			}
		}
	}

	public void onMouseClicked(MouseEvent e) {
		for (HElement h : elements) {
			if (isWithin(e.getPoint(), h.getBounds())) {
				h.mouseClicked(e);
			}
		}
	}

	public void onMouseReleased(MouseEvent e) {
		for (HElement h : elements) {
			if (isWithin(e.getPoint(), h.getBounds())) {
				h.mouseReleased(e);
			}
		}
	}

	public void addHUDElement(HElement e) {
		elements.add(e);
	}

	public List<HElement> getElements() {
		return elements;
	}
}
