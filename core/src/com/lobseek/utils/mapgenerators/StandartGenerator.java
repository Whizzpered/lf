/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.utils.mapgenerators;

import com.badlogic.gdx.utils.Array;
import com.lobseek.decimated.Main;
import com.lobseek.decimated.actors.Base;
import com.lobseek.decimated.components.Point;
import com.lobseek.decimated.components.Room;
import com.lobseek.utils.MapGenerator;
import static com.lobseek.utils.Math.PI2;
import static com.lobseek.utils.Math.dist;
import java.util.Random;

/**
 *
 * @author Whizzpered
 */
public class StandartGenerator extends MapGenerator {

    public StandartGenerator(int players, int capability) {
        super(players, capability);
    }

    @Override
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
            angle = Main.R.nextFloat() * (com.lobseek.utils.Math.PI2 / players);
            float lmin = 250 / com.lobseek.utils.Math.tan(com.lobseek.utils.Math.PI / players);
            l = r.nextInt((int)(room.size - lmin - 500)) + lmin;
            x = l * com.lobseek.utils.Math.cos(angle);
            y = l * com.lobseek.utils.Math.sin(angle);
            is = false;
            if (p.size > 1) {
                for (Point pa : p) {
                    dist = dist(x, y, pa.x, pa.y);
                    if (dist < 1000) {
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
        float theta = Main.R.nextFloat() * PI2;
        for (current = 1; current <= players; current++) {
            angle = ((com.lobseek.utils.Math.PI2 / players) * current) + theta;
            for (Point pa : p) {
                float pax = pa.x * com.lobseek.utils.Math.cos(angle) - pa.y * com.lobseek.utils.Math.sin(angle);
                float pay = pa.x * com.lobseek.utils.Math.sin(angle) + pa.y * com.lobseek.utils.Math.cos(angle);
                room.add(new Base(pax, pay, current));
            }
        }
    }
}
