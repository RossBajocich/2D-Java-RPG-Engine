package components;

import utilities.Console;
import utilities.Console.in;
import characters.Player;
import elements.Member;

public class NPCInput extends InputComponent {

	Player modify;
	Player chasing;
	private double chaseDistance = 150;
	long lastChase;
	private long chaseForget = 3 * 1000;

	public NPCInput() {
		super();
	}

	public void setModify(Member m) {
		if (m instanceof Player) {
			this.modify = (Player) m;
		} else {
			Console.log("Cannot have non player modifier for NPCInput!",
					Console.in.ERROR);
		}
	}

	public void setChasing(Player p) {
		this.chasing = p;
		lastChase = System.currentTimeMillis();
		Console.log("Player " + p.getName() + " is now being chased", in.INFO);
	}

	@Override
	public void update() {
		if (System.currentTimeMillis() - lastChase > chaseForget) {
			chasing = null;
			Console.log("chasing = null, lastChase:" + lastChase + " minus "
					+ (System.currentTimeMillis() - lastChase), Console.in.INFO);
		}
		if (!((AttackComponent) modify.get(AttackComponent.class)).isDead()) {
			if (chasing != null) {
				if (((PhysicsComponent) modify.get(PhysicsComponent.class))
						.getDistance(chasing) > chaseDistance) {
					lastChase = System.currentTimeMillis();
				} else {
					move();
				}
			}
		}
	}

	public void move() {
		if (chasing != null) {
			if (((PhysicsComponent) modify.get(PhysicsComponent.class))
					.getDistance(chasing) < ((AttackComponent) modify
					.get(AttackComponent.class)).getAttackDistance()) {
				((AttackComponent) modify.get(AttackComponent.class))
						.attack(chasing);
			} else {
				moveTowards(chasing);
			}
		}
	}

	private void moveTowards(Player target) {
		PhysicsComponent physics = modify.get(PhysicsComponent.class);

		double ratio = modify.getStats().moveSpeed
				/ physics.getDistance(target);
		physics.setX(physics.getX()
				+ (((PhysicsComponent) target.get(PhysicsComponent.class))
						.getX() - physics.getX()) * ratio);
		physics.setY(physics.getY()
				+ (((PhysicsComponent) target.get(PhysicsComponent.class))
						.getY() - physics.getY()) * ratio);
	}

}
