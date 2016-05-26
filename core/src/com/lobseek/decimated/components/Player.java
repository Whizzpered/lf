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
package com.lobseek.decimated.components;

import com.badlogic.gdx.Gdx;
import com.lobseek.decimated.units.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.lobseek.decimated.Main;
import com.lobseek.decimated.ProjectLogger;
import com.lobseek.decimated.actors.Base;
import com.lobseek.decimated.gui.Button;
import com.lobseek.decimated.gui.SpawnBar;
import com.lobseek.decimated.screens.GameScreen;
import com.lobseek.utils.ColorFabricator;
import static com.lobseek.utils.Math.*;
import com.lobseek.widgets.LWContainer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yew_Mentzaki
 */
public class Player {

    private int index;
    private final Room room;
    public String name = "Bot";
    public Color color = Color.RED;
    public boolean ai = true;
    public int alliance = -1;
    public Class types[];
    public float produce[];
    public int units, max, maxUnits = max = 5;
    public int experience;
    public boolean blocked;
    private Base base;

    public void setTypes(Class... types) {
        this.types = types;
        this.produce = new float[types.length];
        for (int i = 0; i < produce.length; i++) {
            produce[i] = 1f / ((float) produce.length);
        }
    }

    public Player(Room room, int index) {
        this.room = room;
        this.index = index;
        setTypes(
                Disruptor.class,
                Phantom.class,
                Planer.class,
                Dragoon.class,
                Controller.class
        );
        if (index != 1) {
            randomizeProduction();
        }
    }

    public void win() {
        System.out.println("Player " + index + " won!");
        if (index == room.player) {
            blocked = true;
            room.stop();
            room.screen.add(new Button("game.victory") {
                @Override
                public void tapUp(Touch t) {
                    room.screen.menu.show();
                    room.screen.room = null;
                    room.destroy();
                    hide();
                }
            });

        }
    }

    public void checkVictory() {
        for (int in = 1; in < room.players.length; in++) {
            if (in == index) {
                continue;
            }
            if (isEnemy(in) && room.players[in].maxUnits > room.players[in].max) {
                return;
            }
        }
        win();
    }

    public void fail() {
        for (Actor a : room.actors) {
            if (a != null && a instanceof Unit) {
                Unit u = (Unit) a;
                if (u.owner == index) {
                    u.hp = 0;
                }
            }
        }
        if (index == room.player) {
            blocked = true;
            room.stop();
            room.screen.add(new Button("game.defeat") {
                @Override
                public void tapUp(Touch t) {
                    room.screen.menu.show();
                    room.screen.room = null;
                    room.destroy();
                    hide();
                }
            });
        }
        System.out.println("Player " + index + " failed!");
    }

    public void randomizeProduction() {
        for (int i = 0; i < produce.length; i++) {
            produce[i] = 0;
        }
        for (int i = 0; i < 5; i++) {
            produce[Main.R.nextInt(produce.length)] += 0.2f;
        }
    }

    public Unit spawn(float x, float y, float angle) {
        if (units > maxUnits) {
            return null;
        }
        float rand = Main.R.nextFloat();
        float value = 0;
        Class clazz = null;
        for (int i = 0; i < types.length; i++) {
            value += produce[i];
            if (rand <= value) {
                clazz = types[i];
                break;
            }
        }
        if (clazz == null) {
            ProjectLogger.println("clazz == null");
            clazz = types[Main.R.nextInt(types.length)];
        }
        try {
            Constructor c
                    = clazz.getConstructor(float.class, float.class, float.class, int.class);
            return (Unit) c.newInstance(x, y, angle, index);
        } catch (Exception ex) {
            ProjectLogger.println(ex);
        }
        return null;
    }

    public Base getTarget() {
        if (this.base != null && (this.base.owner != index || this.base.power < 20)) {
            return this.base;
        }
        float x = 0, y = 0, i = 0;
        List<Base> base = new ArrayList<Base>();
        for (Actor a : room.actors) {
            if (a instanceof Base) {
                Base b = (Base) a;
                if (b.owner == index) {
                    x += b.x;
                    y += b.y;
                    i++;
                } else {
                    base.add(b);
                }
            }
        }
        Base bs = null;
        float dist = Float.MAX_VALUE;
        for (Base b : base) {
            float d = dist(b.x, b.y, x, y);
            if (d < dist) {
                dist = d;
                bs = b;
            }
        }
        this.base = bs;
        if (bs != null) {
            System.out.println("Base of " + bs.owner + " player is now target for " + index);
        }
        return bs;
    }

    public LWContainer getUnitList() {
        final com.badlogic.gdx.graphics.g2d.Sprite background
                = GameScreen.background;

        background.getTexture()
                .setFilter(Texture.TextureFilter.Linear,
                        Texture.TextureFilter.Linear);

        final SpawnBar[] bars = new SpawnBar[types.length];

        LWContainer menu = new LWContainer() {
            float alpha = 0;

            @Override
            public void act(float delta) {
                if (isVisible()) {
                    alpha = Math.min(1, alpha + delta);
                } else {
                    alpha = Math.max(0, alpha - delta);
                }
                super.act(delta);
            }

            @Override
            public void draw(Batch b) {
                background.setSize(parent.width, parent.height);
                background.setPosition(0, 0);
                background.setColor(ColorFabricator.neon(alpha));
                background.draw(b);
                width = parent.width;
                height = parent.height;
                super.draw(b);
            }

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

        for (int i = 0; i < types.length; i++) {
            final int index = i;
            SpawnBar s = new SpawnBar() {
                @Override
                public float x() {
                    return ((parent.width - 200) / ((float) types.length - 1))
                            * ((float) index)
                            + 100;
                }

                @Override
                public void valueChanged(float penis) {
                    produce[index] = getValue();
                    float secondpenis = penis;
                    int dicks = 1;
                    do {
                        penis = secondpenis / ((float) types.length - 1);
                        secondpenis = 0;
                        dicks--;
                        for (int j = 0; j < types.length; j++) {
                            if (j != index) {
                                float v = produce[j];
                                if (v - penis < 0 || v - penis > 1) {
                                    if (Math.abs(v - penis) > 0.00001) {
                                        secondpenis -= v - penis;
                                        dicks = 1;
                                    }
                                }
                                produce[j] = Math.min(1, Math.max(0, v - penis));
                            }
                        }
                    } while (dicks > 0);
                    for (int j = 0; j < types.length; j++) {
                        if (j != index) {
                            bars[j].value = produce[j];
                        }
                    }
                }
            };
            s.value = produce[i];
            s.sprite = new Sprite("gui/" + types[i].getSimpleName().toLowerCase(), true);
            bars[index] = s;
            //s.hide();
            menu.add(s);
        }
        menu.hide();
        return menu;
    }

    public boolean isEnemy(int player) {
        //*
        if (player == 0) {
            return false;
        } else if (room.players[player] == this) {
            return false;
        } else if (alliance == -1) {
            return true;
        } else {
            return room.players[player].alliance == alliance;
        }
        //*/return true;
    }

}
