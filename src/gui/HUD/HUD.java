package gui.HUD;

import elements.Renderable;
import game.Level;
import game.View;
import game.World;
import gui.BufferedScreen;
import gui.RenderManager;
import gui.Screen;
import gui.VolatileScreen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import utilities.Console;
import utilities.Console.in;
import loaders.ResourceLoader;
import characters.Player;

public class HUD {
	BufferedScreen s;
	World w;
	LinkedList<HElement> elements = new LinkedList<HElement>();
	VolatileScreen map;
	RenderManager rm;
	View currentView;

	public HUD(BufferedScreen hudScreen, World world, RenderManager rm) {
		s = hudScreen;
		w = world;
		this.rm = rm;
		initialize();
	}

	private void initialize() {
		Player p = w.getCurrentLevel().getMainPlayer();

		int mapWidth = 100, mapHeight = 100;
		// map = new VolatileScreen(s.getWidth() - mapWidth, s.getHeight()
		// - mapHeight, mapWidth, mapHeight, rm);

		int iWidth = 100, iHeight = 200;
		HBackPack backpack = new HBackPack(0, s.getHeight() - iHeight, iWidth,
				iHeight, p);
		backpack.setImage(ResourceLoader.getImage("white.png"));
		addHUDElement(backpack);

		int infoW = 150, infoH = 100;
		HInfoText info = new HInfoText(iWidth, s.getHeight() - infoH, infoW,
				infoH, p);
		info.setImage(ResourceLoader.getImage("white.png"));
		addHUDElement(info);

		int barW = s.getWidth() - (iWidth + infoW + mapWidth), barH = iWidth;
		HBars bars = new HBars(iWidth + infoW, s.getHeight() - barH, barW,
				barH, p);
		addHUDElement(bars);
	}

	public void setView(View v) {
		currentView = v;
	}

	private void drawMap(Screen scr, int x, int y, int width, int height) {
		Graphics gMap = scr.getGraphics();
		Level a = w.getCurrentLevel();

		int mapW = width;
		int mapH = height;
		gMap.drawImage(a.getBackground(), x, y, mapW, mapH, null);
		gMap.setColor(Color.black);
		gMap.drawRect(x, y, mapW, mapH);

		float scaleX = mapW / a.getWidth();
		float scaleY = mapH / a.getHeight();

		for (List<Renderable> renders : a.getRenders().values()) {
			for (Renderable e : renders) {
				if (e.getImage() != null) {
					gMap.drawImage(e.getImage(), x + (int) (e.getX() * scaleX),
							y + (int) (e.getY() * scaleY),
							(int) (e.getWidth() * scaleX),
							(int) (e.getHeight() * scaleY), null);
				} else {
					Console.log("img for member " + e + " is null", in.INFO);
				}
			}
		}
		if (currentView != null) {
			gMap.drawRect((int) (currentView.getX() * scaleX),
					(int) (currentView.getY() * scaleY),
					(int) (currentView.getWidth() * scaleX),
					(int) (currentView.getHeight() * scaleY));

			gMap.drawRect(x, y, mapW, mapH);
		}
		gMap.dispose();
	}

	public void update() {
		for (HElement h : elements) {
			h.update();
			h.draw(s);
		}
		drawMap(s, s.getWidth() - 100, s.getHeight() - 100, 100, 100);
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

	public LinkedList<HElement> getElements() {
		return elements;
	}
}
