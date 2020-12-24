import java.awt.*;
import java.util.Random;

public class Boid {
    public Vec2d direction;
    public Point position;
    private Random rand = new Random();
    private float theta;

    public Boid (Point initPos, Vec2d initDir){
        this.position = initPos;
        this.direction = initDir;
    }

    public void tick(Boid[] boids, int me, int AveragePosX, int AveragePosY, double AverageDirX, double AverageDirY){
        this.position.x += Math.round(this.direction.x);
        this.position.y += Math.round(this.direction.y);

        this.position.x = (this.position.x + 800) % 800;
        this.position.y = (this.position.y + 800) % 800;

        for (int i = 0; i < boids.length; i++){
            if (i != me){
                if (Math.abs((boids[i].position.x-this.position.x) % 800) + Math.abs((boids[i].position.y-this.position.y) % 800) < 100
                && boids[i].position.x-this.position.x != 0 && boids[i].position.y-this.position.y != 0){
                    this.direction.x -= 1.0*Math.abs(boids[i].position.x-this.position.x)/(boids[i].position.x-this.position.x);
                    this.direction.y -= 1.0*Math.abs(boids[i].position.y-this.position.y)/(boids[i].position.y-this.position.y);

                }else if (Math.abs(boids[i].position.x-this.position.x) + Math.abs(boids[i].position.y-this.position.y) < 200
                        && boids[i].position.x-this.position.x != 0 && boids[i].position.y-this.position.y != 0) {
                    this.direction.x += 0 * Math.abs(boids[i].position.x - this.position.x) / (boids[i].position.x - this.position.x);
                    this.direction.y += 0 * Math.abs(boids[i].position.y - this.position.y) / (boids[i].position.y - this.position.y);
                }else if (Math.abs(boids[i].position.x-this.position.x) + Math.abs(boids[i].position.y-this.position.y) < 300
                        && boids[i].position.x-this.position.x != 0 && boids[i].position.y-this.position.y != 0) {
                    this.direction.x += 0.1 * Math.abs(boids[i].position.x - this.position.x) / (boids[i].position.x - this.position.x);
                    this.direction.y += 0.1 * Math.abs(boids[i].position.y - this.position.y) / (boids[i].position.y - this.position.y);
                }
            }
        }
        if (this.position.x-AveragePosX != 0 && this.position.y-AveragePosY != 0){
            //System.out.println(AveragePosX + "" + AveragePosY);
            this.direction.x += 0.1*Math.abs(AveragePosX - this.position.x) / (AveragePosX - this.position.x);
            this.direction.y += 0.1*Math.abs(AveragePosY - this.position.y) / (AveragePosY - this.position.y);
        }

        this.direction.x += 100/this.position.x + 100/(this.position.x-800);
        this.direction.y += 100/this.position.y + 100/(this.position.y-800);

        this.direction.x = (50*this.direction.x + AverageDirX)/50;
        this.direction.y = (50*this.direction.y + AverageDirY)/50;

        theta = 360*rand.nextFloat();

        this.direction.x += Math.sin(theta);
        this.direction.y += Math.cos(theta);

        this.direction.x = Math.signum(this.direction.x) * Math.min(Math.abs(this.direction.x), 10);
        this.direction.y = Math.signum(this.direction.y) * Math.min(Math.abs(this.direction.y), 10);
    }
}
