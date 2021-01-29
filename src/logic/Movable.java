package logic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * A class which extend the Movable class, will have a velocity vector attached to it.
 */
public abstract class Movable extends Actor {
	/** This is a velocity vector, which represents the Actor's speed. Time based. */
	protected Point2D.Double speed;

	/**
	 * A movable character has a speed and usually moves by some kind of correlation to speed.
	 * @param position The initial position of the Movable.
	 * @param collisionRect The initial collision box of the Movable.
	 */
	public Movable(Point2D.Double position, Rectangle2D.Double collisionRect) {
		super(position, collisionRect);
	}

	/**
	 * Set the vertical speed.
	 * @param x is the vertical speed of an actor.
	 */
	public void setXSpeed(double x) { speed.x = x; }

	/**
	 * Set the horizontal speed.
	 * @param y is the horizontal speed of an actor.
	 */
	public void setYSpeed(double y) { speed.y = y; }

	/**
	 * @return Returns the vertical speed of the character (x).
	 */
	public double getXSpeed() { return speed.x; }

	/**
	 * @return Returns the horizontal speed of the character (y).
	 */
	public double getYSpeed() { return speed.y; }

	/**
	 * Updates the position of the Movable by its speed.
	 * @param dtime time passed from the previous update.
	 */
	@Override
	public void tick(double dtime) {
		setPosition(new Point2D.Double(
				getPosition().getX() + speed.getX(),
				getPosition().getY() - speed.getY())
		);
	}
}
