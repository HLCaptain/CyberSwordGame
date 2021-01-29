package logic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * A player shot bullet which damages enemies if it hit them, cannot penetrate walls (gets destroyed by them).
 */
public class PistolBullet extends Movable {
	/**
	 * Damage dealt to the hit entities.
	 */
	private int damage = 20;

	/**
	 * Bullet has a speed vector (with that a direction as well) and a starting position. It is created on the game map.
	 * @param position the starting position of the bullet.
	 * @param speed the speed of the bullet.
	 * @param gameMap the bullet is on this game map scene.
	 */
	public PistolBullet(Point2D.Double position, Point2D.Double speed, GameMap gameMap) {
		super(position, new Rectangle2D.Double(position.getX() + 0.45, position.getY() + 0.45, 0.1, 0.1));
		this.speed = new Point2D.Double(speed.getX(), speed.getY());
		this.gameMap = gameMap;
		this.gameMap.addActorToBuffer(this);
		setImg(ActorType.PISTOLBULLET.getImgPath());
	}

	/**
	 * Collides with every actor in the way.
	 */
	@Override
	public void collisionHandling() {
		for (Actor a : getCollidingActors()) {
			a.collideWith(this);
		}
	}

	/**
	 * Handles collisions and updates position with each update cycle.
	 * @param dtime time passed from the previous update.
	 */
	@Override
	public void tick(double dtime) {
		super.tick(dtime);

		collisionHandling();
	}

	/**
	 * If hit an enemy or ground tile, then the bullet is destroyed.
	 */
	public void die() {
		this.gameMap.removeActorFromBuffer(this);
	}

	/**
	 * @return the damage of the bullet.
	 */
	public int getDamage() {
		return damage;
	}
}
