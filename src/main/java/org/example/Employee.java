package org.example;

public class Employee {
    private String id;
    private String name;
    private String position;
    private double dailySalary;
    private double daysPresent;

    public Employee(String id, String name, String position, double dailySalary, double daysPresent) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.dailySalary = dailySalary;
        this.daysPresent = daysPresent;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public double getDailySalary() { return dailySalary; }
    public double getDaysPresent() { return daysPresent; }
    public double computeGrossSalary() {
        return dailySalary * daysPresent;
    }
}
