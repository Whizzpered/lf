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

import com.lobseek.decimated.components.Bullet;
import com.lobseek.decimated.components.Point;
import com.lobseek.decimated.components.Sprite;
import com.lobseek.decimated.components.Unit;
import com.lobseek.decimated.components.Weapon;
import com.lobseek.decimated.particles.Explosion;

/**
 *
 * @author Yew_Mentzaki
 */
public class Disruptor extends Unit {

    private static Sprite
            explosion_1 = new Sprite("plasma/explosion1"),
            explosion_2 = new Sprite("plasma/explosion2"); 
    
    class PlasmaBullet extends Bullet {

        public PlasmaBullet(Unit from, Unit to, float x, float y) {
            super(from, to, x, y);
            speed = 1200;
            sprite = new Sprite("plasma/b0");
            detonationDistance = 80;
        }

        @Override
        public void explode(Unit to) {
            to.hit(60, from);
            room.add(new Explosion(x, y, 300, explosion_1, 35, 400));
            room.add(new Explosion(x, y, 600, explosion_2, 35, 120));
        }
    }

    class DisruptorWeapon extends Weapon {

        public DisruptorWeapon() {
            x = 5;
            y = 0;
            turnSpeed = 3;
            range = 800;
            ammo = maxAmmo = 1;
            reloadTime = 5f;
            reloadAmmoTime = 5;
            speed = 1200;
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
        mass = 40;
        hp = maxHp = 600;
        speed = 100;
        turnSpeed = 2;
    }

}
