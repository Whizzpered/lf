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
    public float volume = 0f;
    private com.badlogic.gdx.audio.Sound sound;

    Sound(String name, AssetManager am) {
        this.name = name;
        String nm = ("sounds/" + name + ".ogg");
        sound = Gdx.audio.newSound(Gdx.files.internal(nm));
    }

    private Sound(Sound s) {
        name = s.name;
        sound = s.sound;
        volume = s.volume;
    }

    public void play(float volume) {
        try {
            if (this.volume * volume > 0) {
                sound.play(this.volume * volume);
            }
        } catch (Exception e) {
            //fuck off
        }
    }

    public void play() {
        play(1);
    }

    public Sound copy() {
        return new Sound(this);
    }

}
