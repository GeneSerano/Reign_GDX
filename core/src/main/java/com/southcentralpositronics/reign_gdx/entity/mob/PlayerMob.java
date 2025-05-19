package com.southcentralpositronics.reign_gdx.entity.mob;

import com.southcentralpositronics.reign_gdx.Game;
import com.southcentralpositronics.reign_gdx.entity.projectile.Projectile;
import com.southcentralpositronics.reign_gdx.events.Event;
import com.southcentralpositronics.reign_gdx.events.EventDispatcher;
import com.southcentralpositronics.reign_gdx.events.EventListener;
import com.southcentralpositronics.reign_gdx.events.types.MousePressedEvent;
import com.southcentralpositronics.reign_gdx.events.types.MouseReleasedEvent;
import com.southcentralpositronics.reign_gdx.graphics.AnimatedSprite;
import com.southcentralpositronics.reign_gdx.graphics.Screen;
import com.southcentralpositronics.reign_gdx.graphics.Sprite;
import com.southcentralpositronics.reign_gdx.graphics.SpriteSheet;
import com.southcentralpositronics.reign_gdx.graphics.ui.*;
import com.southcentralpositronics.reign_gdx.input.Keyboard;
import com.southcentralpositronics.reign_gdx.input.Mouse;
import com.southcentralpositronics.reign_gdx.util.FileUtils;
import com.southcentralpositronics.reign_gdx.util.ImageUtil;
import com.southcentralpositronics.reign_gdx.util.MathUtils;
import com.southcentralpositronics.reign_gdx.util.Vector2i;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PlayerMob extends Mob implements EventListener {
	private final Keyboard      input;
	private       Sprite        player;
	private final UIManager     ui;
	private final UIProgressBar uiHealthBar;
	private final UILabel       scoreLabel;
	private       UIButton      uibutton;
	private final String        name;
	private final double        speed    = 2;
	private       boolean       shooting = false;
	private       Vector2i      position;
	private       Vector2i      size;
	private       UIPanel       panel;
	private       UIMiniMap     miniMap;
	public        long          score    = 0;


	public PlayerMob(String name, Keyboard input) {
		this(Game.screenCenter[0], Game.screenCenter[1], name, input);
	}

	public PlayerMob(int x, int y, String name, Keyboard input) {
		mobUp    = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
		mobDown  = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
		mobLeft  = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
		mobRight = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);


		this.x     = x;
		this.y     = y;
		this.input = input;
		this.name  = name;
		animSprite = mobDown;
		health     = 1.0;
		ui         = Game.getUiManager();
		type       = Type.PLAYER;

		//UI Panel
		position = new Vector2i((479 - 80), 1, Game.scale);
		size     = new Vector2i(80, 168, Game.scale);
		panel    = (UIPanel) new UIPanel(position, size).setColor(0x4f4f4f);
		ui.addPanel(panel);

		// Player Name
		position = new Vector2i(60, 250);
		UILabel label = new UILabel(position, name);
		label.setColor(0xbbbbbb);
		label.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		panel.addComponent(label);

		//Health Bar
		position    = new Vector2i(10, 265);
		size        = new Vector2i(222, 24);
		uiHealthBar = new UIProgressBar(position, size);
		uiHealthBar.setColor(Color.BLACK);
		uiHealthBar.setForegroundColor(new Color(0x870000));
		uiHealthBar.setProgress(health);
		uiHealthBar.setInteractedColor(new Color(0x200000));
		panel.addComponent(uiHealthBar);

		//Health bar Text
		position = new Vector2i(uiHealthBar.getPosition());
		Vector2i shiftBy = new Vector2i(3, 20);
		label = new UILabel(position.offsetFromBy(shiftBy), "HP");
		label.setFont(new Font("Segoe Script", Font.BOLD, 22));
		label.setColor(Color.WHITE);
		panel.addComponent(label);

		//Score Text
		position   = new Vector2i(10, 325);
		scoreLabel = new UILabel(position, "Score: " + score * 100);
		scoreLabel.setFont(new Font("Ariel", Font.PLAIN, 18));
		scoreLabel.setColor(Color.WHITE);
		panel.addComponent(scoreLabel);

		//Button
		position = new Vector2i(10, 350);
		size     = new Vector2i(120, 40);
		Vector2i oset = new Vector2i(10, 25);
		uibutton = new UIButton(position, size, oset, new UIActionListener() {
			@Override
			public void perform() {
				level.generateMobs();
			}
		});
		uibutton.setText("Add Mobs");
		uibutton.label.setFont(new Font("Verdana", Font.PLAIN, 20));
		panel.addComponent(uibutton);

		//Button
		position = new Vector2i(10, 395);
		size     = new Vector2i(120, 40);
		oset     = new Vector2i(5, 25);
		uibutton = new UIButton(position, size, oset, new UIActionListener() {
			@Override
			public void perform() {
				level.clearMobs();
			}
		});
		uibutton.setText("Clear Mobs");
		uibutton.label.setFont(new Font("Verdana", Font.PLAIN, 20));
		panel.addComponent(uibutton);

		//Image Button
		position = new Vector2i(5, 175);
		File          imageFile = null;
		BufferedImage image;
		try {
//			imageFile = new File("resources/textures/home.png");
            imageFile = FileUtils.getInternalPath("core/src/main/resources/textures/home.png").file();
            System.out.print("Trying to load: " + imageFile.toPath());
			if (!imageFile.exists()) {
				System.out.println("Image file NOT FOUND at: " + imageFile.getAbsolutePath());
			} else if (!imageFile.canRead()) {
				System.out.println("No READ perms for file at: " + imageFile.getAbsolutePath());
			}

			image = ImageIO.read(imageFile);
			System.out.println(" --- Load succeeded!");
		} catch (IOException e) {
			System.out.print(" --- Load FAILED!!");
			throw new RuntimeException(e);
		}
		image = ImageUtil.makeColorTransparent(image, new Color(Screen.ALPHA_CHAN));
		UIButton imageButton = new UIButton(position, image, new UIActionListener() {
			@Override
			public void perform() {
				System.exit(0);
			}
		});
		panel.addComponent(imageButton);

		//Mini Map
		position = new Vector2i(60, 3);
		size     = new Vector2i(0, 0);
		miniMap  = new UIMiniMap(position);
		panel.addComponent(miniMap);
	}

	@Override
	public void update() {
		if (level != null) miniMap.init(level);
		if (walking) {
			animSprite.update();
		} else {
			animSprite.setFrame(0);
		}
		double xa = 0, ya = 0;

		if (input.up) {
			animSprite = mobUp;
			ya -= speed;
		} else if (input.down) {
			animSprite = mobDown;
			ya += speed;
		}
		if (input.left) {
			animSprite = mobLeft;
			xa -= speed;
		} else if (input.right) {
			animSprite = mobRight;
			xa += speed;
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		updateShooting();
		checkForHit();
		clearProjectiles();

	}

	private void checkForHit() {
		List<Projectile> projectileList = level.getProjectiles();
		int              clipDist       = 16;

		for (int i = 0; i < projectileList.size(); i++) {
			Projectile projectile  = projectileList.get(i);
			var        xDist       = (x + collisionOffsetX) - (projectile.getX() + projectile.collisionOffsetX) + 8;
			var        yDist       = (y + collisionOffsetY) - (projectile.getY() + projectile.collisionOffsetY) + 16;
			int        playerDistX = (int) Math.abs(xDist);
			int        playerDistY = (int) Math.abs(yDist);
//			System.out.print("X: " + playerDistX);
//			System.out.println(" Y: " + playerDistY);
			if (!(projectile.getFiredBy() instanceof PlayerMob)) {
				if (playerDistX < clipDist && playerDistY < clipDist) {
					projectile.hitTarget();
					health -= 0.01;
					health = MathUtils.min(health, 0.0);
					uiHealthBar.setProgress(health);
//					if (health <= 0.0) System.exit(0);
				}
			}
		}
	}

	private void clearProjectiles() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) {
				level.getProjectiles().remove(i);
				i--;
			}
		}
	}

	public String getName() {
		return name;
	}

	private boolean onMousePressed(MousePressedEvent e) {
		if (Mouse.getX() > 1180) return false;
		else if (e.getButton() == MouseEvent.BUTTON1) {
			shooting = true;
			return true;
		}
		return false;
	}

	private void updateShooting() {
		if (shooting) {
			boolean fireTest = (Game.updateInt % Projectile.SHOT_DELAY == 0 || level.getProjectiles().isEmpty());
			if (fireTest) {
				double dx          = Mouse.getX() - Game.screenCenter[0];
				double dy          = Mouse.getY() - Game.screenCenter[1];
				double firingAngle = Math.atan2(dy, dx);
				shootAtPos((int) x, (int) y, firingAngle);
			}
		}
	}

	private boolean onMouseReleased(MouseReleasedEvent e) {
		if (Mouse.getX() > 1180) return false;
		else if (e.getButton() == MouseEvent.NOBUTTON) {
			shooting = false;
			return true;
		}
		return false;
	}

	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent) e));
	}

	public void scoredKill() {
		score += 1;
		scoreLabel.setText("Score: " + score * 100);
		health += 0.5;
	}
}
