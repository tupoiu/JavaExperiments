import java.awt.*;
import java.util.Random;
import java.util.Timer;

public class engine {
    //Loop settings
    private java.util.Timer timer;
    private boolean isRunning;
    final int fps = 60;

    //Stage initialisation
    public int stageWidth = 800;
    public int stageHeight = 800;

    //Current information
    double progress = 0;

    Random rand = new Random();

    //Boids array
//    public Boid[] boids = {
//            new Boid(new Point(100,500), new Vec2d(-1,0)),
//            new Boid(new Point(200,200), new Vec2d(-1,-1)),
//            new Boid(new Point(100,300), new Vec2d(0,1)),
//            new Boid(new Point(500,400), new Vec2d(-1,-1)),
//            new Boid(new Point(700,500), new Vec2d(0,-1)),
//            new Boid(new Point(500,600), new Vec2d(-1,0)),
//
//    };

    private int boidNumber = 10;
    public Boid[] boids = new Boid[boidNumber];

    public int averagePosX = 0;
    public int averagePosY = 0;

    public double averageDirX = 0;
    public double averageDirY = 0;

    rendering R = new rendering();

    public void init(){

        for (int i = 0; i < boidNumber; i++){
            boids[i] = new Boid(new Point(700/boidNumber*(i+2),700/boidNumber*(i+2)), new Vec2d(0.1+rand.nextDouble()-0.5, rand.nextDouble()-0.5));
        }

        isRunning = true;
        R.init(stageWidth,stageHeight);
        R.stage.update(boids);
        timer = new Timer();
        timer.schedule(new tickTask(), 0, 1000 / fps);
    }
    private class tickTask extends java.util.TimerTask {
        public void run(){
            R.stage.update(boids);
            R.stage.validate();
            R.stage.repaint();
            progress++;
            tick();
//            System.out.println("");
            if (!isRunning)
            {
                timer.cancel();
            }
        }
    }

    public void tick(){
        averagePosY = 0;
        averagePosX = 0;
        averageDirX = 0;
        averageDirY = 0;

        for (int i = 0; i < boids.length; i++){
            averagePosX += boids[i].position.x;
            averagePosY += boids[i].position.y;
            averageDirX += boids[i].direction.x;
            averageDirY += boids[i].direction.y;
        }
        averagePosX /= boids.length;
        averagePosY /= boids.length;

        averageDirX /= (double)boids.length;
        averageDirY /= (double)boids.length;
        for (int i = 0; i < boids.length; i++){
            boids[i].tick(boids, i, averagePosX, averagePosY, averageDirX, averageDirY);
        }

    }

    public static void main(String args[]){
        engine M = new engine();
        M.init();
    }

}
