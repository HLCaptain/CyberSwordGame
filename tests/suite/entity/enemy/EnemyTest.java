package suite.entity.enemy;

import logic.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.*;

/**
 * Testing use cases what can happen to all enemies.
 */
public class EnemyTest {

	Player player;
	Enemy enemy;
	GameMap gameMap;
	double deltaDouble = 0.001;

	/**
	 * Setting up a player and enemy on the game map.
	 */
	@Before
	public void setUp() {
		gameMap = new GameMap(20, 15);
		new PlayerSpawn(new Point2D.Double(4, 4), gameMap);
		player = new Player(gameMap);
		enemy = new Enemy(new Point2D.Double(1, 1), gameMap);
		enemy.grounded = true;
		gameMap.tick(0);
	}

	/**
	 * The enemy should set its target as the player on the game map.
	 * Also, it should add itself to the game map's scene.
	 */
	@Test
	public void construction() {
		assertEquals(player, enemy.getTarget());
		assertEquals("Enemy", enemy.getName());
		assertTrue(gameMap.getActors().contains(enemy));
	}

	/**
	 * The enemy should heal properly! Its health points never goes above its maximum hp.
	 */
	@Test
	public void heal() {
		assertEquals(enemy.getHp(), enemy.getMaxHp(), deltaDouble);
		enemy.takeDmg(2);
		enemy.heal(1);
		assertEquals(enemy.getMaxHp() - 2 + 1, enemy.getHp(), deltaDouble);
		enemy.heal(enemy.getMaxHp());
		assertEquals(enemy.getMaxHp(), enemy.getHp(), deltaDouble);
	}

	/**
	 * Testing damage taking, if hit critically (current hp - dmg less than 0) it should be 0.
	 */
	@Test
	public void takeDmg() {
		enemy.takeDmg(1);
		assertTrue(enemy.getHp() < enemy.getMaxHp());
		enemy.takeDmg(enemy.getMaxHp());
		assertEquals(0, enemy.getHp(), deltaDouble);
	}

	/**
	 * If the enemy died, then he removes himself from the actors.
	 */
	@Test
	public void die() {
		enemy.takeDmg(100);
		assertEquals(0, enemy.getHp(), deltaDouble);
		gameMap.tick(0);
		assertFalse(gameMap.getActors().contains(enemy));
	}

	/**
	 * The enemy should be hurt after getting shot with a bullet. (So its hp goes down.)
	 */
	@Test
	public void collideWith() {
		assertEquals(enemy.getHp(), enemy.getMaxHp(), deltaDouble);
		enemy.collideWith(new PistolBullet(new Point2D.Double(0, 0), new Point2D.Double(0, 0), gameMap));
		assertTrue(enemy.getHp() < enemy.getMaxHp());
	}
}