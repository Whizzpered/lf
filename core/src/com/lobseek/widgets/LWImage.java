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
package com.lobseek.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.decimated.components.Sprite;

/**
 *
 * @author yew_mentzaki
 */
public class LWImage extends LWidget{

    Sprite image;

    public LWImage(String image) {
        this.image = new Sprite(image);
        this.width = this.image.width;
        this.height = this.image.height;
    }

    @Override
    public void draw(Batch b) {
        image.x = x();
        image.y = y();
        image.width = width;
        image.height = height;
        image.draw(b);
    }
}
