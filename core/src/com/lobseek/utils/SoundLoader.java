/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import java.util.ArrayList;

/**
 *
 * @author Whizzpered
 */
public class SoundLoader {

    private static ArrayList<Sound> sound = new ArrayList<Sound>();

    public static void load(AssetManager am) {
        FileHandle fh = Gdx.files.internal("sounds");
        for (FileHandle f : fh.list()) {
            if (f.name().contains(".ogg")) {
                sound.add(new Sound(f.name().replace(".ogg", ""), am));
            }
        }
    }

    public static Sound getSound(String name) {
        if (name == null) {
            return null;
        }
        for (int i = 0; i < sound.size(); i++) {
            Sound s = sound.get(i);
            if (name.equals(s.name)) {
                return s.copy();
            }
        }
        return null;
    }
}
