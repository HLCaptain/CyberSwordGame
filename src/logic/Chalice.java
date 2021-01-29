package logic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * The Chalice is the object, which the player has to get to. It ends the game with the player winning it.
 */
public class Chalice extends Actor {
	/**
	 * The Chalice has a position and a representing image, the goal of the game is to get the Chalice.
	 * @param position the position of the chalice on the map.
	 * @param gameMap the game map the Chalice is on.
	 */
	public Chalice(Point2D.Double position, GameMap gameMap) {
		super(position, new Rectangle2D.Double(position.getX() + 0.25, position.getY() + 0.25, 0.5, 0.5));
		this.gameMap = gameMap;
		gameMap.addActor(this);
		setImg(ActorType.CHALICE.getImgPath());
	}

	/**
	 * If the player is colliding with this actor, then the player won the game.
	 * @param player the player who is colliding with this actor.
	 */
	@Override
	public void collideWith(Player player) {
		gameMap.gameWon();
		System.out.println("You Won!");
	}
}
