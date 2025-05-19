package com.southcentralpositronics.reign_gdx.graphics;

import com.southcentralpositronics.reign_gdx.entity.mob.Mob;
import com.southcentralpositronics.reign_gdx.entity.projectile.Projectile;
import com.southcentralpositronics.reign_gdx.level.tile.Tile;

import java.util.Random;

public class Screen {
	public static final int    ALPHA_CHAN    = 0xffff00ff;
	public final        int    MAP_SIZE      = 8;
	public final        int    MAP_SIZE_MASK = MAP_SIZE - 1;
	private final       Random random        = new Random();
	public              int    width, height;
	public  int[] pixels;
	public  int[] tiles = new int[MAP_SIZE * MAP_SIZE]; // 4096 tiles
	private int   yOffset;
	private int   xOffset;


	public Screen(int width, int height) {
		this.width  = width;
		this.height = height;
		pixels      = new int[width * height]; // 50_400 pixels

		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;

		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp;

				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;

				int pa = xa + ya * width;
				int pb = x + y * tile.sprite.SIZE;
				int pc = tile.sprite.pixels[pb];

				if (pc != ALPHA_CHAN) pixels[pa] = pc;
			}
		}
	}

	//	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
//		if (fixed) {
//			xp -= xOffset;
//			yp -= yOffset;
//		}
//
//		for (int y = 0; y < sprite.SIZE; y++) {
//			int ya = y + yp;
//			for (int x = 0; x < sprite.SIZE; x++) {
//				int xa = x + xp;
//				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
//				if (xa < 0) xa = 0;
//
//				int pa = xa + ya * width;
//				int pb = x + y * 16;
//				int pc = sprite.pixels[pb];
//
//				if (pc != 0xFFFF00FF) pixels[pa] = pc;
//			}
//		}
//	}
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}

		int spriteHeight = sprite.getHeight();
		int spriteWidth  = sprite.getWidth();

		for (int y = 0; y < spriteHeight; y++) {
			int ya = y + yp;
			for (int x = 0; x < spriteWidth; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;

				int pa = xa + ya * width;
				int pb = x + y * spriteWidth;
				int pc = sprite.pixels[pb];

				if (pc != ALPHA_CHAN) pixels[pa] = pc;
			}
		}
	}

	public void renderSpriteSheet(int xp, int yp, SpriteSheet sheet, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}

		int sheetHeight = sheet.SPRITE_HEIGHT;
		int sheetWidth  = sheet.SPRITE_WIDTH;

		for (int y = 0; y < sheetHeight; y++) {
			int ya = y + yp;
			for (int x = 0; x < sheetWidth; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;

				int pa = xa + ya * width;
				int pb = x + y * sheetWidth;
				int pc = sheet.pixels[pb];

				if (pc != ALPHA_CHAN) pixels[pa] = pc;
			}
		}
	}

	public void renderProjectile(int xp, int yp, Projectile p) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < p.getSpriteSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < p.getSpriteSize(); x++) {
				int xa = x + xp;
				if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height) {
					break;
				}
				if (xa < 0) {
					xa = 0;
				}
				int col = p.getSprite().pixels[x + y * p.getSprite().SIZE];
				if (col != ALPHA_CHAN) {
					pixels[xa + ya * width] = col;
				}
			}
		}
	}

	public void renderMob(int xp, int yp, Mob mob) {
		xp -= xOffset;
		yp -= yOffset;

		for (int y = 0; y < 32; y++) {
			int ya = y + yp;
			int ys = y;
			for (int x = 0; x < 32; x++) {
				int xa = x + xp;
				int xs = x;

				if (xa < -32 || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;

				int pa = xa + ya * width;
				int pb = xs + ys * 32;
				int pc = mob.getSprite().pixels[pb];

				if (pc != ALPHA_CHAN) pixels[pa] = pc;
			}
		}
	}

	public void renderMob(int xp, int yp, Sprite sprite, int flip) {
		xp -= xOffset;
		yp -= yOffset;

		for (int y = 0; y < 32; y++) {
			int ya = y + yp;
			int ys = y;
			if (flip == 2 || flip == 3) ys = 31 - y;

			for (int x = 0; x < 32; x++) {
				int xa = x + xp;
				int xs = x;
				if (flip == 1 || flip == 3) xs = 31 - x;

				if (xa < -32 || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;

				int pa = xa + ya * width;
				int pb = xs + ys * 32;
				int pc = sprite.pixels[pb];

				if (pc != ALPHA_CHAN) pixels[pa] = pc;
			}
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void drawRect(int xp, int yp, int width, int height, int color, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}

		for (int x = xp; x <= xp + width; x++) {
			if (x < 0 || x >= this.width || yp >= this.height) continue;
			if (yp > 0) pixels[x + yp * this.width] = color;
			if (yp + height >= this.height) continue;
			if (yp + height > 0) pixels[x + (yp + height) * this.width] = color;
		}
		for (int y = yp; y <= yp + height; y++) {
			if (xp >= this.width || y < 0 || y >= this.height) continue;
			if (xp > 0) pixels[xp + y * this.width] = color;
			if (xp + width >= this.width) continue;
			if (xp + width > 0) pixels[(xp + width) + y * this.width] = color;
		}
	}

	public void renderString(int xp, int yp, Sprite sprite, int color, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}

		int spriteHeight = sprite.getHeight();
		int spriteWidth  = sprite.getWidth();

		for (int y = 0; y < spriteHeight; y++) {
			int ya = y + yp;
			for (int x = 0; x < spriteWidth; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;

				int pa = xa + ya * width;
				int pb = x + y * spriteWidth;
				int pc = sprite.pixels[pb];

				if (pc != ALPHA_CHAN) pixels[pa] = color;
			}
		}
	}
}
