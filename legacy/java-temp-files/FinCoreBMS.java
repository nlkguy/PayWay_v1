/*
    authored by @nlkguy
*/

// import external java


import java.security.MessageDigest; // for hashing
import java.nio.charset.StandardCharsets; // for hashing

import java.util.*;
/*
    - main class
        - init data storage , file paths
        - display menu - prompt
            - employee login
            - customer login
            - exit
        - employee menu
        - customer menu
        - other menu flows

    - Employee class
        - attributes
            - emp_id
            - emp_name
            - emp_email
            - emp_password_hash
            - emp_address
            - emp_phone
        - func
            - empRegister()
            - empLogin()
            - empViewProfile()
            - empUpdateProfile()

    - Customer class
        - attributes
            - cust_ssn_id
            - cust_name
            - cust_email
            - cust_password_hash
            - cust_address
            - cust_phone
            - cust_pan
            - cust_aadhaar
        - func
            - custRegister()
            - custLogin()
            - custViewProfile()
            - custUpdateProfile()

    - CustomerService / CustomerManager class
        - func
            - registerCustomer()
            - viewCustomerBySSN()
            - updateCustomerBySSN()
            - deleteCustomerBySSN()
            - listAllCustomers()

    - Helpers
        - file handling
        - input validation
        - password hashing
        - generate unique IDs
        - other misc stuff

        - file stuff
            - saveEmployeeData()
            - loadEmployeeData()
            - saveCustomerData()
            - loadCustomerData()
            - loadUserData()
            - checkPasswordHash()
*/


public class FinCoreBMS {

    private static final Scanner SC = new Scanner(System.in);
    // main scanner object , use everywhere
    public static void main(String[] args) {
        // add detailed banner later , use helperclass
        System.out.println(">>> FinCoreBMS is the flagship project of PayWay Solutions! <<<");

        boolean running = true;

        while (running){

            System.out.println("\n\t\t>>Main Menu:");
            System.out.println("\t\t>>>>1. Employee Login");
            System.out.println("\t\t>>>>2. Customer Login");
            System.out.println("\t\t>>>>3. Exit");
            System.out.print("Select an option: ");

            String choice = SC.nextLine();

            switch (choice) {

                case "1":
                    // Employee login flow
                    if (employeeLoginFlow()) {
                        employeeMenu();
                    } else {
                        System.out.println("\t\t\t>>>>Employee login failed.");
                    }
                    break;
                case "2":
                    // Customer login flow
                    System.out.println("\t\t\t>>>>Customer login selected.");
                    // Call customer login method here
                    break;
                case "3":
                    System.out.println("\t\t\t>>>>Exiting FinCoreBMS. Goodbye!");
                    running = false;
                    break;
                case "69":
                    System.out.println("Unofficial CIA backdooor to Add Employee");
                    employeeRegisterFlow();
                    break;
                default:
                    System.out.println("\t\t\t>>>>Invalid option. Please tryagain.");
            }

            
        }
    }


    private static boolean employeeLoginFlow() {
        System.out.print("\t\t\t\t\t>>>>Username: ");
        String username = SC.nextLine();
        System.out.print("\t\t\t\t\t>>>>Password: ");
        // do the whole password masking later
        String password = SC.nextLine();

        boolean loginSuccess = Employee.empLogin(username, password);

        return loginSuccess;
    }
    private static boolean employeeRegisterFlow() {
        // add password / username policy later
        System.out.print("\t\t\t\t\t>>>>Choose a Username: ");
        String username = SC.nextLine();
        System.out.print("\t\t\t\t\t>>>>Choose a Password: ");
        String password = SC.nextLine();
        System.out.print("\t\t\t\t\t>>>>Confirm Password: ");
        String confirm_password = SC.nextLine();
        if (!password.equals(confirm_password)) {
            System.out.println("\t\t\t\t\t>>>>Passwords do not match. Registration failed.");
            return false;
        }
        // save employee data to csv file 
        boolean success = Employee.empRegister(username,password);
        if (success) {
            System.out.println("\t\t\t\t\t>>>>Employee Registration Successful.");
        } else {
            System.out.println("\t\t\t\t\t>>>>Employee Registration Failed. Username may already exist.");
        }
        // Employee registration logic here
        // For now, just return true to simulate success
        return true;
    }

