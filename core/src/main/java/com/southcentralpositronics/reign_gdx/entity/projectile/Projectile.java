package com.southcentralpositronics.reign_gdx.entity.projectile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.southcentralpositronics.reign_gdx.entity.Entity;
import com.southcentralpositronics.reign_gdx.entity.spawner.ParticleSpawner;
import com.southcentralpositronics.reign_gdx.level.Level;

public abstract class Projectile extends Entity {
    public static final float SHOT_DELAY = 0.125f; // 8 frames at 60fps

    protected Vector2 position;
    protected Vector2 origin;
    protected Vector2 velocity;

    protected float angle;
    protected float speed;
    protected float range;
    protected float damage;

    protected TextureRegion texture;
    protected Entity        firedBy;

    public int collisionOffsetX = -12;
    public int collisionOffsetY = -2;

    public Projectile(float x, float y, float direction) {
        this.position = new Vector2(x, y);
        this.origin   = new Vector2(x, y);
        this.angle    = direction;

        this.velocity = new Vector2((float) Math.cos(direction), (float) Math.sin(direction));
    }

    public void update(float delta) {
        if (removed) return;

        Vector2 nextPos = position.cpy().add(velocity.x * speed, velocity.y * speed);
        boolean hitWall = level.tileCollision(position.x, position.y, 7, velocity.x * speed, velocity.y * speed, -8, -8);

        if (hitWall) {
            remove();
            return;
        }

        position.set(nextPos);

        if (position.dst(origin) > range) {
            remove();
        }
    }

    public void render(Batch batch) {
        if (texture != null && !removed) {
            batch.draw(texture, position.x + collisionOffsetX, position.y + collisionOffsetY);
        }
    }

    public void hitTarget() {
        if (removed) return;
        level.add(new ParticleSpawner(position.x, position.y, angle, 10, 1000, level));
        removed = true;
    }

    @Override
    public void remove() {
        if (removed) return;
        level.add(new ParticleSpawner(position.x, position.y, angle, 10, 250, level));
        removed = true;
    }

    public void init(Level level) {
        this.level = level;
    }

    public Entity getFiredBy() {
        return firedBy;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public TextureRegion getTexture() {
        return texture;
    }
}
