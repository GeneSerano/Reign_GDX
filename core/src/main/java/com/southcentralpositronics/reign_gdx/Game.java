package com.southcentralpositronics.reign_gdx;

import com.southcentralpositronics.reign_gdx.entity.mob.PlayerMob;
import com.southcentralpositronics.reign_gdx.events.Event;
import com.southcentralpositronics.reign_gdx.events.EventListener;
import com.southcentralpositronics.reign_gdx.graphics.Screen;
import com.southcentralpositronics.reign_gdx.graphics.layers.Layer;
import com.southcentralpositronics.reign_gdx.graphics.ui.UIManager;
import com.southcentralpositronics.reign_gdx.input.Keyboard;
import com.southcentralpositronics.reign_gdx.input.Mouse;
import com.southcentralpositronics.reign_gdx.level.Level;
import com.southcentralpositronics.reign_gdx.level.tile.TileCoordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas implements Runnable, EventListener {
	final        JFrame        frame;
	private final        Keyboard      keyboard;
	private final        Level         level;
	private final        PlayerMob     player;
	private final        Screen        screen;
	private              Thread        thread;
	public static final  boolean       DEBUG            = false;
	private static final long          serialVersionUID = 1L;
	private static final int           width            = 480 - 85;
	private static final int           height           = 270;
	public static        int           updateInt        = 0;
	public static        int[]         screenCenter     = new int[2];
	public static        int           scale            = 3;
	public static        String        title            = "Reign";
	public static        int           screenCenterX    = width / 2;
	private static final UIManager     uiManager        = new UIManager();
	private final        BufferedImage image            = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private final        int[]         pixels           = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private       boolean     running    = false;
	private final List<Layer> layerStack = new ArrayList<Layer>();

	public Game() {
		screenCenter[0] = getWindowWidth() / 2;
		screenCenter[1] = getWindowHeight() / 2;

		Dimension size = new Dimension(width * scale + 85 * scale, height * scale);
		setPreferredSize(size);

		screen   = new Screen(width, height);
		frame    = new JFrame();
		keyboard = new Keyboard();
		level    = Level.spawn;
		addLayer(level);
		TileCoordinates playerSpawnPoint = new TileCoordinates(20, 42);
		player = new PlayerMob(playerSpawnPoint.getX(), playerSpawnPoint.getY(), "King Steve", keyboard);
		level.add(player);
		addKeyListener(keyboard);
		Mouse mouseListener = new Mouse(this);
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
	}

	public static int getWindowWidth() {
		return width * scale;
	}

	public static int getWindowHeight() {
		return height * scale;
	}

	public static UIManager getUiManager() {
		return uiManager;
	}

	public void addLayer(Layer layer) {
		layerStack.add(layer);
	}

	public synchronized void start() {
		running = true;

		thread = new Thread(this, "Display");
		thread.start();
	}

	public Level getLevel() {
		return level;
	}

	@Override
	public void run() {

		long         lastTime = System.nanoTime();
		long         timer    = System.currentTimeMillis();
		final double ns       = 1_000_000_000.0 / 60.0;
		double       delta    = 0;
		int          frames   = 0, updates = 0;

		requestFocus();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				update();
				updates++;
				updateInt = updates;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + " | [" + updates + " TPS / " + frames + " FPS] | [ X:" + player.getX() + " Y:" + player.getY() + "]");
				updates = 0;
				frames  = 0;
			}
		}
	}

	private void update() {
		keyboard.update();
		uiManager.update();

		for (Layer layer : layerStack) layer.update();

		if (keyboard.esc) {
			System.exit(0);
			this.stop();
		}
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();

		double xScroll = player.getX() - (double) screen.width / 2;
		double yScroll = player.getY() - (double) screen.height / 2;

		level.setScroll((int) xScroll, (int) yScroll);

		for (Layer layer : layerStack) layer.render(screen);

		System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

		int mouseX      = Mouse.getX();
		int mouseY      = Mouse.getY();
		int playerX     = (int) player.getX();
		int playerY     = (int) player.getY();
		int playerTileX = playerX / 16;
		int playerTileY = playerY / 16;

		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, width * scale, height * scale, null);
		g.setColor(Color.CYAN);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		g.drawRect(Mouse.getX() - 16, Mouse.getY() - 16, 32, 32);
		g.drawString("Mouse:       X: " + mouseX + "   Y: " + mouseY + " B: " + Mouse.getMouseButton(), 32, 32);
		g.drawString("Player:      X: " + playerX + " Y: " + playerY, 32, 48);
		g.drawString("Player Tile: X: " + playerTileX + "   Y: " + playerTileY, 32, 48 + 16);
		uiManager.render(g);

		g.dispose();
		bs.show();

	}

	public synchronized void stop() {
		running = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onEvent(Event event) {
		for (int i = layerStack.size() - 1; i >= 0; i--) {
			layerStack.get(i).onEvent(event);
		}
	}
}
