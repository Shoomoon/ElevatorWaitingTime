import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {
    private Elevator elevator;
    private double accuracy;
    private double acc;
    private double brakeAcc;
    private double mxSpeed;
    private List<Employee> passengers;
    private Building building;
    @BeforeEach
    void setUp() {
        int maxLevel = 20;
        accuracy = 0.001;
        acc = 1.0;
        brakeAcc = 1.0;
        mxSpeed = 5.0;
        elevator = new Elevator(1, maxLevel, acc, brakeAcc, mxSpeed);
        double arriveTime = 3.0;
        passengers = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            passengers.add(new Employee(arriveTime * i + 1, i + 1));
        }
        double eachLevelHeight = 2.743; // 9 feet = 2.743m
        List<Double> levels = new ArrayList<>();
        levels.add(0.0); // the height of the first floor is 0
        levels.add(2 * eachLevelHeight); // the height of the first level (height of 2nd floor)is 18 feet
        for (int i = 2; i <= maxLevel; i++) {
            levels.add(levels.get(i - 1) + eachLevelHeight);
        }
        building = new Building(levels);
    }

    @Test
    void updateTimeAtLevel1() {
        Assertions.assertEquals(5, elevator.updateTimeAtLevel1(5), accuracy);
        Assertions.assertEquals(10, elevator.updateTimeAtLevel1(5), accuracy);
        Assertions.assertEquals(15.03, elevator.updateTimeAtLevel1(5.03), accuracy);
    }

    @Test
    void timeCost() {
        assertTrue(timeCostForDistance(25, elevator.timeCost(25)));
        assertTrue(timeCostForDistance(10, elevator.timeCost(10)));
        assertTrue(timeCostForDistance(250, elevator.timeCost(250)));
    }
    private boolean timeCostForDistance(double distance, double timeCost) {
        double maxSpeed = elevator.getMaxSpeed();
        double a = elevator.getAcceleration();
        double ba = elevator.getBrakeAcceleration();

        double timeBase = maxSpeed / a + maxSpeed / ba;
        double distanceBase = maxSpeed * timeBase / 2;
        if (distance < distanceBase) {
            double t0 = maxSpeed / a * timeCost / timeBase;
            double t1 = maxSpeed / ba * timeCost / timeBase;
            if (!doubleEqual(t0 + t1, timeCost)) {
                return false;
            }
            double d = 0.5 * timeCost * t0 * a;
            if (!doubleEqual(d, distance)) {
                return false;
            }
        } else {
            double t0 = maxSpeed / a;
            double t1 = maxSpeed / ba;
            double t2 = timeCost - t0 - t1;
            double d = 0.5*maxSpeed*(t0 + t1) + maxSpeed * t2;
            if (!doubleEqual(distance, d)) {
                return false;
            }
        }
        return true;
    }
    private boolean doubleEqual(double d0, double d1) {
        return Math.abs(d0 - d1) <= accuracy;
    }

    @Test
    void getTimeAtLevel1() {
        elevator.updateTimeAtLevel1(5);
        Assertions.assertEquals(5, elevator.getTimeAtLevel1(), accuracy);
        elevator.updateTimeAtLevel1(11.77);
        Assertions.assertEquals(16.77, elevator.getTimeAtLevel1(), accuracy);
    }

    @Test
    void getAcceleration() {
        Assertions.assertEquals(1, elevator.getAcceleration(), accuracy);
    }

    @Test
    void getBrakeAcceleration() {
        Assertions.assertEquals(1, elevator.getBrakeAcceleration(), accuracy);
    }

    @Test
    void getMaxSpeed() {
        Assertions.assertEquals(mxSpeed, elevator.getMaxSpeed(), accuracy);
    }

    @Test
    void checkIfLaunch() {
        assertFalse(elevator.checkIfLaunch(2));
        for (int i = 0; i < 9; i++) {
            elevator.addPassenger(passengers.get(i));
            // always launch when waiting time > elevator max waiting time
            assertTrue(elevator.checkIfLaunch(passengers.get(i).getArriveTime() + 11));
            assertFalse(elevator.checkIfLaunch(passengers.get(i + 1).getArriveTime()));
        }
        elevator.addPassenger(passengers.get(9));
        assertTrue(elevator.checkIfLaunch(passengers.get(9).getArriveTime()));
        assertTrue(elevator.checkIfLaunch(passengers.get(10).getArriveTime()));
    }

    @Test
    void launch() {
        elevator.launch(building);
        assertEquals(0, elevator.getTimeAtLevel1(), accuracy);
        elevator.addPassenger(passengers.get(0));
        elevator.launch(building);
        assertEquals(elevator.getTimeAtLevel1(), elevator.timeCost(building.getDistanceBetweenLevels(1, passengers.get(0).getTargetLevel())), accuracy);
    }

    @Test
    void acceptPassenger() {
        Employee employee = new Employee(0, 40);
        assertFalse(elevator.acceptPassenger(employee));

        for (int i = 0; i < 10; i++) {
            assertTrue(elevator.acceptPassenger(passengers.get(i)));
            elevator.addPassenger(passengers.get(i));
        }
        assertFalse(elevator.acceptPassenger(passengers.get(10)));
    }

    @Test
    void compareTo() {
        Elevator elevator1 = new Elevator(1, 20, acc, brakeAcc, mxSpeed);
        assertEquals(0, elevator.compareTo(elevator1));
        assertEquals(0, elevator1.compareTo(elevator));
        elevator1.addPassenger(passengers.get(0));
        assertEquals(-1, elevator.compareTo(elevator1));
        assertEquals(1, elevator1.compareTo(elevator));
    }

    @Test
    void addPassenger() {
        for (int i = 0; i < 10; i++) {
            assertTrue(elevator.acceptPassenger(passengers.get(i)));
            elevator.addPassenger(passengers.get(i));
        }
        assertThrowsExactly(RuntimeException.class, () -> elevator.addPassenger(passengers.get(10)), "The number of passengers has reached the capacity of the elevator!");
    }
}