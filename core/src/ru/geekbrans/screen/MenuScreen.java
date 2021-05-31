//package ru.geekbrans.screen;////public class MenuScreen {
//}
package ru.geekbrans.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.geekbrans.base.BaseScreen;
import ru.geekbrans.math.Rect;
import ru.geekbrans.sprite.Background;

public class MenuScreen extends BaseScreen {
//    final private float V_LEN = 1.0f;
//    private Texture ship;
//    private Texture shipLeft;
//    private Texture shipRight;
//    private Vector2 pos;
//    private Vector2 touch;
//    private Vector2 speed;
//    private Vector2 tempVector;
    private Texture backgroundTexture;
    private Background background;

    @Override
    public void show() {
        super.show();
        backgroundTexture = new Texture("space.jpg");
        background = new Background(backgroundTexture);
//        ship = new Texture("ship.png");
//        shipLeft = new Texture("shipleft.png");
//        shipRight = new Texture("shipright.png");
//        pos = new Vector2(-0.5f, -0.5f);
//        touch = new Vector2();
//        tempVector = new Vector2();
//        speed = new Vector2(0, 0 );
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        background.draw(batch);

//        if (speed.x == 0) {
//            batch.draw(ship, pos.x, pos.y, 0.5f, 0.8f);
//        } else if (speed.x > 0) {
//            batch.draw(shipRight, pos.x, pos.y, 50, 78);
//        } else {
//            batch.draw(shipLeft, pos.x, pos.y, 50, 78);
//        }
        batch.end();
//        tempVector.set(touch);
//        if (tempVector.sub(pos).len() <= speed.len()) {
//            pos.set(touch);
//            speed.set(0, 0);
//        } else {
//            pos.add(speed);
//        }
    }

    @Override
    public void dispose() {
        super.dispose();
//        ship.dispose();
//        shipLeft.dispose();
//        shipRight.dispose();
        backgroundTexture.dispose();
    }

//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
//        speed.set(touch.cpy().sub(pos)).setLength(V_LEN);
//        return false;
//    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        //pos.set(touch);
        return false;
    }
}
