import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {
    private Building building;
    private List<Employee> employees;
    private List<Elevator> elevators;
    int maxLevel = 20;
    private double accuracy;

    @BeforeEach
    void setUp() {
        accuracy = 0.001;
        double eachLevelHeight = 2.743; // 9 feet = 2.743m
        List<Double> levels = new ArrayList<>();
        levels.add(0.0); // the height of the first floor is 0
        levels.add(2 * eachLevelHeight); // the height of the first level (height of 2nd floor)is 18 feet
        for (int i = 2; i <= maxLevel; i++) {
            levels.add(levels.get(i - 1) + eachLevelHeight);
        }
        building = new Building(levels);
        double arriveTimeGap = 3.0;
        employees = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            employees.add(new Employee(3 * i + 1, i + 1));
        }

        elevators = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            elevators.add(new Elevator(1, maxLevel, 1, 1, 5));
        }
    }

    @Test
    void timeCost() {
        List<Employee> passengers = new ArrayList<>();
        passengers.add(new Employee(0, 3));
        passengers.add(new Employee(1, 2));
        passengers.add(new Employee(2, 2));
        passengers.add(new Employee(2, 3));
        List<Employee> passengers2 = new ArrayList<>();
        passengers2.add(new Employee(2, 3));
        List<Double> actual = building.timeCost(elevators, passengers);
        List<Double> actual2 = building.timeCost(elevators, passengers2);
        assertEquals(actual.get(actual.size() - 1), actual2.get(actual2.size() - 1), accuracy);
    }

    @Test
    void getDistanceBetweenLevels() {
        assertEquals(2.743 * 2, building.getDistanceBetweenLevels(1, 2), accuracy);
        assertEquals(2.743, building.getDistanceBetweenLevels(2, 3), accuracy);
        assertEquals(2.743*4, building.getDistanceBetweenLevels(2, 6), accuracy);
    }
}