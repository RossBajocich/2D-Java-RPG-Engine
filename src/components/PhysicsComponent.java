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

	@Override
	public void update() {

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

	public Rectangle getBoundsRect(double x_move, double y_move) {
		return bounds.getRect((int) (x + x_move), (int) (y + y_move));
	}

	public Rectangle getBoundsRect() {
		return bounds.getRect((int) x, (int) y);
	}

	public Bounds getBounds() {
		return bounds;
	}

	public double getDistance(Member e) {
		if (e != null) {
			return Math.sqrt(Math.pow(x - ((PhysicsComponent)e.get(PhysicsComponent.class)).getX(), 2)
					+ Math.pow(y - ((PhysicsComponent)e.get(PhysicsComponent.class)).getY(), 2));
		}
		return 0;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Position getPosition() {
		return new Position(x, y);
	}

	public void setBounds(Bounds b) {
		bounds = b;
	}

	public Direction getDirection() {
		// TODO Auto-generated method stub
		return dir;
	}

}
