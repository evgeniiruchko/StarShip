package ru.geekbrans.pool;

import ru.geekbrans.sprite.Bullet;
import ru.geekbrans.base.SpritesPool;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
