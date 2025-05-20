package com.southcentralpositronics.reign_gdx.entity.spawner;

import com.southcentralpositronics.reign_gdx.entity.Particle;
import com.southcentralpositronics.reign_gdx.level.Level;

public class ParticleSpawner extends Spawner {

    private final int    life;
    private final double angle;

    public ParticleSpawner(double x, double y, double angle, int life, int amount, Level level) {
        super(x, y, Type.PARTICLE, amount, level);
        this.life  = life;
        this.angle = angle;
    }

    @Override
    protected void spawnEntities() {
        for (int i = 0; i < amountToSpawn; i++) {
            Particle p = new Particle(x, y, angle, life, level);
            level.add(p);
            spawnedEntities.add(p);
        }
        spawnedCount = amountToSpawn;
    }

    @Override
    public void update() {
        if (!finished) {
            spawnEntities();
            finished = true;
            onFinished();
        }
    }
}
