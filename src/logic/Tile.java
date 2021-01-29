package logic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public abstract class Tile extends Actor implements Collidable, Serializable {
	private static final long serialVersionUID = 1L;

	// TODO: remove if not testing
	/**
	 * Only for testing purposes. Usually collided blocks have a red outline instead of green while testing.
	 */
	public boolean collided = false;

	/**
	 * Just calling a super to construct as an Actor.
	 * @param position the position of the tile.
	 * @param collisionRect the collision box of the tile.
	 */
	public Tile(Point2D.Double position, Rectangle2D.Double collisionRect) {
		super(position, collisionRect);
	}

	/**
	 * Sets collided's value back to false every time.
	 * @param dtime time passed from the previous update.
	 */
	@Override
	public void tick(double dtime) {
		collided = false;
	}
}
