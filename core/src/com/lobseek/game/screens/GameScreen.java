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
package com.lobseek.game.screens;

import com.lobseek.game.actors.Base;
import com.lobseek.game.actors.Test;
import com.lobseek.game.components.Room;
import com.lobseek.game.units.Disruptor;
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
        room.add(new Base(300, 150, 1));
        room.add(new Base(-300, 150, 1));
        room.add(new Base(-0, 150, 1));
        room.add(new Base(300, -150, 2));
        room.add(new Base(-300, -150, 2));
        room.add(new Base(-0, -150, 2));
        for (int i = 0; i < 120; i++) {
            room.add(new Disruptor(0, 300, 0, 1));
            room.add(new Disruptor(0, -300, 0, 2));
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

}
