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
public class Sprite {
    public final String name;
    private com.badlogic.gdx.graphics.g2d.Sprite sprite;
    boolean loaded;

    public Sprite(String name) {
        this.name = name;
    }
    
    public boolean load(){
        if(loaded)return sprite == null;
        if(sprite == null)
    }
    
}
