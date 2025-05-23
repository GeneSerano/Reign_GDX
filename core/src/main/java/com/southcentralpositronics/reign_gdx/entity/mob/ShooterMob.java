package com.southcentralpositronics.reign_gdx.entity.mob;

import com.southcentralpositronics.reign_gdx.entity.projectile.Projectile;

import java.util.List;

public class ShooterMob extends Mob {
    private int   xa                   = random.nextInt(3) - 1;
    private int   ya                   = random.nextInt(3) - 1;
    private float directionChangeTimer = 0;
    private float shootCooldown        = 0;

    public ShooterMob(int x, int y, String path) {
        super(path);
        this.x          = x << 4;
        this.y          = y << 4;
        this.animSprite = mobDown; // Ensure animations are injected externally
        this.type       = Type.ENEMY;
        this.health     = 1.0;
    }

    @Override
    public void update(float delta) {
        directionChangeTimer += delta;
        shootCooldown += delta;

        shootClosest();
        checkForHit();

        // Update direction every 0.5–1.5 seconds
        if (directionChangeTimer >= random.nextFloat(0.5f, 1.5f)) {
            directionChangeTimer = 0;
            if (random.nextBoolean()) {
                ya = random.nextInt(3) - 1;
                xa = 0;
            } else {
                xa = random.nextInt(3) - 1;
                ya = 0;
            }
        }

        if (xa != 0 || ya != 0) {
            if (collision(0, ya * 2)) ya *= -1;
            if (collision(xa * 2, 0)) xa *= -1;
            move(xa, ya);
            walking = true;
        } else {
            walking = false;
        }

        // Determine facing direction
        if (Math.abs(ya) > Math.abs(xa)) {
            dir = ya < 0 ? Direction.UP : Direction.DOWN;
        } else if (xa != 0) {
            dir = xa < 0 ? Direction.LEFT : Direction.RIGHT;
        }

        super.updateAnimation(delta, walking, dir);
    }

//    private void shootClosest() {
//        if (shootCooldown < 1.5f) return;
//
//        List<PlayerMob> players = level.getPlayersInRadius(this, 300);
//        if (!players.isEmpty()) {
//            PlayerMob target = players.getFirst();
//            double dx = target.getX() - this.x;
//            double dy = target.getY() - this.y;
//            double angle = Math.atan2(dy, dx);
//            shoot(x + 8, y + 8, angle); // Fire from center
//            shootCooldown = 0;
//        }
//    }

    private void checkForHit() {
        List<Projectile> projectiles = level.getProjectiles();
        int              hitRange    = 16;

        for (Projectile projectile : projectiles) {
            if (!(projectile.getFiredBy() instanceof PlayerMob)) continue;

            double dx = (x + collisionOffsetX) - (projectile.getX() + projectile.collisionOffsetX) + 8;
            double dy = (y + collisionOffsetY) - (projectile.getY() + projectile.collisionOffsetY) + 16;

            if (Math.abs(dx) < hitRange && Math.abs(dy) < hitRange) {
                projectile.hitTarget();
                health -= 0.1;
                if (health <= 0) {
                    remove();
                    ((PlayerMob) projectile.getFiredBy()).scoredKill();
                }
            }
        }
    }
}
