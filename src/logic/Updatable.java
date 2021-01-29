package logic;

/**
 * Updatable objects have a tick() method which if called, used for updating an object.
 */
public interface Updatable {
	/**
	 * This method is usually called by the internal game clock to update all objects.
	 * @param dtime time passed from the previous update.
	 */
	default void tick(double dtime) { }
}
