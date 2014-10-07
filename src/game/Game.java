package game;

import gui.RenderManager;
import gui.HUD.HUD;

import java.awt.event.KeyEvent;

import loaders.LevelLoader;
import utilities.Clock;
import utilities.Keyboard;
import utilities.Mouse;

public class Game {
	Mouse m;
	HUD hud;
	View mainView;
	World w;

	Menu menu;
	private GameState state;

	private RenderLoop render_loop;

	public enum GameState {
		RUNNING, PAUSED
	}

	public Game(RenderManager rm, Mouse m) {
		this.m = m;

		Level l = LevelLoader.getLevel("level0");
		w = new World(l);

		hud = new HUD(w, rm.getMain().getWidth(), rm.getMain().getHeight());

		menu = new Menu(this);

		state = GameState.RUNNING;

		render_loop = new RenderLoop(rm, w, hud);
	}

	public void setState(GameState s) {
		state = s;
	}

	public GameState getState() {
		return state;
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
