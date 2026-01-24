import java.sql.*;
import java.time.*;
import java.util.*;

public class Account {


    public long accountNo;
    public int customerId;
    public LocalDate openingDate;
    public String accountType;
    public String branchName;
    public String ifscCode;
    public String status;
    public double balance;
    public LocalDateTime lastAccess;

    // ----------------------------------------------------
    public void viewSummary() {

        System.out.println("\n---- ACCOUNT ----");

        System.out.println("Account: " + accountNo);
        System.out.println("Customer: " + customerId);
        System.out.println("Type: " + accountType);
        System.out.println("Balance: " + balance);
    }

    // ----------------------------------------------------
    public static Account openAccount(
        int customerId,
        Scanner sc) {

        Account a = new Account();

        a.customerId = customerId;

        System.out.print("Type: ");
        a.accountType = sc.nextLine();

        System.out.print("Branch: ");
        a.branchName = sc.nextLine();

        System.out.print("IFSC: ");
        a.ifscCode = sc.nextLine();

        a.balance = 0;
        a.status = "ACTIVE";
        a.openingDate = LocalDate.now();

        saveToDB(a);

        System.out.println("Account Opened");

        return a;
    }

    // ----------------------------------------------------
    public static boolean saveToDB(Account a) {

        try {

            Connection con =
                DatabaseConnection.getConnection();

            String q =
            "INSERT INTO ACCOUNT " +
            "(CUSTOMERID,TYPE,BRANCH,IFSC," +
            "STATUS,BALANCE,OPENINGDATE) " +
            "VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps =
                con.prepareStatement(q);

            ps.setInt(1, a.customerId);
            ps.setString(2, a.accountType);
            ps.setString(3, a.branchName);
            ps.setString(4, a.ifscCode);
            ps.setString(5, a.status);
            ps.setDouble(6, a.balance);
            // ps.setDate(7,Date.valueOf(a.openingDate));

            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ----------------------------------------------------
    public static Account findByAccountNo(
        long acc) {

        try {

            Connection con =
                DatabaseConnection.getConnection();

            PreparedStatement ps =
            con.prepareStatement(
            "SELECT * FROM ACCOUNT " +
            "WHERE ACCOUNTNO=?");

            ps.setLong(1, acc);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Account a = new Account();

                a.accountNo =
                    rs.getLong("ACCOUNTNO");

                a.customerId =
                    rs.getInt("CUSTOMERID");

                a.balance =
                    rs.getDouble("BALANCE");

                a.accountType =
                    rs.getString("TYPE");

                return a;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ----------------------------------------------------
    public static void searchAccount(
        long acc) {

        Account a = findByAccountNo(acc);

        if (a == null)
            System.out.println("Not Found");
        else
            a.viewSummary();
    }

    // ----------------------------------------------------
    public static void listAccounts(
        int limit) {

        try {

            Connection con =
                DatabaseConnection.getConnection();

            PreparedStatement ps =
            con.prepareStatement(
            "SELECT * FROM ACCOUNT " +
            "FETCH FIRST ? ROWS ONLY");

            ps.setInt(1, limit);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println(
                rs.getLong("ACCOUNTNO") +
                " - " +
                rs.getDouble("BALANCE"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------
    public static boolean editAccountDetails(
        long acc,
        Scanner sc) {

        System.out.print("New Status: ");
        String s = sc.nextLine();

        try {

            Connection con =
                DatabaseConnection.getConnection();

            PreparedStatement ps =
            con.prepareStatement(
            "UPDATE ACCOUNT " +
            "SET STATUS=? WHERE ACCOUNTNO=?");

            ps.setString(1, s);
            ps.setLong(2, acc);

            ps.executeUpdate();

            System.out.println("Updated");

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}import java.sql.*;
import java.time.*;

public class AuthManager {

        // ----------------------------------------------------
    public static boolean loginEmployee(
        int id,
        String pass) {

        String hash =
            PasswordManager.getStoredPasswordHash(
                "EMPLOYEE", id);

        if (hash == null)
            return false;

        return PasswordManager.verify(pass, hash);
    }

    // ----------------------------------------------------
    public static boolean loginCustomer(
        int id,
        String pass) {

        String hash =
            PasswordManager.getStoredPasswordHash(
                "CUSTOMER", id);

        if (hash == null)
            return false;

        return PasswordManager.verify(pass, hash);
    }

    // ----------------------------------------------------
    public static void updateLastAccessed(
        String role,
        int id) {

        try {

            Connection con =
                DatabaseConnection.getConnection();

            String table =
                role.equals("EMPLOYEE") ?
                "EMPLOYEE" : "CUSTOMER";

            String idcol =
                role.equals("EMPLOYEE") ?
                "EMPID" : "CUSTOMERID";

            PreparedStatement ps =
            con.prepareStatement(
            "UPDATE " + table +
            " SET LASTACCESSED=? WHERE " +
            idcol + "=?");

            ps.setTimestamp(
                1,
                Timestamp.valueOf(
                    LocalDateTime.now()));

            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}import java.sql.*;
import java.time.*;
import java.util.*;

public class Customer {

    public int customerId;
    public String firstName;
    public String lastName;
    public String email;
    public String contactNo;
    public String aadharNo;
    public String pan;
    public String address;
    public String gender;
    public String passwordHash;
    public LocalDate dob;
    public LocalDate joiningDate;
    public LocalDateTime lastAccess;

    // ----------------------------------------------------
    public void viewCustomerProfile() {

        System.out.println("\n---- CUSTOMER PROFILE ----");

        System.out.println("ID: " + customerId);
        System.out.println("Name: " + firstName);
        System.out.println("Email: " + email);
        System.out.println("Contact: " + contactNo);
        System.out.println("Aadhar: " + aadharNo);
    }

    // ----------------------------------------------------
    public static Customer addCustomer(Scanner sc) {

        Customer c = new Customer();

        System.out.print("First Name: ");
        c.firstName = sc.nextLine();

        System.out.print("Email: ");
        c.email = sc.nextLine();

        System.out.print("Contact: ");
        c.contactNo = sc.nextLine();

        System.out.print("Aadhar: ");
        c.aadharNo = sc.nextLine();

        System.out.print("Password: ");
        String p = sc.nextLine();

        c.passwordHash = PasswordManager.hashPassword(p);

        c.joiningDate = LocalDate.now();

        saveToDB(c);

        System.out.println("Customer Added");

        return c;
    }

    // ----------------------------------------------------
    public static boolean saveToDB(Customer c) {

        try {

            Connection con = DatabaseConnection.getConnection();

            String q =
            "INSERT INTO CUSTOMER " +
            "(FIRSTNAME,EMAIL,CONTACT,AADHAR,PASSWORDHASH,JOININGDATE) " +
            "VALUES (?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(q);

            ps.setString(1, c.firstName);
            ps.setString(2, c.email);
            ps.setString(3, c.contactNo);
            ps.setString(4, c.aadharNo);
            ps.setString(5, c.passwordHash);
            //ps.setDate(6, Date.valueOf(c.joiningDate));
            

            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ----------------------------------------------------
    public static boolean deleteCustomerData(int id) {

        try {

            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps =
            con.prepareStatement("DELETE FROM CUSTOMER WHERE CUSTOMERID=?");

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Deleted");

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ----------------------------------------------------
    public static String searchCustomer(int id) {

        Customer c = findById(id);

        if (c == null)
            return "Customer Not Exist";

        return "Customer Exists: " + c.firstName;
    }

    // ----------------------------------------------------
    public static Customer findById(int id) {

        try {

            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps =
            con.prepareStatement(
            "SELECT * FROM CUSTOMER WHERE CUSTOMERID=?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Customer c = new Customer();

                c.customerId = rs.getInt("CUSTOMERID");
                c.firstName = rs.getString("FIRSTNAME");
                c.email = rs.getString("EMAIL");
                c.contactNo = rs.getString("CONTACT");

                return c;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ----------------------------------------------------
    public static void listCustomers(int limit) {

        try {

            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps =
            con.prepareStatement(
            "SELECT * FROM CUSTOMER FETCH FIRST ? ROWS ONLY");

            ps.setInt(1, limit);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                rs.getInt("CUSTOMERID") + " - " +
                rs.getString("FIRSTNAME"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------
    public static boolean editCustomerData(int id, Scanner sc) {

        Customer c = findById(id);

        if (c == null) {
            System.out.println("Not Found");
            return false;
        }

        System.out.print("New Email: ");
        c.email = sc.nextLine();

        try {

            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps =
            con.prepareStatement(
            "UPDATE CUSTOMER SET EMAIL=? WHERE CUSTOMERID=?");

            ps.setString(1, c.email);
            ps.setInt(2, id);

            ps.executeUpdate();

            System.out.println("Updated");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
}
}import java.sql.*;

public class DatabaseConnection {



        // CHANGE THESE ACCORDING TO YOUR SYSTEM
    private static final String URL =
    "jdbc:oracle:thin:@localhost:1521:xe";

    private static final String USER =
    "system";

    private static final String PASS =
    "oracle";

    // ----------------------------------------------------
    public static Connection getConnection() {

        try {

            Class.forName(
            "oracle.jdbc.driver.OracleDriver");

            return DriverManager.getConnection(
                URL, USER, PASS);

        } catch (Exception e) {

            System.out.println(
            "DB Connection Failed");

            e.printStackTrace();

            return null;
        }
    }
    // ----------------------------------------------------
}import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Employee {

    // ----------------------------------------------------
// FIELDS
// ----------------------------------------------------
    public int empId;
    public String firstName;
    public String lastName;
    public String email;
    public String address;
    public String passwordHash;
    public String aadhar;
    public String pan;
    public String currentRole;
    public String gender;
    public String contactNo;
    public LocalDate dob;
    public LocalDate joiningDate;
    public LocalDateTime lastAccessed;

    // ----------------------------------------------------
    // VIEW PROFILE
    // ----------------------------------------------------
    public void viewEmployeeProfile() {

        System.out.println("\n---- EMPLOYEE PROFILE ----");

        System.out.println("ID: " + empId);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Contact: " + contactNo);
        System.out.println("Role: " + currentRole);
        System.out.println("Address: " + address);
        System.out.println("Aadhar: " + aadhar);
        System.out.println("PAN: " + pan);
        System.out.println("DOB: " + dob);
        System.out.println("Joined: " + joiningDate);
    }

    // ----------------------------------------------------
    // ADD EMPLOYEE
    // ----------------------------------------------------
    public static Employee addEmployee(Scanner sc) {

        Employee e = new Employee();

        System.out.println("\nEnter Employee Details");

        System.out.print("First Name: ");
        e.firstName = sc.nextLine();

        System.out.print("Last Name: ");
        e.lastName = sc.nextLine();

        System.out.print("Email: ");
        e.email = sc.nextLine();

        if (!InputValidators.isValidEmail(e.email)) {
            System.out.println("Invalid Email");
            return null;
        }

        System.out.print("Contact: ");
        e.contactNo = sc.nextLine();

        System.out.print("Address: ");
        e.address = sc.nextLine();

        System.out.print("Aadhar: ");
        e.aadhar = sc.nextLine();

        System.out.print("PAN: ");
        e.pan = sc.nextLine();

        System.out.print("Gender: ");
        e.gender = sc.nextLine();

        System.out.print("Role: ");
        e.currentRole = sc.nextLine();

        System.out.print("DOB (dd-MM-yyyy): ");
        e.dob = InputValidators.parseDate(sc.nextLine());

        System.out.print("Password: ");
        String pass = sc.nextLine();

        e.passwordHash = PasswordManager.hashPassword(pass);

        e.joiningDate = LocalDate.now();

        boolean ok = saveToDB(e);

        if (ok)
            System.out.println("Employee Added");
        else
            System.out.println("Failed to Add");

        return e;
    }

    // ----------------------------------------------------
    // SAVE TO DB
    // ----------------------------------------------------
    public static boolean saveToDB(Employee e) {

        try {

            Connection con = DatabaseConnection.getConnection();

            String q =
            "INSERT INTO EMPLOYEE " +
            "(FIRSTNAME,LASTNAME,EMAIL,ADDRESS,PASSWORDHASH,AADHAR,PAN," +
            "ROLE,GENDER,CONTACTNO,DOB,JOININGDATE) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(q);

            ps.setString(1, e.firstName);
            ps.setString(2, e.lastName);
            ps.setString(3, e.email);
            ps.setString(4, e.address);
            ps.setString(5, e.passwordHash);
            ps.setString(6, e.aadhar);
            ps.setString(7, e.pan);
            ps.setString(8, e.currentRole);
            ps.setString(9, e.gender);
            ps.setString(10, e.contactNo);
            //ps.setDate(11, Date.valueOf(e.dob));
            //ps.setDate(12, Date.valueOf(e.joiningDate));

            ps.executeUpdate();

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // ----------------------------------------------------
    // FIND BY ID
    // ----------------------------------------------------
    public static Employee findById(int id) {

        try {

            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps =
            con.prepareStatement("SELECT * FROM EMPLOYEE WHERE EMPID=?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Employee e = new Employee();

                e.empId = rs.getInt("EMPID");
                e.firstName = rs.getString("FIRSTNAME");
                e.lastName = rs.getString("LASTNAME");
                e.email = rs.getString("EMAIL");
                e.contactNo = rs.getString("CONTACTNO");
                e.address = rs.getString("ADDRESS");
                e.currentRole = rs.getString("ROLE");
                e.aadhar = rs.getString("AADHAR");
                e.pan = rs.getString("PAN");

                return e;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // ----------------------------------------------------
    // EDIT PROFILE
    // ----------------------------------------------------
    public static boolean editEmployeeProfile(int id, Scanner sc) {

        Employee e = findById(id);

        if (e == null) {
            System.out.println("Not Found");
            return false;
        }

        System.out.print("New Email: ");
        e.email = sc.nextLine();

        System.out.print("New Contact: ");
        e.contactNo = sc.nextLine();

        try {

            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps =
            con.prepareStatement(
            "UPDATE EMPLOYEE SET EMAIL=?, CONTACTNO=? WHERE EMPID=?");

            ps.setString(1, e.email);
            ps.setString(2, e.contactNo);
            ps.setInt(3, id);

            ps.executeUpdate();

            System.out.println("Updated");
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
}
}
//import java.util.Scanner;

public class FinCoreMain {
/*
    -   FinCoreMain.java
    -   DatabaseConnection.java
    -   AuthManager.java
    -   PasswordManager.java
    -   InputValidators.java
    -   Employee.java
    -   Customer.java
    -   Account.java
*/

private static Scanner sc = new Scanner(System.in);
private static String loggedRole = null;
private static int loggedId = -1;

public static void main(String[] args) {

    System.out.println("====================================");
    System.out.println("     WELCOME TO FINCORE BMS         ");
    System.out.println("====================================");

    while (true) {
        showLoginMenu();
    }
}

// ------------------------------------------------------
// LOGIN MENU
// ------------------------------------------------------
public static void showLoginMenu() {

    System.out.println("\nLOGIN MENU");
    System.out.println("1. Employee Login");
    System.out.println("2. Customer Login");
    System.out.println("3. Exit");

    int choice = readInt("Enter choice: ");

    switch (choice) {

        case 1:
            employeeLogin();
            break;

        case 2:
            customerLogin();
            break;

        case 3:
            System.out.println("Thank you for using FinCore BMS");
            System.exit(0);

        default:
            System.out.println("Invalid choice");
    }
}

// ------------------------------------------------------
// EMPLOYEE LOGIN FLOW
// ------------------------------------------------------
private static void employeeLogin() {

    int id = readInt("Enter Employee ID: ");
    String pass = readString("Enter Password: ");

    boolean ok = AuthManager.loginEmployee(id, pass);

    if (ok) {
        loggedRole = "EMPLOYEE";
        loggedId = id;

        AuthManager.updateLastAccessed("EMPLOYEE", id);

        showMainMenu();
    } 
    else {
        System.out.println("Invalid Credentials");
    }
}

// ------------------------------------------------------
// CUSTOMER LOGIN FLOW
// ------------------------------------------------------
private static void customerLogin() {

    int id = readInt("Enter Customer ID: ");
    String pass = readString("Enter Password: ");

    boolean ok = AuthManager.loginCustomer(id, pass);

    if (ok) {
        loggedRole = "CUSTOMER";
        loggedId = id;

        AuthManager.updateLastAccessed("CUSTOMER", id);

        showMainMenu();
    } 
    else {
        System.out.println("Invalid Credentials");
    }
}

// ------------------------------------------------------
// MAIN MENU AFTER LOGIN
// ------------------------------------------------------
public static void showMainMenu() {

    while (true) {

        System.out.println("\nMAIN MENU - Logged as " + loggedRole);

        System.out.println("1. Employee Operations");
        System.out.println("2. Customer Operations");
        System.out.println("3. Account Operations");
        System.out.println("4. Logout");

        int ch = readInt("Enter choice: ");

        switch (ch) {

            case 1:
                showEmployeeMenu();
                break;

            case 2:
                showCustomerMenu();
                break;

            case 3:
                showAccountMenu();
                break;

            case 4:
                loggedRole = null;
                loggedId = -1;
                return;

            default:
                System.out.println("Invalid choice");
        }
    }
}

// ------------------------------------------------------
// EMPLOYEE MENU
// ------------------------------------------------------
public static void showEmployeeMenu() {

    while (true) {

        System.out.println("\nEMPLOYEE MENU");

        System.out.println("1. Add Employee");
        System.out.println("2. View Employee Profile");
        System.out.println("3. Edit Employee");
        System.out.println("4. Back");

        int ch = readInt("Enter choice: ");

        switch (ch) {

            case 1:
                //Employee.addEmployee(sc);
                break;

            case 2:
                int id = readInt("Enter Emp ID: ");
                //Employee e = Employee.findById(id);
                int e = 0; // Placeholder since Employee class is not implemented
                if (e != 0){ // null
                    //e.viewEmployeeProfile();
                }
                else
                    System.out.println("Not Found");
                break;

            case 3:
                int eid = readInt("Enter Emp ID: ");
                //Employee.editEmployeeProfile(eid, sc);
                break;

            case 4:
                return;

            default:
                System.out.println("Invalid");
        }
    }
}

// ------------------------------------------------------
// CUSTOMER MENU
// ------------------------------------------------------
public static void showCustomerMenu() {

    while (true) {

        System.out.println("\nCUSTOMER MENU");

        System.out.println("1. Add Customer");
        System.out.println("2. Edit Customer");
        System.out.println("3. Delete Customer");
        System.out.println("4. Search Customer");
        System.out.println("5. List Customers");
        System.out.println("6. Back");

        int ch = readInt("Enter choice: ");

        switch (ch) {

            case 1:
                //Customer.addCustomer(sc);
                break;

            case 2:
                int id = readInt("Enter ID: ");
                //Customer.editCustomerData(id, sc);
                break;

            case 3:
                int did = readInt("Enter ID: ");
                //Customer.deleteCustomerData(did);
                break;

            case 4:
                int sid = readInt("Enter ID: ");
                //String res = Customer.searchCustomer(sid);
                //System.out.println(res);
                break;

            case 5:
                int lim = readInt("Limit: ");
                //Customer.listCustomers(lim);
                break;

            case 6:
                return;

            default:
                System.out.println("Invalid");
        }
    }
}

// ------------------------------------------------------
// ACCOUNT MENU
// ------------------------------------------------------
public static void showAccountMenu() {

    while (true) {

        System.out.println("\nACCOUNT MENU");

        System.out.println("1. Open Account");
        System.out.println("2. Edit Account");
        System.out.println("3. Search Account");
        System.out.println("4. List Accounts");
        System.out.println("5. Back");

        int ch = readInt("Enter choice: ");

        switch (ch) {

            case 1:
                int cid = readInt("Customer ID: ");
                Account.openAccount(cid, sc);
                break;

            case 2:
                long acc = Long.parseLong(readString("Account No: "));
                Account.editAccountDetails(acc, sc);
                break;

            case 3:
                long sacc = Long.parseLong(readString("Account No: "));
                Account.searchAccount(sacc);
                break;

            case 4:
                int lim = readInt("Limit: ");
                Account.listAccounts(lim);
                break;

            case 5:
                return;

            default:
                System.out.println("Invalid");
        }
    }
}

// ------------------------------------------------------
// SAFE INPUT HELPERS
// ------------------------------------------------------

public static int readInt(String msg) {

    System.out.print(msg);

    try {
        return Integer.parseInt(sc.nextLine());
    } 
    catch (Exception e) {
        System.out.println("Enter valid number");
        return readInt(msg);
    }
}

public static String readString(String msg) {

    System.out.print(msg);
    return sc.nextLine();
}

}import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InputValidators {


    // ----------------------------------------------------
    public static boolean isValidEmail(
        String email) {

        return email != null &&
        email.matches(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // ----------------------------------------------------
    public static boolean isValidPhone(
        String phone) {

        return phone != null &&
            phone.matches("[0-9]{10}");
    }

    // ----------------------------------------------------
    public static boolean isValidAadhar(
        String a) {

        return a != null &&
            a.matches("[0-9]{12}");
    }

    // ----------------------------------------------------
    public static boolean isValidPAN(
        String p) {

        return p != null &&
            p.matches("[A-Z]{5}[0-9]{4}[A-Z]");
    }

    // ----------------------------------------------------
    public static LocalDate parseDate(
        String s) {

        try {

            DateTimeFormatter f =
            DateTimeFormatter.ofPattern(
                "dd-MM-yyyy");

            return LocalDate.parse(s, f);

        } catch (Exception e) {

            System.out.println(
                "Invalid Date Format");

            return LocalDate.now();
        }
    }
// ----------------------------------------------------

}   import java.security.MessageDigest;
import java.sql.*;

public class PasswordManager {

        // ----------------------------------------------------
    // HASH PASSWORD USING SHA-256
    // ----------------------------------------------------
    public static String hashPassword(String plain) {

        try {

            MessageDigest md =
                MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(plain.getBytes());

            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ----------------------------------------------------
    // VERIFY PASSWORD
    // ----------------------------------------------------
    public static boolean verify(
        String plain,
        String storedHash) {

        String newHash = hashPassword(plain);

        return newHash != null &&
            newHash.equals(storedHash);
    }

    // ----------------------------------------------------
    // GET STORED HASH FROM DB
    // ----------------------------------------------------
    public static String getStoredPasswordHash(
        String role,
        int id) {

        try {

            Connection con =
                DatabaseConnection.getConnection();

            String table =
                role.equals("EMPLOYEE") ?
                "EMPLOYEE" : "CUSTOMER";

            String idcol =
                role.equals("EMPLOYEE") ?
                "EMPID" : "CUSTOMERID";

            PreparedStatement ps =
            con.prepareStatement(
            "SELECT PASSWORDHASH FROM " +
            table + " WHERE " + idcol + "=?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("PASSWORDHASH");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}