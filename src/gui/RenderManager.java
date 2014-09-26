package gui;

import java.util.TreeMap;

import javax.swing.JFrame;

import utilities.Console;

public class RenderManager implements Runnable {
	JFrame parent;
	VolatileScreen main;
	TreeMap<Integer, Screen> screens;

	public RenderManager(JFrame parent) {
		this.parent = parent;
		main = new VolatileScreen(0, 0, parent.getWidth(), parent.getHeight(), this);
		// main.setBackground(ResourceLoader.getImage("background.jpg"));
		screens = new TreeMap<Integer, Screen>();
	}

	public JFrame getWindow() {
		return parent;
	}

	public void addScreen(Integer key, Screen s) {
		screens.put(key, s);	//TODO: Cannot have multiple screens for the same key (supposed to be depth..)
	}

	public void run() {
		for (Screen s : screens.values()) {
			if (parent != null && s.getImage() != null) {
				main.getGraphics().drawImage(s.getImage(), s.getX(), s.getY(),
						null);
			} else {
				Console.log(("Something went wrong. parent: " + parent
						+ ", img: " + s.getImage()), Console.in.ERROR);
			}
		}
		parent.getGraphics().drawImage(main.getImage(), 0, 25,
				parent.getWidth(), parent.getHeight() - 25, null);
	}

	public void reset() {
		main.reset();
		for (Screen s : screens.values()) {
			s.reset();
		}
	}

	public VolatileScreen getMain() {
		return main;
	}
}