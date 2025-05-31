package com.southcentralpositronics.reign_gdx.entity.mob;

import com.badlogic.gdx.Gdx;
import com.southcentralpositronics.reign_gdx.Game;
import com.southcentralpositronics.reign_gdx.entity.projectile.Projectile;
import com.southcentralpositronics.reign_gdx.graphics.AnimatedSprite;
import com.southcentralpositronics.reign_gdx.input.Keyboard;
import com.southcentralpositronics.reign_gdx.input.Mouse;

import java.util.List;

public class PlayerMob extends Mob {
    private Keyboard keyboard;
    private Mouse    mouse;
    private String   name;

    private final double  speed    = 2;
    private       boolean shooting = false;
    public        long    score    = 0;
    protected     int     height;
    protected     int     width;

    public PlayerMob(Game game, int x, int y, String name) {
        this.x           = x;
        this.y           = y;
        keyboard         = game.getKeyboard();
        mouse            = game.getMouse();
        this.name        = name;
        this.health      = 1.0;
        this.type        = Type.PLAYER;
        walking          = false;
        direction        = Direction.DOWN;
        collisionOffsetX = -24;
        collisionOffsetY = -32;
        paddingLeft      = 8;
        paddingRight     = 8;
        paddingTop       = 24;
        paddingBottom    = 0;

        atlas      = game.getMobAtlas();
        mobUp      = new AnimatedSprite(atlas, "King_Cherno_Up", 0.2f, true);
        mobDown    = new AnimatedSprite(atlas, "King_Cherno_Down", 0.2f, true);
        mobLeft    = new AnimatedSprite(atlas, "King_Cherno_Left", 0.2f, true);
        mobRight   = new AnimatedSprite(atlas, "King_Cherno_Right", 0.2f, true);
        animSprite = mobDown;

        height = animSprite.getCurrentFrame().getRegionHeight();
        width  = animSprite.getCurrentFrame().getRegionWidth();

        viewport = game.getViewport();
    }


    @Override
    public void update() {
        double xa = 0, ya = 0;

        if (keyboard.up) {
            ya += speed;
            direction = Direction.UP;
        } else if (keyboard.down) {
            ya -= speed;
            direction = Direction.DOWN;
        }

        if (keyboard.left) {
            xa -= speed;
            direction = Direction.LEFT;
        } else if (keyboard.right) {
            xa += speed;
            direction = Direction.RIGHT;
        }

        boolean isMoving = xa != 0 || ya != 0;
        if (isMoving) {
            move(xa, ya);
        }
        if (shootCooldown > 0f) {
            shootCooldown -= Gdx.graphics.getDeltaTime();
        }
        updateAnimation(Gdx.graphics.getDeltaTime(), isMoving, direction);
        if (mouse.isLeftPressed()) {
            firePlayerSpell();  // your existing method
        }

//        checkForHit();
        clearProjectiles();
    }

    private void checkForHit() {
        List<Projectile> projectiles = level.getProjectiles();
        for (Projectile p : projectiles) {
            if (!(p.getFiredBy() instanceof PlayerMob)) {
                int dx = (int) ((x + collisionOffsetX) - (p.getX() + p.collisionOffsetX) + 8);
                int dy = (int) ((y + collisionOffsetY) - (p.getY() + p.collisionOffsetY) + 16);
                if (Math.abs(dx) < 16 && Math.abs(dy) < 16) {
                    p.hitTarget();
                }
            }
        }
    }

    private void clearProjectiles() {
        level.getProjectiles().removeIf(Projectile::isRemoved);
    }

    public void scoredKill() {
        score++;
        health += 0.5;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
