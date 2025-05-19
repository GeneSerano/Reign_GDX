package com.southcentralpositronics.reign_gdx.entity.mob;

import com.southcentralpositronics.reign_gdx.graphics.AnimatedSprite;
import com.southcentralpositronics.reign_gdx.graphics.SpriteSheet;
import com.southcentralpositronics.reign_gdx.level.tile.Node;
import com.southcentralpositronics.reign_gdx.util.Vector2i;

import java.util.List;

public class AStarChaserMob extends Mob {
	private       double     xa    = 0;
	private       double     ya    = 0;
	private final double     speed = 0.5;
	private       List<Node> path  = null;
	private       int        time  = 0;

	public AStarChaserMob(int x, int y) {
		mobUp           = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
		mobDown         = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
		mobLeft         = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
		mobRight        = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);
		this.x          = x << 4;
		this.y          = y << 4;
		this.animSprite = mobDown;
		sprite          = animSprite.getSprite();
		type            = Type.ENEMY;
	}

	private void move() {
		xa = 0;
		ya = 0;

//		int px = (int) level.getPlayer(0).getX();
//		int py = (int) level.getPlayer(0).getY();
		int px = (int) level.getClientPlayer().getX();
		int py = (int) level.getClientPlayer().getY();

		int      svX      = ((int) x) >> 4; // divide by 16 but faster
		int      svY      = ((int) y) >> 4;
		Vector2i startVec = new Vector2i(svX, svY);
		Vector2i destVec  = new Vector2i(px >> 4, py >> 4);

		if (time % 6 == 0) {
			path = level.findPath(startVec, destVec);
		}

		if (path != null && !path.isEmpty()) {
			Vector2i vector = path.getLast().tile;
			if (x < vector.x << 4) xa += speed;
			if (x > vector.x << 4) xa -= speed;
			if (y < vector.y << 4) ya += speed;
			if (y > vector.y << 4) ya -= speed;
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void update() {
		time++;
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

//	public void render(Screen screen) {
//		int offset = 16;
//		sprite = animSprite.getSprite();
//		screen.renderMob((int) x - offset, (int) y - offset, this);
//	}
}
