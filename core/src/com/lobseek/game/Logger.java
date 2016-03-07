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
package com.lobseek.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.PrintStream;
import java.util.Date;

/**
 *
 * @author Yew_Mentzaki
 */
public class Logger {

    private static PrintStream console;
    private static FileHandle log;

    static void init() {
        console = System.out;
        FileHandle dir = Gdx.files.local(Main.NAME.toLowerCase());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        log = Gdx.files.local(dir.name() + '/' + new Date().toGMTString().
                replace(" ", "_").toLowerCase() + ".txt");
        PrintStream logger = new PrintStream(console) {

            @Override
            public void print(Object obj) {
                console.print(obj);
                log.writeString(obj.toString(), true);
            }

            @Override
            public void print(String s) {
                console.print(s);
                log.writeString(s, true);
            }

            @Override
            public void print(boolean b) {
                console.print(b);
                log.writeString(String.valueOf(b), true);
            }

            @Override
            public void print(char c) {
                console.print(c);
                log.writeString(c + "", true);
            }

            @Override
            public void print(char[] c) {
                console.print(c);
                log.writeString(new String(c), true);
            }

            @Override
            public void print(double v) {
                console.print(v);
                log.writeString(String.valueOf(v), true);
            }

            @Override
            public void print(float v) {
                console.print(v);
                log.writeString(String.valueOf(v), true);
            }

            @Override
            public void print(int v) {
                console.print(v);
                log.writeString(String.valueOf(v), true);
            }

            @Override
            public void print(long v) {
                console.print(v);
                log.writeString(String.valueOf(v), true);
            }

            @Override
            public void println(Object obj) {
                console.println(obj);
                log.writeString(obj.toString() + '\n', true);
            }

            @Override
            public void println(String s) {
                console.println(s);
                log.writeString(s + '\n', true);
            }

            @Override
            public void println(boolean b) {
                console.println(b);
                log.writeString(String.valueOf(b) + '\n', true);
            }

            @Override
            public void println(char c) {
                console.println(c);
                log.writeString(c + "\n", true);
            }

            @Override
            public void println(char[] c) {
                console.println(c);
                log.writeString(new String(c) + '\n', true);
            }

            @Override
            public void println(double v) {
                console.println(v);
                log.writeString(String.valueOf(v) + '\n', true);
            }

            @Override
            public void println(float v) {
                console.println(v);
                log.writeString(String.valueOf(v) + '\n', true);
            }

            @Override
            public void println(int v) {
                console.println(v);
                log.writeString(String.valueOf(v) + '\n', true);
            }

            @Override
            public void println(long v) {
                console.println(v);
                log.writeString(String.valueOf(v) + '\n', true);
            }

            @Override
            public void println() {
                console.println();
                log.writeString("\n", true);
            }
        };
        System.setOut(logger);
    }
}
