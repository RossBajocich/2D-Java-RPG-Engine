package components;

import utilities.Console;
import characters.Player;
import elements.Member;

public class NPCInput extends InputComponent {

	Player modify;
	Player chasing;
	private double chaseDistance = 150;
	boolean attacked = false;
	boolean found = false;
	long lastChase;
	private long chaseForget = 3 * 1000;

	public NPCInput() {
		super();
	}

	public void setModify(Member m){
		if(m instanceof Player){
		this.modify = (Player) m;
		}else{
			Console.log("Cannot have non player modifier for NPCInput!", Console.in.ERROR);
		}
	}
	
	@Override
	public void update() {
		if (System.currentTimeMillis() - lastChase > chaseForget) {
			chasing = null;
			attacked = false;
		}
		if (!modify.getAttack().isDead()) {
			if (attacked) {
				if (modify.getPhysics().getDistance(chasing) > chaseDistance) {
					lastChase = System.currentTimeMillis();
				} else {
					move();
				}
			}
		}
	}

	public void move() {
		if (chasing != null) {
			if (modify.getPhysics().getDistance(chasing) < modify.getAttack()
					.getAttackDistance()) {
				modify.getAttack().attack(chasing);
			} else {
				moveTowards(chasing);
			}
		}
	}

	private void moveTowards(Player target) {
		PhysicsComponent physics = modify.getPhysics();

		double ratio = modify.getStats().moveSpeed
				/ physics.getDistance(target);
		physics.setX(physics.getX()
				+ (target.getPhysics().getX() - physics.getX()) * ratio);
		physics.setY(physics.getY()
				+ (target.getPhysics().getY() - physics.getY()) * ratio);
		/*
		 * level.notifyMove(physics); level.update();
		 */
	}

}
