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
package com.lobseek.game.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.game.Main;
import com.lobseek.utils.ColorFabricator;
import static com.lobseek.utils.Math.*;
import static java.lang.Math.max;

/**
 *
 * @author Yew_Mentzaki
 */
public class Unit extends Actor {

    public int owner;
    public float speed, turnSpeed, selectionAlpha;
    public float tx, ty, experience = 10;
    public float hp, maxHp;
    protected float deathTimer;
    boolean selected;
    public Weapon weapons[];
    public boolean flying;

    protected static final Sprite selection = new Sprite("selection");
    protected Sprite body, body_team, body_shadow;
    public static Sprite MMS = new Sprite("minimap/unit");

    public Unit(float x, float y, float angle, int owner) {
        super(x, y, angle);
        tx = x;
        ty = y;
        z = 10;
        this.owner = owner;
        minimapSprite = MMS;
    }

    @Override
    public void minimapRender(Batch batch, float delta) {
        minimapSprite.setColor(room.players[owner].color);
        super.minimapRender(batch, delta);
    }

    @Override
    public void create() {
        room.players[owner].units++;
        super.create();
        if (weapons != null) {
            for (int i = 0; i < weapons.length; i++) {
                weapons[i].on = this;
                weapons[i].room = room;
                weapons[i].angle = angle;
                weapons[i].create();
            }
        }
    }

    public void hit(float hp, Unit from) {
        if (this.hp > 0 && this.hp - hp <= 0) {
            from.experience += this.experience / 2;
            this.experience = 0;
        }
        this.hp = max(0, this.hp - hp);
    }

    public void move(float delta) {
        if (speed > 0 && turnSpeed > 0) {
            if (x != tx && y != ty) {
                float ta = (float) atan2(ty - y, tx - x);
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
                if (angle == ta || speed >= 200) {
                    float d = (float) sqrt(pow(tx - x, 2) + pow(ty - y, 2));
                    if (d > speed * delta) {
                        d = speed * delta;
                    }
                    move(d * cos(angle), d * sin(angle));
                }
            }
        }
    }

    public void setSprite(String name) {
        body = new Sprite(name + "_body");
        body_team = new Sprite(name + "_body_team");
        body_shadow = new Sprite(name + "_body_shadow");
        if (weapons != null) {
            for (int i = 0; i < weapons.length; i++) {
                weapons[i].setSprite(name);
            }
        }
    }

    @Override
    public void act(float delta) {
        if (selected && hp > 0) {
            selectionAlpha = Math.min(1, selectionAlpha + delta * 2);
        } else {
            selectionAlpha = Math.max(0, selectionAlpha - delta * 2);
        }
        if (hp > 0) {
            move();
            move(delta);
            handleCollision(delta);
            handleBarricadeCollision(delta);

            for (int i = 0; i < weapons.length; i++) {
                weapons[i].act(delta);
            }
        } else {
            deathTimer += delta / 2;
            if (deathTimer >= 1) {
                room.players[owner].units--;
                remove();
            }
        }
    }

    public void handleBarricadeCollision(float delta) {
        if(!flying){
        Barricade b1 = room.getBarricade(x, y);
        handleBarricadeCollision(b1);
        Barricade b2 = room.getBarricade(x + width / 2, y);
        if (b2 != b1) {
            handleBarricadeCollision(b2);
        }
        Barricade b3 = room.getBarricade(x - width / 2, y);
        if (b3 != b1 && b3 != b2) {
            handleBarricadeCollision(b3);
        }
        Barricade b4 = room.getBarricade(x, y - height / 2);
        if (b4 != b1 && b4 != b2 && b4 != b3) {
            handleBarricadeCollision(b4);
        }
        Barricade b5 = room.getBarricade(x, y + height / 2);
        if (b5 != b1 && b5 != b2 && b5 != b3 && b5 != b4) {
            handleBarricadeCollision(b5);
        }
        }
    }

    private void handleBarricadeCollision(Barricade b) {
        if (b != null) {
            if (abs(x - b.x) > width + 20 || abs(y - b.y) > height + 20) {
                return;
            }
            float d = dist(x, y, b.x, b.y);
            float r = dist(0, 0, width + b.width, height + b.height) / 2 - 20;
            if (d < r) {
                r -= d;
                float angle = atan2(b.y - y, b.x - x);
                kick(r, angle + PI);
                b.kick(0, angle);
            }
        }
    }

    @Override
    public void tick(float delta) {
        for (int i = 0; i < weapons.length; i++) {
            weapons[i].tick(delta);
        }
    }

    @Override
    public void render(Batch batch, float delta) {
        render(batch, delta, Color.WHITE);
    }

    @Override
    public void renderShadow(Batch batch, float delta) {
        body_shadow.x = x;
        body_shadow.y = y + 15;
        body_shadow.angle = angle;
        body_shadow.a = Math.max(0, 1 - deathTimer);
        body_shadow.draw(batch);
        body_shadow.y = y + 7.5f;
        body_shadow.draw(batch);
        if(weapons != null)
        for (Weapon w : weapons){
            w.renderShadow(batch, delta);
        }
    }

    public void render(Batch batch, float delta, Color parentColor) {
        parentColor = new Color(parentColor.r, parentColor.g,
                parentColor.b, parentColor.a * Math.max(0, 1 - deathTimer));
        body.x = x;
        body.y = y;
        body.angle = angle;
        body.setColor(parentColor);
        body.draw(batch);
        body_team.x = x;
        body_team.y = y;
        body_team.angle = angle;
        body_team.setColor(room.players[owner].color);
        body_team.r *= parentColor.r;
        body_team.g *= parentColor.g;
        body_team.b *= parentColor.b;
        body_team.a *= parentColor.a;
        body_team.draw(batch);
        if (selectionAlpha > 0) {
            selection.x = x;
            selection.y = y;
            selection.angle = atan2(ty - y, tx - x);
            selection.setColor(ColorFabricator.neon(selectionAlpha));
            selection.draw(batch);
        }
        for (int i = 0; i < weapons.length; i++) {
            weapons[i].render(batch, delta, parentColor);
        }
    }
}
