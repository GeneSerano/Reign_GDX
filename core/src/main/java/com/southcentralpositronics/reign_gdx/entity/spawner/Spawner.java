package com.southcentralpositronics.reign_gdx.entity.spawner;

import com.southcentralpositronics.reign_gdx.entity.Entity;
import com.southcentralpositronics.reign_gdx.level.Level;

import java.util.ArrayList;
import java.util.List;

public abstract class Spawner extends Entity {

    protected final List<Entity> spawnedEntities = new ArrayList<>();
    protected final Type         type;

    protected int     amountToSpawn;
    protected int     spawnedCount = 0;
    protected boolean finished     = false;

    public Spawner(double x, double y, Type type, int amount, Level level) {
        init(level);
        this.x             = x;
        this.y             = y;
        this.type          = type;
        this.amountToSpawn = amount;
    }

    /**
     * Call this every frame to update spawner logic.
     */
    @Override
    public void update() {
        if (finished) return;

        spawnEntities();

        if (spawnedCount >= amountToSpawn) {
            finished = true;
            onFinished();
        }
    }

    /**
     * Spawn entities logic.
     * Subclasses should override this method to control how entities are spawned.
     */
    protected abstract void spawnEntities();

    /**
     * Hook called once spawning is finished.
     * By default, remove the spawner from the level.
     */
    protected void onFinished() {
        remove();
    }

    public List<Entity> getSpawnedEntities() {
        return spawnedEntities;
    }

    public enum Type {
        MOD, PARTICLE
    }
}
