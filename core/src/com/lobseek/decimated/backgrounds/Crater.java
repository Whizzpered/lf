/*
 * This program is open software.
 * You may:
 *  * buy this program with Google Play or App Store.
 *  * read code, change code.
 *  * compile and run code if you bought this program.
 *  * share your modification with people who bought this program.
 * You may not:
 *  * sell this program.
 *  * sell your modification of this program as independent product.
 *  * share your modification with people who have no legal copy of
 *                                                    this program.
 *  * share compiled program with people who have no legal copy of it. 
 */
package com.lobseek.decimated.backgrounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.lobseek.decimated.Main;

/**
 *
 * @author yew_mentzaki
 */
public class Crater extends BackgroundObject {

    static Sprite sprite, sprite2;
    Sprite crater;
    float a;

    public Crater(float x, float y) {
        super(x, y);
        radius = 75;
        if (sprite != null) {
            Sprite s[] = {
                sprite, sprite2
            };
            crater = s[Main.R.nextInt(s.length)];
        }
        a = Main.R.nextFloat() * 360f;
    }

    @Override
    public void render(Batch b) {
        if (sprite == null) {
            sprite = new Sprite(new Texture(Gdx.files.internal("crater.png")));
            sprite2 = new Sprite(new Texture(Gdx.files.internal("crater2.png")));
            Sprite s[] = {
                sprite, sprite2
            };
            for (Sprite c : s) {
                c.setOrigin(c.getWidth() / 2, c.getHeight() / 2);
            }
            crater = s[Main.R.nextInt(s.length)];
        }
        if(crater != null){
        crater.setCenter(x, y);
        crater.rotate(a);
        crater.draw(b);
        crater.rotate(-a);
        }
    }

}
