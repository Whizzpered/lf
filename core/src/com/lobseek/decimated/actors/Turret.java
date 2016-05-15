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
package com.lobseek.decimated.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.decimated.Main;
import com.lobseek.decimated.components.Actor;
import com.lobseek.decimated.components.Point;
import com.lobseek.decimated.components.Room;
import com.lobseek.decimated.components.Unit;
import com.lobseek.decimated.components.Sprite;
import com.lobseek.decimated.particles.Explosion;
import com.lobseek.decimated.units.Phantom;
import static com.lobseek.utils.Math.*;
import com.lobseek.utils.Sound;
import com.lobseek.utils.SoundLoader;

/**
 *
 * @author yew_mentzaki
 */
public class Turret extends Actor {

    static Sprite turret = new Sprite("turret_body"),
            turret_shadow = new Sprite("turret_body_shadow");

    static Sprite turret_head = new Sprite("turret_head"),
            turret_head_shadow = new Sprite("turret_head_shadow"),
            turret_head_glow = new Sprite("turret_head_glow"),
            turret_head_glow2 = new Sprite("turret_head_glow2");
    Base base;
    Unit target;
    public float cx, cy, onangle;
    public float turnSpeed = 2, range = 200;
    public float speed = 400;

    public int ammo, maxAmmo = ammo = 1;
    public float reload, reloadTime, reloadAmmoTime = reloadTime = 10;
    public float attack, attackTime;

    public Turret(float x, float y, float angle, Base base) {
        super(x, y, atan2(y - base.y, x - base.x));
        this.angle = onangle = atan2(y - base.y, x - base.x);
        this.base = base;
        z = 0;
        width = height = 300;
    }

    @Override
    public void renderShadow(Batch batch, float delta) {
        turret_shadow.x = x;
        turret_shadow.y = y + 10;
        turret_shadow.angle = onangle;
        turret_shadow.draw(batch);
        turret_shadow.y = y + 20;
        turret_shadow.draw(batch);

    }

    @Override
    public void act(float delta) {
        if (reload > 0) {
            reload = Math.max(0, reload - delta);
        }
        float ta = (float) onangle;
        float x = this.x;
        float y = this.y;
        if (target != null) {
            float d = dist(x, y, target.x, target.y);
            d /= speed;
            ta = (float) atan2(target.y + target.vy * d - y, target.x + target.vx * d - x);
        }
        if (angle < -PI) {
            angle += 2 * PI;
        }
        if (angle > +PI) {
            angle -= 2 * PI;
        }
        if (angle != ta) {
            if (abs(ta - angle) > turnSpeed * delta) {
                int v = (abs(ta - angle) <= 2 * PI - abs(ta - angle)) ? 1 : -1;

                if (ta < angle) {
                    angle -= turnSpeed * v * delta;
                } else if (ta > angle) {
                    angle += turnSpeed * v * delta;
                }
            } else {
                angle = ta;
            }
        }
        if (angle == ta && target != null) {
            if (reload == 0) {
                if (attack == 0) {
                    attack = attackTime;
                    ammo--;
                    if (ammo == 0) {
                        reload = reloadAmmoTime;
                        ammo = maxAmmo;
                    } else {
                        reload = reloadTime;
                    }
                    Point p = new Point(cx, cy);
                    float ang = angle;
                    p.x = cx * cos(ang) - cy * sin(ang);
                    p.y = cx * sin(ang) + cy * cos(ang);
                    p.x += x;
                    p.y += y;
                    shoot(target, p);
                } else {
                    attack = Math.max(0, attack - delta);
                }
            }
        }
    }

    @Override
    public void tick(float delta) {
        float r = range;
        Unit un = null;
        if (room != null) {
            for (Actor a : room.actors) {
                if (a instanceof Unit) {
                    Unit u = (Unit) a;
                    float d = dist(u.x, u.y, x, y);
                    if (u.hp > 0 && room.players[base.owner].isEnemy(u.owner) && d <= r) {
                        target = u;
                        r = d;
                        un = u;
                    }
                }
            }
        }
        target = un;
    }

    @Override
    public void render(Batch batch, float delta) {
        turret.x = x;
        turret.y = y;
        turret.angle = onangle;
        turret.draw(batch);
        turret_head.x = x;
        turret_head.y = y;
        turret_head.angle = angle;
        turret_head.draw(batch);
        turret_head_glow2.x = x;
        turret_head_glow2.y = y;
        turret_head_glow2.angle = angle;
        turret_head_glow2.a = 1 - reload / reloadAmmoTime;
        turret_head_glow2.draw(batch);
        turret_head_glow.x = x;
        turret_head_glow.y = y;
        turret_head_glow.angle = angle;
        turret_head_glow.a = 1 - reload / reloadAmmoTime;
        turret_head_glow.draw(batch);
    }

    static Sprite exp = new Sprite("plasma/explosion6");
    Unit unit = new Unit(x, y, 0, 0);

    private void shoot(Unit target, Point p) {
        if (target instanceof Phantom) {
            room.add(new Explosion(x, y, 600, exp, 35, 230));
            room.add(new Explosion(x, y, 400, exp, 35, 330));
            room.add(new Explosion(x, y, 200, exp, 35, 430));
            room.blind(0.7f, target.x, target.y);
            ((Phantom) target).visiblity = 1;
        } else {
            room.add(new Explosion(target.x, target.y, 600, exp, 35, 230));
            room.add(new Explosion(target.x, target.y, 400, exp, 35, 330));
            room.add(new Explosion(target.x, target.y, 200, exp, 35, 430));
            room.blind(0.7f, target.x, target.y);
            target.hit(500, unit);
        }
        float dist = dist(x, y, room.cam.x, room.cam.y)
                - (room.getWidth() / 3 + room.getHeight() / 3);
        if (dist < 300) {
            Main.sl.getSound("tesla").play((300 - dist) / 300);
        }
    }

}
