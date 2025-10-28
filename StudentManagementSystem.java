import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * 1. Student Class: Represents an individual student.
 * Implements Serializable to allow saving/loading objects to/from a file.
 */
class Student implements Serializable {
    private static final long serialVersionUID = 1L; // For serialization version control
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return String.format("| %-20s | %-12d | %-10s |", name, rollNumber, grade);
    }
}

/**
 * 2. StudentManager Class: Manages the collection of students and handles file I/O.
 * Renamed from StudentManagementSystem to resolve naming conflict with the main class.
 */
class StudentManager {
    private List<Student> students;
    private static final String FILE_NAME = "students.ser"; // File for persistence

    public StudentManager() {
        // 4. Load student data when the system is initialized
        this.students = loadStudents();
    }

    // --- Core Management Methods (2, 5) ---

    /**
     * Adds a new student to the collection.
     * @param student The Student object to add.
     */
    public void addStudent(Student student) {
        if (students.stream().anyMatch(s -> s.getRollNumber() == student.getRollNumber())) {
            System.out.println("\n[Error] A student with Roll Number " + student.getRollNumber() + " already exists.");
            return;
        }
        students.add(student);
        saveStudents(); // Save immediately after addition
        System.out.println("\n[Success] Student added successfully: " + student.getName());
    }

    /**
     * Removes a student by roll number.
     * @param rollNumber The roll number of the student to remove.
     */
    public void removeStudent(int rollNumber) {
        if (students.removeIf(s -> s.getRollNumber() == rollNumber)) {
            saveStudents(); // Save immediately after removal
            System.out.println("\n[Success] Student with Roll Number " + rollNumber + " removed successfully.");
        } else {
            System.out.println("\n[Error] Student with Roll Number " + rollNumber + " not found.");
        }
    }

    /**
     * Searches for a student by roll number.
     * @param rollNumber The roll number to search for.
     * @return The found Student object, or null if not found.
     */
    public Student searchStudent(int rollNumber) {
        return students.stream()
                       .filter(s -> s.getRollNumber() == rollNumber)
                       .findFirst()
                       .orElse(null);
    }

