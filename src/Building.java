import java.util.*;

public class Building {
    private final List<Double> levelHeight;
    public Building(List<Double> levelHeight) {
        this.levelHeight= levelHeight;
    }

    public List<Double> timeCost(List<Elevator> elevators, List<Employee> sortedEmployees) {
        List<Double> timeRes = new ArrayList<>();
        // employees should be sorted based on arriveTime
        sortedEmployees.sort((o1, o2) -> {
            double diff = o1.getArriveTime() - o2.getArriveTime();
            if (diff < 0) {
                return -1;
            }
            if (o1.getArriveTime() > 0) {
                return 1;
            }

            return 0;
        });
        //
        for (Employee employee: sortedEmployees) {
            launchElevators(elevators, employee.getArriveTime());
            timeRes.add(latestTime(elevators));
            Elevator elevator = employee.findTheBestElevator(elevators);
            elevator.addPassenger(employee);
        }
        launchElevators(elevators, Integer.MAX_VALUE);
        timeRes.add(latestTime(elevators));
        return timeRes;
    }

    private Double latestTime(List<Elevator> elevators) {
        double latestTimeAtLevel1 = 0;
        for (Elevator elevator: elevators) {
            latestTimeAtLevel1 = Math.max(latestTimeAtLevel1, elevator.getTimeAtLevel1());
        }
        return latestTimeAtLevel1;
    }

    private void launchElevators(List<Elevator> elevators, double nextArriveTime) {
        for (Elevator elevator: elevators) {
            if (elevator.checkIfLaunch(nextArriveTime)) {
                elevator.launch(this);
            }
        }
    }

    public double getDistanceBetweenLevels(int level0, int level1) {
        return Math.abs(levelHeight.get(level0 - 1) - levelHeight.get(level1 - 1));
    }
}
