import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class rendering {
    //Environment
    JFrame frame = new JFrame("Cellular Automaton");
    graphicsPanel stage = new graphicsPanel();

    private int rows;
    private int cols;
    private int[][] cells;
    private int state;
    private int cellLength;

    public void init(int stageWidth, int stageHeight, int rows, int cols, int cellLength){

        this.rows = rows;
        this.cols = cols;
        this.cellLength = cellLength;

        frame.getContentPane().setPreferredSize(new Dimension(stageWidth, stageHeight));
        frame.getContentPane().add(stage, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        stage.setFocusable(true);
    }

    public class graphicsPanel extends JPanel {
        int progress;
        public graphicsPanel() {}

        public void update(int[][] cellsN, int t){
            cells = cellsN;
            progress = t;
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            for (int i=0; i<rows; i++){
                for (int j=0; j<cols; j++){
                    state = cells[i][j];
                    g.setColor(new Color((100*state)%255, (50 + 80*state)%255, (80 + 50*state+i*i+j*j+255)%255));
                    //g.setColor(new Color(80*state, 50 + 60*state, 80 + 50*state));
                    g.fillRect(cellLength*i,cellLength*j, cellLength,cellLength);

                }
            }
        }
    }

    public static void main(String args[]){
        System.out.println("Start");
        rendering c = new rendering();
        //c.init(800, 800, 10, 10, 5);
    }
}

