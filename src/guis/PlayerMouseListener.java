package guis;

import logic.GameMap;
import logic.PistolBullet;
import logic.Player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

/**
 * This mouse listener will handle the mouse input from the user and has effect on the player's actions.
 * Currently it is only shooting bullets.
 */
public class PlayerMouseListener implements MouseListener {
	/**
	 * The game map the user shots bullets on.
	 */
	private GameMap gameMap;

	/**
	 * The player which the user shots bullets from.
	 */
	private Player player;

	/**
	 * Finds the player on the game map and sets the game map.
	 * @param gameMap the user is able to spawn/shoot bullets on this game map.
	 */
	public PlayerMouseListener(GameMap gameMap) {
		this.gameMap = gameMap;
		this.player = gameMap.getPlayer();
	}

	/**
	 * Shooting a bullet from the player position and to the direction where the mouse is at.
	 * @param e the current MouseEvent.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO: make bullet shoot to cursor
		// Speed of the bullet.
		double speedX;
		double speedY;

		// The cursor position relative to the player's positions
		double deltaX = ((player.getCollisionRect().getX() + player.getCollisionRect().getWidth() / 2 - gameMap.getGameCanvas().getCamera().getPosition().getX()) * gameMap.getGameCanvas().getCamera().getScale()) * (-1) + e.getPoint().getX();
		double deltaY = ((player.getCollisionRect().getY() + player.getCollisionRect().getHeight() / 2 - gameMap.getGameCanvas().getCamera().getPosition().getY()) * gameMap.getGameCanvas().getCamera().getScale()) - e.getPoint().getY();

		// Normalize speed of the bullet using the cursor direction relative to the player
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		speedX = deltaX / distance * 0.4;
		speedY = deltaY / distance * 0.4;

		// Initializing the bullet.
		new PistolBullet(new Point2D.Double(player.getPosition().getX(), player.getPosition().getY()), new Point2D.Double(speedX, speedY), this.gameMap);
	}

	/**
	 * Implemented, but not used.
	 * @param e The current MouseEvent.
	 */
	@Override
	public void mouseClicked(MouseEvent e) { }

	/**
	 * Implemented, but not used.
	 * @param e The current MouseEvent.
	 */
	@Override
	public void mouseReleased(MouseEvent e) { }

	/**
	 * Implemented, but not used.
	 * @param e The current MouseEvent.
	 */
	@Override
	public void mouseEntered(MouseEvent e) { }

	/**
	 * Implemented, but not used.
	 * @param e The current MouseEvent.
	 */
	@Override
	public void mouseExited(MouseEvent e) {	}
}
