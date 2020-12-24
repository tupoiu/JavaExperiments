import java.awt.event.*;
import java.util.Random;
import java.util.Timer;
//This program let's you draw stuff with the mouse

public class engine {
    //Loop settings
    private java.util.Timer timer;
    private boolean isRunning;
    final int fps = 10;
    private Random rand = new Random();

    //Arrays initialisation
    public int cols = 180;
    public int rows = 320;
    public int cellLength = 5;
    
    double threshold = 0.7;

    public int[][] cellsNminus1 = new int[rows][cols];
    public int[][] cellsN = new int[rows][cols];
    public int[][] cellsNplus1 = new int[rows][cols];

    //Temp
    int sum;
    int t = 0;

    boolean pen;

    //Stage initialisation
    public int stageWidth = 960;
    public int stageHeight = 540;

    //Current information
    double progress = 0;

    rendering R = new rendering();

    public void init(){
        isRunning = true;
        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                if (rand.nextDouble() > 0.6 + 0.4*(float)i/(float)rows) {
				    cellsN[i][j] = 1;
//                        if (i<rows/2) {
//                            cellsN[i][j] = 1;
//                        }else if (rand.nextDouble() > threshold/3){
//                            cellsN[i][j] = 1;
//                        }
				} else {
				    cellsN[i][j] = 0;
				}
            }
        }
        cellsNminus1 = cellsN;
//        t++;

        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                cellsN[i][j] = 0;
                }
            }
        
        R.stage.update(cellsN,t);
        R.init(stageWidth, stageHeight, rows, cols, cellLength);
        R.stage.addMouseMotionListener(new mlistener());
        R.stage.addKeyListener(new klistener());
        timer = new Timer();
        timer.schedule(new tickTask(), 0, 1000 / fps);
    }
    private class tickTask extends java.util.TimerTask {
        public void run(){

            R.stage.update(cellsN, t);
            R.stage.validate();
            tick();
            R.stage.repaint();
            if (!isRunning)
            {
                timer.cancel();
            }
        }
    }

    private void tick(){

        if (t>0) {
            t++;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    rules(i,j);
                }
            }
            cellsNminus1 = cellsN;
            cellsN = cellsNplus1;
        }
    }

    public void reset(){
        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                if (i > rows/4 && i < 3*rows/4 && j > cols/4 && j < 3*cols/4 || true) {
                    if (rand.nextDouble() > threshold) {
                        cellsN[i][j] = 1;
                    } else {
                        cellsN[i][j] = 0;
                    }
                }else{
                    cellsN[i][j] = 0;
                }
            }
        }
    }

    public void rules(int i, int j){
        sum = 0;
        sum = moore8(i, j);
        //sum += order2moore8A(i, j);
        //sum = order2moore8B(i, j);

        unstableToIslandsRules(i, j);
        //testRules(i,j);
    }

    public int moore8(int i, int j){

        for (int p = -1; p < 2; p++) {
            for (int q = -1; q < 2; q++) {
                sum += cellsN[(i + p + rows - 1) % (rows - 1)][(j + q + cols - 1) % (cols - 1)];
            }
        }
        sum -= cellsN[i][j];

        return sum;
    }

    public int order2moore8A(int i, int j){
        for (int p = -1; p < 2; p++) {
            for (int q = -1; q < 2; q++) {
                sum += cellsN[(i + p + rows - 1) % (rows - 1)][(j + q + cols - 1) % (cols - 1)];
            }
        }
        sum -= cellsNminus1[i][j];

        return sum;
    }

    public int order2moore8B(int i, int j){
        for (int p = -1; p < 2; p++) {
            for (int q = -1; q < 2; q++) {
                sum += cellsN[(i + p + rows) % (rows)][(j + q + cols) % (cols)];
                sum += cellsNminus1[(i + p + rows) % (rows)][(j + q + cols) % (cols)];
            }
        }
        sum -= cellsN[i][j];
        sum -= cellsNminus1[i][j];

        return sum;
    }

    public void unstableToIslandsRules(int i, int j){
        if (t < 5) {
            if (sum < 2 || sum > 5) {
                cellsNplus1[i][j] = 0;
            } else if (sum == 4 || sum == 5) {
                cellsNplus1[i][j] = cellsN[i][j];
            } else if (sum == 3) {
                cellsNplus1[i][j] = 1;
            }
        } else if (t < 30) {
            if (sum < 3) {
                cellsNplus1[i][j] = 0;
            } else if (sum == 3 || sum == 4) {
                cellsNplus1[i][j] = cellsN[i][j];
            } else if (sum > 4 && sum < 7) {
                cellsNplus1[i][j] = 1;
            } else if (sum == 8) {
                cellsNplus1[i][j] = 2;
            } else if (sum > 8 && sum < 11) {
                cellsNplus1[i][j] = cellsN[i][j];
            } else {
                cellsNplus1[i][j] = Math.max(0, cellsN[i][j] - 1);
            }
        } else {
            if (sum < 3) {
                cellsNplus1[i][j] = 0;
            } else if (sum > 2 && sum < 6) {
                cellsNplus1[i][j] = Math.min(cellsN[i][j], 1);
            } else if (sum > 5) {
                cellsNplus1[i][j] = 1;
            }
        }
    }

    public void testRules(int i,int j){
        if (t < 100) {
            if (sum <= 4 || sum >= 9) {
                cellsNplus1[i][j] = 0;
            } else if (sum == 6 || sum == 8) {
                cellsNplus1[i][j] = 1;
            } else {
                cellsNplus1[i][j] = cellsN[i][j];
            }
        }
    }

    public class mlistener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            int i = (e.getX()-e.getX()%cellLength)/cellLength;
            int j = (e.getY()-e.getY()%cellLength)/cellLength;
            cellsN[i][j] = 1;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        	
        }
    }

    public class klistener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
//
        }

        @Override
        public void keyPressed(KeyEvent e) {
            t++;
            System.out.println("Go");
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }



    public static void main(String args[]){
        //System.out.println("ABC");
        engine M = new engine();
        M.init();
    }

}
