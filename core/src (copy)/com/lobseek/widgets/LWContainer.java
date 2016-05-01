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
package com.lobseek.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.decimated.components.Touch;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yew_mentzaki
 */
public class LWContainer extends LWidget {

    private List<LWidget> widgets = new ArrayList<LWidget>();

    public void add(LWidget w) {
        widgets.add(w);
        w.parent = this;
    }

    public void remove(LWidget w) {
        widgets.remove(w);
        w.parent = null;
    }

    @Override
    public void act(float delta) {
        for (int i = 0; i < widgets.size(); i++) {
            LWidget w = widgets.get(i);
            if (w != null) {
                w.act(delta);
            }
        }
    }

    @Override
    public void show() {
        if (!visible) {
            visible = true;
            for (int i = 0; i < widgets.size(); i++) {
                LWidget w = widgets.get(i);
                if (w != null) {
                    w.show();
                }
            }
        }
    }

    @Override
    public void hide() {
        if (visible) {
            visible = false;
            for (int i = 0; i < widgets.size(); i++) {
                LWidget w = widgets.get(i);
                if (w != null) {
                    w.hide();
                }
            }
        }
    }

    @Override
    public void draw(Batch b) {
        for (int i = 0; i < widgets.size(); i++) {
            LWidget w = widgets.get(i);
            if (w != null) {
                w.draw(b);
            }
        }
    }

    @Override
    public boolean checkTapDown(Touch t) {
        for (int i = widgets.size() - 1; i >= 0; i--) {
            LWidget w = widgets.get(i);
            if (w != null && w.checkTapDown(t)) {
                w.tapDown(t);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkSwipe(Touch t) {
        for (int i = widgets.size() - 1; i >= 0; i--) {
            LWidget w = widgets.get(i);
            if (w != null && w.checkSwipe(t)) {
                w.swipe(t);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkTapUp(Touch t) {
        for (int i = widgets.size() - 1; i >= 0; i--) {
            LWidget w = widgets.get(i);
            if (w != null && w.checkTapUp(t)) {
                w.tapUp(t);
                return true;
            }
        }
        return false;
    }
}
