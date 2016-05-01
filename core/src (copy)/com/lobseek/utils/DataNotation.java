/*
 * Copyright (C) 2015 Yew_Mentzaki
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fe.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataNotation extends ArrayList<DataNotation> {

    public static DataNotation fromString(String string) {
        String s[] = string.replace("\t", "    ").split("\n");
        class SD {

            public SD(SD parent, DataNotation container, int depth) {
                this.parent = parent;
                if (parent != null) {
                    parent.container.add(container);
                }
                this.container = container;
                this.depth = depth;
            }

            SD parent;
            DataNotation container;
            int depth;
        }
        DataNotation sd = new DataNotation();
        SD cd = new SD(null, sd, -1);
        SD pd = null;
        int i = 0;
        while (i < s.length) {
            String c = s[i];
            int j = 0;
            for (; j < c.length(); j++) {
                if (c.charAt(j) != ' ') {
                    break;
                }
            }
            c = c.substring(j);
            if (pd != null) {
                if (j > pd.depth) {
                    cd = pd;
                }
            }
            while (cd != null && j <= cd.depth) {
                cd = cd.parent;
            }
            String c2[] = c.split("=");
            if (c2.length > 1) {
                pd = new SD(cd, new DataNotation(c2[0].trim(), c2[1].trim()), j);
            } else if (c2.length == 1) {
                if (i + 1 < s.length) {
                    String c3 = s[i + 1];
                    int j2 = 0;
                    for (; j2 < c3.length(); j2++) {
                        if (c3.charAt(j2) != ' ') {
                            break;
                        }
                    }
                    if (j2 > j) {
                        pd = new SD(cd, new DataNotation(c2[0].trim()), j);
                    } else {
                        pd = new SD(cd, new DataNotation(null, c2[0].trim()
                                .replace("\\\\", "//\\\\//")
                                .replace("\\0", "\0")
                                .replace("\\f", "f")
                                .replace("\\n", "n")
                                .replace("\\r", "r")
                                .replace("\\t", "t")
                                .replace("\\=", "=")
                                .replace("//\\\\//", "\\")), j);
                    }
                } else {
                    pd = new SD(cd, new DataNotation(null, c2[0].trim()), j);
                }
            }
            i++;
        }
        return sd;
    }

    public static DataNotation fromFile(String file) throws FileNotFoundException {
        return fromFile(new File(file));
    }

    public static DataNotation fromFile(File file) throws FileNotFoundException {
        Scanner s = new Scanner(file, "UTF8");
        String c = new String();
        while (s.hasNextLine()) {
            c += s.nextLine() + '\n';
        }
        return fromString(c);
    }

    public static DataNotation fromFileSafely(String file) {
        return fromFileSafely(new File(file));
    }

    public static DataNotation fromFileSafely(File file) {
        try {
            return fromFile(file);
        } catch (FileNotFoundException ex) {
            return new DataNotation();
        }
    }

    public static String toString(DataNotation sdata) {
        String s = new String();
        for (int i = 0; i < sdata.size(); i++) {
            s += sdata.get(i).objectToString(0) + ((i + 1 < sdata.size()) ? ("\n") : (""));
        }
        return s;
    }

    public static File toFile(DataNotation fdata, String file) {
        return toFile(fdata, new File(file));
    }

    public static File toFile(DataNotation fdata, File file) {
        PrintWriter pw;
        try {
            pw = new PrintWriter(file, "UTF8");
            for (String s : toString(fdata).split("\n")) {
                pw.println(s);
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataNotation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DataNotation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

    public DataNotation(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public DataNotation(String name) {
        this.name = name;
    }

    private DataNotation() {

    }

    private String name;
    private Object value;

    @Override
    public boolean isEmpty() {
        return !(isObject() || isList());
    }

    public boolean isList() {
        return size() > 0;
    }

    public boolean isObject() {
        return value != null;
    }

    public DataNotation get(String name) {
        for (int i = 0; i < size(); i++) {
            DataNotation sc = get(i);
            if (name.equals(sc.name)) {
                return sc;
            }
        }
        return new DataNotation(name);
    }

    public boolean contains(String name) {
        if (name == null) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            DataNotation sc = get(i);
            if (name.equals(sc.name)) {
                return true;
            }
        }
        return false;
    }

    public DataNotation def(String name) {
        DataNotation sc = get(name);
        if (!contains(name)) {
            add(sc);
        }
        return sc;
    }

    public DataNotation set(String name, Object value) {
        DataNotation sc = get(name);
        if (sc.isEmpty()) {
            add(sc);
        }
        sc.setValue(value);
        return sc;
    }

    public DataNotation add(String name, Object value) {
        DataNotation adden = new DataNotation(name, value);
        add(adden);
        return adden;
    }

    public DataNotation add(String name) {
        DataNotation adden = new DataNotation(name);
        add(adden);
        return adden;
    }

    public DataNotation remove(String name) {
        DataNotation sc = get(name);
        remove(sc);
        return sc;
    }

    public DataNotation bind(String name, Object value) {
        DataNotation sc = get(name);
        if (sc.isEmpty()) {
            add(sc);
        }
        if (sc.value == null) {
            sc.setValue(value);
        }
        return sc;
    }

    public DataNotation bind(String name) {
        return bind(name, null);
    }

    public String getName() {
        return name;
    }

    private String spaces(int d) {
        String s = new String();
        for (int i = 0; i < d; i++) {
            s += "    ";
        }
        return s;
    }

    private String objectToString(int depth) {
        String s = spaces(depth);
        String v = null;
        if (value != null) {
            v = value.toString();
            try {
                double d = Double.parseDouble(v);
            } catch (Exception e) {
                if (!(v.equals("true") || v.equals("false"))) {
                    char c[] = v.toCharArray();
                    v = new String();
                    boolean control = false;
                    for (char i : c) {
                        if (i == '\\') {
                            v += "\\\\";
                        } else if (i == '\0') {
                            v += "\\0";
                        } else if (i == '\b') {
                            v += "\\b";
                        } else if (i == '\t') {
                            v += "\\t";
                        } else if (i == '\n') {
                            v += "\\n";
                        } else if (i == '\f') {
                            v += "\\f";
                        } else if (i == '\r') {
                            v += "\\r";
                        } else if (i == '=') {
                            v += "\\=";
                        } else if (i == '`') {
                            v += "\\`";
                        } else {
                            v += i;
                        }
                    }
                }
            }
        }
        if (name != null && value != null) {
            s += name + " = " + v;
        } else if (name != null) {
            s += name;
        } else if (v != null) {
            s += v;
        }
        for (int i = 0; i < size(); i++) {
            s += "\n" + get(i).objectToString(depth + 1);
        }
        return s;
    }

    @Override
    public String toString() {
        if (value != null) {
            return value.toString();
        } else {
            return null;
        }
    }

    public double toDouble() {
        return Double.parseDouble(value.toString());
    }

    public float toFloat() {
        return Float.parseFloat(value.toString());
    }

    public long toLong() {
        return Long.parseLong(value.toString());
    }

    public int toInteger() {
        return Integer.parseInt(value.toString());
    }

    public short toShort() {
        return Short.parseShort(value.toString());
    }

    public byte toByte() {
        return Byte.parseByte(value.toString());
    }

    public char toCharacter() {
        return (value.toString().charAt(0));
    }

    public boolean toBoolean() {
        return Boolean.parseBoolean(value.toString());
    }

    public String toString(String def) {
        return value == null ? def : value.toString();
    }

    public double toDouble(double def) {
        return value == null ? def : Double.parseDouble(value.toString());
    }

    public float toFloat(float def) {
        return value == null ? def : Float.parseFloat(value.toString());
    }

    public long toLong(long def) {
        return value == null ? def : Long.parseLong(value.toString());
    }

    public int toInteger(int def) {
        return value == null ? def : Integer.parseInt(value.toString());
    }

    public short toShort(short def) {
        return value == null ? def : Short.parseShort(value.toString());
    }

    public byte toByte(byte def) {
        return value == null ? def : Byte.parseByte(value.toString());
    }

    public char toCharacter(char def) {
        return value == null ? def : (value.toString().charAt(0));
    }

    public boolean toBoolean(boolean def) {
        return value == null ? def : Boolean.parseBoolean(value.toString());
    }

    public Object toObject() {
        return value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
