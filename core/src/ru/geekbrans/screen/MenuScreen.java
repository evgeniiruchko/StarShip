//package ru.geekbrans.screen;////public class MenuScreen {
//}
package ru.geekbrans.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.geekbrans.base.BaseScreen;
import ru.geekbrans.math.Rect;
import ru.geekbrans.sprite.Background;
import ru.geekbrans.sprite.ExitButton;
import ru.geekbrans.sprite.PlayButton;
import ru.geekbrans.sprite.Ship;
import ru.geekbrans.sprite.Star;

public class MenuScreen extends BaseScreen {
    private static final int STARS_COUNT = 256;
    private final Game game;
    private Texture backgroundTexture;
    private Background background;
    private TextureAtlas atlas;
    private Star[] stars;
    private ExitButton exitButton;
    private PlayButton playButton;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        backgroundTexture = new Texture("textures/space.jpg");
        background = new Background(backgroundTexture);
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        stars = new Star[STARS_COUNT];
        for (int i = 0; i < STARS_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        exitButton = new ExitButton(atlas);
        playButton = new PlayButton(atlas, game);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        exitButton.resize(worldBounds);
        playButton.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundTexture.dispose();
        atlas.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        exitButton.touchDown(touch, pointer, button);
        playButton.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        exitButton.touchUp(touch, pointer, button);
        playButton.touchUp(touch, pointer, button);
        return false;
    }

    private void update (float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
    }

    private void draw() {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        playButton.draw(batch);
        exitButton.draw(batch);
        batch.end();
    }
}
