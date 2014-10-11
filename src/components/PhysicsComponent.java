package components;

import elements.Member;
import game.Level;
import game.Position;

import java.awt.Rectangle;

import loaders.Bounds;
import characters.Player;

public class PhysicsComponent extends Component {

	public enum Direction {
		UP, LEFT, RIGHT, DOWN
	}

	private Bounds bounds;
	private double x, y;
	private Direction dir;

	public PhysicsComponent() {
		super();
	}

	public Bounds getBounds() {
		return bounds;
	}

	public Rectangle getBoundsRect() {
		return bounds.getRect((int) x, (int) y);
	}

	public Rectangle getBoundsRect(double x_move, double y_move) {
		return bounds.getRect((int) (x + x_move), (int) (y + y_move));
	}

	public Direction getDirection() {
		// TODO Auto-generated method stub
		return dir;
	}

	public double getDistance(Member e) {
		if (e != null) {
			return Math.sqrt(Math.pow(
					x - (e.get(PhysicsComponent.class)).getX(), 2)
					+ Math.pow(y - (e.get(PhysicsComponent.class)).getY(), 2));
		}
		return 0;
	}

	public Position getPosition() {
		return new Position(x, y);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void move(Direction dir) {
		double change;
		if (modify instanceof Player) {
			change = ((Player) modify).getStats().moveSpeed;
		} else {
			change = 1.0;
		}

		Level level = modify.getLevel();

		switch (dir) {
		case UP:
			if (level.tryMove(this, x, y - change)) {
				y -= change;
			}
			break;
		case DOWN:
			if (level.tryMove(this, x, y + change)) {
				y += change;
			}
			break;
		case LEFT:
			if (level.tryMove(this, x - change, y)) {
				x -= change;
			}
			break;
		case RIGHT:
			if (level.tryMove(this, x + change, y)) {
				x += change;
			}
			break;
		default:
			break;
		}
	}

	public void setBounds(Bounds b) {
		bounds = b;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public void update() {

	}

}
