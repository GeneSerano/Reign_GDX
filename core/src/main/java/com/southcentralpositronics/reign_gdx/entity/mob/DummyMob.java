package com.southcentralpositronics.reign_gdx.entity.mob;

import com.southcentralpositronics.reign_gdx.graphics.LibGDXAnimatedSprite;


public class DummyMob extends Mob {
	private int ya   = random.nextInt(3) - 1;
	private int xa   = random.nextInt(3) - 1;
	private int time = 0;

	public DummyMob(int x, int y) {
		mobUp           = new LibGDXAnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
		mobDown         = new LibGDXAnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
		mobLeft         = new LibGDXAnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
		mobRight        = new LibGDXAnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);
		this.x          = x << 4;
		this.y          = y << 4;
		this.animSprite = mobDown;
		type            = Type.ENEMY;

	}

	public void update() {
		time++;

		if (time % random.nextInt(30, 60) == 0) {
			if (random.nextBoolean()) {
				ya = random.nextInt(3) - 1;
				xa = 0;
			} else {
				ya = 0;
				xa = random.nextInt(3) - 1;
			}
		}

		if (xa != 0 || ya != 0) {
			if (collision(0, ya * 2)) {
				ya *= -1;
			}
			if (collision(xa * 2, 0)) {
				xa *= -1;
			}
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}

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
		}
		if (xa > 0) {
			dir        = Direction.RIGHT;
			animSprite = mobRight;
		}
	}
}
