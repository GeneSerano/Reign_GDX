package com.southcentralpositronics.reign_gdx.events;

public class EventDispatcher {

	private final Event event;

	public EventDispatcher(Event event) {
		this.event = event;
	}

	public void dispatch(Event.Type type, EventHandler handler) {
		if (event.handled)
			return;

		if (event.getType() == type)
			event.handled = handler.onEvent(event);
	}

}
