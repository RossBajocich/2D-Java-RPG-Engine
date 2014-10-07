package utilities;

public interface Functor {
	/*
	 * If the objects are known to you, just use the variables directly in the
	 * implementation
	 */
	public void execute();

	/*
	 * If the objects are not known to you, cast the Object and use the o
	 * variable
	 */
	public void execute(Object o);
}
