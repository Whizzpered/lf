/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.game.screens;

import com.lobseek.game.components.Room;

/**
 *
 * @author aenge
 */
public class GameScreen extends Screen {

    Room room;

    public GameScreen() {
        room = new Room(this, 256, 64);
        room.add()
    }

    @Override
    public void render(float delta) {
        room.render(delta);
    }

}
