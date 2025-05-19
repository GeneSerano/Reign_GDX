package com.southcentralpositronics.reign_gdx.entity.spawner;

import com.southcentralpositronics.reign_gdx.entity.Particle;
import com.southcentralpositronics.reign_gdx.level.Level;

public class ParticleSpawner extends Spawner {

	public int life;

	public ParticleSpawner(double x, double y, double angle, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;

		for (int i = 0; i < amount; i++) {
			level.add(new Particle(x, y, angle, life, level));
		}
		remove();
	}
}
