package com.southcentralpositronics.reign_gdx.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.southcentralpositronics.reign_gdx.events.EventListener;

public class Mouse extends InputAdapter {
    private final EventListener listener;
    private boolean leftPressed;

    public Mouse(EventListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            leftPressed = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            leftPressed = false;
            return true;
        }
        return false;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public static int getX() {
        return Gdx.input.getX();
    }

    public static int getY() {
        return Gdx.input.getY();
    }
}
