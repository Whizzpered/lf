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

/**
 *
 * @author Yew_Mentzaki
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