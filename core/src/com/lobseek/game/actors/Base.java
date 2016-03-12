/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.game.Main;
import com.lobseek.game.components.Actor;
import com.lobseek.game.components.Sprite;
import com.lobseek.game.components.Unit;

import static com.lobseek.utils.Math.*;

/**
 *
 * @author Yew_Mentzaki
 */
public class Base extends Actor {

    public int owner = 1;
    float animation, animation2, timer, platformAngle;
    Unit inQueue;

    Sprite sprite = new Sprite("base_hand"),
            sprite_team = new Sprite("base_hand_team"),
            underground = new Sprite("base_underground"),
            platform = new Sprite("base_platform");

    public Base(float x, float y, float angle) {
        super(x, y, angle);
        z = 1;
        width = 200;
        height = 200;
        mass = 10;
    }

    @Override
    public void kick(float dist, float angle) {
    }

    @Override
    public void act(float delta) {
        platformAngle -= delta / 4;
        if (inQueue != null) {
            inQueue.angle = platformAngle;
            if (animation < 1) {
                animation = Math.min(1, animation + delta / 2);
            } else if (animation2 < 1) {
                animation2 = Math.min(1, animation2 + delta / 2);
                inQueue.x = x + cos(platformAngle) * animation2 * 200;
                inQueue.y = y + sin(platformAngle) * animation2 * 200;
            } else {
                inQueue.tx = x + cos(platformAngle) * 300;
                inQueue.ty = y + sin(platformAngle) * 300;
                room.add(inQueue);
                inQueue = null;
            }
        } else {
            if (animation2 > 0) {
                animation2 = Math.max(0, animation2 - delta / 2);
            } else if (animation > 0) {
                animation = Math.max(0, animation - delta / 2);
            } else {
                platformAngle = Main.R.nextFloat() * PI * 2;
                inQueue = new Unit(x, y, platformAngle, 1);
                inQueue.room = room;
            }
        }
    }

    @Override
    public void tick(float delta) {
        handleCollision(delta);
    }

    @Override
    public void render(Batch batch, float delta) {
        float hands = 16;
        float sp = PI * 2 / hands;
        underground.x = x;
        underground.y = y;
        underground.angle = angle;
        underground.r = underground.g = underground.b = animation;
        underground.width = 180;
        underground.height = 180;
        underground.draw(batch);
        if (animation < 1) {
            platform.x = x;
            platform.y = y;
            platform.angle = platformAngle;
            platform.r = platform.g = platform.b = animation;
            platform.draw(batch);
            if (inQueue != null) {
                inQueue.render(batch, delta, new Color(animation, animation,
                        animation, 1));
            }
        }
        for (int i = 0; i < hands; i++) {
            Sprite sprite;
            if (i == hands - 1) {
                sprite = sprite_team;
                sprite.setColor(room.players[owner].color);
            } else {
                sprite = this.sprite;
            }
            sprite.x = x - cos(sp * ((float) i) + angle + animation)
                    * (90 + 20 * sqrt(animation));
            sprite.y = y - sin(sp * ((float) i) + angle + animation)
                    * (90 + 20 * sqrt(animation));
            sprite.angle = sp * ((float) i + angle + animation)
                    + animation * PI / 2;
            sprite.draw(batch);
        }
        if (animation == 1) {
            platform.x = x + cos(platformAngle) * (animation2 * 80);
            platform.y = y + sin(platformAngle) * (animation2 * 80);
            platform.angle = platformAngle;
            platform.r = platform.g = platform.b = 1;
            platform.draw(batch);
            platform.x = x + cos(platformAngle) * animation2 * 160;
            platform.y = y + sin(platformAngle) * animation2 * 160;
            platform.draw(batch);
            if (inQueue != null) {
                inQueue.render(batch, delta);
            }
        }
    }
}
