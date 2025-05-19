package com.southcentralpositronics.reign_gdx.graphics.ui;

import com.southcentralpositronics.reign_gdx.util.Vector2i;

import java.awt.*;

public class UILabel extends UIComponent {
	public  String text;
	private Font   font;

	public UILabel(Vector2i position, String text) {
		super(position);
		this.font  = new Font("Verdana", Font.PLAIN, 24);
		this.text  = text;
		this.color = Color.WHITE;

		if (text == "") active = false;
	}

	public UILabel(Vector2i position) {
		this(position, "");
	}

	public void update() {
	}

	public UILabel setFont(Font font) {
		this.font = font;
		return this;
	}

	public void render(Graphics g) {
		int gx = position.x + offset.x;
		int gy = position.y + offset.y;

//		g.setColor(Color.BLACK);
//		g.setFont(new Font("Verdana", Font.BOLD, 24));
//		g.drawString(text, gx-2, gy);
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, gx, gy);
	}

	public void setText(String text) {
		this.text = text;
	}
}