    /**
     * Displays all students in a formatted table.
     */
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("\n--- No students currently registered. ---");
            return;
        }

        System.out.println("\n--- Registered Students ---");
        System.out.println("+----------------------+--------------+------------+");
        System.out.println("| Name                 | Roll Number  | Grade      |");
        System.out.println("+----------------------+--------------+------------+");
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("+----------------------+--------------+------------+");
    }

    /**
     * Edits the information of an existing student.
     * @param rollNumber The roll number of the student to edit.
     * @param newName The new name.
     * @param newGrade The new grade.
     * @return true if successful, false otherwise.
     */
    public boolean editStudent(int rollNumber, String newName, String newGrade) {
        Student student = searchStudent(rollNumber);
        if (student != null) {
            student.setName(newName);
            student.setGrade(newGrade);
            saveStudents(); // Save changes
            System.out.println("\n[Success] Student Roll No. " + rollNumber + " updated.");
            return true;
        } else {
            System.out.println("\n[Error] Student with Roll Number " + rollNumber + " not found.");
            return false;
        }
    }

    // --- Persistence Methods (4) ---

    /**
     * Saves the current list of students to a file using serialization.
     */
    public void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("[Persistence Error] Could not save data: " + e.getMessage());
        }
    }

    /**
     * Loads the list of students from the file.
     * @return The loaded list of students, or a new empty list if file doesn't exist or load fails.
     */
    @SuppressWarnings("unchecked") // Suppressing warning for unchecked cast from Object to List<Student>
    private List<Student> loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("\n[Info] Loading student data from file...");
            return (List<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("[Info] Data file not found. Starting with an empty list.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[Persistence Error] Failed to load data. Starting with an empty list. Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

/**
 * 3, 5, 6. Main Class: Handles the console user interface, menu, and input validation.
 */
public class StudentManagementSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // FIX: Renamed class and removed 'new new' syntax error.
        StudentManager system = new StudentManager(); 
        int choice = 0;

        System.out.println("\n--- WELCOME TO THE STUDENT MANAGEMENT SYSTEM ---");

        do {
            displayMenu();
            try {
                // 6. Input validation for menu choice
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addStudentHandler(scanner, system);
                        break;
                    case 2:
                        editStudentHandler(scanner, system);
                        break;
                    case 3:
                        removeStudentHandler(scanner, system);
                        break;
                    case 4:
                        searchStudentHandler(scanner, system);
                        break;
                    case 5:
                        system.displayAllStudents();
                        break;
                    case 6:
                        System.out.println("\n[Info] Saving data and exiting. Goodbye!");
                        system.saveStudents();
                        break;
                    default:
                        System.out.println("\n[Error] Invalid choice. Please select an option between 1 and 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n[Error] Invalid input. Please enter a number for the menu option.");
                scanner.nextLine(); // Clear buffer
                choice = 0; // Reset choice to keep loop running
            }
        } while (choice != 6);

        scanner.close();
    }

    /**
     * Displays the main menu options.
     */
    private static void displayMenu() {
        System.out.println("\n------------------------------------------------");
        System.out.println("1. Add New Student");
        System.out.println("2. Edit Student Information");
        System.out.println("3. Remove Student");
        System.out.println("4. Search Student by Roll Number");
        System.out.println("5. Display All Students");
        System.out.println("6. Exit Application");
        System.out.println("------------------------------------------------");
    }

    // --- Input Handlers with Validation (6) ---
    // Handler methods now accept StudentManager type

    private static void addStudentHandler(Scanner scanner, StudentManager system) {
        System.out.println("\n--- Add New Student ---");
        String name;
        int rollNumber = -1;
        String grade;

        // 6. Name Validation
        do {
            System.out.print("Enter student name (required): ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("[Validation Error] Name cannot be empty.");
            }
        } while (name.isEmpty());

        // 6. Roll Number Validation (Must be positive integer)
        while (rollNumber <= 0) {
            try {
                System.out.print("Enter roll number (positive integer): ");
                rollNumber = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (rollNumber <= 0) {
                    System.out.println("[Validation Error] Roll number must be a positive integer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("[Validation Error] Invalid input. Please enter a number for roll number.");
                scanner.nextLine(); // Clear buffer
                rollNumber = -1; // Reset
            }
        }

        // 6. Grade Validation
        do {
            System.out.print("Enter student grade (e.g., A, B+, Pass, required): ");
            grade = scanner.nextLine().trim();
            if (grade.isEmpty()) {
                System.out.println("[Validation Error] Grade cannot be empty.");
            }
        } while (grade.isEmpty());

        Student newStudent = new Student(name, rollNumber, grade);
        system.addStudent(newStudent);
    }

    private static void removeStudentHandler(Scanner scanner, StudentManager system) {
        System.out.println("\n--- Remove Student ---");
        try {
            System.out.print("Enter roll number of student to remove: ");
            int rollNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            system.removeStudent(rollNumber);
        } catch (InputMismatchException e) {
            System.out.println("[Validation Error] Invalid input. Please enter a number for roll number.");
            scanner.nextLine(); // Clear buffer
        }
    }

    private static void searchStudentHandler(Scanner scanner, StudentManager system) {
        System.out.println("\n--- Search Student ---");
        try {
            System.out.print("Enter roll number of student to search: ");
            int rollNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            Student student = system.searchStudent(rollNumber);

            if (student != null) {
                System.out.println("\n[Result] Student Found:");
                System.out.println("+----------------------+--------------+------------+");
                System.out.println("| Name                 | Roll Number  | Grade      |");
                System.out.println("+----------------------+--------------+------------+");
                System.out.println(student);
                System.out.println("+----------------------+--------------+------------+");
            } else {
                System.out.println("\n[Result] Student with Roll Number " + rollNumber + " not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("[Validation Error] Invalid input. Please enter a number for roll number.");
            scanner.nextLine(); // Clear buffer
        }
    }

    private static void editStudentHandler(Scanner scanner, StudentManager system) {
        System.out.println("\n--- Edit Student Information ---");
        int rollNumber = -1;
        String newName;
        String newGrade;

        // 6. Roll Number Validation for search
        while (rollNumber <= 0) {
            try {
                System.out.print("Enter roll number of student to edit: ");
                rollNumber = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (rollNumber <= 0) {
                    System.out.println("[Validation Error] Roll number must be a positive integer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("[Validation Error] Invalid input. Please enter a number for roll number.");
                scanner.nextLine(); // Clear buffer
                rollNumber = -1; // Reset
            }
        }

        // Check if student exists before asking for new details
        Student existingStudent = system.searchStudent(rollNumber);
        if (existingStudent == null) {
            System.out.println("\n[Error] Student with Roll Number " + rollNumber + " not found. Cannot edit.");
            return;
        }

        // 6. New Name Validation
        do {
            System.out.println("Current Name: " + existingStudent.getName());
            System.out.print("Enter new student name (leave blank to keep current): ");
            newName = scanner.nextLine().trim();
            if (newName.isEmpty()) {
                newName = existingStudent.getName(); // Keep current name
            }
        } while (false); // Only runs once, keeps current name if blank

        // 6. New Grade Validation
        do {
            System.out.println("Current Grade: " + existingStudent.getGrade());
            System.out.print("Enter new student grade (leave blank to keep current): ");
            newGrade = scanner.nextLine().trim();
            if (newGrade.isEmpty()) {
                newGrade = existingStudent.getGrade(); // Keep current grade
            }
        } while (false); // Only runs once, keeps current grade if blank

        // Perform edit
        system.editStudent(rollNumber, newName, newGrade);
    }
}