import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class Rendering {
    //Environment
    JFrame frame = new JFrame("Procedural Experiment");
    graphicsPanel stage = new graphicsPanel();

    //Tentacle
    private Tentacle tentacle;
    private Vec2d goal;
    private Vec2d midGoal;

    public void init(int stageWidth, int stageHeight){
        frame.getContentPane().setPreferredSize(new Dimension(stageWidth, stageHeight));
        frame.getContentPane().add(stage, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public class graphicsPanel extends JPanel {
        double progress;
        public graphicsPanel() {}

        public void update(Tentacle t){
            tentacle = t;
            goal = tentacle.goal;
            midGoal = tentacle.midGoal;
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.BLUE);
            for (int i = 0; i < tentacle.joints.length; i++){
                g.fillOval((int)tentacle.joints[i].pos.x, (int)tentacle.joints[i].pos.y,10, 10);//joints
            }
            g.setColor(Color.RED);
            g.fillOval((int)Math.round(goal.x)-4, (int)Math.round(goal.y)-4, 8,8);

            g.setColor(new Color(170,80,70));
            g.fillOval((int)Math.round(midGoal.x)-2, (int)Math.round(midGoal.y)-2, 4,4);
        }
    }

    public static void main(String args[]){
        System.out.println("ABC");
        Rendering c = new Rendering();
        c.init(800, 800);
    }
}

