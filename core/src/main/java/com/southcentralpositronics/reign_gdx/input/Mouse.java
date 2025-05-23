package com.southcentralpositronics.reign_gdx.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.southcentralpositronics.reign_gdx.events.EventListener;
import com.southcentralpositronics.reign_gdx.events.types.MouseMovedEvent;
import com.southcentralpositronics.reign_gdx.events.types.MousePressedEvent;
import com.southcentralpositronics.reign_gdx.events.types.MouseReleasedEvent;

public class Mouse implements InputProcessor {

    private static int mouseB = -1;
    private static int x = -1;
    private static int y = -1;

    private final EventListener eventListener;

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
    public boolean mouseMoved(int screenX, int screenY) {
        x = screenX;
        y = screenY;

        MouseMovedEvent event = new MouseMovedEvent(x, y, false);
        eventListener.onEvent(event);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        x = screenX;
        y = screenY;

        MouseMovedEvent event = new MouseMovedEvent(x, y, true);
        eventListener.onEvent(event);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        x = screenX;
        y = screenY;
        mouseB = button;

        MousePressedEvent event = new MousePressedEvent(mouseB, x, y);
        eventListener.onEvent(event);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        x = screenX;
        y = screenY;
        mouseB = Input.Buttons.LEFT; // Default/fallback
        MouseReleasedEvent event = new MouseReleasedEvent(button, x, y);
        eventListener.onEvent(event);
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    // Unused input events
    @Override public boolean keyDown(int keycode) { return false; }
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }

}
