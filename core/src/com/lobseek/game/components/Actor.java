/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.game.components;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 *
 * @author aenge
 */
public class Actor implements Comparable<Actor> {

    public float x, y, z, angle;
    public float width, height;
    boolean removed;
    public Room room;

    /*
    * @param x abscissa of actor
    * @param y ordinate of actor
    * @param angle direction of actor in radians
    */
    public Actor(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
 
    /*
    * Removes this actor from his room.
    */
    public void remove() {
        removed = true;
    }
    
    /*
    * Being invoked when this actor is added to the room.
    */
    public void create(){
    }

    /*
    * Compares this actor to other one to sort them by Z position.
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

    /*
    * Renders this actor in his room. Can be avoked only when actor of part of
    * actor is on the screen.
    * @param batch instance of SpriteBatch with all camera projections
    * @param delta time between frames in seconds
    */
    public void render(Batch batch, float delta) {
        
    }

    /*
    * Avokes every 10 milliseconds, here must be handled movement and other simple
    * activities. Cycles here may make game slower.
    * @param delta time between frames in seconds
    */
    public void act(float delta) {

    }

    /*
    * Avokes every 50 milliseconds, a cycles and other kinds of shit must be here.
    * @param delta time between frames in seconds
    */
    public void tick(float delta) {

    }
}
