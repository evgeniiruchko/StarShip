//package ru.geekbrans.sprite;////public class Ship {
//}
package ru.geekbrans.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrans.base.Sprite;
import ru.geekbrans.math.Rect;
import ru.geekbrans.pool.BulletPool;
import ru.geekbrans.sprite.Bullet;

public class Ship extends Sprite {
    protected Vector2 v0;
    protected Vector2 speed;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected Sound bulletSound;
    protected float bulletHeight;
    protected int damage;
    protected int hp;

    protected float reloadInterval;
    protected float reloadTimer;

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
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, worldBounds, damage, bulletHeight);
        bulletSound.play();
    }
}