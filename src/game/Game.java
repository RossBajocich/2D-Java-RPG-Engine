package game;

import graphics.RenderLoop;
import gui.RenderManager;
import gui.HUD.HUD;

import java.awt.event.KeyEvent;

import loaders.LevelLoader;
import utilities.Clock;
import utilities.Keyboard;
import world.World;

public class Game {
	public enum GameState {
		RUNNING, PAUSED
	}

	private HUD hud;
	private static World w = new World();
	private Menu menu;
	private GameState state;

	private RenderLoop render_loop;

	public Game(RenderManager rm) {
		if (w.getCurrentLevel() == null) {
			// should load the files in a different thread for loading screen
			load();
		}

		hud = new HUD(w, rm.getMain().getWidth(), rm.getMain().getHeight());

		menu = new Menu(this);

		state = GameState.RUNNING;

		render_loop = new RenderLoop(rm, w, hud);
	}

	public static void load() {
		w.setLevel(LevelLoader.getLevel("level0"));
	}

	public HUD getHUD() {
		// TODO Auto-generated method stub
		return hud;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState s) {
		state = s;
	}

	public void update() {
		// add support for only updating certain amounts of game time to
		// compensate for lag

		switch (state) {
		case RUNNING:
			// game logic
			Clock.update();

			w.getCurrentLevel().update();
			render_loop.render();

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

			break;
		default:

		}
	}
}
