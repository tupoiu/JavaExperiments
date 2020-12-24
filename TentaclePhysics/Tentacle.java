public class Tentacle {
    private Vec2d origin = new Vec2d(200,200);
    private int jointsNumber = 10;
    private int jointsDistance = 4;
    private double jointMass = 0.5;
    public Vec2d goal = new Vec2d(100, 100);
    public Vec2d midGoal;
    public Vec2d normal = new Vec2d(0,0);
    public Vec2d vnormal = new Vec2d(0,0);
    public double magnitude = 0;
    public double vmagnitude = 0;
    public double k = 0.1;
    public double damp = 0.5;
    public double g = 1;
    public double strength = 20;

    public Joint[] joints;

    public Tentacle(Vec2d goal){
        this.goal = goal;
        midGoal = new Vec2d(goal.x, goal.y);
        joints = new Joint[jointsNumber];
        joints[0] = new Joint(origin);
        for(int i = 1; i < jointsNumber; i++){
            joints[i] = new Joint(origin.x, origin.x + jointsDistance*i);
            //joints[i].pos.y += jointsDistance;
        }

        joints[jointsNumber-1].pos.x -= 10;
    }

    public void tick(){
        //System.out.println(joints[1].pos);
        //System.out.println(joints[jointsNumber-1].accel.x);

        //Joints accelerate towards their previous one
        for (int i = 1; i < jointsNumber; i++) {
            magnitude = (joints[i].pos.distance(joints[i-1].pos));

            //System.out.println(magnitude);

            normal.x = (joints[i-1].pos.x - joints[i].pos.x)/Math.max(1, magnitude);
            normal.y = (joints[i-1].pos.y - joints[i].pos.y)/Math.max(1, magnitude);
            //System.out.println(normal);

            vmagnitude = joints[i].vel.distance(new Vec2d(0,0));
            vnormal.x = joints[i].vel.x/Math.max(0.01, vmagnitude);
            vnormal.y = joints[i].vel.y/Math.max(0.01, vmagnitude);

            //System.out.println(vmagnitude*vnormal.x);
            //System.out.println(joints[i].vel.x);


            //System.out.println(vmagnitude);

            joints[i].accel.x = (k*normal.x * (magnitude - jointsDistance) - damp*vnormal.x*vmagnitude)/jointMass;
            joints[i].accel.y = (k*normal.y * (magnitude - jointsDistance) - damp*vnormal.y*vmagnitude)/jointMass + g;

//            joints[i].accel.x = (k*normal.x * (magnitude - jointsDistance) - damp*joints[i].vel.x)/jointMass;
//            joints[i].accel.y = (k*normal.y * (magnitude - jointsDistance) - damp*joints[i].vel.y)/jointMass + g;

            //System.out.println(joints[i].accel);

            joints[i-1].accel.x -= k*normal.x * (magnitude - jointsDistance) / jointMass;
            joints[i-1].accel.y -= k*normal.y * (magnitude - jointsDistance) / jointMass;
        }

        //Accelerate the claw towards the goal
        //Find acceleration normal and magnitude
        magnitude = joints[jointsNumber-1].pos.distance(goal);
        normal.x = (goal.x - joints[jointsNumber-1].pos.x)/Math.max(1, magnitude);
        normal.y = (goal.y - joints[jointsNumber-1].pos.y)/Math.max(1, magnitude);

        //Set the midgoal position
        midGoal.x = (goal.x + joints[jointsNumber-1].pos.x)/2;
        midGoal.y = (goal.y + joints[jointsNumber-1].pos.y)/2 + magnitude*0.2;

        joints[jointsNumber-1].accel.x += strength*normal.x;
        joints[jointsNumber-1].accel.y += strength*normal.y;

//        joints[jointsNumber-1].accel.x *= Math.min((magnitude)/20+0,1);
//        joints[jointsNumber-1].accel.y *= Math.min((magnitude)/20+0,1);

        joints[jointsNumber-1].vel.x *= Math.min(1, magnitude/20);
        joints[jointsNumber-1].vel.y *= Math.min(1, magnitude/20);

        normal.x = (midGoal.x - joints[jointsNumber-1].pos.x)/Math.max(1, magnitude);
        normal.y = (midGoal.y - joints[jointsNumber-1].pos.y)/Math.max(1, magnitude);

        joints[jointsNumber-2].accel.x += 0.0*strength*normal.x * Math.min((magnitude+jointsDistance)/40,1);
        joints[jointsNumber-2].accel.y += 0.0*strength*normal.y * Math.min((magnitude+jointsDistance)/40,1);

        joints[0].accel.x = 0;
        joints[0].accel.y = 0;

        for (int i = 0; i < jointsNumber; i++){
            joints[i].tick();
        }
    }

}
