#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <iomanip>
using namespace std;

// ======== Base Class (Abstraction + Inheritance) =========
class Person {
protected:
    string name;
    string parentPhone;
public:
    virtual void display() = 0; // Pure virtual function (Polymorphism)
    void setName(string n) { name = n; }
    string getName() { return name; }
    void setParentPhone(string p) { parentPhone = p; }
    string getParentPhone() { return parentPhone; }
};

// ======== Derived Class Student (Encapsulation + Inheritance) =========
class Student : public Person {
private:
    int roll;
    string studentClass;
public:
    Student() {}
    Student(int r, string n, string c, string p) {
        roll = r;
        name = n;
        studentClass = c;
        parentPhone = p;
    }
    void setRoll(int r) { roll = r; }
    int getRoll() { return roll; }
    void setStudentClass(string c) { studentClass = c; }
    string getStudentClass() { return studentClass; }

    // Polymorphism in action
    void display() override {
        cout << left << setw(10) << roll 
             << setw(20) << name 
             << setw(10) << studentClass 
             << setw(15) << parentPhone << endl;
    }

    // File I/O format
    string toFileString() {
        return to_string(roll) + "," + name + "," + studentClass + "," + parentPhone + "\n";
    }

    static Student fromFileString(string line) {
        Student s;
        size_t pos = 0;
        vector<string> tokens;
        while ((pos = line.find(",")) != string::npos) {
            tokens.push_back(line.substr(0, pos));
            line.erase(0, pos + 1);
        }
        tokens.push_back(line);
        if (tokens.size() == 4) {
            s.setRoll(stoi(tokens[0]));
            s.setName(tokens[1]);
            s.setStudentClass(tokens[2]);
            s.setParentPhone(tokens[3]);
        }
        return s;
    }
};

// ======== Repository for File Handling =========
class StudentRepository {
private:
    string filename = "studentdata.txt";
public:
    void saveAll(vector<Student>& students) {
        ofstream fout(filename, ios::trunc);
        for (auto& s : students) fout << s.toFileString();
        fout.close();
    }

    vector<Student> loadAll() {
        vector<Student> students;
        ifstream fin(filename);
        string line;
        while (getline(fin, line)) {
            if (!line.empty())
                students.push_back(Student::fromFileString(line));
        }
        fin.close();
        return students;
    }
};

// ======== Menu-driven Program =========
class StudentManagement {
private:
    vector<Student> students;
    StudentRepository repo;
public:
    StudentManagement() {
        students = repo.loadAll(); // Load data at start
    }
    ~StudentManagement() {
        repo.saveAll(students); // Save data at end
    }

    void addStudent() {
        int roll;
        string name, cls, phone;
        cout << "Enter Roll No: ";
        cin >> roll;
        cin.ignore();
        cout << "Enter Name: ";
        getline(cin, name);
        cout << "Enter Class: ";
        getline(cin, cls);
        cout << "Enter Parent Phone: ";
        getline(cin, phone);
        students.push_back(Student(roll, name, cls, phone));
        cout << "Student added successfully!\n";
    }

    void editStudent() {
        int roll;
        cout << "Enter Roll No of Student to Edit: ";
        cin >> roll;
        cin.ignore();
        for (auto& s : students) {
            if (s.getRoll() == roll) {
                string name, cls, phone;
                cout << "Enter New Name: ";
                getline(cin, name);
                cout << "Enter New Class: ";
                getline(cin, cls);
                cout << "Enter New Parent Phone: ";
                getline(cin, phone);
                s.setName(name);
                s.setStudentClass(cls);
                s.setParentPhone(phone);
                cout << "Record updated successfully!\n";
                return;
            }
        }
        cout << "Student not found!\n";
    }

    void deleteStudent() {
        int roll;
        cout << "Enter Roll No of Student to Delete: ";
        cin >> roll;
        for (auto it = students.begin(); it != students.end(); ++it) {
            if (it->getRoll() == roll) {
                students.erase(it);
                cout << "Record deleted successfully!\n";
                return;
            }
        }
        cout << "Student not found!\n";
    }

    void showAllStudents() {
        if (students.empty()) {
            cout << "No records found!\n";
            return;
        }
        cout << left << setw(10) << "Roll" 
             << setw(20) << "Name" 
             << setw(10) << "Class" 
             << setw(15) << "Parent Phone" << endl;
        cout << "-------------------------------------------------------------\n";
        for (auto& s : students) s.display();
    }

    void menu() {
        int choice;
        do {
            cout << "\n===== Student Record Management =====\n";
            cout << "1. Add Student\n";
            cout << "2. Edit Student\n";
            cout << "3. Delete Student\n";
            cout << "4. Show All Students\n";
            cout << "5. Exit\n";
            cout << "Enter your choice: ";
            cin >> choice;
            cin.ignore();
            switch (choice) {
                case 1: addStudent(); break;
                case 2: editStudent(); break;
                case 3: deleteStudent(); break;
                case 4: showAllStudents(); break;
                case 5: cout << "Exiting...\n"; break;
                default: cout << "Invalid choice! Try again.\n";
            }
        } while (choice != 5);
    }
};

// ======== Main =========
int main() {
    StudentManagement sm;
    sm.menu();
    return 0;
}
