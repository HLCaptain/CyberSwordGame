package guis;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class responsible for the change of different brushes when a button is clicked.
 * Can set a delegate for the delegator, so the delegator can call a drawing method on the delegate.
 */
public class DrawButtonListener implements ActionListener {
	/**
	 * The delegator that holds the actual Draw object.
	 */
	private MouseListenerDelegator mld;

	/**
	 * Constructor sets the delegator at the start.
	 * @param m The delegator which has a delegate to draw.
	 */
	public DrawButtonListener(MouseListenerDelegator m) { mld = m; }
	
	/**
	 * The component that was clicked last time.
	 */
	private Component last;
		
	/**
	 * Sets the Draw subclass according to the action command. The button last
	 * clicked is enabled, current button disabled. 
	 */
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();

		if ("DrawEmptyTile".equals(cmd)) {
			mld.setDelegate(new EditorEmptyTileDraw());
		} else if ("DrawGroundTile".equals(cmd)) {
			mld.setDelegate(new EditorGroundTileDraw());
		} else if ("DrawPlayerSpawn".equals(cmd)) {
			mld.setDelegate(new EditorPlayerSpawnDraw());
		} else if ("DrawChalice".equals(cmd)) {
			mld.setDelegate(new EditorChaliceDraw());
		} else if ("DrawEnemySpawner".equals(cmd)) {
			mld.setDelegate(new EditorEnemySpawnerDraw());
		}

		if (last != null) last.setEnabled(true);
		last = (Component)arg0.getSource();
		last.setEnabled(false);
	}
}
