package logic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import static logic.Constants.DELTA_TIME;

/**
 * The game clock updates every Updatable periodically.
 * Also gives them the delta time (time difference between the refreshes, time passed since the last refresh).
 */
public class GameClock extends Timer {
	/**
	 * The elements which needs to be updated.
	 */
	private final ArrayList<Updatable> updatables;

	/**
	 * Stores the number of updates.
	 */
	private int ticks;

	/**
	 * Saves the time when the timer starts.
	 */
	private final long startMs;

	/**
	 * Updates the time since the timer started.
	 */
	private long timeSinceStart;

	/**
	 * Ticks per second, or frames per second. How many update cycles can be done in one second?
	 */
	private int fps;

	/**
	 * This action listener calls the GameTimer's tickAll(dtime) method and refreshes the variables of the timer.
	 */
	public class TickerActionListener implements ActionListener {
		public TickerActionListener() { super(); }
		@Override
		public void actionPerformed(ActionEvent e) {
			ticks++;
			long oldTimeSinceStart = timeSinceStart;
			timeSinceStart = new Date().getTime() - startMs;
			long dtime = timeSinceStart - oldTimeSinceStart;
			if (oldTimeSinceStart % 1000 > timeSinceStart % 1000) {
				fps = ticks;
				ticks = 0;
			}
			tickAll((int) dtime);
		}
	}

	/**
	 * Just starts the timer and initializes it. Sets the necessary variables.
	 */
	public GameClock() {
		super((int) DELTA_TIME, null);
		addActionListener(new TickerActionListener());
		this.updatables = new ArrayList<>();
		startMs = new Date().getTime();
		start();
	}

	/**
	 * Adds an updatable to the updatables array.
	 * @param u the updatable to add.
	 */
	public void addUpdatable(Updatable u) {
		updatables.add(u);
	}

	/**
	 * Removes an updatable from the updatables array.
	 * @param u the updatable to remove.
	 */
	public void removeUpdatable(Updatable u) {
		updatables.remove(u);
	}

	/**
	 * Ticks all updatable.
	 * @param dtime time passed from the previous update.
	 */
	private void tickAll(int dtime) {
		for (Updatable updatable : updatables) {
			updatable.tick(dtime);
		}
	}

	/**
	 * @return the frames per second or update cycles per second.
	 */
	public int getFPS() {
		return fps;
	}

	/**
	 * @return the time in milliseconds when the timer started.
	 */
	public long getTimeSinceStart() {
		return timeSinceStart;
	}
}