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
import com.lobseek.decimated.components.Bullet;
import com.lobseek.decimated.components.Point;
import com.lobseek.decimated.components.Sprite;
import com.lobseek.decimated.components.Unit;
import com.lobseek.decimated.components.Weapon;
import static com.lobseek.utils.Math.*;

/**
 *
 * @author Yew_Mentzaki
 */
public class Planer extends Unit {

    public float power = 0, shield = 0;
    private static Sprite power_sprite = new Sprite("planer_power"),
             shield_sprite = new Sprite("planer_shield");
    
    class PlasmaBullet extends Bullet {

        public PlasmaBullet(Unit from, Unit to, float x, float y) {
            super(from, to, x, y);
            speed = 2000;
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
            range = 750;
            ammo = maxAmmo = 5;
            reloadTime = 0.3f;
            reloadAmmoTime = 1;
            speed = 1600;
        }

        @Override
        public void shoot(Unit to, Point from) {
            room.add(new PlasmaBullet(on, to, from.x, from.y));
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(hp > 0){
        if(shield == 0){
            power = Math.min(power + delta, 20);
        }else{
            shield = Math.max(shield - delta, 0);
        }
        }else{
            shield = power = 0;
        }
        immortal = shield > 0;
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

    @Override
    public void hit(float hp, Unit from) {
        if(shield >= 1){
            return;
        }else if(shield > 0){
            super.hit(hp * (1 - shield), from);
        }else{
            super.hit(hp, from);
            shield = power / 2;
            power = 0;
        }
    }

    @Override
    public void render(Batch batch, float delta, Color parentColor) {
        super.render(batch, delta, parentColor);
        power_sprite.a = power / 20;
        power_sprite.x = x;
        power_sprite.y = y;
        power_sprite.angle = angle;
        power_sprite.draw(batch);
        shield_sprite.a = Math.min(shield / 2, 1);
        shield_sprite.x = x;
        shield_sprite.y = y;
        shield_sprite.angle = angle;
        shield_sprite.draw(batch);
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
        hp = maxHp = 350;
        speed = 170;
        turnSpeed = 2;
        flying = true;
    }

}
