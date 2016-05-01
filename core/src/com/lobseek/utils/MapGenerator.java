/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.lobseek.decimated.actors.Base;
import com.lobseek.decimated.components.Point;
import com.lobseek.decimated.components.Room;
import java.util.Random;
import static com.lobseek.utils.Math.*;

/**
 *
 * @author Whizzpered
 */
public class MapGenerator {

    int players, cpteam, current = 1;

    public MapGenerator(int players, int capability) {
        cpteam = capability;
        this.players = players;
    }

    public void generate(Room room) {
        /*
         Множество баз одного игрока b[];
         пока размер b < 3:
         угол = случайный число от 0 до 2П/n - некоторое число, чтобы при повороте базы тоже не пересекались друг с другом;
         расстояние = случайное число от 250/tan(П/n) до 3000;
         х = расстояние * cos(угол)
         y = расстояние * sin(угол)
         если dist(x, y, b[i].x, b[i].y) < 500, то прервать цикл,
         иначе добавить новую базу с координатами x, y в множество b
        
        
         добавил счетчик неудачных попыток создать базу. Если их слишком много, обнуляю 
         массив. Это на тот случай, если вдруг они неудачно расположатся и будет 
         практически невозможно их создать. Надеюсь, код понятен.
        
         */
        Random r = new Random();
        float x, y, angle, l, dist;
        boolean is;
        int count = 0;
        Array<Point> p = new Array<Point>();
        while (p.size < 3) {
            if (count > 250) {
                p = new Array<Point>();
            }
            angle =((Math.PI2 / players) * current);
            l = r.nextInt(2750) + 250;
            x = l * Math.cos(angle);
            y = l * Math.sin(angle);
            is = false;
            if (p.size > 1) {
                for (Point pa : p) {
                    dist = dist(x, y, pa.x, pa.y);
                    if (dist < 500) {
                        is = false;
                        break;
                    } else {
                        is = true;
                    }
                }
            } else {
                p.add(new Point(x, y));
            }
            if (is) {
                count = 0;
                p.add(new Point(x, y));
            } else {
                count++;
            }
        }
        for (current = 1; current <= players; current++) {
            angle = ((Math.PI2 / players) * current);
            for (Point pa : p) {
                float ax = pa.x;
                pa.x = pa.x * Math.cos(angle) - pa.y * Math.sin(angle);
                pa.y = ax * Math.sin(angle) - pa.y * Math.cos(angle);
                room.add(new Base(pa.x, pa.y, current));
            }
        }
    }

}
