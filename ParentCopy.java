import java.util.Scanner;

public class ParentCopy extends UserCopy implements UserActionsCopy {
    private String childName;
    private int childAge;
    private String suggestedProgram;
    private String attendanceStatus;  // New field to track attendance
    private String reportCardGrade;   // New field to track report card

    // Constructor for Parent class
    public ParentCopy(String name, int age, String childName, int childAge, String suggestedProgram) {
        super(name, age);  // Calling parent class constructor
        this.childName = childName;
        this.childAge = childAge;
        this.suggestedProgram = suggestedProgram;
    }

    // Getter methods
    public String getChildName() {
        return childName;
    }

    public int getChildAge() {
        return childAge;
    }

    public String getProgram() {
        return suggestedProgram;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String status) {
        this.attendanceStatus = status;
    }

    public String getReportCardGrade() {
        return reportCardGrade;
    }

    public void setReportCardGrade(String grade) {
        this.reportCardGrade = grade;
    }

    // Setter method for suggested program
    public void setProgram(String newProgram) {
        this.suggestedProgram = newProgram;
    }

    // Perform action based on choice
    @Override
    public void performAction(int choice) {
        switch (choice) {
            case 1:
                System.out.println("Viewing Screening Result for " + getChildName());
                break;
            case 2:
                chooseExtracurricularActivities();  // Handle extracurricular selection
                break;
            case 3:
                System.out.println("Viewing extracurricular activities for " + getChildName());
                break;
            case 4:
                viewAttendance();  // View attendance
                break;
            case 5:
                viewReportCard();  // View report card
                break;
            case 6:
                System.out.println("Exiting Parent Portal.\n");
                break;
            default:
                System.out.println("Invalid choice. Please try again.\n");
        }
    }
    

    // Handle choosing extracurricular activities
    private void chooseExtracurricularActivities() {
        System.out.println("Choosing Extracurricular Activities for " + getChildName() + "...");
        String[] activities = {"Music", "Sports", "Art"};
        displayOptions(activities);

        int activityChoice = getIntInput("Choose an activity for " + getChildName() + ": ");
        if (activityChoice >= 1 && activityChoice <= activities.length) {
            System.out.println(getChildName() + " has chosen " + activities[activityChoice - 1] + ".");
        } else {
            System.out.println("Invalid choice. Please try again.");
            chooseExtracurricularActivities();  // Retry if invalid choice
        }
    }

    // Helper method to display options
    private static void displayOptions(String[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    // Helper method to handle integer input
    private static int getIntInput(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    
    // Display the available options in the Parent Portal
    @Override
    public void displayOptions() {
        String[] options = {
            "View Screening Result",
            "Choose Extracurricular Activities",
            "View Extracurricular Activities",
            "View Attendance",
            "View Report Card",
            "Exit Parent Portal"
        };
        displayOptions(options);
    }

    // Method to view attendance
    private void viewAttendance() {
        if (attendanceStatus != null) {
            System.out.println("Attendance Status for " + getChildName() + ": " + attendanceStatus);
        } else {
            System.out.println("No attendance data available for " + getChildName());
        }
    }

    // Method to view report card
    private void viewReportCard() {
        if (reportCardGrade != null) {
            System.out.println("Report Card for " + getChildName() + ": " + reportCardGrade);
        } else {
            System.out.println("No report card data available for " + getChildName());
        }
    }

    // Method for selecting a new program
    public static String chooseProgram() {
        String[] programs = {"Kindergarten", "Pre-School", "Elementary"};
        System.out.println("\nSelect a new program:");
        displayOptions(programs);

        int programChoice = getIntInput("Enter your choice (1-3): ");
        if (programChoice >= 1 && programChoice <= programs.length) {
            return programs[programChoice - 1];
        } else {
            System.out.println("Invalid choice. Please try again.");
            return chooseProgram();  // Retry if invalid choice
        }
    }

    // Method to update child's program
    public static void updateChildProgram(ParentCopy parent) {
        System.out.println("Currently enrolled in: " + parent.getProgram());
        String newProgram = chooseProgram();  // Get new program from user
        parent.setProgram(newProgram);  // Update the program in Parent object
        System.out.println("Program updated to: " + newProgram);
    }
}
