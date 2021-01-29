package logic;

import guis.GameCanvas;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The game's map is a field where Actors can interact and a grid, on which the entities can move.
 * This map takes care of updating the Actors on the map and also can be serialized.
 */
public class GameMap implements Serializable, Updatable {
	/**
	 * Need for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Arrays of tiles.
	 */
	private ArrayList<ArrayList<Tile>> grid;

	/**
	 * The actors on the map. (Non tiles.)
	 */
	private final ArrayList<Actor> actors;

	/**
	 * Actors to add after an update cycle ends not interrupting the cycle. Acts like a buffer.
	 */
	private final ArrayList<Actor> actorsToAdd;

	/**
	 * Actors to remove after an update cycle ends not interrupting the cycle. Acts like a buffer.
	 */
	private final ArrayList<Actor> actorsToRemove;

	/**
	 * The canvas which has this game map.
	 * If the game has been won or lost, we can communicate the outcome with this canvas.
	 */
	private GameCanvas gameCanvas;

	/**
	 * The single player of this game map. Some actors may use this actor's attributes.
	 */
	private Player player = null;

	/**
	 * The single player spawn of this game map. The player use this to spawn on this map.
	 */
	private PlayerSpawn playerSpawn = null;

	/**
	 * Fills the grid array with empty tiles with the given sizes.
	 * @param column Number of columns of the map.
	 * @param row Number of rows of the map.
	 */
	public GameMap(int column, int row) {
		actors = new ArrayList<>();
		actorsToAdd = new ArrayList<>();
		actorsToRemove = new ArrayList<>();
		grid = new ArrayList<>();
		for (int i = 0; i < column; i++) {
			grid.add(new ArrayList<>());
			for (int j = 0; j < row; j++) {
				grid.get(i).add(new EmptyTile(new Point2D.Double(j, i), new Rectangle2D.Double(j, i, 1, 1), this));
			}
		}
	}

	/**
	 * Sets the current player spawn on the map. Used in the editor.
	 * @param playerSpawn the new player spawn.
	 */
	public void setPlayerSpawn(PlayerSpawn playerSpawn) {
		removeActor(this.playerSpawn);
		this.playerSpawn = playerSpawn;
		addActor(playerSpawn);
	}

	/**
	 * @return the current player spawn of the map.
	 */
	public PlayerSpawn getPlayerSpawn() {
		return playerSpawn;
	}

	/**
	 * Sets the current player of the map.
	 * @param player the player of this map.
	 */
	public void setPlayer(Player player) {
		removeActorFromBuffer(this.player);
		this.player = player;
		addActorToBuffer(player);
	}

	/**
	 * @return the current player of the map.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Add the actor to a buffer not to interrupt the current update cycle.
	 * @param a the actor to add to this buffer. Will be added to the updating scene after a cycle ends.
	 */
	public void addActorToBuffer(Actor a) {
		actorsToAdd.add(a);
	}

	/**
	 * Add the actor to a buffer not to interrupt the current update cycle.
	 * @param a the actor to add to this buffer. Will be removed from the updating scene after a cycle ends.
	 */
	public void removeActorFromBuffer(Actor a) {
		actorsToRemove.add(a);
	}

	/**
	 * Directly add an actor to the scene list. Used in the editor, where there is no clock.
	 * @param a the actor to add to the scene.
	 */
	public void addActor(Actor a) {
		actors.add(a);
	}

	/**
	 * Directly remove an actor from the scene list. Used in the editor, where there is no clock.
	 * @param a the actor to remove from the scene.
	 */
	public void removeActor(Actor a) {
		if (a != null)
			actors.remove(a);
	}

	/**
	 * @return gets the actors on the game map. (Non tiles.)
	 */
	public ArrayList<Actor> getActors() {
		return actors;
	}

	/**
	 * Sets the current game canvas in which this game map is modified and displayed.
	 * @param gameCanvas the current game canvas.
	 */
	public void setGameCanvas(GameCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}

	/**
	 * @return the number of columns of the map's grid.
	 */
	public int getColumnNum() {
		return grid.get(0).size();
	}

	/**
	 * @return the number of rows of the map's grid.
	 */
	public int getRowNum() {
		return grid.size();
	}

	/**
	 * @return the grid of tiles of the map.
	 */
	public ArrayList<ArrayList<Tile>> getGrid() {
		return grid;
	}

	/**
	 * Sets the new map size, trying to conserve the original map.
	 * @param column this variable is the new column size of the map.
	 * @param row this variable is the new row size of the map.
	 */
	public void setGridSize(int column, int row) {
		// Temporary array with the new size
		ArrayList<ArrayList<Tile>> tempGrid = new ArrayList<>();

		// Setting the new sized array with the current array's tiles, or filling up the current array with empty tiles.
		for (int i = 0; i < row; i++) {
			tempGrid.add(new ArrayList<>());
			if (i < grid.size()) {
				for (int j = 0; j < column; j++) {
					tempGrid.get(i).add(new EmptyTile(new Point2D.Double(j, i), new Rectangle2D.Double(j, i, 1, 1), this));
					if (j < grid.get(i).size())
						tempGrid.get(i).set(j, grid.get(i).get(j));
				}
			} else {
				for (int j = 0; j < column; j++) {
					tempGrid.get(i).add(new EmptyTile(new Point2D.Double(j, i), new Rectangle2D.Double(j, i, 1, 1), this));
				}
			}
		}

		grid = tempGrid;
	}

	/**
	 * Notifying the canvas that the game has ended with the player winning.
	 */
	public void gameWon() {
		if (gameCanvas != null)
			gameCanvas.gameWon();
	}

	/**
	 * Notifying the canvas that the game has ended with the player losing.
	 */
	public void gameLost() {
		if (gameCanvas != null)
			gameCanvas.gameLost();
	}

	/**
	 * @return the current game canvas this map is displayed on.
	 */
	public GameCanvas getGameCanvas() {
		return gameCanvas;
	}

	/**
	 * Updates all the actors in the scene and grid.
	 * In the end, updates the actors in the scene, preventing any problem in the middle of the cycle.
	 * @param dtime time passed from the previous update.
	 */
	@Override
	public void tick(double dtime) {
		// Updating all actors in the scene.
		for (Actor a : actors) {
			a.tick(dtime);
		}

		// Updating all tiles in the grid.
		for (ArrayList<Tile> tileArrayList : grid) {
			for (Tile tile : tileArrayList)
				tile.tick(dtime);
		}

		// Removing the actors.
		actors.removeAll(actorsToRemove);
		actorsToRemove.clear();

		// Adding the actors.
		actors.addAll(actorsToAdd);
		actorsToAdd.clear();
	}
}
