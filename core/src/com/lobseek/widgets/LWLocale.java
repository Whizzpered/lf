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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.util.Locale;
import org.fe.main.DataNotation;

/**
 *
 * @author yew_mentzaki
 */
public class LWLocale {

    private static DataNotation locale, localeEn;

    public static void init() {
        if (locale == null) {
            String l = Locale.getDefault().getLanguage();
            FileHandle fh = Gdx.files.internal("locale/" + l + ".locale");
            String s = fh.readString();
            locale = DataNotation.fromString(s);
        }
        if (localeEn == null) {
            FileHandle fh = Gdx.files.internal("locale/en.locale");
            String s = fh.readString("utf8");
            localeEn = DataNotation.fromString(s);
        }
    }

    public static String get(String key) {
        if (locale != null) {
            if (key == null) {
                return null;
            }
            String k[] = key.toLowerCase().split("\\.");
            DataNotation fd = locale;
            for (String k1 : k) {
                fd = fd.get(k1);
            }
            String s = fd.toString();
            if (s != null) {
                return s;
            } else {
                k = key.toLowerCase().split("\\.");
                fd = localeEn;
                for (String k1 : k) {
                    fd = fd.get(k1);
                }
                s = fd.toString();
                if (s != null) {
                    return s;
                }
            }
        }
        return key;
    }
}
