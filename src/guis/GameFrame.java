package guis;

import logic.GameMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static logic.Constants.*;

public class GameFrame extends CSGFrame {

	public GameFrame(JFrame parentFrame) {
		// Initiating the window.
		super("Cyber Sword Game", null);
		this.setSize(640, 420);
		this.setLocation(
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getWidth()) / 2,
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - this.getHeight()) / 2
		);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		new SaveLoadButtonListener(this).actionPerformed(new ActionEvent(this, 1, "Load"));
		setFocusable(true);

		setUpCanvas();
	}

	@Override
	public void loadMap(GameMap gameMap) {
		if(checkMapToLoad(gameMap)) {
			this.gameMap = gameMap;
		}

	}

	@Override
	public boolean saveMap(GameMap gameMap) {
		return true;
	}

	/**
	 * Checks if the map has the necessary tiles to be saved or loaded.
	 * @param gameMap the map to be checked.
	 * @return This method returns true IF:
	 * -- There is only one Player Spawn and is on an empty tile
	 * -- There is at least one Chalices (goals) and is on an empty tile
	 * on the map.
	 */
	private boolean checkMapToLoad(GameMap gameMap) {
		return true;
	}

	private void setUpCanvas() {
		if (gameMap == null || gameMap.getGrid().size() == 0) {
			return;
		}
		int width = Math.min(MAX_GAME_FRAME_WIDTH, gameMap.getColumnNum() * TILE_SCALE);
		int height = Math.min(MAX_GAME_FRAME_HEIGHT, gameMap.getRowNum() * TILE_SCALE);
		GameCanvas gameCanvas = new GameCanvas(width, height, gameMap, this);
		this.add(gameCanvas, BorderLayout.CENTER);
		this.pack();
		this.setLocation(new Point(
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getWidth()) / 2,
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - this.getHeight()) / 2
		));
	}
}
