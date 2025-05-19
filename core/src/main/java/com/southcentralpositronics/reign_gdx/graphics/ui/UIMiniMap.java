package com.southcentralpositronics.reign_gdx.graphics.ui;

import com.southcentralpositronics.reign_gdx.entity.mob.Mob;
import com.southcentralpositronics.reign_gdx.entity.mob.PlayerMob;
import com.southcentralpositronics.reign_gdx.level.Level;
import com.southcentralpositronics.reign_gdx.level.tile.Tile;
import com.southcentralpositronics.reign_gdx.util.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

public class UIMiniMap extends UIComponent {
	private              Level         level;
	private              BufferedImage image;
	private static       int           width; // Change to match your map size
	private static       int           height;
	private static       Tile[][]      tileList  = null;
	private static final int           TILE_SIZE = 3; // Size of each tile in pixels

	private static final HashMap<Tile, Integer> tileColors = new HashMap<>();

	static {
		tileColors.put(Tile.spawnGrassTile, Tile.pixelColor_spawnGrassTile);
		tileColors.put(Tile.spawnHedgeTile, Tile.pixelColor_spawnHedgeTile);
		tileColors.put(Tile.spawnWaterTile, Tile.pixelColor_spawnWaterTile);
		tileColors.put(Tile.spawnWallTile, Tile.pixelColor_spawnWallTile);
		tileColors.put(Tile.spawnWallAltTile, Tile.pixelColor_spawnWallAltTile);
		tileColors.put(Tile.spawnFloorTile, Tile.pixelColor_spawnFloorTile);
	}


	public UIMiniMap(Vector2i position) {
		super(position);
	}

	public void init(Level level) {
		if (tileList == null) {
			this.level = level;
			tileList   = generateTileList(level);
			height     = tileList.length;
			width      = tileList[0].length;
			BufferedImage img = generateMiniMap(tileList);
			setImage(img);
		}
	}

	@Override
	public void update() {
		if (tileList != null) {
			BufferedImage img = generateMiniMap(tileList);
			setImage(img);
		}
	}

	public void setImage() {
		this.image = null;
	}

	public void setImage(BufferedImage image) {
		if (image != null) this.image = image;
		else setImage();
	}

	public BufferedImage generateMiniMap(Tile[][] map) {
		int           width  = map[0].length * TILE_SIZE;
		int           height = map.length * TILE_SIZE;
		BufferedImage image  = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D    g      = image.createGraphics();

		// Draw tiles onto the image
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				Tile tile  = map[y][x]; // Get the tile at (x, y)
				int  color = tileColors.getOrDefault(tile, 0xff000000); // Default to black if not found
				g.setColor(new Color(color, true));

				PlayerMob player      = level.getClientPlayer();
				int       playerTileX = (int) Math.round(player.getX()) / 16;
				int       playerTileY = (int) Math.round(player.getY()) / 16;
				if (playerTileX == x && playerTileY == y) {
					g.setColor(Color.BLUE);
				}
				List<Mob> mobs = level.getMobs();
				for (int i = 0; i < mobs.size(); i++) {
					Mob mob      = mobs.get(i);
					int mobTileX = (int) Math.round(mob.getX()) / 16;
					int mobTileY = (int) Math.round(mob.getY()) / 16;
					if (mobTileX == x && mobTileY == y) {
						g.setColor(Color.RED);

					}
				}

				g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
			}
		}
		g.dispose();
		return image;
	}

	public Tile[][] generateTileList(Level level) {
		int tilesX = level.getWidth();
		int tilesY = level.getHeight();

		tileList = new Tile[tilesY][tilesX];

		for (int ty = 0; ty < tilesY; ty++) {
			for (int tx = 0; tx < tilesX; tx++) {
				tileList[ty][tx] = level.getTile(tx, ty);
			}
		}
		return tileList;
	}

	public void render(Graphics g) {
		int posX = position.x + offset.x;
		int posY = position.y + offset.y;

		if (image != null) {
			g.drawImage(image, posX, posY, null);
		}
	}
}
