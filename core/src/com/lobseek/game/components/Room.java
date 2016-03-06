/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lobseek.game.components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lobseek.game.screens.Screen;
import java.util.Arrays;

/**
 *
 * @author aenge
 */
public class Room {

    public Actor[] actors;
    private final Particle[] particles;
    private final Actor[] actorList;

    public final Screen screen;
    public final OrthographicCamera camera = new OrthographicCamera(960, 540);
    public final SpriteBatch batch = new SpriteBatch();

    private int maxActor = 0;

    public Actor add(Actor actor) {
        if (actor == null) {
            return null;
        }
        for (int i = 0; i < actorList.length; i++) {
            Actor a = actorList[i];
            if (a == null || a.removed) {
                actorList[i] = actor;
                maxActor = Math.max(i, maxActor);
                actor.room = this;
                actor.create();
                break;
            }
        }
        return actor;
    }

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

    public Actor remove(Actor actor) {
        if (actor != null) {
            actor.removed = true;
        }
        return actor;
    }

    public Room(Screen screen, int actors, int particles) {
        this.screen = screen;
        this.actorList = new Actor[actors];
        this.particles = new Particle[particles];
        this.actors = new Actor[0];
    }

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

    public void render(float delta) {
        camera.setToOrtho(false, screen.width, screen.height);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Actor a : actors) {
            if (a != null && !a.removed) {
                if (a.x + a.width >= camera.position.x
                        && a.y + a.height >= camera.position.y
                        && a.x - a.width
                        <= camera.position.x + camera.viewportWidth
                        && a.y - a.height
                        <= camera.position.y + camera.viewportHeight) {
                    a.render(batch, delta);
                }
            }
        }
        batch.end();
    }

    public void tick(float delta) {
        for (Actor a : actors) {
            if (a != null && !a.removed) {
                a.tick(delta);
            }
        }
    }

}
