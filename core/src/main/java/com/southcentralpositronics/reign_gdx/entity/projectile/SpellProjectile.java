package com.southcentralpositronics.reign_gdx.entity.projectile;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.southcentralpositronics.reign_gdx.entity.Entity;

public class SpellProjectile extends Projectile {

    public SpellProjectile(double x, double y, double angle, Entity firedBy, TextureAtlas atlas) {
        super(x, y, angle);
        this.firedBy = firedBy;

        // Get the rotated region if needed — or just use the raw region
        texture = atlas.findRegion("fireball");

        this.range  = 150; // Could randomize if needed
        this.damage = 2000;
        this.speed = 5;
        this.nextX = speed * Math.cos(angle);
        this.nextY = speed * Math.sin(angle);
    }
}
