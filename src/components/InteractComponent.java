package components;

import characters.Player;

public class InteractComponent extends Component {

	private long lastInteract = 0;
	private float interactWait = 0.5f;
	private double interactDistance = 100;

	public InteractComponent() {
		super();
	}

	@Override
	public void update() {

	}

	public double getInteractDistance() {
		return interactDistance;
	}

	public void interact(){
		//get closest InteractComponent and interact with it
		
	}
	
	/*
	 * other system needs to check if the two components are close enough for an
	 * interact to happen, but this does not handle it
	 */
	public void interact(InteractComponent i) {
		if ((System.currentTimeMillis() - lastInteract) > interactWait * 1000) {
			lastInteract = System.currentTimeMillis();
		}
		if (i != null) {
			interact(i);
			i.onInteract(this);
		}
	}

	/*
	 * private scope because only time something should call onInteract, is when
	 * interact is called on it through this component
	 */
	private void onInteract(InteractComponent interactComponent) {

	}

	public void onInteract(Player p) {
		
	}
}
