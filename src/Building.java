import javax.crypto.ExemptionMechanism;
import java.util.*;

public class Building {
    private final List<Double> levelHeight;
    public Building(List<Double> levelHeight) {
        this.levelHeight= levelHeight;
    }

    public double timeCost(List<Elevator> elevators, List<Employee> sortedEmployees) {
        // employees should be sorted based on arriveTime
        List<Queue<Employee>> queues = new ArrayList<>();
        for (int i = 0; i < elevators.size(); i++) {
            queues.add(new LinkedList<>());
        }
        for (Employee e: sortedEmployees) {
            arriveEmployee(e, elevators,);
            findQueue(e, queues);
        }

        double timeCost = 0;
        for (Elevator elevator: elevators) {
            timeCost = Math.max(timeCost, elevator.getTimeAtLevel1());
        }
        return timeCost;
    }

    private void findQueue(Employee e, List<Queue<Employee>> queues) {
        int minQueueSize = Integer.MAX_VALUE;

    }

    private double timeCostToDeliverAll(Elevator elevator, List<Employee> employees) {
        employees.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.getTargetLevel() - o2.getTargetLevel();
            }
        });
        double totalTimeCost = 0;
        int currentLevel = 1;
        for (Employee employee: employees) {
            double distance = getDistanceBetweenLevels(currentLevel, employee.getTargetLevel());
            double timeCost = elevator.timeCost(distance);
            currentLevel = employee.getTargetLevel();
            totalTimeCost += timeCost;
        }
        // at last move from currentLevel to level 1
        totalTimeCost += elevator.timeCost(getDistanceBetweenLevels(currentLevel, 1));
        elevator.updateTimeAtLevel1(totalTimeCost);
        return totalTimeCost;
    }
    private double getDistanceBetweenLevels(int level0, int level1) {
        return Math.abs(getHeight(level0) - getHeight(level1));
    }
    private double getHeight(int level) {
        return levelHeight.get(level - 1);
    }
}
