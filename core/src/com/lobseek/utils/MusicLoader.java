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
package com.lobseek.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.lobseek.decimated.Main;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author yew_mentzaki
 */
public class MusicLoader {

    private static FileHandle[] music;
    private static int index = 1;
    private static Music mc, mn;

    public static void load() {
        FileHandle fh = Gdx.files.internal("music");
        FileHandle list[] = fh.list();
        boolean b[] = new boolean[list.length];
        music = new FileHandle[list.length];
        for (int j = 0; j < list.length; j++) {
            int i;
            do {
                i = Main.R.nextInt(list.length);
            } while (b[i]);
            b[i] = true;
            music[j] = list[i];
        }
        list = null;
        b = null;
        fh = null;
        mc = Gdx.audio.newMusic(music[0]);
        mc.play();
        mn = Gdx.audio.newMusic(music[index]);
        Timer t = new Timer("Music timer");
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                play();
            }
        }, 100, 100);
    }

    private static void play() {
        try {
            if (!mc.isPlaying()) {
                mn.play();
                mc.dispose();
                mc = mn;
                index++;
                if (index == music.length) {
                    index = 0;
                }
                mn = Gdx.audio.newMusic(music[index]);
            }
        } catch (Exception e) {
        }
    }
}
