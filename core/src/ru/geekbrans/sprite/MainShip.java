//package ru.geekbrans.sprite;////public class Ship {
//}
package ru.geekbrans.sprite;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrans.base.Ship;
import ru.geekbrans.base.Sprite;
import ru.geekbrans.math.Rect;
import ru.geekbrans.pool.BulletPool;
import ru.geekbrans.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final float PADDING = 0.03f;
    private static final float HEIGHT = 0.15f;
    private static final int INVALID_POINTER = -1;
    private static final float RELOAD_INTERVAL = 0.5f;
    private static final int HP = 100;

    private boolean isPressedLeft;
    private boolean isPressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public MainShip(TextureAtlas atlas, ExplosionPool explosionPool, BulletPool bulletPool, Sound bulletSound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.explosionPool = explosionPool;
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletSound = bulletSound;
        this.bulletV = new Vector2(0, 0.5f);
        this.bulletPos = new Vector2();
        v0 = new Vector2(0.5f, 0);
        speed = new Vector2();
        reloadInterval = RELOAD_INTERVAL;
        bulletHeight = 0.01f;
        damage = 1;
        hp = HP;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft()
                        || bullet.getLeft() > getRight()
                        || bullet.getBottom() > pos.y
                        || bullet.getTop() < getBottom()
        );
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
//        counterTime += delta;
//        if (counterTime >= SHOOT_INTERVAL) {
//            shoot();
//            counterTime = 0f;
//        }
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = true;
                moveRight();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = false;
                if (isPressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = false;
                if (isPressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    private void moveRight() {
        speed.set(v0);
    }

    private void moveLeft() {
        speed.set(v0).rotateDeg(180);
    }

    private void stop() {
        speed.setZero();
    }

    public void newGame() {
        this.hp = HP;
        this.pos.x = worldBounds.pos.x;
        this.speed.set(0, 0);
        flushDestroy();
    }
}