package Server;

import org.sqlite.SQLiteConfig;
import java.sql.*;
import java.util.Scanner;


public class Main {

    //----------------------------------------------------------------

    public static Connection db = null;


    private static void openDatabase(String dbFile) {

        try {

            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("Database connection successfully established");

        } catch (Exception exception) {

            System.out.println("Database connection error: " + exception.getMessage());

        }

    }

    private static void closeDatabase() {

        try {

            db.close();
            System.out.println("Disconnected from database.");

        } catch (Exception exception) {

            System.out.println("Database disconnection error: " + exception.getMessage());

        }


    }

    //----------------------------------------------------------------


    public static void main(String[] args) {

//Input into data base

        openDatabase("Users.db");

        Scanner input = new Scanner(System.in);

        System.out.println("Enter your first name: ");
        String firstNameInput = input.nextLine();

        System.out.println("Enter your sure name: ");
        String lastNameInput = input.nextLine();

        System.out.println("Please choose your username: ");
        String userNameInput = input.nextLine();

        System.out.println("Enter your user ID number: ");
        int userIdInput = input.nextInt();

//INPUT STATEMENT

        try {

            PreparedStatement ps = db.prepareStatement("INSERT INTO userInfo (userID, userName, firstName, lastName) VALUES (?, ?, ?, ?)");

            ps.setInt(1, userIdInput);
            ps.setString(2, userNameInput);
            ps.setString(3, firstNameInput);
            ps.setString(4, lastNameInput);

            ps.executeUpdate();

            //code using the database goes here


        } catch (Exception exception){
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
// DELETE STATEMENT

        try {

            PreparedStatement ps = db.prepareStatement("DELETE FROM userInfo WHERE userName = 12");
            ps.execute();
            //code using the database goes here


        } catch (Exception exception){
            System.out.println("Database disconnection error: " + exception.getMessage());
        }

// CREATE STATEMENT

        try {

            PreparedStatement ps = db.prepareStatement("CREATE TABLE test_table(column_1 datatype, column_2 datatype, column_3 datatype) ");
            ps.execute();
            //code using the database goes here


        } catch (Exception exception){
            System.out.println("Database disconnection error: " + exception.getMessage());
        }

// UPDATE STATEMENT

        try {

            PreparedStatement ps = db.prepareStatement("UPDATE table_name SET test_column = test_value WHERE test_column = test_value");
            ps.execute();
            //code using the database goes here

        } catch (Exception exception){
            System.out.println("Database disconnection error: " + exception.getMessage());
        }







        // OUTPUT STATEMENT
        try {

            PreparedStatement ps = db.prepareStatement("SELECT userID, userName, firstName, lastName FROM userInfo");

            ResultSet results = ps.executeQuery();
            while(results.next()){

                int userID = results.getInt(1);
                String userName = results.getString(2);
                String firstName = results.getString(3);
                String lastName = results.getString(4);
                System.out.println(userID + " " + userName + " " + firstName + " " + lastName);

            }


        } catch (Exception exception){

            System.out.println("Database error: " + exception.getMessage());

        }

        closeDatabase();
    }
}














































