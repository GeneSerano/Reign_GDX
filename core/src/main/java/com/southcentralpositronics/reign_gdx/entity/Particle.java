package com.southcentralpositronics.reign_gdx.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.southcentralpositronics.reign_gdx.level.Level;

public class Particle extends Entity {
    protected double xx, yy, zz, xa, ya, za;
    private final int    life;
    private       int    time = 0;
    private final Sprite sprite;

    public Particle(double x, double y, double angle, int life, Level level) {
        this.x    = x;
        this.y    = y;
        this.xx   = x;
        this.yy   = y;
        this.life = life + random.nextInt(0, 15);
        this.xa   = random.nextGaussian();
        this.ya   = random.nextGaussian();
        this.zz   = random.nextFloat() + 2.0;

        sprite = new Sprite();
        sprite.setColor(255f, 0f, 0f, 1f);  // Injected texture region
        init(level);
    }

    public void update() {
        time++;
        if (time >= Integer.MAX_VALUE - 1) {
            time = 0;
        }
        if (time > life) {
            remove();
        }

        za -= 0.1;
        if (zz <= 0) {
            za *= -0.5;
            xa *= 1;
            ya *= 1;
        }

        double xDelta = xx + xa;
        double yDelta = yy + ya;
        double zDelta = zz + za;
        move(xDelta, yDelta + zDelta);
    }

    private void move(double xDelta, double yDelta) {
        if (collision(xa, 0)) {
            this.xa *= -1;
        }
        if (collision(0, ya)) {
            this.ya *= -1;
            this.za *= -1;
        }

        this.xx += xa;
        this.yy += ya;
        this.zz += za;
    }

    public boolean collision(double xb, double yb) {
        return level.tileCollision(xx + xb, yy + yb);
    }

    public void render(SpriteBatch batch) {
        batch.draw(sprite, (float) (xx - 5), (float) (yy - zz + 5));
    }
}
