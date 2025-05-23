package com.southcentralpositronics.reign_gdx.entity.mob;

import java.util.List;

public class ChaserMob extends Mob {
    private       double xa    = 0;
    private       double ya    = 0;
    private final double speed = 0.8;

    public ChaserMob(int x, int y, String path) {
        super(path);
        this.x          = x << 4;
        this.y          = y << 4;
        this.animSprite = mobDown; // Ensure mobDown is set after construction
        this.type       = Type.ENEMY;
    }

    private void chasePlayer() {
        xa = 0;
        ya = 0;

        List<PlayerMob> players = level.getPlayersInRadius(this, 800);
        if (!players.isEmpty()) {
            PlayerMob target = players.getFirst();

            if (x < target.getX()) xa += speed;
            if (x > target.getX()) xa -= speed;
            if (y < target.getY()) ya += speed;
            if (y > target.getY()) ya -= speed;
        }

        if (xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else {
            walking = false;
        }
    }

    public void update(float delta) {
        chasePlayer();

        // Determine direction
        if (Math.abs(ya) > Math.abs(xa)) {
            dir = ya < 0 ? Direction.UP : Direction.DOWN;
        } else if (xa != 0) {
            dir = xa < 0 ? Direction.LEFT : Direction.RIGHT;
        }

        // Use Mob’s animation handling
        super.updateAnimation(delta, walking, dir);
    }
}
