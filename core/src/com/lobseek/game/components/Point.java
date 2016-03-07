/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.game.components;

/**
 *
 * @author aenge
 */
public class Point {
    public float x, y;

    /**
     * Point in center of coordinates.
     */
    public Point() {
    }

    /**
     * Point with some position.
     * @param x
     * @param y 
     */
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Copies this point.
     * @return copy of this point.
     */
    public Point getCopy() {
        return new Point(x, y);
    }
    
    /**
    * Sum this vector with other one.
    * 
    * @param p other vector.
    */
    public void add(Point p) {
        x += p.x;
        y += p.y;
    }
    
    /**
    * Removes from this vector other one.
    * 
    * @param p other vector.
    */
    public void remove(Point p) {
        x -= p.x;
        y -= p.y;
    }
    
    /**
    * Sum this vector with other one.
    * 
    * @param x position of other vector on X
    * @param y position of other vector on Y
    */
    public void add(float px, float py) {
        x += px;
        y += py;
    }
    
    /**
    * Removes from this vector other one.
    * 
    * @param x position of other vector on X
    * @param y position of other vector on Y
    */
    public void remove(float px, float py) {
        x -= px;
        y -= py;
    }
}