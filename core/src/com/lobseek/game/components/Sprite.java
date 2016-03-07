/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package com.lobseek.game.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.game.Main;

/**
 * @author aenge
 */
public class Sprite {

    public float x, y, width, height, angle;
    public float originalWidth, originalHeight;
    public final String name;
    private com.badlogic.gdx.graphics.g2d.Sprite sprite;
    boolean loaded, broken;

    /**
     * @param name path of image in "atlas.pack"
     */
    public Sprite(String name) {
        this.name = name;
        load();
    }

    /**
     * Loads image if it's exists or marks it as broken one.
     */
    private boolean load() {
        if (!broken && !loaded && Main.atlas != null) {
            if (sprite == null) {
                loaded = true;
                sprite = Main.atlas.createSprite(name);
                if (sprite != null) {
                    originalWidth = sprite.getWidth();
                    originalHeight = sprite.getHeight();

                    if (width == 0 && height == 0) {
                        width = originalWidth;
                        height = originalHeight;
                    }
                } else {
                    broken = true;
                }
            }
        }
        return false;
    }

    /**
     * Draws sprite.
     *
     * @param batch instance of SpriteBatch. Just take it from Screen or Room
     */
    public void draw(Batch batch) {
        load();
        if (broken) {
            return;
        }
        sprite.setSize(width, height);
        sprite.setOrigin(width / 2, height / 2);
        sprite.setCenter(x, y);
        float a = angle * 57.2957f;
        sprite.rotate(a);
        sprite.draw(batch);
        sprite.rotate(-a);
    }

}
