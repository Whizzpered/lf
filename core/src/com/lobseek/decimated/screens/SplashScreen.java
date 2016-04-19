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
package com.lobseek.decimated.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.lobseek.decimated.Main;

/**
 *
 * @author Yew_Mentzaki
 */
public class SplashScreen extends Screen {

    Sprite lobseek;
    float lobseekWidth;
    boolean load = false;
    float lobseekLightness = 2;

    @Override
    public void show() {
        Texture t = new Texture(Gdx.files.internal("lobseek.png"));
        t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        lobseek = new Sprite(t);
        lobseekWidth = lobseek.getWidth();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        B.begin();
        lobseek.setAlpha(Math.min(1, lobseekLightness));
        if (lobseekWidth > width) {
            lobseek.setScale(width / lobseekWidth);
        } else {
            lobseek.setScale(1);
        }
        lobseek.setCenter(width / 2, height / 2);
        lobseek.draw(B);
        B.end();
        if (!load) {
            main.load();
            load = true;
        } else if (lobseekLightness > 0) {
            lobseekLightness -= delta;
            if (lobseekLightness < 0) {
                lobseekLightness = 0;

                Main.main.setScreen(new GameScreen());
            }
        }
    }
}
