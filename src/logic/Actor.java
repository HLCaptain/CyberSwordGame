package logic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An actor is a thing which can act and "is" on the game map. It can be updated, collided with,
 * also can be saved and loaded in. The game map also contains actors, which then can be handled in a common space.
 */
public abstract class Actor implements Updatable, Serializable, Collidable {
	/**
	 * The current position of an actor.
	 */
	private Point2D.Double position;

	/**
	 * The path of the image which represents the actor in a gui element.
	 */
	private String imgPath;

	/**
	 * The collision box of an actor.
	 */
	protected Rectangle2D.Double collisionRect;

	/**
	 * The game map the actor is in.
	 */
	protected GameMap gameMap;

	/**
	 * The name of the actor.
	 */
	protected String name;

	/**
	 * Sets up the actor with its position and collision box.
	 * @param position the location of the actor.
	 * @param collisionRect the collision box of the actor.
	 */
	public Actor(Point2D.Double position, Rectangle2D.Double collisionRect) {
		this.position = position;
		this.collisionRect = collisionRect;
	}

	/**
	 * Sets the position of the actor. The collision box's position follows the actor.
	 * @param position the new position of the actor.
	 */
	public void setPosition(Point2D.Double position) {
		this.collisionRect.setRect(new Rectangle2D.Double(
				collisionRect.getX() - this.position.getX() + position.getX(),
				collisionRect.getY() - this.position.getY() + position.getY(),
				collisionRect.getWidth(),
				collisionRect.getHeight()));
		this.position = position;
	}

	/**
	 * Setting the path to the image which will represent this actor in a gui.
	 * @param pathname the path to the image.
	 */
	public void setImg(String pathname) {
		imgPath = pathname;
	}

	/**
	 * The game map which this actor is associated with.
	 * @param gameMap the game map to set.
	 */
	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}

	/**
	 * Getting the image's path to load in the actor's image.
	 * @return the actor's image's path.
	 */
	public String getImgPath() {
		return this.imgPath;
	}

	/**
	 * Gets the actor's position.
	 * @return the actor's position.
	 */
	public Point2D.Double getPosition() {
		return position;
	}

	/**
	 * Gets the collision box of the actor.
	 * @return the collision box of the actor.
	 */
	public Rectangle2D.Double getCollisionRect() {
		return collisionRect;
	}

	/**
	 * Gets the actors on the game map, which collision box intersects this actor's collision box.
	 * @return List of actors.
	 */
	public List<Actor> getCollidingActors() {
		ArrayList<Actor> collidedActors = new ArrayList<>();
		for (Actor a : gameMap.getActors()) {
			if (getCollisionRect().intersects(a.getCollisionRect()))
				collidedActors.add(a);
		}
		for (ArrayList<Tile> a1 : gameMap.getGrid()) {
			for (Actor a2 : a1) {
				if (a2.getCollisionRect().intersects(this.getCollisionRect()))
					collidedActors.add(a2);
			}
		}
		return collidedActors;
	}

	/**
	 * Getting the name of an actor.
	 * Usually used for identifying.
	 * @return name of actor.
	 */
	public String getName() {
		return name;
	}
}
