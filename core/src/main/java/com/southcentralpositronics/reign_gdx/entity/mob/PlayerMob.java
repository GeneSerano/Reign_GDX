package com.southcentralpositronics.reign_gdx.entity.mob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.southcentralpositronics.reign_gdx.Game;
import com.southcentralpositronics.reign_gdx.entity.projectile.Projectile;
import com.southcentralpositronics.reign_gdx.events.Event;
import com.southcentralpositronics.reign_gdx.events.EventDispatcher;
import com.southcentralpositronics.reign_gdx.events.EventListener;
import com.southcentralpositronics.reign_gdx.events.types.MousePressedEvent;
import com.southcentralpositronics.reign_gdx.events.types.MouseReleasedEvent;
import com.southcentralpositronics.reign_gdx.graphics.LibGDXAnimatedSprite;
import com.southcentralpositronics.reign_gdx.input.Keyboard;
import com.southcentralpositronics.reign_gdx.input.Mouse;

import java.awt.event.MouseEvent;
import java.util.List;

public class PlayerMob extends Mob implements EventListener {
    private final Keyboard keyboard;
    private       String   name;

    private final double  speed    = 2;
    private       boolean shooting = false;
    public        long    score    = 0;

    public PlayerMob(int x, int y, String name, Keyboard keyboard, String path) {
        super(path);
        this.x        = x;
        this.y        = y;
        this.keyboard = keyboard;
        this.name     = name;
        this.health   = 1.0;
        this.type     = Type.PLAYER;
        walking          = false;
        dir              = Direction.DOWN;
        collisionOffsetX = -24;
        collisionOffsetY = -32;

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(path));

        mobUp    = new LibGDXAnimatedSprite(atlas, "King_Cherno_Up", 0.2f, true);
        mobDown  = new LibGDXAnimatedSprite(atlas, "King_Cherno_Down", 0.2f, true);
        mobLeft  = new LibGDXAnimatedSprite(atlas, "King_Cherno_Left", 0.2f, true);
        mobRight = new LibGDXAnimatedSprite(atlas, "King_Cherno_Right", 0.2f, true);
        atlas.dispose();
        animSprite = mobDown;
    }

    @Override
    public void update() {
        double    xa        = 0, ya = 0;
        Direction direction = null;

        if (keyboard.up) {
            ya -= speed;
            direction = Direction.UP;
        } else if (keyboard.down) {
            ya += speed;
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

        updateAnimation(Gdx.graphics.getDeltaTime(), isMoving, direction);
        updateShooting();
        checkForHit();
        clearProjectiles();
    }

    private void updateShooting() {
        if (shooting) {
            boolean fireTest = (Game.updateInt % Projectile.SHOT_DELAY == 0 || level.getProjectiles().isEmpty());
            if (fireTest) {
                double dx    = Mouse.getX() - Game.screenCenter[0];
                double dy    = Mouse.getY() - Game.screenCenter[1];
                double angle = Math.atan2(dy, dx);
                shootAtPos((int) x, (int) y, angle);
            }
        }
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

    private boolean onMousePressed(MousePressedEvent e) {
        if (Mouse.getX() > 1180) return false;
        if (e.getButton() == MouseEvent.BUTTON1) {
            shooting = true;
            return true;
        }
        return false;
    }

    private boolean onMouseReleased(MouseReleasedEvent e) {
        if (Mouse.getX() > 1180) return false;
        if (e.getButton() == MouseEvent.NOBUTTON) {
            shooting = false;
            return true;
        }
        return false;
    }

    @Override
    public void onEvent(Event event) {
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, e -> onMousePressed((MousePressedEvent) e));
        dispatcher.dispatch(Event.Type.MOUSE_RELEASED, e -> onMouseReleased((MouseReleasedEvent) e));
    }

    public void scoredKill() {
        score++;
        health += 0.5;
    }

    public String getName() {
        return name;
    }
}
