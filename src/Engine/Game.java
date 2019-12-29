package Engine;

import Engine.Display.Display;
import Engine.Graphics.CameraProjection;
import Engine.Graphics.Cube;
import Engine.InputManager.KeyManager;

import java.awt.*;
import java.awt.image.BufferStrategy;

class Engine implements Runnable {
    private boolean
        isRunning = false;

    private final int
        DEFAULT_FPS = 60;

    private int
        width,
        height;
    private String
        title;
    private long
        frameCount = 0;
    private Cube
        cube;
    private BufferStrategy
        DrawStrategy;

    private Graphics
        GFX;

    private Display
        Display;

    private KeyManager
        KeyManager;
    private Thread
        ExecutonThread;

    private CameraProjection
        CameraProjection;
    public Engine(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
    }
    public void init() {
        Display = new Display(title,width,height);
        KeyManager = new KeyManager();
        Display.getFrame().addKeyListener(KeyManager);
        CameraProjection = new CameraProjection(0,0,0,90, width, height, 1000.0f, 0.1f);
        Cube c = new Cube(CameraProjection,0,0,0,5,5,5);
        CameraProjection.addTriangles(c.getMeshCube());
    }
    @Override
    public void run() {
        init();
        int fps = DEFAULT_FPS;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long currentTime;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(isRunning){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / timePerTick;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if(delta >= 1){
                tick();
                render();
                ticks++;
                frameCount++;
                delta--;
            }
            if(timer >= 1000000000){
                Display.getFrame().setTitle(title + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }

    public void tick() {
        CameraProjection.tick(KeyManager);
    }

    private void render(){
        DrawStrategy = Display.getCanvas().getBufferStrategy();
        if(DrawStrategy == null){
            Display.getCanvas().createBufferStrategy(3);
            return;
        }
        GFX = DrawStrategy.getDrawGraphics();
        GFX.clearRect(0, 0, width, height);
        CameraProjection.render(GFX);
        //End Drawing!
        DrawStrategy.show();
        GFX.dispose();
    }

    public synchronized void start() {
        if(isRunning)
            return;
        isRunning = true;
        ExecutonThread = new Thread(this);
        ExecutonThread.start();
    }

    public synchronized void stop(){
        if(!isRunning)
            return;
        isRunning = false;
        try {
            ExecutonThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
