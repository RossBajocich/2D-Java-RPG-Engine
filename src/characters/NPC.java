package characters;

import game.Position;

public class NPC extends Player {

	Player chasing;
	private double chaseDistance = 150;
	boolean attacked = false;
	boolean found = false;
	long lastChase;
	private long chaseForget = 3 * 1000;

	public NPC(double x, double y, int width, int height) {
		super(x, y, width, height);
	}

	public NPC() {
		// TODO Auto-generated constructor stub
		super();
	}

	public void onAttack(Player p) {
		attacked = true;
		chasing = p;
		if (!dead) {
			attack(p);
		}
	}

	private void waitForget(long ms) {
		if (System.currentTimeMillis() - lastChase > chaseForget) {
			chasing = null;
			attacked = false;
		}
	}

	public void step() {
		if (!dead) {
			if (attacked) {
				found = false;
				if (getDistance(chasing) > chaseDistance) {
					waitForget(3 * 1000);
				}
				if (!found && attacked) {
					for (Player p : level.getPlayers()) {
						if (p == this) {
							continue;
						}
						if (getDistance(p) <= chaseDistance) {
							found = true;
							chasing = p;
							move();
						}
					}
				}
			}
		}
	}

	public void move() {
		if (chasing != null) {
			if (getDistance(chasing) < attackDistance) {
				attack(chasing);
			} else {
				moveTowards(chasing);
			}
		}
	}

	private void moveTowards(Player target) {
		old = new Position(x, y);

		double ratio = stats.moveSpeed / getDistance(target);
		this.x += (target.getX() - x) * ratio;
		this.y += (target.getY() - y) * ratio;

		level.notifyMove(this);
		// level.update();
	}

	public void onClick(int x, int y, button b) {
		
	}
}
