package gui;

import game.Game;
import game.GameLoop;

import javax.swing.JFrame;

import utilities.Keyboard;

@SuppressWarnings("serial")
public class Window extends JFrame {
	private static RenderManager rm;
	private static GameLoop gameLoop;
	private static Game game;
	private static Keyboard k;
	private static Window w;

	public Window(String title, int width, int height) {
		super(title);
		setSize(width, height);
		setIgnoreRepaint(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIgnoreRepaint(true);
		k = new Keyboard();
		addKeyListener(k);
		setVisible(true);
		setResizable(false);
		rm = new RenderManager(this);
	}

	public static Window get() {
		return w;
	}

	public static void main(String[] args) {
		w = new Window("Game", 500, 500);
		Game.load();
		game = new Game(rm);
		gameLoop = new GameLoop(game);
		while (true) {
			gameLoop.run();
			rm.run();
		}
	}
}
