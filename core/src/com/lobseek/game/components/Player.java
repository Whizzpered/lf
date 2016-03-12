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

/**
 *
 * @author Yew_Mentzaki
 */
public class Player {

    private final Room room;
    public String name = "Bot";
    public Color color = Color.RED;
    public boolean ai = false;
    public int alliance = -1;

    public Player(Room room) {
        this.room = room;
    }

    public boolean isEnemy(int player) {
        if (player == 0) {
            return false;
        }
        if (alliance == -1) {
            return true;
        }else{
            return room.players[player].alliance == alliance;
        }
    }

}
