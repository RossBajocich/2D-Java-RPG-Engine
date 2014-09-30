package components;

import elements.Member;
import game.Level;
import game.Position;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import loaders.Bounds;
import characters.Player;

public class PhysicsComponent extends Component {

	public enum Direction {
		UP, LEFT, RIGHT, DOWN
	}

	private List<Position> collision_overflow = new ArrayList<Position>();
	private Level level;
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

	public boolean collide(PhysicsComponent p) {
		return bounds.intersects(p.getBounds());
	}

	public Rectangle getBounds(double x_move, double y_move) {
		return bounds.getBounds((int) (x + x_move), (int) (y + y_move));
	}

	public Rectangle getBounds() {
		return bounds.getBounds((int) x, (int) y);
	}

	public double getDistance(Member e) {
		if (e != null) {
			return Math.sqrt(Math.pow(x - e.getPhysics().getX(), 2)
					+ Math.pow(y - e.getPhysics().getY(), 2));
		}
		return 0;
	}

	public void setLevel(Level l) {
		this.level = l;
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

	public void setBounds(Bounds r) {
		bounds = r;
	}

	public Direction getDirection() {
		// TODO Auto-generated method stub
		return dir;
	}

}
