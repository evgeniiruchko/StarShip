//package ru.geekbrans.screen;////public class MenuScreen {
//}
package ru.geekbrans.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.geekbrans.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    final private float V_LEN = 1.0f;
    private Texture ship;
    private Texture shipLeft;
    private Texture shipRight;
    private Texture background;
    private Vector2 pos;
    private Vector2 endPos;
    private Vector2 speed;
    private Vector2 tempVector;

    @Override
    public void show() {
        super.show();
        background = new Texture("space.jpg");
        ship = new Texture("ship.png");
        shipLeft = new Texture("shipleft.png");
        shipRight = new Texture("shipright.png");
        pos = new Vector2();
        endPos = new Vector2();
        tempVector = new Vector2();
        speed = new Vector2(0, 0 );
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.draw(background, 0, 0);

        if (speed.x == 0) {
            batch.draw(ship, pos.x, pos.y, 50, 78);
        } else if (speed.x > 0) {
            batch.draw(shipRight, pos.x, pos.y, 50, 78);
        } else {
            batch.draw(shipLeft, pos.x, pos.y, 50, 78);
        }
        batch.end();
        tempVector.set(endPos);
        if (tempVector.sub(pos).len() <= speed.len()) {
            pos.set(endPos);
            speed.set(0, 0);
        } else {
            pos.add(speed);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        ship.dispose();
        shipLeft.dispose();
        shipRight.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        endPos.set(screenX, Gdx.graphics.getHeight() - screenY);
        speed.set(endPos.cpy().sub(pos)).setLength(V_LEN);
        return false;
    }
}
