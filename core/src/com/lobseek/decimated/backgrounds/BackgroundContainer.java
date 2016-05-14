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
package com.lobseek.decimated.backgrounds;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 *
 * @author yew_mentzaki
 */
public class BackgroundContainer {

    public int x, y;
    private BackgroundObject[] bo = new BackgroundObject[50];
    private int i = 0;
    public float radius = 0;

    public BackgroundContainer(int x, int y) {
        this.x = x;
        this.y = y;
    }

    

    public void render(Batch b) {
        for (BackgroundObject bo1 : bo) {
            if (bo1 != null) {
                bo1.render(b);
            } else {
                return;
            }
        }
    }

    public void add(BackgroundObject o) {
        int i = this.i;
        bo[i] = o;
        i++;
        if (i >= bo.length) {
            i -= bo.length;
        }
        this.i = i;
        radius = Math.max(o.radius, radius);
    }
}
