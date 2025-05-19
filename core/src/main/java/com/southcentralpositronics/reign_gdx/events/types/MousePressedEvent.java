package com.southcentralpositronics.reign_gdx.events.types;

import com.southcentralpositronics.reign_gdx.events.Event;

public class MousePressedEvent extends MouseButtonEvent {

	public MousePressedEvent(int button, int x, int y) {
		super(button, x, y, Event.Type.MOUSE_PRESSED);
	}

}
