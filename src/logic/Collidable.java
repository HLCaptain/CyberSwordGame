package logic;

/**
 * All class which implements this interface is able to collide with one another.
 * Invoking the other's collideWith methods can have an effect on the invoker object.
 * Also, when left the collision, once a collideExit method may be called.
 */
public interface Collidable {
	/**
	 * If collision occurs, this method is called
	 * on the object whose class implements this
	 * interface.
	 * @param entity the actor who collided with the object.
	 */
	default void collideWith(Entity entity) { }

	/**
	 * Colliding with a pistol bullet.
	 * @param pistolBullet is the bullet which collided.
	 */
	default void collideWith(PistolBullet pistolBullet) { }

	/**
	 * Colliding with an enemy.
	 * @param enemy is the enemy which collided.
	 */
	default void collideWith(Enemy enemy) { }

	/**
	 * Colliding with the player.
	 * @param player the player which collided.
	 */
	default void collideWith(Player player) { }

	/**
	 * On collision exit, some class invokes this method to interact with the entity collided.
	 * @param entity the entity which is not colliding anymore.
	 */
	default void collideExit(Entity entity) { }

	/**
	 * Handling the collision of an object.
	 * Usually called in updates, to get the colliding objects and do something with them.
	 */
	default void collisionHandling() { }
}
