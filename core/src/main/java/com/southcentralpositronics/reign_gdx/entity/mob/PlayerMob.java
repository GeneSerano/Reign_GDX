package com.southcentralpositronics.reign_gdx.entity.mob;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.southcentralpositronics.reign_gdx.Game;
import com.southcentralpositronics.reign_gdx.entity.projectile.Projectile;
import com.southcentralpositronics.reign_gdx.events.Event;
import com.southcentralpositronics.reign_gdx.events.EventDispatcher;
import com.southcentralpositronics.reign_gdx.events.EventListener;
import com.southcentralpositronics.reign_gdx.events.types.MousePressedEvent;
import com.southcentralpositronics.reign_gdx.events.types.MouseReleasedEvent;
import com.southcentralpositronics.reign_gdx.graphics.LibGDXAnimatedSprite;
import com.southcentralpositronics.reign_gdx.graphics.ui.UIMiniMap;
import com.southcentralpositronics.reign_gdx.input.Keyboard;
import com.southcentralpositronics.reign_gdx.input.Mouse;

import java.awt.event.MouseEvent;
import java.util.List;

public class PlayerMob extends Mob implements EventListener {
    private final Keyboard  input;
    private final String    name;
    private final double    speed    = 2;
    private       boolean   shooting = false;
    private       UIMiniMap miniMap;
    public        long      score    = 0;

    public PlayerMob(String name, Keyboard input) {
        this(Game.screenCenter[0], Game.screenCenter[1], name, input);
    }

    public PlayerMob(int x, int y, String name, Keyboard input) {
        this.x      = x;
        this.y      = y;
        this.input  = input;
        this.name   = name;
        this.health = 1.0;
        this.type   = Type.PLAYER;

        // Load animations from a TextureAtlas
        TextureAtlas atlas = Game.atlas; // Make sure this is initialized before calling PlayerMob
        mobUp    = new LibGDXAnimatedSprite(atlas.findRegions("player_up"), 0.2f);
        mobDown  = new LibGDXAnimatedSprite(atlas.findRegions("player_down"), 0.2f);
        mobLeft  = new LibGDXAnimatedSprite(atlas.findRegions("player_left"), 0.2f);
        mobRight = new LibGDXAnimatedSprite(atlas.findRegions("player_right"), 0.2f);

        animSprite = mobDown; // initial direction
    }

    @Override
    public void update() {
        if (level != null && miniMap != null) miniMap.init(level);

        double xa = 0, ya = 0;
        if (input.up) {
            animSprite = mobUp;
            ya -= speed;
        } else if (input.down) {
            animSprite = mobDown;
            ya += speed;
        }
        if (input.left) {
            animSprite = mobLeft;
            xa -= speed;
        } else if (input.right) {
            animSprite = mobRight;
            xa += speed;
        }

        if (xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
            animSprite.update();
        } else {
            walking = false;
            animSprite.setFrame(0);
        }

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
