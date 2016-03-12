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
package com.lobseek.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.lobseek.game.screens.GameScreen;
import com.lobseek.game.screens.SplashScreen;
import java.util.Random;

/**
 *
 * @author Yew_Mentzaki
 */
public class Main extends Game {

    public static final String NAME = "LobseekForces";
    public static final Random R = new Random(Long.MAX_VALUE - System.nanoTime());
    public static final AssetManager AM = new AssetManager();
    public static Main main;
    public static int fps;
    public static long nanos;
    public static TextureAtlas atlas;
    private boolean loaded;
    private static long time;
    private static int frames;

    /**
     * Called when application is loading. Handle load of all textures, sounds,
     * fonts and locale files.
     */
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

    /**
     * Called when application was just launched.
     */
    @Override
    public void create() {
        main = this;
        setScreen(new SplashScreen());
    }

    /**
     * Handles render and counts frames per second.
     */
    @Override
    public void render() {
        frames++;
        long t = System.currentTimeMillis();
        long n = System.nanoTime();
        if(t - time > 1000){
            time = t;
            fps = frames;
            frames = 0;
        }
        super.render();
        n = System.nanoTime() - n;
        nanos = (nanos * 10 + n) / 11;
    }
}
