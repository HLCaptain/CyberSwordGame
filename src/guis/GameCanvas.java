package guis;

import logic.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * This is the canvas to display the game elements and state of the game.
 * The user can interact with the game map inside and this canvas will show the results.
 * Every GameCanvas has an inner renderer, a class inherited from Draw.
 */
public class GameCanvas extends MyCanvas implements Updatable {
	/**
	 * Shown and always updated game map. It contains the Actors on the field and the tiles placed on the map.
	 */
	public GameMap gameMap;

	/**
	 * The camera follows the player. Clips onto the side of the map when the Player is at the sides.
	 */
	private final Camera camera;

	/**
	 * Inner clock calls the Updatables' tick() method, which will update all the Actors and things in the game.
	 */
	public GameClock gameClock;

	/**
	 * It spawns on a player spawn and enemies chase it.
	 * The user controls the player.
	 */
	public Player player;

	/**
	 * Takes input from the keyboard from the user. Controls the player.
	 */
	public PlayerInputListener pil;

	/**
	 * The frame which the canvas is on.
	 */
	public GameFrame parentFrame;

	/**
	 * Initializes the game map, places Player and starts the timer.
	 * Also starts rendering the game map, then refreshes every few milliseconds.
	 * @param width the width of the canvas in pixels.
	 * @param height the height of the canvas in pixels.
	 * @param gameMap the game map to update and display.
	 * @param parentFrame the frame which this canvas is on.
	 */
	public GameCanvas(int width, int height, GameMap gameMap, GameFrame parentFrame) {
		// Setting base attributes.
		// Setting the renderer for the canvas.
		super(new GameCanvasDraw());
		this.parentFrame = parentFrame;
		this.gameMap = gameMap;
		this.camera = new Camera(new Point2D.Double((double) gameMap.getColumnNum() / 2, (double) gameMap.getRowNum() / 2), gameMap);
		this.gameClock = new GameClock();
		setFocusable(true);

		// Setting up the displayed image and initializing the game map actors.
		setCanvasSize(width, height);
		setUpUpdatables();
		setUpActors();
	}

	/**
	 * Sets the canvas size in pixels.
	 * @param width the new width in pixels.
	 * @param height the new height in pixels.
	 */
	@Override
	public void setCanvasSize(int width, int height) {
		// Setting the values, "clearing" the canvas
		d = new Dimension(width, height);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setPreferredSize(d);
		bottom = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_RGB);
		bottom.getGraphics().setColor(Color.red);
		bottom.getGraphics().fillRect(0,0,d.width, d.height);
		image = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_RGB);
		top = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_ARGB);
		// Drawing the new grid out onto the canvas
	}

	/**
	 * Setting up the actors, placing all Updatables in the GameClock's array to call the tick() method to update them.
	 */
	private void setUpUpdatables() {
		gameClock.addUpdatable(camera);
		gameClock.addUpdatable(gameMap);
		gameClock.addUpdatable(myCanvasDraw);
	}

	/**
	 * Creating the player and connecting the player with the inputs (mouse and keyboard).
	 * Also sets the camera target to the player.
	 */
	private void setUpActors() {
		player = new Player(gameMap);
		camera.setTarget(player);
		pil = new PlayerInputListener(player);
		parentFrame.addKeyListener(pil);
		gameMap.setGameCanvas(this);
		addMouseListener(new PlayerMouseListener(gameMap));
	}

	/**
	 * @return current Camera.
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Called when the game is ended, the game is won by the player.
	 */
	public void gameWon() {
		((GameCanvasDraw) myCanvasDraw).setEndMsg("YOU WON!");
		myCanvasDraw.tick(69);
		gameClock.removeUpdatable(gameMap);
		gameClock.stop();
	}

	/**
	 * Called when the game is ended, the game is lost by the player.
	 */
	public void gameLost() {
		((GameCanvasDraw) myCanvasDraw).setEndMsg("YOU LOST!");
		myCanvasDraw.tick(69);
		gameClock.removeUpdatable(gameMap);
		gameClock.stop();
	}
}