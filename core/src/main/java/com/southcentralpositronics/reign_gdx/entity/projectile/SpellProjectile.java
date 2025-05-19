package com.southcentralpositronics.reign_gdx.entity.projectile;

import com.southcentralpositronics.reign_gdx.entity.Entity;


public class SpellProjectile extends Projectile {

	public SpellProjectile(double x, double y, double angle, Entity firedBy) {
		super(x, y, angle);
		this.firedBy = firedBy;
		this.sprite  = Sprite.rotateSprite(Sprite.spellSprite, angle);
		range        = 150; //random.nextInt(150, 200);
		damage       = 2000;
		speed        = 5;
		nx           = speed * Math.cos(angle);
		ny           = speed * Math.sin(angle);

	}
}
