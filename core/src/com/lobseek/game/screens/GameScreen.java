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

import com.lobseek.game.actors.Test;
import com.lobseek.game.components.Room;

/**
 *
 * @author Yew_Mentzaki
 */
public class GameScreen extends Screen {

    Room room;

    public GameScreen() {
        room = new Room(this, 256, 64);
        room.start();
        add(room);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

}
