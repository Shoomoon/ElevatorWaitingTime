import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int maxLevel = 50;
        List<Double> levelHeight = generatorLevels(maxLevel);
        Building building = new Building(levelHeight);

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

        List<Double> timeCostForSolidRangeElevator = new ArrayList<>();
        List<Double> timeCostForFlexibleRangeElevator = new ArrayList<>();
        for (int i = 1; i <= 100000; i += 100) {
            List<Employee> employees = generatorEmployees(maxLevel, i);
            timeCostForSolidRangeElevator.add(building.timeCost(solidElevators, employees));
            timeCostForFlexibleRangeElevator.add(building.timeCost(flexibleElevators, employees));
        }
        plot(timeCostForSolidRangeElevator, timeCostForFlexibleRangeElevator);
    }

    private static List<Double> generatorLevels(int levelCount) {
        double eachLevelHeight = 2.743; // 9 feet = 2.743m
        List<Double> levels = new ArrayList<>();
        levels.add(2 * eachLevelHeight); // the height of the first level is 18 feet
        for (int i = 1; i < levelCount; i++) {
            levels.add(levels.get(i - 1) + eachLevelHeight);
        }
        return levels;
    }

    private static List<Employee> generatorEmployees(int maxLevel, int employeeCount) {
        // return sorted employees based on arriveTime
        // also we can use gaussian data
        List<Employee> employees = new ArrayList<>();
        int seed = 21651;
        Random random = new Random(seed);
        double arriveTime = 0;
        for (int i = 0; i < employeeCount; i++) {
            int targetLevel = random.nextInt(2, maxLevel + 1);
            arriveTime += random.nextDouble(100);
            employees.add(new Employee(arriveTime, targetLevel));
        }
        return employees;
    }

    private static void plot(List<Double> t0, List<Double> t1) {
        DefaultXYDataset dataset = new DefaultXYDataset();
        double[][] data0 = new double[2][t0.size()];
        double[][] data1 = new double[2][t1.size()];
        for (int i = 0; i < t0.size(); i++) {
            data0[0][i] = (i + 1) * 100;
            data0[1][i] = t0.get(i);
        }
        for (int i = 0; i < t1.size(); i++) {
            data1[0][i] = (i + 1) * 100;
            data1[1][i] = t1.get(i);
        }
        dataset.addSeries("Solid Elevator", data0);
        dataset.addSeries("Flexible Elevator", data1);

        String title = "Time Cost";
        JFreeChart chart = ChartFactory.createXYLineChart(title,"Volume", "Time/s", dataset);

        ChartFrame frame = new ChartFrame(title, chart);
        frame.pack();
        frame.setVisible(true);
    }
}