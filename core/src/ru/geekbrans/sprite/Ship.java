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

import ru.geekbrans.base.Sprite;
import ru.geekbrans.math.Rect;
import ru.geekbrans.pool.BulletPool;

public class Ship extends Sprite {

    private static final float PADDING = 0.03f;
    private static final float HEIGHT = 0.15f;
    private static final int INVALID_POINTER = -1;
    private static final float SHOOT_INTERVAL = 0.5f;

    private Vector2 v0 = new Vector2(0.5f, 0);
    private Vector2 speed = new Vector2();

    private boolean isPressedLeft;
    private boolean isPressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private Rect worldBounds;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletSpeed;
    private Vector2 bulletPosition;

    private Sound piu;

    private float counterTime = 0f;

    public Ship(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2 ,2);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletSpeed = new Vector2(0, 0.5f);
        this.bulletPosition = new Vector2();
        this.piu = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
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
        pos.mulAdd(speed, delta);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
        counterTime += delta;
        if (counterTime >= SHOOT_INTERVAL) {
            shoot();
            counterTime = 0f;
        }
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
//            case Input.Keys.UP:
//            case Input.Keys.SPACE:
//                shoot();
//                break;
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

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bulletPosition.set(pos.x, pos.y + getHalfHeight());
        bullet.set(this, bulletRegion, bulletPosition, bulletSpeed, worldBounds, 1, 0.01f);
        piu.play();
    }


}