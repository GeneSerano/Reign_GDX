package com.southcentralpositronics.reign_gdx.graphics;

import com.southcentralpositronics.reign_gdx.level.tile.voidTile;

public class Sprite {
	// Projectile Sprites
	public static Sprite spellSprite     = new Sprite(16, 0, 0, SpriteSheet.spellTileSheet);
	public static Sprite arrowSprite     = new Sprite(16, 1, 0, SpriteSheet.projectileTileSheet);
	//	Particle Sprites
	public static Sprite particle_normal = new Sprite(1, 0xff6e210b);


	// Spawn level spritesw
	public static Sprite spawnGrassSprite   = new Sprite(16, 0, 0, SpriteSheet.spawnTileSheet);
	public static Sprite spawnHedgeSprite   = new Sprite(16, 1, 0, SpriteSheet.spawnTileSheet);
	public static Sprite spawnWaterSprite   = new Sprite(16, 2, 0, SpriteSheet.spawnTileSheet);
	public static Sprite spawnWallSprite    = new Sprite(16, 0, 2, SpriteSheet.spawnTileSheet);
	public static Sprite spawnWallAltSprite = new Sprite(16, 0, 1, SpriteSheet.spawnTileSheet);
	public static Sprite spawnFloorSprite   = new Sprite(16, 1, 1, SpriteSheet.spawnTileSheet);

	public static Sprite floorSprite  = new Sprite(16, 1, 0, SpriteSheet.mainTileSheet);
	public static Sprite grassSprite  = new Sprite(16, 0, 0, SpriteSheet.mainTileSheet);
	public static Sprite flowerSprite = new Sprite(16, 1, 0, SpriteSheet.mainTileSheet);
	public static Sprite rockSprite   = new Sprite(16, 2, 0, SpriteSheet.mainTileSheet);
	public static Sprite voidSprite   = new Sprite(16, voidTile.color);

	// Player sprites
	public final int         SIZE;
	public       int[]       pixels;
	protected    SpriteSheet sheet;
	private      int         x;
	private       int y;
	private final int width;
	private final int height;

	public Sprite(int[] pixels, int width, int height) {
		if (width == height) {
			SIZE = width;
		} else {
			SIZE = -1;
		}
		this.width  = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
	}

	protected Sprite(SpriteSheet sheet, int width, int height) {
		if (width == height) {
			SIZE = width;
		} else {
			SIZE = -1;
		}
		this.width  = width;
		this.height = height;
		this.sheet  = sheet;
	}

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE        = size;
		pixels      = new int[SIZE * SIZE];
		this.x      = x * SIZE;
		this.y      = y * SIZE;
		this.width  = SIZE;
		this.height = SIZE;
		this.sheet  = sheet;
		load();
	}

	public Sprite(int size, int x, int y, int color) {
		SIZE        = size;
		this.width  = SIZE;
		this.height = SIZE;
		this.x      = x;
		this.y      = y;
		pixels      = new int[SIZE * SIZE];
		setColor(color);
	}

	public Sprite(int size, int color) {
		SIZE        = size;
		this.width  = SIZE;
		this.height = SIZE;
		pixels      = new int[SIZE * SIZE];
		setColor(color);
	}

	public Sprite(int width, int height, int color) {
		this.width  = width;
		this.height = height;
		SIZE        = -1;
		pixels      = new int[width * height];
		setColor(color);
	}

	public static Sprite[] splitSpriteSheet(SpriteSheet sheet) {
		int   sheetHeight   = sheet.getSheetHeight();
		int   sheetWidth    = sheet.getSheetWidth();
		int   sheetSize     = sheetWidth * sheetHeight;
		int   spriteWidth   = sheet.SPRITE_WIDTH;
		int   spriteHeight  = sheet.SPRITE_HEIGHT;
		int   spriteSize    = spriteWidth * spriteHeight;
		int   spriteCount   = sheetSize / spriteSize;
		int   rowCount      = sheetHeight / spriteHeight;
		int   columnCount   = sheetWidth / spriteWidth;
		int   currentSprite = 0;
		int[] pixels        = new int[spriteSize];

		Sprite[] sprites = new Sprite[spriteCount];

		for (int yp = 0; yp < rowCount; yp++) {
			for (int xp = 0; xp < columnCount; xp++) {

				for (int y = 0; y < spriteHeight; y++) {
					for (int x = 0; x < spriteWidth; x++) {

						int xOffset = x + xp * spriteWidth;
						int yOffset = y + yp * spriteHeight;
						pixels[x + y * spriteWidth] = sheet.getPixels()[xOffset + yOffset * sheetWidth];
					}
				}
				sprites[currentSprite] = new Sprite(pixels, spriteWidth, spriteHeight);
				currentSprite++;
			}
		}
		return sprites;
	}

	public static Sprite rotateSprite(Sprite sprite, double angle) {
		int[] spritePixels = rotateSpritePixels(sprite.pixels, sprite.width, sprite.height, angle);
		return new Sprite(spritePixels, sprite.width, sprite.height);
	}

	private static int[] rotateSpritePixels(int[] pixels, int width, int height, double angle) {
		int[] result = new int[width * height];

		double nx_x = rotateX(-angle, 1.0, 0.0);
		double nx_y = rotateY(-angle, 1.0, 0.0);
		double ny_x = rotateX(-angle, 0.0, 1.0);
		double ny_y = rotateY(-angle, 0.0, 1.0);

		double x0 = rotateX(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
		double y0 = rotateY(-angle, -width / 2.0, -height / 2.0) + height / 2.0;

		for (int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;

			for (int x = 0; x < width; x++) {
				int xx    = (int) x1;
				int yy    = (int) y1;
				int color = 0;

				if (xx < 0 || xx >= width || yy < 0 || yy >= height) {
					color = Screen.ALPHA_CHAN;
				} else {
					color = pixels[xx + yy * width];
				}
				result[x + y * width] = color;

				x1 += nx_x;
				y1 += nx_y;
			}
			x0 += ny_x;
			y0 += ny_y;
		}

		return result;
	}

	private static double rotateX(double angle, double x, double y) {
		double result;
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);

		result = x * cos + y * -sin;
		return result;

	}

	private static double rotateY(double angle, double x, double y) {
		double result;
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);

		result = x * sin + y * cos;
		return result;

	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pa = x + y * width;
				int pb = (x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH;
				pixels[pa] = sheet.pixels[pb];
			}
		}
	}

	private void setColor(int color) {
		for (int i = 0; i < width * height; i++)
		     pixels[i] = color;
	}


}
