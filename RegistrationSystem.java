import java.sql.*;
import java.util.Scanner;

public class RegistrationSystem {

    // creating Database connection
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/registrationdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "@David80192887";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Registration System");

        // Get user input to enter their name and password
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Register the user and choose semester
        String semester = chooseSemester(scanner);

        // Register the user
        boolean isRegistered = registerUser(username, password, semester);

        // Display the result after the registration
        if (isRegistered) {
            System.out.println("Registration successful!");

            
        } else {
            System.out.println("Registration failed. Please try again.");
        }

        // Ask if the user wants to see all registered students or specific students by username
        System.out.print("Do you want to see all registered students? press (yes/no):");
        String showAll = scanner.nextLine().toLowerCase();

        if (showAll.equals("yes")) {
            displayAllUsers();

        }
        else {
            System.out.print(" OR Enter username to search: ");
            String searchUsername = scanner.nextLine();
            displayUserData(searchUsername);
            displayAllUsers();
        }
        // Close the scanner after displaying the database result
        scanner.close();
    }

    private static String chooseSemester(Scanner scanner) {
        System.out.println("Choose Semester:");
        System.out.println("1. 1st Semester, 1st Year");
        System.out.println("2. 2nd Semester, 1st Year");
        System.out.println("3. 1st Semester, 2nd Year");
        System.out.println("4. 2nd Semester, 2nd Year");
        System.out.println("5. 1st Semester, 3rd Year");
        System.out.println("6. 2nd Semester, 3rd Year");
        System.out.println("7. 1st Semester, 4th Year");
        System.out.println("8. 2nd Semester, 4th Year");
        System.out.println("9. 1st Semester, 5th Year");

        int choice;
        do {
            System.out.print("Enter your choice (1-9): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                // the above message is displayed if the user does't enter the number to choose the semister
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 9);

        switch (choice) {
            case 1:
                return "1st Semester, 1st Year";
            case 2:
                return "2nd Semester, 1st Year";
            case 3:
                return "1st Semester, 2nd Year";
            case 4:
                return "2nd Semester, 2nd Year";
            case 5:
                return "1st Semester, 3rd Year";
            case 6:
                return "2nd Semester, 3rd Year";
            case 7:
                return "1st Semester, 4th Year";
            case 8:
                return "2nd Semester, 4th Year";
            case 9:
                return "1st Semester, 5th Year";
            default:
                return "";
        }
    }
    private static boolean registerUser(String username, String password, String semester) {
        try {
            // Load the Java DataBase Connector driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            // Prepare the SQL statement
            String sql = "INSERT INTO user (username, password, semester) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, semester);

                // Execute the SQL statement
                int rowsAffected = preparedStatement.executeUpdate();

                // Return true if registration is successful 
                return rowsAffected > 0;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static void displayUserData(String username) {
        try {
            // Load the Java DataBase Connector driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            // Prepare the SQL statement
            String sql = "SELECT * FROM user WHERE username = ?"; // Fix: use "user" instead of "users"
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);

                // Execute the SQL statement
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Display user data
                    while (resultSet.next()) {
                        System.out.println("ID: " + resultSet.getInt("id"));
                        System.out.println("Username: " + resultSet.getString("username"));
                        System.out.println("Password: " + resultSet.getString("password"));
                        System.out.println("Semester: " + resultSet.getString("semester"));
                    }
                }
            }
            // Close the resources
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    private static void displayAllUsers() {
        try {
            // Load the Java DataBase Connector driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            // Ask if the user wants to sort  by username
            System.out.print("Do you want to sort by username or by id?");
            Scanner scanner = new Scanner(System.in);
            String sortByUsername = scanner.nextLine().toLowerCase();

            // Prepare the SQL statement
            String sql;
            if (sortByUsername.equals("username")) {
                sql = "SELECT * FROM user ORDER BY username";
            } else {
                sql = "SELECT * FROM user";
            }

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                // Display all user data
                while (resultSet.next()) {
                    System.out.println("ID: " + resultSet.getInt("id"));
                    System.out.println("Username: " + resultSet.getString("username"));
                    System.out.println("Password: " + resultSet.getString("password"));
                    System.out.println("Semester: " + resultSet.getString("semester"));
                    System.out.println();
                }
            }
            // Close the resources
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
