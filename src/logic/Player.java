package logic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

import static logic.Constants.*;

/**
 * The player is controlled by the user. It can shoot bullets to kill enemies, jump, move left and right, and die.
 * Also able to win the game by reaching the Chalice and lose by dying.
 * The player spawns at the beginning of the game at the player spawn.
 */
public class Player extends Entity {
	/**
	 * Spawning the player at the game map's player spawn, also adding to the game map's scene with full hp.
	 * @param gameMap the game map on which this player exists.
	 */
	public Player(GameMap gameMap) {
		// The player is at the spawn.
		super(gameMap.getPlayerSpawn().getPosition(), new Rectangle2D.Double(
				gameMap.getPlayerSpawn().getPosition().getX() + 0.25,
				gameMap.getPlayerSpawn().getPosition().getY(),
				0.5,
				1
				)
		);
		this.gameMap = gameMap;
		this.speed = new Point2D.Double(0, 0);
		this.name = "Player";
		this.collided = new ArrayList<>();
		this.colliding = new ArrayList<>();

		// Setting up the player on the scene.
		gameMap.setPlayer(this);
		setImg(ActorType.PLAYER.getImgPath());

		setUpHp();
	}

	/**
	 * Handles collisions and updates shown image based on movement.
	 * @param dtime time passed from the previous update.
	 */
	@Override
	public void tick(double dtime) {
		super.tick(dtime);
		collisionHandling();

		if (!grounded) {
			setYSpeed(speed.getY() - GRAVITY / dtime * VELOCITY_CONST * .5);
		} else setYSpeed(0);
		if (getXSpeed() < 0)
			setImg("resources/Entities/Player/PlayerStand_Col_32x32_Rot.png");
		if (getXSpeed() > 0)
			setImg("resources/Entities/Player/PlayerStand_Col_32x32.png");

		if (invincible) {
			invincibleTimeStart -= dtime;
			if (invincibleTimeStart < 0) {
				invincibleTimeStart = invincibleTime;
				invincible = false;
			}
		}
	}

	/**
	 * Collide with every actor which this player's collision box intersects.
	 */
	@Override
	public void collisionHandling() {
		colliding = (ArrayList<Actor>) getCollidingActors();
		for (Actor a : collided) {
			if (!colliding.contains(a))
				a.collideExit(this);
		}
		for (Actor a : colliding) {
			a.collideWith(this);
		}
		collided = colliding;
	}

	/**
	 * Takes damage from somebody.
	 * @param damage the amount of damage taken.
	 */
	@Override
	public void takeDmg(double damage) {
		if (!invincible) {
			if (hp - damage <= 0) {
				hp = 0;
				die();
			} else
				hp -= damage;
		}
	}

	/**
	 * Sets health points to maximum.
	 */
	@Override
	public void setUpHp() {
		maxHp = 100;
		hp = maxHp;
	}

	/**
	 * If the player died, then the user lost the game.
	 */
	@Override
	public void die() {
		gameMap.gameLost();
	}

	/**
	 * Colliding with an enemy, resulting in the enemy damaging the player.
	 * @param enemy is the enemy which collided.
	 */
	@Override
	public void collideWith(Enemy enemy) {
		if (!invincible) {
			takeDmg(enemy.getDamage());
			invincible = true;
		}
	}

	/**
	 * @return the actual image of the player.
	 */
	public BufferedImage getImg() {
		return ActorType.PLAYER.getImg();
	}
}
