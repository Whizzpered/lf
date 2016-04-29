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
import com.lobseek.decimated.components.Touch;
import com.lobseek.utils.ColorFabricator;

/**
 *
 * @author yew_mentzaki
 */
public class SpawnBar extends LWidget {

    private float value = 1;
    private float lightness;
    private static Sprite spawn_bar_top = new Sprite("gui/spawn_bar_top", true),
            spawn_bar_center = new Sprite("gui/spawn_bar_center", true),
            spawn_bar_bottom = new Sprite("gui/spawn_bar_bottom", true);

    public SpawnBar() {
        width = 120;
    }

    public float getValue() {
        return value;
    }

    @Override
    public void tapDown(Touch t) {
        float rh = (height - spawn_bar_bottom.height - spawn_bar_top.height);
       t.y -= (spawn_bar_bottom.height);
        setValue(t.y / rh);
    }

    @Override
    public void swipe(Touch t) {
        float rh = (height - spawn_bar_bottom.height - spawn_bar_top.height);
        t.y -= (spawn_bar_bottom.height);
        setValue(t.y / rh);
    }

    public void valueChanged() {
        //PIS'KA PIS'KA PIS'KA
    }

    public void setValue(float value) {
        if (value <= 1 && value >= 0) {
            this.value = value;
            valueChanged();
        }
    }

    @Override
    public void act(float delta) {
        if (isVisible()) {
            lightness = Math.min(1, lightness + delta);

        } else {
            lightness = Math.max(0, lightness - delta);
        }
    }

    @Override
    public void draw(Batch b) {
        if (parent != null) {
            float x = x();
            float y = y();
            Color c = ColorFabricator.neon(lightness);
            float h = parent.height;
            this.height = h;
            float rh = (h - spawn_bar_bottom.height - spawn_bar_top.height - 46)
                    * value * lightness;
            ////////////////////////
            spawn_bar_bottom.x = this.x;
            spawn_bar_bottom.y = spawn_bar_bottom.height / 2;
            spawn_bar_bottom.setColor(c);
            spawn_bar_bottom.draw(b);
            ////////////////////////
            spawn_bar_top.x = this.x;
            spawn_bar_top.y = rh + spawn_bar_bottom.height + spawn_bar_top.height / 2;
            spawn_bar_top.setColor(c);
            spawn_bar_top.draw(b);
            ////////////////////////
            spawn_bar_center.x = this.x;
            spawn_bar_center.y = spawn_bar_bottom.height + rh / 2;
            spawn_bar_center.height = rh;
            spawn_bar_center.setColor(c);
            spawn_bar_center.draw(b);
            ////////////////////////
            String code = ((int) (value * 100)) + "%";
            Font.draw(code, x - Font.width(code) / 2, rh
                    + spawn_bar_bottom.height + spawn_bar_top.height - 20, c, b);
        }
    }
}
