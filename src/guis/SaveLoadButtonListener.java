package guis;

import logic.GameMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Saves current progress from the canvas onto a file or
 * Loads in up a file to continue where the user left off.
 */
public class SaveLoadButtonListener implements ActionListener {
	/**
	 * The parent which implements the loadMap and saveMap methods.
	 * The parentFrame decides when to save and how to load a specific map.
	 */
	private CSGFrame parentFrame;

	/**
	 * This internal listener handles the save and load button's functions and calls the proper methods.
	 * This listener is used for loading and saving maps.
	 * @param parentFrame this is the CSGFrame which we save the map from and load the map to.
	 */
	public SaveLoadButtonListener(CSGFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

	/**
	 * Loading or saving according to the already set action commands.
	 * @param e the current ActionEvent.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Deciding to load or save. Calls the proper methods.
		if ("Load".equals(e.getActionCommand())) {
			loadGameMap();
		}
		if ("Save".equals(e.getActionCommand())) {
			saveGameMap();
		}
	}

	/**
	 * Loads in a grid of tiles from a file.
	 * Also draws the fresh array of tile onto the canvas.
	 * Uses a file chooser to load a map from (somewhere).
	 */
	private void loadGameMap() {
		// Loading in with file chooser.
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File( "resources/Maps"));
		chooser.setDialogTitle("Loading a map to edit!");
		chooser.setVisible(true);
		GameMap temp = null;
		boolean success = false;
		int returnVal = chooser.showDialog(parentFrame, "Load");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(chooser.getSelectedFile()));
				temp = (GameMap)ois.readObject();
				ois.close();
				success = true; // Did not throw an exception, everything is fine.
			} catch(Exception ex) { ex.printStackTrace(); }
		}

		// Refreshing the canvas with the new game map.
		if (success)
			parentFrame.loadMap(temp);
	}

	/**
	 * Saves current progress into a file.
	 * Uses a file chooser to save the map to (somewhere).
	 */
	private void saveGameMap() {
		// If map is not okay yet, then it will be handled by the parent frame.
		if (!parentFrame.saveMap(parentFrame.gameMap))
			return;
		// Saving the current game map with file chooser.
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File( "resources/Maps"));
		chooser.setDialogTitle("Saving the edited map!");
		chooser.setVisible(true);
		int returnVal = chooser.showDialog(parentFrame, "Save");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(
						new FileOutputStream(chooser.getSelectedFile().getPath() + ".gamemap"));
				oos.writeObject(parentFrame.gameMap);
				oos.close();
			} catch(Exception ex) { ex.printStackTrace(); }
		}
	}
}
