package com.southcentralpositronics.reign_gdx.entity.mob;

import com.southcentralpositronics.reign_gdx.level.tile.Node;
import com.southcentralpositronics.reign_gdx.util.Vector2i;

import java.util.List;

public class AStarChaserMob extends Mob {
    private       double     xa        = 0;
    private       double     ya        = 0;
    private final double     speed     = 0.5;
    private       List<Node> path      = null;
    private       float      pathTimer = 0f; // in seconds

    public AStarChaserMob(int x, int y, String path) {
        // These should be set by the caller after loading from TextureAtlas
        this.x          = x << 4;
        this.y          = y << 4;
        this.animSprite = mobDown;
        this.type       = Type.ENEMY;
    }

    private void calculatePath() {
        int px = (int) level.getClientPlayer().getX();
        int py = (int) level.getClientPlayer().getY();

        Vector2i startVec = new Vector2i(((int) x) >> 4, ((int) y) >> 4);
        Vector2i destVec  = new Vector2i(px >> 4, py >> 4);

        this.path = level.findPath(startVec, destVec);
    }

    private void moveAlongPath() {
        xa = 0;
        ya = 0;

        if (path != null && !path.isEmpty()) {
            Vector2i target = path.getLast().tile;

            double targetX = target.x << 4;
            double targetY = target.y << 4;

            if (x < targetX) xa += speed;
            if (x > targetX) xa -= speed;
            if (y < targetY) ya += speed;
            if (y > targetY) ya -= speed;
        }

        if (xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else {
            walking = false;
        }
    }

    public void update(float delta) {
        pathTimer += delta;

        // Recalculate path every ~0.1 seconds (6 times per second)
        if (pathTimer >= 0.1f) {
            calculatePath();
            pathTimer = 0f;
        }

        moveAlongPath();

        // Set direction based on movement
        if (Math.abs(ya) > Math.abs(xa)) {
            direction = ya < 0 ? Direction.UP : Direction.DOWN;
        } else if (xa != 0) {
            direction = xa < 0 ? Direction.LEFT : Direction.RIGHT;
        }

        // Call Mob's animation update
        super.updateAnimation(delta, walking, direction);
    }
}
