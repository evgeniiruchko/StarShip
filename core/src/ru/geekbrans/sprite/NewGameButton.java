//package ru.geekbrans.sprite;////public class NewGameButton {
//}
package ru.geekbrans.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrans.base.ScaledButton;
import ru.geekbrans.math.Rect;
import ru.geekbrans.screen.GameScreen;
import ru.geekbrans.screen.MenuScreen;

public class NewGameButton extends ScaledButton {
    private static final float HEIGHT = 0.09f;
    private static final float PADDING = 0.03f;
    private GameScreen gameScreen;

    public NewGameButton(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setTop(worldBounds.pos.y - 0.4f);
    }

    @Override
    protected void action() {
        gameScreen.newGame();
    }
}