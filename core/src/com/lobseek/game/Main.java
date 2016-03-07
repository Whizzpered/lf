package com.lobseek.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.lobseek.game.screens.GameScreen;
import com.lobseek.game.screens.SplashScreen;
import java.util.Random;

public class Main extends Game {

    public static final String NAME = "LobseekForces";
    public static final Random R = new Random(Long.MAX_VALUE - System.nanoTime());
    public static final AssetManager AM = new AssetManager();
    public static Main main;
    public static TextureAtlas atlas;
    private boolean loaded;

    public void load() {
        if (loaded) {
            return;
        }
        loaded = true;
        AM.load("atlas.pack", TextureAtlas.class);
        AM.finishLoading();
        atlas = main.AM.get("atlas.pack");
        screen = null;
        setScreen(new GameScreen());
    }

    @Override
    public void create() {
        main = this;
        setScreen(new SplashScreen());
    }

    @Override
    public void render() {
        super.render();
    }
}
