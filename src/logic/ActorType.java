package logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
	 * The path to the image which represents an actor.
	 * Used for rendering an actor in a gui element.
	 */
	private BufferedImage img;

	/**
	 * Setting the path to the actor representing image.
	 * @param pathname the path to the actor representing image.
	 */
	ActorType(String pathname) {
		imgPath = pathname;
		try {
			img = ImageIO.read(new File(pathname));
		} catch(IOException e) {
			System.out.println("File in " + pathname + " cannot be loaded.");
		}
	}

	/**
	 * @return the path to the actor representing image.
	 */
	public String getImgPath() {
		return imgPath;
	}

	/**
	 * @return buffered image of the actor.
	 */
	public BufferedImage getImg() {
		return img;
	}
}
