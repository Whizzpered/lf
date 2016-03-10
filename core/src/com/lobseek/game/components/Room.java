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
package com.lobseek.game.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.lobseek.game.Main;
import com.lobseek.game.actors.Test;
import com.lobseek.game.screens.Screen;
import static com.lobseek.utils.Math.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Yew_Mentzaki
 */
public class Room implements Layer {

    private final Particle[] particles;
    public final Actor[] actors;
    private final Actor[] renderActors;
    public final Screen screen;
    private final OrthographicCamera camera = new OrthographicCamera();
    public final Point cam = new Point();
    public final SpriteBatch batch = new SpriteBatch();
    public float deltaSize;
    private int maxActor;
    private long actTime, tickTime;
    private final Timer actTimer, tickTimer;
    private BitmapFont font = new BitmapFont();
    private boolean running;
    private float fingerDistance;

    /**
     * @param screen screen where Room will be displayed
     * @param actors maximal number of actors in room
     * @param particles maximal number of particles in room
     */
    public Room(Screen screen, int actors, int particles) {
        this.screen = screen;
        this.actors = new Actor[actors];
        this.renderActors = new Actor[actors];
        this.particles = new Particle[particles];
        actTimer = new Timer("Act Timer");
        tickTimer = new Timer("Tick Timer");

        actTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (running) {
                    float d = (float) (System.currentTimeMillis() - actTime) / 1000f;
                    actTime = System.currentTimeMillis();
                    act(d);
                }
            }
        }, 10, 10);
        tickTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (running) {
                    float d = (float) (System.currentTimeMillis() - tickTime) / 1000f;
                    tickTime = System.currentTimeMillis();
                    tick(d);
                }
            }
        }, 25, 25);
    }

    /**
     * Adds actor to this room.
     *
     * @param actor
     * @return the actor from parameter backwards
     */
    public Actor add(Actor actor) {
        if (actor == null) {
            return null;
        }
        for (int i = 0; i < actors.length; i++) {
            Actor a = actors[i];
            if (a == null || a.removed) {
                actors[i] = actor;
                maxActor = Math.max(i + 1, maxActor);
                actor.room = this;
                actor.create();
                break;
            }
        }
        return actor;
    }

    /**
     * Adds particle to this room. Replaces oldest particle if particle array is
     * full <s>of shit</s>.
     *
     * @param particle
     * @return the particle from parameter backwards
     */
    public Particle add(Particle particle) {
        if (particle == null) {
            return null;
        }
        int min = -1, time = 10001;
        for (int i = 0; i < particles.length; i++) {
            Particle p = particles[i];
            int tl = p.timeLeft();
            if (tl <= 0) {
                particles[i] = particle;
                particle.room = this;
                particle.create();
                return particle;
            } else {
                if (time < tl) {
                    time = tl;
                    min = i;
                }
            }
        }
        if (min != -1) {
            particles[min] = particle;
        }
        particle.room = this;
        particle.create();
        return particle;
    }

    /**
     * Removes actor from this room. Actually, just hidding it before the next
     * one will replace it.
     *
     * @param actor
     * @return the actor from parameter
     */
    public Actor remove(Actor actor) {
        if (actor != null) {
            actor.removed = true;
        }
        return actor;
    }

    /**
     * Pauses or unpauses game.
     */
    public void pause() {
        running = !running;
    }

    /**
     * Starting game in it's stoped or not began yet.
     */
    public void start() {
        running = true;
    }

    /**
     * Stoping game if it runs.
     */
    public void stop() {
        running = false;
    }

    /**
     * Acts actors and particles. Avokes automatically, by timer.
     *
     * @param delta time between acts in seconds
     */
    public void act(float delta) {
        int cur = 0, max = 0;
        for (Actor a : actors) {
            cur++;
            if (a != null && !a.removed) {
                max = cur;
                a.act(delta);
            }
        }
        maxActor = max;
    }

    /**
     * Renders terrain, actors and particles in frame. Avokes by screen.
     *
     * @param delta time between frames in seconds
     */
    @Override
    public void render(float delta) {
        for (int i = 0; i < actors.length; i++) {
            renderActors[i] = actors[i];
        }
        Arrays.sort(renderActors, new Comparator<Actor>() {
            @Override
            public int compare(Actor o1, Actor o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return 1;
                }
                if (o2 == null) {
                    return -1;
                }
                return o1.compareTo(o2);
            }
        });
        float width = screen.width - deltaSize;
        float height = (width / screen.width) * screen.height;
        camera.setToOrtho(true, width, height);
        camera.position.x = cam.x;
        camera.position.y = cam.y;
        camera.update();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
//        System.out.println(camera.position.x + ":" + camera.position.y + " (" +
//                camera.viewportWidth + ":" + camera.viewportHeight + ")");
        for (Actor a : renderActors) {
            if (a != null && !a.removed) {
                if (a.x + a.width / 2
                        >= camera.position.x - camera.viewportWidth / 2
                        && a.y + a.height / 2
                        >= camera.position.y - camera.viewportHeight / 2
                        && a.x - a.width / 2
                        <= camera.position.x + camera.viewportWidth / 2
                        && a.y - a.height / 2
                        <= camera.position.y + camera.viewportHeight / 2) {
                    a.render(batch, delta);
                }
            }
        }
        batch.end();
        screen.B.begin();
        font.setColor(Color.WHITE);
        font.draw(screen.B, String.valueOf(Main.fps), 20, 20);
        screen.B.end();
    }

    /**
     * Ticks actors. Avokes automatically, by timer.
     *
     * @param delta time between tick in seconds
     */
    public void tick(float delta) {
        for (Actor a : actors) {
            if (a != null && !a.removed) {
                a.tick(delta);
            }
        }
    }

    /**
     * Handles touch down for this layer.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event
     * @param button the button
     * @return whether the input touched anything
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!screen.touches[1].down) {
            add(new Test(screenX + cam.x - screen.width / 2,
                    screenY + cam.y - screen.height / 2,
                    Main.R.nextFloat() * 6.28f));
        } else if (screen.touches[0].down) {
            fingerDistance = dist(screen.touches[1].x, screen.touches[1].y,
                    screen.touches[0].x, screen.touches[0].y);
        }
        return false;
    }

    /**
     * Handles touch up for this layer.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event
     * @param button the button
     * @return whether the input touched anything
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Handles drag of finger for this layer.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event
     * @return whether the input touched anything
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (screen.touches[0].down && !screen.touches[1].down) {
            cam.x -= screen.touches[0].dx;
            cam.y -= screen.touches[0].dy;
        } else if (screen.touches[0].down && screen.touches[1].down) {
            float fd = dist(screen.touches[1].x, screen.touches[1].y,
                    screen.touches[0].x, screen.touches[0].y);
            float d = fd - fingerDistance;
            deltaSize += d;
            float fx = (screen.touches[0].x - screen.width / 2) +
                    (screen.touches[1].x - screen.width / 2);
            float fy = (screen.touches[0].y - screen.height / 2) +
                    (screen.touches[1].y - screen.height / 2);
            float a = atan2(fy, fx);
            fx = abs(fx) / screen.width;
            fy = abs(fy) / screen.height;
            cam.x += cos(a) * d * fx;
            cam.y += sin(a) * d * fy;
            fingerDistance = fd;

        }
        return false;
    }

}
