package logic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * An entity has health management and damage.
 * Also it is able to heal and take damage. Entities may have ay invincible period after damage taking.
 */
public abstract class Entity extends Movable {
	/**
	 * If true, then the entity is on the ground.
	 */
	public boolean grounded;

	/**
	 * The maximum health point of the entity.
	 */
	protected double maxHp;

	/**
	 * The actual health point of the entity.
	 */
	protected double hp;

	/**
	 * If true, then the enemy cannot take damage.
	 */
	protected boolean invincible;

	/**
	 * The duration of invincibility after damage taking.
	 */
	protected int invincibleTime = 1000;

	/**
	 * The timer which counts down since the invincibility started.
	 */
	protected int invincibleTimeStart = invincibleTime;

	/**
	 * The damage this entity can deal.
	 */
	protected double damage = 20;

	/**
	 * The actors which this entity was colliding since this moment.
	 * Usually calls the collisionExit method of these actors.
	 */
	protected ArrayList<Actor> collided;

	/**
	 * The actors which this entity is colliding at the moment.
	 * Usually calls the collisionWith method of these actors.
	 */
	protected ArrayList<Actor> colliding;

	/**
	 * Just passing the construction to super.
	 * @param position position of the entity.
	 * @param collisionRect collision box of the entity.
	 */
	public Entity(Point2D.Double position, Rectangle2D.Double collisionRect) {
		super(position, collisionRect);
	}

	/**
	 * Passing the update to the extended Movable class, because it handles the movement.
	 * @param dtime time passed from the previous update.
	 */
	@Override
	public void tick(double dtime) {
		super.tick(dtime);
	}

	/**
	 * Takes an amount of damage.
	 * @param damage the amount of damage taken.
	 */
	public abstract void takeDmg(double damage);

	/**
	 * Sets up the health points at the beginning of construction.
	 */
	public abstract void setUpHp();

	/**
	 * Heals with an amount of health.
	 * @param health the amount of health the entity heals itself.
	 */
	public void heal(double health) {
		if (health + hp > maxHp)
			hp = maxHp;
		else
			hp += health;
	}

	/**
	 * @return the maximum health points of the entity.
	 */
	public double getMaxHp() {
		return maxHp;
	}

	/**
	 * @return the current health points of the entity.
	 */
	public double getHp() {
		return hp;
	}

	/**
	 * @return the damage which can be dealt by this entity.
	 */
	public double getDamage() {
		return damage;
	}

	/**
	 * An entity dies.
	 */
	public abstract void die();

	/**
	 * Jumping lifts the entity up into the air and it is no longer grounded.
	 */
	public void jump() {
		if (grounded) {
			grounded = false;
			setYSpeed(0.16);
		}
	}
}
