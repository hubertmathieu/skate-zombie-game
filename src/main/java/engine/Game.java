package engine;

import java.awt.event.KeyListener;

public abstract class Game {


    public boolean playing = true;
    private RenderingEngine renderingEngine;
    private GameTime gameTime;

    public abstract void initialize();
    public abstract void update();
    public abstract void draw(Buffer buffer);
    public abstract void conclude();

    public Game() {
        renderingEngine = RenderingEngine.getInstance();
        renderingEngine.setViewport(800, 600);
    }

    public void start() {
        initialize();
        run();
        conclude();
    }

    public void stop() {
        playing = false;
    }

    public void run() {
        renderingEngine.start();
        gameTime = GameTime.getInstance();
        while (playing) {
            update();
            draw(renderingEngine.getRenderingBuffer());
            renderingEngine.renderBufferOnScreen();
            gameTime.synchronize();
        }
        renderingEngine.stop();
    }

    protected void addKeyListener(KeyListener listener) {
        renderingEngine.addKeyListener(listener);
    }


}
