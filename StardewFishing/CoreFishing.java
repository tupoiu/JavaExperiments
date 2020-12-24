import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Timer;

public class CoreFishing {

    //Loop settings
    private java.util.Timer timer;
    private boolean isRunning;
    final int fps = 60;

    //Environment
    JFrame frame = new JFrame("Stardew Valley Fishing Minigame Replica");
    graphicsPanel stage = new graphicsPanel();

    //Declaring the attributes of the stage
    public int barLength = 100;
    public double gravity = -0.17;
    public double playerA = 0.34; //The amount of acceleration the player can exert on the bar.
    public int stageWidth = 800;
    public int stageHeight = 800;
    public double difficulty = 5; //Choose difficulty here


    BasicFishBehaviour.Carp fish = new BasicFishBehaviour.Carp(difficulty);
//    BasicFishBehaviour.Bass fish = new BasicFishBehaviour.Bass(difficulty);
//    BasicFishBehaviour.Mackerel fish = new BasicFishBehaviour.Mackerel(difficulty);
    //Current information
    public int barY = 0; //The bar's height, measured in pixels from the bottom of the bar to the floor.
    public double barV; //The bar's positive or negative velocity, measured in pixels/tick
    public double barA; //The bar's positive or negative acceleration, measured in pixels/tick^2
    public double progress;
    public int fishY = 400; //The fish's height, measured in pixels from the bottom of the bar to the floor.

    //Input information variables
    volatile public boolean mouseDown = false;
    volatile public boolean hop = false;

    //Test variables
    public int t = 0;

    public CoreFishing(){
        isRunning = true;
    }


    public void gameLoop(){
        progress = 0;
        frame.getContentPane().setPreferredSize(new Dimension(stageWidth, stageHeight));
        stage.setSize(new Dimension(stageWidth, stageHeight));
        stage.setFocusable(true);
        stage.addMouseListener(new mouseListener());
        stage.addKeyListener(new keyListener());
        frame.getContentPane().add(stage, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        timer = new Timer();
        timer.schedule(new tickTask(), 0, 1000 / fps);
    }

    private class tickTask extends java.util.TimerTask
    {
        public void run() //this becomes the loop
        {

            if (progress<680 && Math.abs((fishY+20) - (barY+barLength/2)) < 20 + barLength/2) {
                progress = progress + 1.5;
            }else if(progress >= 680){
                System.out.println("WIN");
                timer.cancel();
            }else if(progress >= 0){
                progress = progress - 1.5*(0.5 + 0.05*difficulty);
            }
            tick();
            fish.tick(stageHeight);
            fishY = fish.getY();
            stage.setHeight(barY);
//            System.out.println(barY);
            if (!isRunning)
            {
                timer.cancel();
            }
        }
    }

    public void tick(){
        //bar acceleration check
        if(mouseDown){
            barA = gravity + playerA;
        }else{
            barA = gravity;
        }
        if(hop){
            barY = barY + 150;
            barV = barV/2;
            hop = false;
        }
        barV = barV + barA;//bar velocity update
        if (barY < 0) {
            barV = 0.7 * Math.abs(barV);
            barY = 0;
        }

        if (barY > stageHeight - barLength) {
            barV = -0.7 * Math.abs(barV) - 0.5;
            barY = stageHeight - barLength;
        }
        barY = (int)(barY+barV);
    }

    public class graphicsPanel extends JPanel {
        int barY;
        public graphicsPanel() {
            barY = 0;
//            this.setSize(new Dimension(stageWidth, stageHeight));
        }
        public graphicsPanel(int height) {
            barY = height;
//            this.setSize(new Dimension(stageWidth, stageHeight));
        }


        public void setHeight(int height){
            barY = height;
            this.validate();
            this.repaint();
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.BLUE);
            g.fillRect(0,stageHeight - barY - barLength,100,barLength);//fishing bar

            g.setColor(Color.BLACK);
            g.fillRect(100, 0, 700, 60);//exterior progress bar

            g.setColor(Color.RED);
            g.fillRect(110, 10, (int)progress, 40);//interior progress bar

            g.setColor(Color.ORANGE);
            g.fillRect(30, stageHeight - fishY - 40, 40, 40);
        }
    }

    public class mouseListener implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e){
            //System.out.println("CLICK");
            mouseDown = true;
        }
        @Override
        public void mouseReleased(MouseEvent e){
            mouseDown = false;
        }
        @Override
        public void mouseClicked(MouseEvent e){}
        @Override
        public void mouseEntered(MouseEvent e){}
        @Override
        public void mouseExited(MouseEvent e){}

    }

    public class keyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent k){
            if (k.getKeyCode() == KeyEvent.VK_D || k.getKeyCode() == KeyEvent.VK_F) {
                hop = true;
            }
        }
        @Override
        public void keyPressed(KeyEvent k){
            if (k.getKeyCode() == KeyEvent.VK_D || k.getKeyCode() == KeyEvent.VK_F){
                hop = true;
            }
        }
        @Override
        public void keyReleased(KeyEvent k){}
    }

    public static void main(String args[]){
        System.out.println("Initialisation");
        CoreFishing c = new CoreFishing();
        c.gameLoop();
    }
}
