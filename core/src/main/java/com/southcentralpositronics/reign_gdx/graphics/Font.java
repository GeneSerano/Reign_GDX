package com.southcentralpositronics.reign_gdx.graphics;

public class Font {

	private static final String      DROPPED_CHARS        = "gjpqy,.";
	public static        String      charIndex  = "";
	private static final SpriteSheet font       = new SpriteSheet("/fonts/arial.png", 16);
	private static final Sprite[]    characters = Sprite.splitSpriteSheet(font);
	private final        int         DEFAULT_CHAR_SPACING = 12;
	private final        int         MAX_LINE_LENGTH      = 10;


	public Font() {
		charIndex += "ABCDEFGHIJKLM";
		charIndex += "NOPQRSTUVWXYZ";
		charIndex += "abcdefghijklm";
		charIndex += "nopqrstuvwxyz";
		charIndex += "0123456789.,'";
		charIndex += "'\"\";:!@$%()-+";

	}

	public void render(int x, int y, Screen screen, int color, String string) {
		render(x, y, 0, screen, color, string);
	}

	public void render(int x, int y, int spacing, Screen screen, String string) {
		render(x, y, spacing, screen, 0, string);
	}

	public void render(int x, int y, Screen screen, String string) {
		render(x, y, 0, screen, 0, string);
	}

	public void render(int x, int y, int spacing, Screen screen, int color, String string) {
		int newLines = 0;
		int xOffset  = 0;
		for (int i = 0; i < string.length(); i++) {
			int  yOffset     = 0;
			char currentChar = string.charAt(i);
			int  index       = charIndex.indexOf(currentChar);
			if (currentChar == '\n') {
				newLines++;
				xOffset = 0;
				continue;
			}
			if (i > 0 && i % MAX_LINE_LENGTH == 0) {
				newLines++;
				xOffset = 0;
			}
			if (newLines != 0) {
				yOffset = (newLines * 16);
			}
			if (DROPPED_CHARS.indexOf(currentChar) != -1) {
				yOffset += 3;
			}
			if (index == -1) continue;

			int xPos = x + xOffset;
			int yPos = y + yOffset;
			screen.renderString(xPos, yPos, characters[index], color, false);
			xOffset += (DEFAULT_CHAR_SPACING + spacing);
		}
	}
}
