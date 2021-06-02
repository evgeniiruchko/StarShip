//package ru.geekbrans.sprite;////public class PlayButton {
//}
package ru.geekbrans.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrans.base.ScaledButton;
import ru.geekbrans.math.Rect;
import ru.geekbrans.screen.GameScreen;

public class PlayButton extends ScaledButton {

    private static final float HEIGHT = 0.26f;
    private static final float PADDING = 0.03f;

    private final Game game;

    public PlayButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
        setLeft(worldBounds.getLeft() + PADDING);
    }

    @Override
    protected void action() {
        game.setScreen(new GameScreen());
    }
}