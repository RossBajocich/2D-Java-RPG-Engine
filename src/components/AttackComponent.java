package components;

import java.util.Random;

import utilities.Clock;
import utilities.Functor;
import utilities.Stats;
import characters.Player;
import elements.Member;

public class AttackComponent extends Component {

	private Stats stats;
	private int health, mana;
	private long lastAttack = 0;
	private boolean dead;
	private static double attackDistance = 75;

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
				((Player) o).getContainer().dropItems();
				((Member) o).remove();
			}
		}, this);
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

	public void attack() {
		// get closest player or whatever and call attack() on it
	}

	public void attack(Player p) {
		// TODO Auto-generated method stub
		if (dead) {
			return;
		}
		if (!p.getAttack().isDead()) {
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
					p.getAttack().addHealth(-1 * atk);
				}
				lastAttack = System.currentTimeMillis();
			}
		}

	}

	public void onAttack(Player p) {
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
