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
package com.lobseek.decimated.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import static com.lobseek.utils.Math.*;

/**
 *
 * @author Yew_Mentzaki
 */
public class Weapon {

    public Unit on;
    public float x, y, cx, cy, angle;
    public float turnSpeed, range;
    public float speed;
    public Room room;
    public Unit target;

    public int ammo, maxAmmo;
    public float reload, reloadTime, reloadAmmoTime;
    public float attack, attackTime;

    public Weapon() {
    }

    public void create() {
    }

    Sprite sprite, sprite_shadow;

    public void setSprite(String name) {
        sprite = new Sprite(name + "_head");
        sprite_shadow = new Sprite(name + "_head_shadow");
    }

    public void act(float delta) {
        if (reload > 0) {
            reload = Math.max(0, reload - delta);
        }
        float an = on.angle;
        float x = this.x * cos(an) - this.y * sin(an);
        float y = this.x * sin(an) + this.y * cos(an);
        x += on.x;
        y += on.y;
        float ta = (float) on.angle;
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
            if (reload == 0 && (on.visiblity == 1 || !on.selected || on.target == target)) {
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
                }else{
                    attack = Math.max(0, attack - delta);
                }
            }
        }
    }

    public Point getWeaponPosition() {
        float x = this.x * cos(angle) - this.y * sin(angle);
        float y = this.x * sin(angle) + this.y * cos(angle);
        x += on.x;
        y += on.y;
        Point p = new Point(cx, cy);
        float ang = angle;
        p.x = cx * cos(ang) - cy * sin(ang);
        p.y = cx * sin(ang) + cy * cos(ang);
        p.x += x;
        p.y += y;
        return p;
    }

    public void tick(float delta) {
        float dist = Float.MAX_VALUE;
        Unit t = null;
        for (Actor a : room.actors) {
            if (a != on && (a instanceof Unit)) {
                Unit u = (Unit) a;
                if (u.hp > 0 && !u.immortal && u.visiblity > 0
                        && room.players[on.owner].isEnemy(u.owner)) {
                    float d2 = dist(u.x, u.y, on.x, on.y);
                    float d = dist(u.x, u.y, on.tx, on.ty);
                    if (d2 <= range) {
                        if (d < dist) {
                            dist = d;
                            t = u;
                        }
                        if (on.target == u) {
                            t = u;
                            break;
                        }
                    }
                }
            }
        }
        target = t;
    }

    public void shoot(Unit to, Point from) {

    }

    public void renderShadow(Batch batch, float delta) {
        Point p = new Point(x, y);
        float an = on.angle;
        p.x = x * cos(an) - y * sin(an);
        p.y = x * sin(an) + y * cos(an);
        p.x += on.x;
        p.y += on.y;
        sprite_shadow.x = p.x;
        sprite_shadow.y = p.y + 25;
        sprite_shadow.angle = angle;
        sprite_shadow.a = Math.max(0, 1 - on.deathTimer) * on.visiblity;
        sprite_shadow.draw(batch);
        sprite_shadow.y = p.y + 17.5f;
        sprite_shadow.draw(batch);
    }

    public final void render(Batch batch, float delta, Color parentColor) {
        Point p = new Point(x, y);
        if (on != null) {
            float an = on.angle;
            p.x = x * cos(an) - y * sin(an);
            p.y = x * sin(an) + y * cos(an);
            p.x += on.x;
            p.y += on.y;
            render(batch, delta, p, parentColor);
        }
    }

    public void render(Batch batch, float delta, Point point, Color parentColor) {
        sprite.x = point.x;
        sprite.y = point.y;
        sprite.angle = angle;
        sprite.setColor(parentColor);
        sprite.draw(batch);
    }
}
