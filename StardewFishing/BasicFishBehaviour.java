import java.util.Random;

public class BasicFishBehaviour {
    public int fishY;
    public double fishV;
    public double fishA;

    public static class Carp{
        int t = 0;
        double Y, V, A;
        double difficulty;
        int period;
        Random rand = new Random();
        public Carp(double difficulty){
            Y = 400;
            V = 0.3;
            A = -0.05;
            this.difficulty = difficulty;
            period = (int)(30 - 2*difficulty);
        }
        public void tick(int stageHeight){
            t++;
            V = V+A;
            if (Y < 0) {
                V = 0.5 * Math.abs(V);
                Y = 0;
            }

            if (Y > stageHeight - 40) {
                V = -0.5 * Math.abs(V) - 0.5;
                Y = stageHeight - 40;
            }
            Y = Y+(1+0.05*difficulty)*V;
            if(t% period == 0){
                A = 0.07*difficulty*(rand.nextDouble()-0.5);
            }
            if((t% 2*period) == 0){
                A = (0.4+rand.nextDouble())*A;
            }
        }
        public int getY(){
            return (int)Y;
        }
    }
    //Salmon. Haddock, Chub
    public static class Bass{
        int t = 0;
        double Y, V, A;
        double difficulty;
        Random rand = new Random();
        public Bass(double difficulty){
            Y = 400;
            V = 0.3;
            A = -0.05;
            this.difficulty = difficulty;
        }
        public void tick(int stageHeight){
            t++;

            V = 2*V/(1+Math.exp(0.02*Math.abs(V))) + (Math.pow(difficulty, 1.25))*A;//Difficulty from 2 to 10 here

//            V = V+A;
            if (Y < 0) {
//                V = 0.5 * Math.abs(V);
                V = 0;
                Y = 0;
            }

            if (Y > stageHeight - 40) {
//                V = -0.5 * Math.abs(V) - 0.5;
                V = 0;
                Y = stageHeight - 40;
            }
            Y = Y+V;
            if((t% 30) == 0){
                A = (0.1 + 0.4*rand.nextDouble())*(rand.nextDouble()-0.5);
            }
            if((t% 60) == 0){
                A = Math.pow(rand.nextDouble(), 2)*A - 0.25*A;
            }
        }
        public int getY(){
            return (int)Y;
        }
    }

    public static class Mackerel{
        int t = 0;
        double Y, V, A;
        int centre;
        double difficulty;
        Random rand = new Random();
        public Mackerel(double difficulty){
            Y = 400;
            V = 0.3;
            A = -0.05;

            this.difficulty = difficulty;
        }
        public void tick(int stageHeight){
            t++;
            A = -(Y-centre)/1000;
            V = V + A;
            if (Y < 0) {
//                V = 0.5 * Math.abs(V);
                V = 0;
                Y = 0;
            }

            if (Y > stageHeight - 40) {
//                V = -0.5 * Math.abs(V) - 0.5;
                V = 0;
                Y = stageHeight - 40;
            }
            Y = Y+V;
            if((t% 60) == 0){
                centre = (int)(stageHeight/2 + 0.4*(rand.nextDouble()-0.5)*stageHeight);
                V = (5+0.5*difficulty)*(rand.nextDouble()-0.5);
            }
//            if((t% 60) == 0){
//                V = 20*(rand.nextDouble()-0.5);
//            }
        }
        public int getY(){
            return (int)Y;
        }
    }

}
