package com.southcentralpositronics.reign_gdx.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class Keyboard implements InputProcessor {

    private final boolean[] keys = new boolean[256]; // LibGDX keycodes go up to 255
    public        boolean   up, down, left, right, esc;

    public void update() {
        up    = keys[Input.Keys.W] || keys[Input.Keys.UP];
        down  = keys[Input.Keys.S] || keys[Input.Keys.DOWN];
        left  = keys[Input.Keys.A] || keys[Input.Keys.LEFT];
        right = keys[Input.Keys.D] || keys[Input.Keys.RIGHT];
        esc   = keys[Input.Keys.ESCAPE];
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode >= 0 && keycode < keys.length) keys[keycode] = true;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode >= 0 && keycode < keys.length) keys[keycode] = false;
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
