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

    final private float V_LEN = 1.0f;
    private Texture texture;
    private Vector2 position;
    private Vector2 speed;
    private Vector2 tempVector = new Vector2();
    private Vector2 touch = new Vector2(0, 0);

    public Ship(Texture texture) {
        super(new TextureRegion(texture));
        this.texture = texture;
        this.position = new Vector2();
        this.speed = new Vector2(0, 0);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.1f);
        this.pos.set(worldBounds.pos);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        //touch.set(touch.x, Gdx.graphics.getHeight() - touch.y);
        //speed.set(touch.cpy().sub(pos)).setLength(V_LEN);
        this.touch.set(touch);
        speed.set(touch.cpy().sub(this.pos)).setLength(V_LEN);
        return false;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        tempVector.set(this.touch);
        if (tempVector.sub(pos).len() <= speed.len()) {
            pos.set(touch);
            speed.set(0, 0);
        } else {
            pos.add(speed);
        }
        spriteBatch.draw(texture, pos.x, pos.y);
        //super.draw(spriteBatch);
    }
    //    @Override
//    public void draw(SpriteBatch spriteBatch) {
//        spriteBatch.draw(this.texture, position.x, position.y);
//    }
}