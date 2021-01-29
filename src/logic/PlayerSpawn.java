package logic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * The player spawn on a game map defines where the player spawns on the start of the game.
 */
public class PlayerSpawn extends Actor implements Serializable {
	/**
	 * The player spawn is really just a position for now, where the player character first spawns.
	 * @param position the spawn position.
	 * @param gameMap the game map this spawn in on.
	 */
	public PlayerSpawn(Point2D.Double position, GameMap gameMap) {
		super(position, new Rectangle2D.Double(position.getX(),  position.getY(), 1, 1));
		this.gameMap = gameMap;
		gameMap.setPlayerSpawn(this);
		setImg(ActorType.PLAYERSPAWN.getImgPath());
		name = "PlayerSpawn";
	}
}
