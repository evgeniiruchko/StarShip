//package ru.geekbrans.sprite;////public class Backgrounf {
//}
package ru.geekbrans.sprite;

import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
import ru.geekbrans.base.Sprite;
import ru.geekbrans.math.Rect;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background extends Sprite {

    public Background(Texture texture) {
        super(new TextureRegion(texture));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(1f);
        this.pos.set(worldBounds.pos);
    }
}