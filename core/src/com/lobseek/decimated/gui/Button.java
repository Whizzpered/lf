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

import com.badlogic.gdx.graphics.Color;
import com.lobseek.widgets.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.decimated.components.Sprite;
import com.lobseek.utils.ColorFabricator;

/**
 *
 * @author yew_mentzaki
 */
public class Button extends LWidget {

    String code;
    private float lightness;
    private static Sprite button_left = new Sprite("gui/button_left", true),
            button_center = new Sprite("gui/button_center", true),
            button_right = new Sprite("gui/button_right", true);

    public Button(String code) {
        this.code = code;
        height = 146;
    }

    @Override
    public void act(float delta) {
        if (isVisible()) {
            lightness = Math.min(1, lightness + delta);

        } else {
            lightness = Math.max(0, lightness - delta);
        }
        if (lightness < 0.5) {
            width = (float) Math.pow(lightness * 2, 2) * 447;
        } else {
            width = 447;
        }
    }

    @Override
    public void draw(Batch b) {
        float x = x();
        float y = y();
        Color c = ColorFabricator.neon((float) (Math.max(0, lightness - 0.5) * 2));
        button_center.setColor(c);
        button_center.x = x;
        button_center.y = y;
        button_center.draw(b);
        Font.draw(code, x - Font.width(code) / 2, y - 35, c, b);
        c = ColorFabricator.neon((float) (Math.min(0.5, lightness) * 2));
        button_right.setColor(c);
        button_right.x = x + (width - button_right.width) / 2;
        button_right.y = y;
        button_right.draw(b);
        button_left.setColor(c);
        button_left.x = x - (width - button_left.width) / 2;
        button_left.y = y;
        button_left.draw(b);
    }
}
