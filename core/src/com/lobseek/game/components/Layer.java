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
public interface Layer {

    /**
     * Renders layer.
     *
     * @param delta
     */
    public void render(float delta);

    /**
     * Called when the screen was touched or a mouse button was pressed. The
     * button parameter will be {@link Buttons#LEFT} on iOS.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event
     * @param button the button
     * @return whether the input touched anything
     */
    public boolean touchDown(int screenX, int screenY, int pointer, int button);

    /**
     * Called when a finger was lifted or a mouse button was released. The
     * button parameter will be {@link Buttons#LEFT} on iOS.
     *
     * @param pointer the pointer for the event
     * @param button the button
     * @return whether the input touched anything
     */
    public boolean touchUp(int screenX, int screenY, int pointer, int button);

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param pointer the pointer for the event
     * @return whether the input touched anything
     */
    public boolean touchDragged(int screenX, int screenY, int pointer);

    public boolean scrolled(int amount);
    
}
