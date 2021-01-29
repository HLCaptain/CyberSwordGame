package guis;

import logic.EnemySpawner;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Draws "enemy spawners" on the canvas.
 */
public class EditorEnemySpawnerDraw extends Draw {
	/**
	 * The canvas which we will draw the enemy spawners on.
	 */
	private EditorCanvas ec;

	/**
	 * Your everyday useless constructor.
	 */
	public EditorEnemySpawnerDraw() { super(); }

	/**
	 * We set the custom editor canvas, because it has access to the game map which we will modify.
	 * @param mc is the canvas which we draw enemy spawners on.
	 */
	@Override
	public void setCanvas(MyCanvas mc) {
		super.setCanvas(mc);
		ec = (EditorCanvas) mc;
	}

	/**
	 * Sets an array with the appropriate actor type then redraws the array.
	 * @param g the graphics class this method draws on.
	 */
	@Override
	protected void makeDraw(Graphics g) {
		double col = (double)(startx / ec.tileSize);
		double row = (double)(starty / ec.tileSize);
		new EnemySpawner(new Point2D.Double(col, row), ec.gameMap);
		ec.myCanvasDraw.makeDraw(g);
	}

	/**
	 * Draws enemy spawners onto the canvas. One spawner per click.
	 * @param arg0 current mouse event, we use this to track down the cursor position.
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		startx = arg0.getX();
		starty = arg0.getY();
		makeDraw(canvas.getBottom());
		canvas.update(canvas.getGraphics());
	}

	/**
	 * Neutralizing the method not to do anything.
	 * @param arg0 The current MouseEvent.
	 */
	public void mouseReleased(MouseEvent arg0) { }

	/**
	 * Neutralizing the method not to do anything.
	 * @param arg0 The current MouseEvent.
	 */
	public void mouseDragged(MouseEvent arg0) {	}
}
