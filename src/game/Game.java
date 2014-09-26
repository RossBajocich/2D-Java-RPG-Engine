package game;

import elements.Renderable;
import gui.BufferedScreen;
import gui.RenderManager;
import gui.VolatileScreen;
import gui.HUD.HUD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;

import loaders.LevelLoader;
import utilities.Clock;
import utilities.Console;
import utilities.Console.in;
import utilities.Globals.Direction;
import utilities.Keyboard;
import utilities.Mouse;
import characters.Player;

public class Game {
	private Keyboard k;
	private Mouse m;
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

	public Game(RenderManager rm, Keyboard k, Mouse m) {
		this.rm = rm;
		this.k = k;
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

		rm.addScreen(0, mainScr);
		rm.addScreen(1, hudScr);

		mainView = new View(300, 300, l.getWidth(), l.getHeight(), p);
		Console.log("w:" + l.getWidth() + " h: " + l.getHeight(), in.INFO);
		currentView = mainView;
		currentView.center(p);

		menu = new Menu(this);
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

		//Console.log("vx: " + viewX + " vy: " + viewY + " vw: " + viewWidth + " vh: " + viewHeight, in.INFO);
		
		gMain.drawImage(
				a.getBackground().getSubimage(viewX, viewY, viewWidth,
						viewHeight), 0, 0, scrWidth, scrHeight, null);

		for (List<Renderable> renders : a.getRenders().values()) {
			for (Renderable e : renders) {
				if (e.isWithin(currentView)) {
					// Console.log("drew an object?", in.INFO);
					int x = (int) ((e.getX() - currentView.getX()) * scaleX);
					int y = (int) ((e.getY() - currentView.getY()) * scaleY);

					if (e.getImage() != null) {
						gMain.drawImage(e.getImage(), x, y,
								(int) (e.getWidth() * scaleX),
								(int) (e.getHeight() * scaleY), null);
					} else {
						Console.log(
								"img for object at " + e.getX() + ", "
										+ e.getY() + " is null!!", in.INFO);
					}
					if (e instanceof Player && !((Player) e).isMainPlayer()) {
						Player p = (Player) e;
						int barW = 100, barH = 25;
						int x1 = (int) ((e.getX() - currentView.getX()) * scaleX)
								- barW;
						int y1 = (int) ((e.getY() - currentView.getY()) * scaleY)
								- barH;

						gMain.setColor(Color.red);
						gMain.fillRect(x1, y1, barW, barH);

						gMain.setColor(Color.green);
						double temp = (p.getHealth() / p.getMaxHealth()) * barW;
						gMain.fillRect(x1, y1, (int) temp, barH);

						gMain.setColor(Color.black);
						gMain.drawRect(x1, y1, barW, barH);

						gMain.drawString("hp: " + p.getHealth(), x1 + 5,
								y1 + 18);

						y1 = y1 + barH;
						barH = 10;

						gMain.setColor(Color.white);
						gMain.fillRect(x1, y1, barW, barH);

						gMain.setColor(Color.blue);
						temp = p.getWaitRatio() * barW;
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

		if (!menu_activated) {
			// game logic
			Player p = w.getCurrentLevel().getMainPlayer();

			if (k.getKey(KeyEvent.VK_W)) {
				p.move(Direction.UP);
			}else
			if (k.getKey(KeyEvent.VK_S)) {
				p.move(Direction.DOWN);
			}else
			if (k.getKey(KeyEvent.VK_A)) {
				p.move(Direction.LEFT);
			}else
			if (k.getKey(KeyEvent.VK_D)) {
				p.move(Direction.RIGHT);
			}
			
			if (k.getKey(KeyEvent.VK_F)) {
				w.getCurrentLevel().attack(p);
			}
			if (k.getKey(KeyEvent.VK_I)) {
				w.getCurrentLevel().interact(p);
			}

			Clock.update();

			w.getCurrentLevel().update();
			hud.update();
			draw();
			// Console.log("ran!", in.INFO);
			
			// end game logic

		} else {
			if (k.getKey(KeyEvent.VK_UP)) {
				menu.move(Menu.direction.UP);
			}
			if (k.getKey(KeyEvent.VK_DOWN)) {
				menu.move(Menu.direction.DOWN);
			}
			if (k.getKey(KeyEvent.VK_ENTER)) {
				menu.select();
			}
			menu.draw(hudScr);
		}
	}
}
