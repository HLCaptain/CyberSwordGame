package guis;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Helper class for easy change of Draws in MyCanvas.
 * All Listener methods are delegated to delegate.
 */
public class MouseListenerDelegator implements MouseListener, MouseMotionListener {

	/**
	 * The active drawer which can draw onto the canvas.
	 */
	private Draw delegate;

	/**
	 * The delegate use this canvas to draw onto.
	 */
	private MyCanvas canvas;

	/**
	 * Setting canvas and adding itself to canvas as MouseListener.
	 * @param mc the canvas which this mouse listener "listens" to.
	 */
	public MouseListenerDelegator(MyCanvas mc) {
		canvas = mc;
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
	}

	/**
	 * Setting the delegate.
	 * @param delegate will be the class inherited from Draw which will draw on the canvas.
	 */
	public void setDelegate(Draw delegate) {
		this.delegate = delegate;
		delegate.setCanvas(canvas);
	}

	/**
	 * Redirecting MouseEvent calls to the delegates.
	 * @param arg0 The current mouse event.
	 */
	public void mouseMoved(MouseEvent arg0) {
		delegate.mouseMoved(arg0);
	}

	/**
	 * Redirecting MouseEvent calls to the delegates.
	 * @param arg0 The current mouse event.
	 */
	public void mouseClicked(MouseEvent arg0) {
		delegate.mouseClicked(arg0);
	}

	/**
	 * Redirecting MouseEvent calls to the delegates.
	 * @param arg0 The current mouse event.
	 */
	public void mouseEntered(MouseEvent arg0) {
		delegate.mouseEntered(arg0);
	}

	/**
	 * Redirecting MouseEvent calls to the delegates.
	 * @param arg0 The current mouse event.
	 */
	public void mouseExited(MouseEvent arg0) {
		delegate.mouseExited(arg0);
	}

	/**
	 * Redirecting MouseEvent calls to the delegates.
	 * @param arg0 The current mouse event.
	 */
	public void mousePressed(MouseEvent arg0) {
		delegate.mousePressed(arg0);
	}

	/**
	 * Redirecting MouseEvent calls to the delegates.
	 * @param arg0 The current mouse event.
	 */
	public void mouseReleased(MouseEvent arg0) {
		delegate.mouseReleased(arg0);
	}

	/**
	 * Redirecting MouseEvent calls to the delegates.
	 * @param arg0 The current mouse event.
	 */
	public void mouseDragged(MouseEvent arg0) {
		delegate.mouseDragged(arg0);
	}
}
