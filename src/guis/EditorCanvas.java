package guis;

import logic.GameMap;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This is the canvas for the editor. It shows the game map in its current state.
 * A drawer and a mouse listener always updates the map and the drawn image to the user.
 */
public class EditorCanvas extends MyCanvas {

	/**
	 * This map holds the information which is editable by the user's brushes.
	 */
	public GameMap gameMap;

	/**
	 * The size of 1 tile in pixels.
	 */
	public int tileSize;

	/**
	 * Sets the size according to the game map's size and tiles' size.
	 * Also sets the drawer to the canvas.
	 * @param tileSize Size of 1 tile in pixels.
	 * @param gameMap The game map to edit.
	 */
	public EditorCanvas(int tileSize, GameMap gameMap) {
		super(new EditorCanvasDraw());
		this.tileSize = tileSize;
		this.gameMap = gameMap;
		setCanvasSize(gameMap.getRowNum(), gameMap.getColumnNum());
	}

	/**
	 * Sets the size of the canvas according to some given values.
	 * @param column Number of column in the game map array.
	 * @param row Number of row in the game map array.
	 */
	@Override
	public void setCanvasSize(int column, int row) {
		// Setting the values, "clearing" the canvas
		d = new Dimension(column * tileSize + 1, row * tileSize + 1);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setPreferredSize(d);
		bottom = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_RGB);
		bottom.getGraphics().setColor(Color.red);
		bottom.getGraphics().fillRect(0,0,d.width, d.height);
		image = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_RGB);
		top = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_ARGB);

		// Modifying the grid's size
		gameMap.setGridSize(column, row);

		// Drawing the new grid out onto the canvas
		myCanvasDraw.makeDraw(getBottom());
	}
}
