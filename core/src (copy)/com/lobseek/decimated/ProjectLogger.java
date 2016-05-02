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
package com.lobseek.decimated;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.PrintStream;
import java.util.Date;

/**
 *
 * @author Yew_Mentzaki
 */
public class ProjectLogger {

    public static String[] internalLog = new String[128];

    private static void put(Object p) {
        for (int i = internalLog.length - 1;
                i > 0; i--) {
            internalLog[i] = internalLog[i - 1];
        }
        internalLog[0] = p.toString();
    }

    public static void println(Object p) {
        if (p instanceof Exception) {
            Exception e = (Exception) p;
            System.out.println(e.toString());
            put(e.toString());
            for (StackTraceElement ste : e.getStackTrace()) {
                System.out.println("    " + ste.toString());
                put("    " + ste.toString());
            }
        } else {
            System.out.println(p.toString());
            put(p);
        }
    }
}
