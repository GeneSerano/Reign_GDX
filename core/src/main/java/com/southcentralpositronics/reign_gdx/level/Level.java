package com.southcentralpositronics.reign_gdx.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.southcentralpositronics.reign_gdx.Game;
import com.southcentralpositronics.reign_gdx.entity.Entity;
import com.southcentralpositronics.reign_gdx.entity.Particle;
import com.southcentralpositronics.reign_gdx.entity.mob.Mob;
import com.southcentralpositronics.reign_gdx.entity.mob.PlayerMob;
import com.southcentralpositronics.reign_gdx.entity.projectile.Projectile;
import com.southcentralpositronics.reign_gdx.entity.projectile.SpellProjectile;
import com.southcentralpositronics.reign_gdx.events.Event;
import com.southcentralpositronics.reign_gdx.graphics.TileLoader;
import com.southcentralpositronics.reign_gdx.graphics.TileMapBuilder;
import com.southcentralpositronics.reign_gdx.level.tile.Node;
import com.southcentralpositronics.reign_gdx.level.tile.Tile;
import com.southcentralpositronics.reign_gdx.util.Vector2i;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Level {
    private final ArrayList<Entity>     entities    = new ArrayList<>();
    private final ArrayList<Projectile> projectiles = new ArrayList<>();
    private final ArrayList<Particle>   particles   = new ArrayList<>();
    private final ArrayList<PlayerMob>  players     = new ArrayList<>();
    private final ArrayList<Mob>        mobs        = new ArrayList<>();
    private       PlayerMob             player      = getClientPlayer();

    protected int width, height;
    protected Tile[][] tiles;

    private int xScroll, yScroll;

    public Level(String atlasPath, String mapPath) {
        loadLevel(atlasPath, mapPath);
    }

    protected void generateLevel(Pixmap colorMap, Map<Integer, Tile> tileMap) {
        tiles = TileMapBuilder.buildTileGrid(colorMap, tileMap);
    }

    protected void loadLevel(String atlasPath, String mapPath) {
        TextureAtlas tileAtlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        FileHandle   PixelMap  = Gdx.files.internal(mapPath);
        Pixmap       colorMap  = new Pixmap(PixelMap);
        width  = colorMap.getWidth();
        height = colorMap.getHeight();
        Map<Integer, Tile> tileMap = TileLoader.loadTiles(tileAtlas);
        generateLevel(colorMap, tileMap);
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

    public void render(SpriteBatch batch) {
        int tileSize     = 16; // Assuming tiles are 16x16 pixels
        int screenWidth  = Game.WIDTH / Game.SCALE;
        int screenHeight = Game.HEIGHT / Game.SCALE;
        setScroll((int) (player.getX() - (double) screenWidth / 2),
                  (int) (player.getY() - (double) screenHeight / 2));

        int x0 = xScroll / tileSize;
        int x1 = (xScroll + screenWidth + tileSize) / tileSize;
        int y0 = yScroll / tileSize;
        int y1 = (yScroll + screenHeight + tileSize) / tileSize;

        renderTiles(batch, tiles, 0, 0, tileSize);
//        renderTiles(batch, tiles, xScroll, yScroll, tileSize);

        // Render entities
        for (Entity e : entities) e.render(batch, xScroll, yScroll);
        for (Particle p : particles) p.render(batch, xScroll, yScroll);
        for (Projectile p : projectiles) p.render(batch, xScroll, yScroll);
        for (Mob m : mobs) m.render(batch, xScroll, yScroll);
        for (PlayerMob p : players) p.render(batch, xScroll, yScroll);
    }

    public void renderTiles(SpriteBatch batch, Tile[][] tileGrid, int xScroll, int yScroll, int tileSize) {
        width  = tileGrid.length;
        height = tileGrid[0].length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile tile = tileGrid[x][y];
                if (tile == null || tile.getTexture() == null) continue;

                float renderX = x * tileSize - xScroll;
                float renderY = y * tileSize - yScroll;

                batch.draw(tile.getTexture(), renderX, renderY, tileSize, tileSize);
            }
        }
    }


    public Tile getTileAt(int x, int y) {
        if (x < 0 || y < 0 || x >= tiles.length || y >= tiles[0].length) {
            return null; // Or optionally return a default void_tile
        }
        return tiles[x][y];
    }

    public Tile.tileType getTile(int x, int y) {
        return getTileAt(x, y).tile_Type;
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

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public boolean tileCollision(double x, double y) {
        int tileSize = 16; // or your actual tile size

        int tileX = (int) (x / tileSize);
        int tileY = (int) (y / tileSize);

        if (tileX < 0 || tileY < 0 || tileX >= tiles.length || tileY >= tiles[0].length) {
            return true; // Out-of-bounds tiles are treated as solid
        }

        Tile tile = tiles[tileX][tileY];
        return tile != null && tile.isSolid();
    }

    public void setPlayer(PlayerMob player) {
        this.player = player;
    }

    public List<Node> findPath(Vector2i startVec, Vector2i destVec) {
        return List.of();
    }

    // Additional methods like generateMobs, findPath, tileCollision can be implemented similarly
}
