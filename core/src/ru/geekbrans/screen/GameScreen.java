//package ru.geekbrans.screen;////public class GameScreen {
//}
package ru.geekbrans.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.geekbrans.base.BaseScreen;
import ru.geekbrans.math.Rect;
import ru.geekbrans.pool.BulletPool;
import ru.geekbrans.sprite.Background;
import ru.geekbrans.sprite.Ship;
import ru.geekbrans.sprite.Star;

public class GameScreen extends BaseScreen {
    private static final int STAR_COUNT = 64;

    private Ship heroShip;
    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private Star[] stars;

    private BulletPool bulletPool;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/space.jpg");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bulletPool = new BulletPool();
        heroShip = new Ship(atlas, bulletPool);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        freeAllDestroyed();
        draw();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        heroShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        heroShip.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        heroShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        heroShip.update(delta);
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyed();
    }

    private void draw() {
        ScreenUtils.clear(0.33f, 0.45f, 0.68f, 1);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        heroShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override

    public boolean keyUp(int keycode) {
        heroShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        heroShip.keyDown(keycode);
        return false;
    }




}