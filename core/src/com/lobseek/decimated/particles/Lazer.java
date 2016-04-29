/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.decimated.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.decimated.components.Point;
import com.lobseek.decimated.components.Unit;

/**
 *
 * @author Whizzpered
 */
public class Lazer {

    int lifeTime;
    float tx,ty;
    Unit from;

    protected static void drawLine(float fx, float fy, float tx, float ty, float width, Color color, Batch batch) {
        float length = (float) Math.sqrt(Math.pow(tx - fx, 2) + Math.pow(ty - fy, 2));
        float dx = tx - fx;
        float dy = ty - fy;
        float angle = (float) Math.atan2(dy, dx);
        float a2 = (float) (angle + Math.PI / 2);
        batch.setColor(color);
        /*batch.draw(MyGdxGame.PIXEL, fx - (float) Math.cos(a2) * width / 2, fy - (float) Math.sin(a2) * width / 2,
         0f, 0f, length, width,
         1f, 1f, (float) (180f / Math.PI * angle), 0, 0, MyGdxGame.PIXEL.getWidth(),
         MyGdxGame.PIXEL.getHeight(), false, false);*/
        //HERE IS A DRAWING OF PIXEL SPRITE. ADD TO THIS LINK ON IT
    }

    public void draw(Point camera, Batch batch) {

        // PLEASE INSERT VIEWPORT_WIDTH and HEIGHT here!
        
        if (lifeTime > 0) {
            Color y = new Color(1, 1, 0, lifeTime / 8);
            for (int i = 0; i < 4; i++) {
                //drawLine(from.x - camera.x, from.y - camera.y, tx - camera.x, ty - camera.y, (16f * i - lifeTime * 12f * i) * Game.VIEWPORT_WIDTH, y, batch);
            }
            Color w = new Color(1, 1, 1, lifeTime);
            //drawLine(from.x - camera.x, from.y - camera.y, tx - camera.x, ty - camera.y, lifeTime * 8f * Game.VIEWPORT_WIDTH, w, batch);
        }
    }
}
