package guis;

import logic.GroundTile;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Draws "ground tiles" on the canvas.
 */
public class EditorGroundTileDraw extends Draw {

	/**
	 * The canvas which we will draw ground tiles on.
	 */
	private EditorCanvas ec;

	/**
	 * Your everyday useless constructor.
	 */
	public EditorGroundTileDraw() { super(); }

	/**
	 * We set the custom editor canvas, because it has access to the game map which we will modify.
	 * @param mc is the canvas which we draw ground tiles on.
	 */
	@Override
	public void setCanvas(MyCanvas mc) {
		super.setCanvas(mc);
		ec = (EditorCanvas) mc;
	}

	/**
	 * Sets an array with the appropriate tile type then redraws the array.
	 * @param g the graphics class this method draws on.
	 */
	public void makeDraw(Graphics g) {
		int col = (int) Math.floor((double) (endx / ec.tileSize));
		int row = (int) Math.floor((double) (endy / ec.tileSize));
		if (col < ec.gameMap.getGrid().get(0).size() && row < ec.gameMap.getGrid().size() && col >= 0 && row >= 0)
			ec.gameMap.getGrid().get(row).set(col, new GroundTile(new Point2D.Double(col, row), new Rectangle2D.Double(col, row, 1, 1), ec.gameMap));
		ec.myCanvasDraw.makeDraw(g);
	}

	/**
	 * Draws ground tiles onto the canvas while dragging the mouse.
	 * @param arg0 current mouse event, we use this to track down the cursor position.
	 */
	@Override
	public void mouseDragged(MouseEvent arg0) {
		realDraw(arg0, canvas.getBottom());
	}
}
