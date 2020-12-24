public class Joint {
    public Vec2d pos;
    public Vec2d vel = new Vec2d (0,0);
    public Vec2d accel = new Vec2d(0,0);

    public Joint(Vec2d initPosition){
        pos = initPosition;
    }

    public Joint(double initX, double initY){
        pos = new Vec2d(initX, initY);
    }

    public void tick(){
        this.pos.x += this.vel.x;
        this.pos.y += this.vel.y;
        this.vel.x += this.accel.x;
        this.vel.y += this.accel.y;

    }

    public double distance(Joint j){
        return pos.distance(j.pos);
    }

    public double distanceSq(Joint j){
        return pos.distanceSq(j.pos);
    }
}
