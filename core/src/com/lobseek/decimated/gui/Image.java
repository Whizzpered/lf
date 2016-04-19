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
package com.lobseek.decimated.gui;

import com.lobseek.widgets.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.decimated.components.Sprite;
import com.lobseek.utils.ColorFabricator;

/**
 *
 * @author yew_mentzaki
 */
public class Image extends LWidget{

    Sprite image;
    private float lightness;

    public Image(String image) {
        this.image = new Sprite(image);
        this.width = this.image.width;
        this.height = this.image.height;
    }

    @Override
    public void act(float delta) {
        if(isVisible()){
            lightness = Math.min(1, lightness + delta);
        }else{
            lightness = Math.max(0, lightness - delta);
        }
    }

    @Override
    public void draw(Batch b) {
        image.x = x();
        image.y = y();
        image.width = width;
        image.height = height;
        image.setColor(ColorFabricator.neon(lightness));
        image.draw(b);
    }
}
