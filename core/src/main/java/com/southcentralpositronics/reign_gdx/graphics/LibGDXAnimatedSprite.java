package com.southcentralpositronics.reign_gdx.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class LibGDXAnimatedSprite {
    private final Animation<TextureRegion> animation;
    private       float                    stateTime = 0f;
    private final boolean                  looping;

    public LibGDXAnimatedSprite(TextureAtlas atlas, String regionName, float frameDuration, boolean looping) {
        Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(regionName);
        if (regions == null || regions.size == 0) {
            throw new IllegalArgumentException("No regions found for: " + regionName);
        }
        this.animation = new Animation<>(frameDuration, regions);
        this.looping   = looping;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
    }

    public TextureRegion getCurrentFrame() {
        return animation.getKeyFrame(stateTime, looping);
    }

    public TextureRegion getFrame(int index) {
        TextureRegion[] frames = animation.getKeyFrames();
        if (index < 0 || index >= frames.length) throw new IndexOutOfBoundsException();
        return frames[index];
    }

    public int getFrameCount() {
        return animation.getKeyFrames().length;
    }

    public float getAnimationDuration() {
        return animation.getAnimationDuration();
    }

    public void reset() {
        stateTime = 0f;
    }

    public void setFrame(int index) {
        if (index < 0 || index >= animation.getKeyFrames().length) {
            throw new IndexOutOfBoundsException("Frame index out of bounds");
        }
        stateTime = index * animation.getFrameDuration();
    }
}

