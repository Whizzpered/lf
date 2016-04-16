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
package com.lobseek.decimated.screens;

import com.lobseek.decimated.actors.Base;
import com.lobseek.decimated.actors.Test;
import com.lobseek.decimated.components.Room;
import com.lobseek.decimated.units.Disruptor;
import static com.lobseek.utils.Math.*;

/**
 *
 * @author Yew_Mentzaki
 */
public class GameScreen extends Screen {

    Room room;

    public GameScreen() {
        room = new Room(this, 1024, 64);
        room.start();
        room.player = 1;
        add(room);
        room.add(new Base(600, 2000, 1));
        room.add(new Base(-600, 2000, 1));
        room.add(new Base(-0, 2000, 1));
        room.add(new Base(600, -2000, 2));
        room.add(new Base(-600, -2000, 2));
        room.add(new Base(-0, -2000, 2));
        room.add(new Base(2000, 600, 3));
        room.add(new Base(2000, -600, 3));
        room.add(new Base(2000, 0, 3));
        room.add(new Base(-2000, 600, 4));
        room.add(new Base(-2000, -600, 4));
        room.add(new Base(-2000, 0, 4));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

}
