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

import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.decimated.Main;
import com.lobseek.decimated.components.Actor;
import com.lobseek.decimated.components.Point;
import com.lobseek.decimated.components.Sprite;
import com.lobseek.decimated.components.Unit;
import com.lobseek.decimated.components.Weapon;
import com.lobseek.decimated.particles.Explosion;
import static com.lobseek.utils.Math.atan2;
import static com.lobseek.utils.Math.cos;
import static com.lobseek.utils.Math.dist;
import static com.lobseek.utils.Math.sin;

/**
 *
 * @author Yew_Mentzaki
 */
public class Controller extends Unit {

    static Sprite droneSprite = new Sprite("drone");
    static Sprite droneSpriteShadow = new Sprite("drone_shadow");
    static Sprite droneSpriteWing = new Sprite("drone_wing");
    static Sprite droneSpriteWing2 = new Sprite("drone_wing2");
    private static Sprite deathExplosion = new Sprite("plasma/explosion3");

    Controller controller = this;

    class Drone extends Actor {

        Unit target = controller;
        boolean sitting = true;
        int place;
        float wings = Main.R.nextFloat() * 10, speed = 400;

        public Drone(float x, float y, float angle, int place) {
            super(x, y, angle);
            this.place = place;
            z = 15;
        }

        @Override
        public void act(float delta) {
            wings += delta * 5;
            float tx, ty;
            //z = Math.min(120, z + delta * 30);
            if (controller.hp > 0) {
                if (target != controller
                        && (room.players[controller.owner].isEnemy(target.owner)
                        ? (target.hp <= 0)
                        : (target.hp <= 0 || target.hp == target.maxHp))) {
                    sitting = false;
                    target = controller;
                }
                if (target == controller) {
                    Point p = controller.getPlace(place);
                    tx = p.x;
                    ty = p.y;
                } else {
                    tx = target.x;
                    ty = target.y;
                }
                if (sitting) {
                    //z = Math.max(15, z - delta * 60);
                    x = tx;
                    y = ty;
                    angle = (angle * 19 + target.angle) / 20;
                    if (room.players[controller.owner].isEnemy(target.owner)) {
                        target.hit(delta * 20, controller);
                        if (Main.R.nextFloat() < delta * 3) {
                            room.add(new Explosion(x + (0.5f - Main.R.nextFloat()) * target.width,
                                    y + (0.5f - Main.R.nextFloat()) * target.width,
                                    200, deathExplosion, 5, 60));
                        }
                    } else {
                        target.hp = Math.min(target.maxHp, target.hp + delta * 5);
                    }
                } else {
                    wings += delta * 60;
                    float angle = atan2(ty - y, tx - x);
                    if (dist(x, y, tx, ty) < speed * delta) {
                        sitting = true;
                    }
                    this.angle = angle;
                    x += cos(angle) * speed * delta;
                    y += sin(angle) * speed * delta;
                }
            } else {
                sitting = false;
                wings += delta * 60;
                float angle = Main.R.nextFloat() * 6.28f;
                if (dist(0, 0, vx + cos(angle) * 40, vy + sin(angle) * 40)
                        < speed) {
                    vx += cos(angle) * 30;
                    vy += sin(angle) * 30;
                }
                this.angle = atan2(vy, vx);
                x += delta * vx;
                y += delta * vy;
                if (dist(x, y, controller.x, controller.y) > 600) {
                    remove();
                    room.add(new Explosion(x, y, 200, deathExplosion, 35, 120));
                }
            }
        }

        @Override
        public void render(Batch batch, float delta) {
            droneSprite.x = x;
            droneSprite.y = y;
            droneSprite.angle = angle;
            droneSprite.draw(batch);
            Sprite wing = droneSpriteWing;
            if (!sitting) {
                wing = droneSpriteWing2;
            }
            wing.x = x;
            wing.y = y;
            wing.angle = angle + cos(wings) + 0.6f;
            wing.draw(batch);
            wing.height = -wing.height;
            wing.angle = angle - cos(wings) - 0.6f;
            wing.draw(batch);
            wing.height = -wing.height;
        }

        @Override
        public void renderShadow(Batch batch, float delta) {
            droneSpriteShadow.x = x;
            droneSpriteShadow.y = y + z;
            droneSpriteShadow.angle = angle;
            droneSpriteShadow.a = (135f - z) / 135f;
            droneSpriteShadow.draw(batch);
        }
    }

    public Point getPlace(int number) {
        float x, y;
        switch (number) {
            case 0:
                x = 43;
                y = 26;
                break;
            case 1:
                x = 43;
                y = -26;
                break;
            case 2:
                x = -34;
                y = 26;
                break;
            case 3:
                x = -34;
                y = -26;
                break;
            default:
                x = y = 0;
        }
        float ox = this.x + x * cos(angle) - y * sin(angle);
        float oy = this.y + x * sin(angle) + y * cos(angle);
        return new Point(ox, oy);
    }

    public Controller(float xcord, float ycord, float angle, int owner) {
        super(xcord, ycord, angle, owner);
        weapons = new Weapon[]{};
        setSprite("controller");
        width = height = 75;
        mass = 40;
        hp = maxHp = 600;
        speed = 100;
        turnSpeed = 2;
    }

    Drone[] drones;

    @Override
    public void tick(float delta) {
        ai();
        boolean b = true;
        if (drones != null) {
            for (Drone d : drones) {
                
                if (d.target == controller) {
                    b = false;
                    break;
                }
            }
            if (b) {
                return;
            }
            for (Actor a : room.actors) {
                if (a != this && (a instanceof Unit)) {
                    Unit u = (Unit) a;
                    float d = dist(x, y, u.x, u.y);
                    if (d < 1000) {
                        if (room.players[owner].isEnemy(u.owner) || u.hp < u.maxHp) {
                            Drone fdr = null;
                            for (Drone dr : drones) {
                                if (dr.target == controller) {
                                    fdr = dr;
                                }
                                if (dr.target == u) {
                                    u = null;
                                }
                            }
                            if (u != null && fdr != null) {
                                if (!room.players[owner].isEnemy(u.owner) || d < 400) {
                                    fdr.sitting = false;
                                    fdr.target = u;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void act(float delta) {
        if (drones == null) {
            Drone[] drones = new Drone[4];
            for (int i = 0; i < 4; i++) {
                Point p = getPlace(i);
                drones[i] = new Drone(p.x, p.y, angle, i);
                room.add(drones[i]);
            }
            this.drones = drones;
        }
        super.act(delta);
    }

}
