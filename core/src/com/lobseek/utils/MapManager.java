/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.utils;

import com.badlogic.gdx.utils.Array;
import com.lobseek.decimated.Main;
import com.lobseek.decimated.components.Room;
import com.lobseek.utils.mapgenerators.StandartGenerator;
import java.util.Random;

/**
 *
 * @author Whizzpered
 */
public class MapManager {

    Array<MapGenerator> maps = new Array<MapGenerator>();
    Room target;
    int players, cpteam, current = 1;

    public MapManager(int players, int capability, Room room) {
        target = room;
        this.players = players;
        cpteam = capability;

        //Хз, что тут еще должно быть. 
        maps.add(new StandartGenerator(players, capability));
    }

    public void generate() {
        int i = Main.R.nextInt(maps.size);
        maps.get(i).generate(target);
    }

}
