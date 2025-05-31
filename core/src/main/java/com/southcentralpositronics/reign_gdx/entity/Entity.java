package com.southcentralpositronics.reign_gdx.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.southcentralpositronics.reign_gdx.level.Level;

import java.util.Random;

public class Entity {
    protected int paddingLeft, paddingRight, paddingTop, paddingBottom;
    protected final Random  random  = new Random();
    protected       boolean removed = false;
    protected       int     life;
    protected       int     time    = 0;
    protected       double  x;
    protected       double  y;
    protected       double  nextX;
    protected       double  nextY;
    protected       Sprite  sprite;
    protected       Level   level;
    protected       Type    type;

    public Entity() {
    }

    public Entity(double x, double y, Sprite sprite) {
        this.x      = x;
        this.y      = y;
        this.sprite = sprite;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void update() {
        // Override in subclasses
    }

    public void init(Level level) {
        this.level = level;
    }

    public void render(SpriteBatch batch, int xScroll, int yScroll) {
//        if (sprite == null) return;
//
//        // Calculate screen position relative to camera scroll
//        float renderX = (float) (x - xScroll);
//        float renderY = (float) (y - yScroll);
//        renderX = 0;
//        renderY = 0;
//        sprite.setPosition(renderX, renderY);
//        sprite.draw(batch);
    }

    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Marks the entity as removed and triggers onRemove hook.
     * Subclasses can override onRemove() to add custom removal behavior.
     */
    public void remove() {
        if (!removed) {
            removed = true;
            onRemove();
        }
    }

    /**
     * Hook for subclasses to execute code when entity is removed.
     */
    protected void onRemove() {
        // Default empty implementation
        // e.g., subclasses can spawn particles here
    }

    public boolean isRemoved() {
        return removed;
    }

    public Type getType() {
        return type;
    }

    public Vector2 getPosition() {
        return new Vector2((float) x, (float) y);
    }

    public float distanceTo(Entity other) {
        return this.getPosition().dst(other.getPosition());
    }

    public enum Type {
        ENEMY, PLAYER, PARTICLE, PROJECTILE, SPAWNER
        // Add more types here as needed
    }
}
