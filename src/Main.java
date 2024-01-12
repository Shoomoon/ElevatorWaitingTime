import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // init building
        int maxLevel = 50;
        List<Double> levelHeight = initLevelHeight(maxLevel);
        Building building = new Building(levelHeight);
        // init elevators
        double acceleration = 1.0; // 1.0 m/s^2
        double brakeAcceleration = -1.0; // acceleration of stop, 1 m/s^2
        double maxSpeed = 3.0; // 3.0 m/s
        int elevatorCount = 4;
        List<Elevator> solidElevators = new ArrayList<>();
        List<Elevator> flexibleElevators = new ArrayList<>();
        for (int i = 0; i < elevatorCount; i++) {
            solidElevators.add(new Elevator(1, maxLevel, acceleration, brakeAcceleration, maxSpeed));
            flexibleElevators.add(new Elevator(maxLevel * i / elevatorCount, maxLevel * (i + 1) / elevatorCount,
                    acceleration, brakeAcceleration, maxSpeed));
        }
        // init test case
        int maxEmployeeCount = 100;
        List<Employee> employees = initEmployees(maxLevel, maxEmployeeCount);
        List<Double> timeCostForSolidRangeElevator = building.timeCost(solidElevators, employees);
        List<Double> timeCostForFlexibleRangeElevator = building.timeCost(flexibleElevators, employees);
        // plot the result
        plot(timeCostForSolidRangeElevator, timeCostForFlexibleRangeElevator);
    }

    private static List<List<Employee>> generatorTestCases(int maxLevel) {
        List<List<Employee>> testCases = new ArrayList<>();
        int seed = 21541;
        Random random = new Random(seed);
        for (int i = 100; i <= 100000; i += 100) {
            List<Employee> employees = initEmployees(maxLevel, i);
            testCases.add(employees);
        }
        return testCases;
    }

    private static List<Double> initLevelHeight(int levelCount) {
        double eachLevelHeight = 2.743; // 9 feet = 2.743m
        List<Double> levels = new ArrayList<>();
        levels.add(0.0); // the height of the first floor is 0
        levels.add(2 * eachLevelHeight); // the height of the first level (height of 2nd floor)is 18 feet
        for (int i = 2; i <= levelCount; i++) {
            levels.add(levels.get(i - 1) + eachLevelHeight);
        }
        return levels;
    }

    private static List<Employee> initEmployees(int maxLevel, int employeeCount) {
        // return sorted employees based on arriveTime
        // also we can use gaussian data
        List<Employee> employees = new ArrayList<>();
        int seed = 24934541;
        Random random = new Random(seed);
        int maxTimeGap = 3;
        double arriveTime = 0;
        for (int i = 0; i < employeeCount; i++) {
            int targetLevel = random.nextInt(2, maxLevel + 1);
            arriveTime += random.nextDouble(maxTimeGap);
            employees.add(new Employee(arriveTime, targetLevel));
        }
        return employees;
    }

    public static void plot(List<Double> t0, List<Double> t1) {
        XYSeries data0 = new XYSeries("Solid Elevator");
        XYSeries data1 = new XYSeries("Flexible Elevator");
        for (int i = 0; i < t0.size(); i++) {
            data0.add(i, t0.get(i));
        }
        for (int i = 0; i < t1.size(); i++) {
            data1.add(i, t1.get(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(data0);
        dataset.addSeries(data1);

        String title = "Time Cost";
        JFreeChart chart = ChartFactory.createXYLineChart(title,"Volume", "Time/s", dataset);

        ChartFrame frame = new ChartFrame(title, chart);
        frame.pack();
        frame.setVisible(true);
    }
}