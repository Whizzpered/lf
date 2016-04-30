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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.decimated.Main;
import com.lobseek.decimated.components.Particle;
import com.lobseek.decimated.components.Point;
import com.lobseek.decimated.components.Unit;
import com.lobseek.decimated.components.Weapon;
import static com.lobseek.utils.Math.*;

/**
 *
 * @author Whizzpered
 */
public class Lazer extends Particle {

    Weapon from;
    float length;

    protected static void drawLine(float fx, float fy, float tx, float ty, float width, Color color, Batch batch) {
        float length = (float) Math.sqrt(Math.pow(tx - fx, 2) + Math.pow(ty - fy, 2));
        float dx = tx - fx;
        float dy = ty - fy;
        float angle = (float) Math.atan2(dy, dx);
        float a2 = (float) (angle + Math.PI / 2);
        batch.setColor(color);
        batch.draw(Main.laser, fx - (float) Math.cos(a2) * width / 2, fy - (float) Math.sin(a2) * width / 2,
                0f, 0f, length, width,
                1f, 1f, (float) (180f / Math.PI * angle), 0, 0, Main.laser.getWidth(),
                Main.laser.getHeight(), false, false);
    }

    public Lazer(Weapon from, float x, float y, float tx, float ty) {
        super((x + tx) / 2, (y + ty) / 2, 1500);
        this.from = from;
        this.length = dist(x, y, tx, ty);
        this.radius = length / 2;
    }

    @Override
    public void render(Batch batch, float delta) {
        if (this.timeLeft() > 0) {
            Color w = new Color(1, 1, 1, pow(lightness(), 5));
            Point p = from.getWeaponPosition();
            float tx = p.x + cos(from.angle) * length;
            float ty = p.y + sin(from.angle) * length;
            x = (p.x + tx) / 2;
            y = (p.y + ty) / 2;
            drawLine(p.x, p.y, tx, ty, lightness() * 20, w, batch);
        } else {
            remove();
            return;
        }
    }
}
