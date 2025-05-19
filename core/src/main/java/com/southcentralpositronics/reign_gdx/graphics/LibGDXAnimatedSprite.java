package com.southcentralpositronics.reign_gdx.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class LibGDXAnimatedSprite {

    private final Animation<TextureRegion> animation;
    private float elapsedTime = 0f;
    private boolean looping = true;
    private boolean playing = true;

    public LibGDXAnimatedSprite(Array<TextureRegion> frames, float frameDuration) {
        this.animation = new Animation<>(frameDuration, frames, Animation.PlayMode.LOOP);
    }

    public void update(float delta) {
        if (playing) elapsedTime += delta;
    }

    public TextureRegion getFrame() {
        return animation.getKeyFrame(elapsedTime, looping);
    }

    public void setLooping(boolean loop) {
        this.looping = loop;
    }

    public void stop() {
        playing = false;
    }

    public void start() {
        playing = true;
    }

    public void reset() {
        elapsedTime = 0f;
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(elapsedTime);
    }

    public void setPlayMode(Animation.PlayMode playMode) {
        animation.setPlayMode(playMode);
    }
}
