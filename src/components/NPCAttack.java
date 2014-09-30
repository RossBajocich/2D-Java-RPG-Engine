package components;

import characters.Player;

public class NPCAttack extends AttackComponent {

	boolean attacked;
	Player chasing;
	
	public NPCAttack() {
		super();
	}

	public void onAttack(Player p){
		attacked = true;
		chasing = p;
		if (!isDead()) {
			attack(p);
		}
	}
	
	
}
