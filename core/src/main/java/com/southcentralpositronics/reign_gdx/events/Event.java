package com.southcentralpositronics.reign_gdx.events;

public class Event {

	boolean handled;
	private final Type type;
	protected Event(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public enum Type {
		MOUSE_PRESSED,
		MOUSE_RELEASED,
		MOUSE_MOVED
	}

}