    private static boolean employeeMenu(){
        boolean empMenuRunning = true;

        while (empMenuRunning) {
            System.out.println("\n\t\t\t\t>>>>Employee Menu:");
            System.out.println("\t\t\t\t\t>>>>1. View Profile");
            System.out.println("\t\t\t\t\t>>>>2. Update Profile");
            System.out.println("\t\t\t\t\t>>>>3. Add Employee");
            System.out.println("\t\t\t\t\t>>>>0. Logout");
            System.out.print("\t\t\t\t\t>>>>Select an option: ");

            String choice = SC.nextLine();

            switch (choice) {
                case "1":
                    // View profile logic
                    System.out.println("\t\t\t\t\t>>>>View Profile selected.");
                    break;
                case "2":
                    // Update profile logic
                    System.out.println("\t\t\t\t\t>>>>Update Profile selected.");
                    break;
                case "0":
                    System.out.println("\t\t\t\t\t>>>>Logging out from Employee Menu.");
                    empMenuRunning = false;
                    break;
                case "3":
                    System.out.println("\t\t\t\t\t>>>>Add Employee selected.");
                    employeeRegisterFlow();
                    break;
                default:
                    System.out.println("\t\t\t\t\t>>>>Invalid option. Please try again.");
            }
        }
        return true;
    }

    
}

class Helpers {

    private static final String EMP_FILE = "data/employees.csv";
    private static final String CUST_FILE = "data/customers.csv";

    private static void ensureEmployeeFile() {
        try {
            java.io.File dir = new java.io.File("data");
            if (!dir.exists()) dir.mkdirs();

            java.io.File file = new java.io.File(EMP_FILE);
            if (!file.exists()) {
                java.io.FileWriter fw = new java.io.FileWriter(file);
                fw.write("username,password_hash\n");
                fw.close();
            }
        } catch (Exception e) {
            System.out.println("Error creating employee file: " + e.getMessage());
        }
    }

    public static boolean saveEmployee(String username, String passwordHash) {

        ensureEmployeeFile();

        try (java.io.FileWriter fw = new java.io.FileWriter(EMP_FILE, true)) {
            fw.write(username + "," + passwordHash + "\n");
            return true;
        } catch (Exception e) {
            System.out.println("Error saving employee: " + e.getMessage());
            return false;
        }   
    }


    public static String getPasswordHashForUser(String username) {
        // open file , read data , find user , return hash

        ensureEmployeeFile();

        try (Scanner fileScanner = new Scanner(new java.io.File(EMP_FILE))) {

            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine(); // skip header
            }

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",", -1);
                if (parts.length != 2) continue;

                String fileUser = parts[0].trim();
                String fileHash = parts[1].trim();

                if (fileUser.equals(username)) {
                    return fileHash;
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading employee file: " + e.getMessage());
        }

        return null;
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();

        } catch (Exception e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }

    public static boolean checkPasswordHash(String password, String storedHash) {
        return hashPassword(password).equals(storedHash);
    }

}


class Employee{

    private String username;
    private String password_hash;
    /*
    - attributes
            - emp_id
            - emp_name
            - emp_email
            - emp_password_hash
            - emp_address
            - emp_phone
    */

    private int emp_id;
    private String emp_name;


    public static boolean empLogin(String username, String password) {
        // get stored hash
        // compare hashes
        String stored_hash = Helpers.getPasswordHashForUser(username);
        return Helpers.checkPasswordHash(password, stored_hash);
    }

    public static boolean empRegister(String username, String password) {

        // check if user already exists
        if (Helpers.getPasswordHashForUser(username) != null) {
            return false;
        }

        String password_hash = Helpers.hashPassword(password);
        return Helpers.saveEmployee(username, password_hash);
    }

    // load employee data from file
    // verify email and password hash
    // func to compare given password hash with stored hash
    

}

class Customer{


}


//---------------------------------------------------