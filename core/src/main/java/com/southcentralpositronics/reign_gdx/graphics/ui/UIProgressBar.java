package com.southcentralpositronics.reign_gdx.graphics.ui;

import com.southcentralpositronics.reign_gdx.util.MathUtils;
import com.southcentralpositronics.reign_gdx.util.Vector2i;

import java.awt.*;

public class UIProgressBar extends UIComponent {
	private double progress;

	public UIProgressBar(Vector2i position, Vector2i size) {
		super(position);
		this.size     = size;
		this.position = position;
	}

	public void update(){
		this.progress = MathUtils.clamp(progress, 0, 100);
	}

	public void render(Graphics g) {
		int healthBarHeight = size.y;
		int posX;
		int posY;

		g.setColor(color);
		posX = position.x + offset.x - 2;
		posY = position.y + offset.y - 2;
		g.fillRect(posX, posY, size.x + 4, healthBarHeight + 4);

		g.setColor(interactedColor);
		posX = position.x + offset.x;
		posY = position.y + offset.y;
		g.fillRect(posX, posY, size.x, healthBarHeight);

		g.setColor(foregroundColor);
		posX = position.x + offset.x;
		posY = position.y + offset.y;
		int healthBarPercentage = (int) (size.x * progress);
		g.fillRect(posX, posY, healthBarPercentage, healthBarHeight);
	}

	public double getProgress() {
		return progress;
	}



	public void setProgress(double progress) {
		this.progress = MathUtils.clamp(progress, 0.0, 1.0);
	}

}
