package com.southcentralpositronics.reign_gdx.entity.mob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.southcentralpositronics.reign_gdx.Game;
import com.southcentralpositronics.reign_gdx.entity.Entity;
import com.southcentralpositronics.reign_gdx.entity.projectile.Projectile;
import com.southcentralpositronics.reign_gdx.entity.projectile.SpellProjectile;
import com.southcentralpositronics.reign_gdx.entity.spawner.ParticleSpawner;
import com.southcentralpositronics.reign_gdx.graphics.LibGDXAnimatedSprite;
import com.southcentralpositronics.reign_gdx.graphics.Renderer;
import com.southcentralpositronics.reign_gdx.util.Vector2i;

import java.util.List;

public class Mob extends Entity {
    protected static final int DETECTION_DISTANCE = 100;

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

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("resources/atlas/Mobs.atlas"));

        mobUp    = new LibGDXAnimatedSprite(atlas, "King_Berno_Up", 0.2f, true);
        mobDown  = new LibGDXAnimatedSprite(atlas, "King_Berno_Down", 0.2f, true);
        mobLeft  = new LibGDXAnimatedSprite(atlas, "King_Berno_Left", 0.2f, true);
        mobRight = new LibGDXAnimatedSprite(atlas, "King_Berno_Right", 0.2f, true);

        animSprite = mobDown;
    }

    public void move(double xa, double ya) {
        if (xa != 0 && ya != 0) {
            move(xa, 0);
            move(0, ya);
            return;
        }

        if (xa > 0) dir = Direction.RIGHT;
        else if (xa < 0) dir = Direction.LEFT;

        if (ya > 0) dir = Direction.DOWN;
        else if (ya < 0) dir = Direction.UP;

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
        if (Game.updateInt % Projectile.SHOT_DELAY != 0) return;

        PlayerMob       closestPlayer = null;
        List<PlayerMob> players       = level.getPlayersInRadius(this, DETECTION_DISTANCE);
        double          minDist       = Double.MAX_VALUE;

        for (PlayerMob player : players) {
            double dist = Vector2i.getDistance(new Vector2i((int) x, (int) y), new Vector2i((int) player.getX(), (int) player.getY()));

            if (dist < minDist) {
                minDist       = dist;
                closestPlayer = player;
            }
        }

        if (closestPlayer != null) {
            double dx          = closestPlayer.getX() - x;
            double dy          = closestPlayer.getY() - y;
            double firingAngle = Math.atan2(dy, dx);
            shootAtPos((int) x, (int) y, firingAngle);
        }
    }

    /**
     * Base update function that should be called by all extending mobs.
     */
    public void update(float delta) {
        updateAnimation(delta, walking, dir);
    }

    /**
     * Updates the current animation frame based on movement direction and walking state.
     */
    protected void updateAnimation(float delta, boolean isWalking, Direction direction) {
        if (mobUp == null || mobDown == null || mobLeft == null || mobRight == null) return;

        // Change animation direction
        switch (direction) {
            case UP -> animSprite = mobUp;
            case DOWN -> animSprite = mobDown;
            case LEFT -> animSprite = mobLeft;
            case RIGHT -> animSprite = mobRight;
        }

        if (isWalking) {
            animSprite.update(delta);
        } else {
            animSprite.setFrame(0);
        }
    }

    public void render(Renderer renderer) {
        if (animSprite == null) return;

        TextureRegion frame = animSprite.getCurrentFrame();

        // Adjust x/y with collision offsets to align sprite properly
        float drawX = (float) x + collisionOffsetX;
        float drawY = (float) y + collisionOffsetY;

        renderer.draw(frame, drawX, drawY, frame.getRegionWidth(), frame.getRegionHeight());
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
