package com.southcentralpositronics.reign_gdx.graphics.ui;

import com.southcentralpositronics.reign_gdx.input.Mouse;
import com.southcentralpositronics.reign_gdx.util.Vector2i;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UIButton extends UIComponent {
	public  UILabel          label;
	private       Image            image;
	private final UIButtonListener buttonListener;
	private final UIActionListener actionListener;
	//	private int              posX, posY;
	private       Rectangle        rect;
	private boolean          inside        = false;
	private boolean          pressed       = false;
	private boolean          ignorePressed = false;
	private boolean          ignoreAction  = false;

	public UIButton(Vector2i position, Vector2i size, Vector2i oset, UIActionListener actionListener) {
		super(position, size);
		this.actionListener = actionListener;
		buttonListener      = new UIButtonListener();
		Vector2i pos = position.offsetFromBy(oset);

		label       = new UILabel(pos, "Hello");
		label.color = Color.WHITE;
		color       = foregroundColor;
	}

	public UIButton(Vector2i position, BufferedImage image, Vector2i oset, UIActionListener actionListener) {
		super(position, new Vector2i(image.getWidth(), image.getHeight()));
		Vector2i pos = position.offsetFromBy(oset);
		this.position = pos;
		setImage(image);
		this.actionListener = actionListener;
		buttonListener      = new UIButtonListener();
		color               = foregroundColor;
	}

	public UIButton(Vector2i position, BufferedImage image, UIActionListener actionListener) {
		this(position, image, new Vector2i(5, 50), actionListener);
	}

	public UIButton(Vector2i position, Vector2i size, UIActionListener actionListener) {
		this(position, size, new Vector2i(5, 25), actionListener);
	}

	public void init() {

	}

	public void init(UIPanel panel) {
		super.init(panel);
		if (label != null) panel.addComponent(label);
	}

	public void setImage() {
		this.image = null;
	}

	public void setImage(Image image) {
		if (image != null) this.image = image;
		else setImage();
	}

	public void setText() {
		label.text = "";
		active     = false;
	}

	public void setText(String text) {
		if (text != "" || text != null) label.text = text;
		else setText();
	}

	public void ignoreNextPress() {
		ignoreAction = true;
	}

	public void update() {
		Vector2i abs  = new Vector2i(getAbsPos(position));
		rect = new Rectangle(abs.x, abs.y, size.x, size.y);

		boolean mouseInRect      = rect.contains(Mouse.getX(), Mouse.getY());
		int     mouseButton      = Mouse.getMouseButton();
		boolean mouseLeftPressed = (mouseButton == MouseEvent.BUTTON1);


		if (mouseInRect) {
			if (!inside) {
				ignorePressed = mouseLeftPressed;
				buttonListener.mouseEntered(this);
			}
			inside = true;

			if (!pressed && !ignorePressed && mouseLeftPressed) {
				buttonListener.mouseButtonPressed(this);
				pressed = true;
			} else if (!mouseLeftPressed || Mouse.getMouseButton() == MouseEvent.NOBUTTON) {
				if (pressed) {
					buttonListener.mouseButtonReleased(this);
					if (!ignoreAction) actionListener.perform();
					else ignoreAction = false;
					pressed = false;
				}
				ignorePressed = false;
			}

		} else {
			if (inside) {
				buttonListener.mouseExited(this);
				pressed = false;
			}
			inside = true;
		}
	}

	public void render(Graphics g) {
		int posX = position.x + offset.x;
		int posY = position.y + offset.y;

		g.setColor(color);
		g.fillRect(posX, posY, size.x, size.y);

		if (image != null) {
			g.drawImage(image, posX, posY, null);
		} else if (label != null) label.render(g);
	}
}
