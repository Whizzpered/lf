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
package com.lobseek.game.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.game.Main;

/**
 * @author Yew_Mentzaki
 */
public class Sprite {

    public float x, y, width, height, angle;
    public float originalWidth, originalHeight;
    public final String name;
    private com.badlogic.gdx.graphics.g2d.Sprite sprite;
    boolean loaded, broken;
    public float r = 1, g = 1, b = 1, a = 1;

    /**
     * @param name path of image in "atlas.pack"
     */
    public Sprite(String name) {
        this.name = name;
        load();
    }
    
    public void setScale(float scale){
        width = scale * originalWidth;
        height = scale * originalHeight;
    }
    
    public void setColor(Color color){
        r = color.r;
        g = color.g;
        b = color.b;
        a = color.a;
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
        sprite.setColor(r, g, b, a);
        float a = angle * 57.2957f;
        sprite.rotate(a);
        sprite.draw(batch);
        sprite.rotate(-a);
    }

}
