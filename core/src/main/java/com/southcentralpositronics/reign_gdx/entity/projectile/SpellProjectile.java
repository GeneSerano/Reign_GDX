package com.southcentralpositronics.reign_gdx.entity.projectile;

import com.southcentralpositronics.reign_gdx.entity.Entity;
import com.southcentralpositronics.reign_gdx.graphics.Assets;

public class SpellProjectile extends Projectile {

    public SpellProjectile(double x, double y, double angle, Entity firedBy) {
        super(x, y, angle);
        this.firedBy = firedBy;

        // Get the rotated region if needed — or just use the raw region
        this.texture = Assets.atlas.findRegion("spell_fireball");

        this.range  = 150; // Could randomize if needed
        this.damage = 2000;
        this.speed  = 5;
        this.nx     = speed * Math.cos(angle);
        this.ny     = speed * Math.sin(angle);
    }
}
