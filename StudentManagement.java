import java.io.*;
import java.util.*;

// ======== Repository for File Handling =========
class StudentRepository {
    private final String filename = "studentdata.txt";

    public void saveAll(List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Student s : students) {
                bw.write(s.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public List<Student> loadAll() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                Student s = Student.fromFileString(line);
                if (s != null) students.add(s);
            }
        } catch (IOException e) {
            // Ignore if file not found on first run
        }
        return students;
    }
}

// ======== Menu-driven Program =========
public class StudentManagement {
    private List<Student> students;
    private StudentRepository repo;

    public StudentManagement() {
        repo = new StudentRepository();
        students = repo.loadAll(); // Load at start
    }

    public void exit() {
        repo.saveAll(students); // Save at end
        System.out.println("Data saved. Exiting...");
    }
    public void addStudent(Scanner sc) {
        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();
        sc.nextLine(); // clear buffer
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Class: ");
        String cls = sc.nextLine();
        System.out.print("Enter Parent Phone: ");
        String phone = sc.nextLine();

        students.add(new Student(roll, name, cls, phone));
        repo.saveAll(students); // Save at end
        System.out.println("Student added successfully!");
    }

    public void editStudent(Scanner sc) {
        System.out.print("Enter Roll No of Student to Edit: ");
        int roll = sc.nextInt();
        sc.nextLine();
        for (Student s : students) {
            if (s.getRoll() == roll) {
                System.out.print("Enter New Name: ");
                String name = sc.nextLine();
                System.out.print("Enter New Class: ");
                String cls = sc.nextLine();
                System.out.print("Enter New Parent Phone: ");
                String phone = sc.nextLine();
                s.setName(name);
                s.setStudentClass(cls);
                s.setParentPhone(phone);
                System.out.println("Record updated successfully!");
                return;
            }
        }
        System.out.println("Student not found!");
    }

    public void deleteStudent(Scanner sc) {
        System.out.print("Enter Roll No of Student to Delete: ");
        int roll = sc.nextInt();
        sc.nextLine();
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            Student s = it.next();
            if (s.getRoll() == roll) {
                it.remove();
                System.out.println("Record deleted successfully!");
                return;
            }
        }
        System.out.println("Student not found!");
    }

    public void showAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No records found!");
            return;
        }
        System.out.printf("%-10s %-20s %-10s %-15s\n", "Roll", "Name", "Class", "Parent Phone");
        System.out.println("-------------------------------------------------------------");
        for (Student s : students) s.display();
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n===== Student Record Management =====");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Show All Students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addStudent(sc);
                case 2 -> editStudent(sc);
                case 3 -> deleteStudent(sc);
                case 4 -> showAllStudents();
                case 5 -> exit();
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 5);
        sc.close();
    }

    // ======== Main =========
    public static void main(String[] args) {
        StudentManagement sm = new StudentManagement();
        sm.menu();
    }
}
