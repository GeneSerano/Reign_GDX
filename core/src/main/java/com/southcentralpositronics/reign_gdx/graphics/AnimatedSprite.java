package com.southcentralpositronics.reign_gdx.graphics;

public class AnimatedSprite extends Sprite {

	private int frame = 0, rate = 5, length = -1, time = 0;
	private Sprite sprite;

	public AnimatedSprite(SpriteSheet sheet, int height, int width, int length) {
		super(sheet, height, width);
		this.length = length;
		sprite      = sheet.getSprites()[0];

		if (length > sheet.getSprites().length) {
			System.err.println("ERROR:::Animation length too long for sprite frames.");
		}
	}

	public void update() {
		time++;
		if (time % rate == 0) {
			if (frame >= length - 1) {
				frame = 0;
			} else {
				frame++;
			}

			sprite = sheet.getSprites()[frame];
		}
//		System.out.println("Sprite: " + sprite + "; Frame: " + frame);
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setFrameRate(int frames) {
		rate = frames;
	}

	public void setFrame(int frame) {
		if (frame > sheet.getSprites().length - 1) {
			System.err.println("Set frame higher than animation count. " + this);
			return;
		}
		sprite = sheet.getSprites()[frame];
	}
}
