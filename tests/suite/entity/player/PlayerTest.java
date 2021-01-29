package suite.entity.player;

import logic.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.*;

/**
 * Testing use cases which can happen to a player.
 */
public class PlayerTest {

	Player player;
	GameMap gameMap;
	double deltaDouble = 0.001;

	/**
	 * Player spawning on a game map grounded and with '1' speed on the x axis.
	 */
	@Before
	public void setUp() {
		gameMap = new GameMap(20, 15);
		new PlayerSpawn(new Point2D.Double(4, 4), gameMap);
		player = new Player(gameMap);
		player.setXSpeed(1);
		player.grounded = true;
	}

	/**
	 * Testing proper construction.
	 * Also testing if everything set in the setUp is set.
	 */
	@Test
	public void construction() {
		assertEquals(new Point2D.Double(4, 4), player.getPosition());
		assertSame(gameMap.getPlayer(), player);
		assertEquals("Player", player.getName());
		assertEquals(1, player.getXSpeed(), deltaDouble);
		assertEquals(player.getHp(), player.getMaxHp(), deltaDouble);
	}

	/**
	 * The player should update its position according to its speed.
	 */
	@Test
	public void tick() {
		player.tick(1);
		assertEquals(5, player.getPosition().getX(), deltaDouble);
		player.grounded = false;
		player.tick(1);
		assertEquals(4 + Constants.GRAVITY * Constants.VELOCITY_CONST / 2, player.getPosition().getY(), deltaDouble);
	}

	/**
	 * A player should heal to full hp at maximum. Not more.
	 */
	@Test
	public void heal() {
		player.takeDmg(25);
		player.heal(20);
		assertEquals(95, player.getHp(), deltaDouble);
		player.heal(30);
		assertEquals(player.getMaxHp(), player.getHp(), deltaDouble);
	}

	/**
	 * The player's hp should cap at a 0 minimum if taking critical damage (current hp - dmg less than 0);
	 */
	@Test
	public void takeDmg() {
		player.takeDmg(25);
		assertEquals(75, player.getHp(), deltaDouble);
		player.takeDmg(100);
		assertEquals(0, player.getHp(), deltaDouble);
	}

	/**
	 * The player when dying, should remove itself from the scene of the game map.
	 */
	@Test
	public void die() {
		player.takeDmg(100);
		assertEquals(0, player.getHp(), deltaDouble);
		assertFalse(gameMap.getActors().contains(player));
	}

	/**
	 * Colliding with an enemy causes damage dealt to the player.
	 */
	@Test
	public void collideWith() {
		assertEquals(player.getHp(), player.getMaxHp(), deltaDouble);
		player.collideWith(new Enemy(new Point2D.Double(0, 0), gameMap));
		assertEquals(80, player.getHp(), deltaDouble);
	}
}