package com.southcentralpositronics.reign_gdx.graphics.ui;

import com.southcentralpositronics.reign_gdx.util.Vector2i;

import java.awt.*;

public class UIComponent {
	protected Vector2i position, offset, size;
	protected Color   foregroundColor = Color.DARK_GRAY;
	protected Color   interactedColor = Color.LIGHT_GRAY;
	protected Color   color           = foregroundColor;
	protected boolean active          = true;
	protected UIPanel panel;

	public UIComponent(Vector2i position) {
		this.position = position;
		offset        = new Vector2i();
	}

	public UIComponent(Vector2i position, Vector2i size) {
		this.position = position;
		this.size     = size;
		offset        = new Vector2i();
	}

	void setOffset(Vector2i offset) {
		this.offset = offset;
	}

	public Vector2i getPosition() {
		return position;
	}

	public UIComponent setColor(int color) {
		this.color = new Color(color);
		return this;
	}

	public UIComponent setColor(Color color) {
		this.color = color;
		return this;
	}

	public void update() {
	}

	public void render(Graphics g) {

	}

	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public void setInteractedColor(Color interactedColor) {
		this.interactedColor = interactedColor;
	}

	protected void init(UIPanel uiPanel) {
		this.panel = panel;
	}

	public Vector2i getAbsPos(Vector2i vector2i) {
		Vector2i pos = position.offsetFromBy(offset);
		return pos;
	}
}
