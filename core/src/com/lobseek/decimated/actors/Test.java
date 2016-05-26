/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.decimated.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.decimated.Main;
import com.lobseek.decimated.components.Actor;
import com.lobseek.decimated.components.Sprite;

import static com.lobseek.utils.Math.*;

/**
 *
 * @author Yew_Mentzaki
 */
public class Test extends Actor {

    float dangle, hangle, hdangle;

    Sprite sprite = new Sprite("disruptor_body");
    Sprite head_sprite = new Sprite("disruptor_head");

    public Test(float x, float y, float angle) {
        super(x, y, angle);
        z = 10;
        width = 75;
        height = 75;
        mass = 10;
    }

    @Override
    public void act(float delta) {
        x += cos(angle) * delta * 30;
        y += sin(angle) * delta * 30;
        dangle += (Main.R.nextFloat() - 0.5f) * 0.02f * delta;
        angle += dangle;
        hdangle += (Main.R.nextFloat() - 0.5f) * 0.02f * delta;
        hangle += hdangle;
    }

    @Override
    public void tick(float delta) {
        handleCollision(delta);
    }

    @Override
    public void render(Batch batch, float delta) {
        sprite.x = x;
        sprite.y = y;
        sprite.angle = angle;
        sprite.draw(batch);
        head_sprite.x = x;
        head_sprite.y = y;
        head_sprite.angle = hangle;
        head_sprite.draw(batch);
    }
}
