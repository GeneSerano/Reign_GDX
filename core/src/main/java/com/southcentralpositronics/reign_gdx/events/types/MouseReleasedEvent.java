package com.southcentralpositronics.reign_gdx.events.types;

import com.southcentralpositronics.reign_gdx.events.Event;

public class MouseReleasedEvent extends MouseButtonEvent {

	public MouseReleasedEvent(int button, int x, int y) {
		super(button, x, y, Event.Type.MOUSE_RELEASED);
	}

}
