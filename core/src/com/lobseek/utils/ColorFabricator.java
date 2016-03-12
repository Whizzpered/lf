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
package com.lobseek.utils;

import com.badlogic.gdx.graphics.Color;
import static java.lang.Math.*;

/**
 *
 * @author Yew_Mentzaki
 */
public class ColorFabricator {

    public static Color neon(float alpha) {
        return new Color(
                max(0, alpha - 0.7f) * 0.5f,
                min(1, alpha * 2),
                min(1, max(0, alpha * 2 - 0.5f)) * 0.5f,
                alpha
        );
    }
}
