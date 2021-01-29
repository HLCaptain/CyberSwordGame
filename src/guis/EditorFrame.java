package guis;

import logic.GameMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static logic.Constants.*;

/**
 * The user can edit, modify game maps.
 * Game map's size is customizable and progress
 * can be saved and loaded with a press of a button.
 * You can draw all types of tiles onto the canvas
 * and then load in a custom map with the game.
 */
public class EditorFrame extends CSGFrame {
	// By the way, these variables can be integrated into the construction,
	// but I was too lazy to do it, and it is easier and to read.

	// Ordinary components to warm the heart.
	private JLabel columnLabel;
	private JLabel rowLabel;
	private JTextField columnField;
	private JTextField rowField;
	private JButton setSizeButton;
	private JButton drawEmptyTileButton;
	private JButton drawGroundTileButton;
	private JButton drawPlayerSpawnButton;
	private JButton drawChaliceButton;
	private JButton drawEnemySpawnerButton;

	// Load and save buttons.
	private JButton loadButton;
	private JButton saveButton;

	// Needed for canvas to draw on.
	private MyCanvas mapEditorCanvas;
	private MouseListenerDelegator mld;

	// This is a kinda complex layout.
	private final GridBagLayout layout;

	// Parameters for the game map and editor.
	private int tileSize = EDITOR_TILE_SCALE;
	private int maxRows = MAX_ROWS;
	private int minRows = MIN_ROWS;
	private int maxCol = MAX_COLUMNS;
	private int minCol = MIN_COLUMNS;

	/**
	 * Loads in the map and refreshes the canvas.
	 * @param gameMap the map which we load in.
	 */
	@Override
	public void loadMap(GameMap gameMap) {
		// No restrictions on map loading in the editor
		this.gameMap = gameMap;
		((EditorCanvas) this.mapEditorCanvas).gameMap = gameMap;
		mapEditorCanvas.myCanvasDraw.makeDraw(mapEditorCanvas.getBottom());
		mapEditorCanvas.update(mapEditorCanvas.getGraphics());
		// Setting new frame size with the new map.
		setEditorFrameSize(gameMap.getColumnNum(), gameMap.getRowNum());
	}

	/**
	 * Checks if it is safe to save the map. (Has all the rules met.)
	 * @param gameMap the map to save.
	 * @return if succeed, will return TRUE. If the map has not met the necessary rules, then returns FALSE.
	 */
	@Override
	public boolean saveMap(GameMap gameMap) {
		if (checkMapToSave(gameMap) == 0) {
			return true;
		}
		// Notifying an error
		JDialog errorDialog = new JDialog(this, "Bad values");
		GridLayout errorLayout = new GridLayout();
		errorLayout.setColumns(1);
		errorLayout.setRows(checkMapToSave(gameMap));
		errorDialog.setLayout(errorLayout);

		setDialogProperties(errorDialog);
		return false;
	}

	/**
	 * Checks how many rules a map broke.
	 * @param gameMap the map to check.
	 * @return Number of rules the map broke.
	 */
	private int checkMapToSave(GameMap gameMap) {
		int rulesBroken = 0;
		// TODO: check all the rules broken and increment accordingly.
		return rulesBroken;
	}

	/**
	 * Logs all the rules broken in the error's dialog window.
	 * @param gameMap map which broke the rules.
	 * @param errorDialog the dialog to add error labels to.
	 */
	private void saveErrorLogger(GameMap gameMap, JDialog errorDialog) {
		// TODO: check all the rules broken and add them to the error dialog accordingly.
		errorDialog.add(new Label("Rules are not met in this map"));
	}

