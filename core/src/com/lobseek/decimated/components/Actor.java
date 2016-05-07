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
package com.lobseek.decimated.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import static com.lobseek.utils.Math.*;

/**
 *
 * @author Yew_Mentzaki
 */
public class Actor implements Comparable<Actor> {

    public float x, y, z, vx, vy, angle;
    public float width, height, mass;
    boolean removed, created;
    public boolean phantom, standing;
    public Room room;
    public Sprite minimapSprite;
    public boolean onScreen;

    /**
     * @param x abscissa of actor
     * @param y ordinate of actor
     * @param angle direction of actor in radians
     */
    public Actor(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    /**
     * Removes this actor from his room.
     */
    public void remove() {
        removed = true;
    }

    /**
     * Being invoked when this actor is added to the room.
     */
    public void create() {
        created = true;
    }
    
    /**
     * Handles movement.
     */
    public void move(){
        x += vx;
        y += vy;
        vx = vy = 0;
    }
    
    public void move(float x, float y){
        vx += x;
        vy += y;
    }

    /**
     * Compares this actor to other one to sort them by Z position.
     *
     * @param actor other actor.
     * @return standard comparing value
     * @see java.lang.Comparable
     */
    @Override
    public int compareTo(Actor actor) {
        if (z > actor.z) {
            return 1;
        } else if (z < actor.z) {
            return -1;
        } else {
            return 0;
        }
    }
    

    /**
     * Renders shadow this actor in his room. Can be avoked only when actor of part of
     * actor is on the screen.
     *
     * @param batch instance of SpriteBatch with all camera projections
     * @param delta time between frames in seconds
     */
    public void renderShadow(Batch batch, float delta) {

    }


    /**
     * Renders this actor in his room. Can be avoked only when actor of part of
     * actor is on the screen.
     *
     * @param batch instance of SpriteBatch with all camera projections
     * @param delta time between frames in seconds
     */
    public void render(Batch batch, float delta) {

    }

    /**
     * Renders GUI elements for this actor in his room. Can be avoked only when actor of part of
     * actor is on the screen.
     *
     * @param batch instance of SpriteBatch with all camera projections
     * @param delta time between frames in seconds
     */
    public void renderInterface(Batch batch, float delta) {

    }

    public void minimapRender(Batch batch, float delta, float alpha) {
        if (minimapSprite != null) {
            minimapSprite.x = room.screen.width + (x / room.size * 72f) - 86;
            minimapSprite.y = room.screen.height + (y / room.size * 72f) - 86;
            minimapSprite.a = alpha;
            minimapSprite.angle = angle;
            minimapSprite.draw(batch);
        }
    }

    /**
     * Avokes every 10 milliseconds, here must be handled movement and other
     * simple activities. Cycles here may make game slower.
     *
     * @param delta time between acts in seconds
     */
    public void act(float delta) {

    }

    /**
     * Avokes every 50 milliseconds, a cycles and other kinds of shit must be
     * here.
     *
     * @param delta time between tick in seconds
     */
    public void tick(float delta) {

    }

    public void kick(float dist, float angle) {
        if (!standing) {
            vx += cos(angle) * dist;
            vy += sin(angle) * dist;
        }
    }

    public void handleCollision(float delta) {
        for (Actor a : room.actors) {
            if (a != null && !a.phantom && a != this) {
                float size = (Math.max(width, height) + Math.max(a.width, a.height));
                if (abs(x + vx - a.x - a.vx) > size || abs(y + vy - a.y - a.vy) > size) {
                    continue;
                }
                float d = dist(x + vx, y + vy, a.x + a.vx, a.y + a.vy);
                float r = dist(0, 0, width + a.width, height + a.height) / 2 - 20;
                if (d < r) {
                    r -= d;
                    float angle = atan2(a.y + a.vy - y - vy, a.x + a.vx - x - vx);
                    if (a.mass == 0 || standing) {
                        a.kick(r, angle);
                    } else if (mass == 0 || a.standing) {
                        kick(r, angle + PI);
                    } else {
                        r /= 2;
                        float m = a.mass / mass;
                        a.kick(r / m, angle);
                        kick(r * m, angle + PI);
                    }
                }
            }
        }
    }
}
