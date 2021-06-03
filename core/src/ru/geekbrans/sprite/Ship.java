//package ru.geekbrans.sprite;////public class Ship {
//}
package ru.geekbrans.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrans.base.Sprite;
import ru.geekbrans.math.Rect;

public class Ship extends Sprite {

    private static final float V_LEN = 0.006f;
    private static final float PADDING = 0.03f;
    private Vector2 speed;
    private Vector2 tempVector;
    private Vector2 touch;
    private static float leftBorder;
    private static float rightBorder;

    public Ship(Texture texture) {
        super(new TextureRegion(texture));
        touch = new Vector2();
        this.speed = new Vector2();
        this.tempVector = new Vector2();
    }

    public Ship(TextureAtlas atlas) {
        //super(atlas.findRegion("main_ship"));
        super(new TextureRegion(atlas.findRegion("main_ship"),
                0, 0,
                atlas.findRegion("main_ship").getRegionWidth() / 2,
                atlas.findRegion("main_ship").getRegionHeight()
        ));
        touch = new Vector2();
        this.speed = new Vector2();
        this.tempVector = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.15f);

        setBottom(worldBounds.getBottom() + PADDING);

        leftBorder = worldBounds.getLeft();
        rightBorder = worldBounds.getRight();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch.x, 0);
        speed.set((touch.cpy().sub(pos)).x, 0).setLength(V_LEN);
        return false;
    }

    @Override
    public void update(float delta) {
        if (touch.len() != 0) {
            tempVector.set(touch);
            //if (tempVector.sub(pos).len() <= V_LEN) {
            if ((this.pos.x <= tempVector.x + V_LEN) && (this.pos.x >= tempVector.x - V_LEN)) {
                pos.set(touch.x, this.pos.y);
                speed.set(0, 0);
            } else {
                pos.add(speed);
            }
        }
    }

    public boolean keyDown(int keycode) {
        System.out.println("key down " + keycode);
        switch (keycode) {
            case Input.Keys.LEFT:
                System.out.println(this.getLeft());
                speed.set(-1, 0).setLength(V_LEN);
                if (this.getLeft() > leftBorder) {
                    pos.add(speed);
                }
                break;
            case Input.Keys.RIGHT:
                System.out.println(this.getLeft());
                speed.set(1, 0).setLength(V_LEN);
                if (this.getLeft() < rightBorder) {
                pos.add(speed);
                }
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }
//        if (speed.x == 0) {
//            batch.draw(ship, pos.x, pos.y, 0.5f, 0.8f);
//        } else if (speed.x > 0) {
//            batch.draw(shipRight, pos.x, pos.y, 50, 78);
//        } else {
//            batch.draw(shipLeft, pos.x, pos.y, 50, 78);
//        }
}