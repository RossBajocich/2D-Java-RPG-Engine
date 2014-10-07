package components;

import java.util.Random;

import utilities.Clock;
import utilities.Console;
import utilities.Console.in;
import utilities.Functor;
import utilities.Stats;
import characters.Player;
import elements.Member;

public class AttackComponent extends Component {

	private Stats stats;
	private int health, mana;
	private long lastAttack = 0;
	private boolean dead;
	private double attackDistance = 75;

	public AttackComponent() {
		super();
	}

	@Override
	public void update() {
		stats = ((Player) modify).getStats();
	}

	public double getAttackDistance() {
		return attackDistance;
	}

	public void addHealth(int h) {
		if (health + h <= 0) {
			health = 0;
			die();
		} else if (health + h >= stats.health) {

		} else {
			health += h;
		}
	}

	private void die() {
		dead = true;
		Clock.createTimer(2000, new Functor() {
			@Override
			public void execute(Object o) {
			}

			@Override
			public void execute() {
				if (((Player) modify).getContainer() != null) {
					((Player) modify).getContainer().dropItems();
				}
				((Member) modify).getLevel().remove(modify);
			}
		});
	}

	public boolean isDead() {
		return dead;
	}

	public void setMana(int m) {
		mana = m;
	}

	public void setHealth(int h) {
		this.health = h;
	}

	public int getHealth() {
		return health;
	}

	public int getMana() {
		return mana;
	}

	public double getMaxMana() {
		return stats.mana;
	}

	public double getMaxHealth() {
		return stats.health;
	}

	public boolean attack() {
		// get closest player or whatever and call attack() on it
		for (Member p : modify.getLevel().getElements()) {
			if (p.getType().equalsIgnoreCase("player")) {
				if (((Player) p).get(AttackComponent.class) == this) {
					continue;
				}
				if (((PhysicsComponent) modify.get(PhysicsComponent.class)).getDistance(p) < this.attackDistance) {
					// Should i call onAttack() here? Or inside attack()?
					attack((Player) p);
					((AttackComponent) ((Player) p).get(AttackComponent.class)).onAttack(this);
					return true;
				}
			}
		}
		return false;
	}

	public void attack(Player p) {
		Console.log("THis player attacked " + p.getName(), in.INFO);
		if (dead) {
			return;
		}
		if (!((AttackComponent) p.get(AttackComponent.class)).isDead()) {
			// TODO: make this check a timer...
			if ((System.currentTimeMillis() - lastAttack) > stats.speed * 1000) {
				// TODO: make this "miss" code better
				Random r = new Random();
				int i = r.nextInt(100);

				if (i <= p.getStats().def) {
					// missed
					// Console.log("Player " + modify.getName()
					// + " missed attack against " + p.getName(), in.INFO);
				} else {
					int atk = p.getStats().attack;
					if (atk < 0) {
						atk = 0;
					}
					((AttackComponent) p.get(AttackComponent.class)).addHealth(-1 * atk);
				}
				lastAttack = System.currentTimeMillis();
			}
		}

	}

	public void onAttack(AttackComponent p) {
		if (!dead) {
			// attack(p);
		}
	}

	public double getWaitRatio() {
		double diff = (System.currentTimeMillis() - lastAttack);
		if (diff > stats.speed * 1000) {
			return 1;
		}
		return diff / ((double) stats.speed * 1000);
	}
}
