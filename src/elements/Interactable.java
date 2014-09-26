package elements;

public interface Interactable {

	public abstract void interact(Interactable i);

	public abstract void onInteract(Interactable i);

	public abstract double getX();
	public abstract double getY();

	public abstract String getName();
}
