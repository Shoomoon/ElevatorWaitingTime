import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    public void plotTest() {
        List<Double> t0 = new ArrayList<>();
        t0.add(3.6);
        t0.add(7.3);
        List<Double> t1 = new ArrayList<>();
        t1.add(4.5);
        t1.add(8.1);
        Main.plot(t0, t1);
        System.out.println("Finished.");
    }

    @org.junit.jupiter.api.Test
    void main() {
    }

    @org.junit.jupiter.api.Test
    void plot() {
    }
}