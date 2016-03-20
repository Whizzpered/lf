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
package com.lobseek.game.units;

import com.lobseek.game.components.Bullet;
import com.lobseek.game.components.Point;
import com.lobseek.game.components.Sprite;
import com.lobseek.game.components.Unit;
import com.lobseek.game.components.Weapon;
import static com.lobseek.utils.Math.*;

/**
 *
 * @author Yew_Mentzaki
 */
public class Planer extends Unit {

    class PlasmaBullet extends Bullet {

        public PlasmaBullet(Unit from, Unit to, float x, float y) {
            super(from, to, x, y);
            speed = 1600;
            sprite = new Sprite("laser");
            detonationDistance = 45;
        }

        @Override
        public void explode(Unit to) {
            to.hit(2, from);
        }
    }

    class DisruptorWeapon extends Weapon {

        public DisruptorWeapon(float x, float y) {
            this.x = x;
            this.y = y;
            cx = 50;
            turnSpeed = 6;
            range = 1000;
            ammo = maxAmmo = 5;
            reloadTime = 0.3f;
            reloadAmmoTime = 1;
        }

        @Override
        public void shoot(Unit to, Point from) {
            room.add(new PlasmaBullet(on, to, from.x, from.y));
        }

    }

    @Override
    public void move(float delta) {
        angle += turnSpeed * delta;
        if (x != tx && y != ty) {
            float d = dist(x, y, tx, ty);
            d = Math.min(speed * delta, d);
            float a = atan2(ty - y, tx - x);
            move(cos(a) * d, sin(a) * d);
        }
    }

    public Planer(float xcord, float ycord, float angle, int owner) {
        super(xcord, ycord, angle, owner);
        weapons = new Weapon[]{
            new DisruptorWeapon(28, -22),
            new DisruptorWeapon(-5, 34),
            new DisruptorWeapon(-31, -28)
        };
        setSprite("planer");
        width = height = 75;
        mass = 40;
        hp = maxHp = 250;
        speed = 170;
        turnSpeed = 2;
        flying = true;
    }

}
