package com.southcentralpositronics.reign_gdx.graphics.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIManager {
	private final List<UIPanel> panels = new ArrayList<UIPanel>();

	public UIManager() {

	}

	public void addPanel(UIPanel panel) {
		panels.add(panel);
	}

	public void update() {
		for (UIPanel panel : panels) {
			panel.update();
		}
	}

	public void render(Graphics g) {
		for (UIPanel panel : panels) {
			panel.render(g);
		}
	}
}
