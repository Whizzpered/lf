/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.lobseek.decimated.Main;

/**
 *
 * @author Whizzpered
 */
public class Sound {

    String name;
    public float volume = 1f;
    private com.badlogic.gdx.audio.Sound[] sounds;

    Sound(String name, AssetManager am) {
        this.name = name;
        sounds = new com.badlogic.gdx.audio.Sound[4];
        for (int i = 0; i < 3; i++) {
            String nm = ("sounds/" + name + "_" + (char) ('a' + i) + ".ogg");
            sounds[i] = Gdx.audio.newSound(Gdx.files.internal(nm));
            if (sounds[i] == null) {
            } else {
            }
        }
    }

    private Sound(Sound s) {
        name = s.name;
        sounds = s.sounds;
        volume = s.volume;
    }

    public void play(float volume) {
        sounds[Main.R.nextInt(sounds.length)].play(this.volume * volume);
    }

    public void play() {
        play(1);
    }

    public Sound copy() {
        return new Sound(this);
    }

}
