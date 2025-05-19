package com.southcentralpositronics.reign_gdx.entity.mob;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.southcentralpositronics.reign_gdx.Game;
import com.southcentralpositronics.reign_gdx.entity.Entity;
import com.southcentralpositronics.reign_gdx.entity.projectile.Projectile;
import com.southcentralpositronics.reign_gdx.entity.projectile.SpellProjectile;
import com.southcentralpositronics.reign_gdx.entity.spawner.ParticleSpawner;
import com.southcentralpositronics.reign_gdx.graphics.LibGDXAnimatedSprite;
import com.southcentralpositronics.reign_gdx.util.Vector2i;

import java.util.List;

public class Mob extends Entity {
    protected static int DETECTION_DISTANCE = 100;

    protected final int collisionOffsetX;
    protected final int collisionOffsetY;
    protected boolean walking = false;

    protected LibGDXAnimatedSprite animSprite;
    protected LibGDXAnimatedSprite mobUp, mobDown, mobLeft, mobRight;
    protected Direction dir = Direction.DOWN;

    protected double health;

    public Mob() {
        collisionOffsetX = -24;
        collisionOffsetY = -32;
    }

    public void move(double xa, double ya) {
        if (xa != 0 && ya != 0) {
            move(xa, 0);
            move(0, ya);
            return;
        }

        if (xa > 0) dir = Direction.RIGHT;
        if (xa < 0) dir = Direction.LEFT;
        if (ya > 0) dir = Direction.DOWN;
        if (ya < 0) dir = Direction.UP;

        for (int x = 0; x < Math.abs(xa); x++) {
            if (!collision(abs(xa), ya)) {
                this.x += abs(xa);
            }
        }

        for (int y = 0; y < Math.abs(ya); y++) {
            if (!collision(xa, abs(ya))) {
                this.y += abs(ya);
            }
        }

        walking = xa != 0 || ya != 0;
    }

    private int abs(double value) {
        return value < 0 ? -1 : 1;
    }

    protected void shootAtPos(int x, int y, double angle) {
        Projectile projectile = new SpellProjectile(x, y - 16, angle, this);
        level.add(projectile);
    }

    protected void shootClosest() {
        boolean fireTest = Game.updateInt % Projectile.SHOT_DELAY == 0;
        if (fireTest) {
            PlayerMob closestPlayer = null;
            List<PlayerMob> players = level.getPlayersInRadius(this, DETECTION_DISTANCE);
            double min = 0;

            for (int i = 0; i < players.size(); i++) {
                PlayerMob player = players.get(i);
                double distance = Vector2i.getDistance(
                    new Vector2i((int) x, (int) y),
                    new Vector2i((int) player.getX(), (int) player.y));

                if (i == 0 || distance < min) {
                    min = distance;
                    closestPlayer = player;
                }
            }

            if (closestPlayer != null) {
                double dx = closestPlayer.getX() - x;
                double dy = closestPlayer.getY() - y;
                double firingAngle = Math.atan2(dy, dx);
                shootAtPos((int) x, (int) y, firingAngle);
            }
        }
    }

    public void update(float delta) {
        // Update animation based on direction and walking state
        if (walking) {
            switch (dir) {
                case UP -> animSprite = mobUp;
                case DOWN -> animSprite = mobDown;
                case LEFT -> animSprite = mobLeft;
                case RIGHT -> animSprite = mobRight;
            }
        }

        if (animSprite != null) {
            animSprite.update(delta);
        }
    }

    public void render(SpriteBatch batch) {
        if (animSprite != null) {
            TextureRegion currentFrame = animSprite.getFrame();
            batch.draw(currentFrame, (float) x + collisionOffsetX, (float) y + collisionOffsetY);
        }
    }

    protected boolean collision(double xa, double ya) {
        return level.tileCollision(x, y, 14, xa, ya, 0, 0);
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    @Override
    public void remove() {
        super.remove();
        new ParticleSpawner(x, y, 0, 10, 100, level);
    }
}
