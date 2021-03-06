package com.lobseek.decimated.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.lobseek.decimated.Main;
import com.lobseek.utils.ColorFabricator;

/**
 *
 * @author yew_mentzaki
 */
public class Font {

    private static Sprite[] letters = new Sprite[Character.MAX_VALUE];
    private static Sprite[] letters_simple = new Sprite[Character.MAX_VALUE];
    private static boolean[] loaded = new boolean[Character.MAX_VALUE];
    public static TextureAtlas atlas;
    public static TextureAtlas atlas_simple;

    public static void draw(Object text, float x, float y, Color color, Batch batch) {
        if (text == null) {
            return;
        }

        char[] s = text.toString().toCharArray();
        for (char c : s) {
            if (letters[c] == null) {
                if (loaded[c]) {
                    continue;
                }
                letters[c] = atlas.createSprite(String.valueOf((int) c));
                letters_simple[c] = atlas_simple.createSprite(String.valueOf((int) c));
                loaded[c] = true;
                if (letters[c] == null) {
                    System.out.println("\"" + (int) c + "\" hasn't been loaded!");
                    continue;
                }
            }

            Sprite sp = (Main.contrast ? letters_simple[c] : letters[c]);
            sp.setPosition(x, y);
            float width = sp.getWidth();
            sp.setColor(color);
            sp.draw(batch);
            x += width;
        }
    }

    public static void draw(Object text, float x, float y, float width, Color color, Batch batch) {
        if (text == null) {
            return;
        }
        float cw = 0;
        int i = 0;
        int space = 0;
        String line = new String();

        char[] s = (' ' + text.toString()).toCharArray();
        for (char c : s) {
            if (letters[c] == null) {
                if (loaded[c]) {
                    continue;
                }
                letters[c] = atlas.createSprite(String.valueOf((int) c));
                letters_simple[c] = atlas_simple.createSprite(String.valueOf((int) c));
                loaded[c] = true;
                if (letters[c] == null) {
                    System.out.println("\"" + (int) c + "\" hasn't been loaded!");
                    continue;
                }
            }

            Sprite sp = (Main.contrast ? letters_simple[c] : letters[c]);
            if (c == ' ' || c == '\t' || c == '\n') {
                space = i;
            }
            cw += sp.getWidth();
            line += c;
            if (cw > width) {
                if (space != 0) {
                    draw(line.substring(0, space), x, y, color, batch);
                    i -= space;
                    line = line.substring(space);
                    space = 0;
                } else {
                    draw(line, x, y, color, batch);
                    i = 0;
                    line = new String();
                }
                y -= 48;
                cw = width(line);
            }
            i++;
        }
        draw(line, x, y, color, batch);
    }

    public static float width(Object text) {
        float ww = 0;
        if (text == null) {
            return 0;
        }
        char[] s = text.toString().toCharArray();
        for (char c : s) {
            if (letters[c] == null) {
                if (loaded[c]) {
                    continue;
                }
                letters[c] = atlas.createSprite(String.valueOf((int) c));
                letters_simple[c] = atlas_simple.createSprite(String.valueOf((int) c));
                loaded[c] = true;
                if (letters[c] == null) {
                    System.out.println("\"" + (int) c + "\" hasn't been loaded!");
                    continue;
                }
            }

            Sprite sp = (Main.contrast ? letters_simple[c] : letters[c]);
            ww += sp.getWidth();
        }
        return ww;
    }
}
