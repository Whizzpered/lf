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
import com.lobseek.decimated.particles.Explosion;
import static com.lobseek.utils.Math.*;

/**
 *
 * @author Yew_Mentzaki
 */
public class Dragoon extends Unit {
    
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
            x = 15;
            y = 0;
            turnSpeed = 300;
            range = 750;
            ammo = maxAmmo = 1;
            reloadTime = 5f;
            reloadAmmoTime = 5;
            speed = 1200;
        }

        @Override
        public void shoot(Unit to, Point from) {
            room.add(new PlasmaBullet(on, to, from.x, from.y));
        }

        @Override
        public void render(Batch batch, float delta, Point point, Color parentColor) {
        }

        @Override
        public void renderShadow(Batch batch, float delta) {
        }

    }

    static Sprite sprite = new Sprite("dragoon_body");
    static Sprite spriteShadow = new Sprite("dragoon_body_shadow");
    static Sprite spriteLeg = new Sprite("dragoon_leg");
    static Sprite spriteLegShadow = new Sprite("dragoon_leg_shadow");
    static Sprite spriteThing = new Sprite("dragoon_thing");
    static Sprite spriteThingShadow = new Sprite("dragoon_thing_shadow");
    static Sprite spriteDoor = new Sprite("dragoon_door");
    float thingAngle, doorAngle;
    Point legTarget[] = new Point[4];

    @Override
    public void act(float delta) {
        thingAngle += delta * 3;
        super.act(delta);
        if(weapons[0].target != null && weapons[0].reload < 0.3f){
            doorAngle = Math.min(2, doorAngle + delta * 8);
        }else{
            doorAngle = Math.max(0, doorAngle - delta * 4);
        }
    }

    @Override
    public void move(float delta) {
        if (x != tx && y != ty) {
            float speed = this.speed * abs(cos(thingAngle * 2)) * 2;
            float d = dist(x, y, tx, ty);
            d = Math.min(speed * delta, d);
            float a = atan2(ty - y, tx - x);
            move(cos(a) * d, sin(a) * d);
            float s = cos(thingAngle * 2) > 0 ? 1 : -1;
            for (int i = 0; i < legTarget.length; i++) {
                float s1 = s * (i % 2 == 0 ? 1 : -1);
                legTarget[i].x += s1 * cos(a) * d / 2;
                legTarget[i].y += s1 * sin(a) * d / 2;
            }
        }
        float a1 = PI / 4, a2 = PI * 2 / ((float) legTarget.length);
        for (int i = 0; i < legTarget.length; i++) {
            float lx = width / 2 * cos(a1 + a2 * i);
            float ly = height / 2 * sin(a1 + a2 * i);
            legTarget[i].x = (19 * legTarget[i].x + lx) / 20;
            legTarget[i].y = (19 * legTarget[i].y + ly) / 20;
        }
    }

    @Override
    public void renderShadow(Batch batch, float delta) {
        spriteThingShadow.x = x;
        spriteThingShadow.y = y;
        spriteThingShadow.angle = thingAngle;
        spriteThingShadow.a = Math.max(0, 1 - deathTimer);
        spriteThingShadow.draw(batch);
        for (int i = 0; i < legTarget.length; i++) {
            float a = atan2(legTarget[i].y, legTarget[i].x);
            spriteLegShadow.x = x + legTarget[i].x / 2;
            spriteLegShadow.y = y + legTarget[i].y / 2 + 5;
            spriteLegShadow.angle = a;
            spriteLegShadow.a = Math.max(0, 1 - deathTimer);
            spriteLegShadow.draw(batch);
            spriteLegShadow.y = y + legTarget[i].y / 2 + 10;
            spriteLegShadow.draw(batch);
        }
        spriteShadow.x = x;
        spriteShadow.y = y + 12;
        spriteShadow.a = Math.max(0, 1 - deathTimer);
        spriteShadow.draw(batch);
    }

    @Override
    public void render(Batch batch, float delta, Color parentColor) {
        parentColor = new Color(parentColor.r, parentColor.g,
                parentColor.b, parentColor.a * Math.max(0, 1 - deathTimer));
        spriteThing.x = x;
        spriteThing.y = y - 5;
        spriteThing.angle = thingAngle;
        spriteThing.setColor(parentColor);
        spriteThing.draw(batch);
        for (int i = 0; i < legTarget.length; i++) {
            float a = atan2(legTarget[i].y, legTarget[i].x);
            spriteLeg.x = x + legTarget[i].x / 2;
            spriteLeg.y = y + legTarget[i].y / 2;
            spriteLeg.angle = a;
            spriteLeg.setColor(parentColor);
            spriteLeg.draw(batch);
        }
        sprite.x = x;
        sprite.y = y;
        sprite.setColor(parentColor);
        sprite.draw(batch);
        spriteDoor.setColor(room.players[owner].color);
        spriteDoor.r *= parentColor.r;
        spriteDoor.g *= parentColor.g;
        spriteDoor.b *= parentColor.b;
        spriteDoor.a *= parentColor.a;
        spriteDoor.x = x - 17;
        spriteDoor.y = y - 7;
        spriteDoor.angle = doorAngle;
        spriteDoor.draw(batch);
        spriteDoor.x = x + 17;
        spriteDoor.width = -spriteDoor.width;
        spriteDoor.angle = -doorAngle;
        spriteDoor.draw(batch);
        spriteDoor.width = -spriteDoor.width;
    }

    public Dragoon(float xcord, float ycord, float angle, int owner) {
        super(xcord, ycord, PI / 2, owner);
        weapons = new Weapon[]{
            new DisruptorWeapon()
        };
        width = 100;
        height = 120;
        mass = 40;
        hp = maxHp = 750;
        speed = 150;
        turnSpeed = 2;
        flying = true;
        float a1 = PI / 4, a2 = PI * 2 / ((float) legTarget.length);
        for (int i = 0; i < legTarget.length; i++) {
            legTarget[i] = new Point(
                    width / 2 * cos(a1 + a2 * i),
                    height / 2 * sin(a1 + a2 * i)
            );
        }
    }

}
