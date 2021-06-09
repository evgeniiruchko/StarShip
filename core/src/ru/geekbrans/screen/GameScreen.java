//package ru.geekbrans.screen;////public class GameScreen {
//}
package ru.geekbrans.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.List;

import ru.geekbrans.base.BaseScreen;
import ru.geekbrans.math.Rect;
import ru.geekbrans.pool.BulletPool;
import ru.geekbrans.pool.EnemyPool;
import ru.geekbrans.sprite.Background;
import ru.geekbrans.sprite.Bullet;
import ru.geekbrans.sprite.EnemyShip;
import ru.geekbrans.sprite.MainShip;
import ru.geekbrans.sprite.Star;
import ru.geekbrans.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {
    private static final int STAR_COUNT = 64;

    private MainShip mainShip;
    private EnemyPool enemyPool;
    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private Star[] stars;

    private BulletPool bulletPool;

    private Sound laserSound;
    private Sound bulletSound;
//    private Music music;

    private EnemyEmitter enemyEmitter;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/space.jpg");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyPool = new EnemyPool(worldBounds, bulletPool, bulletSound);
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        mainShip = new MainShip(atlas, bulletPool, laserSound);
        enemyEmitter = new EnemyEmitter(worldBounds, enemyPool, atlas);
//        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
//        music.setLooping(true);
//        music.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollision();
        freeAllDestroyed();
        draw();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        bulletSound.dispose();
        laserSound.dispose();
//        music.dispose();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
    }

    private void draw() {
        ScreenUtils.clear(0.33f, 0.45f, 0.68f, 1);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override

    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    private void collision(EnemyShip enemyShip) {
        float minDist = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
        List<Bullet> bulletList = bulletPool.getActiveObjects();

        if(enemyShip.pos.dst2(mainShip.pos) < Math.pow(minDist, 2)) {
            enemyShip.destroy();
        }

        for(Bullet bullet: bulletList) {
            if (bullet.getOwner() == mainShip && enemyShip.pos.dst2(bullet.pos) < Math.pow(minDist, 2)) {
                enemyShip.destroy();
                bullet.destroy();
            }
        }
    }

    private void checkCollision() {
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        for(EnemyShip enemyShip: enemyShipList) {
            collision(enemyShip);
        }
    }
}