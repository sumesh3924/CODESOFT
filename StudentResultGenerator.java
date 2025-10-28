import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * StudentResultGenerator: A program to calculate a student's total marks, 
 * average percentage, and final grade based on 6 subjects.
 */
public class StudentResultGenerator {

    // Define the subjects and total number of subjects
    private static final String[] SUBJECTS = 
        {"IT", "Maths", "English", "Biology", "Physics", "Chemistry"};
    private static final int NUM_SUBJECTS = SUBJECTS.length;
    private static final int MAX_MARKS_PER_SUBJECT = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] marks = new int[NUM_SUBJECTS];

        System.out.println("--- Student Result Calculation ---");
        System.out.println("Please enter the marks obtained (out of 100) for the following " + NUM_SUBJECTS + " subjects:");

        // 1. Input: Take marks obtained (out of 100) in 6 subjects
        if (inputMarks(scanner, marks)) {
            // 2. Calculate Total Marks
            int totalMarks = calculateTotalMarks(marks);

            // 3. Calculate Average Percentage
            double averagePercentage = calculateAveragePercentage(totalMarks);

            // 4. Grade Calculation
            String grade = determineGrade(averagePercentage);

            // 5. Display Results
            displayResults(totalMarks, averagePercentage, grade);
        }

        scanner.close();
    }

    /**
     * Prompts the user to input marks for each subject and validates the input.
     * @param scanner The Scanner object for input.
     * @param marks The array to store the subject marks.
     * @return true if all marks were successfully entered, false otherwise.
     */
    private static boolean inputMarks(Scanner scanner, int[] marks) {
        for (int i = 0; i < NUM_SUBJECTS; i++) {
            boolean valid = false;
            while (!valid) {
                try {
                    System.out.print("Enter marks for " + SUBJECTS[i] + " (0-" + MAX_MARKS_PER_SUBJECT + "): ");
                    int mark = scanner.nextInt();
                    
                    if (mark >= 0 && mark <= MAX_MARKS_PER_SUBJECT) {
                        marks[i] = mark;
                        valid = true;
                    } else {
                        System.out.println("Error: Marks must be between 0 and " + MAX_MARKS_PER_SUBJECT + ". Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a whole number.");
                    scanner.next(); // Clear the invalid input from the scanner
                }
            }
        }
        return true;
    }

    /**
     * Calculates the sum of all subject marks.
     * @param marks The array of subject marks.
     * @return The total marks obtained.
     */
    private static int calculateTotalMarks(int[] marks) {
        int total = 0;
        for (int mark : marks) {
            total += mark;
        }
        return total;
    }

    /**
     * Calculates the average percentage.
     * Total Marks is divided by the total possible marks (NUM_SUBJECTS * 100).
     * @param totalMarks The sum of all subject marks.
     * @return The average percentage.
     */
    private static double calculateAveragePercentage(int totalMarks) {
        // Total possible marks is NUM_SUBJECTS * MAX_MARKS_PER_SUBJECT
        // Since all subjects are out of 100, the average is simply Total Marks / NUM_SUBJECTS.
        return (double) totalMarks / NUM_SUBJECTS; 
    }

    /**
     * Assigns a grade based on the average percentage achieved.
     * @param averagePercentage The calculated average percentage.
     * @return The assigned grade (e.g., "A+", "B", "F").
     */
    private static String determineGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return "A+ (Excellent)";
        } else if (averagePercentage >= 80) {
            return "A (Very Good)";
        } else if (averagePercentage >= 70) {
            return "B (Good)";
        } else if (averagePercentage >= 60) {
            return "C (Satisfactory)";
        } else if (averagePercentage >= 50) {
            return "D (Pass)";
        } else {
            return "F (Fail)";
        }
    }

    /**
     * Displays the calculated results to the user.
     * @param totalMarks The total marks obtained.
     * @param averagePercentage The average percentage.
     * @param grade The assigned grade.
     */
    private static void displayResults(int totalMarks, double averagePercentage, String grade) {
        System.out.println("\n--- Result Summary ---");
        System.out.println("Total Subjects: " + NUM_SUBJECTS);
        System.out.println("Total Possible Marks: " + (NUM_SUBJECTS * MAX_MARKS_PER_SUBJECT));
        System.out.println("---------------------------------");
        System.out.println("1. Total Marks Obtained: " + totalMarks + " / " + (NUM_SUBJECTS * MAX_MARKS_PER_SUBJECT));
        // Use String.format for cleaner output of the percentage
        System.out.printf("2. Average Percentage: %.2f%%\n", averagePercentage);
        System.out.println("3. Assigned Grade: " + grade);
        System.out.println("---------------------------------");
    }
}
