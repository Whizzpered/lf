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
package com.lobseek.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.game.Main;
import com.lobseek.game.components.Actor;
import com.lobseek.game.components.Sprite;
import com.lobseek.game.components.Unit;
import com.lobseek.game.units.Disruptor;

import static com.lobseek.utils.Math.*;

/**
 *
 * @author Yew_Mentzaki
 */
public class Base extends Actor {

    public int owner = 1;
    float animation, animation2, timer, platformAngle;
    Unit inQueue;
    public static Sprite MMS = new Sprite("minimap/base");

    Sprite sprite = new Sprite("base_hand"),
            sprite_shadow = new Sprite("base_hand_shadow"),
            sprite_team = new Sprite("base_hand_team"),
            underground = new Sprite("base_underground"),
            platform = new Sprite("base_platform"),
            platform_shadow = new Sprite("base_platform_shadow");

    public Base(float x, float y, int owner) {
        super(x, y, Main.R.nextFloat() * 6.28f);
        this.owner = owner;
        z = 1;
        width = 200;
        height = 200;
        mass = 10;
        minimapSprite = MMS;
        standing = true;
    }

    @Override
    public void minimapRender(Batch batch, float delta) {
        minimapSprite.setColor(room.players[owner].color);
        super.minimapRender(batch, delta);
    }

    @Override
    public void act(float delta) {
        platformAngle -= delta / 4;
        if (inQueue != null) {
            inQueue.angle = platformAngle;
            if (animation < 1) {
                animation = Math.min(1, animation + delta / 2);
            } else if (animation2 <= 1) {
                animation2 = Math.min(1, animation2 + delta / 2);
                inQueue.x = x + cos(platformAngle) * animation2 * 200;
                inQueue.y = y + sin(platformAngle) * animation2 * 200;
            }
            if (animation2 == 1) {
                inQueue.tx = x + cos(platformAngle) * 300;
                inQueue.ty = y + sin(platformAngle) * 300;
                room.add(inQueue);
                inQueue = null;
            }
        } else if (animation2 > 0) {
            animation2 = Math.max(0, animation2 - delta / 2);
        } else if (animation > 0) {
            animation = Math.max(0, animation - delta / 2);
        } else {
            platformAngle = Main.R.nextFloat() * PI * 2;
            inQueue = room.players[owner].spawn(x, y, angle);
            if (inQueue != null) {
                inQueue.room = room;
                inQueue.create();
            }
        }
    }

    @Override
    public void tick(float delta) {
        handleCollision(delta);
    }

    @Override
    public void renderShadow(Batch batch, float delta) {
        float hands = 16;
        float sp = PI * 2 / hands;
        if (inQueue != null) {
            inQueue.renderShadow(batch, delta);
        }
        for (int i = 0; i < hands; i++) {
            sprite_shadow.x = x - cos(sp * ((float) i) + angle + animation)
                    * (90 + 20 * sqrt(animation));
            sprite_shadow.y = y - sin(sp * ((float) i) + angle + animation)
                    * (90 + 20 * sqrt(animation)) + 15 - animation * 12.5f;
            sprite_shadow.angle = sp * ((float) i + animation)
                    + animation * PI / 2 + angle;
            sprite_shadow.draw(batch);
        }
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
            sprite.angle = sp * ((float) i + animation)
                    + animation * PI / 2 + angle;
            sprite.draw(batch);
        }
        if (animation == 1) {

            platform_shadow.x = x + cos(platformAngle) * (animation2 * 80);
            platform_shadow.y = y + sin(platformAngle) * (animation2 * 80)
                    + animation2 * 20;
            platform_shadow.angle = platformAngle;
            platform_shadow.r = platform_shadow.g = platform_shadow.b = 1;
            platform_shadow.draw(batch);
            platform_shadow.x = x + cos(platformAngle) * animation2 * 160;
            platform_shadow.y = y + sin(platformAngle) * animation2 * 160
                    + 20 - animation2 * 20;
            platform_shadow.draw(batch);

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
