package com.southcentralpositronics.reign_gdx.input;

import com.southcentralpositronics.reign_gdx.events.EventListener;
import com.southcentralpositronics.reign_gdx.events.types.MouseMovedEvent;
import com.southcentralpositronics.reign_gdx.events.types.MousePressedEvent;
import com.southcentralpositronics.reign_gdx.events.types.MouseReleasedEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.TimeUnit;

public class Mouse implements MouseListener, MouseMotionListener {
	private static int           mouseB = -1;
	private static int           x      = -1;
	private static int           y      = -1;
	private final  EventListener eventListener;

	public Mouse(EventListener eventListener) {
		this.eventListener = eventListener;
	}

	public static int getX() {
		return x;
	}

	public static int getY() {
		return y;
	}

	public static int getMouseButton() {
		return mouseB;
	}


	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		x = mouseEvent.getX();
		y = mouseEvent.getY();

		MouseMovedEvent event = new MouseMovedEvent(x, y, false);
		eventListener.onEvent(event);
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		mouseB = mouseEvent.getButton();

		MousePressedEvent event = new MousePressedEvent(mouseB, x, y);
		eventListener.onEvent(event);

	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		mouseB = MouseEvent.NOBUTTON;
		MouseReleasedEvent event = new MouseReleasedEvent(mouseB, x, y);
		eventListener.onEvent(event);
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		mouseB = mouseEvent.getButton();
		try {
			TimeUnit.MILLISECONDS.sleep(50);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		mouseB = -1;

	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {
		x = -1;
		y = -1;

	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
		x = mouseEvent.getX();
		y = mouseEvent.getY();

		MouseMovedEvent event = new MouseMovedEvent(x, y, true);
		eventListener.onEvent(event);
	}


}
