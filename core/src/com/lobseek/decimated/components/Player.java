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
package com.lobseek.decimated.components;

import com.badlogic.gdx.Gdx;
import com.lobseek.decimated.units.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.decimated.Main;
import com.lobseek.decimated.ProjectLogger;
import com.lobseek.utils.ColorFabricator;
import com.lobseek.widgets.LWContainer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yew_Mentzaki
 */
public class Player {

    private int index;
    private final Room room;
    public String name = "Bot";
    public Color color = Color.RED;
    public boolean ai = false;
    public int alliance = -1;
    public Class types[];
    public float produce[];
    public int units, maxUnits = 20;

    public void setTypes(Class... types) {
        this.types = types;
        this.produce = new float[types.length];
        for (int i = 0; i < produce.length; i++) {
            produce[i] = 1f / ((float) produce.length);
        }
    }

    public Player(Room room, int index) {
        this.room = room;
        this.index = index;
        setTypes(
                Disruptor.class,
                Phantom.class,
                Planer.class,
                Dragoon.class,
                Controller.class
        );
    }

    public Unit spawn(float x, float y, float angle) {
        if (units > maxUnits) {
            return null;
        }
        float rand = Main.R.nextFloat();
        float value = 0;
        Class clazz = null;
        for (int i = 0; i < types.length; i++) {
            value += produce[i];
            if (rand <= value) {
                clazz = types[i];
                break;
            }
        }
        if (clazz == null) {
            ProjectLogger.println("clazz == null");
            clazz = types[Main.R.nextInt(types.length)];
        }
        try {
            Constructor c
                    = clazz.getConstructor(float.class, float.class, float.class, int.class);
            return (Unit) c.newInstance(x, y, angle, index);
        } catch (Exception ex) {
            ProjectLogger.println(ex);
        }
        return null;
    }

    public LWContainer getUnitList() {
        final com.badlogic.gdx.graphics.g2d.Sprite background
                = new com.badlogic.gdx.graphics.g2d.Sprite(
                        new Texture(Gdx.files.internal("background.png")));

        background.getTexture()
                .setFilter(Texture.TextureFilter.Linear,
                        Texture.TextureFilter.Linear);
        LWContainer menu = new LWContainer() {
            float alpha = 0;

            @Override
            public void act(float delta) {
                if (isVisible()) {
                    alpha = Math.min(1, alpha + delta);
                } else {
                    alpha = Math.max(0, alpha - delta);
                }
                super.act(delta);
            }

            @Override
            public void draw(Batch b) {
                background.setSize(parent.width, parent.height);
                background.setPosition(0, 0);
                background.setColor(ColorFabricator.neon(alpha));
                background.draw(b);
                width = parent.width;
                height = parent.height;
                super.draw(b);
            }

            @Override
            public boolean checkSwipe(Touch t) {
                super.checkSwipe(t);
                return isVisible();
            }

            @Override
            public boolean checkTapDown(Touch t) {
                super.checkTapDown(t);
                return isVisible();
            }

            @Override
            public boolean checkTapUp(Touch t) {
                super.checkTapUp(t);
                return isVisible();
            }

        };
        return menu;
    }

    public boolean isEnemy(int player) {
        //*
        if (player == 0) {
            return false;
        } else if (room.players[player] == this) {
            return false;
        } else if (alliance == -1) {
            return true;
        } else {
            return room.players[player].alliance == alliance;
        }
        //*/return true;
    }

}
