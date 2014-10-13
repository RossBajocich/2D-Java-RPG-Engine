package gui.HUD;

import gui.Window;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import loaders.ResourceLoader;
import world.World;
import characters.Player;

public class HUD implements MouseListener {
	private World w;
	private List<HElement> elements = new ArrayList<HElement>();

	public HUD(World world, int width, int height) {
		Window.get().addMouseListener(this);
		w = world;
		initialize(width, height);
	}

	public void addHUDElement(HElement e) {
		elements.add(e);
	}

	public List<HElement> getElements() {
		return elements;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (HElement h : elements) {
			if (h.getBounds().contains(e.getPoint())) {
				h.mouseClicked(e);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO make this specific to HElements instead of just a notification
		// on the whole HUD
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO make this specific to HElements instead of just a notification
		// on the whole HUD
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (HElement h : elements) {
			if (h.getBounds().contains(e.getPoint())) {
				h.mousePressed(e);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (HElement h : elements) {
			if (h.getBounds().contains(e.getPoint())) {
				h.mouseReleased(e);
			}
		}
	}

	public void render(Graphics g) {
		for (HElement h : elements) {
			h.update();
			h.draw(g);
		}
	}

	private void initialize(int width, int height) {
		Player p = w.getCurrentLevel().getMainPlayer();

		int mapWidth = 100, mapHeight = 100;

		int iWidth = 100, iHeight = 200;
		HContainer container = new HContainer(0, width - iHeight, iWidth,
				iHeight, p);
		container.setImage(ResourceLoader.getImage("HContainer.png"));
		addHUDElement(container);

		int infoW = 150, infoH = 100;
		HInfoText info = new HInfoText(iWidth, height - infoH, infoW, infoH, p);
		info.setImage(ResourceLoader.getImage("white.png"));
		addHUDElement(info);

		int barW = width - (iWidth + infoW + mapWidth), barH = iWidth;
		HBars bars = new HBars(iWidth + infoW, height - barH, barW, barH, p);
		addHUDElement(bars);
	}
}
