/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.decimated.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.decimated.components.Sprite;
import com.lobseek.decimated.components.Touch;
import com.lobseek.utils.ColorFabricator;

/**
 *
 * @author Whizzpered
 */
public class Trigger extends Button {

    boolean value = true;
    public Sprite pimpochka = new Sprite("gui/button_right", true);

    public Trigger(String code) {
        super(code);

    }

    public void done() {
        pimpochka.x = x();
        System.out.println(pimpochka.x + "  " + x());
    }

    @Override
    public void tapUp(Touch t) {
        value = !value;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (value && pimpochka.x > x() - width / 2) {
            pimpochka.x -= 2;
        }
        if (!value && pimpochka.x < x() + width / 2) {
            pimpochka.x += 2;
        }
    }

    @Override
    public void draw(Batch b) {
        super.draw(b);
        float a = (float) (Math.max(0, lightness - 0.5) * 2);
        pimpochka.y = y();
        float x = pimpochka.x;
        pimpochka.x += x() - width;
        Color c = ColorFabricator.neon(a);
        pimpochka.setColor(c);
        pimpochka.draw(b);
        pimpochka.x = x;
    }

}
