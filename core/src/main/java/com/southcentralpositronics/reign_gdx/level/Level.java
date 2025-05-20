package com.southcentralpositronics.reign_gdx.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.southcentralpositronics.reign_gdx.entity.Entity;
import com.southcentralpositronics.reign_gdx.entity.Particle;
import com.southcentralpositronics.reign_gdx.entity.mob.Mob;
import com.southcentralpositronics.reign_gdx.entity.mob.PlayerMob;
import com.southcentralpositronics.reign_gdx.entity.projectile.Projectile;
import com.southcentralpositronics.reign_gdx.entity.projectile.SpellProjectile;
import com.southcentralpositronics.reign_gdx.events.Event;
import com.southcentralpositronics.reign_gdx.level.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
    private final Random                random      = new Random();
    private final ArrayList<Entity>     entities    = new ArrayList<>();
    private final ArrayList<Projectile> projectiles = new ArrayList<>();
    private final ArrayList<Particle>   particles   = new ArrayList<>();
    private final ArrayList<PlayerMob>  players     = new ArrayList<>();
    private final ArrayList<Mob>        mobs        = new ArrayList<>();

    protected int width, height;
    protected int[] tiles;

    private int xScroll, yScroll;

    public Level(int width, int height) {
        this.width  = width;
        this.height = height;
        tiles       = new int[width * height];
        generateLevel();
    }

    public Level(String path) {
        loadLevel(path);
        generateLevel();
    }

    protected void generateLevel() {
        // Implement level generation logic here
    }

    protected void loadLevel(String path) {
        // Use LibGDX's Pixmap to load the level image
        Pixmap pixmap = new Pixmap(Gdx.files.internal(path));
        this.width  = pixmap.getWidth();
        this.height = pixmap.getHeight();
        tiles       = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x + y * width] = pixmap.getPixel(x, y);
            }
        }
        pixmap.dispose();
    }

    public void update() {
        for (Entity e : entities) e.update();
        for (Projectile p : projectiles) p.update();
        for (Particle p : particles) p.update();
        for (Mob m : mobs) m.update();
        for (PlayerMob p : players) p.update();
        remove();
    }

    private void remove() {
        entities.removeIf(Entity::isRemoved);
        projectiles.removeIf(Entity::isRemoved);
        particles.removeIf(Entity::isRemoved);
        mobs.removeIf(Entity::isRemoved);
        players.removeIf(Entity::isRemoved);
    }

    public void render(SpriteBatch batch, int xScroll, int yScroll, int screenWidth, int screenHeight) {
        int tileSize = 16; // Assuming tiles are 16x16 pixels

        int x0 = xScroll / tileSize;
        int x1 = (xScroll + screenWidth + tileSize) / tileSize;
        int y0 = yScroll / tileSize;
        int y1 = (yScroll + screenHeight + tileSize) / tileSize;

        // Render tiles
        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                Tile tile = getTile(x, y);
                if (tile != null) {
                    tile.render(batch, x * tileSize - xScroll, y * tileSize - yScroll);
                }
            }
        }

        // Render entities
        for (Entity e : entities) e.render(batch, xScroll, yScroll);
        for (Particle p : particles) p.render(batch, xScroll, yScroll);
        for (Projectile p : projectiles) p.render(batch, xScroll, yScroll);
        for (Mob m : mobs) m.render(batch, xScroll, yScroll);
        for (PlayerMob p : players) p.render(batch, xScroll, yScroll);
    }


    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return Tile.VOID;
        int color = tiles[x + y * width];
        return Tile.getTileByColor(color);
    }

    public void add(Entity e) {
        e.init(this);
        if (e instanceof Particle) {
            particles.add((Particle) e);
        } else if (e instanceof SpellProjectile) {
            projectiles.add((Projectile) e);
        } else if (e instanceof PlayerMob) {
            players.add((PlayerMob) e);
        } else if (e instanceof Mob) {
            mobs.add((Mob) e);
        } else {
            entities.add(e);
        }
    }

    public void setScroll(int xScroll, int yScroll) {
        this.xScroll = xScroll;
        this.yScroll = yScroll;
    }

    public PlayerMob getClientPlayer() {
        return !players.isEmpty() ? players.getFirst() : null;
    }

    public List<Entity> getEntitiesInRadius(Entity e, int radius) {
        List<Entity> result = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.equals(e)) continue;
            if (e.getPosition().dst(entity.getPosition()) <= radius) {
                result.add(entity);
            }
        }
        return result;
    }

    public List<PlayerMob> getPlayersInRadius(Entity e, float radius) {
        List<PlayerMob> result = new ArrayList<>();
        for (PlayerMob player : players) {
            if (e.distanceTo(player) <= radius) {
                result.add(player);
            }
        }
        return result;
    }

    public void onEvent(Event event) {
        PlayerMob clientPlayer = getClientPlayer();
        if (clientPlayer != null) {
            clientPlayer.onEvent(event);
        }
    }

    public void clearMobs() {
        mobs.clear();
    }

    // Additional methods like generateMobs, findPath, tileCollision can be implemented similarly
}
