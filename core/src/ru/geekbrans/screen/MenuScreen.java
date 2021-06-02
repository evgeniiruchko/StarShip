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
import ru.geekbrans.sprite.Ship;

public class MenuScreen extends BaseScreen {
      private Ship ship;
//    private Texture shipLeft;
//    private Texture shipRight;
//    private Vector2 pos;
//    private Vector2 touch;
//    private Vector2 speed;
    private Texture backgroundTexture;
    private Background background;

    @Override
    public void show() {
        super.show();
        backgroundTexture = new Texture("space.jpg");
        background = new Background(backgroundTexture);
        ship = new Ship(new Texture("ship.png"));
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
        ship.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ship.update(delta);
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        background.draw(batch);
        ship.draw(batch);

//        if (speed.x == 0) {
//            batch.draw(ship, pos.x, pos.y, 0.5f, 0.8f);
//        } else if (speed.x > 0) {
//            batch.draw(shipRight, pos.x, pos.y, 50, 78);
//        } else {
//            batch.draw(shipLeft, pos.x, pos.y, 50, 78);
//        }
        batch.end();

    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundTexture.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        ship.touchDown(touch, pointer, button);
        return false;
    }
}
