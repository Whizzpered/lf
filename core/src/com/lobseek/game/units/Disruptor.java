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

/**
 *
 * @author Yew_Mentzaki
 */
public class Disruptor extends Unit {

    class PlasmaBullet extends Bullet {

        public PlasmaBullet(Unit from, Unit to, float x, float y) {
            super(from, to, x, y);
            speed = 1200;
            sprite = new Sprite("plasma/b0");
            detonationDistance = 80;
        }

        @Override
        public void explode(Unit to) {
            to.hit(10, from);
        }
    }

    class DisruptorWeapon extends Weapon {

        public DisruptorWeapon() {
            x = 5;
            y = 0;
            turnSpeed = 3;
            range = 1000;
            ammo = maxAmmo = 10;
            reloadTime = 0.8f;
            reloadAmmoTime = 10;
        }

        @Override
        public void shoot(Unit to, Point from) {
            room.add(new PlasmaBullet(on, to, from.x, from.y));
        }

    }

    public Disruptor(float xcord, float ycord, float angle, int owner) {
        super(xcord, ycord, angle, owner);
        weapons = new Weapon[]{
            new DisruptorWeapon()
        };
        setSprite("disruptor");
        width = height = 75;
        mass = 10;
        hp = maxHp = 100;
        speed = 100;
        turnSpeed = 2;
    }

}
