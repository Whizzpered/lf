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
package com.lobseek.decimated.particles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.decimated.Main;
import com.lobseek.decimated.components.Particle;
import com.lobseek.decimated.components.Sprite;

/**
 *
 * @author yew_mentzaki
 */
public class Explosion extends Particle{
    
    Sprite sprite;
    float resize;
    float angle;
    float start;
    public Explosion(float x, float y, int lifeTime, Sprite sprite, float start, float resize) {
        super(x, y, lifeTime);
        this.angle = Main.R.nextFloat() * 6.28f;
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
            sprite.angle = angle;
            sprite.draw(batch);
        }
    }
    
}
