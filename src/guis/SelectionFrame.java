package guis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Lets the user choose between two (2) very important things.
 * Edit a map?
 * or
 * Play a game?
 * The user presses the button according to their desire.
 */
public class SelectionFrame extends JFrame {
	/**
	 * Needed this self reference, because the buttons' action listener is defined in this class.
	 * (And the "this" is not working as a reference of this frame as it should be.)
	 */
	private final SelectionFrame self = this;

	/**
	 * Opens the map editor or the game according to the set action command.
	 */
	public class SelectionActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ("open game".equals(e.getActionCommand())) {
				self.setVisible(false);
				GameFrame gameFrame = new GameFrame(self);
			}
			if ("open editor".equals(e.getActionCommand())) {
				self.setVisible(false);
				EditorFrame editorFrame = new EditorFrame(self);
			}
		}
	}

	/**
	 * Setting basic window attributes and the two big buttons.
	 * So the user is able to edit a map or play a game.
	 */
	public SelectionFrame() {
		// Initiating the window.
		super("Selection Window");
		this.setSize(300, 150);
		this.setLocation(
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getWidth()) / 2,
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - this.getHeight()) / 2
		);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		// Setting up the components.

		// This button calls the map editor window to pop up.
		JButton editorButton = new JButton("Edit a map!");
		editorButton.addActionListener(new SelectionActionListener());
		editorButton.setActionCommand("open editor");

		// This button calls the game window to pop up.
		JButton gameButton = new JButton("Play the game!");
		gameButton.addActionListener(new SelectionActionListener());
		gameButton.setActionCommand("open game");

		// Setting up the layout.
		GridLayout layout = new GridLayout();
		this.setLayout(layout);
		this.add(editorButton);
		this.add(gameButton);
	}
}
