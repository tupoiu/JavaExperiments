import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Timer;
public class Engine {
    //Loop settings
    private java.util.Timer timer;
    private boolean isRunning;
    final int fps = 30;

    //Stage initialisation
    public int stageWidth = 800;
    public int stageHeight = 800;

    //Current information
    double progress = 0;

    //Tentacle info
    Vec2d goal = new Vec2d(100, 100);

    public Tentacle tentacle = new Tentacle(goal);

    Rendering R = new Rendering();

    public void init(){
        isRunning = true;
        R.stage.update(tentacle);
        R.stage.addMouseMotionListener(new mlistener());
        R.init(stageWidth,stageHeight);
        timer = new Timer();
        timer.schedule(new tickTask(), 0, 1000 / fps);
    }
    private class tickTask extends java.util.TimerTask {
        public void run(){
            tentacle.tick();
            R.stage.update(tentacle);
            R.stage.validate();
            R.stage.repaint();
            progress++;
//            System.out.println("");
            if (!isRunning)
            {
                timer.cancel();
            }
        }
    }

    public class mlistener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            goal.x = e.getX();
            goal.y = e.getY();
            //System.out.println("bang");
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }


    public static void main(String args[]){
        Engine M = new Engine();
        M.init();
    }

}
