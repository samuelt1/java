// JDBC Example - printing a database's metadata
// Coded by Chen Li/Kirill Petrov Winter, 2005
// Slightly revised for ICS185 Spring 2005, by Norman Jacobson

import java.sql.*;                              // Enable SQL processing
import java.util.Scanner;

public class JDBC1 {

    public static void main(String[] arg) throws Exception {

        Connection connection = null;
        boolean connected = true;
        while (connected) {
            System.out.println("At anytime you may type 'exit' to quit.");
            
            System.out.println("Enter your username:");
            String username = readNext();

            System.out.println("Enter your password:");
            String password = readNext();
            
            // Incorporate mySQL driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            try {
                // Connect to the database
                connection = DriverManager.getConnection("jdbc:mysql:///moviedb", username, password);
                connected = false;
            } catch (SQLException e) {
                System.out.println("Your Username and Password did not match");
            }
        }

        // create update DB statement -- deleting second record of table; return status
        Statement select = connection.createStatement();
        ResultSet result = select.executeQuery("Select * from stars");

        // Get metatdata from stars; print # of attributes in table
        System.out.println("The results of the query");
        ResultSetMetaData metadata = result.getMetaData();
        System.out.println("There are " + metadata.getColumnCount() + " columns");

        // Print type of each attribute
        for (int i = 1; i <= metadata.getColumnCount(); i++) {
            System.out.println("Type of column " + i + " is " + metadata.getColumnTypeName(i));
        }

        // print table's contents, field by field
        while (result.next()) {
            System.out.println("Id = " + result.getInt(1));
            System.out.println("Name = " + result.getString(2) + result.getString(3));
            System.out.println("DOB = " + result.getString(4));
            System.out.println("photoURL = " + result.getString(5));
            System.out.println();
        }
    }

    public static String readNext() {
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
        if (input.equals("exit")) {
            System.exit(0);
        }
        return input;
    }
}
