/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.game.Main;
import com.lobseek.game.components.Actor;
import com.lobseek.game.components.Sprite;

/**
 *
 * @author aenge
 */
public class Test extends Actor{

    Sprite sprite = new Sprite("builder_body");
    public Test(float x, float y, float angle) {
        super(x, y, angle);
        z = 10;
//        width = 75;
//        height = 75;
    }

    @Override
    public void act(float delta) {
    x += (float)Math.cos(angle) * delta * 100;
    y += (float)Math.sin(angle) * delta * 100;
    angle += (Main.R.nextFloat() - 0.5f) * 50f * delta;
    }

    @Override
    public void render(Batch batch, float delta) {
        sprite.x = x;
        sprite.y = y;
        sprite.angle = angle;
        sprite.draw(batch);
        
        
        
    }
    
}
