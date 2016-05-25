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

    public boolean value = true;
    private Sprite pimpochka = new Sprite("gui/trigger", true);
    private float pimpochkiPolozhenie = 0;

    public Trigger(String code) {
        super(code);
    }

    public void done() {
        pimpochka.x = x();
    }

    @Override
    public void tapUp(Touch t) {
    }

    @Override
    public void swipe(Touch t) {
        value = t.dx > 0;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (value) {
            pimpochkiPolozhenie = (float) Math.min(Math.PI / 2, pimpochkiPolozhenie + delta * 5);
        } else {
            pimpochkiPolozhenie = (float) Math.max(-Math.PI / 2, pimpochkiPolozhenie - delta * 5);
        }
    }

    @Override
    public void draw(Batch b) {
        super.draw(b);
        float a = (float) (Math.max(0, lightness - 0.5) * 2);
        pimpochka.y = y();
        pimpochka.x = (float) (x() + (width - pimpochka.width - 50) / 2 * Math.sin(pimpochkiPolozhenie));
        Color c = ColorFabricator.neon(a);
        pimpochka.setColor(c);
        pimpochka.angle = (float)(Math.sin(pimpochkiPolozhenie) * Math.PI / 2 - Math.PI / 2);
        pimpochka.draw(b);
    }

}
