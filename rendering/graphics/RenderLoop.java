package graphics;

import gui.BufferedScreen;
import gui.RenderManager;
import gui.RenderManager.ScreenLayer;
import gui.Screen;
import gui.HUD.HUD;

import java.awt.Graphics;

import world.Level;
import world.World;

public class RenderLoop {

	private RenderManager rm;
	private View playerReference;
	private ReferenceFrame levelReference;
	private World w;
	private Screen mainScr, hudScr;
	private HUD hud;
	boolean drawBounds = true;

	public RenderLoop(RenderManager rm, World w, HUD hud) {
		this.rm = rm;
		playerReference = new View(0, 0, 300, 300, w.getCurrentLevel(), w
				.getCurrentLevel().getMainPlayer());
		levelReference = new ReferenceFrame(0, 0, w.getCurrentLevel()
				.getWidth(), w.getCurrentLevel().getHeight(),
				w.getCurrentLevel());
		levelReference.setDrawBars(false);
		mainScr = rm.getMain();
		this.w = w;
		this.hud = hud;

		mainScr = new BufferedScreen(0, 0, rm.getMain().getWidth(), rm
				.getMain().getHeight(), rm);
		hudScr = new BufferedScreen(0, 0, mainScr.getWidth(),
				mainScr.getHeight(), rm);

		rm.addScreen(ScreenLayer.MAIN, mainScr);
		rm.addScreen(ScreenLayer.HUD, hudScr);
	}

	public void render() {
		rm.reset();

		Graphics gMain = mainScr.getGraphics();
		Graphics gHUD = hudScr.getGraphics();
		// make sure view is centered
		playerReference.update();

		Level a = w.getCurrentLevel();

		playerReference.draw(0, 0, mainScr.getWidth(), mainScr.getHeight(),
				gMain, a);

		levelReference.draw(400, 400, 100, 100, gHUD, a);

		hud.render(hudScr.getGraphics());

		gMain.dispose();
	}
}
