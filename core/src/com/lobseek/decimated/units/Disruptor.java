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

import com.lobseek.decimated.Main;
import com.lobseek.decimated.components.Bullet;
import com.lobseek.decimated.components.Point;
import com.lobseek.decimated.components.Sprite;
import com.lobseek.decimated.components.Unit;
import com.lobseek.decimated.components.Weapon;
import com.lobseek.decimated.particles.Explosion;
import com.lobseek.decimated.particles.Lazer;
import static com.lobseek.utils.Math.*;
import static java.lang.Math.max;

/**
 *
 * @author Yew_Mentzaki
 */
public class Disruptor extends Unit {

    private static Sprite explosion_1 = new Sprite("plasma/explosion4"),
            explosion_2 = new Sprite("plasma/explosion5");
    
    private float energy, energyReload = 30;
    
    class DisruptorWeapon extends Weapon {

        public DisruptorWeapon() {
            x = 5;
            y = 0;
            cx = 5;
            turnSpeed = 3;
            range = 1550;
            ammo = maxAmmo = 1;
            reloadTime = 15f;
            reloadAmmoTime = 15f;
            speed = 1200;
        }

        @Override
        public void shoot(Unit to, Point from) {
            to.hit(50, to);
            room.add(new Lazer(this, from.x, from.y, to.x, to.y));
            room.add(new Explosion(to.x, to.y, 400, explosion_1, 35, 300));
            room.add(new Explosion(to.x, to.y, 500, explosion_2, 35, 120));
            room.add(new Explosion(from.x, from.y, 200, explosion_2, 35, 80));
        }

    }

    @Override
    public void hit(float hp, Unit from) {
        super.hit(hp, from);
        if(energy == 0){
            energy = energyReload;
            room.add(new Explosion(x, y, 1000, explosion_2, 35, 500));
            float a = atan2(from.y - y, from.x - x) + Main.R.nextFloat() - 0.5f;
            x -= cos(a) * weapons[0].range / 2;
            y -= sin(a) * weapons[0].range / 2;
            room.add(new Explosion(x, y, 1000, explosion_1, 35, 500));
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        energy = max(energy - delta, 0);
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