	/**
	 * Forwards the resize of the window if the user wants to add more rows and columns of tiles.
	 */
	public class SizeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int columns = Integer.parseInt(columnField.getText());
			int rows = Integer.parseInt(rowField.getText());
			setEditorFrameSize(columns, rows);
		}
	}

	/**
	 * The editor's main window. The user can create, save and modify a map.
	 * @param callerFrame the frame which initiated this window. It is used to go back to, when finished in the editor. (Not implemented.)
	 */
	public EditorFrame(JFrame callerFrame) {
		// Initiating the window.
		super("CSG Map Editor", null);
		this.setMinimumSize(new Dimension(200, 110));
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		// Setting up the components.
		setUpSizingTools();

		// Setting up the toolbar components and the canvas.
		setUpCanvas();
		setUpToolBar();
		setUpSaveLoad();

		// Setting up the layout with the sizing tools and toolbar.
		layout = new GridBagLayout();
		setUpLayoutWest();

		// Configuring the east elements with the canvas.
		setUpLayoutEast();

		this.pack();

		// At last, we set the position to the center of the screen.
		this.setLocation(
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getWidth()) / 2,
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - this.getHeight()) / 2
		);
	}

	/**
	 * Sets up the save and load feature of the map.
	 * The user is able to save and load in a game map.
	 */
	private void setUpSaveLoad() {
		SaveLoadButtonListener saveLoadListener = new SaveLoadButtonListener(this);
		saveButton = new JButton("Save map");
		saveButton.addActionListener(saveLoadListener);
		saveButton.setActionCommand("Save");
		loadButton = new JButton("Load map");
		loadButton.addActionListener(saveLoadListener);
		loadButton.setActionCommand("Load");
	}

	/**
	 * On the toolbar are the brushes what enables the user to draw on the canvas.
	 * Every button represents a brush, every brush "draws" on the canvas a different way.
	 */
	private void setUpToolBar() {
		DrawButtonListener drawButtonListener = new DrawButtonListener(mld);
		drawEmptyTileButton = new JButton("Empty Tile");
		drawEmptyTileButton.addActionListener(drawButtonListener);
		drawEmptyTileButton.setActionCommand("DrawEmptyTile");
		drawGroundTileButton = new JButton("Ground Tile");
		drawGroundTileButton.addActionListener(drawButtonListener);
		drawGroundTileButton.setActionCommand("DrawGroundTile");
		drawPlayerSpawnButton = new JButton("Player Spawn");
		drawPlayerSpawnButton.addActionListener(drawButtonListener);
		drawPlayerSpawnButton.setActionCommand("DrawPlayerSpawn");
		drawChaliceButton = new JButton("Chalice");
		drawChaliceButton.addActionListener(drawButtonListener);
		drawChaliceButton.setActionCommand("DrawChalice");
		drawEnemySpawnerButton = new JButton("Enemy Spawner");
		drawEnemySpawnerButton.addActionListener(drawButtonListener);
		drawEnemySpawnerButton.setActionCommand("DrawEnemySpawner");
	}

	/**
	 * Sets up the listeners and delegators so the user is able to draw onto the canvas.
	 */
	private void setUpCanvas() {
		gameMap = new GameMap(minCol, minRows);
		mapEditorCanvas = new EditorCanvas(tileSize, gameMap);
		mld = new MouseListenerDelegator(mapEditorCanvas);
		mld.setDelegate(new EditorGroundTileDraw());
	}

	/**
	 * The sizing feature enables to size the game map however (within limits) the user wants.
	 * User can set the rows and columns on the game map.
	 */
	private void setUpSizingTools() {
		columnLabel = new JLabel("Set the columns:");
		rowLabel = new JLabel("Set the rows:");
		columnField = new JTextField(6);
		rowField = new JTextField(6);
		setSizeButton = new JButton("Set size!");
		setSizeButton.addActionListener(new SizeActionListener());
	}

	/**
	 * Placing the brush buttons onto the canvas. Located on the left side.
	 * Also sets the layout of the west panel.
	 */
	private void setUpLayoutWest() {
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel west = new JPanel();
		west.setLayout(layout);
		this.add(west, BorderLayout.WEST);

		// West panel setup with the buttons
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 0;
		gbc.ipady = 4;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0, 10, 0, 0);
		west.add(columnLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		west.add(columnField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0, 10, 0, 0);
		west.add(rowLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		west.add(rowField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 0, 0, 0);
		west.add(setSizeButton, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 0, 5, 5);
		west.add(saveButton, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 5, 5, 0);
		west.add(loadButton, gbc);

		//
		// Setting size, loading and saving ends here, brushes follow up.
		//

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 8, 0, 2);
		west.add(drawEmptyTileButton, gbc);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 2, 0, 8);
		west.add(drawGroundTileButton, gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.insets = new Insets(5, 8, 0, 2);
		west.add(drawPlayerSpawnButton, gbc);
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.insets = new Insets(5, 2, 0, 8);
		west.add(drawChaliceButton, gbc);
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.insets = new Insets(5, 8, 0, 2);
		west.add(drawEnemySpawnerButton, gbc);

		//
		// Insert here more tile drawing buttons ON TOP (above this comment)
		//

		// We set a border to have some space between the components. (Aesthetic change.)
		west.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));
	}

	/**
	 * Setting up the canvas, which the user will draw onto.
	 * This canvas draws the game map in its current state (always).
	 */
	private void setUpLayoutEast() {
		JPanel east = new JPanel();
		east.setLayout(layout);
		east.add(mapEditorCanvas);
		east.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(east);
	}

	/**
	 * Handles the resize process.
	 * @param columns the number of columns of tiles.
	 * @param rows the number of rows of tiles.
	 */
	public void setEditorFrameSize(int columns, int rows) {
		// Checking if in boundaries
		if (rows > maxRows || rows < minRows || columns > maxCol || columns < minCol) {
			// Notifying the user about incorrect values with a dialog window.
			JDialog errorDialog = new JDialog(this, "Bad values");
			GridLayout errorLayout = new GridLayout();
			errorDialog.setLayout(errorLayout);
			int rulesBroken = 0;
			if (columns > maxCol || columns < minCol) {
				errorDialog.add(new Label("Columns must be between " + minCol + " and " + maxCol + "!"));
				rulesBroken++;
			}
			if (rows > maxRows || rows < minRows) {
				errorDialog.add(new Label("Rows must be between " + minRows + " and " + maxRows + "!"));
				rulesBroken++;
			}

			errorLayout.setColumns(1);
			errorLayout.setRows(rulesBroken);

			setDialogProperties(errorDialog);
			return;
		}
		mapEditorCanvas.setCanvasSize(columns, rows);
		this.pack();
		this.setLocation(
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getWidth()) / 2,
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - this.getHeight()) / 2
		);
	}
}
