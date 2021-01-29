package logic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * The ground prevents the entities to infinitely fall, giving them a solid ground to walk on.
 */
public class GroundTile extends Tile implements Serializable {
	/**
	 * Need for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Entities can stand on this tile, acts like a solid ground on the map.
	 * @param position position of the ground.
	 * @param collisionRect collision box of the ground.
	 * @param gameMap the tile is on this game map's grid.
	 */
	public GroundTile(Point2D.Double position, Rectangle2D.Double collisionRect, GameMap gameMap) {
		super(position, collisionRect);
		setImg(TileType.GROUND.getImgPath());
		name = "GroundTile";
		this.gameMap = gameMap;
	}

	/**
	 * Destroying the bullet after it hit this rock solid ground tile. No impact on anything.
	 * @param pistolBullet the bullet which hit the ground.
	 */
	@Override
	public void collideWith(PistolBullet pistolBullet) {
		// TODO
		collided = true;
		pistolBullet.die();
	}

	/**
	 * Lets the entity walk on the tile without infinitely falling. Also doesn't let entities pass a ground wall.
	 * Sets their grounded boolean to true accordingly to the type of collision.
	 * @param entity the actor who collided with the object.
	 */
	@Override
	public void collideWith(Entity entity) {
		// The intersection's rectangle
		Rectangle2D.Double intersectionRect = (Rectangle2D.Double) collisionRect.createIntersection(entity.getCollisionRect());

		// The offset we will set the entity's position with.
		double offsetX;
		double offsetY;

		// Is it a side collision or just falling and hitting the ground or jumping and colliding with the entity's head?
		if (intersectionRect.getHeight() > intersectionRect.getWidth()) {
			offsetY = 0;
			if (this.getCollisionRect().getCenterX() < intersectionRect.getCenterX())
				offsetX = intersectionRect.getWidth();
			else
				offsetX = - intersectionRect.getWidth();
		} else {
			offsetX = 0;
			if (this.getCollisionRect().getCenterY() > intersectionRect.getCenterY()) {
				if (entity.getYSpeed() <= 0)
					entity.grounded = true;
				offsetY = - intersectionRect.getHeight();
			} else {
				offsetY = intersectionRect.getHeight();
				if (entity.getYSpeed() > 0) {
					if (gameMap.getRowNum() - 1 > getPosition().getY() && gameMap.getGrid().get((int) getPosition().getY()).get((int) getPosition().getX()).getClass() == EmptyTile.class) {
						// im too lazy to reverse this if
					} else entity.setYSpeed(0);
				}
			}

		}

		// Setting new position accordingly.
		entity.setPosition(new Point2D.Double(
				entity.getPosition().getX() + offsetX,
				entity.getPosition().getY() + offsetY)
		);
	}

	/**
	 * Redirecting to the collision with the entity.
	 * @param enemy is the enemy which collided.
	 */
	@Override
	public void collideWith(Enemy enemy) { collideWith((Entity) enemy); }

	/**
	 * Redirecting to the collision with the entity.
	 * @param player is the player which collided.
	 */
	@Override
	public void collideWith(Player player) { collideWith((Entity) player); }

	/**
	 * Using this for testing, just seeing if the tile is colliding.
	 * @param entity the entity which is not colliding anymore.
	 */
	@Override
	public void collideExit(Entity entity) {
		collided = false;
	}
}
