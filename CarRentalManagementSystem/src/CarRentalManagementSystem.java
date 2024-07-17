import java.sql.*;
import java.util.Scanner;

public class CarRentalManagementSystem {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/car_rental_system";
    static final String USER = "root";
    static final String PASS = "YOUR_PASSWORD";

    static Connection conn = null;
    static Statement stmt = null;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Display main menu
            while (true) {
                displayMainMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        manageCars();
                        break;
                    case 2:
                        manageCustomers();
                        break;
                    case 3:
                        manageRentals();
                        break;
                    case 4:
                        System.out.println("Exiting program...");
                        conn.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please enter again.");
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n===== Car Rental Management System Menu =====");
        System.out.println("1. Manage Cars");
        System.out.println("2. Manage Customers");
        System.out.println("3. Manage Rentals");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void manageCars() {
        while (true) {
            System.out.println("\n===== Car Management Menu =====");
            System.out.println("1. Add a new car");
            System.out.println("2. View car details");
            System.out.println("3. Update car information");
            System.out.println("4. Delete a car");
            System.out.println("5. Back to main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addCar();
                    break;
                case 2:
                    viewCars();
                    break;
                case 3:
                    updateCar();
                    break;
                case 4:
                    deleteCar();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter again.");
            }
        }
    }

    private static void addCar() {
        try {
            System.out.println("\n===== Adding a New Car =====");
            System.out.print("Enter make: ");
            String make = scanner.nextLine();
            System.out.print("Enter model: ");
            String model = scanner.nextLine();
            System.out.print("Enter year: ");
            int year = scanner.nextInt();
            System.out.print("Enter daily rate: ");
            double dailyRate = scanner.nextDouble();

            String sql = "INSERT INTO Car (make, model, year, daily_rate) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, make);
            pstmt.setString(2, model);
            pstmt.setInt(3, year);
            pstmt.setDouble(4, dailyRate);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Car added successfully!");
            } else {
                System.out.println("Failed to add car.");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private static void viewCars() {
        try {
            System.out.println("\n===== Viewing Car Details =====");
            stmt = conn.createStatement();
            String sql = "SELECT * FROM Car";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int carId = rs.getInt("car_id");
                String make = rs.getString("make");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                double dailyRate = rs.getDouble("daily_rate");

                System.out.println("Car ID: " + carId);
                System.out.println("Make: " + make);
                System.out.println("Model: " + model);
                System.out.println("Year: " + year);
                System.out.println("Daily Rate: $" + dailyRate);
                System.out.println("-----------------------------");
            }
            rs.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private static void updateCar() {
        try {
            System.out.println("\n===== Updating Car Information =====");
            System.out.print("Enter car ID to update: ");
            int carId = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            String sql = "SELECT * FROM Car WHERE car_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, carId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.print("Enter new make: ");
                String make = scanner.nextLine();
                System.out.print("Enter new model: ");
                String model = scanner.nextLine();
                System.out.print("Enter new year: ");
                int year = scanner.nextInt();
                System.out.print("Enter new daily rate: ");
                double dailyRate = scanner.nextDouble();

                sql = "UPDATE Car SET make = ?, model = ?, year = ?, daily_rate = ? WHERE car_id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, make);
                pstmt.setString(2, model);
                pstmt.setInt(3, year);
                pstmt.setDouble(4, dailyRate);
                pstmt.setInt(5, carId);

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Car information updated successfully!");
                } else {
                    System.out.println("Failed to update car information.");
                }
            } else {
                System.out.println("Car with ID " + carId + " not found.");
            }
            rs.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private static void deleteCar() {
        try {
            System.out.println("\n===== Deleting a Car =====");
            System.out.print("Enter car ID to delete: ");
            int carId = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            String sql = "DELETE FROM Car WHERE car_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, carId);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Car deleted successfully!");
            } else {
                System.out.println("Failed to delete car. Car with ID " + carId + " not found.");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private static void manageCustomers() {
        System.out.println("\n===== Managing Customers =====");
        System.out.println("1. Add Customer");
        System.out.println("2. View Customer");
        System.out.println("3. Update Customer");
        System.out.println("4. Delete Customer");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                addCustomer();
                break;
            case 2:
                viewCustomer();
                break;
            case 3:
                updateCustomer();
                break;
            case 4:
                deleteCustomer();
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }
    private static void addCustomer() {
        try {
            System.out.println("\n===== Adding a Customer =====");
            System.out.print("Enter customer name: ");
            String name = scanner.nextLine();
            System.out.print("Enter customer address: ");
            String address = scanner.nextLine();
            System.out.print("Enter customer phone: ");
            String phone = scanner.nextLine();

            String sql = "INSERT INTO Customer (name, address, phone) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, phone);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Customer added successfully!");
            } else {
                System.out.println("Failed to add customer.");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private static void viewCustomer() {
        try {
            System.out.println("\n===== Viewing a Customer =====");
            System.out.print("Enter customer ID to view: ");
            int customerId = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            String sql = "SELECT * FROM Customer WHERE customer_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Customer ID: " + rs.getInt("customer_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("Phone: " + rs.getString("phone"));
            } else {
                System.out.println("Customer with ID " + customerId + " not found.");
            }
            rs.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private static void updateCustomer() {
        try {
            System.out.println("\n===== Updating Customer Information =====");
            System.out.print("Enter customer ID to update: ");
            int customerId = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            String sql = "SELECT * FROM Customer WHERE customer_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.print("Enter new name: ");
                String name = scanner.nextLine();
                System.out.print("Enter new address: ");
                String address = scanner.nextLine();
                System.out.print("Enter new phone: ");
                String phone = scanner.nextLine();

                sql = "UPDATE Customer SET name = ?, address = ?, phone = ? WHERE customer_id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, address);
                pstmt.setString(3, phone);
                pstmt.setInt(4, customerId);

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Customer information updated successfully!");
                } else {
                    System.out.println("Failed to update customer information.");
                }
            } else {
                System.out.println("Customer with ID " + customerId + " not found.");
            }
            rs.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private static void deleteCustomer() {
        try {
            System.out.println("\n===== Deleting a Customer =====");
            System.out.print("Enter customer ID to delete: ");
            int customerId = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            String sql = "DELETE FROM Customer WHERE customer_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Customer deleted successfully!");
            } else {
                System.out.println("Failed to delete customer. Customer with ID " + customerId + " not found.");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private static void manageRentals() {
        System.out.println("\n===== Managing Rentals =====");
        System.out.println("1. Rent a Car");
        System.out.println("2. Return a Car");
        System.out.println("3. View Rental Details");
        System.out.println("4. Calculate Rental Charges");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                rentCar();
                break;
            case 2:
                returnCar();
                break;
            case 3:
                viewRentalDetails();
                break;
            case 4:
                calculateRentalCharges();
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    private static void rentCar() {
        try {
            System.out.println("\n===== Renting a Car =====");
            System.out.print("Enter customer ID: ");
            int customerId = scanner.nextInt();
            System.out.print("Enter car ID: ");
            int carId = scanner.nextInt();
            System.out.print("Enter rental date (YYYY-MM-DD): ");
            String rentalDate = scanner.next();
            System.out.print("Enter return date (YYYY-MM-DD): ");
            String returnDate = scanner.next();

            String sql = "INSERT INTO Rental (customer_id, car_id, rental_date, return_date) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, carId);
            pstmt.setDate(3, Date.valueOf(rentalDate));
            pstmt.setDate(4, Date.valueOf(returnDate));

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Car rented successfully!");
            } else {
                System.out.println("Failed to rent car.");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private static void returnCar() {
        try {
            System.out.println("\n===== Returning a Car =====");
            System.out.print("Enter rental ID: ");
            int rentalId = scanner.nextInt();

            String sql = "DELETE FROM Rental WHERE rental_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, rentalId);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Car returned successfully!");
            } else {
                System.out.println("Failed to return car. Rental with ID " + rentalId + " not found.");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private static void viewRentalDetails() {
        try {
            System.out.println("\n===== Viewing Rental Details =====");
            System.out.print("Enter rental ID to view: ");
            int rentalId = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            String sql = "SELECT * FROM Rental WHERE rental_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, rentalId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Rental ID: " + rs.getInt("rental_id"));
                System.out.println("Customer ID: " + rs.getInt("customer_id"));
                System.out.println("Car ID: " + rs.getInt("car_id"));
                System.out.println("Rental Date: " + rs.getDate("rental_date"));
                System.out.println("Return Date: " + rs.getDate("return_date"));
            } else {
                System.out.println("Rental with ID " + rentalId + " not found.");
            }
            rs.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private static void calculateRentalCharges() {
        try {
            System.out.println("\n===== Calculating Rental Charges =====");
            System.out.print("Enter rental ID: ");
            int rentalId = scanner.nextInt();

            String sql = "SELECT DATEDIFF(return_date, rental_date) AS rental_days, daily_rate " +
                    "FROM Rental INNER JOIN Car ON Rental.car_id = Car.car_id " +
                    "WHERE rental_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, rentalId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int rentalDays = rs.getInt("rental_days");
                double dailyRate = rs.getDouble("daily_rate");
                double totalCharge = rentalDays * dailyRate;
                System.out.println("Total rental charges: $" + totalCharge);
            } else {
                System.out.println("Rental with ID " + rentalId + " not found.");
            }
            rs.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}