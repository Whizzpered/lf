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

/**
 *
 * @author Yew_Mentzaki
 */
public class Particle {

    public float x, y, radius;
    long creationTime = System.currentTimeMillis();
    boolean removed;
    public Room room;
    final int lifeTime;

    /**
     * @param x
     * @param y
     * @param lifeTime time of particle life in milliseconds. May not be bigger
     * than 10'000 milliseconds.
     */
    public Particle(float x, float y, int lifeTime) {
        this.x = x;
        this.y = y;
        this.lifeTime = Math.min(lifeTime, 10000);
    }

    /**
     * Count time that left for this particle.
     *
     * @return time in milliseconds that particle will be alive.
     */
    public int timeLeft() {
        if (removed) {
            return -1000;
        }
        return (int) (creationTime + lifeTime - System.currentTimeMillis());
    }

    public float lightness() {
        if (timeLeft() <= 0) {
            return 0;
        } else {
            return (float) timeLeft() / (float) lifeTime;
        }
    }

    /**
     * Called when particle is added to its room.
     */
    public void create() {

    }

    /**
     * Called if particle must be removed immidiately.
     */
    public void remove() {
        removed = true;
    }

    /**
     * Avokes every 10 milliseconds, here must be handled movement and other
     * simple activities. Remember, this is fucking particle and it <b>must
     * not</b>
     * count factorial function, write files or make coffee. It's just particle:
     * piece of dirt, smoke cloud, rocket trace or anything other, simple object
     * that must be handled quickly.
     *
     * @param delta time between acts in seconds
     */
    public void act(float delta) {

    }

    /**
     * Renders particle when it's on screen.
     *
     * @param delta time between tick in seconds
     */
    public void render(Batch batch, float delta) {

    }

}
