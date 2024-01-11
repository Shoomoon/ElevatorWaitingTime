# ElevatorWaitingTime
This project aims to compare the total cost to deliver employees with elevators reaching all levels to that with elevators reaching part of levels.
Add this line to test if working on different computers is fine.
Confirmed that working on different computers is fine.

## Fields and Method introduction
### Class Main
// init building, elevators, and test case. After time cost calculated, plot the result to compare.
```
public static void main(String[] args)
```
// generate different test cases
```
private static List<List<Employee>> generatorTestCases(int maxLevel)
```
// initialize the height of each level
```
private static List<Double> initLevelHeight(int levelCount)
```
// generator employees of test case
```
private static List<Employee> initEmployees(int maxLevel, int employeeCount) {
```
// plot the result for comparison
```
private static void plot(List<Double> t0, List<Double> t1)
```
### Class Employee
// arrive time
```
private final double arriveTime
```
// target level
```
private final int targetLevel
```
// construct method
```
public Employee(double arriveTime, int targetLevel)
```
// getter of arrive time
```
public double getArriveTime()
```
// getter of target level
```
public int getTargetLevel()
```
// find the elevator to enter
```
public Elevator findTheBestElevator(List<Elevator> elevators)
```
### Class Elevator
// the lowest level that this elevator can reach except 1st floor
```
private final int startLevel
```
// the highest level that this elevator can reach
```
private final int endLevel
```
// the acceleration when accelerate
```
private final double acceleration
```
// the acceleration when stop
```
private final double brakeAcceleration
```
// the maximum speed
```
private final double maxSpeed
```
// the next arriving time when on level 1
```
private double timeAtLevel1
```
// human capacity, maximum 10 employees
```
private final int capacity = 10
```
// weight capacity, maximum weight 1300kg
```
private final int weightCapacity = 1300
```
// the maximum waiting time of the elevator when not empty
```
private final double maxWaitingTime = 10
```
// construct method
```
public Elevator(int level0, int level1, double acceleration, double brakeAcceleration, double maxspeed)
```
// update the next arriving time
```
public double updateTimeAtLevel1(double timeDelivery)
```
// calculate the time cost to move as long as distance
```
public double timeCost(double distance)
```
// getter of acceleration
```
public double getAcceleration()
```
// getter of brake acceleration
```
public double getBrakeAcceleration()
```
// getter of maximum speed
```
public double getMaxSpeed()
```
// getter of time at level 1
```
public double getTimeAtLevel1()
```
// check if the elevator can launch or not
```
public boolean checkIfLaunch(double arriveTime)
```
// calculate the time cost to deliver all passengers, and update the time at level 1
```
public double launch(Building building)
```
// check if the elevator accept the employee base on the capacity
```
public boolean acceptPassenger(Employee employee)
```
// check if this elevator is better than another elevator to enter
```
public boolean betterThan(Elevator otherElevator)
```
// add new passenger
```
public void addPassenger(Employee employee)
```
### Class Building
// height of each level
```
private final List<Double> levelHeight```
```
// construct method
```
public Building(List<Double> levelHeight)
```
// time cost for all elevators to deliver all employees
```
public List<Double> timeCost(List<Elevator> elevators, List<Employee> sortedEmployees)
```
// traversal all elevators to check if any elevator launches  
```
private boolean launchElevators(List<Elevator> elevators, double nextArriveTime)
```
// optimizely to find a queue to join
```
private void findElevatorToQueue(Employee e, List<Queue<Employee>> queues)
```
// calcute the total time cost to deliver all passengers
```
private double timeCostToDeliverAll(Elevator elevator, List<Employee> employees)
```
// get the distance between 2 levels
```
public double getDistanceBetweenLevels(int level0, int level1)
```
// find the latest time at level 1 for all elevators
```
private Double latestTime(List<Elevator> elevators)
```