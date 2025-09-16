// import java.io.*;
// import java.util.*;

// ======== Base Class (Abstraction + Inheritance) =========
abstract class Person {
    protected String name;
    protected String parentPhone;

    public abstract void display(); // Pure virtual equivalent

    // Encapsulation: Getters/Setters
    public void setName(String n) { this.name = n; }
    public String getName() { return this.name; }

    public void setParentPhone(String p) { this.parentPhone = p; }
    public String getParentPhone() { return this.parentPhone; }
}

// ======== Derived Class Student (Encapsulation + Inheritance) =========
class Student extends Person {
    private int roll;
    private String studentClass;

    public Student() {}

    public Student(int r, String n, String c, String p) {
        this.roll = r;
        this.name = n;
        this.studentClass = c;
        this.parentPhone = p;
    }

    // Encapsulation: Getters/Setters
    public void setRoll(int r) { this.roll = r; }
    public int getRoll() { return this.roll; }

    public void setStudentClass(String c) { this.studentClass = c; }
    public String getStudentClass() { return this.studentClass; }

    // Polymorphism: Method overriding
    @Override
    public void display() {
        System.out.printf("%-10d %-20s %-10s %-15s\n", roll, name, studentClass, parentPhone);
    }

    // File I/O helper (convert to string for saving)
    public String toFileString() {
        return roll + "," + name + "," + studentClass + "," + parentPhone;
    }

    // Static method to create Student from file line
    public static Student fromFileString(String line) {
        String[] tokens = line.split(",");
        if (tokens.length == 4) {
            try {
                int r = Integer.parseInt(tokens[0]);
                String n = tokens[1];
                String c = tokens[2];
                String p = tokens[3];
                return new Student(r, n, c, p);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
