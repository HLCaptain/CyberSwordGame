package logic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static logic.Constants.*;

/**
 * The camera follows a target.
 * The GameCanvasDrawer uses a camera to render out the image based on the camera's position.
 */
public class Camera extends Actor {
	/**
	 * The camera follows the target, everywhere it goes.
	 * Clips onto the sides of the game map, so there is no glitching in the rendered view.
	 */
	private Actor target;

	/**
	 * Scale in pixel.
	 */
	private final double scale;

	/**
	 * Setting the position and trying to get the current player from the given game map.
	 * @param position the camera's position.
	 * @param gameMap the game map, which the camera is on.
	 */
	public Camera(Point2D.Double position, GameMap gameMap) {
		super(position, new Rectangle2D.Double(
				position.getX(),
				position.getY(),
				(double) Math.min(MAX_GAME_FRAME_WIDTH / TILE_SCALE, gameMap.getColumnNum()),
				(double) Math.min(MAX_GAME_FRAME_HEIGHT / TILE_SCALE, gameMap.getRowNum())));
		this.scale = TILE_SCALE;
		this.gameMap = gameMap;
		target = this.gameMap.getPlayer();
	}

	/**
	 * Setting the target to follow. The camera clips onto the actor's position.
	 * @param target the actor to follow.
	 */
	public void setTarget(Actor target) {
		if (target == null)
			return;
		this.target = target;
		tick(69);
	}

	/**
	 * Updates the camera's position according to the target's position.
	 * The camera's sides clips onto the sides of the game map, if the target is too close to the walls.
	 * @param dtime time passed from the previous update.
	 */
	@Override
	public void tick(double dtime) {
		// Setting the basic location of the camera.
		Point2D.Double newLoc = new Point2D.Double();
		newLoc.x = target.getPosition().getX() - this.getCollisionRect().getWidth() / 2 + 0.5;
		newLoc.y = target.getPosition().getY() - this.getCollisionRect().getHeight() / 2 + 0.5;

		// Modifying the location of the camera if target is too close to the sides.
		if ((target.getPosition().getX() - this.getCollisionRect().getWidth() / 2 + 0.5) < 0)
			newLoc.x = 0;
		if ((target.getPosition().getY() - this.getCollisionRect().getHeight() / 2 + 0.5) < 0)
			newLoc.y = 0;
		if (newLoc.getX() > gameMap.getColumnNum() - this.getCollisionRect().getWidth() - (double) (MAX_GAME_FRAME_WIDTH % TILE_SCALE) / TILE_SCALE)
			newLoc.x = gameMap.getColumnNum() - this.getCollisionRect().getWidth() - (double) (MAX_GAME_FRAME_WIDTH % TILE_SCALE) / TILE_SCALE;
		if (newLoc.getY() > gameMap.getRowNum() - this.getCollisionRect().getHeight() - (double) (MAX_GAME_FRAME_HEIGHT % TILE_SCALE) / TILE_SCALE)
			newLoc.y = gameMap.getRowNum() - this.getCollisionRect().getHeight() - (double) (MAX_GAME_FRAME_HEIGHT % TILE_SCALE) / TILE_SCALE;
		if (gameMap.getColumnNum() <= this.getCollisionRect().getWidth())
			newLoc.x = 0;
		if (gameMap.getRowNum() <= this.getCollisionRect().getHeight())
			newLoc.y = 0;

		// Setting the position at last with the modified values.
		setPosition(newLoc);
	}

	/**
	 * Getting the scale of the pixels that the camera renders in.
	 * Usually called by the renderer.
	 * @return scale in pixels.
	 */
	public double getScale() {
		return scale;
	}

	/**
	 * Getting the current target of the camera.
	 * @return target actor.
	 */
	public Actor getTarget() {
		return target;
	}
}
