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

import com.badlogic.gdx.graphics.g2d.Batch;
import static com.lobseek.utils.Math.*;

/**
 *
 * @author Yew_Mentzaki
 */
public class Bullet extends Actor {

    public Unit from, to;
    public float speed;
    public float detonationDistance;
    public float lifeTime = 10;

    public Sprite sprite;
    public static Sprite MMS = new Sprite("minimap/bullet");

    public Bullet(Unit from, Unit to, float x, float y) {
        super(x, y, atan2(to.y - y, to.x - x));
        this.from = from;
        this.to = to;
        z = 25;
        phantom = true;
        minimapSprite = MMS;
    }

    @Override
    public void create() {
        float d = dist(x, y, to.x, to.y);
        d /= speed / 100;
        angle = (float) atan2(to.y + to.vy * d - y, to.x + to.vx * d - x);
    }

    public void move(float delta) {
        x += cos(angle) * speed * delta;
        y += sin(angle) * speed * delta;
    }

    @Override
    public void act(float delta) {
        lifeTime -= delta;
        if (lifeTime <= 0) {
            remove();
        }
        move(delta);
    }

    @Override
    public void tick(float delta) {
        for (Actor a : room.actors) {
            if (a != null && (a instanceof Unit)) {
                Unit u = (Unit) a;
                if (u.hp > 0 && room.players[from.owner].isEnemy(u.owner)) {
                    float d = dist(x, y, u.x, u.y);
                    if (d < detonationDistance) {
                        explode(u);
                        remove();
                    }
                }
            }
        }
    }

    public void explode(Unit to) {

    }

    @Override
    public void render(Batch batch, float delta) {
        sprite.x = x;
        sprite.y = y;
        sprite.angle = angle;
        sprite.draw(batch);
    }
}
