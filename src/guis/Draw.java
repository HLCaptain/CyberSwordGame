package guis;

import logic.Updatable;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Classes which inherit this class can draw on a MyCanvas.
 */
public abstract class Draw implements MouseListener, MouseMotionListener, Updatable {

	/**
	 * The canvas it draws on.
	 */
	protected MyCanvas canvas;

	/**
	 * Coordinates of mouse pointer when mouse was pressed.
	 */
	protected int startx, starty;

	/**
	 * Last coordinates of mouse pointer.
	 */
	protected int endx, endy;

	/**
	 * Current color to draw to the frame.
	 */
	protected Color color;

	/**
	 * Constructor.
	 */
	public Draw() { color = Color.BLACK; }

	/**
	 * Sets the canvas.
	 * @param mc is the canvas which the child of Draw will draw on.
	 */
	public void setCanvas(MyCanvas mc) {
		canvas = mc;
	}

	/**
	 * Implemented, but not used.
	 * @param arg0 The current MouseEvent.
	 */
	public void mouseMoved(MouseEvent arg0) { }

	/**
	 * Implemented, but not used.
	 * @param arg0 The current MouseEvent.
	 */
	public void mouseClicked(MouseEvent arg0) { }

	/**
	 * Implemented, but not used.
	 * @param arg0 The current MouseEvent.
	 */
	public void mouseEntered(MouseEvent arg0) { }

	/**
	 * Implemented, but not used.
	 * @param arg0 The current MouseEvent.
	 */
	public void mouseExited(MouseEvent arg0) { }


	/**
	 * Sets the startx and starty coordinates to those of the event.
	 */
	public void mousePressed(MouseEvent arg0) {
		startx = endx = arg0.getX();
		starty = endy = arg0.getY();
	}

	/**
	 * Draws on the bottom layer of the MyCanvas object.
	 */
	public void mouseReleased(MouseEvent arg0) {
		realDraw(arg0, canvas.getBottom());
	}

	/**
	 * Draws on the top layer of the MyCanvas object.
	 */
	public void mouseDragged(MouseEvent arg0) {
		realDraw(arg0, canvas.getTop());
	}

	/**
	 * Draws on the top layer, then on the bottom if the user finished drawing.
	 * @param arg0 current MouseEvent
	 * @param g2 the graphics object which we draw onto.
	 */
	protected void realDraw(MouseEvent arg0, Graphics g2) {
		// clearing previous image
		Graphics2D g = (Graphics2D) canvas.getTop();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		makeDraw(g);
		// reset composite
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		
		// setting new final point
		endx = arg0.getX();
		endy = arg0.getY();
		// drawing on the bottom layer
		g2.setColor(color);
		makeDraw(g2);
		canvas.update(canvas.getGraphics());
	}

	/**
	 * Abstract method for children. It implements the actual drawing.
	 * @param g The graphics which this method draws onto.
	 */
	protected abstract void makeDraw(Graphics g);
}
