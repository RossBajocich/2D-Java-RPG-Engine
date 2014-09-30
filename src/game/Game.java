package game;

import elements.Member;
import gui.BufferedScreen;
import gui.RenderManager;
import gui.VolatileScreen;
import gui.HUD.HUD;
import gui.RenderManager.ScreenLayer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;

import loaders.LevelLoader;
import utilities.Clock;
import utilities.Console;
import utilities.Console.in;
import utilities.Keyboard;
import utilities.Mouse;
import characters.Player;
import components.GraphicsComponent;
import components.PhysicsComponent;

public class Game {
	Mouse m;
	Player p;
	int width, height;
	VolatileScreen mainScr;
	BufferedScreen hudScr;
	HUD hud;
	View currentView, mainView;
	World w;
	Menu menu;
	private RenderManager rm;
	boolean menu_activated = false;
	private GameState state;

	public enum GameState {
		RUNNING, PAUSED
	}

	public Game(RenderManager rm, Mouse m) {
		this.rm = rm;
		this.m = m;

		Level l = LevelLoader.getLevel("level0");
		p = l.getMainPlayer();
		if (p == null) {
			Console.log("Wheres the main player?", in.INFO);
		}
		w = new World(l);

		this.width = rm.getWindow().getWidth();
		this.height = rm.getWindow().getHeight();

		mainScr = rm.getMain();
		hudScr = new BufferedScreen(0, 0, mainScr.getWidth(),
				mainScr.getHeight(), rm);
		hud = new HUD(hudScr, w, rm);

		rm.addScreen(ScreenLayer.MAIN, mainScr);
		rm.addScreen(ScreenLayer.HUD, hudScr);

		mainView = new View(300, 300, l.getWidth(), l.getHeight(), p);
		Console.log("w:" + l.getWidth() + " h: " + l.getHeight(), in.INFO);
		currentView = mainView;
		currentView.center(p);

		menu = new Menu(this);

		state = GameState.RUNNING;
	}

	public void setState(GameState s) {
		state = s;
	}

	public GameState getState() {
		return state;
	}

	private void draw() {
		rm.reset();

		Graphics gMain = mainScr.getGraphics();
		currentView.update();

		Level a = w.getCurrentLevel();

		double scaleX = mainScr.getWidth() / currentView.getWidth();
		double scaleY = mainScr.getHeight() / currentView.getHeight();

		int viewX = (int) (currentView.getX() * scaleX);
		int viewY = (int) (currentView.getY() * scaleY);
		int viewWidth = (int) (currentView.getWidth() * scaleX);
		int viewHeight = (int) (currentView.getHeight() * scaleY);

		int scrWidth = Math.min(mainScr.getWidth(), a.getBackground()
				.getWidth());
		int scrHeight = Math.min(mainScr.getHeight(), a.getBackground()
				.getHeight());

		gMain.drawImage(
				a.getBackground().getSubimage(viewX, viewY, viewWidth,
						viewHeight), 0, 0, scrWidth, scrHeight, null);

		for (List<Member> renders : a.getRenders().values()) {
			for (Member e : renders) {
				if (e.getGraphics().isWithin(currentView)) {
					// Console.log("drew an object?", in.INFO);
					PhysicsComponent physics = e.getPhysics();
					GraphicsComponent graphics = e.getGraphics();

					int x = (int) ((physics.getX() - currentView.getX()) * scaleX);
					int y = (int) ((physics.getY() - currentView.getY()) * scaleY);

					if (graphics.getImage() != null) {
						gMain.drawImage(graphics.getImage(), x, y,
								(int) (graphics.getWidth() * scaleX),
								(int) (graphics.getHeight() * scaleY), null);
					} else {
						Console.log("img for object at " + physics.getX()
								+ ", " + physics.getY() + " is null!!", in.INFO);
					}
					if (e instanceof Player && !((Player) e).isMainPlayer()) {
						Player p = (Player) e;
						int barW = 100, barH = 25;
						int x1 = (int) ((physics.getX() - currentView.getX()) * scaleX)
								- barW;
						int y1 = (int) ((physics.getY() - currentView.getY()) * scaleY)
								- barH;

						gMain.setColor(Color.red);
						gMain.fillRect(x1, y1, barW, barH);

						gMain.setColor(Color.green);
						double temp = ((double) p.getAttack().getHealth() / p
								.getAttack().getMaxHealth()) * barW;
						gMain.fillRect(x1, y1, (int) temp, barH);

						gMain.setColor(Color.black);
						gMain.drawRect(x1, y1, barW, barH);

						gMain.drawString("hp: " + p.getAttack().getHealth()
								+ " / " + p.getAttack().getMaxHealth(), x1 + 5,
								y1 + 18);

						y1 = y1 + barH;
						barH = 10;

						gMain.setColor(Color.white);
						gMain.fillRect(x1, y1, barW, barH);

						gMain.setColor(Color.blue);
						temp = p.getAttack().getWaitRatio() * barW;
						gMain.fillRect(x1, y1, (int) temp, barH);
					}
				}
			}
		}

		gMain.dispose();
	}

	public HUD getHUD() {
		// TODO Auto-generated method stub
		return hud;
	}

	public void update() {
		// add support for only updating certain amounts of game time to
		// compensate for lag

		switch (state) {
		case RUNNING:
			// game logic
			Player p = w.getCurrentLevel().getMainPlayer();

			Clock.update();

			w.getCurrentLevel().update();
			hud.update();
			draw();
			// Console.log("ran!", in.INFO);

			// end game logic
			break;
		case PAUSED:
			if (Keyboard.getKey(KeyEvent.VK_UP)) {
				menu.move(Menu.direction.UP);
			}
			if (Keyboard.getKey(KeyEvent.VK_DOWN)) {
				menu.move(Menu.direction.DOWN);
			}
			if (Keyboard.getKey(KeyEvent.VK_ENTER)) {
				menu.select();
			}
			menu.draw(hudScr);
			break;
		default:

		}
	}
}
