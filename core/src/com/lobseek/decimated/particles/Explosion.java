/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.decimated.particles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.decimated.components.Particle;
import com.lobseek.decimated.components.Sprite;

/**
 *
 * @author yew_mentzaki
 */
public class Explosion extends Particle{
    
    Sprite sprite;
    float resize;
    float start;
    public Explosion(float x, float y, int lifeTime, Sprite sprite, float start, float resize) {
        super(x, y, lifeTime);
        this.sprite = sprite;
        this.start = start;
        this.resize = resize;
    }

    @Override
    public void render(Batch batch, float delta) {
        if(sprite == null){
            remove();
            return;
        }else{
            radius = sprite.width = sprite.height = start + (1 - lightness()) * resize;
            sprite.x = x;
            sprite.y = y;
            sprite.a = lightness();
            sprite.draw(batch);
        }
    }
    
}
