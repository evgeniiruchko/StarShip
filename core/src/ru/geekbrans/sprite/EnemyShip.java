//package ru.geekbrans.sprite;////public class EnemyShip {
//}
package ru.geekbrans.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrans.base.Ship;
import ru.geekbrans.math.Rect;
import ru.geekbrans.pool.BulletPool;
import ru.geekbrans.pool.ExplosionPool;

public class EnemyShip extends Ship {
    public EnemyShip(Rect worldBounds, ExplosionPool explosionPool, BulletPool bulletPool, Sound bulletSound) {
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
        v0 = new Vector2();
        speed = new Vector2();
        this.bulletV = new Vector2();
        this.bulletPos = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y - getHalfHeight());
        if (getTop() < worldBounds.getTop()) {
            speed.set(v0);
        } else {
            reloadTimer = reloadInterval * 0.8f;
        }
        if (worldBounds.isOutside(this)) {
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval= reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        speed.set(0, -0.3f);
    }
    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft()
                        || bullet.getLeft() > getRight()
                        || bullet.getBottom() > getTop()
                        || bullet.getTop() < pos.y
        );
    }
}