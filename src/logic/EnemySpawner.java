package logic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Random;

/**
 * Spawns an enemy periodically at its position.
 */
public class EnemySpawner extends Actor implements Serializable {
	/**
	 * Time it takes to spawn an enemy.
	 */
	private final int spawnTime = 5000 - (int) (new Random().nextDouble() * 1000);

	/**
	 * Time left to spawn an enemy.
	 */
	private int timeLeftToSpawn = spawnTime;

	/**
	 * An enemy spawner spawns enemies periodically.
	 * @param position position on the game map.
	 * @param gameMap the game map on which is this spawner.
	 */
	public EnemySpawner(Point2D.Double position, GameMap gameMap) {
		super(position, new Rectangle2D.Double(position.getX(), position.getY(), 1, 1));
		this.gameMap = gameMap;
		this.name = "EnemySpawner";
		gameMap.addActor(this);
		setImg(ActorType.ENEMYSPAWNER.getImgPath());
	}

	/**
	 * Spawns enemies periodically if the Player is around the enemy spawner.
	 * @param dtime time passed from the previous update.
	 */
	@Override
	public void tick(double dtime) {
		if (gameMap.getPlayer().getPosition().distance(this.getPosition()) < 10) {
			timeLeftToSpawn -= dtime;
			if (timeLeftToSpawn < 0) {
				timeLeftToSpawn = spawnTime;
				new Enemy(this.getPosition(), this.gameMap);
			}
		}
	}
}
