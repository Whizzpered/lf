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

import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.game.Main;

/**
 *
 * @author Yew_Mentzaki
 */
public class Barricade extends Actor{

    Sprite sprite;
    static final int decorations = 10;
    
    public Barricade(float x, float y) {
        super(x, y, 0);
        sprite = new Sprite("decorations/" + Main.R.nextInt(decorations));
        mass = 10;
        width = 70;
        height = 70;
        standing = true;
    }

    @Override
    public void render(Batch batch, float delta) {
        sprite.x = x;
        sprite.y = y;
        sprite.draw(batch);
    }
    
}
