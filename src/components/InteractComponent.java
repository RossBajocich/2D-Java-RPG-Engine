package components;

import utilities.Console;
import utilities.Console.in;
import elements.Member;

public class InteractComponent extends Component {
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

	public void interact() {
		// get closest InteractComponent and interact with it
		for (Member p : modify.getLevel().getElements()) {
			if (p.get(InteractComponent.class) != null
					&& p != modify
					&& ((PhysicsComponent) p.get(PhysicsComponent.class))
							.getDistance(modify) < interactDistance) {
				interact(p);
				((InteractComponent) p.get(InteractComponent.class))
						.onInteract(modify);
			}
		}
	}

	/*
	 * other system needs to check if the two components are close enough for an
	 * interact to happen, but this does not handle it
	 */
	protected void interact(Member target) {
		// nothing
		Console.log("basic interact() from " + modify.getName()
				+ " @ interactComponent being called on " + target.getName(),
				in.INFO);
	}

	/*
	 * private scope because only time something should call onInteract, is when
	 * interact is called on it through this component
	 */
	protected void onInteract(Member sender) {
		// nothing
		Console.log("basic interact() from " + sender.getName()
				+ " @ interactComponent being called on " + modify.getName(),
				in.INFO);
	}
}
