import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    private final List<Employee> passengers;
    private final double closeTime = 3.0;
    private final double openTime = 3.0;
    public Elevator(int level0, int level1, double acceleration, double brakeAcceleration, double maxspeed) {
        this.startLevel = level0;
        this.endLevel = level1;
        this.acceleration = acceleration;
        this.brakeAcceleration = brakeAcceleration;
        this.maxSpeed = maxspeed;
        this.timeAtLevel1 = 0;
        this.passengers = new ArrayList<>();
    }


    public double timeCost(double distance) {
        // always start with door closing, and end with door opening
        // elevator accelerate up to maxSpeed, then run at maxSpeed, and brake as breakAccelerate until totally stop
        double t0 = maxSpeed / acceleration + maxSpeed / brakeAcceleration;
        double d0 = maxSpeed * t0 / 2;
        if (distance < d0) {
            return closeTime + openTime + t0 * Math.sqrt(distance / d0);
        }
        return closeTime + openTime + t0 + (distance- d0) / maxSpeed;
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

    public boolean checkIfLaunch(double nextArriveTime) {
        return this.passengers.size() >= this.capacity || !this.passengers.isEmpty() && this.passengers.get(this.passengers.size() - 1).getArriveTime() + this.maxWaitingTime < nextArriveTime;
    }
    public void launch(Building building, double nextArriveTime) {
        if (this.passengers.isEmpty()) {
            return;
        }
        double deliverTime = 0;
        // only extra passenger enters then the elevator will know that no more passenger is accepted, then launch
        double launchTime = Math.min(this.passengers.get(this.passengers.size() - 1).getArriveTime() + this.maxWaitingTime, nextArriveTime);
        // sort passengers by his target level
        this.passengers.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.getTargetLevel() - o2.getTargetLevel();
            }
        });
        int currentLevel = 1;
        for (Employee employee: this.passengers) {
            double distance = building.getDistanceBetweenLevels(employee.getTargetLevel(), currentLevel);
            deliverTime += timeCost(distance);
            currentLevel = employee.getTargetLevel();
        }
        // move from last stop level to level 1
        deliverTime += timeCost(building.getDistanceBetweenLevels(currentLevel, 1));
        this.timeAtLevel1 = launchTime + deliverTime;
        this.passengers.clear();
    }

    public boolean acceptPassenger(Employee employee) {
        return this.startLevel <= employee.getTargetLevel() && employee.getTargetLevel() <= this.endLevel && this.passengers.size() < this.capacity;
    }
    public int compareTo(Elevator otherElevator) {
        int passengersSizeDiff = this.passengers.size() - otherElevator.passengers.size();
        if (passengersSizeDiff < 0) {
            return -1;
        } else if (passengersSizeDiff > 0) {
            return 1;
        }
        return 0;
    }
    public void addPassenger(Employee employee) {
        if (this.passengers.size() >= capacity) {
            throw new RuntimeException("The number of passengers has reached the capacity of the elevator!");
        }
        this.passengers.add(employee);
    }
}
