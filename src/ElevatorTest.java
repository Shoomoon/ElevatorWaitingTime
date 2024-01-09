import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {
    private Elevator elevator;
    private double accuracy;
    @BeforeEach
    void setUp() {
        elevator = new Elevator(0, 10, 1, 1, 5);
        accuracy = 0.001;
    }

    @Test
    void updateTimeAtLevel1() {
        assertEquals(5, elevator.updateTimeAtLevel1(5), accuracy);
        assertEquals(10, elevator.updateTimeAtLevel1(5), accuracy);
        assertEquals(15.03, elevator.updateTimeAtLevel1(5.03), accuracy);
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
        assertEquals(5, elevator.getTimeAtLevel1(), accuracy);
        elevator.updateTimeAtLevel1(11.77);
        assertEquals(16.77, elevator.getTimeAtLevel1(), accuracy);
    }
}