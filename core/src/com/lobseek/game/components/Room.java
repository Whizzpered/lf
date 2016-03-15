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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    public float deltaSize, size = 2000;
    private int maxActor;
    private long actTime, tickTime;
    private final Timer actTimer, tickTimer;
    private BitmapFont font = new BitmapFont();
    private boolean running;
    private float fingerDistance;
    public final Player players[];
    public int player = -1;
    public boolean selection, minimapSwipe;
    private final com.badlogic.gdx.graphics.g2d.Sprite terrain;
    private final Barricade[][] barricades = new Barricade[64][64];
    private final float barricadeSize = 360;

    private Sprite minimapGUI = new Sprite("minimap/gui");
    private Sprite minimapFrame = new Sprite("minimap/frame");

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
        this.players = new Player[16];
        players[0] = new Player(this, 0) {

            @Override
            public boolean isEnemy(int player) {
                return false;
            }

        };
        players[0].name = "NPCs";
        for (int i = 1; i < 16; i++) {
            players[i] = new Player(this, i);
        }
        players[2].color = Color.BLUE;
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
        terrain = new com.badlogic.gdx.graphics.g2d.Sprite(
                new Texture(Gdx.files.internal("dirt.png")));
        for (int i = 0; i < barricades.length; i++) {
            for (int j = 0; j < barricades[i].length; j++) {
                if (Main.R.nextBoolean()) {
                    barricades[i][j] = new Barricade(
                            (i - barricades.length / 2) * barricadeSize + 35
                            + Main.R.nextInt((int) (barricadeSize - 70)),
                            (j - barricades.length / 2) * barricadeSize + 35
                            + Main.R.nextInt((int) (barricadeSize - 70))
                    );
                }
            }
        }
    }

    public Barricade getBarricade(float x, float y) {
        return getBarricade((int) (x / barricadeSize) - (x < 0 ? 1 : 0),
                (int) (y / barricadeSize) - (y < 0 ? 1 : 0));
    }

    public Barricade getBarricade(int x, int y) {
        x += barricades.length / 2;
        y += barricades.length / 2;
        if (x >= 0 && x < barricades.length
                && y >= 0 && y < barricades[x].length) {
            return barricades[x][y];
        }
        return null;
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
                if (!actor.created) {
                    actor.create();
                }
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
                if (!selection) {
                    if (a != null && (a instanceof Unit)) {
                        Unit u = (Unit) a;
                        u.selected = false;
                    }
                }
            }
        }
        if (selection) {
            Point p = getGlobalCoordinates(
                    screen.touches[0].x,
                    screen.touches[0].y);
            float s = Math.abs(screen.width - deltaSize) / screen.width;
            float fx = screen.touches[0].x - screen.width / 2;
            float fy = screen.touches[0].y - screen.height / 2;
            cam.x += fx * delta * s / 2;
            cam.y += fy * delta * s / 2;

            for (Actor a : actors) {
                if (a != null && (a instanceof Unit)) {
                    Unit u = (Unit) a;
                    if (u.owner == player) {
                        if (dist(p.x, p.y, u.x, u.y) < 100f * s) {
                            u.selected = true;
                            selection = true;
                        }
                    }
                    if (u.selected) {
                        u.tx = p.x;
                        u.ty = p.y;
                    }
                }
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
    public void render(float delta
    ) {
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
        camera.setToOrtho(false, width, height);
        camera.position.x = cam.x;
        camera.position.y = cam.y;
        camera.update();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        for (int i = (int) ((cam.x - width / 2) / terrain.getWidth()) - 2;
                i < (int) ((cam.x + width / 2) / terrain.getWidth()) + 2; i++) {
            for (int j = (int) ((cam.y - height / 2) / terrain.getHeight()) - 2;
                    j < (int) ((cam.y + height / 2) / terrain.getHeight()) + 2; j++) {
                terrain.setCenter(i * terrain.getWidth(), j * terrain.getHeight());
                terrain.draw(batch);
            }
        }
        for (int i = (int) ((cam.x - width / 2) / barricadeSize) - 2;
                i < (int) ((cam.x + width / 2) / barricadeSize) + 2; i++) {
            for (int j = (int) ((cam.y - height / 2) / barricadeSize) - 2;
                    j < (int) ((cam.y + height / 2) / barricadeSize) + 2; j++) {
                Barricade b = getBarricade(i, j);
                if (b != null) {
                    b.render(batch, delta);
                }
            }
        }
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
        font.draw(screen.B, Main.fps + "  ---  " + Main.nanos, 20, 20);
        minimapGUI.x = screen.width - minimapGUI.width / 2;
        minimapGUI.y = screen.height - minimapGUI.height / 2;
        minimapGUI.draw(screen.B);
        for (Actor a : renderActors) {
            if (a != null && !a.removed) {
                a.minimapRender(screen.B, delta);
            }
        }
        float sw = (width / size * 72) / 2, sh = (height / size * 72) / 2;
        float sx = screen.width - 86 + (cam.x / size * 72);
        float sy = screen.height - 86 + (cam.y / size * 72);
        minimapFrame.x = sx - sw;
        minimapFrame.y = sy + sh;
        minimapFrame.angle = 0;
        minimapFrame.draw(screen.B);
        minimapFrame.x = sx + sw;
        minimapFrame.angle = PI / 2 * 3;
        minimapFrame.draw(screen.B);
        minimapFrame.y = sy - sh;
        minimapFrame.angle = PI;
        minimapFrame.draw(screen.B);
        minimapFrame.x = sx - sw;
        minimapFrame.angle = PI / 2;
        minimapFrame.draw(screen.B);

        screen.B.end();
    }

    /**
     * Ticks actors. Avokes automatically, by timer.
     *
     * @param delta time between tick in seconds
     */
    public void tick(float delta) {
        for (int i = 0; i < actors.length; i++) {
            Actor a = actors[i];
            if (a == null) {
                continue;
            }
            if (a.removed) {
                actors[i] = null;
            } else {
                a.tick(delta);
            }
        }
    }

    public Point getGlobalCoordinates(float screenX, float screenY) {
        Point p = new Point(screenX, screenY);
        p.x -= screen.width / 2;
        p.y -= screen.height / 2;
        p.x *= (screen.width - deltaSize) / screen.width;
        p.y *= (screen.width - deltaSize) / screen.width;
        p.x += cam.x;
        p.y += cam.y;
        return p;
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
        if (dist(screen.width - 86, screen.height - 86, screenX, screenY) < 72) {
            float sx = screenX - screen.width + 86;
            float sy = screenY - screen.height + 86;
            sx *= size / 72;
            sy *= size / 72;
            cam.x = sx;
            cam.y = sy;
            minimapSwipe = true;
            return true;
        }
        if (!screen.touches[1].down) {
            Point p = getGlobalCoordinates(screenX, screenY);
            float s = Math.abs(screen.width - deltaSize) / screen.width;
            for (Actor a : actors) {
                if (a != null && (a instanceof Unit)) {
                    Unit u = (Unit) a;
                    if (u.owner == player) {
                        if (dist(p.x, p.y, u.x, u.y) < 100f * s) {
                            u.selected = true;
                            selection = true;
                        }
                    }
                }
            }
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
        selection = false;
        minimapSwipe = false;
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
        if (minimapSwipe) {
            if (dist(screen.width - 86, screen.height - 86, screenX, screenY) < 72) {
                float sx = screenX - screen.width + 86;
                float sy = screenY - screen.height + 86;
                sx *= size / 72;
                sy *= size / 72;
                cam.x = sx;
                cam.y = sy;
                minimapSwipe = true;
            }
            return true;
        }
        if (!selection) {
            if (screen.touches[0].down && !screen.touches[1].down) {
                float s = (screen.width - deltaSize) / screen.width;
                cam.x -= screen.touches[0].dx * s;
                cam.y -= screen.touches[0].dy * s;
            } else if (screen.touches[0].down && screen.touches[1].down) {
                float fd = dist(screen.touches[1].x, screen.touches[1].y,
                        screen.touches[0].x, screen.touches[0].y);
                float d = fd - fingerDistance;
                float ds = deltaSize;
                float s = Math.abs(screen.width - deltaSize) / screen.width;
                deltaSize = (float) Math.max(Math.min(deltaSize + d * s, screen.width / 2),
                        -screen.width * 2);
                d = deltaSize - ds;
                float fx = (screen.touches[0].x - screen.width / 2)
                        + (screen.touches[1].x - screen.width / 2);
                float fy = (screen.touches[0].y - screen.height / 2)
                        + (screen.touches[1].y - screen.height / 2);
                float a = atan2(fy, fx);
                fx = abs(fx) / screen.width;
                fy = abs(fy) / screen.height;
                cam.x += cos(a) * d * fx * s;
                cam.y += sin(a) * d * fy * s;
                fingerDistance = fd;

            }
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        float d = amount * 100;
        float ds = deltaSize;
        float s = (screen.width - deltaSize) / screen.width;
        deltaSize = (float) Math.max(Math.min(deltaSize - d * s, screen.width / 2),
                -screen.width * 2);
        d = (deltaSize - ds) / 2;
        float fx = (screen.touches[0].x - screen.width / 2);
        float fy = (screen.touches[0].y - screen.height / 2);
        float a = atan2(fy, fx);
        fx = abs(fx) / screen.width * 2;
        fy = abs(fy) / screen.height * 2;
        cam.x += cos(a) * d * fx * s;
        cam.y += sin(a) * d * fy * s;
        return true;
    }

}
