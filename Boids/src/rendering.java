import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class rendering {
    //Environment
    JFrame frame = new JFrame("Boids");
    graphicsPanel stage = new graphicsPanel();



    public void init(int stageWidth, int stageHeight){
        frame.getContentPane().setPreferredSize(new Dimension(stageWidth, stageHeight));
        frame.getContentPane().add(stage, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public class graphicsPanel extends JPanel {
        private Boid[] boids;
        private int boidRadius = 20;
        public graphicsPanel() {}

        public void update(Boid[] boids){
            this.boids = boids;
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            for (int i = 0; i < 80; i++){
                for (int j = 0; j < 80; j++){
                    g.setColor(new Color((int)(60*Math.sin(i*i + j)+100)%255, (50 + 1*j)%255, (80+i*i+j*j+255)%255));
                    g.fillRect(10*i,10*j,10,10);
                }
            }


            g.setColor(new Color(255,255,255));
//            g.fillRect(0,100,50, 50);//player
            for (int i = 0; i < boids.length; i++){
                //System.out.println(boids[i].position);
//                g.setColor(new Color((boids[i].position.x*2)%255,(boids[i].position.y*2)%255,255-5*i));
                g.fillOval((int)boids[i].position.getX() - boidRadius/2, (int)boids[i].position.getY() - boidRadius/2, boidRadius, boidRadius);
            }


        }
    }

    public static void main(String args[]){
        rendering c = new rendering();
        c.init(800, 800);
    }
}

