/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.game.components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.lobseek.game.screens.Screen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.Timer;

/**
 *
 * @author aenge
 */
public class Room implements Layer {

    public Actor[] actors;
    private final Particle[] particles;
    private final Actor[] actorList;
    public final Screen screen;
    private final OrthographicCamera camera = new OrthographicCamera();
    public final Point cam = new Point();
    public final SpriteBatch batch = new SpriteBatch();
    public float deltaSize;
    private int maxActor;
    private long actTime, tickTime;
    private final Timer actTimer, tickTimer;
    
    /**
     * @param screen screen where Room will be displayed
     * @param actors maximal number of actors in room
     * @param particles maximal number of particles in room
     */
    public Room(Screen screen, int actors, int particles) {
        this.screen = screen;
        this.actorList = new Actor[actors];
        this.particles = new Particle[particles];
        this.actors = new Actor[0];
        actTimer = new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                float d = (float) (System.currentTimeMillis() - actTime) / 1000f;
                actTime = System.currentTimeMillis();
                act(d);
            }
        });
        tickTimer = new Timer(50, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                float d = (float) (System.currentTimeMillis() - tickTime) / 1000f;
                tickTime = System.currentTimeMillis();
                act(d);
            }
        });
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
        for (int i = 0; i < actorList.length; i++) {
            Actor a = actorList[i];
            if (a == null || a.removed) {
                actorList[i] = actor;
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
        if (actTimer.isRunning()) {
            stop();
        } else {
            start();
        }
    }

    /**
     * Starting game in it's stoped or not began yet.
     */
    public void start() {
        if (!actTimer.isRunning()) {
            actTime = System.currentTimeMillis();
            tickTime = System.currentTimeMillis();
            actTimer.start();
            tickTimer.start();
        }
    }

    /**
     * Stoping game if it runs.
     */
    public void stop() {
        if (actTimer.isRunning()) {
            actTimer.stop();
            tickTimer.stop();
        }
    }

    /**
     * Acts actors and particles. Avokes automatically, by timer.
     *
     * @param delta time between acts in seconds
     */
    public void act(float delta) {
        Actor[] actors = Arrays.copyOf(actorList, maxActor);
        Arrays.sort(actors);
        this.actors = actors;
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
        float width = screen.width - deltaSize;
        float height = (width / screen.width) * screen.height;
        camera.setToOrtho(false, width, height);
        camera.position.x = cam.x;
        camera.position.y = cam.y;
        camera.update();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
//        System.out.println(camera.position.x + ":" + camera.position.y + " (" +
//                camera.viewportWidth + ":" + camera.viewportHeight + ")");
        for (Actor a : actors) {
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
        return false;
    }

}
