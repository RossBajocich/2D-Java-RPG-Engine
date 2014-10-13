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

	public void addHealth(int h) {
		if (health + h <= 0) {
			health = 0;
			die();
		} else if (health + h >= stats.health) {

		} else {
			health += h;
		}
	}

	public boolean attack() {
		// get closest player or whatever and call attack() on it
		for (Member p : modify.getLevel().getElements()) {
			if (p.get(AttackComponent.class) != null) {
				if (p.get(AttackComponent.class) == this) {
					continue;
				}
				if ((modify.get(PhysicsComponent.class)).getDistance(p) < this.attackDistance) {
					// Should i call onAttack() here? Or inside attack()?
					attack((Player) p);
					(p.get(AttackComponent.class)).onAttack(this);
					return true;
				}
			}
		}
		return false;
	}

	public void attack(Player p) {
		if (dead) {
			return;
		}
		if (!(p.get(AttackComponent.class)).isDead()) {
			// TODO: make this check a timer...
			if ((System.currentTimeMillis() - lastAttack) > stats.speed * 1000) {
				// TODO: make this "miss" code better
				Random r = new Random();
				int i = r.nextInt(100);

				if (i <= p.getStats().def) {
					// missed
					Console.log("Player " + modify.getName()
							+ " missed attack against " + p.getName(), in.INFO);
				} else {
					int attack = ((Player) modify).getStats().attack;
					if (attack < 0) {
						attack = 0;
					}
					(p.get(AttackComponent.class)).addHealth(-1 * attack);
					Console.log("THis player attacked " + p.getName(), in.INFO);
				}
				lastAttack = System.currentTimeMillis();
			}
		}

	}

	public double getAttackDistance() {
		return attackDistance;
	}

	public int getHealth() {
		return health;
	}

	public int getMana() {
		return mana;
	}

	public double getMaxHealth() {
		return stats.health;
	}

	public double getMaxMana() {
		return stats.mana;
	}

	public double getWaitRatio() {
		double diff = (System.currentTimeMillis() - lastAttack);
		if (diff > stats.speed * 1000) {
			return 1;
		}
		return diff / ((double) stats.speed * 1000);
	}

	public boolean isDead() {
		return dead;
	}

	public void onAttack(AttackComponent p) {
		if (!dead) {
			// attack(p);
		}
	}

	public void setHealth(int h) {
		this.health = h;
	}

	public void setMana(int m) {
		mana = m;
	}

	@Override
	public void update() {
		stats = ((Player) modify).getStats();
	}

	private void die() {
		dead = true;
		Clock.createTimer(2000, new Functor() {
			@Override
			public void execute() {
				if (((Player) modify).getContainer() != null) {
					((Player) modify).getContainer().dropItems();
				}
				modify.getLevel().removeMember(modify);
			}

			@Override
			public void execute(Object o) {
			}
		});
	}
}
