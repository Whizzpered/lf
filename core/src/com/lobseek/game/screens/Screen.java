/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lobseek.game.Main;
import java.util.Random;

/**
 *
 * @author aenge
 */
public class Screen implements com.badlogic.gdx.Screen{

    protected static final SpriteBatch B = new SpriteBatch();
    protected static final Random R = Main.R;
    protected Main main = Main.main;
    public float width, height;
    public OrthographicCamera camera = new OrthographicCamera(960, 540);
    
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        camera.setToOrtho(false, width, height);
        B.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() { 
    }

    @Override
    public void dispose() {
    
    }
    
}
