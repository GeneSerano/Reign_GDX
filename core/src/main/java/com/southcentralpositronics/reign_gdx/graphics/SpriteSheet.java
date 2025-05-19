package com.southcentralpositronics.reign_gdx.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
	public static SpriteSheet mainTileSheet       = new SpriteSheet("/textures/sheets/sprite_sheet.png", 256);
	public static SpriteSheet spawnTileSheet      = new SpriteSheet("/textures/sheets/spawn_sprite_sheet.png", 48);
	public static SpriteSheet spellTileSheet      = new SpriteSheet("/textures/sheets/spell_sprite_sheet.png", 48);
	public static SpriteSheet projectileTileSheet = new SpriteSheet("/textures/sheets/projectiles/wizard.png", 48);
	public static SpriteSheet player              = new SpriteSheet("/textures/King_Cherno.png", 128, 96);
	public static SpriteSheet player_up           = new SpriteSheet(player, 0, 0, 1, 3, 32);
	public static SpriteSheet player_down         = new SpriteSheet(player, 2, 0, 1, 3, 32);
	public static SpriteSheet player_left         = new SpriteSheet(player, 3, 0, 1, 3, 32);
	public static SpriteSheet player_right        = new SpriteSheet(player, 1, 0, 1, 3, 32);
	public static SpriteSheet dummy               = new SpriteSheet("/textures/King_Berno.png", 128, 96);
	public static SpriteSheet dummy_up            = new SpriteSheet(dummy, 0, 0, 1, 3, 32);
	public static SpriteSheet dummy_down          = new SpriteSheet(dummy, 2, 0, 1, 3, 32);
	public static SpriteSheet dummy_left          = new SpriteSheet(dummy, 3, 0, 1, 3, 32);
	public static SpriteSheet dummy_right         = new SpriteSheet(dummy, 1, 0, 1, 3, 32);
	public final  int         SPRITE_SIZE, SPRITE_WIDTH, SPRITE_HEIGHT;
	private final String path;
	public        int[]  pixels;
	private       int    sheetWidth, sheetHeight;
	private Sprite[] sprites;

	public SpriteSheet(SpriteSheet sheet, int x, int y, int sheetWidth, int sheetHeight, int spriteSize) {
		int xx = x * spriteSize;       // sprite precision
		int yy = y * spriteSize;       // sprite precision
		int w  = sheetWidth * spriteSize;   // pixel precision
		int h  = sheetHeight * spriteSize;  // pixel precision
		this.path     = sheet.path;
		SPRITE_WIDTH  = w;
		SPRITE_HEIGHT = h;
		pixels        = new int[w * h];

		if (sheetWidth == sheetHeight) {             // if square
			SPRITE_SIZE = sheetWidth;
		} else {
			SPRITE_SIZE = -1;
		}

		for (int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;

			for (int x0 = 0; x0 < w; x0++) {
				int xp = xx + x0;
				int p1 = x0 + y0 * w;
				int p2 = xp + yp * sheet.SPRITE_WIDTH;
				pixels[p1] = sheet.pixels[p2];
			}
		}
		int frame = 0;
		sprites = new Sprite[sheetWidth * sheetHeight];
		for (int ya = 0; ya < sheetHeight; ya++) {
			for (int xa = 0; xa < sheetWidth; xa++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {
					for (int x0 = 0; x0 < spriteSize; x0++) {
						int sP1 = x0 + y0 * spriteSize;
						int sP2 = (x0 + xa * spriteSize) + (y0 + ya * spriteSize) * SPRITE_WIDTH;
						spritePixels[sP1] = pixels[sP2];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}

	public SpriteSheet(String path, int sheetWidth, int sheetHeight) {
		this.path     = path;
		SPRITE_SIZE   = -1;
		SPRITE_WIDTH  = sheetWidth;
		SPRITE_HEIGHT = sheetHeight;
		pixels        = new int[SPRITE_WIDTH * SPRITE_HEIGHT];
		load();
	}

	public SpriteSheet(String path, int size) {
		this.path     = path;
		SPRITE_SIZE   = size;
		SPRITE_WIDTH  = size;
		SPRITE_HEIGHT = size;
		pixels        = new int[size * size];
		load();
	}

	public Sprite[] getSprites() {
		return sprites;
	}

	public int getSheetHeight() {
		return sheetHeight;
	}

	public int getSheetWidth() {
		return sheetWidth;
	}

	private void load() {
		try {
			System.out.print("Trying to load: " + path);
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			sheetWidth  = image.getWidth();
			sheetHeight = image.getHeight();
			pixels      = new int[sheetWidth * sheetHeight];
			image.getRGB(0, 0, sheetWidth, sheetHeight, pixels, 0, sheetWidth);
			System.out.println(" --- Load succeeded!");
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.print(" --- Load FAILED!!");
			System.exit(56);
		}

	}

	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}
}
