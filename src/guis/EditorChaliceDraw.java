package guis;

import logic.Chalice;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * This functions as a brush which edits the game map.
 * Draws 1 chalice onto the map per click to a specific location.
 */
public class EditorChaliceDraw extends Draw {
	/**
	 * The editor canvas which this class draws onto.
	 */
	private EditorCanvas ec;

	/**
	 * Your everyday useless constructor.
	 */
	public EditorChaliceDraw() { super(); }

	/**
	 * Sets the editor canvas, so
	 * @param mc is the canvas which the child of Draw will draw on.
	 */
	@Override
	public void setCanvas(MyCanvas mc) {
		super.setCanvas(mc);
		ec = (EditorCanvas) mc;
	}

	/**
	 * Draws a single Chalice object onto the game map where the cursor is at. (Once per press.)
	 * @param g The graphics which this method draws onto.
	 */
	@Override
	protected void makeDraw(Graphics g) {
		double col = (double) (startx / ec.tileSize);
		double row = (double) (starty / ec.tileSize);
		new Chalice(new Point2D.Double(col, row), ec.gameMap);
		ec.myCanvasDraw.makeDraw(g);
	}

	/**
	 * When the user presses the mouse, the method sets the start positions and then draws.
	 * @param arg0 The mouse event.
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
