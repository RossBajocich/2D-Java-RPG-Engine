package graphics;

import elements.Member;
import game.Level;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

import characters.Player;

import components.AttackComponent;
import components.GraphicsComponent;
import components.PhysicsComponent;

public class ReferenceFrame {

	protected int x, y, width, height;
	protected Level current_level;
	boolean drawBounds = true;

	public ReferenceFrame(int x, int y, int width, int height,
			Level current_level) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.current_level = current_level;
	}

	public void draw(int window_x, int window_y, int window_width,
			int window_height, Graphics g, Level level) {

		g.drawImage(
				level.getBackground().getSubimage(this.x, this.y, this.width,
						this.height), 0, 0, window_width, window_height, null);

		for (List<Member> l : level.getRenders().values()) {
			for (Member m : l) {
				if ((m.get(GraphicsComponent.class)).isWithin(this)) {
					drawMember(window_x, window_y, window_width, window_height,
							m, g);

					if (m instanceof Player && !((Player) m).isMainPlayer()) {
						drawBars(window_x, window_y, window_width,
								window_height, (Player) m, g);
					}
				}
			}
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	private void drawBars(int window_x, int window_y, int window_width,
			int window_height, Player p, Graphics gMain) {
		PhysicsComponent physics = p.get(PhysicsComponent.class);

		int barW = 50, barH = 15;

		int x1 = (int) (physics.getX() - this.x) - barW;
		int y1 = (int) (physics.getY() - this.y) - barH;

		gMain.setColor(Color.red);
		gMain.fillRect((x1 * window_width) / this.width, (y1 * window_height)
				/ this.height, (int) (barW * window_width) / this.width,
				(int) (barH * window_height) / this.height);

		gMain.setColor(Color.green);
		double temp = ((((double) (p.get(AttackComponent.class)).getHealth() / (p
				.get(AttackComponent.class)).getMaxHealth()) * barW) * window_width)
				/ this.width;
		gMain.fillRect((x1 * window_width) / this.width, (y1 * window_height)
				/ this.height, (int) temp, (int) (barH * window_height)
				/ this.height);

		gMain.setColor(Color.black);
		gMain.drawRect((x1 * window_width) / this.width, (y1 * window_height)
				/ this.height, (int) (barW * window_width) / this.width,
				(int) (barH * window_height) / this.height);

		gMain.drawString("hp: " + (p.get(AttackComponent.class)).getHealth()
				+ " / " + (p.get(AttackComponent.class)).getMaxHealth(),
				(((x1 + 5) * window_width) / this.width),
				(((y1 + 12) * window_height) / this.height));

		y1 = y1 + barH;
		barH = 10;

		gMain.setColor(Color.white);
		gMain.fillRect((x1 * window_width) / this.width, (y1 * window_height)
				/ this.height, (int) barW, barH);

		gMain.setColor(Color.blue);
		temp = (((p.get(AttackComponent.class)).getWaitRatio() * barW) * window_width)
				/ this.width;
		gMain.fillRect((x1 * window_width) / this.width, (y1 * window_height)
				/ this.height, (int) temp, barH);

	}

	private void drawMember(int window_x, int window_y, int window_width,
			int window_height, Member e, Graphics gMain) {
		PhysicsComponent physics = e.get(PhysicsComponent.class);
		GraphicsComponent graphics = e.get(GraphicsComponent.class);

		int x = (int) (physics.getX() - this.x) + window_x;
		int y = (int) (physics.getY() - this.y) + window_y;

		if (graphics.getImage() != null) {
			gMain.drawImage(graphics.getImage(), (x * window_width)
					/ this.width, (y * window_height) / this.height,
					(int) (graphics.getWidth() * window_width) / this.width,
					(int) (graphics.getHeight() * window_height) / this.height,
					null);
			if (drawBounds) {
				// debug
				gMain.setColor(Color.red);
				((Graphics2D) gMain).setStroke(new BasicStroke(2));
				Rectangle r = (e.get(PhysicsComponent.class)).getBoundsRect();
				gMain.drawRect((int) ((r.x - this.x) * window_width)
						/ this.width, (int) ((r.y - this.y) * window_height)
						/ this.height, (int) (r.width * window_width)
						/ this.width, (int) (r.height * window_height)
						/ this.height);
			}
		}
	}
}
