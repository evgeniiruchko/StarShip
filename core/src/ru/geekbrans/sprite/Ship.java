//package ru.geekbrans.sprite;////public class Ship {
//}
package ru.geekbrans.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrans.base.Sprite;
import ru.geekbrans.math.Rect;

public class Ship extends Sprite {

    private static final float V_LEN = 0.006f;
    private Vector2 speed;
    private Vector2 tempVector;
    private Vector2 touch;

    public Ship(Texture texture) {
        super(new TextureRegion(texture));
        touch = new Vector2();
        this.speed = new Vector2();
        this.tempVector = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.15f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        speed.set(touch.cpy().sub(pos)).setLength(V_LEN);
        return false;
    }

    @Override
    public void update(float delta) {
        tempVector.set(touch);
        if (tempVector.sub(pos).len() <= V_LEN) {
            pos.set(touch);
            speed.set(0, 0);
        } else {
            pos.add(speed);
        }
    }
//        if (speed.x == 0) {
//            batch.draw(ship, pos.x, pos.y, 0.5f, 0.8f);
//        } else if (speed.x > 0) {
//            batch.draw(shipRight, pos.x, pos.y, 50, 78);
//        } else {
//            batch.draw(shipLeft, pos.x, pos.y, 50, 78);
//        }
}