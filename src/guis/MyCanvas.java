package guis;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A Canvas supporting 2 layers: bottom for permanent, top for temporary draws.
 */
public abstract class MyCanvas extends JComponent {

	/**
	 * Need for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The object inherited from Draw, which will draw on this canvas.
	 */
	public Draw myCanvasDraw;

	/**
	 * The image that will be drawn to the components Graphics.
	 */
	protected BufferedImage image;

	/**
	 * The bottom layer of the image.
	 */
	protected BufferedImage bottom;

	/**
	 * The temporary, transparent top layer of the image.
	 */
	protected BufferedImage top;

	/**
	 * The sizing dimensions of the canvas.
	 */
	protected Dimension d;

	/**
	 * Setting up the renderer for this canvas.
	 * @param myCanvasDraw is the Draw inherited object which will render out the canvas.
	 */
	public MyCanvas(Draw myCanvasDraw) {
		super();
		this.myCanvasDraw = myCanvasDraw;
		myCanvasDraw.setCanvas(this);
	}

	/**
	 * @return Gets the bottom graphics.
	 */
	public Graphics getBottom() {
		return bottom.getGraphics();
	}

	/**
	 * @return Gets the top graphics.
	 */
	public Graphics getTop() {
		return top.getGraphics();
	}

	/**
	 * Paints the component. Puts bottom and top on to image, then draws image.
	 * @param g the graphics layer to draw on.
	 */
	public void paint(Graphics g) {
		Graphics g0 = image.getGraphics();
		g0.drawImage(bottom,0,0,null);
		g0.drawImage(top, 0,0,null);
		g.drawImage(image,0,0,null);
	}

	/**
	 * Sets the size of the canvas.
	 * @param width new width of the canvas.
	 * @param height new height of the canvas.
	 */
	public abstract void setCanvasSize(int width, int height);
}
