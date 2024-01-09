public class Elevator {
    private final int startLevel;
    private final int endLevel;
    private final double acceleration;
    private final double brakeAcceleration;
    private final double maxSpeed;
    private double timeAtLevel1;
    private final int capacity = 10; // maximum 10 employees
    private final int weightCapacity = 1300; // maximum weight 1300kg
    private final double maxWaitingTime = 10;
    public Elevator(int level0, int level1, double acceleration, double brakeAcceleration, double maxspeed) {
        this.startLevel = level0;
        this.endLevel = level1;
        this.acceleration = acceleration;
        this.brakeAcceleration = brakeAcceleration;
        this.maxSpeed = maxspeed;
        this.timeAtLevel1 = 0;
    }

    public double updateTimeAtLevel1(double timeDelivery) {
        this.timeAtLevel1 += timeDelivery;
        return this.timeAtLevel1;
    }

    public double timeCost(double distance) {
        // elevator accelerate up to maxSpeed, then run at maxSpeed, and brake as breakAccelerate until totally stop
        double t0 = maxSpeed / acceleration + maxSpeed / brakeAcceleration;
        double d0 = maxSpeed * t0 / 2;
        if (distance < d0) {
            return t0 * Math.sqrt(distance / d0);
        }
        return t0 + (distance- d0) / maxSpeed;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public double getBrakeAcceleration() {
        return brakeAcceleration;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getTimeAtLevel1() {
        return timeAtLevel1;
    }
}
