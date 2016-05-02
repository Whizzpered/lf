/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.utils;

import com.lobseek.decimated.components.Room;


/**
 *
 * @author Whizzpered
 */
public abstract class MapGenerator {

    public int players, cpteam, current = 1;

    public MapGenerator(int players, int capability) {
        cpteam = capability;
        this.players = players;
    }

    public abstract void generate(Room room);

}
