import java.util.*;

// Main Class for the Registration and Login System
public class RegistrationLoginSystemCopy {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Facade pattern to simplify interactions
        System.out.println("------------- Welcome to Child Ease -------------");
        UserSystemFacade facade = new UserSystemFacade();

        while (true) {
            displayMainMenu();
            int choice = getIntInput("\nEnter your choice (1-3): ");

            switch (choice) {
                case 1 -> registerParent(facade);
                case 2 -> login(facade);
                case 3 -> {
                    System.out.println("Exiting the system. Goodbye!\n");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1. First Time Registration");
        System.out.println("2. Login");
        System.out.println("3. Exit");
    }

    private static void registerParent(UserSystemFacade facade) {
        // Abstract Factory for creating Parent objects
        UserFactory parentFactory = new ParentFactory();

        System.out.println("First Time Registration:");
        String parentName = getStringInput("\nEnter your name: ");
        int parentAge = getIntInput("Enter your age: ");
        String childName = getStringInput("\nEnter your child's name: ");
        int childAge = getIntInput("Enter your child's age: ");

        String suggestedProgram = suggestProgram(childAge);
        if ("No suitable program available".equals(suggestedProgram)) {
            System.out.println("No suitable program available for your child's age. Please choose a program.");
            suggestedProgram = chooseProgram();
        } else {
            System.out.println("\nSuggested program based on your child's age: " + suggestedProgram);
            System.out.println("Do you want to choose the suggested program? (Yes/No)");
            String useSuggested = scanner.nextLine().trim().toLowerCase();
            if (!useSuggested.equals("yes")) {
                suggestedProgram = chooseProgram();
            }
        }

        processPayment(suggestedProgram);

        Parent newParent = (Parent) parentFactory.createUser(parentName, parentAge);
        newParent.setChildDetails(childName, childAge, suggestedProgram);
        facade.registerParent(newParent);
    }

    private static void processPayment(String suggestedProgram) {
        double amountPaid;
        while (true) {
            System.out.println("\nThe program fee for " + suggestedProgram + " is RM500. Please make the payment.");
            amountPaid = getDoubleInput("Enter payment amount: ");
            if (amountPaid == 500) {
                break;
            } else {
                System.out.println("Incorrect amount. Please enter the exact amount (RM500).");
            }
        }
    }

    private static void login(UserSystemFacade facade) {
        System.out.println("Login:");
        System.out.println("1. Staff");
        System.out.println("2. Parent");
        System.out.println("3. Return to Main Menu");
        System.out.println("4. Exit");
        int loginChoice = getIntInput("\nEnter your choice (1-4): ");

        switch (loginChoice) {
            case 1 -> loginAsStaff(facade);
            case 2 -> loginAsParent(facade);
            case 3 -> System.out.println("Returning to main menu...");
            case 4 -> {
                System.out.println("Exiting the system. Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Please try again.\n");
        }
    }

    private static void loginAsStaff(UserSystemFacade facade) {
        System.out.println("Login as Staff:");
        String staffName = getStringInput("\nEnter your name: ");
        Staff staff = facade.loginStaff(staffName);
        if (staff != null) {
            staffPortal(staff);
        }
    }

    private static void loginAsParent(UserSystemFacade facade) {
        System.out.println("Login as Parent:");
        String parentName = getStringInput("\nEnter your name: ");
        Parent parent = facade.loginParent(parentName);
        if (parent != null) {
            parentPortal(parent);
        }
    }

    private static void parentPortal(Parent parent) {
        System.out.println("Welcome to the Parent Portal!");
        System.out.println("Viewing details for " + parent.getChildName());
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. View Child's Details");
        System.out.println("2. Update Child's Program");
        System.out.println("3. View Payment Status");
        System.out.println("4. View Screening");
        System.out.println("5. View Attendance");
        System.out.println("6. View Curricular");
        System.out.println("7. View Report Card");
        System.out.println("8. Logout");

        int choice = getIntInput("\nEnter your choice (1-8): ");
        switch (choice) {
            case 1 -> viewChildDetails(parent);
            case 2 -> updateChildProgram(parent);
            case 3 -> viewPaymentStatus();
            case 4 -> System.out.println("Screening result: No major issue");
            case 5 -> System.out.println("Attendance status: Your children attended the classroom");
            case 6 -> System.out.println("Curricular status: Your children attended the swimming class");
            case 7 -> System.out.println("Report card result: Congratulations! Your children acquired an A for this semester");
            case 8 -> System.out.println("Logging out...");
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void staffPortal(Staff staff) {
        staff.displayOptions();
        int staffChoice = getIntInput("\nEnter your choice (1-5): ");
        staff.performAction(staffChoice);
    }

    private static void viewChildDetails(Parent parent) {
        System.out.println("Child's Name: " + parent.getChildName());
        System.out.println("Child's Age: " + parent.getChildAge());
        System.out.println("Child's Program: " + parent.getProgram());
    }

    private static void updateChildProgram(Parent parent) {
        System.out.println("Currently enrolled in: " + parent.getProgram());
        System.out.println("Select a new program:");
        String newProgram = chooseProgram();
        parent.setProgram(newProgram);  // Update the program in Parent object
        System.out.println("Program updated to: " + newProgram);
    }

    private static void viewPaymentStatus() {
        System.out.println("Payment Status: Paid RM500 for the current program.");
    }

    
    // Abstract Factory Implementation
    public interface UserFactory {
        User createUser(String name, int age);
    }

    public static class ParentFactory implements UserFactory {
        @Override
        public User createUser(String name, int age) {
            return new Parent(name, age);
        }
    }
    

    // Observer Pattern Implementation
    public interface Observer {
        void update(String message);
    }

    public interface Subject {
        void attach(Observer observer);
        void detach(Observer observer);
        void notifyObservers(String message);
    }

    public static class SystemNotifier implements Subject {
        private final List<Observer> observers = new ArrayList<>();

        @Override
        public void attach(Observer observer) {
            observers.add(observer);
        }

        @Override
        public void detach(Observer observer) {
            observers.remove(observer);
        }

        @Override
        public void notifyObservers(String message) {
            for (Observer observer : observers) {
                observer.update(message);
            }
        }

        public void sendPaymentReminder(String parentName) {
            notifyObservers("Reminder: Please complete the payment for your child's program.");
        }

        public void sendProgramUpdate(String parentName, String program) {
            notifyObservers("Program updated for " + parentName + ": " + program);
        }
    }

    public static class ParentNotifier implements Observer {
        private final String parentName;

        public ParentNotifier(String parentName) {
            this.parentName = parentName;
        }

        @Override
        public void update(String message) {
            System.out.println("\nNotification for " + parentName + ": " + message);
        }
    }

    
    // Facade Pattern Implementation
    public static class UserSystemFacade {
        private final Map<String, Parent> parentAccounts = new HashMap<>();
        private final Map<String, Staff> staffAccounts = new HashMap<>();

        public void registerParent(Parent parent) {
            parentAccounts.put(parent.getName(), parent);
            SystemNotifier notifier = new SystemNotifier();
            notifier.attach(new ParentNotifier(parent.getName()));
            notifier.notifyObservers("Registration completed for " + parent.getChildName());
        }

        public Parent loginParent(String name) {
            if (parentAccounts.containsKey(name)) {
                System.out.println("Login successful for " + name);
                return parentAccounts.get(name);
            } else {
                System.out.println("Parent not found. Please register first.");
                return null;
            }
        }

        public Staff loginStaff(String name) {
            if (!staffAccounts.containsKey(name)) {
                Staff newStaff = new Staff(name, 30, "Teacher"); 
                staffAccounts.put(name, newStaff);
                System.out.println("New staff account created.");
            }
            System.out.println("Please login to access your staff portal, " + name);
            return staffAccounts.get(name);
        }
    }

    
    // Parent and User Classes
    public interface User {
        String getName();
    }

    public static class Parent implements User {
        private final String name;
        private final int age;
        private String childName;
        private int childAge;
        private String program;

        public Parent(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void setChildDetails(String childName, int childAge, String program) {
            this.childName = childName;
            this.childAge = childAge;
            this.program = program;
        }

        public String getChildName() {
            return childName;
        }

        public int getChildAge() {
            return childAge;
        }

        public String getProgram() {
            return program;
        }

        @Override
        public String getName() {
            return name;
        }

        public void setProgram(String program) {
            this.program = program;
        }
    }

    public static class Staff implements User {
        private final String name;
        private final int age;
        private final String role;

        public Staff(String name, int age, String role) {
            this.name = name;
            this.age = age;
            this.role = role;
        }

        public void displayOptions() {
            int pinAttempts = 3;
            boolean pinVerified = false;

            while (pinAttempts > 0 && !pinVerified) {
                System.out.print("\nEnter the 4-digit PIN to access the Staff Portal: ");
                String pin = scanner.nextLine().trim();

                if (pin.equals("1111")) {
                    pinVerified = true;
                } else {
                    pinAttempts--;
                    System.out.println("Incorrect PIN. You have " + pinAttempts + " attempts left.");
                }

                if (pinAttempts == 0) {
                    System.out.println("You have exceeded the number of attempts. Access denied.");
                    return;
                }
            }

            System.out.println("\nStaff Portal - Choose an option:");
            System.out.println("1. Update Screening");
            System.out.println("2. Update Attendance");
            System.out.println("3. Update Curricular");
            System.out.println("4. Update Report Card");
            System.out.println("5. Exit Staff Portal");
            System.out.print("\nPlease enter your choice (1-5): ");
    }

        public void performAction(int choice) {
            switch (choice) {
                case 1 -> System.out.println("Screening has been updated");
                case 2 -> updateAttendance();
                case 3 -> System.out.println("Curricular has been updated");
                case 4 -> updateReportCard();
                case 5 -> System.out.println("Exiting Staff Portal.");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

// Method to update attendance for a student with the student's name
private static void updateAttendance() {
    System.out.print("Enter the student's name: ");
    String studentName = scanner.nextLine().trim();
    System.out.print("Enter the attendance status for " + studentName + " (Present/Absent): ");
    String status = scanner.nextLine().trim();
    if (status.equalsIgnoreCase("Present") || status.equalsIgnoreCase("Absent")) {
        System.out.println("Attendance updated to " + status + " for " + studentName + ".");
    } else {
        System.out.println("Invalid status. Please enter 'Present' or 'Absent'.");
    }
}

// Method to update report card for a student with the student's name
private static void updateReportCard() {
    System.out.print("Enter the student's name: ");
    String studentName = scanner.nextLine().trim();
    System.out.print("Enter the grade for " + studentName + ": ");
    String grade = scanner.nextLine().trim();
    System.out.println("Report card updated with grade: " + grade + " for " + studentName + ".");
}


        @Override
        public String getName() {
            return name;
        }
    }

    // Helper Methods
    public static String suggestProgram(int age) {
        if (age >= 2 && age <= 4) {
            return "Kindergarten";
        } else if (age >= 5 && age <= 6) {
            return "Pre-School";
        } else if (age >= 6 && age <= 12) {
            return "Elementary";
        } else {
            return "No suitable program available";
        }
    }

    public static String chooseProgram() {
        System.out.println("\nSelect your child's study program:");
        System.out.println("1. Kindergarten (Age: 2-4)");
        System.out.println("2. Elementary (Age: 6-12)");
        System.out.println("3. Pre-School (Age: 5-6)");

        while (true) {
            int programChoice = getIntInput("\nEnter your choice (1-3): ");
            switch (programChoice) {
                case 1: return "Kindergarten";
                case 2: return "Elementary";
                case 3: return "Pre-School";
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        }
    }

   // Modify the methods to consume the newline character after reading int or double
public static int getIntInput(String prompt) {
    System.out.print(prompt);
    while (!scanner.hasNextInt()) {
        System.out.println("Invalid input. Please enter an integer.");
        scanner.next(); // Clear the invalid input
        System.out.print(prompt);
    }
    int result = scanner.nextInt();
    scanner.nextLine(); // Consume the newline character
    return result;
}

public static double getDoubleInput(String prompt) {
    System.out.print(prompt);
    while (!scanner.hasNextDouble()) {
        System.out.println("Invalid input. Please enter a valid amount.");
        scanner.next(); // Clear the invalid input
        System.out.print(prompt);
    }
    double result = scanner.nextDouble();
    scanner.nextLine(); // Consume the newline character
    return result;
}

public static String getStringInput(String message) {
    while (true) {
        System.out.print(message);
        String input = scanner.nextLine().trim();
        if (input.matches("[a-zA-Z ]+")) {
            return input;
        } else {
            System.out.println("Invalid input. Please enter a valid name (letters only).\n");
        }
    }
}

}
