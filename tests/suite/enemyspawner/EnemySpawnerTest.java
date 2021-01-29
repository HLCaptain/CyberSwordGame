package suite.enemyspawner;

import logic.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.*;

/**
 * Testing if a spawner is behaving accordingly meaning it spawns enemies periodically at its position.
 */
public class EnemySpawnerTest {

	Player player;
	GameMap gameMap;
	EnemySpawner enemySpawner;

	/**
	 * Setting up a player and an enemy spawner.
	 */
	@Before
	public void setUp() {
		gameMap = new GameMap(20, 15);
		new PlayerSpawn(new Point2D.Double(4, 4), gameMap);
		player = new Player(gameMap);
		enemySpawner = new EnemySpawner(new Point2D.Double(0, 0), gameMap);
	}

	/**
	 * Testing if it spawns the enemy.
	 */
	@Test
	public void tick() {
		gameMap.tick(1);
		// Just the player and player spawn and enemy spawn in there
		assertEquals(3, gameMap.getActors().size());
		// Spawning the enemy
		enemySpawner.tick(5001);
		gameMap.tick(1);
		assertEquals(4, gameMap.getActors().size()); // 3 + 1 = 4, So the enemy spawner spawned an enemy
	}
}