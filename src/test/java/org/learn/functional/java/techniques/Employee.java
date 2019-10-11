package org.learn.functional.java.techniques;

public class Employee {
    private final int id;
    private final String fName;
    private final String lName;

    public Employee(int id, String fName, String lName) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
    }

    public int getId() {
        return id;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }
}
