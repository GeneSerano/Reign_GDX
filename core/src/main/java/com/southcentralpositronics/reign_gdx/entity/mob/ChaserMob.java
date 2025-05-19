package com.southcentralpositronics.reign_gdx.entity.mob;

import com.southcentralpositronics.reign_gdx.graphics.LibGDXAnimatedSprite;


import java.util.List;

public class ChaserMob extends Mob {
	private double xa    = 0;
	private       double ya    = 0;
	private final double speed = 0.8;

	public ChaserMob(int x, int y) {
		mobUp           = new LibGDXAnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
		mobDown         = new LibGDXAnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
		mobLeft         = new LibGDXAnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
		mobRight        = new LibGDXAnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);
		this.x          = x << 4;
		this.y          = y << 4;
		this.animSprite = mobDown;
		sprite          = animSprite.getSprite();
		type            = Type.ENEMY;

	}

	private void move() {
		xa = 0;
		ya = 0;
		List<PlayerMob> players = level.getPlayersInRadius(this, 800);
		if (!players.isEmpty()) {
			PlayerMob player = players.getFirst();

			if (x < player.getX()) xa += speed;
			if (x > player.getX()) xa -= speed;
			if (y < player.getY()) ya += speed;
			if (y > player.getY()) ya -= speed;
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void update() {
		move();

		if (walking) {
			animSprite.update();
		} else {
			animSprite.setFrame(0);
		}

		if (ya < 0) {
			dir        = Direction.UP;
			animSprite = mobUp;
		} else if (ya > 0) {
			dir        = Direction.DOWN;
			animSprite = mobDown;
		}
		if (xa < 0) {
			dir        = Direction.LEFT;
			animSprite = mobLeft;
		} else if (xa > 0) {
			dir        = Direction.RIGHT;
			animSprite = mobRight;
		}
	}
}
