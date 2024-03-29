package guis;

import logic.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static logic.Constants.MIN_GAME_FRAME_WIDTH;

public class GameCanvasDraw extends Draw implements Updatable {
	public GameCanvasDraw() { super(); }

	private GameCanvas gc;
	private BufferedImage emptyTile = null;
	private BufferedImage groundTile = null;
	private BufferedImage playerSpawn = null;
	private BufferedImage player = null;
	private BufferedImage chalice = null;
	private BufferedImage enemy = null;
	private BufferedImage enemySpawner = null;
	private BufferedImage pistolBullet = null;
	private double dtime;
	private String endMsg = null;

	@Override
	public void setCanvas(MyCanvas mc) {
		super.setCanvas(mc);
		gc = (GameCanvas)mc;
	}

	public void setEndMsg(String endMsg) { this.endMsg = endMsg; }

	@Override
	protected void makeDraw(Graphics g) {
		emptyTile = TileType.EMPTY.getImg();
		groundTile = TileType.GROUND.getImg();
		playerSpawn = ActorType.PLAYERSPAWN.getImg();
		chalice = ActorType.CHALICE.getImg();
		enemy = ActorType.ENEMY.getImg();
		enemySpawner = ActorType.ENEMYSPAWNER.getImg();
		pistolBullet = ActorType.PISTOLBULLET.getImg();
		if (gc.player == null)
			player = ActorType.PLAYER.getImg();
		else
			player = gc.player.getImg();

		if (gc.getCamera().getTarget() == null)
			return;
		drawTiles(g);
		drawActors(g);
		drawLifeBar(g, gc.player);
		if (endMsg != null)
			gameEnded(g, endMsg);
	}

	private void drawTiles(Graphics g) {
		for (int i = 0; i < gc.gameMap.getRowNum(); i++) {
			for (int j = 0; j < gc.gameMap.getColumnNum(); j++) {
				Point2D.Double p = gc.gameMap.getGrid().get(i).get(j).getPosition();

				BufferedImage img = null;
				if (gc.gameMap.getGrid().get(i).get(j).getImgPath().equals(TileType.EMPTY.getImgPath())) {
					img = emptyTile;
				} else if (gc.gameMap.getGrid().get(i).get(j).getImgPath().equals(TileType.GROUND.getImgPath())) {
					img = groundTile;
				}
				g.drawImage(
						img,
						(int) ((p.x - gc.getCamera().getPosition().getX()) * gc.getCamera().getScale()),
						(int) ((p.y - gc.getCamera().getPosition().getY()) * gc.getCamera().getScale()),
						(int) (gc.getCamera().getScale()),
						(int) (gc.getCamera().getScale()),
						null
				);
			}
		}
	}

	private void drawActors(Graphics g) {
		if (gc.gameMap.getActors() == null)
			return;
		int actorSize = gc.gameMap.getActors().size();

		for (int i = 0; i < actorSize; i++) {
			Actor a = gc.gameMap.getActors().get(i);
			BufferedImage img = null;
			if (a.getImgPath().equals(ActorType.PLAYERSPAWN.getImgPath())) {
				img = playerSpawn;
			} else if (gc.player != null) {
				if (a.getImgPath().equals(gc.player.getImgPath()))
					img = player;
				if (a.getImgPath().equals(ActorType.PLAYER.getImgPath()))
					img = player;
			}
			if (a.getImgPath().equals(ActorType.CHALICE.getImgPath()))
				img = chalice;
			if (a.getImgPath().equals(ActorType.ENEMY.getImgPath()))
				img = enemy;
			if (a.getImgPath().equals(ActorType.ENEMYSPAWNER.getImgPath()))
				img = enemySpawner;
			if (a.getImgPath().equals(ActorType.PISTOLBULLET.getImgPath()))
				img = pistolBullet;

			g.drawImage(
					img,
					(int) ((a.getPosition().getX() - gc.getCamera().getPosition().getX()) * gc.getCamera().getScale()),
					(int) ((a.getPosition().getY() - gc.getCamera().getPosition().getY()) * gc.getCamera().getScale()),
					(int) (gc.getCamera().getScale()),
					(int) (gc.getCamera().getScale()),
					null
			);
		}
		g.setColor(Color.black);
		g.drawString(gc.gameClock.getFPS() + " FPS", 25, 25);
		g.drawString(dtime + " ms", 25, 35);
	}

	private void drawLifeBar(Graphics g, Player player) {
		g.setColor(Color.red);
		g.fillRect(50, 50, MIN_GAME_FRAME_WIDTH / 2, 20);
		g.setColor(Color.green);
		g.fillRect(50, 50, (int) ((MIN_GAME_FRAME_WIDTH / 2) * player.getHp() / player.getMaxHp()), 20);
	}

	@Override
	public void tick(double dtime) {
		this.dtime = dtime;
		canvas.update(canvas.getGraphics());
		makeDraw(canvas.getBottom());
	}

	private void gameEnded(Graphics g, String msg) {
		g.setColor(Color.blue);
		g.fillRect(gc.getWidth() / 2 - 20, gc.getHeight() / 2 - 15, 100, 20);
		g.setColor(Color.red);
		g.drawString(msg, gc.getWidth() / 2, gc.getHeight() / 2);
		canvas.update(canvas.getGraphics());
	}
}