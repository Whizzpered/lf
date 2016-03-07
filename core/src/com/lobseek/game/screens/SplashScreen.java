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
package com.lobseek.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 *
 * @author Yew_Mentzaki
 */
public class SplashScreen extends Screen {

    Sprite lobseek;
    float lobseekWidth;

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
        if(lobseekWidth > width){
            lobseek.setScale(width/lobseekWidth);
        }else{
            lobseek.setScale(1);
        }
        lobseek.setCenter(width / 2, height / 2);
        lobseek.draw(B);
        B.end();
        main.load();
    }
}
