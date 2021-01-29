package logic;

/**
 * All actor types are listed here, with the path to their representing image.
 */
public enum ActorType {
	PLAYERSPAWN("resources/Props/PlayerSpawner/PlayerSpawner_Col_32x32.png"),
	PLAYER("resources/Entities/Player/PlayerStand_Col_32x32.png"),
	CHALICE("resources/Props/Chalice/0004.png"),
	ENEMYSPAWNER("resources/Props/EnemySpawner/EnemySpawner_Col_32x32.png"),
	ENEMY("resources/Entities/Enemy/EnemyStand_Col_32x32.png"),
	PISTOLBULLET("resources/Projectiles/PistolBullet_Col_32x32.png");

	/**
	 * The path to the image which represents an actor.
	 * Used for rendering an actor in a gui element.
	 */
	private final String imgPath;

	/**
	 * Setting the path to the actor representing image.
	 * @param pathname the path to the actor representing image.
	 */
	ActorType(String pathname) {
		imgPath = pathname;
	}

	/**
	 * @return the path to the actor representing image.
	 */
	public String getImgPath() {
		return imgPath;
	}
}
