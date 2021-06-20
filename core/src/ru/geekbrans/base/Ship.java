//package ru.geekbrans.sprite;////public class Ship {
//}
package ru.geekbrans.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrans.base.Sprite;
import ru.geekbrans.math.Rect;
import ru.geekbrans.pool.BulletPool;
import ru.geekbrans.pool.ExplosionPool;
import ru.geekbrans.sprite.Bullet;
import ru.geekbrans.sprite.Explosion;

public class Ship extends Sprite {
    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;

    protected Vector2 v0;
    protected Vector2 speed;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected Sound bulletSound;
    protected float bulletHeight;
    protected int damage;
    protected int hp;

    protected float reloadInterval;
    protected float reloadTimer;

    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(speed, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0;
            shoot();
        }
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }
    public void damage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
        frame = 1;
        damageAnimateTimer = 0f;
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public int getDamage() {
        return damage;
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, worldBounds, damage, bulletHeight);
        bulletSound.play();
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(this.pos, getHeight());
    }

    public int getHp() {
        return hp;
    }

    public Vector2 getV() {
        return speed;
    }
}