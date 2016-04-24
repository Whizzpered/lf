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
package com.lobseek.decimated.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.decimated.components.Sprite;
import com.lobseek.decimated.components.Unit;
import static com.lobseek.utils.Math.*;

/**
 *
 * @author Yew_Mentzaki
 */
public class Dragoon extends Unit {

    Sprite sprite = new Sprite("dragoon_body");
    Sprite spriteLeg = new Sprite("dragoon_leg");
    
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void move(float delta) {
        if (x != tx && y != ty) {
            float d = dist(x, y, tx, ty);
            d = Math.min(speed * delta, d);
            float a = atan2(ty - y, tx - x);
            move(cos(a) * d, sin(a) * d);
        }
    }

    @Override
    public void render(Batch batch, float delta, Color parentColor) {
        
    }

    public Dragoon(float xcord, float ycord, float angle, int owner) {
        super(xcord, ycord, angle, owner);
        width = height = 75;
        mass = 40;
        hp = maxHp = 350;
        speed = 170;
        turnSpeed = 2;
        flying = true;
    }

}
