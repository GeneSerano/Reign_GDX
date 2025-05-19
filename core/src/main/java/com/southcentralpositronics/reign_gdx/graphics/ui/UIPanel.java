package com.southcentralpositronics.reign_gdx.graphics.ui;

import com.southcentralpositronics.reign_gdx.util.Vector2i;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIPanel extends UIComponent {
	private final List<UIComponent> components = new ArrayList<UIComponent>();

	public UIPanel(Vector2i position, Vector2i size) {
		super(position);
		this.position = position;
		this.size     = size;
		color         = Color.GRAY;
	}

	public void update() {
		for (UIComponent component : components) {
			component.setOffset(position);
			component.update();
		}
	}

	public void addComponent(UIComponent component) {
		component.init(this);
		components.add(component);
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(position.x, position.y, size.x, size.y);

		for (UIComponent component : components) {
			component.render(g);
		}
	}
}
