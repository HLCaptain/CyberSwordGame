package guis;

import logic.GameMap;

import javax.swing.*;
import java.awt.*;

/**
 * This frame has a GameMap, which the frame is able to load in or save.
 */
public abstract class CSGFrame extends JFrame {
	/**
	 * The map of the frame.
	 */
	protected GameMap gameMap;

	/**
	 * Loads a map with restrictions according to a use case.
	 * @param gameMap the game map to load.
	 */
	public abstract void loadMap(GameMap gameMap);

	/**
	 * Saves a map with restrictions according to a use case.
	 * @param gameMap the game map to save.
	 * @return if succeed with the saving process, then returns TRUE.
	 */
	public abstract boolean saveMap(GameMap gameMap);

	/**
	 * Sets up a basic dialog's properties to notify the user about something.
	 * @param dialog the method modifies this dialog's properties.
	 */
	protected void setDialogProperties(JDialog dialog) {
		dialog.setVisible(true);
		dialog.setResizable(false);
		dialog.pack();
		dialog.setLocation(new Point(
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - dialog.getWidth()) / 2,
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - dialog.getHeight()) / 2
		));
	}

	/**
	 * Just sets the game map and the passes the title onto super().
	 * @param title Window title of the frame.
	 * @param gameMap The game map which a child class can have an effect on.
	 */
	public CSGFrame(String title, GameMap gameMap) {
		super(title);
		this.gameMap = gameMap;
	}

	/**
	 * @return the game map of the frame.
	 */
	public GameMap getGameMap() {
		return gameMap;
	}
}
