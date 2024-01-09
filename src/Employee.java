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
}
