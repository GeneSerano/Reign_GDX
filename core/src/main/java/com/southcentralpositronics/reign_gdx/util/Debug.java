package com.southcentralpositronics.reign_gdx.util;

import com.southcentralpositronics.reign_gdx.graphics.Screen;

public class Debug {
	private Debug() {

	}

	public static void drawRect(Screen screen, int x, int y, int width, int height, int color, boolean fixed) {
		screen.drawRect(x, y, width, height, color, fixed);
	}
}
