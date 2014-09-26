package game;

import utilities.Console;

public class GameLoop implements Runnable {
	private boolean running = true;
	static final double FPS = 60;
	static final double NSPF = (1000 * 1000 * 1000) / FPS;
	double start, end;
	Game g;

	public GameLoop(Game g) {
		this.g = g;
		Console.log("FPS: " + FPS, Console.in.INFO);
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean state) {
		running = state;
	}

	public void run() {
		start = System.nanoTime();
		
		//run one step in game
		g.update();
		
		end = System.nanoTime();
		double diff = end - start;

		if (NSPF - diff >= 0) {
			try {
				Thread.sleep((long) (NSPF - diff) / 1000 / 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
