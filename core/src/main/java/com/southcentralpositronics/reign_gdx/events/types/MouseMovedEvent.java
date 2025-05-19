package com.southcentralpositronics.reign_gdx.events.types;

import com.southcentralpositronics.reign_gdx.events.Event;

public class MouseMovedEvent extends Event {

	private final int x;
	private final int y;
	private final boolean dragged;

	public MouseMovedEvent(int x, int y, boolean dragged) {
		super(Event.Type.MOUSE_MOVED);
		this.x       = x;
		this.y       = y;
		this.dragged = dragged;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean getDragged() {
		return dragged;
	}

}
