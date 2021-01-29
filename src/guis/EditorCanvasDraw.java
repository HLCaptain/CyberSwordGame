package guis;

import logic.Actor;
import logic.ActorType;
import logic.TileType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This canvas makes up the editable map portion of the editor frame.
 * The changes made by the player will show on this canvas by drawing on it or modifying its size.
 */
public class EditorCanvasDraw extends Draw {
	/**
	 * This holds the canvas which this class will draw onto.
	 */
	private EditorCanvas ec;

	/**
	 * Holds the image of an empty tile.
	 * It is here because we cheap out on loading images all the time.
	 */
	private BufferedImage emptyTile = null;
	private BufferedImage groundTile = null;
	private BufferedImage playerSpawn = null;
	private BufferedImage chalice = null;
	private BufferedImage enemySpawner = null;

	/**
	 * Constructor. Calls super and loads in images.
	 */
	public EditorCanvasDraw() {
		super();
		try {
			emptyTile = ImageIO.read(new File(TileType.EMPTY.getImgPath()));
			groundTile = ImageIO.read(new File(TileType.GROUND.getImgPath()));
			playerSpawn = ImageIO.read(new File(ActorType.PLAYERSPAWN.getImgPath()));
			enemySpawner = ImageIO.read(new File(ActorType.ENEMYSPAWNER.getImgPath()));
			chalice = ImageIO.read(new File(ActorType.CHALICE.getImgPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Overrides the canvas in parents, sets its own editor canvas.
	 * @param mc is the canvas which the child of Draw will draw on.
	 */
	@Override
	public void setCanvas(MyCanvas mc) {
		super.setCanvas(mc);
		ec = (EditorCanvas) mc;
	}
	/**
	 * Refreshes the canvas' game map with updated elements.
	 * Just draws the whole array out according to the tiles' colour.
	 * @param g the graphics class this method draws onto.
	 */
	@Override
	protected void makeDraw(Graphics g) {
		drawTiles(g);
		drawActors(g);
	}

	/**
	 * Draws Actors on the canvas. (Not tiles.)
	 * @param g The graphics class which this method draws on.
	 */
	private void drawActors(Graphics g) {
		if (ec.gameMap.getActors() == null)
			return;
		// Cycling through Actors on the game map.
		for (Actor a : ec.gameMap.getActors()) {
			// Setting the drawn image per Actor.
			BufferedImage img = null;
			if (a.getImgPath().equals(ActorType.PLAYERSPAWN.getImgPath())) {
				img = playerSpawn;
			} else if (a.getImgPath().equals(ActorType.CHALICE.getImgPath())) {
				img = chalice;
			} else if (a.getImgPath().equals(ActorType.ENEMYSPAWNER.getImgPath())) {
				img = enemySpawner;
			}

			// Draw the actor on the screen.
			g.drawImage(
					img,
					(int) a.getPosition().getX() * ec.tileSize,
					(int) a.getPosition().getY() * ec.tileSize,
					null
			);
		}
	}

	/**
	 * Draws Tiles on the canvas. (Everything except tiles.)
	 * @param g The graphics class which this method draws on.
	 */
	private void drawTiles(Graphics g) {
		// Looping through tiles in the game map.
		for (int i = 0; i < ec.gameMap.getRowNum(); i++) {
			for (int j = 0; j < ec.gameMap.getColumnNum(); j++) {
				// Copying attributes to variables, so it is faster and easier to read.
				Point2D.Double location = ec.gameMap.getGrid().get(i).get(j).getPosition();
				Rectangle2D.Double collisionRect = ec.gameMap.getGrid().get(i).get(j).getCollisionRect();

				// Setting the shown image.
				BufferedImage img = null;
				if (ec.gameMap.getGrid().get(i).get(j).getImgPath().equals(TileType.EMPTY.getImgPath())) {
					img = emptyTile;
				} else if (ec.gameMap.getGrid().get(i).get(j).getImgPath().equals(TileType.GROUND.getImgPath())) {
					img = groundTile;
				}

				// Drawing the image
				g.drawImage(
						img,
						(int) location.x * ec.tileSize,
						(int) location.y * ec.tileSize,
						null
				);

				// Drawing the "grid" per tile
				g.setColor(Color.BLACK);
				g.drawRect(
						(int) location.x * ec.tileSize,
						(int) location.y * ec.tileSize,
						(int) collisionRect.width * ec.tileSize,
						(int) collisionRect.height * ec.tileSize
				);
			}
		}
	}
}
