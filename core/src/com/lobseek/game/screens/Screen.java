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

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lobseek.game.Main;
import com.lobseek.game.components.Layer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Yew_Mentzaki
 */
public class Screen implements com.badlogic.gdx.Screen, InputProcessor {

    public static final SpriteBatch B = new SpriteBatch();
    protected static final Random R = Main.R;
    protected Main main = Main.main;
    public float width, height;
    public OrthographicCamera camera = new OrthographicCamera(960, 540);
    private List<Layer> layers = new ArrayList<Layer>();

    public Layer add(Layer l) {
        layers.add(l);
        return l;
    }

    public Layer remove(Layer l) {
        layers.remove(l);
        return l;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        camera.setToOrtho(false, width, height);
        B.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (int i = 0; i < layers.size(); i++) {
            layers.get(i).render(delta);
        }
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (int i = layers.size(); i >= 0; i--) {
            if (layers.get(i).touchDown(screenX, screenY, pointer, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (int i = layers.size(); i >= 0; i--) {
            if (layers.get(i).touchUp(screenX, screenY, pointer, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (int i = layers.size(); i >= 0; i--) {
            if (layers.get(i).touchDragged(screenX, screenY, pointer)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
