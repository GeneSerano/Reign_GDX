package com.southcentralpositronics.reign_gdx.entity;

import com.southcentralpositronics.reign_gdx.entity.mob.PlayerMob;
import com.southcentralpositronics.reign_gdx.graphics.Screen;
import com.southcentralpositronics.reign_gdx.graphics.Sprite;
import com.southcentralpositronics.reign_gdx.level.Level;

public class Particle extends Entity {
	protected double xx, yy, zz, xa, ya, za;
	private final int    life;
	private       int    time = 0;
	private final Sprite sprite;

	public Particle(double x, double y, double angle, int life, Level level) {
		this.x    = x;
		this.y    = y;
		this.xx   = x;
		this.yy   = y;
		this.life = life + random.nextInt(0, 15);
		this.xa   = random.nextGaussian();
		this.ya   = random.nextGaussian();
//		this.xa = random.nextDouble(-1.0, 1.0);
//		this.ya = random.nextDouble(-1.0, 1.0);
		this.zz = random.nextFloat() + 2.0;

//		if (angle < 0 && this.xa < 0) this.xa *= -1;
//		if (angle < 0 && this.ya < 0) this.ya *= -1;


		sprite = Sprite.particle_normal;
		PlayerMob player = level.getClientPlayer();

	}

	public void update() {
		time++;

		if (time >= Integer.MAX_VALUE - 1) {
			time = 0;
		}
		if (time > life) {
			remove();
		}

		za -= 0.1;
		if (zz <= 0) {
//			zz = 0;
			za *= -0.5;
			xa *= 1;
			ya *= 1;
		}

		double xDelta = xx + xa;
		double yDelta = yy + ya;
		double zDelta = zz + za;
		move(xDelta, yDelta + zDelta);
	}

	private void move(double xDelta, double yDelta) {
//		if (collision(xDelta, yDelta)) {
//			this.xa *= -1;
//			this.ya *= -1;
//			this.za *= -1;
//		}

		if (collision(xa, 0)) {
			this.xa *= -1;
		}
		if (collision(0, ya)) {
			this.ya *= -1;
			this.za *= -1;
		}

		this.xx += xa;
		this.yy += ya;
		this.zz += za;
	}

	public boolean collision(double xb, double yb) {
//		boolean solid = false;
//		int     ix, iy;
//
//		for (int c = 0; c < 4; c++) {
//			double xt = ((xb) - (c % 2 * 16)) / 16;
//			double yt = ((yb) - (c / 2 * 16)) / 16;
//
//			if (c % 2 == 0) {
//				ix = (int) Math.floor(xt);
//			} else {
//				ix = (int) Math.ceil(xt);
//			}
//			if (c / 2 == 0) {
//				iy = (int) Math.floor(yt);
//			} else {
//				iy = (int) Math.ceil(yt);
//			}
//
//			if (level.getTile(ix, iy).solid()) {
//				solid = true;
//			}
//		}
//		return solid;
		return level.tileCollision(xx, yy, 16, xb, yb + za, 0, 2.5);
	}

	public void render(Screen screen) {
		screen.renderSprite((int) xx - 5, (int) (yy - zz) + 5, sprite, true);
	}
}
