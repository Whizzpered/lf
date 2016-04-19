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
import static com.lobseek.utils.Math.*;

/**
 *
 * @author yew_mentzaki
 */
public class LWidget {

    public LWidget parent;
    public float width, height, x, y;
    private LWAlignment verticalAlignment = LWAlignment.LEFT,
            horisontalAlignment = LWAlignment.TOP;
    boolean visible = true;

    public void setAlign(LWAlignment a) {
        if (a == LWAlignment.LEFT || a == LWAlignment.RIGHT) {
            horisontalAlignment = a;
        } else if (a == LWAlignment.TOP || a == LWAlignment.BOTTOM) {
            verticalAlignment = a;
        } else if (a == LWAlignment.CENTER) {
            horisontalAlignment = verticalAlignment = a;
        }
    }

    public LWAlignment getAlign(LWAlignment type) {
        switch (type) {
            case HORISONTAL:
                return horisontalAlignment;
            case VERTICAL:
                return verticalAlignment;
        }
        return null;
    }

    public void setAlign(LWAlignment a, LWAlignment b) {
        if (a == LWAlignment.HORISONTAL) {
            a = b;
        }
        
        if (a == LWAlignment.LEFT || a == LWAlignment.RIGHT || a == LWAlignment.CENTER) {
            horisontalAlignment = a;
        }

        if (b == LWAlignment.TOP || b == LWAlignment.BOTTOM || b == LWAlignment.CENTER) {
            verticalAlignment = b;
        }
    }

    public float x() {
        float x = this.x;

        if (parent != null) {
            x += parent.x();
            if (horisontalAlignment == LWAlignment.LEFT) {
                x -= parent.width / 2;
            } else if (horisontalAlignment == LWAlignment.RIGHT) {
                x += parent.width / 2;
            }
        }

        return (int)x;
    }

    public float y() {
        float y = this.y;

        if (parent != null) {
            y += parent.y();
            if (verticalAlignment == LWAlignment.BOTTOM) {
                y -= parent.height / 2;
            } else if (verticalAlignment == LWAlignment.TOP) {
                y += parent.height / 2;
            }
        }

        return (int)y;
    }

    public void show() {
        if (!visible) {
            visible = true;
        }
    }

    public void hide() {
        if (visible) {
            visible = false;
        }
    }

    public void setVisible(boolean visible) {
        if (visible) {
            show();
        } else {
            hide();
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void act(float delta) {

    }

    public void draw(Batch b) {

    }

    public boolean checkTapDown(Touch t) {
        return handleTap(t);
    }

    public void tapDown(Touch t) {

    }

    public boolean checkSwipe(Touch t) {
        return handleTap(t);
    }

    public void swipe(Touch t) {

    }

    public boolean checkTapUp(Touch t) {
        return handleTap(t);
    }

    public void tapUp(Touch t) {

    }

    public boolean handleTap(Touch t) {
        return visible && (abs(t.x - x()) <= width / 2 && abs(t.y - y()) <= height / 2);
    }
}
