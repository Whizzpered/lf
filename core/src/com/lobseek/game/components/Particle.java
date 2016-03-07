package com.lobseek.game.components;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 *
 * @author aenge
 */
public class Particle {

    public float x, y, radius;
    long creationTime = System.currentTimeMillis();
    boolean removed;
    public Room room;
    final int lifeTime;

    public Particle(float x, float y, int lifeTime) {
        this.x = x;
        this.y = y;
        this.lifeTime = Math.max(lifeTime, 10000);
    }

    public int timeLeft() {
        if(removed)return -1000;
        return (int) (creationTime + lifeTime - System.currentTimeMillis());
    }
    
    public void create(){
        
    }
    
    public void remove(){
        removed = true;
    }

    public void act(float delta) {

    }

    public void render(Batch batch, float delta) {

    }

}
