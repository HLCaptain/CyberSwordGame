package logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * All types of tiles are listed here with their colour and path to their representing image.
 */
public enum TileType implements Serializable {
	EMPTY(new Color(0xFFBDD3EE, true),
			"resources/Tiles/EmptyTile/EmptyTile_Col_32x32.png"),
	GROUND(new Color(0xFF111C21, true),
			"resources/Tiles/GroundTile/GroundTile_Col_32x32.png");

	/**
	 * The color representing the tile type.
	 */
	private final Color color;

	/**
	 * The path to the image representing the tile type.
	 */
	private final String imgPath;

	/**
	 * The image representing the tile type.
	 */
	private BufferedImage img;

	/**
	 * Setting the parameters of a tile type.
	 * @param color the color which represents a type of tile.
	 * @param pathname the path to the image which represents a type of tile.
	 */
	TileType(Color color, String pathname) {
		this.color = color;
		this.imgPath = pathname;
		try {
			img = ImageIO.read(new File(this.imgPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the path to the image representing a type of tile.
	 */
	public String getImgPath() {
		return this.imgPath;
	}

	/**
	 * @return buffered image of the tile.
	 */
	public BufferedImage getImg() {
		return img;
	}
}
