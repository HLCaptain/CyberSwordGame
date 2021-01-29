package logic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * The empty tile is like "air". It lets through entities and bullets,
 * but doesn't do anything much than that. It's empty...
 */
public class EmptyTile extends Tile implements Serializable {
	/**
	 * Need for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * An empty tile has a position and collision box as well. It collides with the entities and has an effect on its movement.
	 * @param position position of the empty tile.
	 * @param collisionRect collision box of the empty tile.
	 * @param gameMap game map in which is this empty tile.
	 */
	public EmptyTile(Point2D.Double position, Rectangle2D.Double collisionRect, GameMap gameMap) {
		super(position, collisionRect);
		setImg(TileType.EMPTY.getImgPath());
		name = "EmptyTile";
		this.gameMap = gameMap;
	}

	/**
	 * This is usually used in testing to check if collided something or not.
	 * @param entity the entity which is not colliding anymore.
	 */
	@Override
	public void collideExit(Entity entity) {
		collided = false;
	}

	/**
	 * Checks if the tile under this tile is an empty tile, if yes, and the entity is fully in this tile,
	 * then the entity is not grounded. (So it falls.)
	 * @param entity the actor who collided with the object.
	 */
	@Override
	public void collideWith(Entity entity) {
		collided = true;
		if (this.collisionRect.createIntersection(entity.getCollisionRect()).getWidth() == entity.getCollisionRect().getWidth()) {
			if ((int) getPosition().getY() < gameMap.getRowNum() - 1) {
				if (gameMap.getGrid().get((int) getPosition().getY() + 1).get((int) getPosition().getX()).getName().equals(this.getName()))
					entity.grounded = false;
			}
		}
	}

	/**
	 * Redirecting to the proper collide method.
	 * @param enemy is the enemy which collided.
	 */
	@Override
	public void collideWith(Enemy enemy) { collideWith((Entity) enemy); }

	/**
	 * Redirecting to the proper collide method.
	 * @param player is the player which collided.
	 */
	@Override
	public void collideWith(Player player) { collideWith((Entity) player); }
}
