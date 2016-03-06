/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 *
 * @author aenge
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
