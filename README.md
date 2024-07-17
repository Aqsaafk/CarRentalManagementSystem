# Car Rental System

This Car Rental System is a console-based Java application that allows users to manage cars, customers, and rentals using a MySQL database for data storage. This README file provides detailed instructions on setting up the MySQL database, installing necessary tools, and running the application.

## Prerequisites

- Java Development Kit (JDK) installed
- MySQL Server installed
- MySQL Workbench installed
- MySQL Connector/J (JDBC) driver

## Installation Instructions

### Step 1: Install MySQL Server

1. **Download MySQL Server**:
   - Go to the [official MySQL website](https://dev.mysql.com/downloads/mysql/).
   - Select your operating system and download the appropriate installer.

2. **Install MySQL Server**:
   - Run the installer and follow the instructions to install MySQL Server.
   - During installation, you will be prompted to set a root password. Remember this password as it will be used to connect to the MySQL server.

### Step 2: Install MySQL Workbench

1. **Download MySQL Workbench**:
   - Go to the [official MySQL Workbench download page](https://dev.mysql.com/downloads/workbench/).
   - Select your operating system and download the appropriate installer.

2. **Install MySQL Workbench**:
   - Run the installer and follow the instructions to install MySQL Workbench.

### Step 3: Install MySQL Connector/J (JDBC)

1. **Download MySQL Connector/J**:
   - Go to the [official MySQL Connector/J download page](https://dev.mysql.com/downloads/connector/j/).
   - Download the latest version of the Connector/J ZIP file.

2. **Extract Connector/J**:
   - Extract the downloaded ZIP file to a convenient location on your computer.

3. **Add Connector/J to Your Project**:
   - In your Java project, add the extracted Connector/J JAR file to the classpath.

### Step 4: Create Schema and Tables using MySQL Workbench

1. **Open MySQL Workbench**:
   - Launch MySQL Workbench from your applications menu.

2. **Connect to MySQL Server**:
   - Click on the "MySQL Connections" section.
   - Click the "Create a new connection" button.
   - Enter the connection name, hostname (usually `localhost`), port (default is `3306`), and your MySQL root username and password.
   - Click "Test Connection" to ensure the connection is successful, then click "OK".

3. **Create a New Schema**:
   - In the MySQL Workbench main window, click the "Schemas" tab.
   - Right-click in the white space under the "Schemas" tab and select "Create Schema".
   - Enter a schema name (e.g., `car_rental`) and click "Apply".

4. **Create Tables**:
   - Right-click the new schema (e.g., `car_rental`) and select "Set as Default Schema".
   - Click the "SQL" tab to open a new SQL query tab.
   - Use the following SQL script to create the necessary tables:

     ```sql
     CREATE TABLE Car (
         car_id INTEGER PRIMARY KEY,
         make TEXT,
         model TEXT,
         year INTEGER,
         daily_rate REAL
             );

     CREATE TABLE Customer (
        customer_id INTEGER PRIMARY KEY,
        name TEXT,
        email TEXT,
        phone_number TEXT,
        address TEXT
          );

     CREATE TABLE Rental (
        rental_id INTEGER PRIMARY KEY,
        car_id INTEGER,
        customer_id INTEGER,
        rental_start_date DATE,
        rental_end_date DATE,
        total_charge REAL,
        FOREIGN KEY (car_id) REFERENCES Car(car_id),
        FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
              );
     ```

   - Paste the script into the SQL query tab and click "Execute" to run the script and create the tables.

## Running the Application

1. **Clone the Repository**:
   - Clone the project repository to your local machine.

2. **Open the Project in Your IDE**:
   - Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).

3. **Add MySQL Connector/J to the Classpath**:
   - Add the MySQL Connector/J JAR file (downloaded and extracted in Step 3) to the project's classpath.

4. **Update Database Connection Details**:
   - In the `CarRentalSystem` class, update the `DriverManager.getConnection` call with your MySQL server connection details:

     ```java
     conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental", "root", "your_password");
     ```

5. **Run the Application**:
   - Run the `CarRentalSystem` main class.
   - Follow the on-screen prompts to manage cars, customers, and rentals.

## Application Functionality

- **Manage Cars**:
  - Add a car
  - View a car
  - Update a car
  - Delete a car

- **Manage Customers**:
  - Add a customer
  - View a customer
  - Update a customer
  - Delete a customer

- **Manage Rentals**:
  - Rent a car
  - Return a car
  - View rental details
  - Calculate rental charges

## Troubleshooting

- **Connection Issues**: Ensure MySQL Server is running and accessible. Verify connection details (hostname, port, username, password).
- **ClassNotFoundException**: Ensure MySQL Connector/J JAR file is correctly added to the project's classpath.
- **SQL Exceptions**: Check SQL syntax and table definitions. Ensure tables exist and have the correct schema.

## Conclusion

This Car Rental System provides a simple console-based interface for managing cars, customers, and rentals using a MySQL database. Follow the installation and setup instructions carefully to ensure the application runs smoothly. Feel free to extend and customize the application to meet your specific requirements.
