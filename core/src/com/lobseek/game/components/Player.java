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
import com.lobseek.game.Main;
import com.lobseek.game.ProjectLogger;
import com.lobseek.game.units.*;
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
    public int units, maxUnits = 10;

    public void setTypes(Class... types) {
        this.types = types;
        this.produce = new float[types.length];
        for (int i = 0; i < produce.length; i++) {
            produce[i] = 100f / ((float) produce.length);
        }
    }

    public Player(Room room, int index) {
        this.room = room;
        this.index = index;
        setTypes(Disruptor.class, Phantom.class, Planer.class);
    }

    public Unit spawn(float x, float y, float angle) {
        if (units > maxUnits) {
            return null;
        }
        Class clazz = types[Main.R.nextInt(types.length)];
        try {
            Constructor c
                    = clazz.getConstructor(float.class, float.class, float.class, int.class);
            return (Unit) c.newInstance(x, y, angle, index);
        } catch (Exception ex) {
            ProjectLogger.println(ex);
        }
        return null;
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
