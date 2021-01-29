package suite.camera;

import logic.Camera;
import logic.GameMap;
import logic.Player;
import logic.PlayerSpawn;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.*;

/**
 * Testing the camera's target finding and tracking feature.
 */
public class CameraTest {

	Camera camera;
	Player player;
	GameMap gameMap;

	/**
	 * Setting up the game map and inside it a player spawner and a player.
	 * Pay attention to the camera's position because it has no relation to the player's position what so ever.
	 */
	@Before
	public void setUp() {
		gameMap = new GameMap(40, 40);
		new PlayerSpawn(new Point2D.Double(20, 20), gameMap);
		player = new Player(gameMap);
		player.grounded = true;
		camera = new Camera(new Point2D.Double(10, 10), gameMap);
	}

	/**
	 * The camera should find the player in a game map and set it as a target to track.
	 */
	@Test
	public void foundTarget() {
		assertEquals(player, camera.getTarget());
	}

	/**
	 * The camera should track its target.
	 */
	@Test
	public void tick() {
		player.setXSpeed(1);
		player.tick(1);
		camera.tick(1);
		// The camera follows the player
		assertEquals(player.getPosition(), new Point2D.Double(
				camera.getPosition().getX() + camera.getCollisionRect().getWidth() / 2 - 0.5,
				camera.getPosition().getY() + camera.getCollisionRect().getHeight() / 2 - 0.5
				)
		);
	}
}