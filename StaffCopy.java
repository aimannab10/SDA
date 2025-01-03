import java.util.Scanner;

public class StaffCopy extends UserCopy implements UserActionsCopy {
    private String role;
    private boolean hasStudents = true; // Flag to check if students exist in the class

    public StaffCopy(String name, int age) {
    super(name, age);
    this.role = "Unknown";  // Default role if not provided
}

public StaffCopy(String name, int age, String role) {
    super(name, age);
    this.role = role;
}

    @Override
    public void displayOptions() {
        Scanner scanner = new Scanner(System.in);

        // Prompt for PIN before accessing the portal
        int pinAttempts = 3; // Limit to 3 attempts
        boolean pinVerified = false;

        while (pinAttempts > 0 && !pinVerified) {
            System.out.print("Enter the 4-digit PIN to access the Staff Portal: ");
            String pin = scanner.nextLine().trim();

            if (pin.equals("1111")) {
                pinVerified = true;  // PIN is correct
            } else {
                pinAttempts--;
                System.out.println("Incorrect PIN. You have " + pinAttempts + " attempts left.");
            }

            if (pinAttempts == 0) {
                System.out.println("You have exceeded the number of attempts. Access denied.");
                return;  // Exit to main menu if no more attempts
            }
        }

        // Once PIN is verified, show options
        System.out.println("\nStaff Portal - Choose an option:");
        System.out.println("1. Update Screening");
        System.out.println("2. Update Attendance");
        System.out.println("3. Update Curricular");
        System.out.println("4. Update Report Card");
        System.out.println("5. Exit Staff Portal");
        System.out.print("\nPlease enter your choice (1-5): ");
    }

    @Override
    public void performAction(int choice) {
        Scanner scanner = new Scanner(System.in);
        switch (choice) {
            case 1:
                System.out.println("Screening has been updated");
                break;
            case 2:
                if (hasStudents) {
                    updateAttendance(scanner);
                } else {
                    System.out.println("No students exist in the class.");
                }
                break;
            case 3: 
                System.out.println("Curricular has been updated");
                break;
            case 4:
                if (hasStudents) {
                    updateReportCard(scanner);
                } else {
                    System.out.println("No students exist in the class.");
                }
                break;
            case 5:
                System.out.println("Exiting Staff Portal.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void updateAttendance(Scanner scanner) {
        System.out.print("Enter the attendance status for the student (Present/Absent): ");
        String status = scanner.nextLine().trim();
        if (status.equalsIgnoreCase("Present") || status.equalsIgnoreCase("Absent")) {
            System.out.println("Attendance updated to " + status + " for the student.");
        } else {
            System.out.println("Invalid status. Please enter 'Present' or 'Absent'.");
        }
    }

    private void updateReportCard(Scanner scanner) {
        System.out.print("Enter the grade for the student: ");
        String grade = scanner.nextLine().trim();
        System.out.println("Report card updated with grade: " + grade);
    }
    
}