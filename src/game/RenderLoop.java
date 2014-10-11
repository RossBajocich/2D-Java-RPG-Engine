package game;

import elements.Member;
import graphics.ReferenceFrame;
import gui.BufferedScreen;
import gui.RenderManager;
import gui.RenderManager.ScreenLayer;
import gui.Screen;
import gui.HUD.HUD;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

import utilities.Console;
import utilities.Console.in;

import components.GraphicsComponent;
import components.PhysicsComponent;

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

		drawMap(hudScr.getWidth() - 100, hudScr.getHeight() - 100, 100, 100,
				gHUD);

		drawHUD(hud);

		gMain.dispose();
	}

	private void drawHUD(HUD hud) {
		hud.render(hudScr);
	}

	private void drawMap(int x, int y, int width, int height, Graphics gMap) {
		Level a = w.getCurrentLevel();

		gMap.drawImage(a.getBackground(), x, y, width, height, null);
		gMap.setColor(Color.black);
		gMap.drawRect(x, y, width, height);

		for (List<Member> renders : a.getRenders().values()) {
			for (Member e : renders) {
				if ((e.get(GraphicsComponent.class)).getImage() != null) {
					GraphicsComponent graphics = e.get(GraphicsComponent.class);

					int mx = (int) (e.get(PhysicsComponent.class)).getX();
					int my = (int) (e.get(PhysicsComponent.class)).getY();

					gMap.drawImage(
							graphics.getImage(),
							(mx / a.getWidth()) * width + x,
							(my / a.getHeight()) * height + y,
							(int) ((graphics.getWidth() / a.getWidth()) * width),
							(int) ((graphics.getHeight() / a.getHeight()) * height),
							null);
					if (drawBounds) {
						// debug
						gMap.setColor(Color.red);
						((Graphics2D) gMap).setStroke(new BasicStroke(2));
						Rectangle r = (e.get(PhysicsComponent.class))
								.getBoundsRect(x, y);
						gMap.drawRect((int) ((r.x / a.getWidth()) * width),
								(int) ((r.y / a.getHeight()) * height),
								(int) ((r.width / a.getWidth()) * width),
								(int) ((r.height / a.getHeight()) * height));
					}
				} else {
					Console.log("img for member " + e + " is null", in.INFO);
				}
			}
		}
		if (playerReference != null) {
			gMap.drawRect(
					(int) ((playerReference.getX() / a.getWidth()) * width),
					(int) ((playerReference.getY() / a.getHeight()) * height),
					(int) ((playerReference.getWidth() / a.getWidth()) * width),
					(int) ((playerReference.getHeight() / a.getHeight()) * height));

			gMap.drawRect(x, y, width, height);
		}
		gMap.dispose();
	}
}
