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
package com.lobseek.decimated.screens;

import com.lobseek.decimated.actors.Base;
import com.lobseek.decimated.actors.Test;
import com.lobseek.decimated.components.Room;
import com.lobseek.decimated.components.Touch;
import com.lobseek.decimated.gui.Button;
import com.lobseek.decimated.gui.Image;
import com.lobseek.decimated.units.Disruptor;
import static com.lobseek.utils.Math.*;
import com.lobseek.widgets.LWAlignment;
import com.lobseek.widgets.LWContainer;

/**
 *
 * @author Yew_Mentzaki
 */
public class GameScreen extends Screen {

    Room room;
    public LWContainer menu;

    void beginGame() {
        menu.hide();
        if (room == null) {
            room = new Room(this, 1024, 64);
            room.player = 1;
            add(room);
            room.add(new Base(600, 2000, 1));
            room.add(new Base(-600, 2000, 1));
            room.add(new Base(-0, 2000, 1));
            room.add(new Base(600, -2000, 2));
            room.add(new Base(-600, -2000, 2));
            room.add(new Base(-0, -2000, 2));
            room.add(new Base(2000, 600, 3));
            room.add(new Base(2000, -600, 3));
            room.add(new Base(2000, 0, 3));
            room.add(new Base(-2000, 600, 4));
            room.add(new Base(-2000, -600, 4));
            room.add(new Base(-2000, 0, 4));
        }
        room.pause();
    }

    public GameScreen() {
        menu = new LWContainer() {
            @Override
            public boolean checkSwipe(Touch t) {

                super.checkSwipe(t);
                return isVisible();
            }

            @Override
            public boolean checkTapDown(Touch t) {
                super.checkTapDown(t);
                return isVisible();
            }

            @Override
            public boolean checkTapUp(Touch t) {
                super.checkTapUp(t);
                return isVisible();
            }

        };
        Image i = new Image("gui/logo");
        i.setAlign(LWAlignment.CENTER);
        i.y = 220;
        menu.add(i);
        Button play = new Button("menu.play") {
            @Override
            public void tapUp(Touch t) {
                beginGame();
            }

        };
        play.setAlign(LWAlignment.CENTER);
        play.y = 90 - 140 * 0;
        menu.add(play);
        play = new Button("menu.settings") {

        };
        play.setAlign(LWAlignment.CENTER);
        play.y = 90 - 140 * 1;
        menu.add(play);
        play = new Button("menu.exit") {
            @Override
            public void tapUp(Touch t) {
                System.exit(0);
            }

        };
        play.setAlign(LWAlignment.CENTER);
        play.y = 90 - 140 * 2;
        menu.add(play);
        add(menu);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

}
