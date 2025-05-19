package com.southcentralpositronics.reign_gdx.level;

import com.southcentralpositronics.reign_gdx.entity.Entity;
import com.southcentralpositronics.reign_gdx.entity.Particle;
import com.southcentralpositronics.reign_gdx.entity.mob.AStarChaserMob;
import com.southcentralpositronics.reign_gdx.entity.mob.Mob;
import com.southcentralpositronics.reign_gdx.entity.mob.PlayerMob;
import com.southcentralpositronics.reign_gdx.entity.mob.ShooterMob;
import com.southcentralpositronics.reign_gdx.entity.projectile.Projectile;
import com.southcentralpositronics.reign_gdx.entity.projectile.SpellProjectile;
import com.southcentralpositronics.reign_gdx.events.Event;
import com.southcentralpositronics.reign_gdx.graphics.Screen;
import com.southcentralpositronics.reign_gdx.graphics.layers.Layer;
import com.southcentralpositronics.reign_gdx.level.tile.Node;
import com.southcentralpositronics.reign_gdx.level.tile.Tile;
import com.southcentralpositronics.reign_gdx.util.FileUtils;
import com.southcentralpositronics.reign_gdx.util.Vector2i;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Level extends Layer {
//	public static Level            spawn       = new SpawnLevel("/textures/levels/SpawnLevel_v20.png");
	public static Level            spawn       = new SpawnLevel("/textures/levels/SpawnLevel.png");
	private final Random           random      = new Random();
	public        List<Entity>     entities    = new ArrayList<Entity>();
	public        List<Projectile> projectiles = new ArrayList<Projectile>();
	public        List<Particle>   particles   = new ArrayList<Particle>();
	public        List<PlayerMob>  players     = new ArrayList<PlayerMob>();
	public        List<Mob>        mobs        = new ArrayList<Mob>();
	protected     int              width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	private   int   xScroll;
	private   int   yScroll;


	private final Comparator<Node> nodeComparator = new Comparator<Node>() {
		public int compare(Node o1, Node o2) {
			if (o2.fCost < o1.fCost) return +1;
			if (o2.fCost > o1.fCost) return -1;
			return 0;
		}
	};

	public Level(int width, int height) {
		this.width  = width;
		this.height = height;
		tilesInt    = new int[width * height];
		generateLevel();
	}

	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	protected void generateLevel() {

	}

	public void generateMobs() {
		int chasers = 10;
		for (int i = 0; i < chasers; i++) {
			while (true) {
				int xi = random.nextInt(0, width);
				int yi = random.nextInt(0, height);
//				System.out.println(Game.getWindowWidth() / 16 + " " + Game.getWindowHeight() / 16);
				Tile tile = getTile(xi, yi);
				if (tile == Tile.spawnFloorTile || tile == Tile.spawnGrassTile) {
					tile = getTile(xi + 1, yi);
					if (tile == Tile.spawnFloorTile || tile == Tile.spawnGrassTile) {
						tile = getTile(xi, yi + 1);
						if (tile == Tile.spawnFloorTile || tile == Tile.spawnGrassTile) {
							tile = getTile(xi - 1, yi);
							if (tile == Tile.spawnFloorTile || tile == Tile.spawnGrassTile) {
								tile = getTile(xi, yi - 1);
								if (tile == Tile.spawnFloorTile || tile == Tile.spawnGrassTile) {
									tile = getTile(xi + 1, yi - 1);
									if (tile == Tile.spawnFloorTile || tile == Tile.spawnGrassTile) {
										tile = getTile(xi - 1, yi + 1);
										if (tile == Tile.spawnFloorTile || tile == Tile.spawnGrassTile) {
											tile = getTile(xi - 1, yi - 1);
											if (tile == Tile.spawnFloorTile || tile == Tile.spawnGrassTile) {
												tile = getTile(xi + 1, yi + 1);
												if (tile == Tile.spawnFloorTile || tile == Tile.spawnGrassTile) {
													add(new ShooterMob(xi, yi));
													break;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		for (int i = 0; i < mobs.size(); i++) {
			mobs.get(i).update();
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).update();
		}
		remove();
	}

	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) {
				entities.remove(i);
			}
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) {
				projectiles.remove(i);
			}
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) {
				particles.remove(i);
			}
		}
		for (int i = 0; i < mobs.size(); i++) {
			if (mobs.get(i).isRemoved()) {
				mobs.remove(i);
			}
		}
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) {
				players.remove(i);
			}
		}
	}

//	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
//		boolean solid = false;
//
//		for (int c = 0; c < 4; c++) {
//			int xt = (x - (c % 2 * size) + xOffset) >> 4;
//			int yt = (y - (c / 2 * size) + yOffset) >> 4;
//			solid = getTile(xt, yt).solid();
//		}
//		return solid;
//	}

	public boolean tileCollision(double x, double y, double size, double xDelta, double yDelta, double xOffset, double yOffset) {
		boolean solid = false;

		for (int c = 0; c < 4; c++) {
			double xt = (((x + xDelta) - (c % 2 * size) + xOffset) / 16);
			double yt = (((y + yDelta) - ((double) c / 2 * size) + yOffset) / 16);

			if (xDelta == -1) xt -= 1;
			if (yDelta == 1) yt += 0.5;

			int tileX, tileY;
			if (c % 2 == 0) {
				tileX = (int) Math.floor(xt); // --->
			} else {
				tileX = (int) Math.ceil(xt); // <---
			}
			if (c / 2 == 0) {
				tileY = (int) Math.floor(yt);
			} else {
				tileY = (int) Math.ceil(yt);
			}

			solid = getTile(tileX, tileY).solid();
		}
		return solid;
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
//			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int           w     = width = image.getWidth();
			int           h     = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);

		} catch (IOException e) {
			System.out.println("*****************************************************");
			System.out.println("*****************************************************");
			System.out.println("*****  Exception: Could not load level file!!!  *****");
			System.out.println("*****************************************************");
			System.out.println("*****************************************************");
			e.printStackTrace();
		}
	}

	public Tile getTile(int x, int y) {
		return Tile.voidTile;
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof SpellProjectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof PlayerMob) {
			players.add((PlayerMob) e);
		} else if (e.getType() == Entity.Type.ENEMY) {
			mobs.add((Mob) e);
		} else {
			entities.add(e);
		}
	}

	public void setScroll(int xScroll, int yScroll) {
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}

	public void render(Screen screen) {
		screen.setOffset(xScroll, yScroll);

		// bitwise 4 same as divide by 16 but faster
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;

		int[] miniMapPixels = new int[x1 * y1];
		int   index         = 0;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				Tile tile = getTile(x, y);
				tile.render(x, y, screen);
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		for (int i = 0; i < mobs.size(); i++) {
			mobs.get(i).render(screen);
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}
	}

	public PlayerMob getClientPlayer() {
		return getPlayer(0);
	}

	public PlayerMob getPlayer(int index) {
		return players.get(index);
	}

	public List<PlayerMob> getPlayers() {
		return players;
	}

	public List<Entity> getEntities() {
		return entities;
	}
	public List<Mob> getMobs() {
		return mobs;
	}

	public List<Entity> getEntitiesInRadius(Entity e, int radius) {
		List<Entity> entitiesInRadius = new ArrayList<Entity>();
		int          entityX          = (int) e.getX();
		int          entityY          = (int) e.getY();

		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.equals(e)) continue;

			int    x        = (int) entity.getX();
			int    y        = (int) entity.getY();
			int    distX    = Math.abs(x - entityX);
			int    distY    = Math.abs(y - entityY);
			double distance = Math.sqrt((distX * distX) + distY * distY);

			if (distance <= radius) {
				entitiesInRadius.add(entity);
			}
		}
		return entitiesInRadius;
	}

	public List<PlayerMob> getPlayersInRadius(Entity e, int radius) {
		List<PlayerMob> playersInRadius = new ArrayList<PlayerMob>();
		List<PlayerMob> players         = getPlayers();
		int             entityX         = (int) e.getX();
		int             entityY         = (int) e.getY();

		for (int i = 0; i < players.size(); i++) {
			Entity entity   = players.get(i);
			int    x        = (int) entity.getX();
			int    y        = (int) entity.getY();
			int    distX    = Math.abs(x - entityX);
			int    distY    = Math.abs(y - entityY);
			double distance = Math.sqrt((distX * distX) + distY * distY);

			if (distance <= radius) {
				playersInRadius.add((PlayerMob) entity);
			}
		}
		return playersInRadius;
	}

	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList    = new ArrayList<Node>();
		List<Node> closedList  = new ArrayList<Node>();
		Node       currentNode = new Node(start, null, 0, Vector2i.getDistance(start, goal));

		openList.add(currentNode);

		while (!openList.isEmpty()) {
			Collections.sort(openList, nodeComparator);
			currentNode = openList.getFirst();
			if (currentNode.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (currentNode.parent != null) {
					path.add(currentNode);
					currentNode = currentNode.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(currentNode);
			closedList.add(currentNode);

			for (int i = 0; i < 9; i++) {
				if (i == 4) continue;

				int  x    = currentNode.tile.x;
				int  y    = currentNode.tile.y;
				int  xDir = (i % 3) - 1;
				int  yDir = (i / 3) - 1;
				Tile at   = getTile(x + xDir, y + yDir);

				if (at == null) continue;
				if (at.solid()) continue;
				Vector2i a        = new Vector2i(x + xDir, y + yDir);
				double   distance = Vector2i.getDistance(currentNode.tile, a) == 1 ? 1 : 0.95;
				double   gCost    = currentNode.gCost + distance;
				double   hCost    = Vector2i.getDistance(a, goal);

				Node node = new Node(a, currentNode, gCost, hCost);

				if (vectorInList(closedList, a) && gCost >= node.gCost) continue;
				if (!vectorInList(openList, a) || gCost < node.gCost) openList.add(node);
			}
		}
		closedList.clear();
		return null;
	}

	private boolean vectorInList(List<Node> list, Vector2i vector) {
		for (Node n : list) {
			if (n.tile.equals(vector)) return true;
		}
		return false;
	}

	public void onEvent(Event event) {
		getClientPlayer().onEvent(event);
	}

	public void clearMobs() {
		List<Entity> entities = getEntities();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity instanceof ShooterMob) {
				entity.remove();
			}
		}
	}
}
