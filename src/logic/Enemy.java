package logic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static logic.Constants.*;

/**
 * An enemies goal is to kill the player. The enemy follows the player and deals damage to it by colliding.
 */
public class Enemy extends Entity {
	/**
	 * The player target to kill. The enemy follows this target.
	 */
	private final Player target;

	/**
	 * Setting up the enemy's target with the game map's player and the position.
	 * Adds itself to the game map's actors and sets its image as well.
	 * @param position the enemy's position.
	 * @param gameMap the game map the enemy is on.
	 */
	public Enemy(Point2D.Double position, GameMap gameMap) {
		super(position, new Rectangle2D.Double(position.getX() + 0.25, position.getY(), 0.5, 1));
		this.gameMap = gameMap;
		this.gameMap.addActorToBuffer(this);
		this.target = gameMap.getPlayer();
		this.speed = new Point2D.Double(0, 0);
		this.name = "Enemy";
		this.collided = new ArrayList<>();
		this.colliding = new ArrayList<>();
		setImg(ActorType.ENEMY.getImgPath());

		setUpHp();
	}

	/**
	 * Handling collision and movement every update.
	 * @param dtime time passed from the previous update.
	 */
	@Override
	public void tick(double dtime) {
		// Setting new position.
		if (this.getPosition().getX() < target.getPosition().getX())
			setXSpeed(VELOCITY_CONST / 4);
		else
			setXSpeed(-VELOCITY_CONST / 4);
		setPosition(new Point2D.Double(
				getPosition().getX() + speed.getX(),
				getPosition().getY() - speed.getY())
		);

		// Colliding with objects at the new position.
		collisionHandling();

		// Handling falling. (Or not falling.)
		if (!grounded) {
			setYSpeed(speed.getY() - GRAVITY / dtime * VELOCITY_CONST * .5);
		} else setYSpeed(0);
		if (getXSpeed() < 0)
			setImg(ActorType.ENEMY.getImgPath());
		if (getXSpeed() > 0)
			setImg(ActorType.ENEMY.getImgPath());
	}

	/**
	 * Taking damage from something if not invincible.
	 * @param damage the value of the taken damage.
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
	 * Set health points to the max.
	 */
	@Override
	public void setUpHp() {
		maxHp = 100;
		hp = maxHp;
	}

	/**
	 * When the enemy dies, it just gets removed from the map.
	 */
	@Override
	public void die() {
		gameMap.removeActorFromBuffer(this);
	}

	/**
	 * If hit with a pistol bullet, the bullet deals damage to the enemy and disappears.
	 * @param pistolBullet is the bullet which collided.
	 */
	@Override
	public void collideWith(PistolBullet pistolBullet) {
		this.takeDmg(pistolBullet.getDamage());
		pistolBullet.die();
	}

	/**
	 * Colliding with the actors intersecting this enemy's collision box.
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
	 * @return the current target actor of the enemy.
	 */
	public Player getTarget() {
		return target;
	}
}
