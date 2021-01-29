package guis;

import logic.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.function.Predicate;

import static logic.Constants.VELOCITY_CONST;

/**
 * This key listener takes input from the keyboard from the user,
 * then does some thing to the player character according to the input.
 */
public class PlayerInputListener extends KeyAdapter implements KeyListener {
	/**
	 * The inputs will call the appropriate method of this player.
	 */
	private Player player;

	/**
	 * An array which holds the currently pressed buttons.
	 * It is here to avoid bad handling of the user inputs, so the inputs make sense.
	 */
	private ArrayList<Character> inputs;

	/**
	 * Setting up the input array and player.
	 * @param player the user controls this player.
	 */
	public PlayerInputListener(Player player) {
		this.player = player;
		this.inputs = new ArrayList<>();
	}

	/**
	 * New key pressed is added to the input array, then a proper input to the player is handled.
	 * @param e the current key event.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'D' || e.getKeyChar() == 'd')
			player.setXSpeed(VELOCITY_CONST * 5);
		if (e.getKeyChar() == 'A' || e.getKeyChar() == 'a')
			player.setXSpeed(-VELOCITY_CONST * 5);
		if (e.getKeyChar() == ' ')
			player.jump();
		inputs.add(e.getKeyChar());
	}

	/**
	 * Remove the released key from the inputs, then according to the input array's content,
	 * the method decide, what to do.
	 * @param e the current key event.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		inputs.removeIf(Predicate.isEqual(e.getKeyChar()));

		// Should the player move vertically?
		boolean verticalMovement = false;
		for (char c : inputs) {
			if (c == 'D' || c == 'd' || c == 'A' || c == 'a') {
				verticalMovement = true;
				break;
			}
		}

		// If no, set speed 0, if yes, then set the speed direction accordingly.
		if (!verticalMovement) {
			player.setXSpeed(0);
		} else {
			// Remember, this is the lifted up key, so it is reversed
			if (e.getKeyChar() == 'A' || e.getKeyChar() == 'a')
				player.setXSpeed(VELOCITY_CONST * 5);
			if (e.getKeyChar() == 'D' || e.getKeyChar() == 'd')
				player.setXSpeed(-VELOCITY_CONST * 5);
		}
	}
}
