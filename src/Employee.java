import java.util.List;

public class Employee {
    private final double arriveTime;
    private final int targetLevel;
    public Employee(double arriveTime, int targetLevel) {
        this.arriveTime = arriveTime;
        this.targetLevel = targetLevel;
    }

    public double getArriveTime() {
        return arriveTime;
    }

    public int getTargetLevel() {
        return targetLevel;
    }
    public Elevator findTheBestElevator(List<Elevator> elevators) {
        Elevator bestElevator = null;
        for (Elevator elevator: elevators) {
            if (elevator.acceptPassenger(this)) {
                if (bestElevator == null) {
                    bestElevator = elevator;
                } else if (elevator.compareTo(bestElevator) < 0) {
                    bestElevator = elevator;
                }
            }
        }
        return bestElevator;
    }

}
