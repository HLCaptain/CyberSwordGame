package logic;

/**
 * These are some basic constants to keep this program up and running.
 * Some are kind of strict, some can be more widely modified.
 */
public class Constants {
	// Time related constants.

	/* Set this to 1-12. At higher values the game starts to lag and to slow down.
	 * Probably because it has to wait, instead of refreshing everything once again.
	 */
	public static final double DELTA_TIME = 12;
	public static final double GRAVITY = 9.81;
	public static final double VELOCITY_CONST = 0.02;

	// Sizing related constants.
	public static final int TILE_SCALE = 128;
	public static final int EDITOR_TILE_SCALE = 32;

	// Rendering related constants.
	public static final int SMALL_PIC_WIDTH = 32;
	public static final int SMALL_PIC_HEIGHT = 32;

	// [Intentional game design] related constants.
	public static final int MIN_ROWS = 10;
	public static final int MAX_ROWS = 40;
	public static final int MIN_COLUMNS = 20;
	public static final int MAX_COLUMNS = 120;

	// Window sizing related constants.
	public static final int MAX_GAME_FRAME_WIDTH = 1600;
	public static final int MAX_GAME_FRAME_HEIGHT = 960;
	public static final int MIN_GAME_FRAME_WIDTH = TILE_SCALE * MIN_ROWS;
	public static final int MIN_GAME_FRAME_HEIGHT = TILE_SCALE * MIN_COLUMNS;
}
